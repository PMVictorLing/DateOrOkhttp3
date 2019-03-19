package com.example.mayn.myfisrtapp.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * File description.
 * 公用的 webiview .除了点餐。其它用到h5页面的地方都用到了此方法类
 *
 * @author ly
 * @date 2018/12/4
 */
public class webViewAll {
    public static String user_Id;
    public static String lat;
    public static Activity mContext;
    public static WebView mWebView;
    private static final int LOCAL_CROP = 13;// 本地图库
    private static String biaoshiId;
    private static String PayType;

    public static void  initWebView(Activity context, WebView webView, String loadUrl, String userId, String latlng) {
        user_Id = userId;
        lat = latlng;
        mContext = context;
        mWebView = webView;
        //设置支持JavaScript脚本
//        WebSettings webSettings = webView.getSettings();
////        webView.setE
//        webSettings.setJavaScriptEnabled(true);
//        //设置可以访问文件
//        webSettings.setAllowFileAccess(true);
//        //设置支持缩放
//        webSettings.setBuiltInZoomControls(false);
//
//        webSettings.setDatabaseEnabled(true);
//        //使用localStorage则必须打开
//        webSettings.setDomStorageEnabled(true);
//        webView.setWebChromeClient(new WebChromeClient());
//        webSettings.setGeolocationEnabled(true);
        //webSettings.setGeolocationDatabasePath(dir);
//        if (Build.VERSION.SDK_INT >= 19) {
//            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        }

        WebSettings wSettings = webView.getSettings();
        // 启用触控缩放
        wSettings.setBuiltInZoomControls(true);
        // 启用支持视窗meta标记（可实现双击缩放）
        wSettings.setUseWideViewPort(true);
        // 以缩略图模式加载页面
        wSettings.setLoadWithOverviewMode(true);
        // 启用JavaScript支持
        wSettings.setJavaScriptEnabled(true);
        wSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= 19) {
            wSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        // 设置将接收各种通知和请求的WebViewClient（在WebView加载所有的链接）
        wSettings.setDisplayZoomControls(false);


        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); // Android默认的处理方式
                handler.proceed();  // 接受所有网站的证书
                //handleMessage(Message msg); // 进行其他处理
            }
        });
        //http://gaoyans.cross.echosite.cn/hamburger/index
        //http://gaoyans.cross.echosite.cn/hamburger/Category
        //http://gaoyans.cross.echosite.cn/hamburger/index
//        webView.loadUrl("http://gaoyans.cross.echosite.cn/hamburger/index");

        // http://gaoyans.cross.echosite.cn/hamburger/index?url=UserCenter

        webView.addJavascriptInterface(new InJavaScript(), "injs");
        webView.loadUrl(loadUrl);

    }

    static class InJavaScript {

        @JavascriptInterface
        public String initDataJavaScript(final String res) {
            Log.e("HBJ H5返回数据：", res);
            return "汉堡机H5调试";
        }

        @JavascriptInterface
        public String getUserid(final String res) {
            Log.e("HBJ H5返回userid：", res);
            return user_Id;
        }

        @JavascriptInterface
        public String userLogin(final String url) {
            Log.e("HBJ H5返回数据：", url);
            return "处理跳转";
        }

        @JavascriptInterface
        public String getClientAddr(final String url) {
            Log.e("HBJ 经纬度：", url);
            return lat;
        }

        @JavascriptInterface
        public String ChooseShop(final String url) {
            Log.e("HBJ 跳转无人店：", url);
            Message message = new Message();
            message.what = 1;
            return "";
        }

        @JavascriptInterface
        public String LogOut(final String url) {
            Log.e("HBJ H5退出登录：", url);
            return "";
        }

        @JavascriptInterface
        public String BackToAndroid(final String url) {
            Log.e("HBJ H5返回Android：", url);
            mContext.finish();
            return "";
        }

        @JavascriptInterface
        public String openFile(final String type, final String biaoshiid) {
            Log.e("HBJ H5上传头像：", type);
            biaoshiId = biaoshiid;
            Message message = new Message();
            message.what = 2;
            message.obj = type;

            return "";
        }

        @JavascriptInterface
        public String PayDeskForAndroid(final String type, final String data) {
            Log.e("HBJ 支付功能：", data);
           /* PayType = type;
            if (type.equals("wechat")) {

                //这里是要处理h5传过来的 加密参数。37fa31e2-9d1d-4919-a74b-e9c58db02907
                String key = FileAES.getMiKey(user_Id); //前端生成key
                Log.e("wechat",key+"");
                //将加密字符和key放入此方法中
//               String str = FileAES.decrypt("0vMRBHN/c8+Bo+dPgP3dwSGDc/bens6opLWHhXVPv24AfNLUGDFnyzaOrHejGQmKeJhoPPOMs99fD3d9re48T+dhqxPhyAITbnz5zXsgXuulQevVzTnoA4Ur+JehvK84OlI1mhVJ8UisiJt3BFfBjZe0+e09D9ScMZaNkT1eYDHOUjgY76a55uH4/n/PNdvBixAQnS+OVhZ3s+54cbOXW4R1bz5va/XZnN0MQYXvoAGhL5plcaj7kwPvytg0BLecQbv7tXecf8YF1btjm5fZxd54bvLt3LQv40bTHOY4ppYKrLD2Bjidbse4Ym3T4k7BnY/I8i/0lvfQNSZsB49CyA==",key);
                String str = FileAES.decrypt(data +
                        "", key);
                Log.e("wechat",str+"");

                objWeixinPay json = new Gson().fromJson(str, objWeixinPay.class);
                Message message = new Message();
                message.what = 5;
                message.obj = json;
                mHandler.sendMessage(message);
            } else if (type.equals("alipay")) {

                Message message = new Message();
                message.what = 5;
                message.obj = data;
                mHandler.sendMessage(message);
            }*/
            return "";
        }

        @JavascriptInterface
        public String GetAlipayBindID(final String data) {
            Log.e("HBJ 个人中心支付宝绑定：", data);
            Message message = new Message();
            message.what = 3;
            return "";
        }

        @JavascriptInterface
        public String GetWechatBindID(final String data) {
            Log.e("HBJ 个人中心微信绑定：", data);
            Message message = new Message();
            message.what = 4;
            return "";
        }

        @JavascriptInterface
        public String cancelBindSub(final String data) {
            Log.e("取消绑定：", data);
           /* if (data.equals("wechat")) {
                SPUtils.put(mContext, "wechatbanding", "");
            } else if (data.equals("alipay")) {
                SPUtils.put(mContext, "paybanding", "");
            }*/
            return "";
        }

        @JavascriptInterface
        public String shareOnline(final String url, final String id) {
            Log.e("商品详情的分享事件：", url + "  分享id=" + id);
//            sharedWebView(InterfaceFinals.webView_Head+url,id);
            return "";
        }

        @JavascriptInterface
        public String openMap(final String url) {
            Log.e("HBJ H5跳转地图：", url);
            Message message = new Message();
            message.what = 6;
            message.obj = url;
            return "";
        }

        //图片类型
        private String mType;

       /* private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        break;
                    case 2:
//                          mContext.startActivity(new Intent(mContext, LoginActivity.class));

                        //图片类型
                        mType = (String) msg.obj;

                        //这里调弹框
                        showTypeDialog(mType);
//                          takePhotoOrSelectPicture();
                        break;

                    case 3: //支付宝绑定
                        SPUtils.put(mContext, "alipay", "info_alipay");//这个的区别是判断是h5调起还是android 端调起
                        Intent intent1 = new Intent(mContext, PayDemoActivity.class);
                        intent1.putExtra("data", "login");//这个的区别是判断是登录还是支付
                        mContext.startActivity(intent1);
                        break;

                    case 4: //微信绑定
                        SPUtils.put(mContext, "wechat", "Info_wechat");
                        if (!MyApplication.mWxApi.isWXAppInstalled()) {
                            Tool.Toast(mContext, "您还未安装微信客户端");
                            return;
                        }
                        final SendAuth.Req reqq = new SendAuth.Req();
                        reqq.scope = "snsapi_userinfo";
                        reqq.state = "diandi_wx_login_test";
                        MyApplication.mWxApi.sendReq(reqq);
                        break;


                    case 5: //调起支付宝 或者 微信支付功能
                        if (PayType.equals("wechat")) {
                            Log.e("HBJ 支付功能：", "调起微信");
                            IWXAPI api = WXAPIFactory.createWXAPI(mContext, InterfaceFinals.weixin_APPID, true);
                            api.registerApp(InterfaceFinals.weixin_APPID);
                            objWeixinPay weixinPay = (objWeixinPay) msg.obj;
                            PayReq req = new PayReq();
                            req.appId = InterfaceFinals.weixin_APPID;
                            req.partnerId = weixinPay.getPartnerid();
                            req.prepayId = weixinPay.getPrepayid();
                            req.packageValue = "Sign=WXPay";
                            req.nonceStr = weixinPay.getNoncestr();
                            req.timeStamp = weixinPay.getTimestamp();
                            req.sign = weixinPay.getSign();
                            api.sendReq(req);
                            //以上代码就是发起微信支付的方法
                        } else if (PayType.equals("alipay")) {//发起支付宝支付
                            Intent intent3 = new Intent(mContext, PayDemoActivity.class);
                            intent3.putExtra("data", "ailipay");
                            intent3.putExtra("result", msg.obj.toString());//订单号
                            mContext.startActivity(intent3);
                        }
                        break;
                    case 6:
                        Intent intent = new Intent(mContext, DiscoveryMap.class);
                        intent.putExtra("url", msg.obj.toString());
                        mContext.startActivity(intent);
                        break;
                }
            }
        };
    }


    private static void sharedWebView(String url,String id) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        List<ResolveInfo> resolveInfos = mContext.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfos.isEmpty()) {
            return;
        }
        List<Intent> targetIntents = new ArrayList<>();
        for (ResolveInfo info : resolveInfos) {
            ActivityInfo ainfo = info.activityInfo;
            switch (ainfo.name) {
                case "com.tencent.mm.ui.tools.ShareImgUI":
                    addShareIntent(targetIntents, ainfo,url,id);
                    break;
                case "com.tencent.mobileqq.activity.JumpActivity":
                    addShareIntent(targetIntents, ainfo,url,id);
                    break;
                case "com.sina.weibo.weiyou.share.WeiyouShareDispatcher":
                    addShareIntent(targetIntents, ainfo,url,id);
                    break;
            }
        }
        if (targetIntents == null || targetIntents.size() == 0) {
            return;
        }
        Intent chooserIntent = Intent.createChooser(targetIntents.remove(0), "请选择分享平台");
        if (chooserIntent == null) {
            return;
        }
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetIntents.toArray(new Parcelable[]{}));
        try {
            mContext.startActivity(chooserIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "找不到该分享应用组件", Toast.LENGTH_SHORT).show();
        }
    }


    private static void addShareIntent(List<Intent> list, ActivityInfo ainfo,String url,String id) {
        Intent target = new Intent(Intent.ACTION_SEND);
        target.setType("text/plain");
//        target.putExtra(Intent.EXTRA_TEXT, "啵嗒美食分享链接" + "http://v587.cross.echosite.cn/hamburger/index?url=ProductStatic");
        target.putExtra(Intent.EXTRA_TEXT, "啵嗒美食分享链接" + url +"?id="+ id);
        target.setPackage(ainfo.packageName);
        target.setClassName(ainfo.packageName, ainfo.name);
        list.add(target);
    }

    *//**
         * 标识id
         *//*
    public static String getbiaoshiId() {
        return biaoshiId;
    }


    public static void showTypeDialog(final String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(mContext, R.layout.infohead_choose_dailog, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    //图片类型广播
                    Intent intent1 = new  Intent();
                    intent1.setAction("com.hbj.userpic");
                    intent1.putExtra("type",type);
                    //发送无序广播
                    mContext.sendBroadcast(intent1);

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    mContext.startActivityForResult(intent, 001);
                }
//                PictureUtils.doPickPhotoAction(mContext, false, false,0);
                dialog.dismiss();

            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
//                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
//                mContext.startActivityForResult(intent2, 2);// 采用ForResult打开
//                dialog.dismiss();

//                Intent intent = new Intent(mContext, SelectImageSingleActivity.class);
//                mContext.startActivity(intent);

                Bundle bundle = new Bundle();
                bundle.putString("action_photo", "action_photo");
                Intent intent1 = new Intent(mContext, ClipPictrueActivity.class);
                //判断图片类型
                intent1.putExtra("type",type);
                intent1.putExtras(bundle);
                mContext.startActivity(intent1);
                dialog.dismiss();
//                ISCameraConfig config = new ISCameraConfig.Builder()
//                        .needCrop(true) // 裁剪
//                        .cropSize(1, 1, 200, 200)
//                        .build();
//
//                ISNav.getInstance().toCameraActivity(this, config, 8);
//                dialog.dismiss();
//                if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(mContext,new String[]{Manifest.permission.CAMERA},1);
//                }else{
//                    openCamera();
//                }

            }
        });
        dialog.setView(view);
        dialog.show();
    }


    static Uri imageUri;

    public static Uri getImage_uri() {
        return imageUri;
    }*/

    }
}
