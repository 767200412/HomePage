package comdemo.example.dell.homepagedemo.beans;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Auto-generated: 2019-08-05 14:3:12
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Data {

    private Banner banner;
    @JsonProperty("platformArticleSelected")
    private Platformarticleselected platformArticleSelected;
    @JsonProperty("indexTop")
    private Indextop indexTop;
    private Menuitems menuItems;
    @JsonProperty("polymericCompanies")
    private Polymericcompanies polymericCompanies;

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public Platformarticleselected getPlatformArticleSelected() {
        return platformArticleSelected;
    }

    public void setPlatformArticleSelected(Platformarticleselected platformArticleSelected) {
        this.platformArticleSelected = platformArticleSelected;
    }

    public Indextop getIndexTop() {
        return indexTop;
    }

    public void setIndexTop(Indextop indexTop) {
        this.indexTop = indexTop;
    }

    public Menuitems getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Menuitems menuItems) {
        this.menuItems = menuItems;
    }

    public Polymericcompanies getPolymericCompanies() {
        return polymericCompanies;
    }

    public void setPolymericCompanies(Polymericcompanies polymericCompanies) {
        this.polymericCompanies = polymericCompanies;
    }
}