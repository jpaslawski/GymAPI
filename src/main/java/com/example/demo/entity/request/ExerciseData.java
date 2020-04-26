package com.example.demo.entity.request;

public class ExerciseData {

    private String name;

    private String info;

    private String category;

    public ExerciseData() {
    }

    public ExerciseData(String name, String info, String category) {
        this.name = name;
        this.info = info;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
