package com.example.test2project.tomato;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.test2project.R;

public class TomatoClockActivity extends AppCompatActivity {
    TomatoView clockView;
    Button btnClock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomato_clock);
        clockView = findViewById(R.id.clockView);
        btnClock = findViewById(R.id.btn_start);
        btnClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!clockView.isStarted() && clockView.getCountdownTime()!=0){
                    clockView.start();
                    btnClock.setText("结 束");
                }else if (!clockView.isStarted() && clockView.getCountdownTime()==0){
                    Toast.makeText(TomatoClockActivity.this, "请先设置学习时间", Toast.LENGTH_SHORT).show();
                } else if(clockView.isStarted() && clockView.getCountdownTime()!=0){
                    clockView.cancel();
                    btnClock.setText("开 始");
                }
            }
        });


    }
}