package com.epam.task9.entity;


import com.epam.task9.exception.TunnelException;
import com.epam.task9.logic.Tunnel;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.log4j.Logger;

import java.util.Objects;

@JsonAutoDetect
public class Train implements Runnable {
    private int id;
    private boolean trafficStatus;
    private final Tunnel tunnel;
    private static final Logger log=Logger.getLogger(Train.class);

    public Train() {
        this.tunnel = Tunnel.getInstance();
    }

    public Train(int id, boolean trafficStatus) {
        this.id = id;
        this.trafficStatus = trafficStatus;
        this.tunnel = Tunnel.getInstance();
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

    public void changeTrafficStatus() {
        if (trafficStatus) {
            this.trafficStatus = false;
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

    @Override
    public void run() {
        try {
            if(!trafficStatus){
            tunnel.addTrain(this);
            changeTrafficStatus();
            tunnel.removeTrain(trafficStatus);
            }
        } catch (TunnelException e) {
            log.warn(String.format("An accident in the tunnel: %s", e.getMessage()));
        }
    }
}
