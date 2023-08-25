import org.junit.gen5.api.AfterEach;
import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;
import java.sql.*;

public class PostgresBenchmark {

    interface Meassureable{
        void TestMethod();
    }

    private PostgresConnector postgresConnector;

    @BeforeAll
    public void Ini(){
        postgresConnector = new PostgresConnector();
        postgresConnector.connect();
    }

    @BeforeEach
    public void InitializePostgresDatabase(){
        postgresConnector.initializeDatabase();
    }

    @AfterEach
    public void TearDownPostgresDatabase(){

    }

    public long Meassure(Meassureable meassureable){
        long startTime = System.nanoTime();
        meassureable.TestMethod();
        long stopTime = System.nanoTime();
        return (stopTime - startTime);
    }

    @Test
    public void Test1(){
        long l = Meassure(() -> {
            //TEST
        });
    }
}
