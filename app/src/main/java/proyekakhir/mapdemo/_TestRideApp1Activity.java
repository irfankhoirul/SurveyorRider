package proyekakhir.mapdemo;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class _TestRideApp1Activity extends FragmentActivity implements SensorEventListener, LocationListener {

    //----MAP----//
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Location FIRST_LOCATION;

    //----ACCELEROMETER----//
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int count=0;
    private boolean timerstart = false;
    private boolean stopSave = false;
    private boolean ACCELEROMETER_START = false;

    //Data yang diambil//
    ArrayList<Float> x = new ArrayList<>();
    ArrayList<Float> y = new ArrayList<>();
    ArrayList<Float> z = new ArrayList<>();
    ArrayList<Float> speed = new ArrayList<>();
    ArrayList<Float> arr_latitude = new ArrayList<>();
    ArrayList<Float> arr_longitude = new ArrayList<>();
//    ArrayList<Integer> mdetik = new ArrayList<>();

    //----ALL----//
    private TextView _act6_txt_detailLat, _act6_txt_detailLong, _act6_txt_detailSpeed,
            _act6_txt_detailDistance, _act6_txt_xaxis, _act6_txt_yaxis, _act6_txt_zaxis,
            _act6_txt_time;
    private ViewFlipper flipper;

    //---------------------------------------------------------------------------------------------------
    //----ALL----//--------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity___test_ride_app1);

        initializeViews();

        //----MAP----//
        setUpMapIfNeeded();

        //----ACCELEROMETER----??
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(getApplicationContext(), "Here we go!", Toast.LENGTH_SHORT).show();
            ACCELEROMETER_START = true;
        }
        else {
            // fai! we dont have an accelerometer!
            Toast.makeText(getApplicationContext(), "Oops!", Toast.LENGTH_SHORT).show();
        }

        mulai();
    }

    public void mulai(){
        Timer T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        int detik = count / 10;
                        timeConverter(detik);
                        count++;
                    }
                });
            }
        },0,100);
    }

    public void timeConverter(int time){
        int jam, menit, detik, temp;

        jam = time/3600;
        temp = time%3600;
        menit = temp/60;
        detik = temp%60;

        _act6_txt_time.setText(Integer.toString(jam)+":"+Integer.toString(menit)+":"+Integer.toString(detik));
    }

    public void initializeViews() {
        _act6_txt_detailLat = (TextView) findViewById(R.id._act6_txt_detailLat); //ok
        _act6_txt_detailLong = (TextView) findViewById(R.id._act6_txt_detailLong); //ok
        _act6_txt_detailSpeed = (TextView) findViewById(R.id._act6_txt_detailSpeed); //test
        _act6_txt_detailDistance = (TextView) findViewById(R.id._act6_txt_detailDistance); //
        _act6_txt_xaxis = (TextView) findViewById(R.id._act6_txt_xaxis); //test
        _act6_txt_yaxis = (TextView) findViewById(R.id._act6_txt_yaxis); //test
        _act6_txt_zaxis = (TextView) findViewById(R.id._act6_txt_zaxis); //test
        _act6_txt_time = (TextView) findViewById(R.id._act6_txt_time); //test
        flipper = (ViewFlipper) findViewById(R.id.viewFlipper2);
    }

    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    protected void onPause() {
        super.onPause();
    }

//    @Override
//    public void onBackPressed() {
//    }

    //---------------------------------------------------------------------------------------------------
    //----MAP----//--------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
            else if(mMap == null)
                Toast.makeText(getApplicationContext(), "Loaded failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
            Location prev, now;
            boolean firstChange = true;
            double distance = 0.0;

            @Override
            public void onMyLocationChange(Location location) {
            //    Toast.makeText(getApplicationContext(), "Location Changed!", Toast.LENGTH_SHORT).show();
            //    mMap.clear();
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                if(mMap != null){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                }
                _act6_txt_detailSpeed.setText(Float.toString(location.getSpeed()*36/10)+" KM/h");
                _act6_txt_detailLat.setText(Double.toString(location.getLatitude()));
                _act6_txt_detailLong.setText(Double.toString(location.getLongitude()));

                if(firstChange==true) {
                    now.setLatitude(location.getLatitude());
                    now.setLongitude(location.getLongitude());
                    firstChange=false;
                }else {
                    prev = now;
                    now.setLatitude(location.getLatitude());
                    now.setLongitude(location.getLongitude());

                    distance+=prev.distanceTo(now);
                }

                _act6_txt_detailDistance.setText(Double.toString(distance));
            }
        };

        mMap.setOnMyLocationChangeListener(myLocationChangeListener);

    }

    //---------------------------------------------------------------------------------------------------
    //----ACCELEROMETER----//----------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    @Override
    public void onSensorChanged(SensorEvent event) {
        _act6_txt_xaxis.setText(Float.toString(event.values[0]));
        _act6_txt_yaxis.setText(Float.toString(event.values[1]));
        _act6_txt_zaxis.setText(Float.toString(event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public ArrayList<Float> hitungF(ArrayList yy){
        ArrayList<Float> F = new ArrayList<Float>();
        float tempM = 0,k=0;
        int batas = yy.size();
        int sisa = batas%10;
        batas-=sisa;
        for(int i = 0; i<batas; i+=10) {
            for (int j = i; j < i+10; j++) {
                tempM += (Float) yy.get(j);
            }
            F.add(tempM);
            tempM=0;
        }
        return F;
    }

    //---------------------------------------------------------------------------------------------------
    //----SPEED----//------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    @Override
    public void onLocationChanged(Location location) {

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

    //Option Menu//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu___test_ride_app1, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu___test_ride_app1_stop:
                Toast.makeText(getApplicationContext(), "Stopped!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent (_TestRideApp1Activity.this,_TestRideApp1ResultActivity.class);
                startActivity(i);

                return true;
            case R.id.menu___test_ride_app1_detail:
                /*

                if(_act6_panel_detail.getVisibility() == View.VISIBLE) {
                    _act6_panel_detail.setVisibility(View.INVISIBLE);
                    _act6_panel_map.setVisibility(View.VISIBLE);
                //    setUpMapIfNeeded();
                }
                else {
                    _act6_panel_map.setVisibility(View.INVISIBLE);

                    _act6_panel_detail.setVisibility(View.VISIBLE);
                    _act6_panel_detail.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
                }
                */

                flipper.showNext();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}