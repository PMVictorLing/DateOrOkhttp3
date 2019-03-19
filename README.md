# DateOrOkhttp3
日期和Okhttp3使用

1.okhttp证书过滤：
    private static OkHttpClient mOkHttpClient = new OkHttpClient()
            .newBuilder()
            .sslSocketFactory(SSLSocketclient.getSSLSocketFactory())//过滤https证书
            .hostnameVerifier(SSLSocketclient.getHostnameVerifier())//过滤https证书
            .build();
            
      public class SSLSocketclient {
    //获取这个SSLSocketFactory
    public static javax.net.ssl.SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取TrustManager
    private static TrustManager[] getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        return trustAllCerts;
    }

    //获取HostnameVerifier
    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }   
    
    
2.日期控件和弹框：
     2.1 控件：
       <DatePicker
            android:id="@+id/datepicker"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"></DatePicker>
     
     2.2 弹框：
      new DatePickerDialog(SecondeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = year+"-"+(month+1)+"-"+dayOfMonth;
                        Log.e("datepicker","date=>"+date);
                        Toast.makeText(SecondeActivity.this, "date=>"+date, Toast.LENGTH_SHORT).show();
                    }
                },mYear,mCalender.get(Calendar.MONTH),mCalender.get(Calendar.DAY_OF_MONTH)).show();
                
            
3.自定义加载框：
      
 /**
 * 加载对话框
 */

public class LoadingDialog extends ProgressDialog {

    private String mMessage;

    private TextView mTitleTv;


    public LoadingDialog(Context context, String message, boolean canceledOnTouchOutside) {
        super(context, R.style.Theme_Light_LoadingDialog);
        this.mMessage = message;
        // 如果触摸屏幕其它区域,可以选择让这个progressDialog消失或者无变化
        setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        mTitleTv = (TextView) findViewById(R.id.tv_loading_dialog);
        mTitleTv.setText(mMessage);
        setCancelable(false);//不可取消
    }

    public void setTitle(String message) {
        this.mMessage = message;
        mTitleTv.setText(mMessage);
    }



    /**
     * 显示在底部
     */
    public void showButtom() {
        // WindowManager windowManager = ((Activity)
        // mContext).getWindowManager();
        // Display display = windowManager.getDefaultDisplay();
        //
        // WindowManager.LayoutParams lp = getWindow().getAttributes();
        // lp.width = (int) (display.getWidth() * 0.8);
        // getWindow().setAttributes(lp);
        // super.show();
    }

}
      
