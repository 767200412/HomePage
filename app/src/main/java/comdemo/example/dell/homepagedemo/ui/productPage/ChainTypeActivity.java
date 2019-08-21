package comdemo.example.dell.homepagedemo.ui.productPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.beans.ChainResponse;
import comdemo.example.dell.homepagedemo.utils.okhttp.listener.DisposeDataListener;
import comdemo.example.dell.homepagedemo.utils.okhttp.request.RequestParams;
import comdemo.example.dell.homepagedemo.utils.okhttp.request.RequestCenter;
import okhttp3.Response;

public class ChainTypeActivity extends AppCompatActivity {
    private ListView ls;
    private RequestCenter requestCenter;
    private List<ChainResponse> chainList;
    private Gson gson = new Gson();
    private BaseAdapter adapter;//要实现的类
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain_type);
        init();
        getList();
        setListener();
    }

    //初始化
    private void init(){
        ls = (ListView)findViewById(R.id.ls);
        requestCenter = new RequestCenter(this);
    }

    //获取供应链信息 并显示
    private void getList(){
        RequestParams params = new RequestParams();
        params.put("TypeId","b0d53699-dde9-e611-80e3-850a1737545e");
        requestCenter.getCompanyCategories(params, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
               String result = null;
                try {
                    result = responseObj.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                chainList = gson.fromJson(result,new TypeToken<List<ChainResponse>>(){}.getType());
                adapter = new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return chainList.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return chainList.get(position);
                    }

                    @Override
                    public long getItemId(int position) {
                        return 0;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        LayoutInflater inflater = ChainTypeActivity.this.getLayoutInflater();
                        View view;
                        if (convertView==null) {
                            //因为getView()返回的对象，adapter会自动赋给ListView
                            view = inflater.inflate(R.layout.list_item, null);
                        }else{
                            view=convertView;
                            Log.i("info","有缓存，不需要重新生成"+position);
                        }
                        tv = (TextView) view.findViewById(R.id.tv);//找到Textviewname
                        tv.setText(chainList.get(position).getName());
                        return view;
                    }
                };

                ls.setAdapter(adapter);

            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    //设置监听
    private void setListener(){
           ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   Intent intent=new Intent();
                   intent.putExtra("selectClass",chainList.get(position).getName());
                   intent.putExtra("CategoryId2",chainList.get(position).getId());
                   setResult(0x002,intent);
                   finish();
               }
           });
    }
}
