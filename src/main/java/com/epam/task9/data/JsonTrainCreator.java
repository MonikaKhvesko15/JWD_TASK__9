package com.epam.task9.data;

import com.epam.task9.entity.TrainsWrapper;
import com.epam.task9.exception.DataException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;

public class JsonTrainCreator implements TrainCreator {

    private final ObjectMapper mapper;

    public JsonTrainCreator() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public TrainsWrapper createTrains(String filename) throws DataException {
        try {
            File file = new File(filename);
            return mapper.readValue(file, TrainsWrapper.class);
        } catch (IOException e) {
            throw new DataException(e.getMessage(),e);
        }
    }
}
