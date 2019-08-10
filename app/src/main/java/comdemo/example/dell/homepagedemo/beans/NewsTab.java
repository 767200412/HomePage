package comdemo.example.dell.homepagedemo.beans;

public class NewsTab {
//    Id": "a0e3593a-0ef9-e611-80e3-850a1737545e",
//            "Title": "经济",
//            "ParentId": null,
//            "Sort": 2,
//            "Notes": null,
//            "Imgurl": "http://files.fccn.cc/6362474249237806103332698.png"

    private String Id;
    private String Title;
    private String ParentId;
    private int Sort;
    private String Notes;
    private String Imgurl;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public int getSort() {
        return Sort;
    }

    public void setSort(int sort) {
        Sort = sort;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public void setImgurl(String imgurl) {
        Imgurl = imgurl;
    }
}
