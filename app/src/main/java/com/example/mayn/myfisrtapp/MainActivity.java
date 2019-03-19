package com.example.mayn.myfisrtapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;

import com.example.mayn.myfisrtapp.bean.PresentBean;
import com.example.mayn.myfisrtapp.http.HttpUtils;
import com.example.mayn.myfisrtapp.http.model.HttpCallBack;
import com.example.mayn.myfisrtapp.util.FileAES;
import com.example.mayn.myfisrtapp.view.webViewAll;

import java.util.HashMap;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private String mUserId = "37fa31e2-9d1d-4919-a74b-e9c58db02907";

    //加密的字符 uyKWlNsyfqlV0azwiQWOdMNYKfSACqrCoMuWXTeuMm7FDUnmEyd4EZmHNph/AB1dVF6sjoPjCEIH1s6WrkKZMA==
//    private String mData = "dfHWrnDNAqLg4Z/1+HJp0WloNSCCMza4GpsNgu6wBrTx/HpwrLABGRqwWw7o1tHljtvPfiMCho9hx6R/JozmCvp7Wg3E79q8BAAJOCQJCUoSCPld9+DjCva5qcwdKhoNDJyMtcEe8gX7OHuD30uQ/f2CX/VMKpn9N7yfE6DV4BfYPefce+V6SpZXEy5YqnFIyDVPEcNCXWIR3cdTAXc1V1i6VjDg+t6DkCWOEi6NU5PueCPzi1EnIl66SJOahZK2kkhND3Qm8Or8Ylyd5YRVQTkLAY66+5wdb5fZavEFc17fxI3ocMDWJ1Jkj7zMrZnyFpNeQBgSFLSDH+NRRyZmWw==";

    private String mData = "uyKWlNsyfqlV0azwiQWOdMNYKfSACqrCoMuWXTeuMm7FDUnmEyd4EZmHNph/AB1dVF6sjoPjCEIH1s6WrkKZMA==" +
            "";
    private WebView mWebView;
    private Button btAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // //这里是要处理h5传过来的 加密参数。37fa31e2-9d1d-4919-a74b-e9c58db02907
        String key = FileAES.getMiKey(mUserId); //前端生成key
        Log.e("FileAES", key + "");
        //将加密字符和key放入此方法中
        //               String str = FileAES.decrypt("0vMRBHN/c8+Bo+dPgP3dwSGDc/bens6opLWHhXVPv24AfNLUGDFnyzaOrHejGQmKeJhoPPOMs99fD3d9re48T+dhqxPhyAITbnz5zXsgXuulQevVzTnoA4Ur+JehvK84OlI1mhVJ8UisiJt3BFfBjZe0+e09D9ScMZaNkT1eYDHOUjgY76a55uH4/n/PNdvBixAQnS+OVhZ3s+54cbOXW4R1bz5va/XZnN0MQYXvoAGhL5plcaj7kwPvytg0BLecQbv7tXecf8YF1btjm5fZxd54bvLt3LQv40bTHOY4ppYKrLD2Bjidbse4Ym3T4k7BnY/I8i/0lvfQNSZsB49CyA==",key);
        String str = FileAES.decrypt(mData +
                "", key);
        Log.e("FileAES", str + "");

        mWebView = this.findViewById(R.id.web_view);
//        String urlname = "http://v587.cross.echosite.cn/hamburger/orderdrink";
        String urlname = "https://www.baidu.com/";
        webViewAll.initWebView(MainActivity.this, mWebView, urlname, "0", "");

        //button属性动画
        btAnimation = findViewById(R.id.bt_animation);
        btAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAnimation.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bly_button_scale_animation));

                startActivity(new Intent(MainActivity.this, SecondeActivity.class));

            }
        });

        Log.e("ActivityTest", "call onCreate");

        //线程测试
        new Thread(new Runnable() {
            @Override
            public void run() {
               /* while (true) {
                    Log.e("ActivityTest","thread **********************");
                }*/
                Log.e("ActivityTest", "thread **********************");
            }
        }).start();

        //测试api
        findViewById(R.id.bt_test_api).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://pms-uat-fps.tech-trans.com/tt-pms-ws/rest/qrpay/sales/present
                postHttpRequest();

            }
        });

    }

    /**
     * post请求
     */
    private void postHttpRequest() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("apiKey", "cUmRhg20W2zUEAb0");
        map.put("type", "WXPAY");
        map.put("rrn", "S01012019030510243531");
        map.put("storeCode", "S01");
        map.put("tillId", "01");
        map.put("salesman", "9999");
        map.put("cashier", "9999");
        map.put("txDateTime", "2019-03-05T10:24:35+0800");
        map.put("amt", 10);
        map.put("additionalAmt", 0);
        map.put("ccy", "HKD");
        map.put("remarks", "");
        map.put("extendParam", "");
        map.put("lang", "zh-HK");
        map.put("bitmap", true);
        map.put("timestamp", "1551752675570");
        map.put("sign", "2Xapwciy+UxHaTapTmHgZyGxYlU=");


        HttpUtils.with(this).Url("https://pms-uat-fps.tech-trans.com/tt-pms-ws/rest/qrpay/sales/present").post()
                .addParam(map)
                .execute(new HttpCallBack<PresentBean>() {


                    @Override
                    protected void onPreExecute() {
                        //加载进度条
                        showLoadingDialog();

                    }

                    @Override
                    public void onSuccess(final PresentBean result) {
                        cancelLoadingDialog();
                        android.util.Log.e(TAG, "Success -- >PresentBean=>" + result.getQrcode());
                        //这是运行在子线程中
                        //切换UI线程 几种方式


                    }

                    @Override
                    public void OnError(Exception e) {
                        cancelLoadingDialog();
                        android.util.Log.e(TAG, "OnError -- >Exception=>" + e.getMessage());
                    }

                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ActivityTest", "call onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ActivityTest", "call onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ActivityTest", "call onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ActivityTest", "call onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ActivityTest", "call onDestroy");
    }
}
