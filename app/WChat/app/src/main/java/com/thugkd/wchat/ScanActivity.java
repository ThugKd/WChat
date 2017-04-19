package com.thugkd.wchat;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanActivity extends AppCompatActivity implements QRCodeView.Delegate {

    private Toolbar toolbar;
    private TextView tvTitle;
    private ZXingView mQRCodeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        //take out actionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.titlebar_title);
        tvTitle.setText(R.string.scan);
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.startSpot();
        mQRCodeView.setResultHandler(this);

    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(ScanActivity.this,R.string.scan,Toast.LENGTH_SHORT).show();
        vibrate();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(ScanActivity.this,"scan error",Toast.LENGTH_SHORT).show();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
