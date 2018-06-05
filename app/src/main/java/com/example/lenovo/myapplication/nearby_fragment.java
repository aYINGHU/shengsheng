package com.example.lenovo.myapplication;

/**
 * Created by lenovo on 2018/5/24.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dingin on 2018/3/19.
 */

public class nearby_fragment extends Fragment {
    private List <String> images = new ArrayList<String>();
    private View view;
    // 单例(方法二)
    private static nearby_fragment fc;
    public static nearby_fragment getFragmentC() {
        if (fc == null) {
            fc = new nearby_fragment();
        }
        return fc;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nearby,container,false);
        //轮播

        //初始化
        images.add(Data.url+"lunbotu/lunbotu1.jpg");
        images.add(Data.url+"lunbotu/lunbotu2.jpg");
        images.add(Data.url+"lunbotu/lunbotu3.jpg");
        // images.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3643251844,1782349203&fm=27&gp=0.jpg");

        final Banner banner = (Banner) view.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Data.url+"lunbotu/lunbotu1/index.html")));
                        break;
                    case 1:
                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(Data.url+"lunbotu/lunbotu2/index.html")));
                        break;
                    case 2:
                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(Data.url+"lunbotu/lunbotu3/index.html")));
                        break;
                    default:
                        break;

                }
            }
        });
        banner.start();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // 准备数据源
        List<Map<String, Object>> dataSource = new ArrayList<>();
        Map<String, Object> itemData1 = new HashMap<>();
        // 定义第1个数据项
        itemData1.put("header", R.drawable.nikelogo);
        itemData1.put("name", "NIKE万达专卖店");
        itemData1.put("price", "$300起");
        itemData1.put("position", "鞋服|裕华区");
        dataSource.add(itemData1);

        itemData1.put("header", R.drawable.nikelogo);
        itemData1.put("name", "NIKE万达专卖店");
        itemData1.put("price", "$300起");
        itemData1.put("position", "鞋服|裕华区");
        dataSource.add(itemData1);

        itemData1.put("header", R.drawable.nikelogo);
        itemData1.put("name", "NIKE万达专卖店");
        itemData1.put("price", "$300起");
        itemData1.put("position", "鞋服|裕华区");
        dataSource.add(itemData1);

        // ListView lv_nearbylist =view.findViewById(R.id.lv_nearby_list);// 获取ListView控件

        NearbyAdapter nearbyAdapter = new NearbyAdapter(// 创建Adapter对象
                getActivity(),       // 上下文环境
                dataSource, // 数据源
                R.layout.nearby_list // 列表项布局文件
        );

        // lv_nearbylist.setAdapter(nearbyAdapter); // 给ListView控件设置适配器
        return view;

    }
}
