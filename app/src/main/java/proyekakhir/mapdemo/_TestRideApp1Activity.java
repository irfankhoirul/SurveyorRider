package proyekakhir.mapdemo;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class _TestRideApp1Activity extends FragmentActivity implements SensorEventListener, LocationListener {

    //----MAP----//
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

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
    ArrayList<Float> latitude = new ArrayList<>();
    ArrayList<Float> longitude = new ArrayList<>();
//    ArrayList<Integer> mdetik = new ArrayList<>();

    //----ALL----//
    private TextView _act6_text_axisvalue, _act6_text_speed;

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

        //----SPEED----//
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                //    location.getLatitude();
                //    Toast.makeText(getApplicationContext(), "Current speed:" + location.getSpeed(),
                //            Toast.LENGTH_SHORT).show();
                _act6_text_speed.setText(Float.toString(location.getSpeed()));
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        mulai();
    }

    public void mulai()
    {
        //    Toast.makeText(getApplicationContext(), "Timer Started", Toast.LENGTH_SHORT).show();
        Timer T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        int milidetik = count;
                        int detik = count / 10;
                        milidetik %= 10;

//                            mdetik.add(count);
                        _act6_text_axisvalue.setText(Integer.toString(detik));

                        count++;
                    }
                });
            }
        },0,100);
    }

    public void initializeViews() {
        _act6_text_axisvalue = (TextView) findViewById(R.id._act6_text_axisvalue);
        _act6_text_speed = (TextView) findViewById(R.id._act6_text_speed);
    }

    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
    }

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
            @Override
            public void onMyLocationChange(Location location) {
                mMap.clear();
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                //    Toast.makeText(getApplicationContext(), "Lat : "+String.valueOf(location.getLatitude()), Toast.LENGTH_SHORT).show();
                mMap.addMarker(new MarkerOptions().position(loc));
                if(mMap != null){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                }
            }
        };

        //    mMap.setOnMyLocationChangeListener(myLocationChangeListener);

    }

    //---------------------------------------------------------------------------------------------------
    //----ACCELEROMETER----//----------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    @Override
    public void onSensorChanged(SensorEvent event) {
    //    _act6_text_axisvalue.setText("X : "+Float.toString(event.values[0])+" Y : "+Float.toString(event.values[1])+" Z : "+Float.toString(event.values[2]));
    //    if (timerstart == false)
    //        timer(event);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void timer(SensorEvent event)
    {
        //    Toast.makeText(getApplicationContext(), "Timer Started", Toast.LENGTH_SHORT).show();
            timerstart=true;
            Timer T=new Timer();
            final SensorEvent ev=event;
            T.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            int milidetik = count;
                            int detik = count / 10;
                            milidetik %= 10;
                            x.add(ev.values[0]);
                            y.add(ev.values[1]);
                            z.add(ev.values[2]);
//                            mdetik.add(count);

                            count++;
                        }
                    });
                }
            },0,100);
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
    //    _act6_text_speed.setText(Float.toString(location.getSpeed()));
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}