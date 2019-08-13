package comdemo.example.dell.homepagedemo.request;


import android.content.Context;

import org.json.JSONObject;

import comdemo.example.dell.homepagedemo.beans.ResponseMessage;
import comdemo.example.dell.homepagedemo.beans.Topdata;
import comdemo.example.dell.homepagedemo.constant.HttpConstant;
import comdemo.example.dell.homepagedemo.okhttp.CommonOkHttpClient;
import comdemo.example.dell.homepagedemo.okhttp.listener.DisposeDataHandle;
import comdemo.example.dell.homepagedemo.okhttp.listener.DisposeDataListener;
import comdemo.example.dell.homepagedemo.okhttp.request.CommonRequest;
import comdemo.example.dell.homepagedemo.okhttp.request.RequestParams;

/**
 * 描述:     统一管理所有的请求
 */

public class RequestCenter {
    private static CommonOkHttpClient commonOkHttpClient;

    public RequestCenter(Context context){
        commonOkHttpClient = CommonOkHttpClient.getCommonOkHttpClient(context);
    }


    //根据参数发送所有的get请求
    private static void getRequest(String url, RequestParams params,
                                   DisposeDataListener listener,
                                   Class<?> clazz){

        commonOkHttpClient.get(CommonRequest.createGetRequest(url, params),
                new DisposeDataHandle(listener,clazz));
    }



    //
    //根据参数发送所有的post请求
    private static void postRequest(String url, RequestParams params,
                                    DisposeDataListener listener,
                                    Class<?> clazz){

        commonOkHttpClient.post(CommonRequest.createPostRequest(url, params),
                new DisposeDataHandle(listener,clazz));
    }

    private static void postRequest(String url,JSONObject param,
                                    DisposeDataListener listener,
                                    Class<?> clazz){

        commonOkHttpClient.post(CommonRequest.createPostRequest(url, param),
                new DisposeDataHandle(listener,clazz));
    }


    public static void requestRecommandData(DisposeDataListener listener){
        RequestCenter.getRequest(HttpConstant.HasAccountByPhoneNumber,null,listener, null);
    }


    //是否以及注册手机号
    public  static void hasAccountByPhoneNumber(JSONObject params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.HasAccountByPhoneNumber,params,listener,null);

    }

    /*
    */


    //获取注册的图形验证码
    public  static  void getVerify(DisposeDataListener listener){
        RequestCenter.getRequest(HttpConstant.VerifyCodeImRanNO,null,listener,null);
    }

    //验证注册的图形验证码是否正确
    public  static  void  Verify(String s,DisposeDataListener listener){
        String ss = HttpConstant.VerifyCodeCheck + s;
        RequestCenter.getRequest(ss,null,listener,null);
    }

    //注册  根据手机号 发送手机验证码
    public static  void GetRegisterVerifyCodeByPhone(JSONObject params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.GetRegisterVerifyCodeByPhone,params,listener,null);
    }

    //注册 验证短信验证码是否正确
    public static void CheckRegisterVerifyCodeByPhone(JSONObject params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.CheckRegisterVerifyCodeByPhone,params,listener,null);
    }

    //密码登录
    public  static void Login(JSONObject params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.Login,params,listener,ResponseMessage.class);
    }

    //获取头部banner数据
    public static void GetData(RequestParams params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.getData,params,listener, Topdata.class);
    }

    public static void GetData(JSONObject params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.getData,params,listener, Topdata.class);
    }


    /*
    *
    * 短信验证码登录
    */
    //获取短信登录的图形验证码
    public static void getLoginVerify(DisposeDataListener listener){
        RequestCenter.getRequest(HttpConstant.LoginVerifyCodeImRanNO,null,listener,null);
    }

    //短信登录的验证图形验证码是否正确
    public  static  void  LoginVerify(String s,DisposeDataListener listener){
        String ss = HttpConstant.LoginVerifyCodeCheck + s;
        RequestCenter.getRequest(ss,null,listener,null);
    }

    //获取短信登录的手机验证码
    public static void GetLoginVerifyCode(JSONObject params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.GetLoginVerifyCode,params,listener,null);
    }

    //验证短信登录的手机验证码
    public static void CheckLoginVerifyCodeByPhone(JSONObject params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.CheckLoginVerifyCodeByPhone,params,listener,null);
    }

    //短信验证码登录
    public static void LoginBySms(JSONObject params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.LoginBySms,params,listener,null);
    }

    /*
     *
     * 找回密码登录
     */

    //找回密码图形验证码
    public static void getResetVerify(DisposeDataListener listener){
        RequestCenter.getRequest(HttpConstant.GetResetPasswordVerifyCodeByPhone,null,listener,null);
    }

    //验证图形验证码是否正确
    public  static  void  ResetVerify(String s,DisposeDataListener listener){
        String ss = HttpConstant.CheckResetPasswordVerifyCodeByPhone + s;
        RequestCenter.getRequest(ss,null,listener,null);
    }

    //根据手机号 发送找回密码的手机验证码
    public static  void GetResetPasswordVerifyCodeByPhone(JSONObject params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.SendResetPasswordVerifyCodeByPhone,params,listener,null);
    }

    //根据手机号 验证找回密码的手机验证码
    public static  void CheckSendResetPasswordVerifyCodeByPhone(JSONObject params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.CheckSendResetPasswordVerifyCodeByPhone,params,listener,null);
    }

    //重置密码
    public static void ResetPassword(JSONObject params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.ResetPassword,params,listener,null);
    }

    //获取新闻标签
    public static void GetPlatformArticleCategory(RequestParams params,DisposeDataListener listener){
        RequestCenter.getRequest(HttpConstant.GetPlatformArticleCategory,params,listener,null);
    }

    public static void GetPlatformArticles(RequestParams params,DisposeDataListener listener){
        RequestCenter.getRequest(HttpConstant.GetPlatformArticles,params,listener,null);
    }

    public static void GetMarketTalks(RequestParams params,DisposeDataListener listener){
        RequestCenter.getRequest(HttpConstant.GetMarketTalks,params,listener,null);
    }


}
