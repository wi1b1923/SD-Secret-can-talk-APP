package com.example.hsuanyen.my_sd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gpsTracker;
    private Location mlocation;
    private Button map_back_btn;
    private Button new_base_btn;
    private ImageButton map_mybase_btn;
    private ImageButton map_refix_btn;
    private TextView map_show_tv;
    double mlatitude, mlongtitude;
    private EditText base_name_et;
    private String user_id;
    private SharedPreferences preferences;
    private boolean base_visi=false;

    private static final String TAG = MapsActivity.class.getSimpleName();
    private long clickTime = 0;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit(); return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次後退鍵退出程式", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            Log.e(TAG, "exit application");
            this.finish();
            System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Bundle bundle=this.getIntent().getExtras();
        user_id=bundle.getString("user_id");

        gpsTracker = new GPSTracker(getApplicationContext());
        mlocation = gpsTracker.getLocation();

        mlatitude = mlocation.getLatitude();
        mlongtitude = mlocation.getLongitude();
        map_refix_btn=(ImageButton)findViewById(R.id.imageButton2);
        map_mybase_btn=(ImageButton)findViewById(R.id.imageButton);
        map_back_btn=(Button) findViewById(R.id.map_back_btn);
        new_base_btn = (Button) findViewById(R.id.new_base_btn);
        map_show_tv = (TextView) findViewById(R.id.map_show_tv);
        base_name_et=(EditText) findViewById(R.id.base_name_et);
        //TextView tx =(TextView)findViewById(R.id.textView2);
        map_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map_back=new Intent();
                map_back.setClass(MapsActivity.this,BaseActivity.class);
                startActivity(map_back);
            }
        });
        preferences=MapsActivity.this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        map_mybase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(base_visi==false){
                    base_visi=true;

                }else{
                    base_visi=false;
                }
                mMap.clear();
                set_old_base(base_visi);
            }
        });

        //location=getLocation();
        //tx.setText(location);
        map_refix_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsTracker = new GPSTracker(getApplicationContext());
                mlocation = gpsTracker.getLocation();
                mlatitude = mlocation.getLatitude();
                mlongtitude = mlocation.getLongitude();
                float zoom;
                zoom = 17;
                LatLng locasite = new LatLng(mlatitude, mlongtitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(locasite));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(locasite)
                        .zoom(zoom)
                        .bearing(0)
                        .tilt(0)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });
        ////新增基地按鈕
        new_base_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view ) {

                Intent new_base_back = new Intent();
                //double [] locations = {mlatitude,mlongtitude};
                //Bundle bundle = new Bundle();
                String base_name=base_name_et.getText().toString();
                String lat=String.valueOf(mlatitude);
                String lon=String.valueOf(mlongtitude);
                String work = "base";

                //bundle.putString("title", );
                //'放入使用者對此座標的命名(秘密基地名稱)
                BackgroundTask2 backgroundTask2 =new BackgroundTask2(MapsActivity.this);
                backgroundTask2.execute(work,user_id,base_name,lat,lon);

                //放入此座標經緯度
                //bundle.putDoubleArray("locations",locations);
                //new_base_back.putExtras(bundle);
                //startActivity(new_base_back);
                //finish();
            }
        });
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        float zoom;
        zoom = 17;
        LatLng locasite = new LatLng(mlatitude, mlongtitude);



        //新增一個目前定位上的標記    (記得要再做一個重新定位按鈕)
        //final MarkerOptions marker_now  = new MarkerOptions().position(locasite).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.flag));
        final Marker marker_now =mMap.addMarker(new MarkerOptions().position(locasite).title(""));
        //mMap.addMarker(new MarkerOptions().position().title());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locasite));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(locasite)
                .zoom(zoom)
                .bearing(0)
                .tilt(0)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        String user_base_get=preferences.getString("user_base",null);
        if(user_base_get.equals("null")){

        }else{
            String[] user_base=preferences.getString("user_base",null).split("[&]");
            if (user_base != null && user_base.length > 0) {
                for (String value : user_base) {
                    if(value!=""){
                        String[] s=value.split("[\\|]");
                        double lat=Double.parseDouble(s[2]);
                        double lon=Double.parseDouble(s[3]);
                        LatLng locasite_old = new LatLng(lat,lon);
                        final Marker marker_old  = mMap.addMarker(new MarkerOptions().position(locasite_old).title(s[1]).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag)));
                        marker_old.setVisible(base_visi);
                    }
                }
            }
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions marker  = new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title(" ");
                mMap.clear();
                set_old_base(base_visi);
                mMap.addMarker(marker);
                mlatitude=point.latitude;
                mlongtitude=point.longitude;

            }
        });


    }
    public void set_old_base(boolean visi){
        String user_base_get=preferences.getString("user_base",null);
        if(user_base_get.equals("null")){

        }else{
            String[] user_base=preferences.getString("user_base",null).split("[&]");
            if (user_base != null && user_base.length > 0) {
                for (String value : user_base) {
                    if(value!=""){
                        String[] s=value.split("[\\|]");
                        double lat=Double.parseDouble(s[2]);
                        double lon=Double.parseDouble(s[3]);
                        LatLng locasite_old = new LatLng(lat,lon);

                        final Marker marker_old  = mMap.addMarker(new MarkerOptions().position(locasite_old).title(s[1]).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag)));
                        marker_old.setVisible(visi);
                    }
                }
            }
        }
    };


}

