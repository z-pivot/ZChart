package com.pivot.zchart;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.pivot.chart.chart.THBarChart;
import com.pivot.chart.chart.THHorizontalBarChart;
import com.pivot.chart.entity.BaseChartViewEntity;
import com.pivot.chart.entity.ChartValueItemEntity;
import com.pivot.chart.view.BaseChartView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 柱状图使用示例
 * @author fanjiaming
 */

public class BarDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_demo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏 
        
        //一般柱状图数据初始化
        List<ChartValueItemEntity> datas = new ArrayList<>();
        List<String> xLabelList = new ArrayList<>();//x轴标签集合
        List<Float> listValue1 = new ArrayList<>();
        List<Float> listValue2 = new ArrayList<>();
        List<Float> listValue3 = new ArrayList<>();
        xLabelList.add("1月");xLabelList.add("2月");xLabelList.add("3月");xLabelList.add("4月");xLabelList.add("5月");
        listValue1.add(12f);listValue1.add(41f);listValue1.add(76f);listValue1.add(63f);listValue1.add(41f);
        listValue2.add(62f);listValue2.add(91f);listValue2.add(44f);listValue2.add(13f);listValue2.add(72f);
        listValue3.add(76f);listValue3.add(15f);listValue3.add(16f);listValue3.add(98f);listValue3.add(11f);
        datas.add(new ChartValueItemEntity(listValue1, new DefaultValueFormatter(1), 0, 12, "条目1", false));
        datas.add(new ChartValueItemEntity(listValue2, new DefaultValueFormatter(1), 0, 12, "条目2", false));
        datas.add(new ChartValueItemEntity(listValue3, new DefaultValueFormatter(1), 0, 12, "条目3", false));
        
        //堆叠柱状图数据初始化
        List<List<Float>> listStackBarValue = new ArrayList<>();
        List<Integer> listStackColor = Arrays.asList(Color.parseColor("#b34DABF5"), Color.parseColor("#b3FCE033"), Color.parseColor("#b391D833"), Color.parseColor("#b338a483"), Color.parseColor("#b30f68a7"));
        String[] listStackLegendText = new String[]{"柱1", "柱2", "柱3", "柱4", "柱5"};
        listStackBarValue.add(listValue1);listStackBarValue.add(listValue2);listStackBarValue.add(listValue3);
        
        //横向柱状图数据初始化
        List<ChartValueItemEntity> horizontalDatas = new ArrayList<>();
        horizontalDatas.add(new ChartValueItemEntity(listValue1, new DefaultValueFormatter(1), 0, 12, "条目1", false));
        
        BaseChartView baseChartView = findViewById(R.id.bar_chart_view);
        List<BaseChartViewEntity> chartViewEntities = new ArrayList<>();
        BaseChartViewEntity chartEntity1 = new BaseChartViewEntity();
        BaseChartViewEntity chartEntity2 = new BaseChartViewEntity();
        BaseChartViewEntity chartEntity3 = new BaseChartViewEntity();
        chartEntity1.chart = THBarChart.instance(new BarChart(getBaseContext()))
                .setListBarValue(datas)//设置柱图数据 必要
                .setListLabel(xLabelList)//设置x轴坐标文本 必要
                .setTextColor(Color.BLACK)//设置x、y轴以及图例文本字体颜色 必要
                .setStartAndSpace(-0.46f, 0.36f, 0.06f)//设置x轴起始位置、不同柱组间隔、每组中不同柱条目间隔 非必要(有默认值)
                .setPercentageValue(false)//设置是否用百分数显示
                //其它的非必要属性请参照线图使用示例
                .effect();//最后调用此方法使所有设置生效 必要
        chartEntity1.title = "";
        chartEntity1.subTitle = "非堆叠柱状图";
        
        chartEntity2.chart = THBarChart.instance(new BarChart(getBaseContext()))
                .addListValue(listStackBarValue, listStackColor, false, null, 12)//设置堆叠柱图数据 必要
                .setStackBarLabel(listStackLegendText)//设置堆叠图图例文本 必要
                .setListLabel(Arrays.asList("1月", "2月", "3月"))//设置x轴坐标文本 必要
                .setTextColor(Color.BLACK)//设置x、y轴以及图例文本字体颜色 必要
                .effect();//最后调用此方法使所有设置生效 必要
        chartEntity2.title = "";
        chartEntity2.subTitle = "堆叠柱状图";

        chartEntity3.chart = THHorizontalBarChart.instance(new HorizontalBarChart(getBaseContext()))
                .setListBarValue(horizontalDatas)//设置柱图数据 必要
                .setListLabel(xLabelList)//设置x轴坐标文本 必要
                .setTextColor(Color.BLACK)//设置x、y轴以及图例文本字体颜色 必要
                .effect();//最后调用此方法使所有设置生效 必要
        chartEntity3.title = "";
        chartEntity3.subTitle = "横向柱状图";
        
        chartViewEntities.add(chartEntity1);
        chartViewEntities.add(chartEntity2);
        chartViewEntities.add(chartEntity3);
        baseChartView.clear();
        baseChartView.initData(chartViewEntities);
    }
}
