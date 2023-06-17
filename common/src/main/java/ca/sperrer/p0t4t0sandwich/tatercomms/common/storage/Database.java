package ca.sperrer.p0t4t0sandwich.tatercomms.common.storage;

/**
 * Represents a database.
 * @param <T> The type of connection to the database
 */
public class Database<T> {
    private final String type;
    private T connection;
    private String database;

    /**
     * Creates a new database connection.
     * @param type The type of database
     * @param connection The connection to the database
     * @param database The name of the database
     */
    public Database(String type, T connection, String database) {
        this.type = type;
        this.connection = connection;
        this.database = database;
    }

    /**
     * Gets the type of database.
     * @return The type of database
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the connection to the database.
     * @return The connection to the database
     */
    public T getConnection() {
        return connection;
    }

    /**
     * Gets the name of the database.
     * @return The name of the database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Sets the connection to the database.
     * @param connection
     */
    public void setConnection(T connection) {
        this.connection = connection;
    }

    /**
     * Sets the name of the database.
     * @param database The name of the database
     */
    public void setDatabase(String database) {
        this.database = database;
    }
}
