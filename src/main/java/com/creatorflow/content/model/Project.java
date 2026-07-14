package com.creatorflow.content.model;

public class Project {

    private Long id;
    private String topic;
    private ProjectStatus status;

    public Project() {
    }

    public Project(Long id, String topic, ProjectStatus status) {
        this.id = id;
        this.topic = topic;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }
}