package com.epam.task9.data;

import com.epam.task9.entity.TrainsWrapper;
import com.epam.task9.exception.DataException;

public interface TrainCreator {
    TrainsWrapper createTrains(String filename) throws DataException;
}
