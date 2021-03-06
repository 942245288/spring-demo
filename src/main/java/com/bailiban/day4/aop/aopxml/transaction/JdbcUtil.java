package com.bailiban.day4.aop.aopxml.transaction;

import java.sql.*;

public class JdbcUtil {

    static String driver = "com.mysql.cj.jdbc.Driver";
    static String userName = "root";
    static String password = "123456";
    static String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf-8";
    static Connection connection;
    static Statement statement;

    static {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, userName, password);
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 开始事务，用于 before advice
    public void transaction_begin() throws SQLException {
        System.out.println("begin");
        connection.setAutoCommit(false);
    }

    // 提交事务，用于 after-returning advice
    public void transaction_commit() throws SQLException {
        System.out.println("commit");
        connection.commit();
    }

    // 回滚事务，用于 after-throwing advice
    public void transaction_rollback() throws SQLException {
        System.out.println("rollback");
        connection.rollback();
    }

    // 回收资源，此处并没有关闭连接，对应于 after advice
    public void transaction_release() {
        System.out.println("release");
    }

    public static void main(String[] args) throws SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String password = "123456";
        String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf-8";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement statement = connection.prepareStatement("select * from account");
             ResultSet resultSet = statement.executeQuery();
             ) {
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + "\t" + resultSet.getString("name") +
                        "\t" + resultSet.getString("password") + "\t" + resultSet.getDouble("money") +
                        "\t" + resultSet.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
