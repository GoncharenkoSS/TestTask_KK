package GoncharenkoSS.test.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;

import java.sql.Time;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Tasks {
    private int id;
    @NotEmpty(message = "Field Title should not be empty")
    private String title;
    @NotEmpty(message = "Field Description should not be empty")
    private String description;
    private Time time;
    @NotEmpty(message = "Field Time should not be empty")
    private String status;
    private int performer;

    public Tasks(int id, String title, String description, Time time, String status, int performer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.status = status;
        this.performer = performer;
    }

    public Tasks() {
    }

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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
