package com.epam.task9.logic;

import com.epam.task9.entity.Train;
import com.epam.task9.entity.Tunnel;

import java.util.concurrent.TimeUnit;

public class RailwaySemaphoreSignal implements Runnable {

    private final Tunnel tunnel;
    private final boolean trainStatus;

    public RailwaySemaphoreSignal(Tunnel tunnel, boolean trainStatus) {
        this.tunnel = tunnel;
        this.trainStatus = trainStatus;
    }

    @Override
    public void run() {

        while (true) {
            try {
                //change traffic status
                TimeUnit timeUnit = TimeUnit.SECONDS;
                timeUnit.sleep(5);
                Train train = tunnel.removeTrain(trainStatus);
                if (train != null)
                    while (!train.getTrafficStatus()) {
                        train.changeTrafficStatus();
                    }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
