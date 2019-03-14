/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-4 上午10:51
 * ********************************************************
 */

package com.pivot.chart.chart;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.pivot.chart.entity.LegendEntity;
import com.pivot.chart.entity.RadarViewEntity;
import com.pivot.chart.view.LegendView;
import com.pivot.chart.view.RadarView;
import com.pivot.z_chart_lib_core.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 雷达图封装类
 * 雷达图不是引自于MPChart库中的
 */
public class THRadarChart {
    private RadarView mRadarView;
    
    private int vertexTextSize = 25;//顶点描述字大小
    private int vertexTextColor;//顶点描述字颜色
    private int legendDirection = LegendView.HORIZONTAL_BOTTOM;//图例位置
    private int legendTextColor = Color.BLACK;//图例文本字体颜色
    private int leftOffset;//图例列表左侧间距
    private int topOffset;//图例列表上侧间距
    private int rightOffset;//图例列表右侧间距
    private int bottomOffset;//图例列表下侧间距
    private float legendTextSize = 12;//图例字体大小
    private boolean isShowLegend = true;//是否显示图例
    private boolean valueTextEnable;//是否显示每组数据
    private boolean isFillEnable;//是否填充数据图层
    private List<Integer> layerColorList;//雷达图网层的颜色
    private List<Integer> dataColorList;//每组数据的颜色
    private List<Float> maxValues;//每一顶点的最大值
    private List<String> legendText;//图例文字集合
    private List<String> vertexText;//顶点描述字
    private List<List<Float>> values;//雷达图数据
    private LegendOnClickListener itemLegendListener = new LegendOnClickListener() {//图例点击事件
        @Override
        public void itemOnClick(int i) {
            System.out.println(i + "");
        }
    };

    private THRadarChart(RadarView mRadarView) {
        this.mRadarView = mRadarView;
    }

    public static THRadarChart instance(RadarView lineChart) {
        return new THRadarChart(lineChart);
    }
    
    public void clearAllData() {
        mRadarView.clearRadarData();
    }

    @SuppressLint("NewApi")
    public View effect() {
        mRadarView.setEmptyHint("无数据");//当没有数据时默认的提示信息
        mRadarView.setLayerColor(layerColorList == null ? Arrays.asList(Color.WHITE, Color.parseColor("#21000000"), Color.WHITE, Color.parseColor("#21000000"), Color.WHITE) : layerColorList);//雷达图网层的颜色
        mRadarView.setVertexLineWidth(1);//顶点连线宽度
        mRadarView.setLayerLineWidth(1);//雷达网线宽度
        mRadarView.setVertexText(vertexText);//顶点文字集合
        mRadarView.setVertexTextColor(vertexTextColor == 0 ? Color.WHITE : vertexTextColor);//顶点文字颜色
        mRadarView.setVertexTextOffset(40);//顶点图文相对于最外层网的偏移量
        mRadarView.setVertexTextSize(vertexTextSize);//顶点文字大小
        
        List<Bitmap> bitmapList = new ArrayList<>();
        for (int i = 0; i < vertexText.size(); i++) {
            bitmapList.add(drawableToBitmap(mRadarView.getContext().getResources().getDrawable(R.drawable.radius_3_no_stroke_black66_bg_shape)));
        }
        mRadarView.setVertexIconBitmap(bitmapList);//设置顶点文本背景
        mRadarView.setVertexIconPosition(3);//设置顶点文本背景位置

        //配置数据
        for (int i = 0; i < values.size(); i++) {
            List<String> listValueText = new ArrayList<>();
            List<Float> listValue = values.get(i);
            for (Float value : listValue) {
                listValueText.add(value.toString());
            }
            RadarViewEntity data = new RadarViewEntity(listValue);
            data.setColor(dataColorList.get(i));
            data.setValueTextEnable(valueTextEnable);//是否显示数据
            data.setValueTextSize(vertexTextSize);//数据文本字体大小
            data.setValueText(listValueText);//数据文本
            data.setValueTextColor(dataColorList.get(i));//数据文本字体颜色
            data.setFillEnable(isFillEnable);
            mRadarView.addData(data);
        }
        mRadarView.setMaxValues(maxValues);//设置各顶点最大限制值
        View viewGroup = mRadarView;
        
        if (isShowLegend) {
            LegendView view = new LegendView(mRadarView.getContext(), legendDirection, mRadarView);
            view.setLegendOffset(leftOffset, topOffset, rightOffset, bottomOffset);
            List<LegendEntity> legendEntities = new ArrayList<>();
            for (int i = 0; i < legendText.size(); i++) {
                int itemNum = i;
                LegendEntity legendEntity = new LegendEntity();
                legendEntity.legend = legendText.get(i);//图例标签
                legendEntity.textSize = legendTextSize;//图例字体大小
                legendEntity.textColor = legendTextColor;//图例字体颜色
                legendEntity.color = dataColorList.get(i);//图例图标颜色
                legendEntity.onClickListener = v -> itemLegendListener.itemOnClick(itemNum);//图例点击监听
                legendEntities.add(legendEntity);
            }
            view.initData(legendEntities);
            viewGroup = view;
        }
        return viewGroup;
    }
    
    public THRadarChart setLayerColorList(List<Integer> layerColorList) {
        this.layerColorList = layerColorList;
        return this;
    }

    public THRadarChart setLegendDirection(int legendDirection) {
        this.legendDirection = legendDirection;
        return this;
    }

    public THRadarChart setLegendTextColor(int legendTextColor) {
        this.legendTextColor = legendTextColor;
        return this;
    }

    public THRadarChart setOffset(int leftOffset, int topOffset, int rightOffset, int bottomOffset) {
        this.leftOffset = leftOffset;
        this.topOffset = topOffset;
        this.rightOffset = rightOffset;
        this.bottomOffset = bottomOffset;
        return this;
    }

    public THRadarChart setLegendTextSize(float legendTextSize) {
        this.legendTextSize = legendTextSize;
        return this;
    }

    public THRadarChart setShowLegend(boolean showLegend) {
        isShowLegend = showLegend;
        return this;
    }

    public THRadarChart setLegendText(List<String> legendText) {
        this.legendText = legendText;
        return this;
    }

    public THRadarChart setItemLegendListener(LegendOnClickListener itemLegendListener) {
        this.itemLegendListener = itemLegendListener;
        return this;
    }

    public THRadarChart setValueTextEnable(boolean valueTextEnable) {
        this.valueTextEnable = valueTextEnable;
        return this;
    }

    public THRadarChart setFillEnable(boolean fillEnable) {
        this.isFillEnable = fillEnable;
        return this;
    }

    public THRadarChart setVertexText(List<String> vertexText) {
        this.vertexText = vertexText;
        return this;
    }

    public THRadarChart setVertexTextSize(int vertexTextSize) {
        this.vertexTextSize = vertexTextSize;
        return this;
    }

    public THRadarChart setValues(List<List<Float>> values) {
        this.values = values;
        return this;
    }

    public THRadarChart setDataColorList(List<Integer> dataColorList) {
        this.dataColorList = dataColorList;
        return this;
    }

    public THRadarChart setMaxValues(List<Float> maxValues) {
        this.maxValues = maxValues;
        return this;
    }
    
    public THRadarChart setVertexTextColor(int vertexTextColor) {
        this.vertexTextColor = vertexTextColor;
        return this;
    }
    
    /**
     * drawable转bitMap
     */
    private static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(80, 30, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, 80, 30);
        drawable.draw(canvas);
        return bitmap;
    }

    public interface LegendOnClickListener{
        /**
         * 图例点击响应
         * @param i 图例下标
         */
        void itemOnClick(int i);
    }
}
