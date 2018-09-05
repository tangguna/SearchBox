package com.tangguna.searchbox.library.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tangguna.searchbox.library.R;

import java.util.ArrayList;

/**
 * ListView列表展示历史记录
 */
public class HistoryDataListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list = new ArrayList<String>();

    public HistoryDataListViewAdapter(Context context, ArrayList<String> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = View.inflate(context,R.layout.list_item,null);
            holder.tv_info = (TextView)view.findViewById(R.id.tv_info);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_info.setText(list.get(i));
        return view;
    }

    class ViewHolder{
        TextView tv_info;
    }
}
