package com.epam.task9.entity;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.Objects;

@JsonAutoDetect
public class Train {
    private int id;
    private boolean trafficStatus;

    public Train() {

    }

    public Train(int id, boolean trafficStatus) {
        this.id = id;
        this.trafficStatus = trafficStatus;
    }

    public int getId() {
        return id;
    }

    public boolean getTrafficStatus() {
        return trafficStatus;
    }

    @Override
    public String toString() {
        return "Train{" + "id=" + id + ", trafficStatus=" + trafficStatus + '}';
    }

    public void changeTrafficStatus(){
        if(trafficStatus){
            this.trafficStatus=false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return id == train.id &&
                trafficStatus == train.trafficStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trafficStatus);
    }
}
