package com.example.nanda.newagri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanda.newagri.Agriculture.Ecological_RelationShip;

public class Ecological extends AppCompatActivity {
    Spinner ecoSpinner;
    TextView ecotitle,ecomain;

    Ecological_RelationShip ecoRelation=new Ecological_RelationShip();
    String MainText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecological);
        ecotitle=(TextView)findViewById(R.id.title);
        ecomain=(TextView)findViewById(R.id.maintext);

        ecoSpinner=(Spinner)findViewById(R.id.ecorelation);
        ecoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    Toast.makeText(getApplicationContext(),"Please select ",Toast.LENGTH_LONG).show();
                }
                if(position==1){
                    MainText=ecoRelation.பழக்காடி();
                    ecotitle.setText("பழக்காடி");
                    ecomain.setText(""+MainText);
                }
                if(position==2){
                    MainText=ecoRelation.மண்புழுஉரம்();
                    ecotitle.setText("மண்புழுஉரம்");
                    ecomain.setText(""+MainText);
                }
                if(position==3){
                    MainText=ecoRelation.அமிர்தகரைசல்();
                    ecotitle.setText("அமிர்தகரைசல்");
                    ecomain.setText(""+MainText);
                }
                if(position==4){
                    MainText=ecoRelation.வீட்டில்இயற்கைஉரம்தயாரித்தல்();
                    ecotitle.setText("வீட்டில் இயற்கை உரம் தயாரித்தல்");
                    ecomain.setText(""+MainText);
                }
                if(position==5){
                    MainText=ecoRelation.தோட்டத்தில்எருதயாரித்தல்();
                    ecotitle.setText("தோட்டத்தில் எருதயாரித்தல்");
                    ecomain.setText(""+MainText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
