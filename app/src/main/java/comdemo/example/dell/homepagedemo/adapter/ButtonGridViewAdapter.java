package comdemo.example.dell.homepagedemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.beans.Subcategories;

public class ButtonGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Subcategories> mList;
    private LayoutInflater inflater;
    private String selectClassCont;


    public ButtonGridViewAdapter(Context mContext, List<Subcategories> mList, String selectClassCont) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
        this.selectClassCont = selectClassCont;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.tag, parent,false);
        TextView tv = (TextView) convertView.findViewById(R.id.tv_name);
        tv.setText(mList.get(position).getName());
        //设置选中的按钮
        if(mList.get(position).getName().equals(selectClassCont)){
                tv.setTextColor(Color.parseColor("#ffff8f43"));
                tv.setBackgroundResource(R.drawable.item);
        }

        return convertView;
    }



}
