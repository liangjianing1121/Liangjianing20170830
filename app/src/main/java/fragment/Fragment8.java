package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bwie.action.R;
import com.bwie.action.XiangQingActivity;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.MyAdapter;
import bean.NetNews;
import bean.News;
import bean.News2;
import dao.NewsDao;
import utils.NetWorkInfoUtils;
import view.xlistview.XListView;

/**
 * Created by DELL on 2017/8/31.
 */

public class Fragment8 extends android.support.v4.app.Fragment implements XListView.IXListViewListener{

    private View view;
    private XListView lv;
    private String url="http://v.juhe.cn/toutiao/index?key=22a108244dbb8d1f49967cd74a0c144d&type=keji";
    private List<News2> list;
    private MyAdapter adapter;
    private NewsDao dao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment3, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
        getData();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), XiangQingActivity.class);
                intent.putExtra("url",list.get(i-1).getUrl());
                startActivity(intent);
            }
        });

        NetWorkInfoUtils netWorkInfoUtils=new NetWorkInfoUtils();
        netWorkInfoUtils.verify(getActivity(), new NetWorkInfoUtils.NetWork() {
            @Override
            public void netWifiVisible() {
                getData();
            }

            @Override
            public void netUnVisible() {


                NetNews select = dao.select("keji");
                String result = select.getResult();
                if(select!=null) {
                    parseData(result);
                    setData();
                }
            }

            @Override
            public void netmobileVisible() {

            }
        });

    }

    private void initData() {

        adapter = new MyAdapter(getActivity(),list);
        lv.setAdapter(adapter);
        dao = new NewsDao(getActivity());
    }
    public void getData() {

        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
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
    private void setData() {
        if(adapter==null){

            adapter=new MyAdapter(getActivity(),list);
            lv.setAdapter(adapter);
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
        lv.stopLoadMore();
        lv.stopRefresh();
    }

    private void parseData(String result) {

        Gson gson=new Gson();
        News news = gson.fromJson(result, News.class);
        List<News.ResultBean.DataBean> data = news.result.data;
        for (int i = 0; i < data.size(); i++) {
            News.ResultBean.DataBean dataBean = data.get(i);
            String title = dataBean.title;
            String author_name = dataBean.author_name;
            String thumbnail_pic_s = dataBean.thumbnail_pic_s;
            String url = dataBean.url;
            String date = dataBean.date;
            News2 news2=new News2(title, date,author_name,thumbnail_pic_s,url);
            list.add(news2);
        }
    }

    private void initView() {
        lv = view.findViewById(R.id.lv);
        list = new ArrayList<>();
        lv.setXListViewListener(this);
        lv.setPullLoadEnable(true);
    }

    @Override
    public void onRefresh() {
        list.clear();
        getData();
    }

    @Override
    public void onLoadMore() {
        getData();

    }

}
