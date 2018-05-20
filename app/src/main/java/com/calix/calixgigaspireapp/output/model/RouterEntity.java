package com.calix.calixgigaspireapp.output.model;

import java.io.Serializable;

public class RouterEntity implements Serializable {

    private String id;
    private String name;
    private String type;

    public String getId() {
        return id;
    }

    public RouterEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RouterEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public RouterEntity setType(String type) {
        this.type = type;
        return this;
    }
}
