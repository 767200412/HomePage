package comdemo.example.dell.homepagedemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import comdemo.example.dell.homepagedemo.beans.Items;
import comdemo.example.dell.homepagedemo.R;

public class RecycleviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Items> mItemList;

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;


    public RecycleviewAdapter (Context context, List<Items> entityList){
        this.mContext = context;
        this.mItemList = entityList;
    }

    public void appendData(List<Items> data){
        if (data != null && !data.isEmpty()) {

            this.mItemList.addAll(data);

            notifyDataSetChanged();
        }
    }
    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.company, parent, false);
            return new RecycleviewViewHolder(view);
        }
        else if(viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_refresh_footer, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecycleviewViewHolder) {
            Items item = mItemList.get(position);
            ((RecycleviewViewHolder) holder).tv_companyName.setText(item.getBrandName());
            String adress;
            adress = item.getResidentProvince() + "-" + item.getResidentCity();
            ((RecycleviewViewHolder) holder).tv_address.setText(adress);
            String mianproduct = "主营:" + item.getJoinCategoriesHangyeCat();
            ((RecycleviewViewHolder) holder).tv_mianproduct.setText(mianproduct);
            if(item.getFcRecommendVisualizationSquareImgUrl() != null){
                Glide.with(mContext).load(item.getFcRecommendVisualizationSquareImgUrl()).into(((RecycleviewViewHolder) holder).imageView1);
            }
            if(item.getLogoUrl() != null) {
                Glide.with(mContext).load(item.getLogoUrl()).into(((RecycleviewViewHolder) holder).imageView2);
            }
        }
        else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState) {
                case LOADING: // 正在加载
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_END: // 加载到底
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size()+1;
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

    private class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }
}
