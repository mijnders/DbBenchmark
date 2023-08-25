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
    public static List<String[]> allData = new ArrayList<>();
    public static final String delimiter = ",";

    @BeforeAll
    public void Ini(){
        neo4JConnector = new Neo4JConnector();
        neo4JConnector.connect();
        neo4JConnector.initializeDatabase();

        for (int i = 1; i <= 10; i++) {
            List<String[]> data = read("C:\\Users\\mijnders\\RiderProjects\\DbBenchmark\\CSV\\Owners\\Owners" + i + ".csv");
            allData.addAll(data);
        }
    }

    @BeforeEach
    public void InitializeNeo4JDatabase(){

    }

    @AfterEach
    public void TearDownNeo4JDatabase(){
        neo4JConnector.send(":auto MATCH (n:Owners) CALL {WITH n DETACH DELETE n} IN TRANSACTIONS");
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

    public static List<String[]> read(String csvFile) {
        List<String[]> rows = new ArrayList<>();

        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";

            while ((line = br.readLine()) != null) {
                String[] tempArr = line.split(delimiter);
                rows.add(tempArr);
            }

            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return rows;
    }
}