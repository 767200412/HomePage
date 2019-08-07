package comdemo.example.dell.homepagedemo.constant;

/**
 * 描述:     统一管理接口地址
 */

public class HttpConstant {
    private static final String URL = "http://devapi.fccn.cc/Api/v1.1/";

    //登录
    public static String Login = URL + "Account/Login";
    //获取数据
    public static String getData = "http://apidev-gateway.fccn.cc/gqlgateway/graphql";

    public static String HasAccountByPhoneNumber = URL + "Account/HasAccountByPhoneNumber";
    public static String VerifyCodeImRanNO = URL + "VerifyCode/ImgRanNO/GetRegisterVerifyCodeByPhone";
    public static String VerifyCodeCheck = URL + "VerifyCode/Check/GetRegisterVerifyCodeByPhone/";
    public static String GetRegisterVerifyCodeByPhone = URL + "Account/GetRegisterVerifyCodeByPhone";
    public static String CheckRegisterVerifyCodeByPhone = URL + "Account/CheckRegisterVerifyCodeByPhone";
}
