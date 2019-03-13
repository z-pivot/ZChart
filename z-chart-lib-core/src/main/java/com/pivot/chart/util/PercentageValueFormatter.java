/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-4 上午9:52
 * ********************************************************
 */

package com.pivot.chart.util;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * 百分数格式化工具类
 */
public class PercentageValueFormatter implements IValueFormatter,IAxisValueFormatter {

    protected DecimalFormat mFormat;

    public PercentageValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0" + "%");
    }
    
    public PercentageValueFormatter(int i) {
        StringBuilder s = new StringBuilder();
        for(int j=0; j<i; j++){
            if (j == 0) {
                s.append(".");
            }
            s.append("0");
        }
        
        s.append("%");
        mFormat = new DecimalFormat("###,###,###,##0" + s.toString());
    }
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value);
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value);
    }
}
