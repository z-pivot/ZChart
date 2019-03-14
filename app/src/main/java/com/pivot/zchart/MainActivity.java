package com.pivot.zchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.zcolin.frame.util.ActivityUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btnLine = findViewById(R.id.btn_line);
        Button btnBar = findViewById(R.id.btn_bar);
        Button btnCombined = findViewById(R.id.btn_line_bar);
        Button btnScatter = findViewById(R.id.btn_scatter);
        Button btnPie = findViewById(R.id.btn_pie);
        Button btnRadar = findViewById(R.id.btn_radar);
        
        btnLine.setOnClickListener(v -> ActivityUtil.startActivity(MainActivity.this, LineDemoActivity.class));
        btnBar.setOnClickListener(v -> ActivityUtil.startActivity(MainActivity.this, BarDemoActivity.class));
        btnCombined.setOnClickListener(v -> ActivityUtil.startActivity(MainActivity.this, CombinedDemoActivity.class));
        btnScatter.setOnClickListener(v -> ActivityUtil.startActivity(MainActivity.this, ScatterDemoActivity.class));
        btnPie.setOnClickListener(v -> ActivityUtil.startActivity(MainActivity.this, PieDemoActivity.class));
        btnRadar.setOnClickListener(v -> ActivityUtil.startActivity(MainActivity.this, RadarDemoActivity.class));
    }
}
