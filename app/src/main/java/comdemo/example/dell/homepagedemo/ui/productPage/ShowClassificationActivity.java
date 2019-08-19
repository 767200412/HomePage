package comdemo.example.dell.homepagedemo.ui.productPage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.beans.Classification;
import comdemo.example.dell.homepagedemo.beans.ResponseMessage;
import comdemo.example.dell.homepagedemo.beans.Subcategories;
import comdemo.example.dell.homepagedemo.okhttp.listener.DisposeDataListener;
import comdemo.example.dell.homepagedemo.okhttp.request.RequestParams;
import comdemo.example.dell.homepagedemo.request.RequestCenter;
import okhttp3.Response;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class ShowClassificationActivity extends AppCompatActivity {
    private ImageView back;//返回标签
    private VerticalTabLayout verticalTabLayout;//垂直标签选项卡
    private LinearLayout container;//进一步的内容
    private RequestCenter requestCenter;
    private String selectClass,selectClassCont;
    //请求列表参数
    private String Skip;
    private String Take;
    private String fields;
    private String TypeId;
    //请求响应的实体类
    private ResponseMessage responseMessage;
    private List<Classification> classList ;
    private List<Subcategories> subList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_classification);
        init();
        Skip = "0";
        Take = "100";
        fields = "SubCategories";
        TypeId = "6e3cecaf-4d35-e711-80e4-da42ba972ebd";
        getData();
        setListenter();
    }

    //初始化
    private void init(){
        back = (ImageView)findViewById(R.id.back);
        verticalTabLayout = (VerticalTabLayout) findViewById(R.id.tab);
        container = (LinearLayout)findViewById(R.id.container);
        requestCenter = new RequestCenter(this);
        Intent intent = getIntent();
        selectClass = intent.getStringExtra("selectClass");
        selectClassCont = intent.getStringExtra("selectClassCont");
    }


    //设置监听器
    private void setListenter(){
       //返回上一界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //标签选中
        verticalTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                selectClass = tab.getTitleView().getText().toString();
                subList = classList.get(position).getSubCategories();
                setContainter();
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
    }

    //获取分类信息数据
    private void getData(){
       RequestParams params = new RequestParams();
       params.put("Skip",Skip);
       params.put("Take",Take);
       params.put("fields",fields);
       params.put("TypeId",TypeId);
       requestCenter.GetCompanyCategories(params, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                String result = null;
                try {
                    result = responseObj.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                classList = gson.fromJson(result,new TypeToken<List<Classification>>(){}.getType());

                setTab();

            }

            @Override
            public void onFailure(Object responseObj) {

            }
       });
    }

    //设置页面
    private void setTab(){
         verticalTabLayout.setTabAdapter(new TabAdapter() {
             @Override
             public int getCount() {
                 return classList.size();
             }

             @Override
             public ITabView.TabBadge getBadge(int position) {
                 return null;
             }

             @Override
             public ITabView.TabIcon getIcon(int position) {
                 return null;
             }

             @Override
             public ITabView.TabTitle getTitle(int position) {
                 ITabView.TabTitle title = new ITabView.TabTitle.Builder()
                         .setContent(classList.get(position).getName())
                         .setTextColor(R.color.colorWhite,Color.BLACK)
                         .setTextSize(15)
                         .build();
                 return title;
             }

             @Override
             public int getBackground(int position) {
                 return 0;
             }
         });


         //设置默认选中效果
         for(int i = 0 ; i<classList.size();i++){
             if(classList.get(i).getName().equals(selectClass)){
                 verticalTabLayout.setTabSelected(i);
             }
         }


    }

    //设置内容
    private void setContainter(){
        container.removeAllViews();
        for(final Subcategories sub:subList) {
            TextView childView1 = (TextView) LayoutInflater.from(ShowClassificationActivity.this)
                    .inflate(R.layout.tag, container, false);
            childView1.setText(sub.getName());
            if(sub.getName().equals(selectClassCont)){
                childView1.setTextColor(Color.parseColor("#ffff8f43"));
                childView1.setBackgroundResource(R.drawable.item);
            }
            childView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectClassCont = sub.getName();
                    Intent intent=new Intent();
                    intent.putExtra("selectClass",selectClass);
                    intent.putExtra("selectClassCont",selectClassCont);
                    intent.putExtra("CategoryId",sub.getId());
                    setResult(0x001,intent);
                    finish();
                }
            });
           container.addView(childView1);

        }
    }
}
