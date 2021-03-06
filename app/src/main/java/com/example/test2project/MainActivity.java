package com.example.test2project;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alltestapplication.AllTestMainActivity;
import com.example.test2project.charts.ChartsActivity;
import com.example.test2project.charts.LearningReportActivity;
import com.example.test2project.plans.PlanListActivity;
import com.example.test2project.tomato.TomatoClockActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    private static final String TAG = "MainActivity";
    private ImageView imageView;
    private Button buttonTomatoClock;
    private Button buttonSDK;
    private Button buttonImage;
    private Button buttonCharts;
    private Button buttonSignInTestDrive;
    private Button buttonLearningReport;
    private Button buttonPlanList;
    private Button buttonBase64;
    private Button buttonQRCode;
    private Button buttonProgressDialog;
    private Button buttonStepCounter;
    private Button buttonBanner;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TestAar.testMyAar();

        buttonTomatoClock = findViewById(R.id.tomatoClock);
        buttonSDK = findViewById(R.id.sdk);
        buttonImage = findViewById(R.id.loadPicture);
        buttonCharts = findViewById(R.id.myCharts);
        buttonSignInTestDrive = findViewById(R.id.signInTestDrive);
        buttonLearningReport = findViewById(R.id.learningReport);
        buttonPlanList = findViewById(R.id.buttonPlanList);
        buttonBase64 = findViewById(R.id.buttonBase64);
        buttonQRCode = findViewById(R.id.buttonQRCode);
        buttonProgressDialog = findViewById(R.id.buttonProgressDialog);
        buttonStepCounter = findViewById(R.id.buttonStepCounter);
        buttonBanner = findViewById(R.id.buttonBanner);

        imageView = findViewById(R.id.image);
        String url = "https://profile.csdnimg.cn/7/4/E/2_qq_43529443";
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.wallhaven_13llv3)
                .error(R.drawable.wallhaven_kwr27q)
                .into(imageView);


        buttonTomatoClock.setOnClickListener(this);
        buttonImage.setOnClickListener(this);
        buttonSDK.setOnClickListener(this);
        buttonCharts.setOnClickListener(this);
        buttonSignInTestDrive.setOnClickListener(this);
        buttonLearningReport.setOnClickListener(this);
        buttonPlanList.setOnClickListener(this);
        buttonBase64.setOnClickListener(this);
        buttonQRCode.setOnClickListener(this);
        buttonProgressDialog.setOnClickListener(this);
        buttonStepCounter.setOnClickListener(this);
        buttonBanner.setOnClickListener(this);
    }

    private void downLoadImage(String url){
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse, response: "+response);
                if(response.body().contentLength() > 0){
                    InputStream in =response.body().byteStream();
                    File file = new File(Environment.getExternalStorageDirectory()+File.separator
                            + BuildConfig.APPLICATION_ID + File.separator+"images");
                    Log.d(TAG, "onResponse, file.toString: "+file.toString());
                    if(file.getParentFile().exists()){
                        FileOutputStream out = new FileOutputStream(file);
                        int len;
                        while((len=in.read())!=-1){
                            out.write(len);
                        }
                    }else{
                        file.getParentFile().mkdirs();
                    }
                }else{
                    Log.d(TAG, "onResponse: 要访问的文件不存在！");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.sdk:{
             startActivity(new Intent(MainActivity.this, AllTestMainActivity.class));
             break;
         }
         case R.id.tomatoClock:{
             Log.d(TAG, "onClick: tomatoClock");
             Toast.makeText(MainActivity.this, "you clicked tomato ！", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(MainActivity.this, TomatoClockActivity.class));
             break;
         }
         case R.id.loadPicture:{
             /*
                int k = imageView.getResources().equals("R.drawable.wallhaven_13llv3") ? 1 :0;
                int res = k==1 ? R.drawable.wallhaven_kwr27q : R.drawable.wallhaven_13llv3;
                Glide.with(MainActivity.this)
                        .load(res)
                        .dontAnimate()
                        .into(imageView);
                */
             /*
             downLoadImage("https://i0.hdslb.com/bfs/sycp/creative_img/201912/5ddd8f713cdcf1e219e57771fa7f8412.jpg@1100w_484h_1c_100q.jpg");
             File file = new File(Environment.getExternalStorageDirectory()+File.separator
                     + BuildConfig.APPLICATION_ID + File.separator+"images");
                     */
             Glide.with(MainActivity.this)
                     .load("https://i0.hdslb.com/bfs/sycp/creative_img/201912/5ddd8f713cdcf1e219e57771fa7f8412.jpg@1100w_484h_1c_100q.jpg")
                     .into(imageView);
             break;
         }
         case R.id.myCharts:{
             Toast.makeText(MainActivity.this, "Show Charts !", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(MainActivity.this, ChartsActivity.class));
             break;
         }
         case R.id.signInTestDrive:{
             Toast.makeText(MainActivity.this, "Sign In !", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(MainActivity.this, SignInActivity.class));
             break;
         }
         case R.id.learningReport:{
             Toast.makeText(MainActivity.this, "Learning Report!", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(MainActivity.this, LearningReportActivity.class));
             break;
         }
         case R.id.buttonPlanList:{
             Toast.makeText(MainActivity.this, "Learning Report!", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(MainActivity.this, PlanListActivity.class));
             break;
         }
         case R.id.buttonBase64:{
             Toast.makeText(MainActivity.this, "Base64 Image!", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(MainActivity.this, Base64Activity.class));
             break;
         }
         case R.id.buttonQRCode:{
             Toast.makeText(MainActivity.this, "Quick Response Code!", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(MainActivity.this, QRCodeActivity.class));
             break;
         }
         case R.id.buttonProgressDialog:{
             startActivity(new Intent(MainActivity.this, ProgressDialogActivity.class));
             break;
         }
         case R.id.buttonStepCounter:{
             startActivity(new Intent(MainActivity.this, StepCounterActivity.class));
             break;
         }
         case R.id.buttonBanner:{
             startActivity(new Intent(MainActivity.this, BannerActivity.class));
             break;
         }
         default:break;
     }
    }

}
