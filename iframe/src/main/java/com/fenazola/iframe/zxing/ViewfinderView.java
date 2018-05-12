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

import java.util.Collection;
import java.util.HashSet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.fenazola.iframe.R;
import com.google.zxing.ResultPoint;
import com.fenazola.iframe.zxing.camera.CameraManager;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

    private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
    private static final long ANIMATION_DELAY = 100L;
    private static final int OPAQUE = 0xFF;
    private static final int SPEEN_DISTANCE = 8; // 扫描线的下落速度
    private static final int CURRENT_POINT_OPACITY = 0xA0;
    private static final int MAX_RESULT_POINTS = 20;
    private static final int POINT_SIZE = 6;

    private CameraManager cameraManager;
    private final Paint paint;
    private Bitmap resultBitmap;
    private final int maskColor;
    private final int resultColor;
    private final int frameColor;
    private final int resultPointColor;
    private int scannerAlpha;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;

    private boolean isFirst;
    private int slideTop;
    @SuppressWarnings("unused")
    private int slideBottom;

    private Bitmap qrLineBitmap;// 扫描线是一张图片
    private int qrWidth;// 扫描线的长
    private int qrHeight;// 扫描线的高
    private Rect qrSrc;
    private Rect qrDst;

    // This constructor is used when the class is built from an XML resource.
    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize these once for performance rather than calling them every time in onDraw().
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Resources resources = getResources();

        qrLineBitmap = BitmapFactory.decodeResource(resources,
                R.drawable.qrcode_scan_line);
        qrWidth = qrLineBitmap.getWidth();
        qrHeight = qrLineBitmap.getHeight();
        qrSrc = new Rect(0, 0, qrWidth, qrHeight);

        maskColor = resources.getColor(R.color.viewfinder_mask);
        resultColor = resources.getColor(R.color.result_view);
        frameColor = resources.getColor(R.color.viewfinder_frame);
        resultPointColor = resources.getColor(R.color.possible_result_points);
        possibleResultPoints = new HashSet<ResultPoint>(5);
        lastPossibleResultPoints = null;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        if (cameraManager == null) {
            return; // not ready yet, early draw before done configuring
        }
        Rect frame = cameraManager.getFramingRect();
        Rect previewFrame = cameraManager.getFramingRectInPreview();
        if (frame == null || previewFrame == null) {
            return;
        }

        // 初始化中间线滑动的最上边和最下边
        if (!isFirst) {
            isFirst = true;
            slideTop = frame.top;
            slideBottom = frame.bottom;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {

            // 绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
            slideTop += SPEEN_DISTANCE;
            if (slideTop >= frame.bottom) {
                slideTop = frame.top;
            }

            qrDst = new Rect(frame.left, slideTop, frame.right, slideTop
                    + qrHeight);
            canvas.drawBitmap(qrLineBitmap, qrSrc, qrDst, null);

            int linewidth = 10;

            // Draw a red "laser scanner" line through the middle to show decoding is active
            paint.setColor(frameColor);

            // draw rect 画扫描区域的四个直角
            canvas.drawRect(15 + frame.left, 15 + frame.top,
                    15 + (linewidth + frame.left), 15 + (50 + frame.top), paint);
            canvas.drawRect(15 + frame.left, 15 + frame.top,
                    15 + (50 + frame.left), 15 + (linewidth + frame.top), paint);
            canvas.drawRect(-15 + ((0 - linewidth) + frame.right),
                    15 + frame.top, -15 + (1 + frame.right),
                    15 + (50 + frame.top), paint);
            canvas.drawRect(-15 + (-50 + frame.right), 15 + frame.top, -15
                    + frame.right, 15 + (linewidth + frame.top), paint);
            canvas.drawRect(15 + frame.left, -15 + (-49 + frame.bottom),
                    15 + (linewidth + frame.left), -15 + (1 + frame.bottom),
                    paint);
            canvas.drawRect(15 + frame.left, -15
                            + ((0 - linewidth) + frame.bottom), 15 + (50 + frame.left),
                    -15 + (1 + frame.bottom), paint);
            canvas.drawRect(-15 + ((0 - linewidth) + frame.right), -15
                    + (-49 + frame.bottom), -15 + (1 + frame.right), -15
                    + (1 + frame.bottom), paint);
            canvas.drawRect(-15 + (-50 + frame.right), -15
                    + ((0 - linewidth) + frame.bottom), -15 + frame.right, -15
                    + (linewidth - (linewidth - 1) + frame.bottom), paint);

            float scaleX = frame.width() / (float) previewFrame.width();
            float scaleY = frame.height() / (float) previewFrame.height();

            Collection<ResultPoint> currentPossible = possibleResultPoints;
            Collection<ResultPoint> currentLast = lastPossibleResultPoints;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new HashSet<ResultPoint>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(OPAQUE);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 6.0f, paint);
                }
            }
            if (currentLast != null) {
                paint.setAlpha(OPAQUE / 2);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 3.0f, paint);
                }
            }

            // Request another update at the animation interval, but only
            // repaint the laser line,
            // not the entire viewfinder mask.
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
                    frame.right, frame.bottom);
        }
    }

    public void drawViewfinder() {
        Bitmap resultBitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (resultBitmap != null) {
            resultBitmap.recycle();
        }
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live scanning display.
     *
     * @param barcode An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode) {
        resultBitmap = barcode;
        invalidate();
    }


    public void addPossibleResultPoint(ResultPoint point) {
        possibleResultPoints.add(point);
    }
}
