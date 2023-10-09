import org.junit.jupiter.api.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class Neo4JReadTest {

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
            var result = neo4jConnector.connect();
            if(result){
                //neo4jConnector.initializeDatabase();
                String[] queries = new String[10];
                for (int i = 1; i <= 10; i++) {
                    List<String[]> data = read("C:\\Users\\mijnders\\RiderProjects\\DbBenchmark\\CSV\\Owners\\Owners" + i + ".csv");
                    allData.addAll(data);
                    String ownerQuery = "LOAD CSV WITH HEADERS FROM 'file:///Owners/Owners" + i + ".csv' AS line\n" +
                            "MATCH (cu:Customer {id: toInteger(line.id)}), (ca:Car {id: toInteger(line.id)}) CREATE (cu)-[:IS_OWNER_OF {color: line.Carcolor, date: line.Date}]->(ca)";
                    queries[i - 1] = ownerQuery;
                }
                for (String query : queries) {
                    neo4jConnector.send(query);
                }
            }
            else{
                while(true){
                    System.err.println("FAILED");
                    Scanner s = new Scanner(System.in);
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @AfterEach
    public void EmptyRelations(){
    }

    @AfterAll
    public static void EmptyDatabase(){
        /*try{
            neo4jConnector.send("MATCH (n) DETACH DELETE n");
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
    public void readOne(){
        List<String> cypherQueries = new ArrayList<>();
        List<Long> executionTimes = new ArrayList<>();

        for (int i = 1; i < 2; i++) {
            String[] oneData = allData.get(i);
            String cypherQuery = "MATCH (customer:Customer {id: " + oneData[0] +"})-[owner:IS_OWNER_OF]->(car:Car {id: " + oneData[1] +"}) RETURN owner.color, owner.date";
            cypherQueries.add(cypherQuery);
        }

        AtomicReference<ResultSet> result = new AtomicReference<ResultSet>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timestamp = "[" + dateFormat.format(new Date()) + "]";

        for (int i = 0; i < cypherQueries.size(); i++) {
            var cypherQuery = cypherQueries.get(i);
            executionTimes.add(Measure(() -> result.set(neo4jConnector.ask(cypherQuery))));
            try (ResultSet rs = result.get()) {
                while (rs.next()) {
                    assertEquals(allData.get(i +1)[2].toLowerCase(), rs.getString("owner.color").toLowerCase());
                    assertEquals(allData.get(i+1)[3].toLowerCase(), rs.getString("owner.date").toLowerCase());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        String fileName = "readOne.csv";
        writeExecutionTimesToFile(fileName, executionTimes, timestamp);
    }
    @Test
    public void readOneHundred(){
        List<String> cypherQueries = new ArrayList<>();
        List<Long> executionTimes = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            String[] oneData = allData.get(i);
            String cypherQuery = "MATCH (customer:Customer {id: " + oneData[0] +"})-[owner:IS_OWNER_OF]->(car:Car {id: " + oneData[1] +"}) RETURN owner.color, owner.date";
            cypherQueries.add(cypherQuery);
        }

        AtomicReference<ResultSet> result = new AtomicReference<ResultSet>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timestamp = "[" + dateFormat.format(new Date()) + "]";

        for (int i = 0; i < cypherQueries.size(); i++) {
            var cypherQuery = cypherQueries.get(i);
            executionTimes.add(Measure(() -> result.set(neo4jConnector.ask(cypherQuery))));
            try (ResultSet rs = result.get()) {
                while (rs.next()) {
                    assertEquals(allData.get(i +1)[2].toLowerCase(), rs.getString("owner.color").toLowerCase());
                    assertEquals(allData.get(i +1)[3].toLowerCase(), rs.getString("owner.date").toLowerCase());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        String fileName = "readOneHundred.csv";
        writeExecutionTimesToFile(fileName, executionTimes, timestamp);
    }
    @Test
    public void readTenThousand(){
        List<String> cypherQueries = new ArrayList<>();
        List<Long> executionTimes = new ArrayList<>();

        for (int i = 1; i <= 10000; i++) {
            String[] oneData = allData.get(i);
            String cypherQuery = "MATCH (customer:Customer {id: " + oneData[0] +"})-[owner:IS_OWNER_OF]->(car:Car {id: " + oneData[1] +"}) RETURN owner.color, owner.date";
            cypherQueries.add(cypherQuery);
        }

        AtomicReference<ResultSet> result = new AtomicReference<ResultSet>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timestamp = "[" + dateFormat.format(new Date()) + "]";

        for (int i = 0; i < cypherQueries.size(); i++) {
            var cypherQuery = cypherQueries.get(i);
            executionTimes.add(Measure(() -> result.set(neo4jConnector.ask(cypherQuery))));
            try (ResultSet rs = result.get()) {
                while (rs.next()) {
                    assertEquals(allData.get(i +1)[2].toLowerCase(), rs.getString("owner.color").toLowerCase());
                    assertEquals(allData.get(i +1)[3].toLowerCase(), rs.getString("owner.date").toLowerCase());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        String fileName = "readOneHundred.csv";
        writeExecutionTimesToFile(fileName, executionTimes, timestamp);
    }
    @Test
    public void readHundredThousand(){
        List<String> cypherQueries = new ArrayList<>();
        List<Long> executionTimes = new ArrayList<>();

        for (int i = 1; i <= 100000; i++) {
            String[] oneData = allData.get(i);
            String cypherQuery = "MATCH (customer:Customer {id: " + oneData[0] +"})-[owner:IS_OWNER_OF]->(car:Car {id: " + oneData[1] +"}) RETURN owner.color, owner.date";
            cypherQueries.add(cypherQuery);
        }

        AtomicReference<ResultSet> result = new AtomicReference<ResultSet>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timestamp = "[" + dateFormat.format(new Date()) + "]";

        for (int i = 0; i < cypherQueries.size(); i++) {
            var cypherQuery = cypherQueries.get(i);
            executionTimes.add(Measure(() -> result.set(neo4jConnector.ask(cypherQuery))));
            try (ResultSet rs = result.get()) {
                while (rs.next()) {
                    assertEquals(allData.get(i +1)[2].toLowerCase(), rs.getString("owner.color").toLowerCase());
                    assertEquals(allData.get(i +1)[3].toLowerCase(), rs.getString("owner.date").toLowerCase());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        String fileName = "readOneHundred.csv";
        writeExecutionTimesToFile(fileName, executionTimes, timestamp);
    }

    @Test
    public void readOneMillion(){
        List<String> cypherQueries = new ArrayList<>();
        List<Long> executionTimes = new ArrayList<>();

        for (int i = 1; i <= 1000000; i++) {
            String[] oneData = allData.get(i);
            String cypherQuery = "MATCH (customer:Customer {id: " + oneData[0] +"})-[owner:IS_OWNER_OF]->(car:Car {id: " + oneData[1] +"}) RETURN owner.color, owner.date";
            cypherQueries.add(cypherQuery);
        }

        AtomicReference<ResultSet> result = new AtomicReference<ResultSet>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timestamp = "[" + dateFormat.format(new Date()) + "]";

        for (int i = 0; i < cypherQueries.size(); i++) {
            var cypherQuery = cypherQueries.get(i);
            executionTimes.add(Measure(() -> result.set(neo4jConnector.ask(cypherQuery))));
            try (ResultSet rs = result.get()) {
                while (rs.next()) {
                    assertEquals(allData.get(i +1)[2].toLowerCase(), rs.getString("owner.color").toLowerCase());
                    assertEquals(allData.get(i +1)[3].toLowerCase(), rs.getString("owner.date").toLowerCase());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        String fileName = "readOneHundred.csv";
        writeExecutionTimesToFile(fileName, executionTimes, timestamp);
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
