package com.example.nanda.newagri.Buy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nanda.newagri.Constants;
import com.example.nanda.newagri.R;
import com.example.nanda.newagri.Sell.SellMatchMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllBuymatchMap extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,OnMapReadyCallback {

    String Array;
    JSONArray parentArray;
    private GoogleMap mMap;
    String product_name, kilo, price, mlat, mlong, username, phno;
    private static final String TAG = SellMatchMap.class.getSimpleName();
    Constants constant=new Constants();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityallbuymatch_map);

        Bundle b = getIntent().getExtras();
        Array = b.getString("arrayOfObj");
        Log.d("Sarray", Array);
        try {
            parentArray = new JSONArray(Array);
            Log.d("parrentArray", parentArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle_night));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        try {
            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject finalObject = parentArray.getJSONObject(i);
                product_name = finalObject.getString("VegName");
                Log.d("productname", product_name);
                kilo = finalObject.getString("VegKG");
                price = finalObject.getString("VegPrice");
                JSONObject useridd = finalObject.getJSONObject("UserId");
                JSONObject latlong = useridd.getJSONObject("location");
                mlat = latlong.getString("lat");
                mlong = latlong.getString("long");
                username = useridd.getString("name");
                Log.d("username", username);
                phno = useridd.getString("emailorphone");
                Log.d("phno", phno);
                LatLng mapMarker = new LatLng(Double.valueOf(mlat), Double.valueOf(mlong));
                mMap.addMarker(new MarkerOptions().position(mapMarker).title(username).snippet("ProductName : " + product_name + "\n" + "Product KG      : " + kilo+" kg"+"\n"+"Price                 : "+price));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(mapMarker));


            }

            mMap.animateCamera(CameraUpdateFactory.zoomTo(11.50f));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    Context mContext=getApplicationContext();
                    LinearLayout info = new LinearLayout(mContext);
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(mContext);
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(mContext);
                    snippet.setTextColor(Color.GRAY);
                    snippet.setText(marker.getSnippet());

                    info.addView(title);
                    info.addView(snippet);

                    return info;
                }
            });

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
}
