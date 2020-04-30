package com.example.ourfirstapp;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseAdapter {
    private List<Map<String,Object>> mData;
    private LayoutInflater  mInflater;

    public MyAdapter(Context context,List<Map<String,Object>> data){
        mInflater=LayoutInflater.from(context);
        mData=data;
    }

    @Override
    public int getCount(){
        return mData.size();
    }

    @Override
    public Object getItem(int position){
        return mData.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //convertView==null;
        //如果为空，需要实例化这个convertView
        //如果不为空可以直接拿来重用
        //convertView!=null；
        //上面无论如何都会得到一个可用的convertView
        //使用这个convertView填充mData位置为position上的数据
        //return convertView;//到此可以满足函数的要求
        ViewHolder holder=null;

        if(convertView==null){
            convertView=mInflater.inflate(R.layout.list_view_item,null);
            holder =new ViewHolder();
            holder.title=convertView.findViewById(R.id.text_view_1);
            convertView.setTag(holder);
        }else{
            holder =(ViewHolder)convertView.getTag();
        }


        Map<String,Object> map=mData.get(position);
        holder.img.setImageResource(Integer.parseInt(map.get("img").toString()));
        holder.title.setText(map.get("title").toString());

        return convertView;
    }

    public final class ViewHolder{//根据条目布局的子控件对应
        public ImageView img;
        public TextView title;
    }
    //根据你的app功能自定义需要的函数
    public void addItem(Map<String,Object> item){
        mData.add(item);
        notifyDataSetChanged();
    }
}
