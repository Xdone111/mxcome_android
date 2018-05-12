package com.fenazola.mxcome.utils;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.fenazola.iframe.umeng.UmengUtils;
import com.fenazola.mxcome.R;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zss.library.widget.CommonDialog;
import com.zss.library.widget.CommonWhiteDialog;

/**
 * Created by zm on 2017/2/23.
 */
public class ShareUtils {

    public static void showShareDialog(final Activity context, String dialogTitle, final String shareText) {
        CommonWhiteDialog dialog = new CommonWhiteDialog(context);
        dialog.setTitle(dialogTitle);
        dialog.setMiddleView(R.layout.layout_share);
        dialog.setDisplayBottomEnable(false);
        dialog.setMiddleBgRes(R.drawable.dialog_corner_bottom_white);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.textView1:

                        break;
                    case R.id.textView2:
                        UmengUtils.shareWeb(context, R.mipmap.ic_launcher, shareText, SHARE_MEDIA.WEIXIN, new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                            }
                        });
                        break;
                    case R.id.textView3:
                        UmengUtils.shareWeb(context, R.mipmap.ic_launcher, shareText, SHARE_MEDIA.QQ, new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                            }
                        });
                        break;
                    case R.id.textView4:
                        UmengUtils.shareWeb(context, R.mipmap.ic_launcher, shareText, SHARE_MEDIA.WEIXIN_CIRCLE, new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                            }
                        });
                        break;
                    case R.id.textView5:
                        UmengUtils.shareWeb(context, R.mipmap.ic_launcher, shareText, SHARE_MEDIA.SINA, new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                            }
                        });
                        break;
                    case R.id.textView6:
                        UmengUtils.shareWeb(context, R.mipmap.ic_launcher, shareText, SHARE_MEDIA.QZONE, new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                            }
                        });
                        break;
                }
            }
        };
        TextView textView1 = (TextView) dialog.findViewById(R.id.textView1);
        TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);
        TextView textView3 = (TextView) dialog.findViewById(R.id.textView3);
        TextView textView4 = (TextView) dialog.findViewById(R.id.textView4);
        TextView textView5 = (TextView) dialog.findViewById(R.id.textView5);
        TextView textView6 = (TextView) dialog.findViewById(R.id.textView6);
        textView1.setOnClickListener(listener);
        textView2.setOnClickListener(listener);
        textView3.setOnClickListener(listener);
        textView4.setOnClickListener(listener);
        textView5.setOnClickListener(listener);
        textView6.setOnClickListener(listener);
        dialog.show();

    }

    public static void showShareDialog(final Activity context, String dialogTitle, final String shareText, final String webTitle, final String webDesc) {
        CommonWhiteDialog dialog = new CommonWhiteDialog(context);
        dialog.setTitle(dialogTitle);
        dialog.setMiddleView(R.layout.layout_share);
        dialog.setDisplayBottomEnable(false);
        dialog.setMiddleBgRes(R.drawable.dialog_corner_bottom_white);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.textView1:

                        break;
                    case R.id.textView2:
                        UmengUtils.shareWeb(context, R.mipmap.ic_launcher, webTitle, webDesc, shareText, SHARE_MEDIA.WEIXIN, new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                            }
                        });
                        break;
                    case R.id.textView3:
                        UmengUtils.shareWeb(context, R.mipmap.ic_launcher, webTitle, webDesc, shareText, SHARE_MEDIA.QQ, new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                            }
                        });
                        break;
                    case R.id.textView4:
                        UmengUtils.shareWeb(context, R.mipmap.ic_launcher, webTitle, webDesc, shareText, SHARE_MEDIA.WEIXIN_CIRCLE, new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                            }
                        });
                        break;
                    case R.id.textView5:
                        UmengUtils.shareWeb(context, R.mipmap.ic_launcher, webTitle, webDesc, shareText, SHARE_MEDIA.SINA, new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                            }
                        });
                        break;
                    case R.id.textView6:
                        UmengUtils.shareWeb(context, R.mipmap.ic_launcher, webTitle, webDesc, shareText, SHARE_MEDIA.QZONE, new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                            }
                        });
                        break;
                }
            }
        };
        TextView textView1 = (TextView) dialog.findViewById(R.id.textView1);
        TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);
        TextView textView3 = (TextView) dialog.findViewById(R.id.textView3);
        TextView textView4 = (TextView) dialog.findViewById(R.id.textView4);
        TextView textView5 = (TextView) dialog.findViewById(R.id.textView5);
        TextView textView6 = (TextView) dialog.findViewById(R.id.textView6);
        textView1.setOnClickListener(listener);
        textView2.setOnClickListener(listener);
        textView3.setOnClickListener(listener);
        textView4.setOnClickListener(listener);
        textView5.setOnClickListener(listener);
        textView6.setOnClickListener(listener);
        dialog.show();

    }
}
