package com.example.test2project.plans;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.util.Log;
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
 * onDestroy中判断是否有修改
 * 如果用户先修改了内容，再删除，则在删除按钮的点击事件中设置boolean值，再在onDestroy中处理
 */

public class PlanInfoActivity extends AppCompatActivity {
    private static final String TAG = "PlanInfoActivity";

    public static final int PLAN_INFO_ACTIVITY = 1;
    private ImageButton buttonDeletePlan;
    private ImageButton buttonBackPlanInfo;
    private CheckBox checkBoxIsFinished;
    private EditText editTextTitle;
    private EditText editTextInfo;
    private PlanBean planBean;

    private boolean isRemoved = false;

    private Intent backIntent;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_info);



        init();



    }

    public void init(){
        planBean = (PlanBean)getIntent().getSerializableExtra("itemInfo");
        position = getIntent().getIntExtra("position", -10);
        backIntent = new Intent();
        backIntent.putExtra("back_position", position);

        buttonDeletePlan = findViewById(R.id.buttonDeletePlan);
        //checkBoxIsFinished = findViewById(R.id.checkBoxOfPlanInfo);
        editTextTitle = findViewById(R.id.editTextTitleOfPlanInfo);
        editTextInfo = findViewById(R.id.editTextPlanInfo);
        buttonBackPlanInfo = findViewById(R.id.buttonPlanInfoBack);

        //checkBoxIsFinished.setChecked(planBean.isFinished());
        editTextTitle.setText(planBean.getTitle());
        editTextInfo.setText(planBean.getInfo());


        buttonDeletePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position != -10){
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
                                    isRemoved = true;
                                    backIntent.putExtra("back_message", "remove");
                                    backIntent.putExtra("back_bean", planBean);
                                    setResult(RESULT_OK, backIntent);
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
                if(isModified()){  //如果用户修改了任务信息
                    //planBean.setFinished(checkBoxIsFinished.isChecked());
                    planBean.setTitle(editTextTitle.getText().toString());
                    planBean.setInfo(editTextInfo.getText().toString());
                    backIntent.putExtra("back_message", "modify");
                }else {
                    backIntent.putExtra("back_message", "no_change");
                }
                backIntent.putExtra("back_bean", planBean);
                setResult(RESULT_OK, backIntent);
                finish();
            }
        });


    }

    /**
     * 判断用户是否修改了任务信息
     * @return
     */
    public boolean isModified(){
        String titleNow = editTextTitle.getText().toString();
        String titlePrevious = planBean.getTitle();
        String infoNow = editTextInfo.getText().toString();
        String infoPrevious = planBean.getInfo();

        if(!titleNow.equals(titlePrevious) || !infoNow.equals(infoPrevious)){
            return true;
        }
        return false;
    }

    /**
     * 监听Back键按下事件,方法1:
     * super.onBackPressed()会自动调用finish()方法执行 onDestroy(),关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     *
     * 通过返回键返回上一个活动，intent传不过去，该问题留到以后解决，暂时禁用返回键
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.d(TAG, "onBackPressed: ");

        if(isModified()){  //如果用户修改了任务信息
            //planBean.setFinished(checkBoxIsFinished.isChecked());
            planBean.setTitle(editTextTitle.getText().toString());
            planBean.setInfo(editTextInfo.getText().toString());
            backIntent.putExtra("back_message", "modify");
        }else {
            backIntent.putExtra("back_message", "no_change");
        }
        backIntent.putExtra("back_bean", planBean);
        setResult(RESULT_OK, backIntent);
        finish();
    }
}
