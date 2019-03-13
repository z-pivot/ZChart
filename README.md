# 图表部分功能说明
==
这个图表库有一个基类是THBaseChart，它抽取了常用图表的一些公共属性，这些公共属性主要用于线图、柱图、组合图，
饼图由于没有x、y轴，所以公共属性不是很多，更多的属性是在THPieChart本类中进行声明。而雷达图是没有继承自这个
基类，因为MPChart库里面的雷达图有部分局限性就没有用，用的是另外的一个私人库，但是大体上的使用和前面四个图表
都是一样的。另外，饼图和雷达图的图例是自定义RecyclerView布局可滑动，这样可以解决图例过多而无法全部显示的问题，
图例也添加了点击监听，返回当前点击图例的下标。前面三种图的图例还是用的MPChart自带的图例，它仅限于图例数量较少
的图表，而且图例也没有点击事件监听。

ChartValueItemEntity.java
    这个实体类是每数据属性的集合，包括该组数据的值集合、颜色、字体大小、格式化方式、图例文字等。还包括部分图表
特有的属性：线图的背景填充渐变色、堆叠柱状图的值集合和颜色集合，这三个属性有特定的构造方法，也可以用set方法设置

BaseChartView.java
    这个类相当于图表的容器，你可以只放一张图表也可以放进去很多张，它也有一个实体类封装了每一张图表的标题、副标题、
字体大小、颜色以及图表对象。它是所有图表显示的基础，当多于一张图表时会在左侧出现tab栏显示图表名称列表，用来点击
切换图表显示。它的使用上都是如下规则，以柱图为例：

        BaseChartView baseChartView = findViewById(R.id.bar_chart_view);
        List<BaseChartViewEntity> chartViewEntities = new ArrayList<>();
        BaseChartViewEntity chartEntity1 = new BaseChartViewEntity();
        BaseChartViewEntity chartEntity2 = new BaseChartViewEntity();
         
        chartEntity1.chart = THBarChart.instance(new BarChart(getBaseContext()))
                .setListBarValue(datas)//设置柱图数据 必要
                ... ...
                .effect();//最后调用此方法使所有设置生效 必要
        chartEntity1.title = "图一";
        chartEntity1.subTitle = "非堆叠柱状图";
        
        chartEntity2.chart = THBarChart.instance(new BarChart(getBaseContext()))
                .addListValue(listStackBarValue, listStackColor, false, null, 12)//设置堆叠柱图数据 必要
                ... ...
                .effect();//最后调用此方法使所有设置生效 必要
        chartEntity2.title = "图二";
        chartEntity2.subTitle = "堆叠柱状图";
        
        chartViewEntities.add(chartEntity1);
        chartViewEntities.add(chartEntity2);
        baseChartView.clear();
        baseChartView.initData(chartViewEntities);
      
LegendView.java
    这个类是把自定义图例列表和图表对象进行组合，最后返回的是一张带自定义图例的图表对象
    
        LegendView pieView = new LegendView(pieChart.getContext(), legendDirection, chartView);//这个chartView就是图表对象
        pieView.setLegendOffset(leftLegendOffset, topLegendOffset, rightLegendOffset, bottomLegendOffset);//设置图例偏移
        List<LegendEntity> legendEntities = new ArrayList<>();
        for (int i = 0; i < labelList.size(); i++) {
            int itemNum = i;
            LegendEntity legendEntity = new LegendEntity();
            legendEntity.legend = labelList.get(i);//设置图例名称列表
            legendEntity.textSize = legendTextSize;//设置图例文本字体大小
            legendEntity.textColor = legendTextColor == 0 ? Color.BLACK : legendTextColor;//设置图例文本字体颜色
            legendEntity.color = listColor == null ? getListColor().get(i) : listColor.get(i);//设置图标颜色
            legendEntity.onClickListener = v -> itemLegendListener.itemOnClick(itemNum);//设置点击响应
            legendEntities.add(legendEntity);
        }
        pieView.initData(legendEntities);

    
