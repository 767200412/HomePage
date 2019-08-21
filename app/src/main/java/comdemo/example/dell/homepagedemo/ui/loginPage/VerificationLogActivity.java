package comdemo.example.dell.homepagedemo.ui.loginPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.utils.okhttp.listener.DisposeDataListener;
import comdemo.example.dell.homepagedemo.utils.okhttp.request.RequestCenter;
import comdemo.example.dell.homepagedemo.utils.dialog.MyDialog2;
import comdemo.example.dell.homepagedemo.utils.dialog.MyDialog3;
import comdemo.example.dell.homepagedemo.utils.SomeMonitorEditText;
import okhttp3.Response;

public class VerificationLogActivity extends AppCompatActivity {
    private EditText mEditPhoneNumber;
    private Button mButtonLog;
    private ImageView mIvExit;
    private String phoneNumber;//手机号
    private String veriCode;//图形验证码
    private Bitmap bitmap;
    private RequestCenter requestCenter;
    private MyDialog2 myDialog2;
    private MyDialog3 myDialog3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_log);
        //初始化
        init();
        mButtonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //跳转到验证码界面
//                Intent intent = new Intent(VerificationLogActivity.this,MessageVerification.class);
//                intent.putExtra("phone",mEditTextPassword.getText().toString());
//                intent.putExtra("type","log");
//                startActivity(intent);
                phoneNumber = mEditPhoneNumber.getText().toString();
                //先判断是否有手机号存在
                hasAccountByPhone();
            }
        });

        mIvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出界面
                Intent intent = new Intent(VerificationLogActivity.this,MainLoginActivity.class);
                intent.putExtra(MainLoginActivity.TAG_EXIT, true);
                startActivity(intent);
            }
        });
    }

    //初始化
    private void init(){
        mEditPhoneNumber = (EditText)findViewById(R.id.et_PhoneNumber);
        mButtonLog = (Button)findViewById(R.id.bt_log);
        mIvExit = (ImageView)findViewById(R.id.imageView4);
        requestCenter = new RequestCenter(this);
        Intent intent = getIntent();//声明一个对象，并获得跳转过来的Intent对象
        phoneNumber = intent.getStringExtra("phone");//从intent对象中获得数据
        mEditPhoneNumber.setText(phoneNumber);
        //限制按钮激活
        new SomeMonitorEditText().SetMonitorEditText(mButtonLog, mEditPhoneNumber);
    }

    //账号是否存在
    private  void hasAccountByPhone(){
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
                if(hasAccount.equals("true")){
                    //有账号 获取图形验证码
                    getVeriCode();
                }
                else if(hasAccount.equals("false")){
                    //没有账号 弹窗提示
                    hasNoAccountDialog();
                }
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    //没有账号的提示框
    private void hasNoAccountDialog(){
        myDialog2=new MyDialog2(VerificationLogActivity.this,R.style.MyDialog);
        //myDialog.setTitle("警告！");
        myDialog2.setMessage("此账号未注册辅城");
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
        requestCenter.getLoginVerify(new DisposeDataListener() {
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
        myDialog3 = new MyDialog3(VerificationLogActivity.this,R.style.MyDialog);
        myDialog3.setBitmap(bitmap);
        myDialog3.setImageOnclickListener(new MyDialog3.onImageOnclickListener(){

            @Override
            public void onImageOnclick() {
                myDialog3.dismiss();
                requestCenter.getResetVerify(new DisposeDataListener() {
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
                veriCode = myDialog3.getEditNumber().replaceAll(" ","");
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
        requestCenter.loginVerify(veriCode, new DisposeDataListener() {
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
                    GetLoginVerifyCode();

                }
                else if(check.equals("false")){
                    //验证码输入错误
                    Toast.makeText(VerificationLogActivity.this,"验证码错误，请重新输入",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    //获取6位手机短信验证码
    private void GetLoginVerifyCode(){
        JSONObject param_getRegister = new JSONObject();
        try {
            param_getRegister.put("VerifyCode",veriCode);
            param_getRegister.put("PhoneRegionCode","+86");
            param_getRegister.put("PhoneNumber",phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestCenter.getLoginVerifyCode(param_getRegister, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                //成功 没有返回
                //跳转到短信验证码的界面
                Intent intent = new Intent(VerificationLogActivity.this,LoginVerifyActivity.class);
                intent.putExtra("phone",phoneNumber);
                intent.putExtra("verifyCode",veriCode);
                startActivity(intent);
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }
}
