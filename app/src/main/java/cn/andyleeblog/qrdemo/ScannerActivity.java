package cn.andyleeblog.qrdemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * 二维码扫描界面
 * Created by andy on 17-5-2.
 */
public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final String LOG_TAG = ScannerActivity.class.getSimpleName();

    static final String SCAN_RESULT = "scan_result";
    static final String SCAN_FORMAT = "scan_format";

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        mScannerView.setAutoFocus(true);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.v(LOG_TAG, result.getText() + ", " + result.getBarcodeFormat().toString());
        Intent returnIntent = new Intent();
        returnIntent.putExtra(SCAN_RESULT, result.toString());
        returnIntent.putExtra(SCAN_FORMAT, result.getBarcodeFormat().toString());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
