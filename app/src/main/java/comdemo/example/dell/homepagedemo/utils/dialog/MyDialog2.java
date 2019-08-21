package comdemo.example.dell.homepagedemo.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comdemo.example.dell.homepagedemo.R;

/**
 * 创建自定义的Dialog，主要学习实现原理
 * Created by admin on 2017/8/30.
 */

public class MyDialog2 extends Dialog {
    private Button yes;//确定按钮
    private TextView titleTV;//消息标题文本
    private TextView message;//消息提示文本
    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本
    //确定文本和取消文本的显示的内容
    private String yesStr, noStr;
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    public MyDialog2(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }



    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param yesOnclickListener
     */
    public void setYesOnclickListener(String str, onYesOnclickListener yesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = yesOnclickListener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog2);
        //空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();

        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = findViewById(R.id.yes);
        titleTV = (TextView) findViewById(R.id.title);
        message = (TextView) findViewById(R.id.message);
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            titleTV.setText(titleStr);
        }
        if (messageStr != null) {
            message.setText(messageStr);
        }
        //如果设置按钮文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }

    }

    /**
     * 初始化界面的确定和取消监听
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesOnclick();
                }
            }
        });

    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleStr = title;
    }

    /**
     * 从外界Activity为Dialog设置message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageStr = message;
    }



    public interface onYesOnclickListener {
        public void onYesOnclick();
    }
}