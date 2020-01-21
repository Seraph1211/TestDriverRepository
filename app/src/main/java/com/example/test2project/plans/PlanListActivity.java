package com.example.test2project.plans;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.test2project.R;

import java.util.ArrayList;
import java.util.List;

public class PlanListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUndonePlans;  //未完成任务列表
    private RecyclerView recyclerViewDonePlans;  //已完成任务列表
    private List<PlanBean> undonePlans = new ArrayList<>();
    private List<PlanBean> donePlans = new ArrayList<>();
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

        recyclerViewUndonePlans.setAdapter(new PlanItemAdapter(this, undonePlans));
        recyclerViewDonePlans.setAdapter(new PlanItemAdapter(this, donePlans));

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlanListActivity.this, "FloatingActionButton !", Toast.LENGTH_SHORT).show();
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
}
