package com.gym.api.entity.request;

public class WorkoutData {

    private String name;

    private String namePL;

    private String info;

    private String infoPL;

    public WorkoutData() {

    }

    public WorkoutData(String name, String namePL, String info, String infoPL) {
        this.name = name;
        this.namePL = namePL;
        this.info = info;
        this.infoPL = infoPL;
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

    public void setNamePL(String namePl) {
        this.namePL = namePl;
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
}
