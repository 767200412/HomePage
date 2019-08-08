package comdemo.example.dell.homepagedemo.Ui.LoginPage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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



import comdemo.example.dell.homepagedemo.R;


public class Register extends AppCompatActivity {
    private TextView mTvUrl;
    private TextView mTvLog;
    private Button mBtnNext;
    private EditText mEtNumber;
    private ImageView mIvExit;
    private MyDialog3 myDialog;

    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    //标识为1，
                   Drawable d =(Drawable) msg.obj;


                    //verifyCode(d);
                        //未注册
                     /*
                        Intent intent = new Intent(Register.this, MessageVerification.class);
                        intent.putExtra("phone", mEtNumber.getText().toString());
                        intent.putExtra("type", "register");
                        startActivity(intent);
                        */


                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mTvUrl = (TextView)findViewById(R.id.tv_URL);
        mTvLog = (TextView)findViewById(R.id.textView10);
        mBtnNext = (Button)findViewById(R.id.bt_log);
        mEtNumber = (EditText)findViewById(R.id.et_PhoneNumber);
        mIvExit = (ImageView)findViewById(R.id.imageView4);


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


//   private void verifyCode(Drawable d){
//       myDialog=new MyDialog3(Register.this,R.style.MyDialog);
//       //myDialog.setTitle("警告！");
//       //myDialog.setMessage("密码错误，找回密码？");
//       Log.d("dialog","开始绘制");
//       myDialog.setBitmap(d);
//       myDialog.setYesOnclickListener("确定", new MyDialog3.onYesOnclickListener() {
//           @Override
//           public void onYesOnclick() {
//               new Thread(new Runnable() {
//                   @Override
//                   public void run() {
//                       String s = myDialog.getEditNumber().replaceAll(" ","");
//                       Log.d("输入的验证码",s);
//                       String url = "http://devapi.fccn.cc/Api/v1.1/VerifyCode/Check/a/"+s;
//                       Log.d("url",url);
//                       OkHttpUtils okHttpUtils= new OkHttpUtils();
//                       String result = okHttpUtils.getSyncInfo(url);
//                       Log.d("r",result);
//                   }
//               }).start();
//               myDialog.dismiss();
//
//           }
//       });
//       myDialog.setNoOnclickListener("取消", new MyDialog3.onNoOnclickListener() {
//           @Override
//           public void onNoClick() {
//               myDialog.dismiss();
//               //返回
//           }
//       });
//       myDialog.show();
//   }

}
