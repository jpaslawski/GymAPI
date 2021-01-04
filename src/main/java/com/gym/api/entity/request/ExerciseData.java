package com.gym.api.entity.request;

public class ExerciseData {

    private String name;

    private String namePL;

    private String info;

    private String infoPL;

    private String category;

    public ExerciseData() {
    }

    public ExerciseData(String name, String namePL, String info, String infoPL, String category) {
        this.name = name;
        this.namePL = namePL;
        this.info = info;
        this.infoPL = infoPL;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePL() {
        return namePL;
    }

    public void setNamePL(String namePL) {
        this.namePL = namePL;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfoPL() {
        return infoPL;
    }

    public void setInfoPL(String infoPL) {
        this.infoPL = infoPL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
