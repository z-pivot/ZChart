package com.pivot.zchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pivot.chart.chart.THArcLineChart;
import com.pivot.chart.entity.BaseChartViewEntity;
import com.pivot.chart.entity.ChartValueItemEntity;
import com.pivot.chart.view.BaseChartView;

import org.xclcharts.chart.ArcLineChart;

import java.util.ArrayList;
import java.util.List;

public class ArcLineDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_line_demo);
        
        //初始化弧线比较图数据
        List<ChartValueItemEntity> listArcLineValue = new ArrayList<>();
        listArcLineValue.add(new ChartValueItemEntity(75f, 0, "文体"));
        listArcLineValue.add(new ChartValueItemEntity(45f, 0, "家具"));
        listArcLineValue.add(new ChartValueItemEntity(57f, 0, "电器"));
        listArcLineValue.add(new ChartValueItemEntity(52f, 0, "食品"));
        listArcLineValue.add(new ChartValueItemEntity(69f, 0, "服饰"));
        
        BaseChartView baseChartView = findViewById(R.id.arc_chart_view);
        List<BaseChartViewEntity> chartViewEntities = new ArrayList<>();
        BaseChartViewEntity chartEntity = new BaseChartViewEntity();
        chartEntity.chart = THArcLineChart.instance(getBaseContext(), new ArcLineChart())
                .setListArcLineValue(listArcLineValue)
                .setCenterText("月销售额(万元)")
                .setInnerRaius(0.5f)
                .setLegendRowSpan(10f)
                .effect();
        
        chartViewEntities.add(chartEntity);
        baseChartView.clear();
        baseChartView.initData(chartViewEntities);
    }
}
