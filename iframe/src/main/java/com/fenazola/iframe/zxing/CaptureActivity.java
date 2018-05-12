/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fenazola.iframe.zxing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.fenazola.iframe.R;
import com.fenazola.iframe.zxing.camera.CameraManager;
import com.fenazola.iframe.zxing.clipboard.ClipboardInterface;
import com.fenazola.iframe.zxing.result.ResultHandler;
import com.fenazola.iframe.zxing.result.ResultHandlerFactory;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.HybridBinarizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * This activity opens the camera and does the actual scanning on a background thread. It draws a
 * viewfinder to help the user place the barcode correctly, shows feedback as the image processing
 * is happening, and then overlays the results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    private static final long DELAY_SHOW = 1 * 500;

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private Result savedResultToShow;
    private ViewfinderView viewfinderView;
    private Result lastResult;
    private boolean hasSurface;
    private boolean copyToClipboard = false;
    private IntentSource source;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private AmbientLightManager ambientLightManager;

    private final Handler mHandler = new Handler();
    private ImageView barcode_image_view;

    ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (getActionBar() != null) {
            getActionBar().hide();
        }

        setContentView(R.layout.zxing_capture);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        ambientLightManager = new AmbientLightManager(this);

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        barcode_image_view = (ImageView) findViewById(R.id.barcode_image_view);
        barcode_image_view.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // historyManager must be initialized here to update the history preference

        // CameraManager must be initialized here, not in onCreate(). This is necessary because we don't
        // want to open the camera driver and measure the screen size if we're going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the wrong size and partially
        // off screen.
        cameraManager = new CameraManager(getApplication());

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);


        handler = null;
        lastResult = null;


        resetStatusView();

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the camera.
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();
        ambientLightManager.start(cameraManager);

        inactivityTimer.onResume();

        Intent intent = getIntent();

        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;

        if (intent != null) {

            String action = intent.getAction();
            String dataString = intent.getDataString();

            if (Intents.Scan.ACTION.equals(action)) {

                // Scan the formats the intent requested, and return the result to the calling activity.
                source = IntentSource.NATIVE_APP_INTENT;
                decodeFormats = DecodeFormatManager.parseDecodeFormats(intent);
                decodeHints = DecodeHintManager.parseDecodeHints(intent);

                if (intent.hasExtra(Intents.Scan.WIDTH) && intent.hasExtra(Intents.Scan.HEIGHT)) {
                    int width = intent.getIntExtra(Intents.Scan.WIDTH, 0);
                    int height = intent.getIntExtra(Intents.Scan.HEIGHT, 0);
                    if (width > 0 && height > 0) {
                        cameraManager.setManualFramingRect(width, height);
                    }
                }

                if (intent.hasExtra(Intents.Scan.CAMERA_ID)) {
                    int cameraId = intent.getIntExtra(Intents.Scan.CAMERA_ID, -1);
                    if (cameraId >= 0) {
                        cameraManager.setManualCameraId(cameraId);
                    }
                }
            } else if (dataString != null &&
                    dataString.contains("http://www.google") &&
                    dataString.contains("/m/products/scan")) {

                // Scan only products and send the result to mobile Product Search.
                source = IntentSource.PRODUCT_SEARCH_LINK;
                decodeFormats = DecodeFormatManager.PRODUCT_FORMATS;

            }
            characterSet = intent.getStringExtra(Intents.Scan.CHARACTER_SET);

        }
    }

    private int getCurrentOrientation() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_90:
                return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            default:
                return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
        }
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        ambientLightManager.stop();
        beepManager.close();
        cameraManager.closeDriver();
        //historyManager = null; // Keep for onActivityResult
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (source == IntentSource.NATIVE_APP_INTENT) {
                    setResult(RESULT_CANCELED);
                    finish();
                    return true;
                }
                if ((source == IntentSource.NONE || source == IntentSource.ZXING_LINK) && lastResult != null) {
                    restartPreviewAfterDelay(0L);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_FOCUS:
            case KeyEvent.KEYCODE_CAMERA:
                // Handle these events so they don't launch the Camera app
                return true;
            // Use volume up/down to turn on light
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                cameraManager.setTorch(false);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                cameraManager.setTorch(true);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
        // Bitmap isn't used yet -- will be used soon
        if (handler == null) {
            savedResultToShow = result;
        } else {
            if (result != null) {
                savedResultToShow = result;
            }
            if (savedResultToShow != null) {
                Message message = Message.obtain(handler, MessageIds.decode_succeeded, savedResultToShow);
                handler.sendMessage(message);
            }
            savedResultToShow = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show the results.
     *
     * @param rawResult   The contents of the barcode.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param barcode     A greyscale bitmap of the camera data which was decoded.
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();
        lastResult = rawResult;
        ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);

        boolean fromLiveScan = barcode != null;
        if (fromLiveScan) {
            // Then not from history, so beep/vibrate and we have an image to draw on
            beepManager.playBeepSoundAndVibrate();
            drawResultPoints(barcode, scaleFactor, rawResult);
        }
        handleDecodeInternally(rawResult, resultHandler, barcode);
    }

    /**
     * Superimpose a line for 1D or dots for 2D to highlight the key features of the barcode.
     *
     * @param barcode     A bitmap of the captured image.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param rawResult   The decoded results which contains the points to draw.
     */
    private void drawResultPoints(Bitmap barcode, float scaleFactor, Result rawResult) {
        ResultPoint[] points = rawResult.getResultPoints();
        if (points != null && points.length > 0) {
            Canvas canvas = new Canvas(barcode);
            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.result_points));
            if (points.length == 2) {
                paint.setStrokeWidth(4.0f);
                drawLine(canvas, paint, points[0], points[1], scaleFactor);
            } else if (points.length == 4 &&
                    (rawResult.getBarcodeFormat() == BarcodeFormat.UPC_A ||
                            rawResult.getBarcodeFormat() == BarcodeFormat.EAN_13)) {
                // Hacky special case -- draw two lines, for the barcode and metadata
                drawLine(canvas, paint, points[0], points[1], scaleFactor);
                drawLine(canvas, paint, points[2], points[3], scaleFactor);
            } else {
                paint.setStrokeWidth(10.0f);
                for (ResultPoint point : points) {
                    if (point != null) {
                        canvas.drawPoint(scaleFactor * point.getX(), scaleFactor * point.getY(), paint);
                    }
                }
            }
        }
    }

    private static void drawLine(Canvas canvas, Paint paint, ResultPoint a, ResultPoint b, float scaleFactor) {
        if (a != null && b != null) {
            canvas.drawLine(scaleFactor * a.getX(),
                    scaleFactor * a.getY(),
                    scaleFactor * b.getX(),
                    scaleFactor * b.getY(),
                    paint);
        }
    }

    // Put up our own UI for how to handle the decoded contents.
    private void handleDecodeInternally(Result rawResult, ResultHandler resultHandler, Bitmap barcode) {
        CharSequence displayContents = resultHandler.getDisplayContents();
        if (copyToClipboard && !resultHandler.areContentsSecure()) {
            ClipboardInterface.setText(displayContents, this);
        }
        viewfinderView.setVisibility(View.GONE);
        ImageView barcodeImageView = (ImageView) findViewById(R.id.barcode_image_view);
        barcodeImageView.setImageBitmap(barcode);
        final Intent back = new Intent("com.fenazola.iframe.zxing");
        back.setData(Uri.parse(rawResult.getBarcodeFormat().toString()
                + "://" + rawResult.getText()));
        back.putExtra("result", rawResult.getText());
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                setResult(RESULT_OK, back);
                finish();
            }
        }, DELAY_SHOW);
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
            }
            decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.camera_title));
        builder.setMessage(getString(R.string.camera_framework_bug));
        builder.setPositiveButton(R.string.button_sure, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(MessageIds.restart_preview, delayMS);
        }
        resetStatusView();
    }

    private void resetStatusView() {
        viewfinderView.setVisibility(View.VISIBLE);
        lastResult = null;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 解析二维码图片文件
     * @param path
     */
    private void decodeFile(final String path) {
        RGBLuminanceSource source = null;
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(null);
        try {
            Bitmap imageBitmap = BitmapFactory
                    .decodeStream(new FileInputStream(new File(path)));
            int width = imageBitmap.getWidth();
            int height = imageBitmap.getHeight();
            int[] pixels = new int[width * height];
            imageBitmap
                    .getPixels(pixels, 0, width, 0, 0, width, height);
            source = new RGBLuminanceSource(width, height, pixels);
            Result result = null;
            if (source != null) {
                BinaryBitmap bitmap = new BinaryBitmap(
                        new HybridBinarizer(source));
                if (bitmap != null) {
                    result = multiFormatReader.decodeWithState(bitmap);
                    handleDecode(result, imageBitmap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleDecode(final Result obj, final Bitmap barcode) {
        inactivityTimer.onActivity();
        viewfinderView.setVisibility(View.GONE);
        try {
            barcode_image_view.setImageBitmap(barcode);
            barcode_image_view.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
        inactivityTimer.onActivity();
        final Intent back = new Intent("com.fenazola.iframe.zxing");
        back.setData(Uri.parse(obj.getBarcodeFormat().toString()
                + "://" + obj.getText()));
        back.putExtra("result", obj.getText());
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                setResult(RESULT_OK, back);
                finish();
            }
        }, DELAY_SHOW);
    }

}
