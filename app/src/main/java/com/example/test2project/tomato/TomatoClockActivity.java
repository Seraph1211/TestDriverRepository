package com.example.test2project.tomato;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.test2project.R;

public class TomatoClockActivity extends AppCompatActivity {

    TomatoView clockView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomato_clock);
        clockView = findViewById(R.id.clockView);
        textView = findViewById(R.id.tv_start);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clockView.start();
            }
        });
    }
}
