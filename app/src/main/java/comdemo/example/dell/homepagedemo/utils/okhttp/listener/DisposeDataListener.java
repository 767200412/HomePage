package comdemo.example.dell.homepagedemo.utils.okhttp.listener;

import okhttp3.Response;

/**

 * 描述:     自定义事件监听回调，处理请求成功和失败时的回调函数
 */

public interface DisposeDataListener {

    //请求成功回调事件处理
    public void onSuccess(Response responseObj);

    //请求失败回调事件处理
    public void onFailure(Object responseObj);

}