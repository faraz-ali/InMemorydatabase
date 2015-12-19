package test.com.database;

import main.com.database.Database;
import main.com.database.Listing;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * <p>
 * Test the database;
 *
 * @author Faraz Ali
 */
public class DatabaseTest {

    private Database eBayDatabase;
    private Listing listing1;
    private Listing listing2;
    private long LISTING_ID_1;
    private long LISTING_ID_2;

    @Before
    public void setUp() throws Exception {
        eBayDatabase = Database.getInstance();
        listing1 = new Listing(1l, 1001l, "some random title 1");
        listing2 = new Listing(5l, 1005l, "some random title 5");
        LISTING_ID_1 = listing1.getListingId();
    }


    /**
     * using reflection to reset database after each test
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        try {
            Method reset = eBayDatabase.getClass().getDeclaredMethod("resetDatabase");
            reset.setAccessible(true);
            reset.invoke(eBayDatabase);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testGetInstance() throws Exception {
        Database database1 = Database.getInstance();
        Database database2 = Database.getInstance();

        assertTrue(database1 == database2);

    }

    @Test
    public void testInsertAndSelect() throws Exception {
        eBayDatabase.insert(LISTING_ID_1, listing1);
        assertThat(eBayDatabase.select(LISTING_ID_1), is(listing1));
    }

    @Test
    public void testDelete() throws Exception {
        eBayDatabase.insert(LISTING_ID_1, listing1);
        eBayDatabase.insert(LISTING_ID_2, listing2);
        assertTrue(eBayDatabase.delete(LISTING_ID_1));
        assertTrue(eBayDatabase.delete(LISTING_ID_2));

        assertTrue(eBayDatabase.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteThrowsExceptionOnNonExistingID() throws Exception {
        eBayDatabase.insert(LISTING_ID_1, listing1);
        eBayDatabase.insert(LISTING_ID_2, listing2);
        assertEquals(eBayDatabase.getSize(), 2);
        assertTrue(eBayDatabase.delete(3));
    }


    @Test
    public void testCreateDatabaseWithKItems() throws Exception {
        assertTrue(eBayDatabase.isEmpty());

        eBayDatabase.createDatabaseWithKItems(5);
        assertEquals(eBayDatabase.getSize(), 5);
        assertNotEquals(eBayDatabase.getSize(), 4);
        assertNotEquals(eBayDatabase.getSize(), 6);

    }

    /**
     * In a database with listings inserted 1, 2, 3, 4, 5
     * after the sweep there should be only one listing remaining
     * with listtindID = 4
     *
     * @throws Exception
     */
    @Test
    public void testSweep() throws Exception {
        assertTrue(eBayDatabase.isEmpty());

        eBayDatabase.createDatabaseWithKItems(5);
        assertEquals(eBayDatabase.getSize(), 5);

        eBayDatabase.sweep();
        assertEquals(eBayDatabase.getSize(), 1);
        eBayDatabase.select(4l);
    }

    @Test
    public void testSweepDoesNotRunWithOnlyOneEntry() {
        eBayDatabase.insert(LISTING_ID_1, listing1);
        eBayDatabase.sweep();
        assertThat(eBayDatabase.select(LISTING_ID_1), is(listing1));
    }
}

