package comdemo.example.dell.homepagedemo.utils.okhttp.listener;

/**

 * 描述:     封装回调接口和要转换的实体对象
 */


public class DisposeDataHandle {

    public DisposeDataListener mListener = null;
    public Class<?> mClass = null;

    public DisposeDataHandle(DisposeDataListener listener) {
        this.mListener = listener;
    }

    public DisposeDataHandle(DisposeDataListener listener, Class<?> clazz) {
        this.mListener = listener;
        this.mClass = clazz;
    }

}
