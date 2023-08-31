
import org.junit.jupiter.api.Test;

import java.sql.*;

public class PostgresBenchmark {

    interface Meassureable{
        void TestMethod();
    }

    public long Meassure(Meassureable meassureable){
        long startTime = System.nanoTime();
        meassureable.TestMethod();
        long stopTime = System.nanoTime();
        return (stopTime - startTime);
    }

    private static PostgresConnector postgresConnector;

    @Test
    public void Ini(){
        postgresConnector = new PostgresConnector();
        postgresConnector.connect();
        postgresConnector.initializeDatabase();
        postgresConnector.ImportData();
    }

    @Test
    public void Test1(){
        long l = Meassure(() -> {
            //TEST
        });
    }
}
