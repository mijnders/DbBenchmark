import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

class Neo4JCreateTest {

    interface Measurable {
        void TestMethod();
    }

    public static Neo4JConnector neo4jConnector;
    public static List<String[]> allData = new ArrayList<>();
    public static final String delimiter = ",";

    @BeforeAll
    public static void Ini(){
        neo4jConnector = new Neo4JConnector();
        neo4jConnector.connect();
        neo4jConnector.initializeDatabase();

        for (int i = 1; i <= 10; i++) {
            List<String[]> data = read("C:\\Users\\mijnders\\RiderProjects\\DbBenchmark\\CSV\\Owners\\Owners" + i + ".csv");
            allData.addAll(data);
        }
    }

    @AfterEach
    public void TearDownNeo4JDatabase(){
        neo4jConnector.send(":auto MATCH (n:Owners) CALL {WITH n DETACH DELETE n} IN TRANSACTIONS");
    }

    @AfterAll
    public static void EmptyNeo4JDatabase(){
        neo4jConnector.send(":auto MATCH (n) CALL {WITH n DETACH DELETE n} IN TRANSACTIONS");
    }

    public long Measure(Measurable measurable){
        long startTime = System.nanoTime();
        measurable.TestMethod();
        long stopTime = System.nanoTime();
        return (stopTime - startTime);
    }


    @Test
    public void createOne(){
        String[] oneData = allData.get(0);
        String cypherQuery = "MATCH (a:Customer), (b:Car) WHERE a.customerId = " + oneData[0] +
                " AND b.carId = " + oneData[1] +
                " CREATE (a)-[r:Owners{Color: '" + oneData[2] + "', date: '" + oneData[3] + "'}]->(b) RETURN type(r);";
        AtomicBoolean result = new AtomicBoolean(false);
        long l = Measure(() -> result.set(neo4jConnector.send(cypherQuery)));
        if(result.get()) System.out.println(l);
    }
    @Test
    public void createOneHundred(){
        StringBuilder cypherQuery = new StringBuilder();
        for (int i = 0; i < 100; i++){
            String[] oneData = allData.get(i);
            cypherQuery.append("MATCH (a:Customer), (b:Car) WHERE a.customerId = ").append(oneData[0]).append(" AND b.carId = ").append(oneData[1]).append(" CREATE (a)-[r:Owners{Color: '").append(oneData[2]).append("', date: '").append(oneData[3]).append("'}]->(b) RETURN type(r);\n");
        }
        AtomicBoolean result = new AtomicBoolean(false);
        String finalCypherQuery = cypherQuery.toString();
        long l = Measure(() -> result.set(neo4jConnector.send(finalCypherQuery)));
        if(result.get()) System.out.println(l);
    }
    @Test
    public void createTenThousand(){
        StringBuilder cypherQuery = new StringBuilder();
        for (int i = 0; i < 10000; i++){
            String[] oneData = allData.get(i);
            cypherQuery.append("MATCH (a:Customer), (b:Car) WHERE a.customerId = ").append(oneData[0]).append(" AND b.carId = ").append(oneData[1]).append(" CREATE (a)-[r:Owners{Color: '").append(oneData[2]).append("', date: '").append(oneData[3]).append("'}]->(b) RETURN type(r);\n");
        }
        AtomicBoolean result = new AtomicBoolean(false);
        String finalCypherQuery = cypherQuery.toString();
        long l = Measure(() -> result.set(neo4jConnector.send(finalCypherQuery)));
        if(result.get()) System.out.println(l);
    }
    @Test
    public void createHundredThousand(){
        StringBuilder cypherQuery = new StringBuilder();
        for (int i = 0; i < 100000; i++){
            String[] oneData = allData.get(i);
            cypherQuery.append("MATCH (a:Customer), (b:Car) WHERE a.customerId = ").append(oneData[0]).append(" AND b.carId = ").append(oneData[1]).append(" CREATE (a)-[r:Owners{Color: '").append(oneData[2]).append("', date: '").append(oneData[3]).append("'}]->(b) RETURN type(r);\n");
        }
        AtomicBoolean result = new AtomicBoolean(false);
        String finalCypherQuery = cypherQuery.toString();
        long l = Measure(() -> result.set(neo4jConnector.send(finalCypherQuery)));
        if(result.get()) System.out.println(l);
    }

    @Test
    public void createOneMillion(){
        StringBuilder cypherQuery = new StringBuilder();
        for (int i = 0; i < 1000000; i++){
            String[] oneData = allData.get(i);
            cypherQuery.append("MATCH (a:Customer), (b:Car) WHERE a.customerId = ").append(oneData[0]).append(" AND b.carId = ").append(oneData[1]).append(" CREATE (a)-[r:Owners{Color: '").append(oneData[2]).append("', date: '").append(oneData[3]).append("'}]->(b) RETURN type(r);\n");
        }
        AtomicBoolean result = new AtomicBoolean(false);
        String finalCypherQuery = cypherQuery.toString();
        long l = Measure(() -> result.set(neo4jConnector.send(finalCypherQuery)));
        if(result.get()) System.out.println(l);
    }

    public static List<String[]> read(String csvFile) {
        List<String[]> rows = new ArrayList<>();

        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

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