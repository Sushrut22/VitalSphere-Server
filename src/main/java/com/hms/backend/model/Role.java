package com.hms.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
public class Role {
    @Id
    private String id;

    private StaffRole name;

    public Role() {

    }

    public Role(StaffRole name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StaffRole getName() {
        return name;
    }

    public void setName(StaffRole name) {
        this.name = name;
    }
}
