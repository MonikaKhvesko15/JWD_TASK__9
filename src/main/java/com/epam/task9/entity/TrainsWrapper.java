package com.epam.task9.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.List;
import java.util.Objects;

@JsonAutoDetect
public class TrainsWrapper {
    private List<Train> trainList;

    public List<Train> getTrainList() {
        return trainList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainsWrapper that = (TrainsWrapper) o;
        return Objects.equals(trainList, that.trainList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainList);
    }
}
