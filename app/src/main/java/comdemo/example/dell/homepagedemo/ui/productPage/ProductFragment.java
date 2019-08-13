package comdemo.example.dell.homepagedemo.ui.productPage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.adapter.ProductAdapter;
import comdemo.example.dell.homepagedemo.beans.Product;
import comdemo.example.dell.homepagedemo.listener.EndlessRecyclerOnScrollListener;
import comdemo.example.dell.homepagedemo.okhttp.listener.DisposeDataListener;
import comdemo.example.dell.homepagedemo.okhttp.request.RequestParams;
import comdemo.example.dell.homepagedemo.request.RequestCenter;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private android.support.design.widget.TabLayout tableLayout;
    private View view,view2;//定义view用来设置fragment的layout
    private RequestCenter requestCenter;
    private String Skip;
    private String Take;
    private String fields;
    private List<Product> productList;
    private LinearLayoutManager manager = new LinearLayoutManager(getContext()); // 定义一个线性布局管理器
    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;


    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_product, container, false);
        init();
        initTab();
        initData();

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

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                adapter.setLoadState(adapter.LOADING);
                //加载新数据
                new LoadDataThread().start();
                adapter.setLoadState(adapter.LOADING_COMPLETE);
            }
        });


        return view;
    }


    private void init(){
        tableLayout = (android.support.design.widget.TabLayout)view.findViewById(R.id.top_tab);
        requestCenter = new RequestCenter(getContext());
        Skip = "0";
        Take = "10";
        fields = "Company,Images,Tags";
        recyclerView = (RecyclerView)view.findViewById(R.id.review);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe);
        // 设置布局管理器
        recyclerView.setLayoutManager(manager);
    }
    //设置顶部标签
    private void initTab(){
        tableLayout.addTab(tableLayout.newTab().setText("求购"));
        tableLayout.addTab(tableLayout.newTab().setText("供应"));
        tableLayout.getTabAt(1).select();
    }

    //获取求购信息
    private void initData(){
        RequestParams params = new RequestParams();
        params.put("Skip",Skip);
        params.put("Take",Take);
        params.put("fields",fields);
        requestCenter.GetMarketTalks(params, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                String re = null;
                try {
                    re = responseObj.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                productList = gson.fromJson(re,new TypeToken<List<Product>>() {}.getType());
                showMessage();
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    //显示列表数据
    private void showMessage(){
// 设置adapter
        if(Skip.equals("0")) {
            adapter = new ProductAdapter(getContext(), productList);
        }
        else {
            adapter.appendData(productList);
        }
        recyclerView.setAdapter(adapter);
    }

    //下拉加载数据
    class LoadDataThread extends Thread {
        @Override
        public void run() {
            int skip_n = Integer.valueOf(Skip);
            skip_n += 10;
            Skip = String.valueOf(skip_n);
            initData();
        }
    }

}
