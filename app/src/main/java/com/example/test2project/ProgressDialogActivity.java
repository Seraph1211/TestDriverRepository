package com.example.test2project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProgressDialogActivity extends AppCompatActivity {

    private Button buttonShowDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_dialog);

        buttonShowDialog = findViewById(R.id.buttonShowDialog);
        buttonShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomProgressDialog.showLoading(ProgressDialogActivity.this);
            }
        });
    }
}
