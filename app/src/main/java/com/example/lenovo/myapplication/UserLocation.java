package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class UserLocation extends Activity {
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private boolean isFirstLocation = true;
    private boolean flag = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.user_location);
        mapView = findViewById(R.id.user_location_map);
        baiduMap = mapView.getMap();
        locationClient = new LocationClient(getApplicationContext());
        locationClientOption = new LocationClientOption();
        locationClientOption.setCoorType("bd0911");
        locationClientOption.setOpenGps(true);
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClientOption.setScanSpan(3000);
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setIsNeedLocationPoiList(true);
        locationClient.setLocOption(locationClientOption);
        MyLocationListener myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        locationClient.start();
        baiduMap.setMyLocationEnabled(true);
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        baiduMap.setMapStatus(msu);
        if (flag){
            Intent intent = new Intent(getApplicationContext(),UserMap.class);
            startActivity(intent);
            UserLocation.this.finish();
        }
    }
    private class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (isFirstLocation){
                String userLocation = bdLocation.getLatitude()+";"+bdLocation.getLongitude();
                Data.userLocation = userLocation;
                LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker);
                OverlayOptions markeroptions = new MarkerOptions().position(point).icon(bitmap).draggable(true);
                Marker marker = (Marker) baiduMap.addOverlay(markeroptions);
                marker.setTitle("您的位置");
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(point);
                baiduMap.animateMapStatus(msu);
                isFirstLocation = false;
                MyLocationData locationData = new MyLocationData.Builder()
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude())
                        .build();
                baiduMap.setMyLocationData(locationData);
                flag=true;
                Intent intent = new Intent(getApplicationContext(),UserMap.class);
                startActivity(intent);
                UserLocation.this.finish();
            }
        }
    }
}
