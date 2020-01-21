package com.example.test2project.plans;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test2project.R;

public class PlanInfoActivity extends AppCompatActivity {

    private ImageButton buttonDeletePlan;
    private CheckBox checkBoxIsFinished;
    private TextView textTitle;
    private TextView textInfo;
    private PlanBean planBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_info);

        planBean = (PlanBean)getIntent().getSerializableExtra("itemInfo");

        init();

        checkBoxIsFinished.setChecked(planBean.isFinished());
        textTitle.setText(planBean.getTitle());

    }

    public void init(){
        buttonDeletePlan = findViewById(R.id.buttonDeletePlan);
        checkBoxIsFinished = findViewById(R.id.checkBoxOfPlanInfo);
        textTitle = findViewById(R.id.textTitleOfPlanInfo);
        textInfo = findViewById(R.id.textPlanInfo);
    }

}
