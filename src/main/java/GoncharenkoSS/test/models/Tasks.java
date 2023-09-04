package GoncharenkoSS.test.models;

public class Tasks {
    private int id;
    private String title;
    private String description;
    private String time;
    private String status;
    private String performer;

    public Tasks(int id, String title, String description, String time, String status, String performer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.status = status;
        this.performer = performer;
    }
    public Tasks(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }
}
