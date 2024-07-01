package ir.sharif.service.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.model.GameHistory;
import ir.sharif.model.User;
import ir.sharif.model.server.GameRecord;
import ir.sharif.view.controllers.Game;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private static Database instance;

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/gwent";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "13831383";

    private Connection connection;
    private Gson gson;

    private Database() {
        gson = new GsonBuilder().create();
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            createTables();
            System.err.println("Database connected successfully");
        } catch (SQLException e) {
            System.err.println("error: Database doesnt connect");
            e.printStackTrace();
        }
    }

    private void createTables(){
        String createUsersTableSql = "CREATE TABLE IF NOT EXISTS USERS (username VARCHAR(255), object TEXT)";
        String createGameHistoriesTableSql = "CREATE TABLE IF NOT EXISTS GAME_HISTORIES (game_token VARCHAR(255), object TEXT)";
        String createGameRecordsTableSql = "CREATE TABLE IF NOT EXISTS GAME_RECORDS (game_token VARCHAR(255), object TEXT)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createUsersTableSql);
            statement.execute(createGameHistoriesTableSql);
            statement.execute(createGameRecordsTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance(){
        if(instance == null) instance = new Database();
        return instance;
    }

    public void addUser(User user){
        String insertUserSql = "INSERT INTO USERS (username, object) VALUES (?, ?)";
        try (PreparedStatement insertUserStatement = connection.prepareStatement(insertUserSql)) {
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, gson.toJson(user));
            int rowsInserted = insertUserStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGameHistories(GameHistory gameHistory){
        String insertGameHistorySql = "INSERT INTO GAME_HISTORIES (game_token, object) VALUES (?, ?)";
        try (PreparedStatement insertGameHistoryStatement = connection.prepareStatement(insertGameHistorySql)) {
            insertGameHistoryStatement.setString(1, gameHistory.getGameToken());
            insertGameHistoryStatement.setString(2, gson.toJson(gameHistory));
            int rowsInserted = insertGameHistoryStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new game history was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGameRecord(GameRecord gameRecord){
        String insertGameRecordSql = "INSERT INTO GAME_RECORDS (game_token, object) VALUES (?, ?)";
        try (PreparedStatement insertGameRecordStatement = connection.prepareStatement(insertGameRecordSql)) {
            insertGameRecordStatement.setString(1, gameRecord.getGameToken());
            insertGameRecordStatement.setString(2, gson.toJson(gameRecord));
            int rowsInserted = insertGameRecordStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new game record was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGameRecord(GameRecord gameRecord){
        String updateGameRecordSql = "UPDATE GAME_RECORDS SET object = ? WHERE game_token = ?";
        try (PreparedStatement updateGameRecordStatement = connection.prepareStatement(updateGameRecordSql)) {
            updateGameRecordStatement.setString(1, gson.toJson(gameRecord));
            updateGameRecordStatement.setString(2, gameRecord.getGameToken());
            int rowsUpdated = updateGameRecordStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing game record was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeUserInfo(String username, User user){
        if(!username.equals(user.getUsername())){
            String updateUserSql = "UPDATE USERS SET username = ? WHERE username = ?";
            try (PreparedStatement updateUserStatement = connection.prepareStatement(updateUserSql)) {
                updateUserStatement.setString(1, user.getUsername());
                updateUserStatement.setString(2, username);
                updateUserStatement.executeUpdate();
                username = user.getUsername();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String updateUserSql = "UPDATE USERS SET object = ? WHERE username = ?";
        try (PreparedStatement updateUserStatement = connection.prepareStatement(updateUserSql)) {
            updateUserStatement.setString(1, gson.toJson(user));
            updateUserStatement.setString(2, username);
            int rowsUpdated = updateUserStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserWithUsername(String username){
        User user = null;
        String selectUserSql = "SELECT * FROM USERS WHERE username = ?";
        try (PreparedStatement selectUserStatement = connection.prepareStatement(selectUserSql)) {
            selectUserStatement.setString(1, username);
            try (ResultSet resultSet = selectUserStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = gson.fromJson(resultSet.getString("object"), User.class);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<GameHistory> getGameHistories(){
        String selectUsersSql = "SELECT * FROM GAME_HISTORIES";
        ArrayList<GameHistory> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectUsersSql);
            while(resultSet.next()){
                GameHistory gameHistory = gson.fromJson(resultSet.getString("object"), GameHistory.class);
                result.add(gameHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<User> getUsers(){
        String selectUsersSql = "SELECT * FROM USERS";
        ArrayList<User> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectUsersSql);
            while(resultSet.next()){
                User user = gson.fromJson(resultSet.getString("object"), User.class);
                result.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<GameRecord> getGameRecords(){
        String selectUsersSql = "SELECT * FROM GAME_RECORDES";
        ArrayList<GameRecord> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectUsersSql);
            while(resultSet.next()){
                GameRecord gameRecord = gson.fromJson(resultSet.getString("object"), GameRecord.class);
                result.add(gameRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public GameRecord getGameRecordWithId(String token){
        GameRecord gameRecord = null;
        String selectGameRecordSql = "SELECT * FROM GAME_RECORDS WHERE game_token = ?";
        try (PreparedStatement selectGameRecordStatement = connection.prepareStatement(selectGameRecordSql)) {
            selectGameRecordStatement.setString(1, token);
            try (ResultSet resultSet = selectGameRecordStatement.executeQuery()) {
                if (resultSet.next()) {
                    gameRecord = gson.fromJson(resultSet.getString("object"), GameRecord.class);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameRecord;
    }

}
