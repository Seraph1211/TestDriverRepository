package com.example.test2project;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * 第一次启动活动，统一加载本周数据，并将相应按钮的字体颜色设为蓝色
 */
public class LearningReportActivity extends AppCompatActivity implements View.OnClickListener{

    private LineChartView lineChartOfConcentrationTime;  //专注时长折线图
    private LineChartView lineChartOfLearningTime;  //学习时长折线图
    private PieChartView pieChart;  //专注时长与学习时长的对比饼状图

    //切换统计图的6个button
    private Button buttonPieChartWeekly;  //饼状图本周视图按钮
    private Button buttonPieChartMonthly;  //饼状图本月视图按钮
    private Button buttonLineChartConcentrationTimeWeekly;  //折线图专注时长本周视图按钮
    private Button buttonLineChartConcentrationTimeMonthly;  //折线图专注时长本月视图按钮
    private Button buttonLineChartLearningTimeWeekly;  //折线图学习时长本周视图按钮
    private Button buttonLineChartLearningTimeMonthly;  //折线图学习时长本月视图按钮

    /*
   本周专注时长数据
     */
    String[] labelsOfConcentrationTimeWeekly = {"1-1","","1-2","1-3","1-4","1-5","1-6","1-7"};//X轴的标注
    float[] scoresOfConcentrationTimeWeekly = {2.6f, 4.2f, 1.3f, 0, 3.6f, 2.5f, 3.0f};//图表的数据点

    /*
    本月专注时长数据
     */
    String[] labelsOfConcentrationTimeMonthly = {"第1周","","第2周","第3周","第4周"};//X轴的标注
    float[] scoresOfConcentrationTimeMonthly = {7.9f, 9.2f, 2.1f, 4.6f};//图表的数据点

    /*
   本周学习时长数据
     */
    String[] labelsOfLearningTimeWeekly = {"1-1","","1-2","1-3","1-4","1-5","1-6","1-7"};//X轴的标注
    float[] scoresOfLearningTimeWeekly = {6.3f, 5.2f, 4.3f, 1.0f, 7.9f, 6.6f, 5.0f};//图表的数据点

    /*
    本月学习时长数据
     */
    String[] labelsOfLearningTimeMonthly = {"第1周","","第2周","第3周","第4周"};//X轴的标注
    float[] scoresOfLearningTimeMonthly = {7.9f, 9.2f, 2.1f, 4.6f};//图表的数据点

    /*
    饼状图所用数据
     */
    List<SliceValue> sliceValues = new ArrayList<>();
    String[] pieLabels = {"专注时长", "不专注时长"};
    private float totalConcentrationTimeWeekly = 8.6f;  //本周专注总时长
    private float totalConcentrationTimeMonthly = 23.3f;  //本月专注总时长
    private float totalLearningTimeWeekly = 12.2f;  //本周学习总时长
    private float totalLearningTimeMonthly = 36.9f;  //本月学习总时长

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_report);

        init();
    }

    public void init(){
        pieChart = findViewById(R.id.pieChartOfConcentration);
        lineChartOfConcentrationTime = findViewById(R.id.lineChartOfConcentrationTime);
        lineChartOfLearningTime = findViewById(R.id.lineChartOfLearningTime);

        buttonPieChartWeekly = findViewById(R.id.buttonPieChartWeekly);
        buttonPieChartMonthly = findViewById(R.id.buttonPieChartMonthly);
        buttonLineChartConcentrationTimeWeekly = findViewById(R.id.buttonLineChartConcentrationTimeWeekly);
        buttonLineChartConcentrationTimeMonthly = findViewById(R.id.buttonLineChartConcentrationTimeMonthly);
        buttonLineChartLearningTimeWeekly = findViewById(R.id.buttonLineChartLearningTimeWeekly);
        buttonLineChartLearningTimeMonthly = findViewById(R.id.buttonLineChartLearningTimeMonthly);

        buttonPieChartWeekly.setOnClickListener(this);
        buttonPieChartMonthly.setOnClickListener(this);
        buttonLineChartConcentrationTimeWeekly.setOnClickListener(this);
        buttonLineChartConcentrationTimeMonthly.setOnClickListener(this);
        buttonLineChartLearningTimeWeekly.setOnClickListener(this);
        buttonLineChartLearningTimeMonthly.setOnClickListener(this);

        //统一加载本周数据，并将相应按钮的字体颜色设为蓝色
        buttonPieChartWeekly.setTextColor(Color.parseColor("#3F59E2"));
        buttonLineChartConcentrationTimeWeekly.setTextColor(Color.parseColor("#3F59E2"));
        buttonLineChartLearningTimeWeekly.setTextColor(Color.parseColor("#3F59E2"));
        initPieChart(totalConcentrationTimeWeekly, totalLearningTimeWeekly);
        initLineChartOfConcentrationTime(labelsOfConcentrationTimeWeekly, scoresOfConcentrationTimeWeekly);
        initLineChartOfLearningTime(labelsOfLearningTimeWeekly, scoresOfLearningTimeWeekly);
    }

    //初始化饼状图
    public void initPieChart(float concentrationTime, float learningTime){

        float[] pieValues = {concentrationTime, learningTime-concentrationTime};

        //计算百分比
        float sum = 0;
        for(int i=0; i<pieLabels.length; i++){
            sum += pieValues[i];
        }
        NumberFormat numberFormat=NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(1);

        //向sliceValues中填充数据
        for(int i=0; i<pieLabels.length; i++){
            String result=numberFormat.format(((double)pieValues[i])/((double)sum));
            SliceValue sliceValue = new SliceValue(pieValues[i], ChartUtils.pickColor()); //新建对象的同时设置数值和颜色
            sliceValue.setLabel(pieLabels[i]+":"+result); //设置标签
            sliceValues.add(sliceValue);
        }

        PieChartData pieChartData = new PieChartData(sliceValues);
        pieChartData.setHasLabels(true); //显示标签，默认不显示
        pieChartData.setHasLabelsOutside(true); //标签的显示位置在饼状图之外
        pieChartData.setHasCenterCircle(true); //显示圆环状饼状图
        pieChartData.setCenterText1("Comparison"); //设置中心圆中的文本
        pieChartData.setCenterText2("专注度比较");
        pieChartData.setCenterText1FontSize(16);
        pieChartData.setCenterText1Color(Color.parseColor("#4876FF"));
        pieChartData.setCenterText2FontSize(12);
        pieChartData.setCenterCircleScale(0.5f);

        pieChart.setPieChartData(pieChartData);
        pieChart.setVisibility(View.VISIBLE);
        pieChart.startDataAnimation();
        pieChart.setCircleFillRatio(0.8f);   //设置饼状图占整个View的比例
        pieChart.setViewportCalculationEnabled(true);  //饼状图自动适应大小
        pieChart.setInteractive(false );
    }

    //初始化专注时长折线图
    public void initLineChartOfConcentrationTime(String[] labelsOfConcentrationTime, float[] scoresOfConcentrationTime){

       List<PointValue> mPointValuesOfConcentrationTime = new ArrayList<PointValue>();
       List<AxisValue> mAxisXValuesOfConcentrationTime = new ArrayList<AxisValue>();

        /*
        填充数据
         */
        for(int i=0; i<scoresOfConcentrationTime.length; i++){
            mPointValuesOfConcentrationTime.add(new PointValue(i, scoresOfConcentrationTime[i]));
        }
        for(int i=0; i<labelsOfConcentrationTime.length; i++){
            mAxisXValuesOfConcentrationTime.add(new AxisValue(i).setLabel(labelsOfConcentrationTime[i]));
        }

        /*
        对曲线进行相关设置
         */
        Line line = new Line(mPointValuesOfConcentrationTime)
                .setColor(Color.parseColor("#FF6347"))  //设置折线颜色为：浅红色
                .setShape(ValueShape.CIRCLE)  //设置折线统计图上每个数据点的形状（有三种：ValueShape.SQUARE/CIRCLE/DIAMOND)
                .setCubic(false)  //曲线是否平滑，即是曲线还是折线
                .setFilled(true)  //是否填充曲线以下的面积
                .setHasLabels(true)  //曲线的数据点是否加上标签/备注
                //.setHasLabelsOnlyForSelected(true)  //点击数据点是否提示数据（与setHasLabels(true)冲突）
                .setHasLines(true)  //是否用曲线显示。 false:只有点，没有曲线显示
                .setPointRadius(3)
                .setHasPoints(true); //是否显示圆点。 false:没有圆点只有点显示

        List<Line> lines = new ArrayList<>();
        lines.add(line);
        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lines);

        /*
        对X轴进行相关设置
         */
        Axis axisX = new Axis()   //X轴
                .setHasTiltedLabels(false)  //X轴的标签是否倾斜，true-倾斜
                .setTextColor(Color.BLACK)  //字体颜色
                //.setName("Data")    //表格名称
                .setTextSize(10)   //字体大小
                .setMaxLabelChars(8)  //最多显示几个X轴坐标 ？
                .setValues(mAxisXValuesOfConcentrationTime) //X轴标签的填充
                .setHasLines(true);   //X轴是否有分割线
        lineChartData.setAxisXBottom(axisX); //X轴在底部（可以在上、下）

        /*
        对Y轴进行相关设置（Y轴是根据数据的大小自动设置Y轴的上限）
         */
        Axis axisY = new Axis()  //Y轴
                .setName("")  //Y轴标注
                .setTextSize(10); //字体大小
        lineChartData.setAxisYLeft(axisY); //Y轴在左边（可以在左、右）

        /*
        设置折线统计图的行为属性，支持缩放、滑动、平移
         */
        lineChartOfConcentrationTime.setInteractive(true); //可交互
        lineChartOfConcentrationTime.setZoomType(ZoomType.HORIZONTAL) ; //放大方式
        lineChartOfConcentrationTime.setMaxZoom((float)2); //最大放大比例 ？
        lineChartOfConcentrationTime.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChartOfConcentrationTime.setLineChartData(lineChartData);
        lineChartOfConcentrationTime.setVisibility(View.VISIBLE);
    }

    //初始化学习时长折线图
    public void initLineChartOfLearningTime(String[] labelsOfLearningTime, float[] scoresOfLearningTime){

        List<PointValue> mPointValuesOfLearningTime = new ArrayList<PointValue>();
        List<AxisValue> mAxisXValuesOfLearningTime = new ArrayList<AxisValue>();

        /*
        填充数据
         */
        for(int i=0; i<scoresOfLearningTime.length; i++){
            mPointValuesOfLearningTime.add(new PointValue(i, scoresOfLearningTime[i]));
        }
        for(int i=0; i<labelsOfLearningTime.length; i++){
            mAxisXValuesOfLearningTime.add(new AxisValue(i).setLabel(labelsOfLearningTime[i]));
        }

        /*
        对曲线进行相关设置
         */
        Line line = new Line(mPointValuesOfLearningTime)
                .setColor(Color.parseColor("#FF6347"))  //设置折线颜色为：浅红色
                .setShape(ValueShape.CIRCLE)  //设置折线统计图上每个数据点的形状（有三种：ValueShape.SQUARE/CIRCLE/DIAMOND)
                .setCubic(false)  //曲线是否平滑，即是曲线还是折线
                .setFilled(true)  //是否填充曲线以下的面积
                .setHasLabels(true)  //曲线的数据点是否加上标签/备注
                //.setHasLabelsOnlyForSelected(true)  //点击数据点是否提示数据（与setHasLabels(true)冲突）
                .setHasLines(true)  //是否用曲线显示。 false:只有点，没有曲线显示
                .setPointRadius(3)
                .setHasPoints(true); //是否显示圆点。 false:没有圆点只有点显示

        List<Line> lines = new ArrayList<>();
        lines.add(line);
        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lines);

        /*
        对X轴进行相关设置
         */
        Axis axisX = new Axis()   //X轴
                .setHasTiltedLabels(false)  //X轴的标签是否倾斜，true-倾斜
                .setTextColor(Color.BLACK)  //字体颜色
                //.setName("Data")    //表格名称
                .setTextSize(10)   //字体大小
                .setMaxLabelChars(8)  //最多显示几个X轴坐标 ？
                .setValues(mAxisXValuesOfLearningTime) //X轴标签的填充
                .setHasLines(true);   //X轴是否有分割线
        lineChartData.setAxisXBottom(axisX); //X轴在底部（可以在上、下）

        /*
        对Y轴进行相关设置（Y轴是根据数据的大小自动设置Y轴的上限）
         */
        Axis axisY = new Axis()  //Y轴
                .setName("")  //Y轴标注
                .setTextSize(10); //字体大小
        lineChartData.setAxisYLeft(axisY); //Y轴在左边（可以在左、右）

        /*
        设置折线统计图的行为属性，支持缩放、滑动、平移
         */
        lineChartOfLearningTime.setInteractive(true); //可交互
        lineChartOfLearningTime.setZoomType(ZoomType.HORIZONTAL) ; //放大方式
        lineChartOfLearningTime.setMaxZoom((float)2); //最大放大比例 ？
        lineChartOfLearningTime.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChartOfLearningTime.setLineChartData(lineChartData);
        lineChartOfLearningTime.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPieChartWeekly:{
                Toast.makeText(LearningReportActivity.this, "PieChart Weekly !", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.buttonPieChartMonthly:{
                Toast.makeText(LearningReportActivity.this, "PieChart Monthly !", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.buttonLineChartConcentrationTimeWeekly:{
                Toast.makeText(LearningReportActivity.this, "LineChart ConcentrationTime Weekly !", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.buttonLineChartConcentrationTimeMonthly:{
                Toast.makeText(LearningReportActivity.this, "LineChart ConcentrationTime Monthly !", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.buttonLineChartLearningTimeWeekly:{
                Toast.makeText(LearningReportActivity.this, "LineChart LearningTime Weekly !", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.buttonLineChartLearningTimeMonthly:{
                Toast.makeText(LearningReportActivity.this, "LineChart LearningTime Monthly !", Toast.LENGTH_SHORT).show();
                break;
            }



            default:break;
        }
    }
}
