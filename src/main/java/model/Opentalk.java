package model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "opentalk")
public class Opentalk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    private String status;

    public Opentalk() {}

    public Opentalk(int id, String title, LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Opentalk(Opentalk opentalk) {
        this.id = opentalk.id;
        this.title = opentalk.title;
        this.startTime = opentalk.startTime;
        this.endTime = opentalk.endTime;
        this.status = opentalk.status;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Opentalk [id=" + id + ", title=" + title + ", startTime=" + startTime + ", endTime=" + endTime + ", status=" + status + "]";
    }
    
    public void updateFrom(Opentalk opentalk) {
        this.title = opentalk.title;
        this.startTime = opentalk.startTime;
        this.endTime = opentalk.endTime;
        this.status = opentalk.status;
    }
}
