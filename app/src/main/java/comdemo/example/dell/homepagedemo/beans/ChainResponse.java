package comdemo.example.dell.homepagedemo.beans;

public class ChainResponse {
     private String Id;
     private String TypeId;
     private String ParentId;
     private String Name;
     private String ActionLink;
     private String Description;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getActionLink() {
        return ActionLink;
    }

    public void setActionLink(String actionLink) {
        ActionLink = actionLink;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
