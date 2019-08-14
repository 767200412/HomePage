package comdemo.example.dell.homepagedemo.beans;

public class Types {
    private String Id;
    private String PublishTime;
    private int Ordering;
    private String Icon;
    private String Name;

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

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
