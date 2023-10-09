import org.junit.jupiter.api.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class Neo4JCreateTest {

    interface Measurable {
        void TestMethod();
    }

    public static Neo4JConnector neo4jConnector;
    public static List<String[]> allData = new ArrayList<>();
    public static final String delimiter = ",";

    @BeforeAll
    public static void Ini(){
        try{
            neo4jConnector = new Neo4JConnector();
            neo4jConnector.connect();
            //neo4jConnector.initializeDatabase();

            for (int i = 1; i <= 10; i++) {
                List<String[]> data = read("C:\\Users\\mijnders\\RiderProjects\\DbBenchmark\\CSV\\Owners\\Owners" + i + ".csv");
                allData.addAll(data);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @AfterEach
    public void EmptyRelations(){
       /* try{
            neo4jConnector.send("MATCH (n:Customer)-[r:IS_OWNER_OF]->() DETACH DELETE r;");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }*/
    }

    @AfterAll
    public static void EmptyDatabase(){
        /*try{
            neo4jConnector.send("MATCH (n) DETACH DELETE n");
            neo4jConnector.send("DROP CONSTRAINT ON (car:Car) ASSERT car.id IS UNIQUE");
            neo4jConnector.send("DROP CONSTRAINT ON (customer:Customer) ASSERT customer.id IS UNIQUE");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }*/
    }

    public long Measure(Measurable measurable){
        long startTime = System.nanoTime();
        measurable.TestMethod();
        long stopTime = System.nanoTime();
        return (stopTime - startTime);
    }


    @Test
    public void createOne(){
        List<String> cypherQueries = new ArrayList<>();
        List<Long> executionTimes = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            String[] oneData = allData.get(i);
            String cypherQuery = "MATCH (a:Customer), (b:Car) WHERE a.id = " +
                    oneData[0] + " AND b.id = " + oneData[1] +
                    " CREATE (a)-[r:IS_OWNER_OF {Color: '" + oneData[2] + "', date: '" + oneData[3] + "'}]->(b)";
            cypherQueries.add(cypherQuery);
        }

        AtomicBoolean result = new AtomicBoolean(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timestamp = "[" + dateFormat.format(new Date()) + "]";

        for (String cypherQuery : cypherQueries) {
            executionTimes.add(Measure(() -> result.set(neo4jConnector.send(cypherQuery))));
            assertTrue(result.get());
        }

        if(result.get()){
            String fileName = "createOne.csv";
            writeExecutionTimesToFile(fileName, executionTimes, timestamp);
        }
    }
    @Test
    public void createOneHundred(){
        List<String> cypherQueries = new ArrayList<>();
        List<Long> executionTimes = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            String[] oneData = allData.get(i);
            String cypherQuery = "MATCH (a:Customer), (b:Car) WHERE a.id = " +
                    oneData[0] + " AND b.id = " + oneData[1] +
                    " CREATE (a)-[r:IS_OWNER_OF {Color: '" + oneData[2] + "', date: '" + oneData[3] + "'}]->(b) RETURN type(r);\n";
            cypherQueries.add(cypherQuery);
        }

        AtomicBoolean result = new AtomicBoolean(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timestamp = "[" + dateFormat.format(new Date()) + "]";

        for (String cypherQuery : cypherQueries) {
            executionTimes.add(Measure(() -> result.set(neo4jConnector.send(cypherQuery))));
            assertTrue(result.get());
        }
        if(result.get()){
        String fileName = "createOneHundred.csv";
        writeExecutionTimesToFile(fileName, executionTimes, timestamp);
        }
    }
    @Test
    public void createTenThousand(){
        List<String> cypherQueries = new ArrayList<>();
        List<Long> executionTimes = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            String[] oneData = allData.get(i);
            String cypherQuery = "MATCH (a:Customer), (b:Car) WHERE a.id = " +
                    oneData[0] + " AND b.id = " + oneData[1] +
                    " CREATE (a)-[r:IS_OWNER_OF {Color: '" + oneData[2] + "', date: '" + oneData[3] + "'}]->(b) RETURN type(r);\n";
            cypherQueries.add(cypherQuery);
        }

        AtomicBoolean result = new AtomicBoolean(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timestamp = "[" + dateFormat.format(new Date()) + "]";

        for (String cypherQuery : cypherQueries) {
            executionTimes.add(Measure(() -> result.set(neo4jConnector.send(cypherQuery))));
            assertTrue(result.get());
        }

        if(result.get()){
        String fileName = "createTenThousand.csv";
        writeExecutionTimesToFile(fileName, executionTimes, timestamp);
            }
    }
    @Test
    public void createHundredThousand(){
        List<String> cypherQueries = new ArrayList<>();
        List<Long> executionTimes = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            String[] oneData = allData.get(i);
            String cypherQuery = "MATCH (a:Customer), (b:Car) WHERE a.id = " +
                    oneData[0] + " AND b.id = " + oneData[1] +
                    " CREATE (a)-[r:IS_OWNER_OF {Color: '" + oneData[2] + "', date: '" + oneData[3] + "'}]->(b) RETURN type(r);\n";
            cypherQueries.add(cypherQuery);
        }

        AtomicBoolean result = new AtomicBoolean(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timestamp = "[" + dateFormat.format(new Date()) + "]";

        for (String cypherQuery : cypherQueries) {
            executionTimes.add(Measure(() -> result.set(neo4jConnector.send(cypherQuery))));
            assertTrue(result.get());
        }
        if(result.get()){
        String fileName = "createHundredThousand.csv";
        writeExecutionTimesToFile(fileName, executionTimes, timestamp);
            }
    }

    @Test
    public void createOneMillion(){
        List<String> cypherQueries = new ArrayList<>();
        List<Long> executionTimes = new ArrayList<>();

        for (int i = 0; i < 1000000; i++) {
            String[] oneData = allData.get(i);
            String cypherQuery = "MATCH (a:Customer), (b:Car) WHERE a.id = " +
                    oneData[0] + " AND b.id = " + oneData[1] +
                    " CREATE (a)-[r:IS_OWNER_OF {Color: '" + oneData[2] + "', date: '" + oneData[3] + "'}]->(b) RETURN type(r);\n";
            cypherQueries.add(cypherQuery);
        }

        AtomicBoolean result = new AtomicBoolean(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timestamp = "[" + dateFormat.format(new Date()) + "]";

        for (String cypherQuery : cypherQueries) {
            executionTimes.add(Measure(() -> result.set(neo4jConnector.send(cypherQuery))));
            assertTrue(result.get());
        }

        if(result.get()){
        String fileName = "createOneMillion.csv";
        writeExecutionTimesToFile(fileName, executionTimes, timestamp);
        }
    }

    private void writeExecutionTimesToFile(String fileName, List<Long> executionTimes, String timestamp) {
        try (FileWriter writer = new FileWriter("testLogs/Neo4J/" + fileName, true)) { // 'true' bedeutet anhängen
            long summe = 0;
            for (Long time : executionTimes) {
                summe += time;
            }
            long average = executionTimes.isEmpty() ? 0 : summe / executionTimes.size();
            writer.write( "Durchschnitt: " + average + " ns\n");
            writer.write("Summe: " + summe + "ns - " + TimeUnit.NANOSECONDS.toMillis(summe) + "ms - " + TimeUnit.NANOSECONDS.toSeconds(summe) + "s - " + TimeUnit.NANOSECONDS.toMinutes(summe) + "m\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> read(String csvFile) {
        List<String[]> rows = new ArrayList<>();

        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                if(!line.startsWith("CustomerID")){
                    String[] tempArr = line.split(delimiter);
                    rows.add(tempArr);
                }
            }

            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return rows;
    }
}