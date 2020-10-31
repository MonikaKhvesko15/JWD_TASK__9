package com.epam.task9.director;

import com.epam.task9.data.DataException;
import com.epam.task9.data.JsonTrainCreator;
import com.epam.task9.data.TrainCreator;
import com.epam.task9.entity.Train;
import com.epam.task9.entity.Tunnel;
import com.epam.task9.logic.RailwaySemaphoreSignal;
import com.epam.task9.logic.TrainLoader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Director {

    public static final String TRAINS_JSON = "Trains.json";

    public static void main(String[] args) throws DataException {

        int threadNumber = Runtime.getRuntime().availableProcessors();
        System.out.println("Available number of threads: " + threadNumber);
        //create thread pool
        ExecutorService service = Executors.newFixedThreadPool(threadNumber);

        Tunnel tunnel = new Tunnel();

        //create train list
        TrainCreator creator = new JsonTrainCreator();
        List<Train> trainList = creator.createTrains(TRAINS_JSON).getTrainList();

        TrainLoader trainLoader = new TrainLoader(tunnel, trainList);

        RailwaySemaphoreSignal firstSemaphore = new RailwaySemaphoreSignal(tunnel, false);
        //RailwaySemaphoreSignal secondSemaphore = new RailwaySemaphoreSignal(tunnel, false);

        service.execute(trainLoader);
        service.execute(firstSemaphore);

        service.shutdown();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        try {
            if (!service.awaitTermination(60,timeUnit)) {
                service.shutdownNow();
            }
        } catch (InterruptedException ex) {
            service.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
