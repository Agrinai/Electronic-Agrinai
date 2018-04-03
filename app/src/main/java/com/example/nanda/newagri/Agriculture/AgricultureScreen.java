package com.example.nanda.newagri.Agriculture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nanda.newagri.Ecological;
import com.example.nanda.newagri.R;


public class AgricultureScreen extends Fragment {
    CardView imageprocesscard,findsoil,ecorelationCard;
    public AgricultureScreen() {
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_agriculture, container, false);
            imageprocesscard=(CardView)view.findViewById(R.id.imageprocesscard);
            ecorelationCard=(CardView)view.findViewById(R.id.ecorelationCard);
            imageprocesscard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(),ImageProcess.class);
                    startActivity(i);
                }
            });
            ecorelationCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(),Ecological.class);
                    startActivity(i);
                }
            });


        return view;
    }
}