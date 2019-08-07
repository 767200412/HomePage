package comdemo.example.dell.homepagedemo.Beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


/**
 * Auto-generated: 2019-08-05 14:3:12
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Items {

    @JsonProperty("__typename")
    private String __typename;
    @JsonProperty("linkUrl")
    private String linkUrl;
    @JsonProperty("v600ImgUrl")
    private String v600ImgUrl;
    @JsonProperty("companyId")
    private String companyId;
    private String cover2;
    private String title;
    private String id;
    private String actionCode;
    private String description;
    @JsonProperty("actionArgsJson")
    private String actionArgsJson;
    private Imgs imgs;
    private String customSiteUrl;
    private String companySiteId;
    private String brandName;
    private boolean hasCompanySite;
    private boolean hasProducts;
    private String fcRecommendVisualizationSquareImgUrl;
    private String residentProvince;
    private String residentCity;
    private String logoUrl;
    private String verifyStatus;
    private int verifyStatusInt;
    private boolean isFavoriteByCurrMember;
    private boolean isFavoriteCompanyHomeByCurrMember;
    private boolean isFavoriteSiteByCurrMember;
    private String joinCategoriesHangyeCat;
    private List<Identities> identities;
    private String memberFavorites;
    private List<Categories> categories;
    public void set__typename(String __typename) {
        this.__typename = __typename;
    }
    public String get__typename() {
        return __typename;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
    public String getLinkUrl() {
        return linkUrl;
    }

    public void setV600ImgUrl(String v600ImgUrl) {
        this.v600ImgUrl = v600ImgUrl;
    }
    public String getV600ImgUrl() {
        return v600ImgUrl;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCover2() {
        return cover2;
    }

    public void setCover2(String cover2) {
        this.cover2 = cover2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionArgsJson() {
        return actionArgsJson;
    }

    public void setActionArgsJson(String actionArgsJson) {
        this.actionArgsJson = actionArgsJson;
    }

    public Imgs getImgs() {
        return imgs;
    }

    public void setImgs(Imgs imgs) {
        this.imgs = imgs;
    }

    public String getCustomSiteUrl() {
        return customSiteUrl;
    }

    public void setCustomSiteUrl(String customSiteUrl) {
        this.customSiteUrl = customSiteUrl;
    }

    public String getCompanySiteId() {
        return companySiteId;
    }

    public void setCompanySiteId(String companySiteId) {
        this.companySiteId = companySiteId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public boolean isHasCompanySite() {
        return hasCompanySite;
    }

    public void setHasCompanySite(boolean hasCompanySite) {
        this.hasCompanySite = hasCompanySite;
    }

    public boolean isHasProducts() {
        return hasProducts;
    }

    public void setHasProducts(boolean hasProducts) {
        this.hasProducts = hasProducts;
    }

    public String getFcRecommendVisualizationSquareImgUrl() {
        return fcRecommendVisualizationSquareImgUrl;
    }

    public void setFcRecommendVisualizationSquareImgUrl(String fcRecommendVisualizationSquareImgUrl) {
        this.fcRecommendVisualizationSquareImgUrl = fcRecommendVisualizationSquareImgUrl;
    }

    public String getResidentProvince() {
        return residentProvince;
    }

    public void setResidentProvince(String residentProvince) {
        this.residentProvince = residentProvince;
    }

    public String getResidentCity() {
        return residentCity;
    }

    public void setResidentCity(String residentCity) {
        this.residentCity = residentCity;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public int getVerifyStatusInt() {
        return verifyStatusInt;
    }

    public void setVerifyStatusInt(int verifyStatusInt) {
        this.verifyStatusInt = verifyStatusInt;
    }

    public boolean isFavoriteByCurrMember() {
        return isFavoriteByCurrMember;
    }

    public void setFavoriteByCurrMember(boolean favoriteByCurrMember) {
        isFavoriteByCurrMember = favoriteByCurrMember;
    }

    public boolean isFavoriteCompanyHomeByCurrMember() {
        return isFavoriteCompanyHomeByCurrMember;
    }

    public void setFavoriteCompanyHomeByCurrMember(boolean favoriteCompanyHomeByCurrMember) {
        isFavoriteCompanyHomeByCurrMember = favoriteCompanyHomeByCurrMember;
    }

    public boolean isFavoriteSiteByCurrMember() {
        return isFavoriteSiteByCurrMember;
    }

    public void setFavoriteSiteByCurrMember(boolean favoriteSiteByCurrMember) {
        isFavoriteSiteByCurrMember = favoriteSiteByCurrMember;
    }

    public String getJoinCategoriesHangyeCat() {
        return joinCategoriesHangyeCat;
    }

    public void setJoinCategoriesHangyeCat(String joinCategoriesHangyeCat) {
        this.joinCategoriesHangyeCat = joinCategoriesHangyeCat;
    }

    public List<Identities> getIdentities() {
        return identities;
    }

    public void setIdentities(List<Identities> identities) {
        this.identities = identities;
    }

    public String getMemberFavorites() {
        return memberFavorites;
    }

    public void setMemberFavorites(String memberFavorites) {
        this.memberFavorites = memberFavorites;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }
}