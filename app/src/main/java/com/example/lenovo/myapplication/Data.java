package com.example.lenovo.myapplication;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dingin on 2018/5/25.
 */

public class Data extends Application {
    public static String PublicUrl="http://pfcwqv.natappfree.cc";
    public static String PrivateUrl="http://10.7.84.240:8080";
    public static List<Business> businessList_data = new ArrayList<>();
    public static List<Goods> goodsList_data = new ArrayList<>();
    public static String url=PublicUrl+"/sx/";
    public static String urlImage=PublicUrl+"/sx/user/";
    public static String urlLunBoTu = url+"/lunbotu/";
    public static User user =new User();
    public static Business business = new Business();
    public static String BusinessUrlImage=PublicUrl+"/sx/business/";
    public static Goods goods = new Goods();
    public static String sousuoGoods = PublicUrl+"/sx/SouSuoGoods?name=";

    public static String urlImageBusiness=PublicUrl+"/sx/business/";
    public static List<Goods> goodsList=new ArrayList<>();
    public static List<Map<String,Goods>> goodsListm=new ArrayList<>();
    public static List<String> typeList=new ArrayList<>();



    }




