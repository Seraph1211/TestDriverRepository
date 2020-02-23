package com.example.test2project.plans;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.test2project.R;

import java.util.ArrayList;
import java.util.List;

public class PlanListActivity extends AppCompatActivity implements PlanItemAdapter.CheckedCallBack {
    private static final String TAG = "PlanListActivity";

    private RecyclerView recyclerViewUndonePlans;  //未完成任务列表
    private RecyclerView recyclerViewDonePlans;  //已完成任务列表
    private List<PlanBean> undonePlans = new ArrayList<>();
    private List<PlanBean> donePlans = new ArrayList<>();
    private PlanItemAdapter undoneAdapter;
    private PlanItemAdapter doneAdapter;
    private FloatingActionButton fabAdd;
    private ImageButton buttonBackPlanList;


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
        buttonBackPlanList = findViewById(R.id.buttonPlanListBack);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);

        //recyclerViewDonePlans.setLayoutManager(
          //      new WrapContentLinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        //recyclerViewUndonePlans.setLayoutManager(
          //      new WrapContentLinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerViewDonePlans.setLayoutManager(layoutManager1);
        recyclerViewUndonePlans.setLayoutManager(layoutManager2);

        recyclerViewDonePlans.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUndonePlans.setItemAnimator(new DefaultItemAnimator());

        initList();

        undoneAdapter = new PlanItemAdapter(this, undonePlans);
        doneAdapter = new PlanItemAdapter(this, donePlans);
        recyclerViewUndonePlans.setAdapter(undoneAdapter);
        recyclerViewDonePlans.setAdapter(doneAdapter);

        undoneAdapter.setCallBack(this);
        doneAdapter.setCallBack(this);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlanListActivity.this, "FloatingActionButton !", Toast.LENGTH_SHORT).show();
                addPlan();
            }
        });

        buttonBackPlanList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void initList(){
        for(int i=0; i<5; i++){
            PlanBean bean1 = new PlanBean();
            bean1.setFinished(false);
            bean1.setTitle("言念君子，温其如玉");
            bean1.setInfo("君子坐而论道，少年起而行之");
           undonePlans.add(bean1);
        }
        for (int i=0; i<5; i++){
            PlanBean bean2 = new PlanBean();
            bean2.setFinished(true);
            bean2.setTitle("杨柳依依，草长莺飞");
            bean2.setInfo("人从天上，载得春来。 剑去山下，暑不敢至");
            donePlans.add(bean2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case PlanInfoActivity.PLAN_INFO_ACTIVITY:{  //PlanInfoActivity

                String backMessage = data.getStringExtra("back_message");
                int backPosition = data.getIntExtra("back_position", -1);
                PlanBean backBean = (PlanBean)data.getSerializableExtra("back_bean");

                //Log.d(TAG, "onActivityResult: "+requestCode+"\n"+backMessage+"\n"+backBean.getTitle()+ "\n"
                //+backBean.isFinished());

                if(resultCode == RESULT_OK){
                    if(backBean.isFinished()){  //要操作的任务为已完成任务
                        //Log.d(TAG, "onActivityResult: done");
                        if(backMessage.equals("remove")){  //删除
                            //donePlans.remove(backPosition);
                            doneAdapter.removeItem(backPosition); //不需要再更新donePlans，adapter中的方法会帮助我们自动更新
                            //Log.d(TAG, "onActivityResult: donePlans.size="+donePlans.size());
                        }else if(backMessage.equals("modify")){  //修改
                            //donePlans.set(backPosition, backBean);
                            doneAdapter.setItem(backBean, backPosition);

                        }
                    }else{  //要操作的任务为未完成任务
                        Log.d(TAG, "onActivityResult: undone");
                        if(backMessage.equals("remove")){  //删除
                            //undonePlans.remove(backPosition);
                            undoneAdapter.removeItem(backPosition); //不需要再更新undonePlans，adapter中的方法会帮助我们自动更新
                        }else if(backMessage.equals("modify")){  //修改
                            //undonePlans.set(backPosition, backBean);
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
        //final EditText editInfoAdd = view.findViewById(R.id.editInfoAdd);

        AlertDialog dialog = new AlertDialog.Builder(PlanListActivity.this)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newPlan.setFinished(false);
                        newPlan.setTitle(editTitleAdd.getText().toString());
                        newPlan.setInfo("");

                        //undonePlans.add(newPlan);
                        undoneAdapter.addItem(newPlan); //不需要再更新undonePlans，adapter中的方法会帮助我们自动更新

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


    @Override
    public void beChecked(int position, PlanBean bean) {
        /*
        Log.d(TAG, "beChecked: position="+position);
        Log.d(TAG, "beChecked: undoneSize1="+undonePlans.size());
        undoneAdapter.removeItem(position);
        Log.d(TAG, "beChecked: undoneSize2="+undonePlans.size());
        bean.setFinished(true);
        Log.d(TAG, "beChecked: doneSize1="+donePlans.size());
        doneAdapter.addItem(bean);
        Log.d(TAG, "beChecked: doneSize2="+donePlans.size());
         */

        undonePlans.remove(position);
        undoneAdapter.notifyDataSetChanged();

        bean.setFinished(true);

        donePlans.add(bean);
        doneAdapter.notifyDataSetChanged();
    }

    @Override
    public void unChecked(int position, PlanBean bean) {
        /*
        Log.d(TAG, "unChecked: position="+position);
        Log.d(TAG, "unChecked: doneSize1="+donePlans.size());
        doneAdapter.removeItem(position);
        Log.d(TAG, "unChecked: doneSize2="+donePlans.size());
        bean.setFinished(false);
        Log.d(TAG, "unChecked: undoneSize1="+undonePlans.size());
        undoneAdapter.addItem(bean);
        Log.d(TAG, "unChecked: undoneSize1="+undonePlans.size());
         */
        donePlans.remove(position);
        doneAdapter.notifyDataSetChanged();

        bean.setFinished(false);

        undonePlans.add(bean);
        undoneAdapter.notifyDataSetChanged();
    }
}
