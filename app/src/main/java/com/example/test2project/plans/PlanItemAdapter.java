package com.example.test2project.plans;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test2project.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PlanItemAdapter extends RecyclerView.Adapter<PlanItemAdapter.PlanItemViewHolder> {
    private static final String TAG  = "PlanItemAdapter";
    private List<PlanBean> planBeanList;
    private Context context;


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
        PlanBean bean = planBeanList.get(planItemViewHolder.getAdapterPosition());
        planItemViewHolder.checkBoxIsFinished.setChecked(bean.isFinished());
        planItemViewHolder.textPlanTitle.setText(bean.getTitle());

        planItemViewHolder.textPlanTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = planItemViewHolder.getAdapterPosition();
                PlanBean bean = planBeanList.get(position);
                Toast.makeText(context, bean.getTitle()+position, Toast.LENGTH_SHORT).show();
                startPlanInfoActivity(bean);
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

    public void startPlanInfoActivity(PlanBean planBean){
        Log.d(TAG, "startPlanInfoActivity: "+planBean);
        Intent intent = new Intent(context, PlanInfoActivity.class);
        intent.putExtra("itemInfo", planBean);
        context.startActivity(intent);
    }
}
