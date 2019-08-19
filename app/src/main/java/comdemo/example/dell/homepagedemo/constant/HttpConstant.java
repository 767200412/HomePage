package comdemo.example.dell.homepagedemo.constant;

/**
 * 描述:     统一管理接口地址
 */

public class HttpConstant {
    private static final String URL = "http://devapi.fccn.cc/Api/v1.1/";
    private static final String URL2 = "http://devapi.fccn.cc/Api/v1/";
    //登录
    public static String Login = URL + "Account/Login";
    //获取数据
    public static String getData = "http://apidev-gateway.fccn.cc/gqlgateway/graphql";

    public static String HasAccountByPhoneNumber = URL + "Account/HasAccountByPhoneNumber";
    //注册
    public static String VerifyCodeImRanNO = URL + "VerifyCode/ImgRanNO/GetRegisterVerifyCodeByPhone";
    public static String VerifyCodeCheck = URL + "VerifyCode/Check/GetRegisterVerifyCodeByPhone/";
    public static String GetRegisterVerifyCodeByPhone = URL + "Account/GetRegisterVerifyCodeByPhone";
    public static String CheckRegisterVerifyCodeByPhone = URL + "Account/CheckRegisterVerifyCodeByPhone";
    //短信验证码登录
    public static String LoginVerifyCodeImRanNO = URL + "VerifyCode/ImgRanNO/GetLoginVerifyCode";
    public static String LoginVerifyCodeCheck = URL + "VerifyCode/Check/GetLoginVerifyCode/";
    public static String GetLoginVerifyCode = URL + "Account/GetLoginVerifyCode";
    public static String CheckLoginVerifyCodeByPhone = URL + "Account/CheckLoginVerifyCodeByPhone";
    public static String LoginBySms = URL +"Account/LoginBySms";
    //忘记密码 重置密码
    public static String GetResetPasswordVerifyCodeByPhone = URL + "VerifyCode/ImgRanNO/GetResetPasswordVerifyCodeByPhone";
    public static String CheckResetPasswordVerifyCodeByPhone = URL + "VerifyCode/Check/GetResetPasswordVerifyCodeByPhone/";
    public static String SendResetPasswordVerifyCodeByPhone = URL + "Account/GetResetPasswordVerifyCodeByPhone";
    public static String CheckSendResetPasswordVerifyCodeByPhone =URL + "Account/CheckResetPasswordVerifyCodeByPhone";
    public static String ResetPassword = URL + "Account/ResetPassword";

    //获取新闻文章
    public static String GetPlatformArticleCategory = URL + "PlatformArticles/PlatformArticleCategory/List";
    public static String GetPlatformArticles = URL + "PlatformArticles/PlatformArticle/List";

    //获取供应购信息
    public static String GetMarketTalks = URL +"MarketTalks";

    //获取标签信息
    public static String GetTags = URL +"MarketTalks/Tags";

    //产品分类信息
    public static String GetCompanyCategories = URL + "CompanyCategories";
    //求购列表
    public static String GetBiddings = URL2 + "Biddings";



    //获取七牛上传凭证
    public static String GetToken = URL2 + "Qiniu/GetUploadToken";
}
