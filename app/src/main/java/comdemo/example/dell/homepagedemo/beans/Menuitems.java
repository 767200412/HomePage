package comdemo.example.dell.homepagedemo.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Menuitems {

    @JsonProperty("__typename")
    private String __typename;
    private List<Items> items;
    public void set__typename(String __typename) {
        this.__typename = __typename;
    }
    public String get__typename() {
        return __typename;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
    public List<Items> getItems() {
        return items;
    }

}
