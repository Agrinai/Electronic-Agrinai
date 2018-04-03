package com.example.nanda.newagri.Agriculture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.nanda.newagri.R;

public class newsActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        tv=(TextView)findViewById(R.id.tv);
        String news=getIntent().getStringExtra("news");
        tv.setText(news);
    }
}
