package day02;

import day01.sort.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestThreadLocal {
    public static void main(String[] args) {
        test2();
    }

    // 一个线程内调用, 得到的是同一个 Connection 对象
    private static void test2() {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                LoggerUtils.get("t").debug("{}", Utils.getConnection());
                LoggerUtils.get("t").debug("{}", Utils.getConnection());
                LoggerUtils.get("t").debug("{}", Utils.getConnection());
            }, "t" + (i + 1)).start();
        }
    }

    // 多个线程调用, 得到的是自己的 Connection 对象
    private static void test1() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                LoggerUtils.get("t").debug("{}", Utils.getConnection());
            }, "t" + (i + 1)).start();
        }
    }

    static class Utils {
        private static final ThreadLocal<Connection> tl = new ThreadLocal<>();

        public static Connection getConnection() {
            Connection conn = tl.get(); // 到当前线程获取资源
            if (conn == null) {
                conn = innerGetConnection(); // 创建新的连接对象
                tl.set(conn); // 将资源存入当前线程
            }
            return conn;
        }

        private static Connection innerGetConnection() {
            try {
                return DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false", "root", "root");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
