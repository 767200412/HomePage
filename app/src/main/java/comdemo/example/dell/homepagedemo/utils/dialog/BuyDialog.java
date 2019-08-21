package comdemo.example.dell.homepagedemo.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.SpannableString;
import android.text.style.LeadingMarginSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.adapter.GridViewAdapter;
import comdemo.example.dell.homepagedemo.beans.Company;
import comdemo.example.dell.homepagedemo.beans.Images;
import comdemo.example.dell.homepagedemo.beans.Product;
import comdemo.example.dell.homepagedemo.beans.Types;

/**
 * 创建自定义的Dialog，主要学习实现原理
 * Created by admin on 2019/7/16.
 */

public class BuyDialog extends Dialog {
    private ImageView finish;//左上角的叉号
    private ImageView image_icon;//发布人头像
    private TextView tv_name;//发布人名字
    private TextView tv_time;//发布于多久前
    private ImageView image_collection;//收藏的爱心
    private TextView collection;//收藏的文字
    private TextView customized;//定制
    private TextView posts;//现货
    private TextView promotion;//促销
    private TextView sample;//样品
    private TextView title;//发布的内容
    private TextView tv_number;//报价的人数
    private TextView qtyUnit;//需要的数量
    private TextView deliveryTime;//交货时间
    private TextView get;//马上接单按钮按钮
    private GridView show_image;//显示图片
    private View view;
    private ImageView imageView;
    private ArrayList<String> urls = new ArrayList<String>();
    private GridViewAdapter adapter;

    private Drawable error;//h获取不到图片显示的图片

    private Product product;//产品的信息

    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本

    private String getStr;//马上接单文本显示的内容

    private onGetOnclickListener getOnclickListener;//马上接单按钮被点击了的监听器
    private onFinishOnclickListener finishOnclickListener;//马上接单按钮被点击了的监听器

    public BuyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }


    /**
     * 设置数据
     */
     public void setData(Product newProduct){
            this.product = newProduct;
     }

    /**
     * 设置马上接单按钮的监听
     *
     * @param getOnclickListener
     */
    public void setGetOnclickListener(onGetOnclickListener getOnclickListener) {

        this.getOnclickListener = getOnclickListener;
    }

    /**
     * 设置右上角叉号单按钮的监听
     *
     * @param finishOnclickListener
     */
    public void setFinishOnclickListener(onFinishOnclickListener finishOnclickListener) {

        this.finishOnclickListener = finishOnclickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_buy_dialog);
        //空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();

        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        finish = (ImageView) findViewById(R.id.finish);
        image_icon = (ImageView) findViewById(R.id.image_icon);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_time = (TextView) findViewById(R.id.tv_time);
        image_collection = (ImageView) findViewById(R.id.image_collection);
        collection = (TextView) findViewById(R.id.collection);
        customized = (TextView) findViewById(R.id.customized);
        posts = (TextView) findViewById(R.id.posts);
        promotion = (TextView) findViewById(R.id.promotion);
        sample = (TextView) findViewById(R.id.sample);
        title = (TextView) findViewById(R.id.title);
        tv_number = (TextView) findViewById(R.id.tv_number);
        qtyUnit = (TextView) findViewById(R.id.qtyUnit);
        deliveryTime = (TextView) findViewById(R.id.deliveryTime);
        get = (TextView) findViewById(R.id.get);
        show_image = (GridView)findViewById(R.id.dynamic);
        error = getContext().getResources().getDrawable(R.mipmap.ic_placeholder_fail);
        view = LayoutInflater.from(getContext()).inflate(R.layout.image_show_gridview, null);
        imageView = view.findViewById(R.id.image_show);
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        Company company = product.getCompany();

        //显示标签
        List<Types> tagsList = product.getTypes();
        customized.setVisibility(View.GONE);
        posts.setVisibility(View.GONE);
        promotion.setVisibility(View.GONE);
        sample.setVisibility(View.GONE);
        TextView[] textViews = new TextView[]{
                customized,
                posts,
                promotion,
                sample};
        int Length = tagsList.size();
        for(int i = 0 ; i<Length;i++){
            textViews[i].setVisibility(View.VISIBLE);
        }
        int a = 0;
        for(Types type:tagsList){
            textViews[a].setText(type.getName());
            a++;
        }

        if (company.getLogoUrl() != null) {
            //发布人头像
            Glide.with(getContext())
                    .load(company.getLogoUrl())
                    .apply(new RequestOptions().error(error))
                    .into(image_icon);
        }
        if (company.getName() != null) {
            //发布人名字
            tv_name.setText(company.getName());
        }
        if(product.getCreatedTime() !=null){
            //右上角时间
            tv_time.setText(product.getCreatedTime().substring(0,9));
        }
        if (product.getDescription() != null) {
            float mPx = 36 *a;//每个tag的距离 * 显示的tag个数

            //1.先创建SpannableString对象
            SpannableString spannableString = new SpannableString(product.getDescription());
            //2.设置文本缩进的样式，参数arg0，首行缩进的像素，arg1，剩余行缩进的像素,这里我将像素px转换成了手机独立像素dp
            LeadingMarginSpan.Standard what = new LeadingMarginSpan.Standard(dp2px(getContext(), mPx), 0);
            //3.进行样式的设置了,其中参数what是具体样式的实现对象,start则是该样式开始的位置，end对应的是样式结束的位置，参数flags，定义在Spannable中的常量
            spannableString.setSpan(what, 0, spannableString.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);

            title.setText(spannableString);
        }
        if (product.getQtyUnit() != null) {
            //需要的数量
            String number = "需要数量:"+product.getQtyUnit();
            qtyUnit.setText(number);
        }
        if( product.getDeliveryTime()!=null){
            //交货时间
            String dtime = "交货时间:"+ product.getDeliveryTime().substring(0,9);
            deliveryTime.setText(dtime);
        }

        //图片展示
        List<Images> images = product.getImages();
//        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
//
//        for(Images image: images){
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("image",image);
//            lstImageItem.add(map);
//        }
        adapter = new GridViewAdapter(getContext(), images);
        show_image.setAdapter(adapter);

    }

    /**
     * 初始化界面监听
     */
    private void initEvent() {
        //设置右上角按钮被点击后，向外界提供监听
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finishOnclickListener != null) {
                    finishOnclickListener.onFinishClick();
                }
            }
        });
        //设置马上接单按钮被点击后，向外界提供监听
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getOnclickListener != null) {
                    getOnclickListener.onGetClick();
                }
            }
        });
    }


    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public interface onFinishOnclickListener {
        public void onFinishClick();
    }

    public interface onGetOnclickListener {
        public void onGetClick();
    }
}