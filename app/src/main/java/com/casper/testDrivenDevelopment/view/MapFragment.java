package com.casper.testDrivenDevelopment.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.casper.testDrivenDevelopment.R;
import com.casper.testDrivenDevelopment.data.Shop;
import com.casper.testDrivenDevelopment.data.ShopLoader;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {


    private TextureMapView mapView = null;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = view.findViewById(R.id.bmap_view);

        BaiduMap baiduMap = mapView.getMap();

        //修改百度地图的初始位置
        LatLng centerPoint = new LatLng(22.2559, 113.541112);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(centerPoint).zoom(17).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.setMapStatus(mMapStatusUpdate);

        //添加标记点
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.book_icon);
        MarkerOptions markerOption = new MarkerOptions().icon(bitmap).position(centerPoint);
        Marker marker = (Marker) baiduMap.addOverlay(markerOption);

        //添加文字
        OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(50)
                .fontColor(0xFFFF00FF).text("暨南大学珠海").rotate(0).position(centerPoint);
        baiduMap.addOverlay(textOption);

        //响应事件
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                Toast.makeText(getContext(), "Marker被点击了！", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        final ShopLoader shopLoader = new ShopLoader();
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                drawShops(shopLoader.getShops());
            }
        };
        shopLoader.load(handler, "http://file.nidama.net/class/mobile_develop/data/bookstore.json");
        return view;
    }

    void drawShops(ArrayList<Shop> shops) {
        if (mapView == null) return;
        BaiduMap mBaidumap = mapView.getMap();
        for (int i = 0; i < shops.size(); i++) {
            Shop shop = shops.get(i);

            LatLng cenpt = new LatLng(shop.getLatitude(), shop.getLongitude());//设定中心点坐标

            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.book_icon);
            //准备 marker option 添加 marker 使用
            MarkerOptions markerOption = new MarkerOptions().icon(bitmap).position(cenpt);
            //获取添加的 marker 这样便于后续的操作
            Marker marker = (Marker) mBaidumap.addOverlay(markerOption);

            OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(50).fontColor(0xFFFF00FF).text(shop.getName()).rotate(0).position(cenpt);
            mBaidumap.addOverlay(textOption);
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) mapView.onDestroy();
    }
}
