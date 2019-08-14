package comdemo.example.dell.homepagedemo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.beans.Company;
import comdemo.example.dell.homepagedemo.beans.Images;
import comdemo.example.dell.homepagedemo.beans.Product;
import comdemo.example.dell.homepagedemo.beans.Types;

public class Product_buyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Product> mItemList;

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


    public Product_buyAdapter(Context context, List<Product> entityList){
        this.mContext = context;
        this.mItemList = entityList;
    }

    public void appendData(List<Product> data){
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
            View view = LayoutInflater.from(mContext).inflate(R.layout.product_buy, parent, false);
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
            RecycleviewViewHolder recycleviewViewHolder = (RecycleviewViewHolder)holder;
            Product product = mItemList.get(position);
            Company company = product.getCompany();
            RequestOptions requestOptions = new RequestOptions()
                    .circleCrop()
                    .error(recycleviewViewHolder.error);
            //显示小型的圆形头像
            Glide.with(mContext)
                    .load(company.getLogoUrl())
                    .apply(requestOptions)
                    .into(recycleviewViewHolder.image_icon);
            //求购人的名字
            recycleviewViewHolder.tv_name.setText(company.getName());
            //右上角的发布时间
            recycleviewViewHolder.tv_time.setText(product.getPublishTime().substring(0,9));
            //求购描述
            String content =null;
            content = product.getDescription();
            if(content != null) {
                recycleviewViewHolder.tv_title.setText(product.getDescription());
            }
            else {
                recycleviewViewHolder.tv_title.setText("");
            }
            //求购的类型
            List<Types> tagsList = product.getTypes();
            recycleviewViewHolder.Customized.setVisibility(View.INVISIBLE);
            recycleviewViewHolder.posts.setVisibility(View.INVISIBLE);
            recycleviewViewHolder.promotion.setVisibility(View.INVISIBLE);
            recycleviewViewHolder.sample.setVisibility(View.INVISIBLE);
            TextView[] textViews = new TextView[]{
                    recycleviewViewHolder.Customized,
                    recycleviewViewHolder.posts,
                    recycleviewViewHolder.promotion,
                    recycleviewViewHolder.sample};
            int Length = tagsList.size();
            for(int i = 0 ; i<Length;i++){
                textViews[i].setVisibility(View.VISIBLE);
            }
            int a = 0;
            for(Types type:tagsList){
                textViews[a].setText(type.getName());
                a++;
            }

            //图片展示
            List<Images> images = product.getImages();

                for(Images image:images){
                    recycleviewViewHolder.Lsview = LayoutInflater.from(mContext).inflate(R.layout.image_show, null);
                    recycleviewViewHolder.image_show = ((RecycleviewViewHolder) holder).Lsview.findViewById(R.id.image_show);
                    if(images != null){
                    String url = image.getImgUri();
                    Glide.with(mContext)
                            .load(url)
                            .apply(new RequestOptions().error(recycleviewViewHolder.error))
                            .into(((RecycleviewViewHolder) holder).image_show);
                        recycleviewViewHolder.linearLayout.addView(recycleviewViewHolder.Lsview);
                }
            }


            //需要的数量
            String number = "数量:"+product.getQtyUnit();
            recycleviewViewHolder.qtyUnit.setText(number);
            //交货时间
            String dtime = "交货时间:"+ product.getDeliveryTime().substring(0,9);
            recycleviewViewHolder.deliveryTime.setText(dtime);


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

        private TextView tv_name;//名字
        private ImageView image_icon;//图标
        private TextView tv_type;//发布人类型
        private TextView tv_time;//发布时间
        private TextView collection;//是否收藏
        private ImageView image_collection;//是否收藏图标
        private TextView tv_title;//求购的标题
        private TextView qtyUnit;//需要数量
        private TextView deliveryTime;//交货时间
        private TextView Customized;//定制
        private TextView posts;//现货
        private TextView promotion;//促销
        private TextView sample;//样品
        private LinearLayout linearLayout;
        private View Lsview;
        private ImageView image_show;
        private Drawable error;




        public RecycleviewViewHolder(View itemView) {
            super(itemView);
            error = itemView.getResources().getDrawable(R.mipmap.ic_placeholder_fail);
            tv_title = (TextView)itemView.findViewById(R.id.tv_title);
           tv_name = (TextView)itemView.findViewById(R.id.tv_name);
           image_icon = (ImageView)itemView.findViewById(R.id.image_icon);
           tv_type = (TextView)itemView.findViewById(R.id.tv_type);
           tv_time = (TextView)itemView.findViewById(R.id.tv_time);
           collection = (TextView)itemView.findViewById(R.id.collection);
           image_collection = (ImageView)itemView.findViewById(R.id.image_collection);
           Customized = (TextView)itemView.findViewById(R.id.Customized);
           posts = (TextView)itemView.findViewById(R.id.posts);
           promotion = (TextView)itemView.findViewById(R.id.promotion);
           sample = (TextView)itemView.findViewById(R.id.sample);
           linearLayout = (LinearLayout)itemView.findViewById(R.id.liner);
           qtyUnit = (TextView)itemView.findViewById(R.id.qtyUnit);
           deliveryTime = (TextView)itemView.findViewById(R.id.deliveryTime);
           Lsview = LayoutInflater.from(mContext).inflate(R.layout.image_show, null);
           image_show = Lsview.findViewById(R.id.image_show);

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
