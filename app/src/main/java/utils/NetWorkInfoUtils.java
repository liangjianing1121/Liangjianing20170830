package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import java.util.Map;

/**
 * Created by DELL on 2017/9/5.
 */

public class NetWorkInfoUtils {


    private Context context;
    private ConnectivityManager manager;
    private NetWork netWork;


    public  void verify(Context context,NetWork network){


        this.context=context;
        //网络连接管理器
        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //网络可用对象
        NetworkInfo info = manager.getActiveNetworkInfo();

        if(info!=null)
        {
            //判断网络对象为手机网络时
            if(info.getType()==ConnectivityManager.TYPE_MOBILE)
            {

                //不让加载图片
                network.netmobileVisible();
            }

            else if(info.getType()==ConnectivityManager.TYPE_WIFI)
            {
                network.netWifiVisible();
            }
            else
            {
                network.netUnVisible();
            }
        }
        else
        {
            network.netUnVisible();
        }
    }
    public void  NetWorkInfoUtils(NetWork netWork) {
        this.netWork = netWork;
    }
    public interface NetWork{


        //wifi网络时
         void netWifiVisible();
        //无网络时
        void netUnVisible();
        //手机网络时
        void netmobileVisible();


    }

}
