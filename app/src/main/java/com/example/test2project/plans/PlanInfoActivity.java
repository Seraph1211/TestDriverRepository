package com.example.test2project.plans;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test2project.R;

/**
 * 返回上一层活动PlanListActivity时
 * 带一个String: back_message: remove-删除  modify-修改 no_change-没有改变
 * 带一个int: back_position-任务在列表中的位置
 * 带一个bean: back_bean-含计划的信息
 */

public class PlanInfoActivity extends AppCompatActivity {

    public static final int plan_info_activity = 1;
    private ImageButton buttonDeletePlan;
    private ImageButton buttonBackPlanInfo;
    private CheckBox checkBoxIsFinished;
    private EditText editTextTitle;
    private EditText editTextInfo;
    private PlanBean planBean;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_info);

        planBean = (PlanBean)getIntent().getSerializableExtra("itemInfo");
        position = getIntent().getIntExtra("position", -1);

        init();

        checkBoxIsFinished.setChecked(planBean.isFinished());
        editTextTitle.setText(planBean.getTitle());

    }

    public void init(){
        buttonDeletePlan = findViewById(R.id.buttonDeletePlan);
        checkBoxIsFinished = findViewById(R.id.checkBoxOfPlanInfo);
        editTextTitle = findViewById(R.id.editTextTitleOfPlanInfo);
        editTextInfo = findViewById(R.id.editTextPlanInfo);
        buttonBackPlanInfo = findViewById(R.id.buttonPlanInfoBack);


        buttonDeletePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position != -1){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(PlanInfoActivity.this);
                    dialog.setMessage("确 定 要 删 除 这 个 计 划 吗?")
                            .setCancelable(false)
                            .setNegativeButton("取 消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("确 定", new DialogInterface.OnClickListener() { //点击确定
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent();
                                    intent.putExtra("back_position", position);
                                    intent.putExtra("back_message", "remove");
                                    intent.putExtra("back_bean", planBean);

                                    finish();
                                }
                            });
                    dialog.show();
                }else {
                    Toast.makeText(PlanInfoActivity.this, "获取任务信息失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBackPlanInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChange = false;
                if(planBean.isFinished()==checkBoxIsFinished.isChecked() || planBean.getInfo().equals(editTextInfo.getText().toString()) || planBean.getTitle().equals(editTextTitle.getText().toString())){
                    isChange = true;
                }
                if(isChange){
                    planBean.setFinished(checkBoxIsFinished.isChecked());
                    planBean.setTitle(editTextTitle.getText().toString());
                    planBean.setInfo(editTextInfo.getText().toString());
                }

                Intent intent = new Intent();
                intent.putExtra("back_position", position);
                intent.putExtra("back_message", "modify");
                intent.putExtra("back_bean", planBean);

                finish();
            }
        });
    }

}
