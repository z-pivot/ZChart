package com.pivot.zchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pivot.chart.chart.THRoseChart;
import com.pivot.chart.entity.BaseChartViewEntity;
import com.pivot.chart.entity.ChartValueItemEntity;
import com.pivot.chart.view.BaseChartView;

import org.xclcharts.chart.RoseChart;

import java.util.ArrayList;
import java.util.List;

/**
 * 南丁格尔玫瑰图使用示例
 */
public class RoseDemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rose_demo);

        List<ChartValueItemEntity> items = new ArrayList<>();
        items.add(new ChartValueItemEntity(450, 0, "PostgreSQL"));
        items.add(new ChartValueItemEntity(230, 0, "SyBase"));
        items.add(new ChartValueItemEntity(630, 0, "MySQL"));
        items.add(new ChartValueItemEntity(320, 0, "PLSQL"));
        items.add(new ChartValueItemEntity(710, 0, "Oracle"));
        items.add(new ChartValueItemEntity(270, 0, "DB2"));
        items.add(new ChartValueItemEntity(750, 0, "SQL Server"));
        items.add(new ChartValueItemEntity(420, 0, "SQLite"));

        BaseChartView baseChartView = findViewById(R.id.rose_chart_view);
        List<BaseChartViewEntity> chartViewEntities = new ArrayList<>();
        BaseChartViewEntity chartEntity = new BaseChartViewEntity();
        chartEntity.chart = THRoseChart.instance(getBaseContext(), new RoseChart())
                .setListRoseValue(items)
                .setItemMaxValue(800)
                .setIntervalAngle(5)
                .setTitle("主流数据库语言及其使用人数（万人）")
                .effect();

        chartViewEntities.add(chartEntity);
        baseChartView.clear();
        baseChartView.initData(chartViewEntities);
    }
}
