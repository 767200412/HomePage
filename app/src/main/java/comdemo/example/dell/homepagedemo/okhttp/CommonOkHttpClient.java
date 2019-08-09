package comdemo.example.dell.homepagedemo.okhttp;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import comdemo.example.dell.homepagedemo.log.JsonUtil;
import comdemo.example.dell.homepagedemo.log.LogUtil;
import comdemo.example.dell.homepagedemo.cookie.CookieJarImpl;
import comdemo.example.dell.homepagedemo.cookie.PersistentCookieStore;
import comdemo.example.dell.homepagedemo.okhttp.https.HttpsUtils;
import comdemo.example.dell.homepagedemo.okhttp.listener.DisposeDataHandle;
import comdemo.example.dell.homepagedemo.okhttp.response.CommonJsonCallback;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 描述:     请求的发送，请求参数的配置，https支持
 */
 
public class CommonOkHttpClient {
 
    private static final int TIME_OUT = 30; //超时参数
    private static OkHttpClient mOkHttpClient;
    static List<Cookie> cookies=new ArrayList<>();

    //使用单例模式
    private volatile static CommonOkHttpClient commonOkHttpClient;
    public PersistentCookieStore cookieStore ;
    public  CookieJar cookieJar ;

    //为我们的Client配置参数  单例模式 私有的构造函数
    private CommonOkHttpClient (Context context){
        // Log信息拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        //设置日志打印级别
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        cookieStore = new PersistentCookieStore(context);
        cookieJar = new CookieJarImpl(cookieStore);

        //创建我们Client对象的构建者
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder
                //为构建者填充超时时间
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                //添加cookie
                .cookieJar(cookieJar)
                //添加日志拦截器
                .addInterceptor(loggingInterceptor)
                //允许重定向
                .followRedirects(true)
                //添加https支持
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                })
                .sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());
        mOkHttpClient = okHttpBuilder.build();
    }


    //获取唯一实例 双检锁/双重校验锁（DCL，即 double-checked locking）
    public static CommonOkHttpClient getCommonOkHttpClient(Context context){
        if (commonOkHttpClient == null) {
            synchronized (CommonOkHttpClient.class) {
                if (commonOkHttpClient == null) {
                    commonOkHttpClient = new CommonOkHttpClient(context);
                }
            }
        }
        return commonOkHttpClient;
    }


    //发送具体的HTTP以及Https请求
    public static Call sendRequest(Request request, CommonJsonCallback commonCallback) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(commonCallback);
        return call;
    }

 
    //GET请求
    public static Call get(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }
 
    //POST请求
    public static Call post(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    //放回CookieStore


    public PersistentCookieStore getCookieStore() {
        return cookieStore;
    }

    private static class HttpLogger implements HttpLoggingInterceptor.Logger {
        private StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            // 请求或者响应开始
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if ((message.startsWith("{") && message.endsWith("}"))
                    || (message.startsWith("[") && message.endsWith("]"))) {
                message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
            }
            mMessage.append(message.concat("\n"));
            // 响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                LogUtil.d(mMessage.toString());
            }
        }
    }
 
}