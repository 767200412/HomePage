package comdemo.example.dell.homepagedemo.beans;

import java.util.List;

public class Polymericcompanies {
    private String __typename;
    private Pageinfo pageInfo;
    private List<Items> items;

    public String get__typename() {
        return __typename;
    }

    public void set__typename(String __typename) {
        this.__typename = __typename;
    }

    public Pageinfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(Pageinfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
