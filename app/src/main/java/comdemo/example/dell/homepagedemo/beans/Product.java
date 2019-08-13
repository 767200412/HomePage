package comdemo.example.dell.homepagedemo.beans;

import java.util.List;

public class Product {
    private String CompanyId;
    private Company Company;
    private String ContacterId;
    private String TypeId;
    private String Titlee;
    private String CategoryId;
    private String IsFavorite;
    private List<Tags> Tags;
    private String Id;
    private String SenderId;
    private String CreatedTime;
    private String Content;
    private List<Images> Images;

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public comdemo.example.dell.homepagedemo.beans.Company getCompany() {
        return Company;
    }

    public void setCompany(comdemo.example.dell.homepagedemo.beans.Company company) {
        Company = company;
    }

    public String getContacterId() {
        return ContacterId;
    }

    public void setContacterId(String contacterId) {
        ContacterId = contacterId;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

    public String getTitlee() {
        return Titlee;
    }

    public void setTitlee(String titlee) {
        Titlee = titlee;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getIsFavorite() {
        return IsFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        IsFavorite = isFavorite;
    }

    public List<comdemo.example.dell.homepagedemo.beans.Tags> getTags() {
        return Tags;
    }

    public void setTags(List<comdemo.example.dell.homepagedemo.beans.Tags> tags) {
        Tags = tags;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public List<comdemo.example.dell.homepagedemo.beans.Images> getImages() {
        return Images;
    }

    public void setImages(List<comdemo.example.dell.homepagedemo.beans.Images> images) {
        Images = images;
    }
}
