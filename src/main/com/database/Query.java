package main.com.database;

/**
 * Created by Faraz Ali on 11/8/15.
 *
 * @author Faraz Ali
 */
public interface Query {
    public void insert(long id, Listing listing);

    public Object select(long id);

    public boolean delete(long id);
}
