package comdemo.example.dell.homepagedemo.Ui.LoginPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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

public class MainLoginActivity extends AppCompatActivity {


    public static final String TAG_EXIT = "exit";//退出程序标识

    private EditText mEditTextPhoneNumber;
    private EditText mEditTextPassword;
    private Button mButtonLog;
    private ImageButton mImageBtn,mImageBtn2;
    private MyDialog myDialog;
    private MyDialog2 myDialog2;
    private TextView mTvReg,mTvLog,mTvForget;
    private int wrongNumber =0;
    private String phoneNumber,passWord;
    private String url = "http://devapi.fccn.cc/Api/v1.1/Account/Login";
    private String TAG = "Success";
    private int flag = 0;
    private RequestCenter requestCenter ;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();
    private ResponseMessage responseMessage;
    private int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        //初始化
        requestCenter = new RequestCenter(this);
        mEditTextPhoneNumber = (EditText)findViewById(R.id.et_PhoneNumber);
        mEditTextPassword = (EditText)findViewById(R.id.et_PassWord);
        mButtonLog = (Button)findViewById(R.id.bt_log);
        mImageBtn = (ImageButton)findViewById(R.id.imageButton);
        mImageBtn2 = (ImageButton)findViewById(R.id.imageView4);
        mTvReg = (TextView)findViewById(R.id.textView11);
        mTvLog = (TextView)findViewById(R.id.textView7);
        mTvForget = (TextView)findViewById(R.id.textView5);

        //设置输入框的提示字符hint
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString("请输入手机号");
        SpannableString ss2 = new SpannableString("请输入密码");
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(20,true);
        AbsoluteSizeSpan ass2 = new AbsoluteSizeSpan(20,true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(ass2, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        mEditTextPhoneNumber.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
        mEditTextPassword.setHint(new SpannableString(ss2));

        //限制按钮激活
        new SomeMonitorEditText().SetMonitorEditText(mButtonLog, mEditTextPassword,mEditTextPhoneNumber);

        //关键部分:自动分隔手机号码通过addTextChangedListener()实现
        mEditTextPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s == null || s.length() == 0)
                    return;
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        stringBuilder.append(s.charAt(i));
                        if ((stringBuilder.length() == 4 || stringBuilder.length() == 9)
                                && stringBuilder.charAt(stringBuilder.length() - 1) != ' ') {
                            stringBuilder.insert(stringBuilder.length() - 1, ' ');
                        }
                    }
                }
                if (!stringBuilder.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (stringBuilder.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }

                    //设置粗体
                    SpannableStringBuilder ssb = new SpannableStringBuilder(stringBuilder.toString());
                    ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, stringBuilder.toString().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    //ssb.setSpan(new ForegroundColorSpan(Color.BLACK), 0, stringBuilder.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mEditTextPhoneNumber.setText(new SpannableString(ssb));

                    //mEditTextPhoneNumber.setText(stringBuilder.toString());
                    mEditTextPhoneNumber.setTextColor(Color.BLACK);
                    mEditTextPhoneNumber.setSelection(index);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //密码的显示/隐藏
        mImageBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    //((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.android_btn_pressed));
                    mEditTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    // ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.ic_little_eye_hide));
                    mEditTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                return false;
            }
        });






        //点击登录
        mButtonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNumber = mEditTextPhoneNumber.getText().toString().replaceAll(" ","");
                passWord = mEditTextPassword.getText().toString();
                //Log.d("phoneNumber",phoneNumber);
                //Log.d("passWord",passWord);

                login();
            }
        });

        //点击注册
       mTvReg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainLoginActivity.this,Register.class);
               startActivity(intent);
           }
       });

       //点击忘记密码
        mTvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLoginActivity.this,FindPassword.class);
                intent.putExtra("phone",mEditTextPhoneNumber.getText().toString());
                startActivity(intent);
            }
        });

        //点击验证码登录
        mTvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLoginActivity.this,VerificationLog.class);
                intent.putExtra("phone",mEditTextPhoneNumber.getText().toString());
                startActivity(intent);
            }
        });

        //退出程序
        mImageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainLoginActivity.this,MainLoginActivity.class);
                intent.putExtra(MainLoginActivity.TAG_EXIT, true);
                startActivity(intent);
            }
        });

    }



    private void SomeWrongDialog(){
        myDialog2=new MyDialog2(MainLoginActivity.this,R.style.MyDialog);
        //myDialog.setTitle("警告！");
        myDialog2.setMessage("账号或者密码错误，请重新输入");
        myDialog2.setYesOnclickListener("确定", new MyDialog2.onYesOnclickListener() {
            @Override
            public void onYesOnclick() {
                myDialog2.dismiss();
            }
        });

        myDialog2.show();
    }

    private void FindPassWordDialog(){
        myDialog=new MyDialog(MainLoginActivity.this,R.style.MyDialog);
        //myDialog.setTitle("警告！");
        myDialog.setMessage("密码错误，找回密码？");
        myDialog.setYesOnclickListener("找回密码", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesOnclick() {
                myDialog.dismiss();
                //前往密码忘记页面 带入手机值
                Intent intent = new Intent(MainLoginActivity.this,FindPassword.class);
                intent.putExtra("phone",mEditTextPhoneNumber.getText().toString());
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


    //退出程序
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(TAG_EXIT, false);
            if (isExit) {
                this.finish();
            }
        }
    }


    //登录login
    private void login(){
        JSONObject param_log = new JSONObject();
        try {
            param_log.put("Account",phoneNumber);
            param_log.put("Password",passWord);
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
                    Intent intent = new Intent(MainLoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else if(code == 400){
                    //状态码400：账号或者密码错误
                    //错误的次数
                    Log.d("登录失败", String.valueOf(code));
                    count++;

                    if (count >= 0 && count < 5) {
                          SomeWrongDialog();
                      } else if (count == 5) {
                        //计数重置
                          count =0;
                          FindPassWordDialog();
                      }
                    }


            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });




    }

}

