package comdemo.example.dell.homepagedemo;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comdemo.example.dell.homepagedemo.Beans.CategoryIdsByOr;
import comdemo.example.dell.homepagedemo.Beans.Data;
import comdemo.example.dell.homepagedemo.Beans.Header;
import comdemo.example.dell.homepagedemo.Beans.Indextop;
import comdemo.example.dell.homepagedemo.Beans.Items;
import comdemo.example.dell.homepagedemo.Beans.Menuitems;
import comdemo.example.dell.homepagedemo.Beans.Pageinfo;
import comdemo.example.dell.homepagedemo.Beans.Platformarticleselected;
import comdemo.example.dell.homepagedemo.Beans.Polymericcompanies;
import comdemo.example.dell.homepagedemo.Beans.Topdata;
import comdemo.example.dell.homepagedemo.adapter.RecycleviewAdapter;
import comdemo.example.dell.homepagedemo.okhttp.listener.DisposeDataListener;
import comdemo.example.dell.homepagedemo.request.RequestCenter;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view,view2;//定义view用来设置fragment的layout
    private Banner banner;
    private MarqueeView marqueeView;
    private HorizontalScrollView hs;
    private LinearLayout ls;
    private TextView tv;
    private ImageView imageView;
    private android.support.design.widget.TabLayout mytab;
    private android.support.v7.widget.RecyclerView mRecyclerView;
    private List<String> mEntityList;//公司列表
    private RequestCenter requestCenter ;
    private Topdata topdata;
    private Data data;
    private comdemo.example.dell.homepagedemo.Beans.Banner banner2;
    private List<Header> headers;
    private Indextop indextop;
    private List<Items> items,items2,items3,items4;
    private Platformarticleselected platformarticleselected;
    private Menuitems menuitems;
    private Polymericcompanies polymericcompanies;
    private Pageinfo pageinfo;

    private CategoryIdsByOr categoryIdsByOr;//用于标签分类的标识
    private String categoryIdsByOrNumber;

    private List images = new ArrayList();//横向滚动广告数据集
    private List<String> messages = new ArrayList<>();//垂直滚动 跑马灯数据集





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        hs = (HorizontalScrollView)view.findViewById(R.id.hs);
        ls = (LinearLayout)view.findViewById(R.id.liner);
        banner = (Banner)view.findViewById(R.id.tv_ad);
        marqueeView = (MarqueeView)view.findViewById(R.id.tv_bottomNews);
        requestCenter = new RequestCenter(getContext());
        mytab = (android.support.design.widget.TabLayout) view.findViewById(R.id.roll_tab);
        mRecyclerView = view.findViewById(R.id.review);
        init();
        initTab();
        initCompany();
        mytab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //添加选中Tab的逻辑
                String title = tab.getText().toString();
                for(Items item:items3){
                     if(item.getTitle().equals(title)){
                         Gson gson = new Gson();
                         categoryIdsByOr =gson.fromJson(item.getActionArgsJson(),CategoryIdsByOr.class);
                         categoryIdsByOrNumber = categoryIdsByOr.getCategoryIdsByOr();
                         Log.e("categoryIdsByOr",categoryIdsByOrNumber);
                         //调用初始化
                         initCompany();
                     }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //添加未选中Tab的逻辑
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
               //再次选中tab的逻辑
            }
        });

        return view;
    }

    //初始上半部数据
    private void init(){
        //获取数据
        final String head =
                "query IndexData {  banner {    __typename    items {      __typename      linkUrl      v600ImgUrl    }  }" +
                        "platformArticleSelected {    __typename    header {      __typename      id      title    }  }  " +
                        "indexTop(oldCategoryId: true) {    __typename    items {      __typename      title      companyId      cover2    }  }}";


        JSONObject pp = new JSONObject();

        JSONObject params_banner = new JSONObject();
        try {
            params_banner.put("query",head);
            params_banner.put("variables",pp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestCenter.GetData(params_banner, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                         String response = null;
                         try {
                              response = responseObj.body().string();
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                         Gson gson = new Gson();
                         topdata = gson.fromJson(response,Topdata.class);
                         data = topdata.getData();
                         banner2 = data.getBanner();
                         items = banner2.getItems();
                         for (Items item:items) {
                             images.add(item.getV600ImgUrl());
                             //Log.e("V600ImgUrl",item.getV600ImgUrl());
                         }
                         showAd();
                         platformarticleselected = data.getPlatformArticleSelected();
                         headers = platformarticleselected.getHeader();
                         for(Header header:headers){
                             messages.add(header.getTitle());
                         }
                         showMarquee();

                         indextop = data.getIndexTop();
                         items2 = indextop.getItems();
                         setHorizontalScrollView();
                       }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    //设置水平滚动 推荐品牌
    public void setHorizontalScrollView(){
        for(Items item:items2) {
            view2 = LayoutInflater.from(getContext()).inflate(R.layout.top_item, null);
            tv = (TextView) view2.findViewById(R.id.top_name);
            imageView = (ImageView)view2.findViewById(R.id.top_image);
            tv.setText(item.getTitle());
            Glide.with(getContext())
                    .load(item.getCover2())
                    .into(imageView);
            ls.addView(view2);

        }
    }
    //显示 最上方 滚动图片广告
    public void showAd(){
        //banner = (Banner)view.findViewById(R.id.tv_ad);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //banner.setBannerTitles(titles);
        //banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        //增加点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(getActivity().getApplicationContext(), "position"+position, Toast.LENGTH_SHORT).show();
            }
        });

    }
    //显示 走马灯 文字广告
    public void showMarquee(){
        marqueeView.startWithList(messages);
        marqueeView.startWithList(messages, R.anim.anim_bottom_in, R.anim.anim_top_out);
    }

    //初始化下方的标签
    public void initTab(){
        String url2 = "query MenusQuery($menuId: ID!) {  menuItems(menuId: $menuId) {    __typename    items {      __typename      id      title      actionCode      description      actionArgsJson      imgs: extProperties(type: \"img\") {        __typename        items {          __typename          key          value        }      }    }  }}";
        JSONObject variables = new JSONObject();
        JSONObject params_banner = new JSONObject();
        try {
            params_banner.put("query",url2);
            variables.put("menuId","Fc6HomeCompanyFilter_f91c2676e6ece61180e3850a1737545e");
            params_banner.put("variables",variables);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        requestCenter.GetData(params_banner, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                String response = null;
                try {
                    response = responseObj.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                topdata = gson.fromJson(response,Topdata.class);
                data = topdata.getData();
                menuitems = data.getMenuItems();
                items3 = menuitems.getItems();
                for(Items item:items3){
                    //设置标签tab
                    mytab.addTab(mytab.newTab().setText(item.getTitle()));
                }
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    //加载公司数据
    public void initCompany(){
        String url3 = "query PolymericCompaniesQuery($args: PolymericCompanyListArgs) {  polymericCompanies(args: $args) {    __typename    pageInfo {      __typename      total      skip      take    }    items {      __typename      customSiteUrl      id      companySiteId      brandName      hasCompanySite      hasProducts      fcRecommendVisualizationSquareImgUrl      residentProvince      residentCity      logoUrl      verifyStatus      verifyStatusInt      isFavoriteByCurrMember      isFavoriteCompanyHomeByCurrMember      isFavoriteSiteByCurrMember      joinCategoriesHangyeCat      identities {        __typename        code        mark        isVerifyPass      }      memberFavorites {        __typename        id      }      categories {        __typename        name      }    }  }}";
//        RequestParams variables3 = new RequestParams();
//        RequestParams params_banner3 = new RequestParams();
//        RequestParams args = new RequestParams();
//        Gson gson = new Gson();
//        params_banner3.put("query",url3);
//        args.put("categoryIdsByOr","bd5c1250-5a35-e711-80e4-da42ba972ebd");
//        args.put("verifyStatus","Pass");
//        args.put("skip","0");
//        args.put("take","10");
//        variables3.put("args",args);
//        String s = gson.toJson(variables3);
//        params_banner3.put("variables",s);
//        Log.e("args",variables3.urlParams.get("args"));
//        Log.e("variables",params_banner3.urlParams.get("variables"));
        JSONObject params_banner3 = new JSONObject();
        JSONObject variables3 = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            params_banner3.put("query",url3);
            //args.put("categoryIdsByOr","bd5c1250-5a35-e711-80e4-da42ba972ebd");
            args.put("categoryIdsByOr",categoryIdsByOrNumber);
            args.put("verifyStatus","Pass");
            args.put("skip","0");
            args.put("take","10");
            variables3.put("args",args);
            params_banner3.put("variables",variables3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.e("params_banner3s",String.valueOf(params_banner3));

        requestCenter.GetData(params_banner3, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                String response = null;
                try {
                    response = responseObj.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                topdata = gson.fromJson(response,Topdata.class);
                data = topdata.getData();
                polymericcompanies = data.getPolymericCompanies();
                items4 = polymericcompanies.getItems();
                if(items4 != null) {
                    initRecyclerView();
                }
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    //显示公司列表
    public void initRecyclerView(){
        // 定义一个线性布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        // 设置布局管理器
        mRecyclerView.setLayoutManager(manager);
        // 设置adapter
        RecycleviewAdapter adapter = new RecycleviewAdapter(getContext(), items4);
        mRecyclerView.setAdapter(adapter);
    }


}
