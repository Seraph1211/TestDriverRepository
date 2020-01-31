package com.example.test2project.plans;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test2project.R;

import java.util.ArrayList;
import java.util.List;

public class PlanListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUndonePlans;  //未完成任务列表
    private RecyclerView recyclerViewDonePlans;  //已完成任务列表
    private List<PlanBean> undonePlans = new ArrayList<>();
    private List<PlanBean> donePlans = new ArrayList<>();
    private PlanItemAdapter undoneAdapter;
    private PlanItemAdapter doneAdapter;
    private FloatingActionButton fabAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);

        init();
    }

    public void init(){
        recyclerViewDonePlans = findViewById(R.id.recyclerViewOfDonePlanList);
        recyclerViewUndonePlans = findViewById(R.id.recyclerViewOfUndonePlanList);
        fabAdd = findViewById(R.id.fabAddPlan);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        recyclerViewDonePlans.setLayoutManager(layoutManager1);
        recyclerViewUndonePlans.setLayoutManager(layoutManager2);

        initList();

        undoneAdapter = new PlanItemAdapter(this, undonePlans);
        doneAdapter = new PlanItemAdapter(this, donePlans);
        recyclerViewUndonePlans.setAdapter(undoneAdapter);
        recyclerViewDonePlans.setAdapter(doneAdapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlanListActivity.this, "FloatingActionButton !", Toast.LENGTH_SHORT).show();
                addPlan();
            }
        });

    }

    public void initList(){
        for(int i=0; i<5; i++){
            PlanBean bean1 = new PlanBean();
            bean1.setFinished(false);
            bean1.setTitle("言念君子，温其如玉");
            bean1.setInfo("吃葡萄不吐葡萄皮，不吃葡萄倒吐葡萄皮");
           undonePlans.add(bean1);
        }
        for (int i=0; i<5; i++){
            PlanBean bean2 = new PlanBean();
            bean2.setFinished(true);
            bean2.setTitle("大力出奇迹");
            bean2.setInfo("人从天上，载得春来。 剑去山下，暑不敢至");
            donePlans.add(bean2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case PlanInfoActivity.plan_info_activity:{  //PlanInfoActivity

                String backMessage = data.getStringExtra("back_message");
                int backPosition = data.getIntExtra("back_position", -1);
                PlanBean backBean = (PlanBean)data.getSerializableExtra("back_bean");

                if(requestCode == RESULT_OK){
                    if(backBean.isFinished()){  //要操作的任务为已完成任务
                        if(backMessage.equals("remove")){  //删除
                            donePlans.remove(backPosition);
                            doneAdapter.removeItem(backPosition);
                        }else if(backMessage.equals("modify")){  //修改
                            donePlans.set(backPosition, backBean);
                            doneAdapter.setItem(backBean, backPosition);
                        }
                    }else{  //要操作的任务为未完成任务
                        if(backMessage.equals("remove")){  //删除
                            undonePlans.remove(backPosition);
                            undoneAdapter.removeItem(backPosition);
                        }else if(backMessage.equals("modify")){  //修改
                            undonePlans.set(backPosition, backBean);
                            undoneAdapter.setItem(backBean, backPosition);
                        }
                    }
                }
                break;
            }
        }
    }

    public void addPlan(){
        final PlanBean newPlan = new PlanBean();

        View view = getLayoutInflater().inflate(R.layout.dialog_add_plan, null);
        final EditText editTitleAdd = view.findViewById(R.id.editTitleAdd);
        final EditText editInfoAdd = view.findViewById(R.id.editInfoAdd);

        AlertDialog dialog = new AlertDialog.Builder(PlanListActivity.this)
                .setTitle("添加事件")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newPlan.setFinished(false);
                        newPlan.setTitle(editTitleAdd.getText().toString());
                        newPlan.setInfo(editInfoAdd.getText().toString());

                        undonePlans.add(newPlan);
                        undoneAdapter.addItem(newPlan);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();

    }


    public RecyclerView getRecyclerViewUndonePlans() {
        return recyclerViewUndonePlans;
    }

    public void setRecyclerViewUndonePlans(RecyclerView recyclerViewUndonePlans) {
        this.recyclerViewUndonePlans = recyclerViewUndonePlans;
    }

    public RecyclerView getRecyclerViewDonePlans() {
        return recyclerViewDonePlans;
    }

    public void setRecyclerViewDonePlans(RecyclerView recyclerViewDonePlans) {
        this.recyclerViewDonePlans = recyclerViewDonePlans;
    }

    public List<PlanBean> getUndonePlans() {
        return undonePlans;
    }

    public void setUndonePlans(List<PlanBean> undonePlans) {
        this.undonePlans = undonePlans;
    }

    public List<PlanBean> getDonePlans() {
        return donePlans;
    }

    public void setDonePlans(List<PlanBean> donePlans) {
        this.donePlans = donePlans;
    }

    public FloatingActionButton getFabAdd() {
        return fabAdd;
    }

    public void setFabAdd(FloatingActionButton fabAdd) {
        this.fabAdd = fabAdd;
    }
}
