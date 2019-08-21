package comdemo.example.dell.homepagedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.constant.NumberConstant;

public class PhotoGridViewAdapter extends android.widget.BaseAdapter {
    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;



    public PhotoGridViewAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        //return mList.size() + 1;//因为最后多了一个添加图片的ImageView
        int count = mList == null ? 1 : mList.size() + 1;
            if (count > NumberConstant.MAX_SELECT_PIC_NUM) {
            return mList.size();
        } else {
            return count;
        }
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
        convertView = inflater.inflate(R.layout.grid_item, parent,false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.pic_iv);
        ImageView iv_delete = (ImageView)convertView.findViewById(R.id.iv_delete);
        if (position < mList.size()) {
            //代表+号之前的需要正常显示图片
            String picUrl = mList.get(position); //图片路径
            Glide.with(mContext).load(picUrl).into(iv);
        } else {
            //Log.e("","-------1---------------------------------------------");
            iv.setImageResource(R.mipmap.ic_add_accessory);//最后一个显示加号图片
            iv_delete.setVisibility(View.GONE);
        }

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CallBackData.doCallBackMethed(position);
            }
        });

        return convertView;
    }

    //静态内部类
    public static class CallBackData{
        private static ClickInterface mClickInterface;//定义的接口

        /**
         * 传输数据与被传输数据连接的心脏。在 要接收数据的类里调用*/
        public static void setClickInterface(ClickInterface clickInterface){
            mClickInterface = clickInterface;
        }

        /**
        * 通过这个方法把要传输的数据传出去
        */
        public static void doCallBackMethed(int position){
            mClickInterface.OnListener(position);
        }
    }
}
