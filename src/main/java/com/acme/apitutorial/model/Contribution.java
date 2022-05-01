package com.acme.apitutorial.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "contribution")
public class Contribution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;


    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "techno_id")
    private Techno techno;


    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Techno getTechno() {
        return techno;
    }

    public void setTechno(Techno techno) {
        this.techno = techno;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}