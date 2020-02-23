package com.example.test2project.plans;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test2project.R;

import java.util.List;

public class PlanItemAdapter extends RecyclerView.Adapter<PlanItemAdapter.PlanItemViewHolder> {
    private static final String TAG  = "PlanItemAdapter";
    private List<PlanBean> planBeanList;
    private Context context;

    private RecyclerView mRecyclerView;

    private CheckedCallBack callBack;

    interface CheckedCallBack{
        void beChecked(int position, PlanBean bean);
        void unChecked(int position, PlanBean bean);
    }

    public CheckedCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CheckedCallBack callBack) {
        this.callBack = callBack;
    }

    public PlanItemAdapter(Context context, List<PlanBean> planBeanList){
        this.context = context;
        this.planBeanList = planBeanList;
    }

    static class PlanItemViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBoxIsFinished;
        TextView textPlanTitle;

        public PlanItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            checkBoxIsFinished = itemView.findViewById(R.id.checkboxIsFinished);
            textPlanTitle = itemView.findViewById(R.id.textPlanTitle);
        }
    }
    @NonNull
    @Override
    public PlanItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plan, viewGroup, false);
        final PlanItemViewHolder myViewHolder = new PlanItemViewHolder(view, context);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlanItemViewHolder planItemViewHolder, int i) {
        final PlanBean bean = planBeanList.get(planItemViewHolder.getAdapterPosition());
        planItemViewHolder.checkBoxIsFinished.setChecked(bean.isFinished());
        planItemViewHolder.textPlanTitle.setText(bean.getTitle());

        /**
         * 为标题注册点击事件
         */
        planItemViewHolder.textPlanTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = planItemViewHolder.getAdapterPosition();
                PlanBean bean = planBeanList.get(position);
                Toast.makeText(context, bean.getTitle()+position, Toast.LENGTH_SHORT).show();
                startPlanInfoActivity(bean, position);
            }
        });


        /**
         * 为checkBox注册点击事件
         * 为避免出现 java.lang.IllegalStateException: Cannot call this method while RecyclerView is Computing a layout or scrolling 错误
         * 需要先判断recyclerView是否在Computing a layout or scrolling
         */
        planItemViewHolder.checkBoxIsFinished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mRecyclerView!=null && !mRecyclerView.isComputingLayout()){  //判断Computing a layout or scrolling
                    if(isChecked){  //如果被选中
                        callBack.beChecked(planItemViewHolder.getAdapterPosition(), bean);
                    }else {
                        callBack.unChecked(planItemViewHolder.getAdapterPosition(),bean);
                    }
                }
            }
        });

        if(bean.isFinished()){
            planItemViewHolder.textPlanTitle.setTextColor(Color.parseColor("#C7C7C7"));
        }
    }

    @Override
    public int getItemCount() {
        return planBeanList.size();
    }

    public void startPlanInfoActivity(PlanBean planBean, int position){
        Log.d(TAG, "startPlanInfoActivity: "+planBean);
        Intent intent = new Intent(context, PlanInfoActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("itemInfo", planBean);

        Activity activity = (AppCompatActivity)context;

        activity.startActivityForResult(intent, PlanInfoActivity.PLAN_INFO_ACTIVITY);
    }

    public void setItem(PlanBean planBean, int position){
        planBeanList.set(position, planBean);
        notifyDataSetChanged();
    }

    //在指定位置添加item
    public void addItem(PlanBean planBean){
        planBeanList.add(planBean);
        notifyDataSetChanged();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    //删除指定位置的item
    public void removeItem(int position){
        planBeanList.remove(position); //删除数据源，移除集合中下标为position的数据

        //notifyItemRemoved(position);  //刷新被删除的地方
        //notifyItemRangeChanged(position, getItemCount()); //刷新被删除的数据，以及后面的数据


        if(!mRecyclerView.isComputingLayout()){
            notifyDataSetChanged();
        }
    }

}

