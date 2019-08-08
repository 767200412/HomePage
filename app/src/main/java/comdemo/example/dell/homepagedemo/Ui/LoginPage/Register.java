package comdemo.example.dell.homepagedemo.Ui.LoginPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import comdemo.example.dell.homepagedemo.Beans.ResponseMessage;
import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.okhttp.listener.DisposeDataListener;
import comdemo.example.dell.homepagedemo.request.RequestCenter;
import okhttp3.Response;


public class Register extends AppCompatActivity {
    private TextView mTvUrl;
    private TextView mTvLog;
    private Button mBtnNext;
    private EditText mEtNumber;
    private ImageView mIvExit;
    private MyDialog3 myDialog3;
    private MyDialog myDialog;
    private MyDialog2 myDialog2;
    private Bitmap bitmap;//图形验证码
    private String phoneNumber;//输入的手机号
    private String s;//用户输入的验证码

    private RequestCenter requestCenter ;
    private Gson gson = new Gson();
    private ResponseMessage responseMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mTvUrl = (TextView)findViewById(R.id.tv_URL);
        mTvLog = (TextView)findViewById(R.id.textView10);
        mBtnNext = (Button)findViewById(R.id.bt_log);
        mEtNumber = (EditText)findViewById(R.id.et_PhoneNumber);
        mIvExit = (ImageView)findViewById(R.id.imageView4);
        requestCenter = new RequestCenter(this);


        //限制按钮激活
        new SomeMonitorEditText().SetMonitorEditText(mBtnNext, mEtNumber);

        //跳转到登录界面
        mTvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,MainLoginActivity.class);
                startActivity(intent);
            }
        });

//        //点击下一步
//        mBtnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String phoneNumber = mEtNumber.getText().toString().replaceAll(" ", "");
//                //
//                final Map<String, String> map = new HashMap<String, String>();
//                map.put("PhoneRegionCode", "+86");
//                map.put("PhoneNumber", phoneNumber);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String url = "http://devapi.fccn.cc/Api/v1.1/Account/HasAccountByPhoneNumber";
//                        Gson gson = new Gson();
//                        String json = gson.toJson(map);
//                        OkHttpUtils okHttpUtils= new OkHttpUtils();
//                        String result = okHttpUtils.postSyncInfo(url,json);
//                        Log.d("结果",result);
//                        if(result.equals("false"))
//                        {
//                            //此手机号 还未注册
//                            //获取图形验证码
//                            Log.d("图片","获取图形验证码");
//                            url = "http://devapi.fccn.cc/Api/v1.1/VerifyCode/ImgRanNO/a";
//                            Log.d("url",url);
//                            Drawable d = okHttpUtils.getSync(url);
//                            //Bitmap bit = Glide.with(getBaseContext()).load(url);
//                            Log.d("图片", String.valueOf(d));
//
//
//                            Message msg = new Message();
//                            msg.what = 1;
//                            msg.obj = d;
//                            uiHandler.sendMessage(msg);
//                        }
//                    }
//                }).start();
//
//
//
//
//            }
//        });

        //点击下一步
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = mEtNumber.getText().toString().replaceAll(" ", "");
                JSONObject param_register = new JSONObject();
                try {
                    param_register.put("PhoneRegionCode","+86");
                    param_register.put("PhoneNumber",phoneNumber);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                requestCenter.hasAccountByPhoneNumber(param_register, new DisposeDataListener() {
                    @Override
                    public void onSuccess(Response responseObj) {
                        String hasAccount =null;
                        try {
                             hasAccount =responseObj.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(hasAccount.equals("false")){
                            //没有账号 获取图形验证码
                            getVeriCode();
                        }
                        else if(hasAccount.equals("true")){
                            //有账号 弹窗提示
                            hasAccountDialog();
                        }
                    }

                    @Override
                    public void onFailure(Object responseObj) {

                    }
                });

            }
        });

        mIvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Register.this,MainLoginActivity.class);
                intent.putExtra(MainLoginActivity.TAG_EXIT, true);
                startActivity(intent);
            }
        });

        //设置《辅城用户注册协议》超链接
        SpannableStringBuilder ssb = new SpannableStringBuilder("注册即代表您同意《辅城用户注册协议》");
        ssb.setSpan(new URLSpan("https://github.com/CaMnter"), 8, mTvUrl.getText().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(0xff4694ff), 8, mTvUrl.getText().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTvUrl.setText(ssb);
        // 在单击链接时凡是有要执行的动作，都必须设置MovementMethod对象
        mTvUrl.setMovementMethod(LinkMovementMethod.getInstance());
       // 设置点击后的颜色，这里涉及到ClickableSpan的点击背景
        mTvUrl.setHighlightColor(0xff8FABCC);

    }
    //已经有账号的提示框
    private void hasAccountDialog(){
        myDialog2=new MyDialog2(Register.this,R.style.MyDialog);
        //myDialog.setTitle("警告！");
        myDialog2.setMessage("此账号已经注册辅城");
        myDialog2.setYesOnclickListener("确定", new MyDialog2.onYesOnclickListener() {
            @Override
            public void onYesOnclick() {
                myDialog2.dismiss();
            }
        });

        myDialog2.show();
    }

    //获取图形验证码
    private void getVeriCode(){
        requestCenter.getVerify(new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                byte[] b = new byte[0];
                try {
                    b = responseObj.body().bytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                veriCodeDialog(bitmap);
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

   //图形验证码弹窗
    private void veriCodeDialog(Bitmap bitmap){
        myDialog3 = new MyDialog3(Register.this,R.style.MyDialog);
        myDialog3.setBitmap(bitmap);
        myDialog3.setImageOnclickListener(new MyDialog3.onImageOnclickListener(){

            @Override
            public void onImageOnclick() {
                myDialog3.dismiss();
                requestCenter.getVerify(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Response responseObj) {
                        byte[] b = new byte[0];
                        try {
                            b = responseObj.body().bytes();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                       Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                        veriCodeDialog(bitmap);
                    }

                    @Override
                    public void onFailure(Object responseObj) {

                    }
                });
            }
        });
        myDialog3.setYesOnclickListener("确定", new MyDialog3.onYesOnclickListener() {
            @Override
            public void onYesOnclick() {
                 s = myDialog3.getEditNumber().replaceAll(" ","");
                //验证是否真确
                veriCodeCheck();

            }
        });
        myDialog3.setNoOnclickListener("取消", new MyDialog3.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                myDialog3.dismiss();
            }
        });

        myDialog3.show();
    }

    //验证码是否正确
    private void veriCodeCheck(){
        requestCenter.Verify(s, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                String check = null;
                try {
                    check = responseObj.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(check.equals("true")){
                    //图形验证码输入正确 发送短信验证码
                    getRegisterVerifyCodeByPhone();
                    //跳转到短信验证码的界面
                    Intent intent = new Intent(Register.this,MessageVerification.class);
                    intent.putExtra("phone",phoneNumber);
                    intent.putExtra("verifyCode",s);
                    startActivity(intent);
                }
                else if(check.equals("false")){
                    //验证码输入错误
                    Toast.makeText(Register.this,"验证码错误，请重新输入",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }


    //根据电话发送短信验证码
    private void getRegisterVerifyCodeByPhone(){
        JSONObject param_getRegister = new JSONObject();
        try {
            param_getRegister.put("VerifyCode",s);
            param_getRegister.put("PhoneRegionCode","+86");
            param_getRegister.put("PhoneNumber",phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestCenter.GetRegisterVerifyCodeByPhone(param_getRegister, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                //成功 没有返回

            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }
}
