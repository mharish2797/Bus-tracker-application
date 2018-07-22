package bus.track.bustracker;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

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


public class Mapbus extends FragmentActivity implements  OnMapReadyCallback {
    Marker TP;
    double latit,longit,adapt=0.001;
    public int hs;
    int N;
    String x[]=new String[100];
   private GoogleMap googleMap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapbus);
        Bundle bundle = getIntent().getExtras();
        N=bundle.getInt("N");

        for(int i=0;i<N;i++){

            x[i]=bundle.getString(String.valueOf(i));
        }
        String bnum=bundle.getString("BusNum");
        GPSTracker gps;
        gps=new GPSTracker(getApplicationContext());
        if (gps.canGetLocation()) {

            latit = gps.getLatitude();
           //latit+=0.002;
            longit = gps.getLongitude();
           // longit+=0.0001;
        }

       /* SupportMapFragment mapFragment =(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync((OnMapReadyCallback) mapFragment);
INSERT into `17H` (latitude,longitude,date,time) VALUES ('13.0466066','80.2215543','23.09.16','20.35')


         */

        double minl=latit,maxl=longit,ml=100,mg=100;
        final LatLng point = new LatLng(latit,longit);

            try {
                if (googleMap == null) {
                    googleMap = ((MapFragment) getFragmentManager().
                            findFragmentById(R.id.map)).getMap();
                    googleMap.getUiSettings().setZoomGesturesEnabled(true);
                }
                GoogleMapOptions options = new GoogleMapOptions();
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                TP = googleMap.addMarker(new MarkerOptions().position(point).title("Your Location").draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latit, longit), 16.0f));
                int hs = N / 2, ms = 0;
                Marker XP[] = new Marker[hs];
                LatLng fus = null;
                while(adapt<1) {
                    for (int i = 0; i < N; i++) {
                        double p, q, ckl, ckg;
                        p = Double.parseDouble(x[i++]);
                        q = Double.parseDouble(x[i++]);
                        String tim = x[i];
                        if (p > latit) ckl = p - latit;
                        else ckl = latit - p;
                        if (q > longit) ckg = q - longit;
                        else ckg = longit - q;
                        if (ckl < ml || ckg < mg) {
                            ml = ckl;
                            mg = ckg;
                            minl = p;
                            maxl = q;
                        }
                        // Toast.makeText(this,String.valueOf(ckl)+" "+String.valueOf(ckg),Toast.LENGTH_LONG).show();
                        if (ckl < adapt && ckg < adapt) {
                            fus = new LatLng(p, q);
                            XP[ms++] = googleMap.addMarker(new MarkerOptions().position(fus).title(bnum + " Last seen @ " + tim).draggable(false).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        /*Polyline line = googleMap.addPolyline(new PolylineOptions()
                                .add(new LatLng(latit, longit), new LatLng(p, q))
                                .width(10)
                                .color(Color.BLUE));*/
                            String url = getDirectionsUrl(point, fus);

                            DownloadTask downloadTask = new DownloadTask(false);

// Start downloading json data from Google Directions API
                            downloadTask.execute(url);

                        }
                    }
                    if(ms==0){ adapt*=2;  }
                    else break;

                }

                googleMap.setMyLocationEnabled(true);
                googleMap.setTrafficEnabled(true);
                options.mapType(GoogleMap.MAP_TYPE_HYBRID).compassEnabled(true).rotateGesturesEnabled(true).tiltGesturesEnabled(true).zoomGesturesEnabled(true);
                MapFragment.newInstance(options);

                LatLng pts=new LatLng(minl,maxl);
                //sggx=true;
                String url = getDirectionsUrl(point, pts);

                DownloadTask downloadTask = new DownloadTask(true);

// Start downloading json data from Google Directions API
                downloadTask.execute(url);

                //googleMap.setLocationSource(source);
            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        final LatLng point = new LatLng(latit,longit);
        TP = googleMap.addMarker(new MarkerOptions().position(point).title("Your Location").draggable(false));
        googleMap.setTrafficEnabled(true);
        googleMap.setMyLocationEnabled(true);
       // googleMap.setLocationSource(poimt);
    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){


        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }



    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception ", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {
        boolean shorter;
        public DownloadTask(boolean shorter){
            this.shorter=shorter;
        }

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask(shorter);

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
        boolean shorter;
        public ParserTask(boolean shorter){
            this.shorter=shorter;
        }
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

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
                lineOptions.width(10);
               if(shorter==true) lineOptions.color(Color.BLUE);
                else
                lineOptions.color(Color.RED);

            }

            // Drawing polyline in the Google Map for the i-th route
            googleMap.addPolyline(lineOptions);
        }
    }


}


