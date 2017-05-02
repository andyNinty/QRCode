package cn.andyleeblog.qrdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bolex.pressscan.ScanTools;

public class MainActivity extends AppCompatActivity {
    ImageView qrImage;
    Button createQr;
    Button scanQr;
    EditText input;
    private static final int SCAN_REQUEST_ZXING_SCANNER = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createQr = (Button) findViewById(R.id.btn_create);
        scanQr = (Button) findViewById(R.id.btn_scan);
        input = (EditText) findViewById(R.id.et_input);
        qrImage = (ImageView) findViewById(R.id.iv_qr);
        createQr.setOnClickListener(new View.OnClickListener() {
            final String filePath = getExternalCacheDir().getPath()
                    + "qr_" + System.currentTimeMillis() + ".jpg";

            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean success = QRCodeUtil.createQRImage(input.getText().toString(), 800, 800,
                                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), filePath);
                        if (success) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "二维码生成成功,扫描或长按二维码即可识别", Toast.LENGTH_LONG).show();
                                    qrImage.setImageBitmap(BitmapFactory.decodeFile(filePath));
                                }
                            });
                        }

                    }
                }).start();
            }
        });

        scanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ScannerActivity.class), SCAN_REQUEST_ZXING_SCANNER);
            }
        });

        qrImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ScanTools.scanCode(v, new ScanTools.ScanCall() {
                    @Override
                    public void getCode(String s) {
                        Toast.makeText(MainActivity.this, "data: " + s, Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCAN_REQUEST_ZXING_SCANNER) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra(ScannerActivity.SCAN_RESULT);
                Toast.makeText(this, "data: " + result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
