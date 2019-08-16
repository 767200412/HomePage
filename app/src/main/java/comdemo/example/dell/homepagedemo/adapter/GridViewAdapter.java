package comdemo.example.dell.homepagedemo.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.beans.Images;
import comdemo.example.dell.homepagedemo.ui.utils.ShowImageActivity;

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Images> mItemList;
    private Drawable error;
    private LayoutInflater layoutInflater;
    private ArrayList<String> urls = new ArrayList<String>();

    public GridViewAdapter(Context context, List<Images> entityList){
        this.mContext = context;
        this.mItemList = entityList;
        this.error = context.getDrawable(R.mipmap.ic_placeholder_fail);
        layoutInflater = LayoutInflater.from(context);
        for(Images image:entityList){
            if(image != null&& !image.getImgUri().equals(null)) {
                urls.add(image.getImgUri());
            }
        }
    }

    public void appendData(List<Images> data){
        if (data != null && !data.isEmpty()) {

            this.mItemList.addAll(data);

            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = layoutInflater.inflate(R.layout.image_show_gridview,null);
        ImageView iv = (ImageView) v.findViewById(R.id.image_show);
        Glide.with(mContext)
                .load(mItemList.get(position).getImgUri())
                .apply(new RequestOptions().error(error))
                .into(iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ShowImageActivity.class);
                intent.putStringArrayListExtra("url", urls);
                intent.putExtra("position",mItemList.get(position).getImgUri());
                v.getContext().startActivity(
                        intent,
                        // 注意这里的sharedView
                        // Content，View（动画作用view），String（和XML一样）
                        ActivityOptions.makeSceneTransitionAnimation(scanForActivity(mContext), v, "sharedView").toBundle());
            }
        });
        return v;
    }



    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity)cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)cont).getBaseContext());

        return null;
    }


}
