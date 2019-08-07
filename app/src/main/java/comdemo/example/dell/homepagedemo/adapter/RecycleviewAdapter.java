package comdemo.example.dell.homepagedemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import comdemo.example.dell.homepagedemo.Beans.Items;
import comdemo.example.dell.homepagedemo.R;

public class RecycleviewAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Items> mItemList;

    public RecycleviewAdapter (Context context, List<Items> entityList){
        this.mContext = context;
        this.mItemList = entityList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.company, parent, false);
        return new RecycleviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Items item = mItemList.get(position);
        ((RecycleviewViewHolder)holder).tv_companyName.setText(item.getBrandName());
        String adress;
        adress = item.getResidentProvince()+"-" + item.getResidentCity();
        ((RecycleviewViewHolder)holder).tv_address.setText(adress);
        String mianproduct = "主营:"+item.getJoinCategoriesHangyeCat();
        ((RecycleviewViewHolder)holder).tv_mianproduct.setText(mianproduct);
        Glide.with(mContext).load(item.getFcRecommendVisualizationSquareImgUrl()).into(((RecycleviewViewHolder)holder).imageView1);
        Glide.with(mContext).load(item.getLogoUrl()).into(((RecycleviewViewHolder)holder).imageView2);

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    private class RecycleviewViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_companyName;//公司名字
        private TextView tv_address;//地址
        private ImageView imageView1,imageView2;//大小2个图标
        private TextView tv_mianproduct;//主营
        private TextView tv_collect;//收藏
        private TextView tv_negotiation;//洽谈
        private TextView tv_officialWebsite;//官网

        public RecycleviewViewHolder(View itemView) {
            super(itemView);
            tv_companyName = (TextView) itemView.findViewById(R.id.tv_companyName);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_mianproduct = (TextView) itemView.findViewById(R.id.tv_mianproduct);
            tv_collect = (TextView) itemView.findViewById(R.id.tv_collect);
            tv_negotiation = (TextView) itemView.findViewById(R.id. tv_negotiation);
            tv_officialWebsite = (TextView) itemView.findViewById(R.id.tv_officialWebsite);
            imageView1 = (ImageView)itemView.findViewById(R.id.image1);//大图标
            imageView2 = (ImageView)itemView.findViewById(R.id.imageView);//小图标

        }
    }
}
