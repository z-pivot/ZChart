/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-7 下午1:42
 * ********************************************************
 */

package com.pivot.chart.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.pivot.chart.adapter.LegendViewAdapter;
import com.pivot.chart.entity.LegendEntity;
import com.pivot.z_chart_lib_core.R;

import java.util.List;

/**
 * 饼状图图例自定义
 */

public class LegendView extends LinearLayout {
    /**
     * 图例方向
     */
    public static final int HORIZONTAL_TOP = 0; //横向头部
    public static final int HORIZONTAL_BOTTOM = 1;//横向底部
    public static final int VERTICAL_LEFT = 2;//纵向左侧
    public static final int VERTICAL_RIGHT = 3;//纵向右侧

    private int direction;//方向
    private int leftOffset = 10;//图例列表左侧间距
    private int topOffset = 120;//图例列表上侧间距
    private int rightOffset = 10;//图例列表右侧间距
    private int bottomOffset = 120;//图例列表下侧间距
    private Context context;
    private View chart;
    private LegendViewAdapter adapter;
    private RecyclerView recyclerView;

    public LegendView(Context context, int direction, View chart) {
        super(context, null);
        this.direction = direction;
        this.context = context;
        this.chart = chart;
    }

    private void initView() {
        setOrientation(VERTICAL);
        setBackgroundResource(R.color.transparent);
        RelativeLayout rootView = new RelativeLayout(getContext());
        recyclerView = new RecyclerView(getContext());
        recyclerView.setId(Integer.MAX_VALUE - 1000);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RelativeLayout.LayoutParams chartLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (direction == HORIZONTAL_TOP || direction == HORIZONTAL_BOTTOM) {
            recyclerView.setHorizontalScrollBarEnabled(false);
            recyclerView.setVerticalScrollBarEnabled(true);
            recyclerView.setFocusable(true);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(leftOffset,topOffset,rightOffset,bottomOffset);
            if (direction == HORIZONTAL_TOP) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                chartLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, recyclerView.getId());
            } else {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                chartLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, recyclerView.getId());
            }
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setLayoutParams(layoutParams);
        } else {
            recyclerView.setHorizontalScrollBarEnabled(true);
            recyclerView.setVerticalScrollBarEnabled(false);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(leftOffset,topOffset,rightOffset,bottomOffset);
            if (direction == VERTICAL_LEFT) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                chartLayoutParams.addRule(RelativeLayout.RIGHT_OF, recyclerView.getId());
            } else {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                chartLayoutParams.addRule(RelativeLayout.LEFT_OF, recyclerView.getId());
            }
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setLayoutParams(layoutParams);
        }
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                rv.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        chart.setLayoutParams(chartLayoutParams);
        rootView.addView(chart);
        rootView.addView(recyclerView);
        addView(rootView);
    }

    public void initData(List<LegendEntity> legendEntity) {
        initView();
        adapter = new LegendViewAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setDatas(legendEntity);
    }
    
    public void setLegendOffset(int leftOffset, int topOffset, int rightOffset, int bottomOffset) {
        this.leftOffset = leftOffset;
        this.topOffset = topOffset;
        this.rightOffset = rightOffset;
        this.bottomOffset = bottomOffset;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
