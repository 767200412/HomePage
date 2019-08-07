package comdemo.example.dell.homepagedemo.request;


import android.content.Context;

import org.json.JSONObject;

import comdemo.example.dell.homepagedemo.Beans.ResponseMessage;
import comdemo.example.dell.homepagedemo.Beans.Topdata;
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

    public  static void hasAccountByPhoneNumber(DisposeDataListener listener,RequestParams params){
        RequestCenter.postRequest(HttpConstant.HasAccountByPhoneNumber,params,listener,null);

    }

    public  static  void getVerify(DisposeDataListener listener){
        RequestCenter.getRequest(HttpConstant.VerifyCodeImRanNO,null,listener,null);
    }

    public  static  void  Verify(String s,DisposeDataListener listener){
        String ss = HttpConstant.VerifyCodeCheck + s;
        RequestCenter.getRequest(ss,null,listener,null);
    }

    public static  void GetRegisterVerifyCodeByPhone(RequestParams params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.GetRegisterVerifyCodeByPhone,params,listener,null);
    }

    public static void CheckRegisterVerifyCodeByPhone(RequestParams params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.CheckRegisterVerifyCodeByPhone,params,listener,null);
    }

    //登录
    public  static void Login(RequestParams params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.Login,params,listener,ResponseMessage.class);
    }

    //获取头部banner数据
    public static void GetData(RequestParams params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.getData,params,listener, Topdata.class);
    }

    public static void GetData(JSONObject params,DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstant.getData,params,listener, Topdata.class);
    }


}
