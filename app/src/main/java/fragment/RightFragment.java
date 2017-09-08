package fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.bwie.action.OfflineActivity;
import com.bwie.action.R;

import cn.jpush.android.api.JPushInterface;
import utils.NetWorkInfoUtils;

/**
 * Created by DELL on 2017/8/30.
 */

public class RightFragment extends Fragment implements View.OnClickListener {


    private View view;
    private RelativeLayout lixian;
    private RelativeLayout wifi;
    private Switch tuisong;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null)
        {

            view = inflater.inflate(R.layout.right_menu_content, container, false);
        }
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        loadNewsData();





    }

    private void loadNewsData() {

        new NetWorkInfoUtils().verify(getActivity(), new NetWorkInfoUtils.NetWork() {
            @Override
            public void netWifiVisible() {

            }

            @Override
            public void netUnVisible() {

            }

            @Override
            public void netmobileVisible() {

            }
        });

    }

    private void initView() {
        lixian = view.findViewById(R.id.lixian);
        wifi = view.findViewById(R.id.wifi);
        tuisong = view.findViewById(R.id.tuisong);

        wifi.setOnClickListener(this);
        lixian.setOnClickListener(this);

        /**
         * 推送开关点击事件
         */
        tuisong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    Toast.makeText(getActivity(), "推送已打开", Toast.LENGTH_SHORT).show();
                    JPushInterface.resumePush(getActivity());

                }
                else
                {
                    Toast.makeText(getActivity(), "推送已关闭", Toast.LENGTH_SHORT).show();
                    JPushInterface.stopPush(getActivity());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.lixian:
                Intent intent=new Intent(getActivity(), OfflineActivity.class);
                startActivity(intent);
                break;

            case R.id.wifi:


                new AlertDialog.Builder(getActivity()).setSingleChoiceItems(new String[]{"大图下载", "无图下载"}, 0, new DialogInterface.OnClickListener() {

                    private SharedPreferences.Editor edit;

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sp = getActivity().getSharedPreferences("con", Context.MODE_PRIVATE);
                        edit = sp.edit();
                        if(i==0)
                        {
                            // TODO: 2017/9/5 大图下载  WiFi下载
                            edit.putBoolean("pic",true);
                        }
                        else if(i==1)
                        {
                            // TODO: 2017/9/5  无图下载  3G/4G下载
                            edit.putBoolean("pic",false);
                        }

                        edit.commit();
                        dialogInterface.dismiss();
                    }
                }).show();

                break;

        }

    }
}
