/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-7 下午1:42
 * ********************************************************
 */

package com.pivot.chart.adapter;

import android.widget.TextView;

import com.pivot.chart.entity.LegendEntity;
import com.pivot.z_chart_lib_core.R;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;

/**
 * 带可滑动图例的PieChart的滑动图例adapter
 */

public class LegendViewAdapter extends BaseRecyclerAdapter<LegendEntity> {
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.piechart_legend_recycler_item;
    }

    @Override
    public void setUpData(CommonHolder holder, int position, int viewType, LegendEntity data) {
        TextView tvColor = getView(holder, R.id.tv_color);
        TextView tvName = getView(holder, R.id.tv_name);
        tvName.setTextColor(data.textColor);
        tvName.setTextSize(data.textSize);
        tvColor.setBackgroundColor(data.color);
        tvName.setClickable(true);
        tvName.setOnClickListener(data.onClickListener);
        tvName.setText(data.legend);
    }
}
