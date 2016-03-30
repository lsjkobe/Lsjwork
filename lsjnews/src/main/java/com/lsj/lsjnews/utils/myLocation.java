package com.lsj.lsjnews.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import com.example.lsj.httplibrary.utils.MyToast;

/**
 * Created by Le on 2016/3/22.
 */
public class myLocation{
    private static myLocation instance;
    public static myLocation getInstance(){
        if(instance == null){
            synchronized(myLocation.class){
                if(instance==null){
                    instance=new myLocation();
                }
            }
        }
        return instance;
    }
    public myLocation() {
    }

    public void openGPSSettings(Context context) {
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (alm
                .isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
//            MyToast.showToast(context, "GPS模块正常");
            return;
        }
        MyToast.showToast(context, "请开启GPS！");
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        ((Activity) context).startActivityForResult(intent, 0); //此为设置完成后返回到获取界面

    }

    public void getLocation(final Context context) {
        openGPSSettings(context);
        // 获取位置管理服务
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) context.getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return ;
        }
        Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置


        // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
        locationManager.requestLocationUpdates(provider, 10 * 1000, 100, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                MyToast.showToast(context,location.getLongitude()+":"+location.getLatitude());
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
        });
    }

}
