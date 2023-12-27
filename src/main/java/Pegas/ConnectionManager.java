package Pegas;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final int DEFAULT_POLL_SIZE = 10;
    private static final String POLL_SIZE_KEY = "db.pool.size";
    private static BlockingQueue<Connection> pool;

    static{
        initConnectionPool();
    }

    private static void initConnectionPool() {
        String poolSize = PropertiesUtil.get(POLL_SIZE_KEY);
        int size = poolSize == null ? DEFAULT_POLL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        for (int i = 0; i<size;i++){
            Connection connection = open();
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
            new  Class[]{Connection.class},((proxy, method, args) -> method.getName().equals("close") ?
                            pool.add((Connection)proxy):method.invoke(connection, args)));
            pool.add(proxyConnection);
        }
    }
    public static Connection get() throws InterruptedException {
        return pool.take();
    }
    private static Connection open(){
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        }catch (SQLException ex){
            throw new RuntimeException("DB was dropped");
        }
    }
    private ConnectionManager() {
    }

}
