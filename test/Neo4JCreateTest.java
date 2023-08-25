import org.junit.gen5.api.AfterEach;
import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;
import java.sql.*;
class Neo4JCreateTest {

    interface Meassureable{
        void TestMethod();
    }

    private Neo4JConnector neo4JConnector;

    @BeforeAll
    public void Ini(){
        neo4JConnector = new Neo4JConnector();
        neo4JConnector.connect();
        neo4JConnector.initializeDatabase();
    }

    @BeforeEach
    public void InitializePostgresDatabase(){
        
    }

    @AfterEach
    public void TearDownPostgresDatabase(){
        neo4JConnector.send("MATCH (n:Owners)\n" +
                "DETACH DELETE n");
    }

    public long Meassure(Meassureable meassureable){
        long startTime = System.nanoTime();
        meassureable.TestMethod();
        long stopTime = System.nanoTime();
        return (stopTime - startTime);
    }


    @Test
    public void createOne(){
        long l = Meassure(() -> {
            //TEST
        });
    }
    @Test
    public void createOneHundred(){
        long l = Meassure(() -> {
            //TEST
        });
    }
    @Test
    public void createTenThousand(){
        long l = Meassure(() -> {
            //TEST
        });
    }
    @Test
    public void createHundredThousand(){
        long l = Meassure(() -> {
            //TEST
        });
    }

    @Test
    public void createOneMillion(){
        long l = Meassure(() -> {
            //TEST
        });
    }
}