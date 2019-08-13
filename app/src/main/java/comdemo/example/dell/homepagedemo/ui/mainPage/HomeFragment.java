package comdemo.example.dell.homepagedemo.ui.mainPage;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
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

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.adapter.RecycleviewAdapter;
import comdemo.example.dell.homepagedemo.beans.CategoryIdsByOr;
import comdemo.example.dell.homepagedemo.beans.Data;
import comdemo.example.dell.homepagedemo.beans.Header;
import comdemo.example.dell.homepagedemo.beans.Indextop;
import comdemo.example.dell.homepagedemo.beans.Items;
import comdemo.example.dell.homepagedemo.beans.Menuitems;
import comdemo.example.dell.homepagedemo.beans.Pageinfo;
import comdemo.example.dell.homepagedemo.beans.Platformarticleselected;
import comdemo.example.dell.homepagedemo.beans.Polymericcompanies;
import comdemo.example.dell.homepagedemo.beans.Topdata;
import comdemo.example.dell.homepagedemo.listener.EndlessRecyclerOnScrollListener;
import comdemo.example.dell.homepagedemo.okhttp.listener.DisposeDataListener;
import comdemo.example.dell.homepagedemo.request.RequestCenter;
import comdemo.example.dell.homepagedemo.ui.loginPage.MainLoginActivity;
import comdemo.example.dell.homepagedemo.utils.GlideImageLoader;
import comdemo.example.dell.homepagedemo.utils.HideScrollListener;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public final int DATA_GAT_SUCCESSS = 1;

    private View view,view2;//定义view用来设置fragment的layout
    private Button btn_log;//立即登录按钮
    private ConstraintLayout constraintLayout;//未登录显示的浮层
    private Banner banner;//广告图片显示
    private MarqueeView marqueeView;//文字广告图片显示
    private HorizontalScrollView hs;//推荐品牌滚动
    private LinearLayout linearLayout;//推荐品牌滚动
    private TextView tv_name;//品牌的名字
    private TextView tv_news;//新闻
    private ImageView imageView;//品牌的图标
    private SwipeRefreshLayout swipeRefreshLayout;
    private android.support.design.widget.TabLayout mytab;
    private android.support.v7.widget.RecyclerView mRecyclerView;
    private List<String> mEntityList;//公司列表
    private RequestCenter requestCenter ;//网络请求中心
    private Topdata topdata;//响应实体类
    private Data data;//响应实体类
    private comdemo.example.dell.homepagedemo.beans.Banner banner2;//响应实体类
    private List<Header> headers;//响应实体类
    private Indextop indextop;//响应实体类
    private List<Items> items,items2,items3;//响应实体类
    private List<Items> items4 = new ArrayList<>();//响应实体类
    private Platformarticleselected platformarticleselected;//响应实体类
    private Menuitems menuitems;//响应实体类
    private Polymericcompanies polymericcompanies;//响应实体类
    private Pageinfo pageinfo;//响应实体类
    private CategoryIdsByOr categoryIdsByOr;//用于标签分类的标识
    private String categoryIdsByOrNumber;
    private RecycleviewAdapter adapter;    // 设置adapter
    private LinearLayoutManager manager = new LinearLayoutManager(getContext()); // 定义一个线性布局管理器
    private int lastLoadDataItemPosition;//加载更多数据时最后一项的索引
    private int skip = 0;//跳过的数据数量
    private int take = 10; //一次加载的数据量
    private List images = new ArrayList();//横向滚动广告数据集
    private List<String> messages = new ArrayList<>();//垂直滚动 跑马灯数据集
    private SharedPreferences sharedPreferences;//保存数据
    private SharedPreferences.Editor editor;
    private String id;
    private HideScrollListener listener;
    private static final int THRESHOLD = 50;
    private int distance = 0;
    private boolean visible = false;//是否可见
    private TextView fab_company,fab_microshop;
    private ConstraintLayout fab;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //步骤1：创建一个SharedPreferences对象
        sharedPreferences = getActivity().getSharedPreferences("LoginData",Context.MODE_PRIVATE);
        //步骤2： 实例化SharedPreferences.Editor对象
         editor = sharedPreferences.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        init();
        //判断是否登录 决定下方浮层的显、隐
        isLog();

        //设置控件swipeRefreshLayout参数
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新的圆圈是否显示
                swipeRefreshLayout.setRefreshing(false);
                //取消下拉刷新
                swipeRefreshLayout.setEnabled(true);
                swipeRefreshLayout.stopNestedScroll();
            }
        });

        //上半部数据获取及显示
        initTop();
        //初始化分类标签
        initTab();
        skip = 0;
        // 设置布局管理器
        mRecyclerView.setLayoutManager(manager);
        //加载公司数据
        initCompany();

        //新闻
        tv_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NewsActivity.class);
                startActivity(intent);
            }
        });

        //立即登录按钮监听
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainLoginActivity.class);
                startActivity(intent);
            }
        });

        //分类标签点击监听
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
                        // Log.e("categoryIdsByOr",categoryIdsByOrNumber);
                         skip = 0;
                         //调用公司数据加载
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




        //公司列表下拉监听  分页加载
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                adapter.setLoadState(adapter.LOADING);
                //加载新数据
                new LoadDataThread().start();
                adapter.setLoadState(adapter.LOADING_COMPLETE);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //onHide();
                if (distance > THRESHOLD && !visible) {
                    //显示动画
                    visible = true;
                    fab.setVisibility(View.VISIBLE);
                    //onShow();
                    distance = 0;
                } else if (distance < -50 && visible) {
                    //隐藏动画
                    visible = false;
                    fab.setVisibility(View.GONE);
                   // onHide();
                    distance = 0;
                }

                if (!visible && dy > 0 || (visible && dy < 0)) {
                    distance += dy;
                }
            }
        });

        //回到安顶部
        fab_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        return view;
    }

    //初始化
    private void init(){
        hs = (HorizontalScrollView)view.findViewById(R.id.hs);
        linearLayout = (LinearLayout)view.findViewById(R.id.liner);
        banner = (Banner)view.findViewById(R.id.tv_ad);
        marqueeView = (MarqueeView)view.findViewById(R.id.tv_bottomNews);
        requestCenter = new RequestCenter(getContext());
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.n_scroll_view);
        mytab = (android.support.design.widget.TabLayout) view.findViewById(R.id.roll_tab);
        mRecyclerView = view.findViewById(R.id.review);
        btn_log = (Button)view.findViewById(R.id.log);
        constraintLayout = (ConstraintLayout)view.findViewById(R.id.cs_bottom);
        tv_news=(TextView)view.findViewById(R.id.tv_news);
        fab_company = (TextView)view.findViewById(R.id.fab_company);
        fab_microshop = (TextView)view.findViewById(R.id.fab_microshop);
        fab = (ConstraintLayout)view.findViewById(R.id.constraintLayout_fab);
        fab.setVisibility(View.GONE);
    }

    //判断是否登录 决定下方浮层的显、隐
    private void isLog(){
        id = sharedPreferences.getString("id","NULL");
        if(id.equals("NULL")){
            constraintLayout.setVisibility(View.VISIBLE);
            Log.d("未登录","");
        }
        else {
            constraintLayout.setVisibility(View.GONE);
            Log.d("登录","");
        }
    }

    //初始上半部数据
    private void initTop(){
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
            view2 = LayoutInflater.from(getActivity()).inflate(R.layout.top_item, null);
            tv_name = (TextView) view2.findViewById(R.id.top_name);
            imageView = (ImageView)view2.findViewById(R.id.top_image);
            tv_name.setText(item.getTitle());
            Glide.with(getContext())
                    .load(item.getCover2())
                    .into(imageView);
            linearLayout.addView(view2);

        }
    }
    //显示 最上方 滚动图片广告
    public void showAd(){
        //设置图片集合
        banner.setImages(images);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
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
        JSONObject params_banner3 = new JSONObject();
        JSONObject variables3 = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            params_banner3.put("query",url3);
            //args.put("categoryIdsByOr","bd5c1250-5a35-e711-80e4-da42ba972ebd");
            args.put("categoryIdsByOr",categoryIdsByOrNumber);
            args.put("verifyStatus","Pass");
            args.put("skip",skip);
            args.put("take",take);
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
//                Message msg = new Message();
//                msg.what=DATA_GAT_SUCCESSS ;
//                handler.sendMessage(msg);
                if(items4 != null){
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
//        // 定义一个线性布局管理器
//        LinearLayoutManager manager = new LinearLayoutManager(getContext());
//        // 设置布局管理器
//        mRecyclerView.setLayoutManager(manager);
        // 设置adapter
        if(skip ==0) {
            adapter = new RecycleviewAdapter(getContext(), items4);
        }
        else {
            adapter.appendData(items4);
        }
        mRecyclerView.setAdapter(adapter);
    }

    //下拉加载数据
    class LoadDataThread extends Thread {
        @Override
        public void run() {
            skip += 10;
            initCompany();
        }
    }



    public void onHide() {
        //隐藏动画

        fab.animate().translationY(fab.getHeight()).setInterpolator(new AccelerateInterpolator(3));
    }


    public void onShow() {

        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
    }




}
