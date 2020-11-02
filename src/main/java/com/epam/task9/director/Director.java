package com.epam.task9.director;

import com.epam.task9.exception.DataException;
import com.epam.task9.data.JsonTrainCreator;
import com.epam.task9.data.TrainCreator;
import com.epam.task9.entity.Train;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Director {

    private static final String TRAINS_JSON = "Trains.json";
    private static final Logger log = Logger.getLogger(Director.class);

    public static void main(String[] args) throws DataException {

        TrainCreator creator = new JsonTrainCreator();
        List<Train> trainList = creator.createTrains(TRAINS_JSON).getTrainList();

        int threadNumber = trainList.size();
        log.info("Thread number: " + threadNumber);

        ExecutorService service = Executors.newFixedThreadPool(threadNumber);
        trainList.forEach(service::submit);

        service.shutdown();

        try {
            if (!service.awaitTermination(30, TimeUnit.SECONDS)) {
                service.shutdownNow();
                if (!service.awaitTermination(30, TimeUnit.SECONDS))
                    log.warn("Service not terminate!");
            }
        } catch (InterruptedException e) {
            service.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
