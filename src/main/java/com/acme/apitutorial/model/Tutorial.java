package com.acme.apitutorial.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tutorials")
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "published")
    private boolean published;
    @Column(name = "level")
    private int level;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Tutorial() {
    }
    public Tutorial(String title, String description, boolean published, int level, Date createdAt) {
        this.title = title;
        this.description = description;
        this.published = published;
        this.level = level;
        this.createdAt = createdAt;
    }
    public long getId() {
        return id;
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
    public boolean isPublished() {
        return published;
    }
    public void setPublished(boolean isPublished) {
        this.published = isPublished;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + ", level=" + level + "]";
    }
}
