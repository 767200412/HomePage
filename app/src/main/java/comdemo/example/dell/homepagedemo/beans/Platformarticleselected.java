package comdemo.example.dell.homepagedemo.beans;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Auto-generated: 2019-08-05 14:3:12
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Platformarticleselected {

    @JsonProperty("__typename")
    private String __typename;
    private List<Header> header;
    public void set__typename(String __typename) {
         this.__typename = __typename;
     }
     public String get__typename() {
         return __typename;
     }

    public void setHeader(List<Header> header) {
         this.header = header;
     }
     public List<Header> getHeader() {
         return header;
     }

}