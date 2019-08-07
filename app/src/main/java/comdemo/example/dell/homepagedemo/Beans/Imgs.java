package comdemo.example.dell.homepagedemo.Beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Imgs {

    @JsonProperty("__typename")
    private String __typename;
    private List<String> items;
    public void set__typename(String __typename) {
        this.__typename = __typename;
    }
    public String get__typename() {
        return __typename;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
    public List<String> getItems() {
        return items;
    }

}