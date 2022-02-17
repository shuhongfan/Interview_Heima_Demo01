package day02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoggerUtils {
    public static final Logger logger1 = LoggerFactory.getLogger("A");
    public static final Logger logger2 = LoggerFactory.getLogger("B");
    public static final Logger logger3 = LoggerFactory.getLogger("C");
    public static final Logger logger4 = LoggerFactory.getLogger("D");
    public static final Logger logger5 = LoggerFactory.getLogger("E");
    public static final Logger logger6 = LoggerFactory.getLogger("F");
    public static final Logger main = LoggerFactory.getLogger("G");

    private static final Map<String, Logger> map = new HashMap<>();

    static {
        map.put("1", logger1);
        map.put("2", logger2);
        map.put("3", logger3);
        map.put("4", logger4);
        map.put("5", logger5);
        map.put("6", logger6);
        map.put("0", logger6);
        map.put("main", main);
    }

    public static Logger get() {
        return get(null);
    }

    public static Logger get(String prefix) {
        String name = Thread.currentThread().getName();
        if(!name.equals("main")) {
            int length = name.length();
            name = name.substring(length - 1);
        }
        return map.getOrDefault(name, logger1);
    }

    public static void main(String[] args) {
        logger1.debug("hello");
        logger2.debug("hello");
        logger3.debug("hello");
        logger4.debug("hello");
        logger5.warn("hello");
        logger6.info("hello");
        main.error("hello");
    }
}
