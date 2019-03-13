package com.pivot.zchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.pivot.chart.chart.THPieChart;
import com.pivot.chart.entity.BaseChartViewEntity;
import com.pivot.chart.view.BaseChartView;
import com.pivot.chart.view.LegendView;

import java.util.ArrayList;
import java.util.List;

/**
 * 饼图使用示例
 * @author fanjiaming
 */
public class PieDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_demo);

        //初始化数据
        List<Float> listPieValue = new ArrayList<>();
        List<String> labelList = new ArrayList<>();
        List<String> markerText = new ArrayList<>();
        labelList.add("1月");labelList.add("2月");labelList.add("3月");labelList.add("4月");labelList.add("5月");
        listPieValue.add(12f);listPieValue.add(41f);listPieValue.add(76f);listPieValue.add(63f);listPieValue.add(41f);
        for (int i = 0; i < 5; i++) {
            markerText.add((i + 1) + "月销售额：" + listPieValue.get(i));
        }
        
        BaseChartView baseChartView = findViewById(R.id.pie_chart_view);
        List<BaseChartViewEntity> chartViewEntities = new ArrayList<>();
        BaseChartViewEntity chartEntity = new BaseChartViewEntity();
        chartEntity.chart = THPieChart.instance(new PieChart(getBaseContext()))
                .setListPieValue(listPieValue, null)//设置饼图数据 必要
                .setCircleLabelList(labelList)//设置条目及图例名称列表 必要
                .setRotation(true)//设置是否可旋转 非必要
                .setShowValues(true)//设置是否显示数值 非必要
                .setShowLabels(true)//设置是否显示条目描述 非必要
                .setCustomText(true)//设置是否使用自定义弹出框文本 非必要
                .setMarkerText(markerText)//设置自定义弹出框文本集合，isCustomText为true该属性才生效
                .setLegendDirection(LegendView.VERTICAL_RIGHT)//设置图例位置 非必要
                .setLegendOffset(0, 130, 0, 0)//设置图例列表偏移 非必要
                .setExtraOffset(0, 0, 0, 0)//设置饼图偏移 非必要
                .setCenterText("饼图示例")//设置中心文本 非必要
                .setItemLegendListener(new THPieChart.LegendOnClickListener() {//设置图例点击响应 非必要
                    @Override
                    public void itemOnClick(int i) {
                        Toast.makeText(getBaseContext(), markerText.get(i), Toast.LENGTH_LONG).show();
                    }
                })
                .effect();//最后调用此方法使所有设置生效 必要
        
        chartViewEntities.add(chartEntity);
        baseChartView.clear();
        baseChartView.initData(chartViewEntities);
    }
}
