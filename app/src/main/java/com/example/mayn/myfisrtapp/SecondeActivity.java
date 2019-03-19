package com.example.mayn.myfisrtapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class SecondeActivity extends BaseActivity implements View.OnTouchListener {

    private boolean mWork = true;
    private ScrollView mScrollview;
    private DatePicker mDatePicker;
    private Calendar mCalender;
    private int mYear;
    private TextView mTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconde);
        Log.e("ActivityTest 2", "call onCreate");
        //线程测试
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (mWork) {

                    Log.e("ActivityTest 2","thread ********************** start");
                    try {
                        Thread.sleep(3000);

                        cancelLoadingDialog();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.e("ActivityTest 2","thread ********************** stop");

            }
        }).start();

        showLoadingDialog();



        //监听scrollview
        mScrollview = (ScrollView)this.findViewById(R.id.scrollview);
        mScrollview.setOnTouchListener(this);

        //日期选择
        mDatePicker = (DatePicker)this.findViewById(R.id.datepicker);
        //初始化
        mCalender = Calendar.getInstance();
        mYear = mCalender.get(Calendar.YEAR);

        mDatePicker.init(mYear,mCalender.get(Calendar.MONTH),mCalender.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener(){

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                Log.e("datepicker",year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                Toast.makeText(SecondeActivity.this, "date=>"+date, Toast.LENGTH_SHORT).show();
            }
        });

        //日历弹框
        mTextContent = (TextView) this.findViewById(R.id.tv_close);
        mTextContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelLoadingDialog();

                new DatePickerDialog(SecondeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = year+"-"+(month+1)+"-"+dayOfMonth;
                        Log.e("datepicker","date=>"+date);
                        Toast.makeText(SecondeActivity.this, "date=>"+date, Toast.LENGTH_SHORT).show();
                    }
                },mYear,mCalender.get(Calendar.MONTH),mCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    /**
     * stop thread running
     */
    public void stopThread() {
        if (mWork) {
            mWork = false;
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ActivityTest 2", "call onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ActivityTest 2", "call onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ActivityTest 2", "call onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ActivityTest 2", "call onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopThread();

        Log.e("ActivityTest 2", "call onDestroy");

        //file存储数据
        String text = mTextContent.getText().toString();
        BufferedWriter bufferedWrider = null;
        if (!TextUtils.isEmpty(text)){
            try {
                FileOutputStream fileOutput = openFileOutput("data", Context.MODE_PRIVATE);
                bufferedWrider = new BufferedWriter(new OutputStreamWriter(fileOutput));
                bufferedWrider.write(text);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bufferedWrider != null){
                    try {
                        bufferedWrider.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE://移动监听
                //判断文章顶部
                if (mScrollview.getScrollY() <= 0){
                    Log.e("ActivityTest 2", "文章----顶部");
                    Toast.makeText(this, "文章----顶部", Toast.LENGTH_SHORT).show();
                }

                if (mScrollview.getChildAt(0).getMeasuredHeight() <= mScrollview.getScrollY() + mScrollview.getHeight()){
                    Log.e("ActivityTest 2", "文章----底部");
                    Toast.makeText(this,"文章----底部",Toast.LENGTH_SHORT).show();
                }

                Log.e("ActivityTest 2", "文章 mScrollview.getScrollY()=》"+mScrollview.getScrollY()
                +" mScrollview.getChildAt(0).getMeasuredHeight()=>"+mScrollview.getChildAt(0).getMeasuredHeight() +" mScrollview.getHeight()="+mScrollview.getHeight());
                break;
        }
        return false;
    }
}
