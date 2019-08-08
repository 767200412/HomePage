package comdemo.example.dell.homepagedemo.Ui.LoginPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import comdemo.example.dell.homepagedemo.Beans.ResponseMessage;
import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.Ui.MainPage.MainActivity;
import comdemo.example.dell.homepagedemo.okhttp.listener.DisposeDataListener;
import comdemo.example.dell.homepagedemo.request.RequestCenter;
import okhttp3.Response;

public class ReallyName extends AppCompatActivity {

    private Button mBtnNext;
    private EditText mEtReallyName;
    private MyDialog myDialog;
    private ImageButton mImageBtn2;
    private String phoneNumber;//用户手机号
    private String s;//图形验证码
    private String edit;//短信验证码
    private String password;//密码
    private RequestCenter requestCenter ;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();
    private ResponseMessage responseMessage;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_really_name);
        mBtnNext = (Button) findViewById(R.id.bt_log);
        mEtReallyName = (EditText) findViewById(R.id.et_ReName);
        mImageBtn2 = (ImageButton)findViewById(R.id.imageButton2);
        Intent intent = getIntent();//声明一个对象，并获得跳转过来的Intent对象
        phoneNumber = intent.getStringExtra("phone");//从intent对象中获得数据
        edit = intent.getStringExtra("verifyCode");
        password = intent.getStringExtra("password");

        SpannableString ss = new SpannableString("请输入姓名");
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(20,true);

        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint
        mEtReallyName.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失

        //限制按钮激活
        new SomeMonitorEditText().SetMonitorEditText(mBtnNext, mEtReallyName);

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //前往下一界面
                boolean ok = true;
                if(ok)
                {
                    //默认自动登录
                    login();
                    //进入首页
                    Intent intent = new Intent(ReallyName.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    //提示弹框
                    SomeWrongDialog();
                }
            }
        });

        mImageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void SomeWrongDialog(){
        myDialog=new MyDialog(ReallyName.this,R.style.MyDialog);
        //myDialog.setTitle("警告！");
        myDialog.setMessage("该手机好以注册过辅城");
        myDialog.setYesOnclickListener("直接登录", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesOnclick() {
                myDialog.dismiss();
                //跳转到登录界面
                //进入首页
                Intent intent = new Intent(ReallyName.this,MainLoginActivity.class);
                startActivity(intent);
            }
        });

        myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                myDialog.dismiss();
                //返回
            }
        });

        myDialog.show();
    }


    //登录login
    private void login(){
        JSONObject param_log = new JSONObject();
        try {
            param_log.put("Account",phoneNumber);
            param_log.put("Password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestCenter.Login(param_log, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                int code = responseObj.code();
                String result = null;
                try {
                    result = responseObj.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(code == 200) {
                    //状态码200 登录成功

                    //步骤1：创建一个SharedPreferences对象
                    sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
                    //步骤2： 实例化SharedPreferences.Editor对象
                    editor = sharedPreferences.edit();
                    if (result != null) {
                        responseMessage = gson.fromJson(result, ResponseMessage.class);
                    }
                    //步骤3：将获取过来的值放入文件
                    editor.putString("id", responseMessage.getId());
                    //步骤4：提交
                    editor.commit();
                    //登录成功 跳转到主页
                    Intent intent = new Intent(ReallyName.this,MainActivity.class);
                    startActivity(intent);
                }



            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });




    }

}