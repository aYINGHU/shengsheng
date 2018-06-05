package com.example.lenovo.myapplication;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {
    private follow_fragment f_a;
    private discovery_fragment f_b;
    private nearby_fragment f_c;
    private mine_fragment f_d;
    private Fragment[] mFragments;
    private int mIndex;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();//方法一，默认第一fragment
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
        f_d =new mine_fragment();
        //添加到数组
        mFragments = new Fragment[]{f_a,f_b,f_c,f_d};
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
            case 3:
                changeFragment(new mine_fragment().getFragmentD());
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

}

