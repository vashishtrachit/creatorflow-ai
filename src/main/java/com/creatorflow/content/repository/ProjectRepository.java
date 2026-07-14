package com.creatorflow.content.repository;

import com.creatorflow.content.model.Project;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProjectRepository {
    private final Map<Long, Project> db = new HashMap<>();

    public void save(Project project){
        db.put(project.getId(),project);
    }

    public Project findById(Long id){
        return db.get(id);
    }

    public Collection<Project> findAll(){
        return db.values();
    }
}
