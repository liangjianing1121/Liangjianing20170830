package com.bwie.action;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerAdapter;
import bean.Catogray;
import bean.NetNews;
import dao.NewsDao;

public class OfflineActivity extends AppCompatActivity  {

    private RecyclerView lv;
    private Button download;
    private List<Catogray> list;
    private String url="http://v.juhe.cn/toutiao/index";
    private String key="22a108244dbb8d1f49967cd74a0c144d";
    private NewsDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        initView();
        initData();
    }

    private void initData() {

        Catogray c=new Catogray();
        c.type="top";
        c.name="头条";
        list.add(c);
        c=new Catogray();
        c.type="shehui";
        c.name="社会";
        list.add(c);
        c=new Catogray();
        c.type="guonei";
        c.name="国内";
        list.add(c);
        c=new Catogray();
        c.type="guoji";
        c.name="国际";
        list.add(c);c=new Catogray();
        c.type="yule";
        c.name="娱乐";
        list.add(c);
        c=new Catogray();
        c.type="tiyu";
        c.name="体育";
        list.add(c);
        c=new Catogray();
        c.type="junshi";
        c.name="军事";
        list.add(c);
        c=new Catogray();
        c.type="keji";
        c.name="科技";
        list.add(c);
        c=new Catogray();
        c.type="caijing";
        c.name="财经";
        list.add(c);
        c=new Catogray();
        c.type="shishang";
        c.name="时尚";
        list.add(c);


        RecyclerAdapter adapter=new RecyclerAdapter(this,list);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int pos, View view) {
                CheckBox checkbox = view.findViewById(R.id.check);
                Catogray c= list.get(pos);

                if(checkbox.isChecked())
                { 
                    checkbox.setChecked(false);
                    c.state=false;
                }
                else
                {
                    checkbox.setChecked(true);
                    c.state=true; 
                }
                list.set(pos,c);
            }
        });
        dao = new NewsDao(this);
    }
    private void initView() {
        lv = (RecyclerView) findViewById(R.id.lv);
        list = new ArrayList<>();
        download = (Button) findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list!=null&&list.size()>0)
                {
                    for (Catogray catogray : list) {
                        if(catogray.state)//判断选中
                        {
                            loadData(catogray.type);
                        }
                    }
                }
                for (Catogray catogray : list) {
                    System.out.println("state============"+catogray.state);
                }
                Toast.makeText(OfflineActivity.this, "数据已下载", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private void loadData(final String type) {
        RequestParams params=new RequestParams(url);
        params.addParameter("key",key);
        params.addParameter("type",type);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                dao.add(type,result);
               // NetNews select = dao.select();
                //Toast.makeText(OfflineActivity.this, select, Toast.LENGTH_SHORT).show();
                //System.out.println(select);
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





}
