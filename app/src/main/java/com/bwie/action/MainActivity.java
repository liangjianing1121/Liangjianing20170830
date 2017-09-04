package com.bwie.action;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.google.gson.Gson;
import com.kson.slidingmenu.SlidingMenu;
import com.kson.slidingmenu.app.SlidingFragmentActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.MyAdapter;
import bean.News;
import bean.News2;
import fragment.Fragment1;
import fragment.Fragment10;
import fragment.Fragment2;
import fragment.Fragment3;
import fragment.Fragment4;
import fragment.Fragment5;
import fragment.Fragment6;
import fragment.Fragment7;
import fragment.Fragment8;
import fragment.Fragment9;
import fragment.LeftFragment;
import fragment.RightFragment;
import view.HorizontalScrollViewMenu;

@ContentView(R.layout.activity_main)
public class MainActivity extends SlidingFragmentActivity {

    private String url="http://v.juhe.cn/toutiao/index";

    private MyAdapter adapter;
    private List<String> list;
    private List<Fragment> fragments;
    private HorizontalScrollViewMenu tabhost;
    private SlidingMenu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        initData();
        initMenu();
    }
    private void initData() {
        list = new ArrayList<>();
        fragments = new ArrayList<>();
        tabhost = (HorizontalScrollViewMenu) findViewById(R.id.tabhost);

        list.add("头条");
        list.add("社会");
        list.add("国内");
        list.add("国际");
        list.add("娱乐");
        list.add("体育");
        list.add("军事");
        list.add("科技");
        list.add("财经");
        list.add("时尚");

        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());
        fragments.add(new Fragment5());
        fragments.add(new Fragment6());
        fragments.add(new Fragment7());
        fragments.add(new Fragment8());
        fragments.add(new Fragment9());
        fragments.add(new Fragment10());

        tabhost.display(list,fragments);


    }

    private void initMenu() {
        //添加左菜单
        setBehindContentView(R.layout.fragment1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment1,new LeftFragment()).commit();


        menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindOffsetRes(R.dimen.BehindOffsetRes);

        //设置右菜单
        menu.setSecondaryMenu(R.layout.fragment2);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment2,new RightFragment()).commit();

    }

    public void left(View v){
        menu.showMenu();
    }

    public void right(View v){
        menu.showSecondaryMenu();
    }
    public void login(View v){

        Intent intent=new Intent(this,Main2Activity.class);
        startActivity(intent);


    }

}
