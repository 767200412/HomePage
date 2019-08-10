package comdemo.example.dell.homepagedemo.ui.mainPage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.adapter.NewsAdapter;
import comdemo.example.dell.homepagedemo.beans.News;
import comdemo.example.dell.homepagedemo.beans.NewsTab;
import comdemo.example.dell.homepagedemo.listener.EndlessRecyclerOnScrollListener;
import comdemo.example.dell.homepagedemo.okhttp.listener.DisposeDataListener;
import comdemo.example.dell.homepagedemo.okhttp.request.RequestParams;
import comdemo.example.dell.homepagedemo.request.RequestCenter;
import okhttp3.Response;

public class NewsActivity extends AppCompatActivity {

    private android.support.design.widget.TabLayout topTab;
    private android.support.v7.widget.RecyclerView newsRecycle;
    private ImageView imageView_back;
    private List<NewsTab>  tabList ;
    private List<News> newsList;
    private RequestCenter requestCenter;
    private String take;
    private String skip;
    private String fields;
    private String categoryid;
    private NewsAdapter adapter;
    private LinearLayoutManager manager = new LinearLayoutManager(this); // 定义一个线性布局管理器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();
        getTab();

        //设置默认选中的信息
        skip = "0";
        take = "10";
        fields = "PlatformArticleAlbum";
        categoryid = "1224838a-e04f-e711-80e4-da42ba972ebd";
        // 设置布局管理器
        newsRecycle.setLayoutManager(manager);
        //加载数据
        getPlatformArticle();

        //退出界面
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //分类标签点击监听
        topTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //添加选中Tab的逻辑
                String title = tab.getText().toString();
                for(NewsTab newsTab : tabList){
                    if(newsTab.getTitle().equals(title)){

                        categoryid = newsTab.getId();

                        skip = "0";
                        //调用公司数据加载
                        getPlatformArticle();

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

        //文章列表下拉监听  分页加载
        newsRecycle.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                adapter.setLoadState(adapter.LOADING);
                //加载新数据
                new LoadDataThread().start();
                adapter.setLoadState(adapter.LOADING_COMPLETE);
            }
        });

    }


    private void init(){
        topTab = (android.support.design.widget.TabLayout) findViewById(R.id.roll_top);
        newsRecycle = (android.support.v7.widget.RecyclerView) findViewById(R.id.review);
        imageView_back = (ImageView)findViewById(R.id.imageView_back);
        requestCenter = new RequestCenter(this);
    }

    //获取标签
    private void getTab(){
        RequestParams params = new RequestParams();
        params.put("IsApp","false");
        params.put("fields","PlatformArticleAlbum");
        requestCenter.GetPlatformArticleCategory(params, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                String result = null;
                try {
                    result = responseObj.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();
                tabList = gson.fromJson(result,new TypeToken<List<NewsTab>>() {}.getType());
                for(NewsTab newsTab:tabList){
                    topTab.addTab(topTab.newTab().setText(newsTab.getTitle()));
                }

            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });

    }

    //获取文章列表
    private void getPlatformArticle(){
        RequestParams params = new RequestParams();
        params.put("Skip",skip);
        params.put("Take",take);
        params.put("fields",fields);
        params.put("categoryid",categoryid);
        requestCenter.GetPlatformArticles(params, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                     String result = null;
                try {
                    result = responseObj.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();
                newsList = gson.fromJson(result,new TypeToken<List<News>>() {}.getType());
                if(newsList != null){
                    initRecyclerView();
                }
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    //显示消息列表
    public void initRecyclerView(){

        // 设置adapter
        if(skip .equals("0")) {
            adapter = new NewsAdapter(this, newsList);
        }
        else {
            adapter.appendData(newsList);
        }
        newsRecycle.setAdapter(adapter);
    }


    //下拉加载数据
    class LoadDataThread extends Thread {
        @Override
        public void run() {
            int skip_n = Integer.valueOf(skip);
            skip_n += 10;
            skip = String.valueOf(skip_n);
            getPlatformArticle();
        }
    }
}
