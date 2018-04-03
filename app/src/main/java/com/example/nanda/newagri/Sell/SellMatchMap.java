package com.example.nanda.newagri.Sell;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanda.newagri.Buy.BuyMatchMap;
import com.example.nanda.newagri.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SellMatchMap extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener, OnMapReadyCallback ,LocationListener {

    private GoogleMap mMap;
    private static final String TAG = SellMatchMap.class.getSimpleName();
    private static final int MY_LOCATION_REQUEST_CODE = 1;
    String Duration, Distance,mlat,mlong,name,phno;
    LatLng perumbakkam,karapakkam, currentLatLong,destinationlatlong;;
    TextView km,time,nametext,phonenumber;
    LocationManager locationManager;
    LinearLayout detailslayout;
    Animation slideUpAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysell_match_map);
        detailslayout=(LinearLayout)findViewById(R.id.detailslayout);
        detailslayout.setVisibility(View.INVISIBLE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mlat=getIntent().getStringExtra("mlat");
        mlong=getIntent().getStringExtra("mlong");
        name=getIntent().getStringExtra("name");
        phno=getIntent().getStringExtra("phno");
        km=(TextView)findViewById(R.id.KiloMetre);
        time=(TextView)findViewById(R.id.duration);
        nametext = (TextView) findViewById(R.id.nameText);
        phonenumber = (TextView) findViewById(R.id.phonenumberText);
        destinationlatlong=new LatLng(Double.valueOf(mlat),Double.valueOf(mlong));
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
        /*mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);*/
        getLocation();
        // Add a marker in Sydney and move the camera
       /* perumbakkam = new LatLng(12.900727, 80.196881);
        karapakkam = new LatLng(12.919052, 80.230034);
        mMap.addMarker(new MarkerOptions().position(perumbakkam).title("Perumbakkam").snippet("Start Point "));
        mMap.addMarker(new MarkerOptions().position(karapakkam).title("Karapakkam").snippet("End Point"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(perumbakkam));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(karapakkam));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20.0f));
        String url = getDirectionsUrl(perumbakkam, karapakkam);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);*/
    }
    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatLong=new LatLng(location.getLatitude(),location.getLongitude());
        String url = getDirectionsUrl(currentLatLong, destinationlatlong);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            Log.d("Result in Post",result.toString());

            try {
                JSONObject json = new JSONObject(result);
                jRoutes = json.getJSONArray("routes");
                for (int i = 0; i < jRoutes.length(); i++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    Log.d("jLegs", jLegs.toString());
                    for (int j = 0; j < jLegs.length(); j++) {
                        JSONObject jsonDistanceObject = null;
                        jsonDistanceObject = ((JSONObject) jLegs.get(j)).getJSONObject("distance");
                        Log.d("Distance", jsonDistanceObject.toString());
                        Distance = jsonDistanceObject.getString("text");
                        Log.d("DistanceString", Distance);
                        JSONObject jsonDurationObject = ((JSONObject) jLegs.get(j)).getJSONObject("duration");
                        Log.d("Duration", jsonDurationObject.toString());
                        Duration = jsonDurationObject.getString("text");
                        Log.d("DurationString", Duration);

                        SharedPreferences sp = getSharedPreferences("mapData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("distance", Distance);
                        editor.putString("duration", Duration);
                        editor.apply();
                    }
                }

            } catch (JSONException e) {
            }
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }



    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                SellDirectionsJSONParser parser = new SellDirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.YELLOW);
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route


            SharedPreferences mapData=getSharedPreferences("mapData",Context.MODE_PRIVATE);
            String DistanceInKm=mapData.getString("distance","");
            Log.d("Distance Success",DistanceInKm);
            String DurationInMinute=mapData.getString("duration","");
            if(DurationInMinute.length()!=0){
                mMap.clear();
                slideUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_anim);
                detailslayout.startAnimation(slideUpAnimation);
                detailslayout.setVisibility(View.VISIBLE);
                mMap.addMarker(new MarkerOptions().position(currentLatLong).title("Your Location").snippet("Start Point").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(destinationlatlong).title("End point").snippet("Distance :"+DistanceInKm+"Duration :"+DurationInMinute));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLong));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(destinationlatlong));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11.50f));
                mMap.addPolyline(lineOptions);
                km.setText(         " : "+DistanceInKm);
                time.setText(       " : "+DurationInMinute);
                nametext.setText(   " : "+name);
                phonenumber.setText(" : "+phno);

            }
            else{
                Toast.makeText(getApplicationContext(),"null",Toast.LENGTH_LONG).show();

            }

        }
    }


    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public boolean onMyLocationButtonClick() {

        Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
            } else {
                Toast.makeText(getApplicationContext(), "Permission was denied", Toast.LENGTH_SHORT).show();
                // Permission was denied. Display an error message.
            }
        }

    }

            /*public String Distance(String Distance, String Duration) {
                SharedPreferences sp = getSharedPreferences("mapData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("distance", Distance);
                editor.putString("duration", Duration);
                editor.apply();

                SharedPreferences sp1 = getSharedPreferences("mapData", Context.MODE_PRIVATE);
                String distance = sp1.getString("distance", "");
                String duration = sp1.getString("duration", "");

                Toast.makeText(getApplicationContext(), "Distance" + distance, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Duration" + duration, Toast.LENGTH_LONG).show();
                return null;
            }*/
}
