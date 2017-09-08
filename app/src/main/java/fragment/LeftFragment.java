package fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.bwie.action.MainActivity;
import com.bwie.action.R;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by DELL on 2017/8/30.
 */

public class LeftFragment extends Fragment implements View.OnClickListener {


    private View view;
    private ImageView night;
    private Switch tuisong;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.left_menu_content,container, false);
        }
            return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    private void initView() {
        night = view.findViewById(R.id.night);
        night.setOnClickListener(this);




    }


    @Override
    public void onClick(View view) {

        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if(mode==Configuration.UI_MODE_NIGHT_YES)
        {

            ((MainActivity) getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }
        else if(mode== Configuration.UI_MODE_NIGHT_NO)
        {
            ((MainActivity) getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }

    }
}

