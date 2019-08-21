package comdemo.example.dell.homepagedemo.ui.productPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.adapter.ClickInterface;
import comdemo.example.dell.homepagedemo.adapter.PhotoGridViewAdapter;
import comdemo.example.dell.homepagedemo.beans.ResponseMessage;
import comdemo.example.dell.homepagedemo.constant.NumberConstant;
import comdemo.example.dell.homepagedemo.utils.okhttp.listener.DisposeDataListener;
import comdemo.example.dell.homepagedemo.utils.okhttp.request.RequestCenter;
import comdemo.example.dell.homepagedemo.ui.showPicture.PictureSelectorConfig;
import comdemo.example.dell.homepagedemo.ui.showPicture.PlusImageActivity;
import okhttp3.Response;

public class PublishSupplyActivity extends AppCompatActivity implements ClickInterface{

    private ImageView back;
    private Context mContext;
    private GridView gridView;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private ArrayList<String> mName = new ArrayList<>(); //上传的图片凭证的数据源
    private PhotoGridViewAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器
    private Boolean isCkeck[] = new Boolean[4];//保存4个标签是否被选中
    private EditText tv_message;//文本框 输入发布的信息
    private TextView text_number;//显示文本框输入了多少数字
    private TextView photo_number;//显示添加了多少图片
    private TextView classification;//产品分类
    private TextView show2,show1;//返回的结果显示
    private TextView customized;//定制
    private TextView posts;//现货
    private TextView promotion;//促销
    private TextView sample;//样品
    private TextView supplyChainTypes;//供应链类型
    private String selectClass,selectChain,selectClassCont;
    private String CategoryId1,CategoryId2;
    private TextView tv_publish;//立即发布
    private RequestCenter requestCenter;
    private Gson gson = new Gson();
    private String token;
    private UploadManager uploadManager = new UploadManager();
    private List<ResponseMessage> resMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_supply);
        init();
        initGridView();
        setListenter();
        setTab();
    }

    //初始化
    private void init(){
        mContext = this;
        back = (ImageView)findViewById(R.id.back);
        gridView = (GridView)findViewById(R.id.gridView);
        customized = (TextView)findViewById(R.id.Customized);
        posts = (TextView)findViewById(R.id.posts);
        promotion = (TextView)findViewById(R.id.promotion);
        sample = (TextView)findViewById(R.id.sample);
        for(int i = 0 ; i < 4 ; i++){
            isCkeck[i] =false;
        }

        text_number = (TextView)findViewById(R.id.text_number);
        tv_message = (EditText)findViewById(R.id.tv_message);
        classification = (TextView)findViewById(R.id.classification);
        show1 = (TextView)findViewById(R.id.show1);
        show2 = (TextView)findViewById(R.id.show2);
        tv_publish = (TextView)findViewById(R.id.tv_publish);
        requestCenter = new RequestCenter(this);
        supplyChainTypes = (TextView)findViewById(R.id.supplyChainTypes);
        photo_number = (TextView)findViewById(R.id.photo_number);

        PhotoGridViewAdapter.CallBackData.setClickInterface(this);
    }

    //设置监听
    private void setListenter(){
        //返回按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //4个按钮 定制 样品 现货 促销
        customized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("选中了","customized");
                if(!isCkeck[0]){
                    //选中
                    //设为橙色
                    //Log.e("变色","橙色");
                    customized.setTextColor(Color.parseColor("#ffff8f43"));
                    customized.setBackgroundResource(R.drawable.item);
                    isCkeck[0] = true;
                    //Log.e("",String.valueOf(isCkeck[0]));
                }
                else {
                    //未选中
                    //设为灰色
                    //Log.e("变色","灰色");
                    customized.setTextColor(Color.parseColor("#ff666666"));
                    customized.setBackgroundResource(R.drawable.item2);
                    isCkeck[0] = false;
                    //Log.e("",String.valueOf(isCkeck[0]));
                }
            }
        });

        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCkeck[1]){
                    //选中
                    //设为橙色
                    posts.setTextColor(Color.parseColor("#ffff8f43"));
                    posts.setBackgroundResource(R.drawable.item);
                    isCkeck[1] = true;
                }
                else{
                    //未选中
                    //设为会色
                    posts.setTextColor(Color.parseColor("#ff666666"));
                    posts.setBackgroundResource(R.drawable.item2);
                    isCkeck[1] = false;
                }
            }
        });

        promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCkeck[2]){
                    //选中
                    //设为橙色
                    promotion.setTextColor(Color.parseColor("#ffff8f43"));
                    promotion.setBackgroundResource(R.drawable.item);
                    isCkeck[2] = true;
                }
                else{
                    //未选中
                    //设为会色
                    promotion.setTextColor(Color.parseColor("#ff666666"));
                    promotion.setBackgroundResource(R.drawable.item2);
                    isCkeck[2] = false;
                }
            }
        });

        sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCkeck[3]){
                    //选中
                    //设为橙色
                    sample.setTextColor(Color.parseColor("#ffff8f43"));
                    sample.setBackgroundResource(R.drawable.item);
                    isCkeck[3] = true;
                }
                else{
                    //未选中
                    //设为会色
                    sample.setTextColor(Color.parseColor("#ff666666"));
                    sample.setBackgroundResource(R.drawable.item2);
                    isCkeck[3] = false;
                }
            }
        });

        //计数 并显示输入了多少字
        tv_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               //修改显示
                String show = "(" + s.length() + "/200)";
                text_number.setText(show);
            }
        });


        //产品分类
        classification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishSupplyActivity.this,ShowClassificationActivity.class);
                intent.putExtra("selectClass",selectClass);
                intent.putExtra("selectClassCont",selectClassCont);
                startActivityForResult(intent,0x002);
            }
        });


        //供应链类别
        supplyChainTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishSupplyActivity.this,ChainTypeActivity.class);
                intent.putExtra("selectClass",selectClass);
                intent.putExtra("CategoryId2",CategoryId2);
                startActivityForResult(intent,0x003);
            }
        });

        //立即发布
        tv_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有未填项目
                isAllOk();
            }
        });
    }

    //初始化图片上传项目GridView
    private void initGridView(){
        mGridViewAddImgAdapter = new PhotoGridViewAdapter(mContext, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过9张，才能点击
                    if (mPicList.size() == NumberConstant.MAX_SELECT_PIC_NUM) {
                        //最多添加9张图片
                        viewPluImg(position);
                    } else {
                        //添加凭证图片
                        selectPic(NumberConstant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra(NumberConstant.IMG_LIST, mPicList);
        intent.putExtra(NumberConstant.POSITION, position);
        startActivityForResult(intent, NumberConstant.REQUEST_CODE_MAIN);
    }

    /**
     * 打开相册或者照相机选择凭证图片，最多9张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                //Log.e("图片",mPicList.get(0));
                mGridViewAddImgAdapter.notifyDataSetChanged();
                String s = "("+mPicList.size() + "/9)";
                photo_number.setText(s);
            }
        }
    }

    private void isAllOk(){
        int Content = tv_message.getText().toString().length();//内容
        boolean flag = false;
        for(int i = 0 ; i < isCkeck.length ;i++){
            if(isCkeck[i] == true){
                flag = true;
            }
        }
        if(Content != 0 && CategoryId1 !=null && CategoryId2 != null && flag){
            //获取上传凭证
            getToken();
        }
        else if(Content == 0){
            Toast.makeText(PublishSupplyActivity.this,"请输入产品信息",Toast.LENGTH_SHORT).show();
        }
        else if(CategoryId1 == null){
            Toast.makeText(PublishSupplyActivity.this,"请选择产品类型",Toast.LENGTH_SHORT).show();
        }
        else if(CategoryId2 == null){
            Toast.makeText(PublishSupplyActivity.this,"请选择供应链类型",Toast.LENGTH_SHORT).show();
        }
        else if(!flag){
            Toast.makeText(PublishSupplyActivity.this,"请选择供应类型",Toast.LENGTH_SHORT).show();
        }
    }

    //获取上传凭证
    private void getToken(){
        requestCenter.getToken(new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                String result = null;
                try {
                    result = responseObj.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ResponseMessage responseMessage =gson.fromJson(result,ResponseMessage.class);
                token = responseMessage.getToken();
                Log.e("token",token);
                //上传图片
                sendPicture();
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });

    }

    //上传图片
    private void sendPicture(){
        for(int i = 0 ; i < mPicList.size();i++) {
            File data = new File(mPicList.get(0));
            String key = data.getName();
            String name  = "http://filesdev.fccn.cc/"+key;
            mName.add(name);
            uploadManager.put(data, key, token, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                    if(info.isOK()) {
                        Log.i("qiniu", "Upload Success");
                    } else {
                        Log.i("qiniu", "Upload Fail");
                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    }
                    Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + response);
                }
            }, null);
        }

        //上传供应信息
        sendMarketTalks();
    }

    //设置标签
    private void setTab(){
          requestCenter.getTags(new DisposeDataListener() {
              @Override
              public void onSuccess(Response responseObj) {
                  String result = null;
                  try {
                      result = responseObj.body().string();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
                  resMe = gson.fromJson(result,new TypeToken<List<ResponseMessage>>(){}.getType());
                  customized.setText(resMe.get(0).getName());
                  posts.setText(resMe.get(1).getName());
                  promotion.setText(resMe.get(2).getName());
                  sample.setText(resMe.get(3).getName());

              }

              @Override
              public void onFailure(Object responseObj) {

              }
          });
    }
    //上传供应信息
    private void sendMarketTalks() {
        String SenderId = "a6b6ba45-5b1f-e711-80e3-850a1737545e";
        String CompanyId = "a2b6ba45-5b1f-e711-80e3-850a1737545e";
        String Content = tv_message.getText().toString();
        JSONObject param = new JSONObject();
        JSONArray CategoryIds = new JSONArray();
        CategoryIds.put(CategoryId1);
        CategoryIds.put(CategoryId2);
        JSONArray Images = new JSONArray();
        JSONObject Image = new JSONObject();
        JSONArray TagIds = new JSONArray();
        int a = 0;
        for(int i = 0 ; i<4;i++){
            if(isCkeck[i]){
                TagIds.put(resMe.get(i).getId());
                a++;
            }
        }
        try {
            for(int i =0;i<mName.size();i++){
                Image.put("OriginalPath",mName.get(i).toString());
                Images.put(Image);
            }
            param.put("SenderId",SenderId);
            param.put("CategoryIds",CategoryIds);
            param.put("Content",Content);
            param.put("Images",Images);
            param.put("TagIds",TagIds);
            param.put("CompanyId",CompanyId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestCenter.sendMarketTalks(param, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                   finish();
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == NumberConstant.REQUEST_CODE_MAIN && resultCode == NumberConstant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(NumberConstant.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
            String s = "("+mPicList.size() + "/9)";
            photo_number.setText(s);
        }
        if(requestCode==0x002&&resultCode==0x001){
            selectClass=data.getStringExtra("selectClass");
            CategoryId1 = data.getStringExtra("CategoryId");
            selectClassCont = data.getStringExtra("selectClassCont");
            show1.setText(selectClassCont);
        }
        if(requestCode==0x003&&resultCode==0x002){
            selectClass=data.getStringExtra("selectClass");
            CategoryId2 = data.getStringExtra("CategoryId2");

            show2.setText(selectClass);
        }
    }

    /**
     * 实现接口，获取数据
     * */
    @Override
    public void OnListener(int position) {
        //删除图片
        mPicList.remove(position);
        mGridViewAddImgAdapter.notifyDataSetChanged();
        String s = "("+mPicList.size() + "/9)";
        photo_number.setText(s);
    }
}
