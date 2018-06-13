package com.example.lenovo.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

/**
 * Created by me on 2018/6/13.
 */

public class BusinessMap extends Activity {
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private boolean isFirstLocation = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.business_map);
        Display display = getWindowManager().getDefaultDisplay();
        Toast toast = Toast.makeText(getApplicationContext(),"请长按标记",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,display.getHeight()/6);
        toast.show();
        mapView = findViewById(R.id.business_map);
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
        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.e("baiduMap","拖拽结束,纬度:"+marker.getPosition().latitude+",经度:"+marker.getPosition().longitude);
                Toast toast = Toast.makeText(getApplicationContext(),"返回保存位置",Toast.LENGTH_LONG);
                Display display = getWindowManager().getDefaultDisplay();
                toast.setGravity(Gravity.TOP,0,display.getHeight()/6);
                toast.show();
                String businessLocation = marker.getPosition().latitude+";"+marker.getPosition().longitude;
                Data.businessLocation = businessLocation;
            }

            @Override
            public void onMarkerDragStart(Marker marker) {
                Display display = getWindowManager().getDefaultDisplay();
                Toast toast = Toast.makeText(getApplicationContext(),"请选择您店铺的位置",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,display.getHeight()/6);
                toast.show();
            }
        });
    }

    private class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (isFirstLocation){
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
            }
        }
    }
}
