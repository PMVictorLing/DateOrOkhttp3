package com.example.mayn.myfisrtapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mayn.myfisrtapp.view.LoadingDialog;

/**
 * 基类activity
 */

public abstract class BaseActivity extends AppCompatActivity {

    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 显示加载框
     */
    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, "加载中。。。", true);
        }
        mLoadingDialog.show();

    }

    public void cancelLoadingDialog() {
        if (mLoadingDialog != null){
            mLoadingDialog.dismiss();
        }
    }
}
