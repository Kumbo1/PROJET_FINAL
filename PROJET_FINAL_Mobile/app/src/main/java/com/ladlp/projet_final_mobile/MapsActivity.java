package com.ladlp.projet_final_mobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    LatLng origin = null;
    LatLng dest = null;
    private GoogleMap mMap;
    int idCommande;
    Connection conn = null;
    String connectionUrl = "jdbc:jtds:sqlserver://dbprojetfinal.czcjxlu56660.ca-central-1.rds.amazonaws.com:8080;database=ProjetDB;user=Master;password=Master123;";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        gestionConnection();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        idCommande = intent.getIntExtra("ID", 0);
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
        while(origin == null){
            if(conn != null){
                try{
                    Statement stm = conn.createStatement();
                    ResultSet rs = stm.executeQuery("SELECT longCommande, latCommande from ProjetDB.dbo.MAP where idCommande = " + idCommande);
                    while(rs.next())
                        origin = new LatLng(rs.getDouble("latCommande"), rs.getDouble("longCommande"));
                }catch(SQLException exc){
                    String yo = exc.toString();
                }
            }
        }
        try{
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT longLivreur, latLivreur from ProjetDB.dbo.MAP where idCommande = " + idCommande);
            while(rs.next())
                dest = new LatLng(rs.getDouble("latLivreur"), rs.getDouble("longLivreur"));
        }catch(SQLException exc){
            String yo = exc.toString();
        }
        mMap.setMapType(mMap.MAP_TYPE_NORMAL);
        mMap.addMarker(new MarkerOptions().position(origin).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));
        if(dest.latitude != 0.0 && dest.longitude != 0.0){
            mMap.addMarker(new MarkerOptions().position(dest).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("Livreur"));
            String url = getDirectionsUrl(origin, dest);
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
        }
    }

    private class DownloadTask extends AsyncTask<String, Void, String>{
        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
            // For storing data from web service
            String data = "";
            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }
    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try{
                jObject = new JSONObject(jsonData[0]);
                JSONDirectionsParser parser = new JSONDirectionsParser();
                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }
        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.RED);
            }
            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(origin);
            builder.include(dest);
            LatLngBounds bounds = builder.build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
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
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=AIzaSyCS7pQ5bFOQhHENri9wbnCMN-lczbApfNU";
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
    void gestionConnection() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Class.forName("net.sourceforge.jtds.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                }
                try {
                    conn = DriverManager.getConnection(connectionUrl);

                } catch (SQLException se) {
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        };
        thread.start();

    }
}
