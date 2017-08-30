package com.bwie.action;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import fragment.LeftFragment;
import fragment.RightFragment;

@ContentView(R.layout.activity_main)
public class MainActivity extends SlidingFragmentActivity {

    private String url="http://v.juhe.cn/toutiao/index";
    @ViewInject(R.id.lv) ListView lv;
    private List<News2> list;
    private MyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        initData();
        initMenu();
    }

    private void initMenu() {
        //添加左菜单
        setBehindContentView(R.layout.fragment1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment1,new LeftFragment()).commit();


        SlidingMenu menu=getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindOffsetRes(R.dimen.BehindOffsetRes);

        //设置右菜单
        menu.setSecondaryMenu(R.layout.fragment2);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment2,new RightFragment()).commit();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        list = new ArrayList<>();
        adapter = new MyAdapter(this, list);
        lv.setAdapter(adapter);
        RequestParams params=new RequestParams(url);
        params.addQueryStringParameter("key","22a108244dbb8d1f49967cd74a0c144d");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                parseData(result);
                setData();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 更新数据
     */
    private void setData() {
        if(adapter==null)
        {
            adapter=new MyAdapter(this,list);
            lv.setAdapter(adapter);
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 解析数据
     * @param result
     */
    private void parseData(String result) {

        Gson gson=new Gson();
        News news = gson.fromJson(result, News.class);
        List<News.ResultBean.DataBean> data = news.result.data;
        for (int i = 0; i < data.size(); i++) {
            News.ResultBean.DataBean dataBean = data.get(i);
            String author_name = dataBean.author_name;
            String title = dataBean.title;
            String date = dataBean.date;
            String thumbnail_pic_s = dataBean.thumbnail_pic_s;
            News2 news2=new News2(title,date,author_name,thumbnail_pic_s);
            list.add(news2);
        }


    }
}
