package com.example.nanda.newagri.Agriculture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.nanda.newagri.Constants;
import com.example.nanda.newagri.R;

public class VegListOfPHvalue extends AppCompatActivity {
    Constants constants=new Constants();

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veglistof_phvalue);
        lv=(ListView)findViewById(R.id.phveglist);

        String color=getIntent().getStringExtra("color");
        if(color.equals("deepbrowncolour")){
            String[] nameArray=constants.deepbrowncolour();
            ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameArray);
            lv.setAdapter(adapter);
        }
        if(color.equals("lightyellowishcolour")){
            String[] nameArray=constants.lightyellowishcolour();
            Log.d("nameArray",nameArray.toString());
            ListAdapter adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameArray);
            lv.setAdapter(adapter);
        }
        if(color.equals("greenishcolour")){
            String[] nameArray=constants.greenishcolour();
            ListAdapter adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameArray);
            lv.setAdapter(adapter);
        }
    }
}
