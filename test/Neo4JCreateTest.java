import org.junit.gen5.api.AfterEach;
import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;
import java.sql.*;
class Neo4JCreateTest {

    interface Meassureable{
        void TestMethod();
    }

    public Neo4JConnector neo4jConnector;
    public static List<String[]> allData = new ArrayList<>();
    public static final String delimiter = ",";

    @BeforeAll
    public void Ini(){
        neo4jConnector = new Neo4jConnector();
        neo4jConnector.connect();
        neo4jConnector.initializeDatabase();

        for (int i = 1; i <= 10; i++) {
            List<String[]> data = read("C:\\Users\\mijnders\\RiderProjects\\DbBenchmark\\CSV\\Owners\\Owners" + i + ".csv");
            allData.addAll(data);
        }
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
        String[] oneData = allData.get(0); // Nehmen Sie den ersten Datensatz für diesen Test.
        String cypherQuery = "MATCH (a:Customer), (b:Car) WHERE a.customerId = " + oneData[0] +
                " AND b.carId = " + oneData[1] +
                " CREATE (a)-[r:Owners{Color: '" + oneData[2] + "', date: '" + oneData[3] + "'}]->(b) RETURN type(r);";
        boolean result;
        long l = Meassure(() -> {
            result = neo4jConnector.send(cypherQuery);
        });
        if(result) System.out.println(l);
    }
    @Test
    public void createOneHundred(){
        String cypherquery += "";
        for (int i = 0; i < 100; i++){
            String[] oneData = allData.get(i); // Nehmen Sie den ersten Datensatz für diesen Test.
            String cypherQuery = "MATCH (a:Customer), (b:Car) WHERE a.customerId = " + oneData[0] +
                    " AND b.carId = " + oneData[1] +
                    " CREATE (a)-[r:Owners{Color: '" + oneData[2] + "', date: '" + oneData[3] + "'}]->(b) RETURN type(r);\n";
        }
        boolean result;
        long l = Meassure(() -> {
            result = neo4jConnector.send(cypherQuery);
        });
        if(result) System.out.println(l);
    }
    @Test
    public void createTenThousand(){
        String cypherquery += "";
        for (int i = 0; i < 10000; i++){
            String[] oneData = allData.get(i); // Nehmen Sie den ersten Datensatz für diesen Test.
            String cypherQuery = "MATCH (a:Customer), (b:Car) WHERE a.customerId = " + oneData[0] +
                    " AND b.carId = " + oneData[1] +
                    " CREATE (a)-[r:Owners{Color: '" + oneData[2] + "', date: '" + oneData[3] + "'}]->(b) RETURN type(r);\n";
        }
        boolean result;
        long l = Meassure(() -> {
            result = neo4jConnector.send(cypherQuery);
        });
        if(result) System.out.println(l);
    }
    @Test
    public void createHundredThousand(){
        String cypherquery += "";
        for (int i = 0; i < 100000; i++){
            String[] oneData = allData.get(i); // Nehmen Sie den ersten Datensatz für diesen Test.
            String cypherQuery = "MATCH (a:Customer), (b:Car) WHERE a.customerId = " + oneData[0] +
                    " AND b.carId = " + oneData[1] +
                    " CREATE (a)-[r:Owners{Color: '" + oneData[2] + "', date: '" + oneData[3] + "'}]->(b) RETURN type(r);\n";
        }
        boolean result;
        long l = Meassure(() -> {
            result = neo4jConnector.send(cypherQuery);
        });
        if(result) System.out.println(l);
    }

    @Test
    public void createOneMillion(){
        String cypherquery += "";
        for (int i = 0; i < 1000000; i++){
            String[] oneData = allData.get(i); // Nehmen Sie den ersten Datensatz für diesen Test.
            String cypherQuery = "MATCH (a:Customer), (b:Car) WHERE a.customerId = " + oneData[0] +
                    " AND b.carId = " + oneData[1] +
                    " CREATE (a)-[r:Owners{Color: '" + oneData[2] + "', date: '" + oneData[3] + "'}]->(b) RETURN type(r);\n";
        }
        boolean result;
        long l = Meassure(() -> {
            result = neo4jConnector.send(cypherQuery);
        });
        if(result) System.out.println(l);
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