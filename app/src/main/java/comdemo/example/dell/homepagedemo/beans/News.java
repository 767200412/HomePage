package comdemo.example.dell.homepagedemo.beans;

import java.util.List;

public class News {

    private String Id;
    private String Keyword;
    private String Title;
    private String ParentId;
    private int TypeLevel;
    private String Authorr;
    private String LinkUrl;
    private String Abstract;
    private String ActionLink;
    private String CategoryId;
    private boolean IsHot;
    private boolean IsHeader;
    private int Sort;
    private int Nice;
    private int Clickk;
//    private Date CreateTime;
//    private Date CreatedTimeUtc;
    private String  CreateTime;
    private String  CreatedTimeUtc;
    private List<Platformarticlealbum> PlatformArticleAlbum;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getKeyword() {
        return Keyword;
    }

    public void setKeyword(String keyword) {
        Keyword = keyword;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public int getTypeLevel() {
        return TypeLevel;
    }

    public void setTypeLevel(int typeLevel) {
        TypeLevel = typeLevel;
    }

    public String getAuthorr() {
        return Authorr;
    }

    public void setAuthorr(String authorr) {
        Authorr = authorr;
    }

    public String getLinkUrl() {
        return LinkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        LinkUrl = linkUrl;
    }

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }

    public String getActionLink() {
        return ActionLink;
    }

    public void setActionLink(String actionLink) {
        ActionLink = actionLink;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public boolean isHot() {
        return IsHot;
    }

    public void setHot(boolean hot) {
        IsHot = hot;
    }

    public boolean isHeader() {
        return IsHeader;
    }

    public void setHeader(boolean header) {
        IsHeader = header;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int sort) {
        Sort = sort;
    }

    public int getNice() {
        return Nice;
    }

    public void setNice(int nice) {
        Nice = nice;
    }

    public int getClickk() {
        return Clickk;
    }

    public void setClickk(int clickk) {
        Clickk = clickk;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getCreatedTimeUtc() {
        return CreatedTimeUtc;
    }

    public void setCreatedTimeUtc(String createdTimeUtc) {
        CreatedTimeUtc = createdTimeUtc;
    }

    public List<Platformarticlealbum> getPlatformArticleAlbum() {
        return PlatformArticleAlbum;
    }

    public void setPlatformArticleAlbum(List<Platformarticlealbum> platformArticleAlbum) {
        PlatformArticleAlbum = platformArticleAlbum;
    }
}
