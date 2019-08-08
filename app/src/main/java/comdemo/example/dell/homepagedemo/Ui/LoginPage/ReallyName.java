package comdemo.example.dell.homepagedemo.Ui.LoginPage;

import android.content.Intent;
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

import comdemo.example.dell.homepagedemo.R;

public class ReallyName extends AppCompatActivity {

    private Button mBtnNext;
    private EditText mEtReallyName;
    private MyDialog myDialog;
    private ImageButton mImageBtn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_really_name);
        mBtnNext = (Button) findViewById(R.id.bt_log);
        mEtReallyName = (EditText) findViewById(R.id.et_ReName);
        mImageBtn2 = (ImageButton)findViewById(R.id.imageButton2);

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
                    //进入首页
                    Intent intent = new Intent(ReallyName.this,MainLoginActivity.class);
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

}
