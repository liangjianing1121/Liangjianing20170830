package adapter;


import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.action.R;

import org.w3c.dom.Text;

import java.util.List;

import bean.Catogray;

/**
 * Created by DELL on 2017/9/5.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{


    private Context context;
    private List<Catogray> list;
    private OnItemClickListener onItemClickListener;

    public RecyclerAdapter(Context context, List<Catogray> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 创建viewholder 和view绑定  和BaseAdapter 中的settag相似
     * @param parent
     * @param viewType
     * @return
     */

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.lv_item, null);


        MyViewHolder myViewHolder=new MyViewHolder(view);



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemClickListener((Integer) view.getTag(),view);

            }
        });


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv.setText(list.get(position).name);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{


        private final TextView tv;
        private final CheckBox checkbox;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv);
            checkbox = itemView.findViewById(R.id.check);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface  OnItemClickListener{
        void OnItemClickListener(int pos,View view);
    }
}
