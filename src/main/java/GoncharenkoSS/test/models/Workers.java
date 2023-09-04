package GoncharenkoSS.test.models;

public class Workers {
    private int id;
    private String name;
    private String position;
    private String avatar;

    public Workers(int id, String name, String position, String avatar) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.avatar = avatar;
    }
    public Workers(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
