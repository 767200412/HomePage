package comdemo.example.dell.homepagedemo.ui.loginPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.beans.ResponseMessage;
import comdemo.example.dell.homepagedemo.utils.okhttp.listener.DisposeDataListener;
import comdemo.example.dell.homepagedemo.utils.okhttp.request.RequestCenter;
import comdemo.example.dell.homepagedemo.utils.dialog.MyDialog2;
import comdemo.example.dell.homepagedemo.utils.dialog.MyDialog3;
import okhttp3.Response;

public class MessageVerification extends AppCompatActivity implements TextWatcher {

    private EditText editText1,editText2,editText3,editText4;
    private TextView mTvPhone,mTv4;
    private MyDialog2 myDialog2;
    private MyDialog3 myDialog3;
    private ImageButton mImageBtn;
    private TimeCount time;
    private String phoneNumber;//用户手机号
    private String s;//图形验证码
    private String edit;//短信验证码
    private Bitmap bitmap;

    private RequestCenter requestCenter ;
    private Gson gson = new Gson();
    private ResponseMessage responseMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_verification);
        //初始化
        init();
        //退出界面
        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //重新发送
        mTv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //图形验证码
                getVeriCode();
                time.start();
            }
        });
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString().length() == 1){
            if(editText1.isFocused())
            {
                editText1.clearFocus();
                editText2.requestFocus();
            }
            else if(editText2.isFocused())
            {
                editText2.clearFocus();
                editText3.requestFocus();
            }

            else if(editText3.isFocused())
            {
                editText3.clearFocus();
                editText4.requestFocus();
            }
            else
            {
                editText4.clearFocus();
                //验证，跳转到下一个页面设置密码
                edit = editText1.getText().toString() + editText2.getText().toString()+editText3.getText().toString()+editText4.getText().toString();
                checkRegisterVerifyCodeByPhone(edit);

            }
        }
    }

    //初始化
    private void init(){
        requestCenter = new RequestCenter(this);
        editText1 = (EditText)findViewById(R.id.editText6);
        editText2 = (EditText)findViewById(R.id.editText7);
        editText3 = (EditText)findViewById(R.id.editText8);
        editText4 = (EditText)findViewById(R.id.editText9);
        mTvPhone = (TextView)findViewById(R.id.textView3);
        mTv4 =(TextView)findViewById(R.id.textView4);
        mImageBtn = (ImageButton)findViewById(R.id.imageButton2);
        //设置60s倒计时
        time = new TimeCount(60000, 1000);
        time.start();
        Intent intent = getIntent();//声明一个对象，并获得跳转过来的Intent对象
        phoneNumber = intent.getStringExtra("phone");//从intent对象中获得数据
        //type = intent.getStringExtra("type");
        s = intent.getStringExtra("verifyCode");
        mTvPhone.setText(phoneNumber);
        editText1.addTextChangedListener(this);
        editText2.addTextChangedListener(this);
        editText3.addTextChangedListener(this);
        editText4.addTextChangedListener(this);
    }
    //设置60s倒计时
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTv4.setClickable(false);
            mTv4.setText(" 重新发送("+millisUntilFinished / 1000+")");
            mTv4.setTextColor(getResources().getColor(R.color.gray));
            mTv4.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        @Override
        public void onFinish() {
            mTv4.setText("重新发送");
            mTv4.setClickable(true);
            mTv4.setTextColor(getResources().getColor(R.color.blue));
            mTv4.setBackgroundColor(getResources().getColor(R.color.colorWhite));

        }
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
    //验证短信验证码是正确
    private  void checkRegisterVerifyCodeByPhone(String e){
        JSONObject param_checkByphone = new JSONObject();
        try {
            param_checkByphone.put("PhoneRegionCode","+86");
            param_checkByphone.put("PhoneNumber",phoneNumber);
            param_checkByphone.put("VerifyCode",e);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        requestCenter.CheckRegisterVerifyCodeByPhone(param_checkByphone, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                String re = null;
                try {
                     re = responseObj.body().string();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                if(re.equals("true")){
                    //跳转到设置密码
                    Intent intent = new Intent(MessageVerification.this,Password.class);
                    intent.putExtra("phone",phoneNumber);
                    intent.putExtra("verifyCode",edit);
                    time.cancel();
                    startActivity(intent);

                }
                else if (re.equals("false")){
                    Toast.makeText(MessageVerification.this,"验证码错误",Toast.LENGTH_LONG).show();
                    editText4.requestFocus();
                }
            }

            @Override
            public void onFailure(Object responseObj) {
            }
        });
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
        myDialog3 = new MyDialog3(MessageVerification.this,R.style.MyDialog);
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

                }
                else if(check.equals("false")){
                    //验证码输入错误
                    Toast.makeText(MessageVerification.this,"验证码错误，请重新输入",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }
}
