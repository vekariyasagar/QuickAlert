package com.msd.project_group_7.model;

import java.io.Serializable;

public class CategoryModel implements Serializable {

    String name;
    int image;

    public CategoryModel(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
