package GoncharenkoSS.test.model;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class Workers {
    private int id;
    @NotEmpty(message = "Field Name should not be empty")
    private String name;
    @NotEmpty(message = "Field Position should not be empty")
    private String position;
    @NotEmpty(message = "Field Avatar should not be empty")
    private String avatar;
    private List<Tasks> listTasks;

    public Workers(String name, String position, String avatar) {
        this.name = name;
        this.position = position;
        this.avatar = avatar;
    }

    public Workers() {
    }

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

    public List<Tasks> getListTasks() {
        return listTasks;
    }

    public void setListTasks(List<Tasks> listTasks) {
        this.listTasks = listTasks;
    }

}
