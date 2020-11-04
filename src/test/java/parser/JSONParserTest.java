package parser;

import com.epam.task9.exception.DataException;
import com.epam.task9.entity.Train;
import com.epam.task9.data.JsonTrainCreator;
import com.epam.task9.data.TrainCreator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class JSONParserTest {

    public static final String TRAINS_TEST_JSON = "src/test/resources/TrainsTest.json";

    @Test
    public void testJSONParserShouldReturnTrainListWhenFileExists() throws DataException {
        //given
        Train firstTrain = new Train(1, true);
        Train secondTrain = new Train(2, false);
        List<Train> trainListExpected = Arrays.asList(firstTrain, secondTrain);

        TrainCreator creator = new JsonTrainCreator();

        //when
        List<Train> trainListActual=creator.createTrains(TRAINS_TEST_JSON).getTrainList();

        //then
         Assert.assertEquals(trainListExpected, trainListActual);
    }
}
