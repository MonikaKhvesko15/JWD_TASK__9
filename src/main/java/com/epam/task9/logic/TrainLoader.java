package com.epam.task9.logic;


import com.epam.task9.entity.Train;
import com.epam.task9.entity.Tunnel;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TrainLoader implements Runnable {

    private static final Lock lock = new ReentrantLock();
    private final Tunnel tunnel;
    private final List<Train> trainList;

    public TrainLoader(Tunnel tunnel, List<Train> trainList) {
        this.tunnel = tunnel;
        this.trainList = trainList;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            for (Train train : trainList) {
                if(!train.getTrafficStatus()) {
                    Thread.currentThread().setName("Train loader for train with id{" + train.getId() + "}");
                    System.out.println(Thread.currentThread().getName());
                    tunnel.addTrain(train);
                }else{
                    System.out.println(train.toString()+" has already passed the tunnel");
                }
            }
        } finally{
        lock.unlock();
        }
    }

}
