package comdemo.example.dell.homepagedemo.Ui.LoginPage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import comdemo.example.dell.homepagedemo.R;

public class MessageVerification extends AppCompatActivity implements TextWatcher {

    private EditText editText1,editText2,editText3,editText4;
    private TextView mTvPhone,mTv4;
    private MyDialog2 myDialog2;
    private String type;
    private ImageButton mImageBtn;
    private TimeCount time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_verification);
        editText1 = (EditText)findViewById(R.id.editText6);
        editText2 = (EditText)findViewById(R.id.editText7);
        editText3 = (EditText)findViewById(R.id.editText8);
        editText4 = (EditText)findViewById(R.id.editText9);
        mTvPhone = (TextView)findViewById(R.id.textView3);
        mTv4 =(TextView)findViewById(R.id.textView4);
        mImageBtn = (ImageButton)findViewById(R.id.imageButton2);
        //设置60s倒计时
        time = new TimeCount(60000, 1000);
        Intent intent = getIntent();//声明一个对象，并获得跳转过来的Intent对象
        String phone = intent.getStringExtra("phone");//从intent对象中获得数据
        type = intent.getStringExtra("type");
        mTvPhone.setText(phone);
        editText1.addTextChangedListener(this);
        editText2.addTextChangedListener(this);
        editText3.addTextChangedListener(this);
        editText4.addTextChangedListener(this);
        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                boolean ok  = true;
                if(ok)
                {
                    if(type.equals("register"))
                    {
                        //跳转到设置密码
                        Intent intent = new Intent(MessageVerification.this,Password.class);
                        startActivity(intent);
                    }
                    else if(type.equals("log"))
                    {
                        //判断手机是否注册


                       // 跳转到首页
                    }
                    else if(type.equals("find"))
                    {
                        //跳转到设置新密码页面
                        Intent intent = new Intent(MessageVerification.this,NewPassword.class);
                        startActivity(intent);
                    }

                }
                else
                {
                       //错误提示框
                    SomeWrongDialog();
                }
            }
        }
    }



    private void SomeWrongDialog(){
        myDialog2=new MyDialog2(MessageVerification.this,R.style.MyDialog);
        //myDialog.setTitle("警告！");
        myDialog2.setMessage("验证码错误，请重新输入");
        myDialog2.setYesOnclickListener("确定", new MyDialog2.onYesOnclickListener() {
            @Override
            public void onYesOnclick() {
                myDialog2.dismiss();
            }
        });

        myDialog2.show();
    }


    //设置60s倒计时
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTv4.setBackgroundColor(Color.parseColor("#B6B6D8"));
            mTv4.setClickable(false);
            mTv4.setText(" 重新发送("+millisUntilFinished / 1000+")");
           // mTv4.setBackgroundColor(Color.parseColor("#000000"));
            mTv4.setBackgroundColor(Color.WHITE);
        }

        @Override
        public void onFinish() {
            mTv4.setText("重新发送");
            mTv4.setClickable(true);
            mTv4.setBackgroundColor(Color.WHITE);

        }
    }
}
