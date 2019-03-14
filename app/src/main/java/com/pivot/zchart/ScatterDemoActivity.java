package com.pivot.zchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.pivot.chart.chart.THScatterChart;
import com.pivot.chart.entity.BaseChartViewEntity;
import com.pivot.chart.entity.ChartValueItemEntity;
import com.pivot.chart.view.BaseChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * 散点图使用示例
 * @author fanjiaming
 */
public class ScatterDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatter_demo);
        
        //初始化数据
        List<ChartValueItemEntity> datas = new ArrayList<>();
        List<String> xLabelList = new ArrayList<>();
        List<Float> listValue1 = new ArrayList<>();
        List<Float> listValue2 = new ArrayList<>();
        List<Float> listValue3 = new ArrayList<>();
        xLabelList.add("1月");xLabelList.add("2月");xLabelList.add("3月");xLabelList.add("4月");xLabelList.add("5月");
        listValue1.add(12f);listValue1.add(41f);listValue1.add(76f);listValue1.add(63f);listValue1.add(41f);
        listValue2.add(62f);listValue2.add(91f);listValue2.add(44f);listValue2.add(13f);listValue2.add(72f);
        listValue3.add(76f);listValue3.add(15f);listValue3.add(16f);listValue3.add(98f);listValue3.add(11f);
        datas.add(new ChartValueItemEntity(listValue1, new DefaultValueFormatter(1), Color.BLACK, 12, true, "条目1", ScatterChart.ScatterShape.TRIANGLE));
        datas.add(new ChartValueItemEntity(listValue2, new DefaultValueFormatter(1), Color.BLUE, 12, true, "条目2", ScatterChart.ScatterShape.CIRCLE));
        datas.add(new ChartValueItemEntity(listValue3, new DefaultValueFormatter(1), Color.RED, 12, true, "条目3", ScatterChart.ScatterShape.SQUARE));
        
        BaseChartView baseChartView = findViewById(R.id.scatter_chart_view);
        List<BaseChartViewEntity> chartViewEntities = new ArrayList<>();
        BaseChartViewEntity chartEntity = new BaseChartViewEntity();
        chartEntity.chart = THScatterChart.instance(new ScatterChart(getBaseContext()))
                .setListScatterValue(datas)//设置散点图数据 必要
                .setListLabel(xLabelList)//设置x轴标签集合 必要
                .setTextColor(Color.BLACK)//设置x、y轴以及图例文本字体颜色 必要
                .setShowGridLine(true)
                .effect();
        
        chartViewEntities.add(chartEntity);
        baseChartView.clear();
        baseChartView.initData(chartViewEntities);
    }
}
