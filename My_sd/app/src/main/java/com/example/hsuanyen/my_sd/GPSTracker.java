package com.example.hsuanyen.my_sd;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by Hsuan Yen on 2017/9/4.
 */

public class GPSTracker extends Service implements LocationListener {

    private static final int REQUEST_ACCESS_COARSE_LOCATION = 0;
    private final Context context;

    boolean isGPSEnabled =false;
    boolean isNetworkEnabled =false;
    boolean canGetLocation =false;

    Location location;
    protected LocationManager locationManager;
    double la, lo;


    public GPSTracker (Context context){
        this.context=context;
    }

    public Location getLocation() {
        try {
            if(ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager = (LocationManager) context
                        .getSystemService(LOCATION_SERVICE);
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    // no network provider is enabled
                } else {
                    this.canGetLocation = true;
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                10000,
                                10, this);
                        Log.d("Network", "Network Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
//                                la = location.getLatitude();
//                                lo = location.getLongitude();
                            }
                        }
                    }
                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    10000,
                                    10, this);
                            Log.d("GPS", "GPS Enabled");
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                                if (location != null) {
//                                    la = location.getLatitude();
//                                    lo = location.getLongitude();
//                                }
                            }
                        }
                    }
                }

            }

            // getting GPS status

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    //public  Location getLocation(){
//        try{
//            int permission = ActivityCompat.checkSelfPermission(context,
//                    Manifest.permission.ACCESS_COARSE_LOCATION);
//
//            if(permission != PackageManager.PERMISSION_GRANTED) {
//                //未取得權限 發送請求給使用者
//                Toast.makeText(context,"未取得權限",Toast.LENGTH_LONG).show();
//
//                ActivityCompat.requestPermissions((Activity) context,
//                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_ACCESS_COARSE_LOCATION
//                );
//            }else {
//                locationManager= (LocationManager) context.getSystemService((LOCATION_SERVICE));
//                isGPSEnabled = locationManager.isProviderEnabled((LocationManager.GPS_PROVIDER));
//                isNetworkEnabled = locationManager.isProviderEnabled( (locationManager.NETWORK_PROVIDER));
//
//                if(isGPSEnabled){
//                    Toast.makeText(context,location+"1111hhhhhhhhhhhhhh",Toast.LENGTH_LONG).show();
//                    if(location==null){
//                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,10,this);
//                        Toast.makeText(context,locationManager+"kk2222222kkkkkkkkkkkkkkkkkk",Toast.LENGTH_LONG).show();
//                        if(locationManager!=null){
//                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                            Toast.makeText(context,"333333333",Toast.LENGTH_LONG).show();
//                            Toast.makeText(context,"地標為"+location+"44444444444kkkkkkkkkkkkkkkkk",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }
//                if(location == null){
//                    Toast.makeText(context,"5555555555555555533333333",Toast.LENGTH_LONG).show();
//                    if(isNetworkEnabled){
//                        if(location == null ){
//                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,10,this);
//                            Toast.makeText(context,"地標為"+location+"",Toast.LENGTH_LONG).show();
//                            if(locationManager!=null){
//                                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                                Toast.makeText(context,"地標2為"+locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)+"",Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }
//                }
//
//            }
//        }catch(Exception ex){
//
//        }
//        Toast.makeText(context,location+"kkkkkkkkkkkkkkkkkkkk",Toast.LENGTH_LONG).show();
//        return location;
//
//    }
    public void onLocationChanged (Location location){

    }
//
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch(requestCode) {
//            case REQUEST_ACCESS_COARSE_LOCATION:
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //取得權限，進行檔案存取
//                    getLocation();
//                } else {
//                    //使用者拒絕權限，停用檔案存取功能
//                }
//        return;
//        }
//    }

    public void onStatusChanged (String Provider, int status, Bundle extra){

    }

    public void onProviderEnabled (String Provider){

    }
    public void onProviderDisabled (String Provider){

    }
    public IBinder onBind(Intent arg0){
        return null;
    }


}
