package comdemo.example.dell.homepagedemo.ui.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import comdemo.example.dell.homepagedemo.R;
import comdemo.example.dell.homepagedemo.utils.SomeMonitorEditText;

public class PasswordActivity extends AppCompatActivity {
    private ImageButton mImageBtn;
    private EditText mEditTextPassword;
    private Button mButtonLog;
    private ImageButton mImageBtn2;
    private String phoneNumber;//用户手机号
    private String s;//图形验证码
    private String edit;//短信验证码
    private String password;//密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        //初始化
        init();

        //限制按钮激活
        new SomeMonitorEditText().SetMonitorEditText(mButtonLog, mEditTextPassword);
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

        //确认/下一步按钮
        mButtonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = mEditTextPassword.getText().toString();
                //跳转到设置真实姓名
                Intent intent = new Intent(PasswordActivity.this,ReallyNameActivity.class);
                intent.putExtra("phone",phoneNumber);
                intent.putExtra("verifyCode",edit);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });

        //退出界面
        mImageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //初始化
    public void init(){
        mEditTextPassword = (EditText)findViewById(R.id.et_PassWord);
        mButtonLog = (Button)findViewById(R.id.bt_log);
        mImageBtn = (ImageButton)findViewById(R.id.imageButton);
        mImageBtn2 = (ImageButton)findViewById(R.id.imageButton2);
        Intent intent = getIntent();//声明一个对象，并获得跳转过来的Intent对象
        phoneNumber = intent.getStringExtra("phone");//从intent对象中获得数据
        edit = intent.getStringExtra("verifyCode");
    }
}
