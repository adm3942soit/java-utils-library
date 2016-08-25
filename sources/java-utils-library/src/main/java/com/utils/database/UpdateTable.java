package com.utils.database;

import com.opencsv.CSVWriter;
import com.utils.date.DateUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by oksdud on 24.08.2016.
 */
public class UpdateTable {

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://ecom-dev.lpb.lv:3306/disputes";
    private static final String DB_USER = "oksdud";
    private static final String DB_PASSWORD = "5AdeWiKuNA";
    public static String fileCSVName = "outputFraudAdvice.csv";
    public static String fileNewCSVName = "FraudAdvice.csv";

    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }

    private static void deleteFraudAdviceRecordsFromTable() throws SQLException {
        String deleteQuery = "DELETE FROM FRAUD_ADVICE;";
        try (
                Connection dbConnection = getDBConnection();
                Statement statement = dbConnection.createStatement();

        ) {
            statement.execute(deleteQuery);

        }
    }

    private static void updateFraudAdviceTable() throws SQLException {
        String addUniqueKeyQuery = "ALTER TABLE FRAUD_ADVICE ADD UNIQUE (ARN);";
        try (
                Connection dbConnection = getDBConnection();
                Statement statement = dbConnection.createStatement();

        ) {
            statement.execute(addUniqueKeyQuery);

        }

    }

    private static Timestamp getCurrentTimeStamp() {

        Date today = new Date();
        return new Timestamp(today.getTime());

    }

    private static Timestamp getTimeStamp(Date date) {

        return new Timestamp(date.getTime());

    }

    private static java.sql.Date getSQLDate(Date date) {

        return new java.sql.Date(date.getTime());

    }

    private static ResultSet getResultSetFromFraudAdvice() throws SQLException {
        ResultSet resultSet = null;
        String loadTableSQL = "SELECT * FROM FRAUD_ADVICE order by ARN desc";
        try (
                Connection dbConnection = getDBConnection();
                Statement statement = dbConnection.createStatement();

        ) {
            dbConnection.setAutoCommit(false);
            resultSet = statement.executeQuery(loadTableSQL);

        }
        return resultSet;
    }

    private static void loadDataFromFraudAdviceIntoCSV(boolean includeHeaders) throws Exception {
        if (new File(fileCSVName).exists()) new File(fileCSVName).delete();
        try (
                CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(fileCSVName), "UTF8")));

        ) {
            ResultSet myResultSet = getResultSetFromFraudAdvice();
            /*the second argument is boolean which represents whether you want to write header columns (table column names) to file or not*/
            writer.writeAll(myResultSet, includeHeaders);
        }
    }

    public static void insertToDatabase() throws Exception {
        String insertTableSQL = "INSERT INTO FRAUD_ADVICE"
                + "(ID, FRAUD_IMPORT_ID, ARN, ICO, REASON_CODE, STTL_AMOUNT, STTL_CURRENCY, STTL_DATE, CREATED)  VALUES"
                + "(?,?,?,?,?,?,?,?,?)";
        Set<String> lines = getFileLines(fileNewCSVName);
        try (Connection connection = getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL)) {
            for (String line : lines) {
                try {
                    preparedStatement.setInt(1, Integer.valueOf(getColumnFromLineWithoutQuotes(line, 0)));
                    preparedStatement.setLong(2, Long.valueOf(getColumnFromLineWithoutQuotes(line, 1)));
                    preparedStatement.setString(3, getColumnFromLineWithoutQuotes(line, 2));
                    preparedStatement.setString(4, getColumnFromLineWithoutQuotes(line, 3));
                    preparedStatement.setShort(5, Short.valueOf(getColumnFromLineWithoutQuotes(line, 4)));
                    preparedStatement.setLong(6, Long.valueOf(getColumnFromLineWithoutQuotes(line, 5)));
                    preparedStatement.setString(7, getColumnFromLineWithoutQuotes(line, 6));
                    preparedStatement.setDate(8, getSQLDate(DateUtil.convertToDate(getColumnFromLineWithoutQuotes(line, 7))));
                    preparedStatement.setTimestamp(9, getTimeStamp(DateUtil.convertToDate(getColumnFromLineWithoutQuotes(line, 8))));
                    preparedStatement.executeUpdate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    continue;
                }
            }

        }
    }


    private static String getColumnFromLineWithoutQuotes(String line, int number) {
        String[] columns = line.split(",");
        System.out.println("column = " + columns[number]);
        return columns[number].substring(1, columns[number].length() - 1);
    }

    private static String getColumnFromLine(String line, int number) {
        String[] columns = line.split(",");
        System.out.println("column = " + columns[number]);
        return columns[number];
    }


    private static Set<String> getFileLines(String fileName) throws Exception {
        Set<String> fileLines
                = new HashSet<>(Files.readAllLines(FileSystems.getDefault().getPath(fileName), StandardCharsets.UTF_8));
        return fileLines;
    }

    private static Set<String> getIdsForUniqueArnFromFile(String fileName) throws Exception {
        final Set<String> fileLines = getFileLines(fileName);
        Set<String> ids = new HashSet<String>();
        Set<String> arns = new HashSet<String>();
        int count = 0;
        for (String line : fileLines) {
            String arn = getColumnFromLine(line, 2);
            arns.add(arn);
            int newCount = arns.size();
            if (newCount == count + 1) {
                ids.add(getColumnFromLine(line, 0));
                count++;
            } else {
                continue;
            }
        }
        return ids;
    }

    public static void stripDuplicatesFromFile(String fileName) throws Exception {
        final Set<String> fileLines = getFileLines(fileName);
        final Set<String> ids = getIdsForUniqueArnFromFile(fileName);
        if (new File(fileNewCSVName).exists()) new File(fileNewCSVName).delete();
        try (
                final Stream<String> stream = Files.lines(
                        FileSystems.getDefault().getPath(fileName), StandardCharsets.UTF_8);
                final BufferedWriter writer = Files.newBufferedWriter(
                        FileSystems.getDefault().getPath(fileNewCSVName),
                        StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
        ) {
            stream.filter(line -> (
                    fileLines.contains(line))
                    &&
                    ids.contains(getColumnFromLine(line, 0)))
                    .forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
        }
    }

    public static void main(String[] argv) {
        try {
            loadDataFromFraudAdviceIntoCSV(false);
            /*exclude duplicate arn*/
            stripDuplicatesFromFile(fileCSVName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            deleteFraudAdviceRecordsFromTable();
            updateFraudAdviceTable();
            insertToDatabase();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }
}
