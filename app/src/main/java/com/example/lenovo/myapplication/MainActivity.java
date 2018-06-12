package com.example.lenovo.myapplication;




import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends FragmentActivity {
    private follow_fragment f_a;
    private discovery_fragment f_b;
    private nearby_fragment f_c;
    private Fragment[] mFragments;
    private int mIndex;
    private RadioGroup radioGroup;
    private View view;
    TextView qianming;
    TextView qiandao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDate();
        initView();
        initFragment();//方法一，默认第一fragment

    }
    private void initDate(){
        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("src",R.drawable.service);
        map.put("name","联系客服");

        Map<String,Object> map1=new HashMap<>();
        map1.put("src",R.drawable.shoucang);
        map1.put("name","收藏");

        Map<String,Object> map4=new HashMap<>();
        map4.put("src",R.drawable.message_center);
        map4.put("name","消息中心");

        Map<String,Object> map6=new HashMap<>();
        map6.put("src",R.drawable.setting);
        map6.put("name","设置");



        list.add(map1);
        list.add(map4);
        list.add(map6);

        list.add(map);
        MenuAdapter adapter=new MenuAdapter(
                MainActivity.this,
                R.layout.list_item_layout,list
        );
        ListView listView= findViewById(R.id.lv);
        listView.setAdapter(adapter);



        // 点击头像修改个人信息
        ImageView head_image = findViewById(R.id.image_user);
        head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UpdateActivity.class);

                startActivityForResult(intent,0);
            }
        });
        qianming = findViewById(R.id.qianming_user);
        qiandao = findViewById(R.id.qiandao_date);
        TextView name =findViewById(R.id.name_user);
        //获取用户姓名和个人信息
        Glide.with(MainActivity.this)
                .load(Data.urlImage+Data.user.getImage())
                .into(head_image);


        name.setText(Data.user.getName());

        qianming.setText(Data.user.getQianming());
        qiandao.setText(Data.user.getQiandao()+"天");
        Button qiandaoButton = findViewById(R.id.qiandao_user);
        qiandaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long day = (new Date().getTime()-Data.user.getQiandao_date().getTime())/(1000*60*60*24);
                Log.d("时间差：", ""+day);
                if (day>1){
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Data.url + "UserQianDao?user_id="+Data.user.getId())
                            .build();
                    Call call = okHttpClient.newCall(request);
                    try {
                        call.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Data.user.setQiandao(Data.user.getQiandao()+1);
                    Data.user.setQiandao_date(new Date());
                }else {
                    Toast.makeText(MainActivity.this,"请明天再来！",Toast.LENGTH_SHORT).show();
                }
                qiandao.setText(Data.user.getQiandao()+"天");
            }
        });



    }



    private void initView() {
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int arg1) {
                //遍历RadioGroup 里面所有的子控件。
                for (int index = 0; index < group.getChildCount(); index++) {
                    //获取到指定位置的RadioButton
                    RadioButton rb = (RadioButton)group.getChildAt(index);
                    //如果被选中
                    if (rb.isChecked()) {
                        setIndexSelected(index);
                        //setIndexSelectedTwo(index);  //方法二
                        break;
                    }
                }

            }
        });

    }
    //方法一，默认第一fragment
    private void initFragment() {
        f_a =new follow_fragment();
        f_b =new discovery_fragment();
        f_c =new nearby_fragment();

        //添加到数组
        mFragments = new Fragment[]{f_a,f_b,f_c};
        //开启事务
        FragmentManager    fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft= fragmentManager.beginTransaction();
        //添加首页
        ft.add(R.id.content,f_a).commit();
        //默认设置为第0个
        setIndexSelected(0);
    }
    //方法一，选中显示与隐藏
    private void setIndexSelected(int index) {

        if(mIndex==index){
            return;
        }
        FragmentManager    fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft= fragmentManager.beginTransaction();

        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if(!mFragments[index].isAdded()){
            ft.add(R.id.content,mFragments[index]).show(mFragments[index]);
        }else {
            ft.show(mFragments[index]);
        }

        ft.commit();
        //再次赋值
        mIndex=index;

    }
    //方法二，选中替换
    private void setIndexSelectedTwo(int index) {
        switch (index) {
            case 0:
                changeFragment(new follow_fragment().getFragmentA());
                break;
            case 1:
                changeFragment(new discovery_fragment().getFragmentB());
                break;
            case 2:
                changeFragment(new nearby_fragment().getFragmentC());
                break;



            default:
                break;
        }
    }


    ////方法二，默认第一fragment
    private void changeFragment(Fragment fm) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.content, fm);
        transaction.commit();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        qianming.setText(Data.user.getQianming());
    }

}

