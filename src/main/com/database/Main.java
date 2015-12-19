package main.com.database;

/**
 * Main class to run the program
 * <p>
 * the listing Id remaining will be 31 after running sweep on database size 31
 * Sweep using mathematical triangular series n(n+1)/2;
 *
 * @author Faraz Ali
 */

public class Main {
    public static void main(String[] args) {
        Database database = Database.getInstance();

        database.createDatabaseWithKItems(100);
        database.printDatabaseContents();
        database.sweep();

        //the listing Id remaining will be 31
        database.printDatabaseContents();
    }
}