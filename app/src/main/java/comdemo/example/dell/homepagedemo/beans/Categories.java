package comdemo.example.dell.homepagedemo.beans;

public class Categories {
    private String __typename;
    private String name;

    private String Id;
    private String PublishTime;
    private int Ordering;
    private String TypeId;
    private String ParentId;
    private String Name;
    public String get__typename() {
        return __typename;
    }

    public void set__typename(String __typename) {
        this.__typename = __typename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return Name;
    }

    public void setName2(String Name) {
        this.Name = Name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String publishTime) {
        PublishTime = publishTime;
    }

    public int getOrdering() {
        return Ordering;
    }

    public void setOrdering(int ordering) {
        Ordering = ordering;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }
}
