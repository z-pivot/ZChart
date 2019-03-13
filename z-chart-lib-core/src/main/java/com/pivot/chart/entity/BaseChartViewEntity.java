/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-4 上午9:40
 * ********************************************************
 */
package com.pivot.chart.entity;

import android.view.View;

/**
 * BaseChartView的实体类
 */
public class BaseChartViewEntity {
    public String title;//标题
    public int titleTextSize;//标题字体大小
    public int titleTextColor;//标题字体颜色
    public String subTitle = "";//副标题
    public View chart;//每个table对应的chart图表
}
