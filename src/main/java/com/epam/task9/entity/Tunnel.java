package com.epam.task9.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tunnel {
    private static final Lock lock = new ReentrantLock();
    private List<Train> trainStorage;
    private static final int maxTrainsInTunnel = 4;
    private static final int minTrainsInTunnel = 0;
    private int trainCounter = 0;

    public Tunnel() {
        trainStorage = new ArrayList<>();
    }

    public synchronized boolean addTrain(Train train) {

        try {
            lock.lock();
            if (trainCounter < maxTrainsInTunnel) {
                //WAITING -> RUNNABLE
                notifyAll();
                trainStorage.add(train);
                ++trainCounter;
                String info = String.format("+%s is successfully passed into the tunnel\ntrain storage: %s\n", train.toString(), trainCounter);
                System.out.println(info);
            } else {
                System.out.println("There is no place for a train in the tunnel:\nthread: " + Thread.currentThread().getName()+" is waiting");
                System.out.println("Train storage: " + trainCounter+"\n");
                //thread waits when trains in tunnel decrease
                wait();
               // return false;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return true;
    }

    //this method take and remove train with his status from tunnel
    public synchronized Train removeTrain(boolean trainStatus) {

        try {
            if (trainCounter > minTrainsInTunnel) {
                //WAITING -> RUNNABLE
                notifyAll();

                for (Train train : trainStorage) {
                    if (train.getTrafficStatus() == trainStatus) {
                        --trainCounter;
                        System.out.println("-"+train.toString()+"is deleted from the tunnel");
                        System.out.println("Train storage: " + trainCounter+"\n");
                        trainStorage.remove(train);
                        return train;
                    }
                }
            }else {
                System.out.println("0 < There are no trains in the tunnel\n");
                //thread wait when trains increase
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
