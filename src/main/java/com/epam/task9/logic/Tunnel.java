package com.epam.task9.logic;

import com.epam.task9.entity.Train;
import com.epam.task9.exception.TunnelException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tunnel {
    private static Tunnel instance = null;

    private static final Lock TUNNEL_LOCKER = new ReentrantLock();
    private static final Lock TRAIN_LOCKER = new ReentrantLock();
    private final List<Train> trainStorage = new ArrayList<>();
    private static final int maxTrainsInTunnel = 4;
    private static final int minTrainsInTunnel = 0;
    private int trainCounter = 0;

    private static final Logger log = Logger.getLogger(Tunnel.class);

    private Tunnel() {
    }

    public static Tunnel getInstance() {
        Tunnel localInstance = instance;
        if (localInstance == null) {
            TUNNEL_LOCKER.lock();
            try {
                localInstance = instance;
                if (localInstance == null) {
                    instance = new Tunnel();
                }
            } finally {
                TUNNEL_LOCKER.unlock();
            }
        }
        return instance;
    }


    public synchronized boolean addTrain(Train train) throws TunnelException {
        try {
            TRAIN_LOCKER.lock();
            if (trainCounter < maxTrainsInTunnel) {
                notifyAll();
                trainStorage.add(train);
                ++trainCounter;
                String info = String.format("+%s is successfully passed into the tunnel\ntrain storage: %s\n", train.toString(), trainCounter);
                log.info(info);
                return true;
            } else {
                log.info("There is no place for a train in the tunnel:\nthread: " + Thread.currentThread().getName() + " is waiting");
                log.info("Train storage: " + trainCounter + "\n");
                wait();
            }
        } catch (InterruptedException e) {
            throw new TunnelException(e.getMessage(), e);
        } finally {
            TRAIN_LOCKER.unlock();
        }
        return false;
    }

    public synchronized boolean removeTrain(boolean trainStatus) throws TunnelException {

        try {

            if (trainCounter >= maxTrainsInTunnel) {
                notifyAll();
                for (Train train : trainStorage) {
                    if (train.getTrafficStatus() == trainStatus) {
                        --trainCounter;
                        log.info("-" + train.toString() + "is deleted from the tunnel");
                        log.info("Train storage: " + trainCounter);
                        trainStorage.remove(train);
                        return true;
                    }
                }
            } else if (trainCounter == minTrainsInTunnel) {
                log.info("0 < There are no trains in the tunnel\n");
                wait();
            }

        } catch (InterruptedException e) {
            throw new TunnelException(e.getMessage(), e);
        }
        return false;
    }
}
