package comdemo.example.dell.homepagedemo.beans;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Auto-generated: 2019-08-05 14:3:12
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Header {

    @JsonProperty("__typename")
    private String __typename;
    private String id;
    private String title;
    public void set__typename(String __typename) {
         this.__typename = __typename;
     }
     public String get__typename() {
         return __typename;
     }

    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

}