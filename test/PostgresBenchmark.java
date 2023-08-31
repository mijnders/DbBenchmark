
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresBenchmark {

    interface Meassureable {
        void TestMethod();
    }

    public long Meassure(Meassureable meassureable) {
        int i = 1000000000;
        long startTime = System.nanoTime();
        meassureable.TestMethod();
        long stopTime = System.nanoTime();
        return ((stopTime - startTime));
    }

    private static final String testFiles = System.getProperty("user.dir") + "\\CSV\\";
    public static List<String> queries = new ArrayList<>();
    public static final String delimiter = ",";

    private class CreateTests {

        public static List<String> read(String csvFile) {
            List<String> rows = new ArrayList<>();
            String query = "INSERT INTO owners(CustomerID, CarID, CarColor, Date) VALUES";

            try {
                File file = new File(csvFile);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;

                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] s = line.split(delimiter);
                    rows.add(query + "('" + s[0] + "', '" + s[1] + "', '" + s[2] + "', '" + s[3] + "');");
                }

                br.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            return rows;
        }


        private static PostgresConnector postgresConnector;

        @BeforeAll
        public static void Ini() {
            postgresConnector = new PostgresConnector();
            postgresConnector.connect();
            postgresConnector.initializeDatabase();
            postgresConnector.importData();
            for (int i = 1; i <= 10; i++) {
                queries.addAll(read(testFiles + "Owners\\Owners" + i + ".csv"));

            }
        }

        @AfterEach
        public void deleteOwners() {
            postgresConnector.deleteData();
        }

        public void createX(int amount) {
            for (int i = 0; i < amount; i++) {
                postgresConnector.send(queries.get(i));
            }
        }

        @Test
        public void createOne() {
            long l = Meassure(() -> {
                createX(1);
            });
            System.out.println(l);
        }

        @Test
        public void createOneHundred() {
            long l = Meassure(() -> {
                createX(100);
            });
            System.out.println(l);
        }

        @Test
        public void createTenThousand() {
            long l = Meassure(() -> {
                createX(10000);
            });
            System.out.println(l);
        }

        @Test
        public void createHundredThousand() {
            long l = Meassure(() -> {
                createX(100000);
            });
            System.out.println(l);
        }

        @Test
        public void createOneMillion() {
            long l = Meassure(() -> {
                createX(1000000);
            });
            System.out.println(l);
        }
    }
}
