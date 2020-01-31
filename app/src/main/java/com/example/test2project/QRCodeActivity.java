package com.example.test2project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test2project.plans.PlanBean;
import com.example.test2project.plans.PlanInfoActivity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class QRCodeActivity extends AppCompatActivity {
    private ImageView imageQRCode;
    private EditText editCreateQRCode;
    private Button buttonCreateQRCode;
    private Button buttonScanQRCode;
    private TextView textQRCodeResult;
    private static final String TAG = "QRCodeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        ZXingLibrary.initDisplayOpinion(this);

        //获取相机拍摄读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //版本判断
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 1);
            }
        }

        imageQRCode = findViewById(R.id.imageQRCode);
        editCreateQRCode = findViewById(R.id.editCreateQRCode);
        buttonCreateQRCode = findViewById(R.id.buttonCreateQRCode);
        buttonScanQRCode = findViewById(R.id.buttonScanQRCode);
        textQRCodeResult = findViewById(R.id.textQRCodeResult);

        buttonCreateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeStr = editCreateQRCode.getText().toString();
                Bitmap bitmap = CodeUtils.createImage(codeStr, 250, 250, null);
                imageQRCode.setImageBitmap(bitmap);
            }
        });

        buttonScanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRCodeActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 2:{
                Log.d(TAG, "onActivityResult: 2");
                if (data != null){
                    Log.d(TAG, "onActivityResult: data!=null");
                    Bundle bundle =data.getExtras();
                    if(bundle == null){
                        Log.d(TAG, "onActivityResult: bundle==null");
                        return;
                    }
                    if(bundle.getInt(CodeUtils.RESULT_TYPE)==CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        textQRCodeResult.setText("解析结果："+result);
                    }else if(bundle.getInt(CodeUtils.RESULT_TYPE)==CodeUtils.RESULT_FAILED){
                        textQRCodeResult.setText("解析二维码失败！");
                    }
                }else {
                    Log.d(TAG, "onActivityResult: data==null");
                }

                break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void Request() {
        //获取相机拍摄读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //版本判断
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 1);
            }
        }
    }


}
