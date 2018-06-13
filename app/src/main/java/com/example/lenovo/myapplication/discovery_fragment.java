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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dingin on 2018/3/19.
 */

public class discovery_fragment extends Fragment  {





    private Button checkgoodbtn;
    private List <String> images = new ArrayList<String>();
    private ListView listView;
    private Gson gson;
    private String businessesListStr;
    private Type type;
    private List<Business> businessList;
    private List<Goods> goodsList;
    private String goodsListStr;
    private ImageView img_menu1;
    private DrawerLayout mdrawerlayout;
    private String name;



//fragment

    private View view;
    // 单例(方法二)
    private static discovery_fragment fb;
    public static discovery_fragment getFragmentB() {
        if (fb == null) {
            fb = new discovery_fragment();
        }
        return fb;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_discovery,container,false);

        //侧边栏点击事件
        img_menu1 = view.findViewById(R.id.btn_menu1);
        mdrawerlayout = view.findViewById(R.id.drawerlayout);
        img_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdrawerlayout.openDrawer(Gravity.LEFT);
            }
        });

        //初始化
        images.add(Data.url+"lunbotu/lunbotu1.jpg");
        images.add(Data.url+"lunbotu/lunbotu2.jpg");
        images.add(Data.url+"lunbotu/lunbotu3.jpg");
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

        EditText etSousuo = view.findViewById(R.id.etSousuo);
        etSousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),sousuoActivity.class);
                startActivityForResult(intent,0);
            }
        });

        //获取商家
        listView =view.findViewById(R.id.lv_discovery_list);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url+"Faxian_index")
                .build();
        final Call call = okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            businessesListStr = response.body().string();
            gson = new Gson();
            type = new TypeToken<List<Business>>(){}.getType();
            businessList = gson.fromJson(businessesListStr,type);

        } catch (IOException e) {
            e.printStackTrace();
        }
        DiscoveryAdapter discoveryAdapter = new DiscoveryAdapter(getActivity(),
                businessList);
        listView.setAdapter(discoveryAdapter);
          return view;

       }



}

