package comdemo.example.dell.homepagedemo.ui.loginPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class ForgetVeriyActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private TextView sendAgain, ed_phoneNumber;
    private TimeCount time;
    private String phoneNumber;//手机号
    private String veriCode;//图形验证码
    private String phoneVeriyCode;//手机验证码
    private RequestCenter requestCenter;
    private MyDialog2 myDialog2;
    private MyDialog3 myDialog3;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_veriy);
        //初始化
        init();

        //重新发送验证码
        sendAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVeriCode();
                time.start();
            }
        });

        //点击下一步按钮
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证手机验证码是否正确
                phoneVeriyCode = editText.getText().toString();
                checkSendResetPasswordVerifyCodeByPhone();
            }
        });
    }

    private void init(){
        editText = (EditText)findViewById(R.id.edit_input);
        button = (Button)findViewById(R.id.bt_next);
        sendAgain = (TextView)findViewById(R.id.tv_send);
        ed_phoneNumber = (TextView)findViewById(R.id.textView3);
        requestCenter = new RequestCenter(this);
        Intent intent = getIntent();//声明一个对象，并获得跳转过来的Intent对象
        phoneNumber = intent.getStringExtra("phoneNumber");//从intent对象中获得数据
        veriCode = intent.getStringExtra("verifyCode");

        ed_phoneNumber.setText(phoneNumber);
        new SomeMonitorEditText().SetMonitorEditText(button, editText);
        //设置60s倒计时
        time = new TimeCount(60000, 1000);
        time.start();
        //设置输入框的提示字符hint
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString("请输入验证码");
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(20,true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        editText.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }
    //重新发送
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            sendAgain.setClickable(false);
            sendAgain.setText(" 重新发送("+millisUntilFinished / 1000+")");
            sendAgain.setTextColor(getResources().getColor(R.color.gray));
            sendAgain.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        @Override
        public void onFinish() {
            sendAgain.setText("重新发送");
            sendAgain.setClickable(true);
            sendAgain.setTextColor(getResources().getColor(R.color.blue));
            sendAgain.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }
    }


    //获取图形验证码
    private void getVeriCode(){
        requestCenter.getResetVerify(new DisposeDataListener() {
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
        myDialog3 = new MyDialog3(ForgetVeriyActivity.this,R.style.MyDialog);
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

    //图形验证码是否正确
    private void veriCodeCheck(){
        requestCenter.ResetVerify(veriCode, new DisposeDataListener() {
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
                    getResetPasswordVerifyCodeByPhone();

                }
                else if(check.equals("false")){
                    //验证码输入错误
                    Toast.makeText(ForgetVeriyActivity.this,"验证码错误，请重新输入",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    //获取8位手机短信验证码
    private void getResetPasswordVerifyCodeByPhone(){
        JSONObject param_getRegister = new JSONObject();
        try {
            param_getRegister.put("VerifyCode",veriCode);
            param_getRegister.put("PhoneRegionCode","+86");
            param_getRegister.put("PhoneNumber",phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestCenter.GetResetPasswordVerifyCodeByPhone(param_getRegister, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                //成功 没有返回

            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }
    //验证手机验证码是否正确
    private void checkSendResetPasswordVerifyCodeByPhone(){
        JSONObject params_phoneVCheck = new JSONObject();
        try {
            params_phoneVCheck.put("VerifyCode",phoneVeriyCode);
            params_phoneVCheck.put("PhoneRegionCode","+86");
            params_phoneVCheck.put("PhoneNumber",phoneNumber);

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        requestCenter.CheckSendResetPasswordVerifyCodeByPhone(params_phoneVCheck, new DisposeDataListener() {
            @Override
            public void onSuccess(Response responseObj) {
                String result = null;
                try {
                    result = responseObj.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(result.equals("true")){
                    //验证正确 跳转到新密码页面
                    Intent intent = new Intent(ForgetVeriyActivity.this,NewPassword.class);
                    intent.putExtra("phoneVeriyCode",phoneVeriyCode);
                    intent.putExtra("phoneNumber",phoneNumber);
                    time.cancel();
                    startActivity(intent);
                }
                else if(result.equals("false")){
                    //验证码错误弹窗
                    VerifyWrong();

                }
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    ////验证码错误弹窗
    private void VerifyWrong(){
        myDialog2=new MyDialog2(ForgetVeriyActivity.this,R.style.MyDialog);
        //myDialog.setTitle("警告！");
        myDialog2.setMessage("验证码错误");
        myDialog2.setYesOnclickListener("确定", new MyDialog2.onYesOnclickListener() {
            @Override
            public void onYesOnclick() {
                myDialog2.dismiss();
            }
        });

        myDialog2.show();
    }
}
