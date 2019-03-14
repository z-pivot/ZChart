/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-4 上午9:35
 * ********************************************************
 */

package com.pivot.chart.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pivot.chart.entity.BaseChartViewEntity;
import com.pivot.z_chart_lib_core.R;
import com.zcolin.frame.util.DisplayUtil;

import java.util.List;

/**
 * 图表容器
 * 适用于多张图表 左侧table 右侧图表（柱状、折线、组合等）
 * 也适用单张图表
 * @author fanjiaming
 */

public class BaseChartView extends LinearLayout {
    private Context context;
    private TextView tvTile;
    private LinearLayout llSubTitle;
    private FrameLayout flContent;
    private ScrollView llSubTitleScroll;

    public BaseChartView(Context context) {
        this(context, null);
    }

    public BaseChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(VERTICAL);
        tvTile = new TextView(getContext());
        tvTile.setGravity(Gravity.CENTER);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        addView(tvTile, layoutParams);
        View rootView = LayoutInflater.from(context).inflate(R.layout.view_table_chart, null);
        llSubTitle = rootView.findViewById(R.id.ll_subtitle);
        flContent = rootView.findViewById(R.id.fl_content);
        llSubTitleScroll = rootView.findViewById(R.id.ll_subtitle_scroll);
        this.addView(rootView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void initData(List<BaseChartViewEntity> listData) {
        if (listData != null && listData.size() > 0) {
            setTitle(listData.get(0));
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(getContext(), 40));
            LayoutParams chartParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            for (int i = 0; i < listData.size(); i++) {
                llSubTitle.addView(createSubTitleTextView(listData.get(i)), params);
                flContent.addView(createChartView(listData.get(i)), chartParams);
            }
            llSubTitle.getChildAt(0).setSelected(true);
            flContent.getChildAt(0).setVisibility(VISIBLE);
            if (listData.size() == 1) {
                llSubTitleScroll.setVisibility(GONE);
            }
        }
    }

    private FrameLayout createChartView(BaseChartViewEntity info) {
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setVisibility(GONE);
        frameLayout.addView(info.chart, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return frameLayout;
    }

    private TextView createSubTitleTextView(BaseChartViewEntity info) {
        TextView tvSubTitle = new TextView(getContext());
        tvSubTitle.setBackgroundResource(R.drawable.btn_chart_select_selector);
        tvSubTitle.setGravity(Gravity.CENTER_VERTICAL);
        tvSubTitle.setPadding((int) getResources().getDimension(R.dimen.dimens_mid), 0, 0, 0);
        tvSubTitle.setText(info.subTitle);
        tvSubTitle.setTextSize(12);
        Drawable arrow = getResources().getDrawable(R.drawable.btn_chart_arrow_selector);
        arrow.setBounds(0, 0, arrow.getMinimumWidth(), arrow.getMinimumHeight());
        tvSubTitle.setCompoundDrawables(null, null, arrow, null);
        tvSubTitle.setTextColor(getResources().getColorStateList(R.color.btn_chart_textcolor_selector));
        tvSubTitle.setOnClickListener(v -> {
            setTitle(info);
            int count = llSubTitle.getChildCount();
            for (int j = 0; j < count; j++) {
                llSubTitle.getChildAt(j).setSelected(llSubTitle.getChildAt(j) == v);
                if (llSubTitle.getChildAt(j) == v) {
                    llSubTitle.getChildAt(j).setSelected(true);
                    flContent.getChildAt(j).setVisibility(VISIBLE);
                } else {
                    llSubTitle.getChildAt(j).setSelected(false);
                    flContent.getChildAt(j).setVisibility(GONE);
                }
            }
        });
        if (info.subTitle.isEmpty()) {
            llSubTitle.setVisibility(GONE);
        }
        return tvSubTitle;
    }

    public void setTitle(BaseChartViewEntity info) {
        if (info.title == null) {
            tvTile.setVisibility(GONE);
        } else {
            if (info.titleTextColor != 0) {
                tvTile.setTextColor(info.titleTextColor);
            }
            if (info.titleTextSize > 0) {
                tvTile.setTextSize(TypedValue.COMPLEX_UNIT_DIP, info.titleTextSize);
            }
            int padding = (int) getResources().getDimension(R.dimen.dimens_big);
            tvTile.setPadding(padding, 0, padding, 0);
            tvTile.setText(info.title);
        }
    }

    public void setLlSubTitleWidth(int llSubTitleWidth) {
        llSubTitleScroll.getLayoutParams().width = DisplayUtil.dip2px(context, llSubTitleWidth);
    }

    public void setLlSubTitleHeight(int llSubTitleHeight) {
        llSubTitleScroll.getLayoutParams().height = DisplayUtil.dip2px(context, llSubTitleHeight);
    }

    public void clear() {
        tvTile.setText("");
        llSubTitle.removeAllViews();
        flContent.removeAllViews();
    }
}
