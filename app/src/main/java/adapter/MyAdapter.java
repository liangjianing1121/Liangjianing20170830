package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.action.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import bean.News2;

/**
 * Created by DELL on 2017/8/30.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<News2> list;
    private final int atype=0;
    private final int btype=1;

    public MyAdapter(Context context, List<News2> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public int getItemViewType(int position) {
        if(position%2==0)
        {
            return atype;
        }
        else
        {
            return btype;
        }

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder1 holder1=null;
        ViewHolder2 holder2=null;
        int type = getItemViewType(i);
        if(view==null) {

            switch (type)
            {

                case atype:
                    holder1=new ViewHolder1();
                    view = View.inflate(context, R.layout.lv_item1, null);
                    holder1.tv_title= view.findViewById(R.id.tv_title);
                    holder1.tv_date= view.findViewById(R.id.tv_date);
                    holder1.tv_name= view.findViewById(R.id.tv_name);
                    holder1.iv=view.findViewById(R.id.iv);
                    view.setTag(holder1);

                    break;
                case btype:

                    holder2=new ViewHolder2();
                    view = View.inflate(context, R.layout.lv_item2, null);
                    holder2.tv_title= view.findViewById(R.id.tv_title);
                    holder2.tv_date= view.findViewById(R.id.tv_date);
                    holder2.tv_name= view.findViewById(R.id.tv_name);
                    holder2.iv=view.findViewById(R.id.iv);
                    view.setTag(holder2);
                    break;

            }


        }
        else{
            switch (type){
                case  atype:
                    holder1= (ViewHolder1) view.getTag();
                    break;
                case btype:
                    holder2= (ViewHolder2) view.getTag();
                    break;
            }

        }


        switch (type)
        {


            case atype:
                holder1.tv_title.setText(list.get(i).getTitle());
                holder1.tv_date.setText(list.get(i).getDate());
                holder1.tv_name.setText(list.get(i).getAuthor_name());
                ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),holder1.iv);

                break;
            case btype:

                holder2.tv_title.setText(list.get(i).getTitle());
                holder2.tv_date.setText(list.get(i).getDate());
                holder2.tv_name.setText(list.get(i).getAuthor_name());
                ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),holder2.iv);
                break;
        }
        return view;
    }
    class ViewHolder1{

        TextView tv_title,tv_date,tv_name;
        ImageView iv;

    }
    class ViewHolder2{
        TextView tv_title,tv_date,tv_name;
        ImageView iv;


    }
}
