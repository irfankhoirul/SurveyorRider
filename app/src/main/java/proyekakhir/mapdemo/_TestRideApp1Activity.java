package proyekakhir.mapdemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public class _TestRideApp1Activity extends FragmentActivity implements SensorEventListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int count=0;
    private boolean timerstart = false;

    private boolean stopSave = false;

    ArrayList<Float> x = new ArrayList<Float>();
    ArrayList<Float> y = new ArrayList<Float>();
    ArrayList<Float> z = new ArrayList<Float>();
    ArrayList<Integer> mdetik = new ArrayList<Integer>();

    private TextView _act6_text_axisvalue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity___test_ride_app1);
        initializeViews();
        setUpMapIfNeeded();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(getApplicationContext(), "Here we go!", Toast.LENGTH_SHORT).show();
        }
        else {
            // fai! we dont have an accelerometer!
            Toast.makeText(getApplicationContext(), "Oops!", Toast.LENGTH_SHORT).show();
        }

    }

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
                        int detik = count/10;
                        milidetik%=10;
                        //     if(stopSave = false) {
                        //    ms.setText(Integer.toString(milidetik));
                        //    s.setText(Integer.toString(detik));
                        x.add(ev.values[0]);
                        y.add(ev.values[1]);
                        z.add(ev.values[2]);
                        mdetik.add(count);
                        //     }
//                        hasil.add(new Pengukuran(count,ev.values[1]));

                        //    displayCurrentValues(ev);
                        //    Toast.makeText(getApplicationContext(), "Y : "+Float.toString(ev.values[1]), Toast.LENGTH_SHORT).show();

                        _act6_text_axisvalue.setText(Integer.toString(detik));
                        count++;
                    }
                });
            }
        },0,100);
    }


    public void initializeViews() {
        _act6_text_axisvalue = (TextView) findViewById(R.id._act6_text_axisvalue);
    }

    protected void onResume() {
        super.onResume();
//        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        setUpMapIfNeeded();
    }

    protected void onPause() {
        super.onPause();
        //    sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(timerstart==false)
            timer(event);
    //    _act6_text_axisvalue.setText("X : "+Float.toString(event.values[0])+" - Y : "+Float.toString(event.values[1])+" - Z : "+Float.toString(event.values[2]));

        // clean current values
//        displayCleanValues();
        // display the current x,y,z accelerometer values
//        displayCurrentValues(event);
        // display the max x,y,z accelerometer values
    //    displayMaxValues();

        // get the change of the x,y,z values of the accelerometer
    //    deltaX = Math.abs(lastX - event.values[0]);
    //    deltaY = Math.abs(lastY - event.values[1]);
    //    deltaZ = Math.abs(lastZ - event.values[2]);

        // if the change is below 2, it is just plain noise
    //    if (deltaX < 2)
    //        deltaX = 0;
    //    if (deltaY < 2)
    //        deltaY = 0;
    }

}
