package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.action.R;

import java.util.ArrayList;
import java.util.List;

import adapter.MyAdapter;

/**
 * Created by DELL on 2017/8/31.
 */

public class HorizontalScrollViewMenu extends LinearLayout implements ViewPager.OnPageChangeListener {

    private Context context;
    private HorizontalScrollView hScrollView;
    private LinearLayout mLinear;
    private ViewPager vp;
    private List<String> list;
    private List<Fragment> fragmentList;
    private int count;
    private List<TextView> topViews;

    public HorizontalScrollViewMenu(Context context) {
        this(context,null);
    }

    public HorizontalScrollViewMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalScrollViewMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalScrollViewMenu);

        typedArray.recycle();
        intView();

    }

    private void intView() {


        View view = LayoutInflater.from(context).inflate(R.layout.horizontal_scroll_tabhost, this, true);

        hScrollView = view.findViewById(R.id.horiaontal_scorll);
        mLinear = view.findViewById(R.id.mlineralayout);
        vp = view.findViewById(R.id.vp);
        vp.addOnPageChangeListener(this);
    }

    public void display(List<String> list, List<Fragment> fragments){

        this.list=list;
        this.fragmentList=fragments;
        this.count=list.size();
        topViews = new ArrayList<>(count);
        drawUi();
    }
    private void drawUi() {

        drawHscrollView();
        drawviewpager();
    }

    private void drawviewpager() {


        MyAdapter adapter=new MyAdapter(((FragmentActivity)context).getSupportFragmentManager() );

        vp.setAdapter(adapter);
    }

    private void drawHscrollView() {

        //mLinear.setBackgroundColor(Color.rgb(213,230,246));

            for (int i = 0; i < count; i++) {
                String s = list.get(i);
                TextView tv = (TextView) View.inflate(context, R.layout.top_tv, null);
                tv.setText(s);

                //textView的点击事件
                final int finalI = i;
                tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp.setCurrentItem(finalI);
                    }
                });
                mLinear.addView(tv);
                topViews.add(tv);
            }
            topViews.get(0).setSelected(true);
    }



    public void update(){
        mLinear.removeAllViews();


    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
if(mLinear!=null&mLinear.getChildCount()>0) {

    for (int i = 0; i < mLinear.getChildCount(); i++) {

        if (position == i) {
            mLinear.getChildAt(i).setSelected(true);
        } else {
            mLinear.getChildAt(i).setSelected(false);
        }
    }
}
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


}
