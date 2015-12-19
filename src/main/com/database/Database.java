package main.com.database;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * <p>
 * Singleton Database.
 *
 * @author Faraz Ali
 */
public class Database implements Query {
    private static Database database = new Database();
    private static ListingsList listingsList = new ListingsList();

    private static Map<Long, ListingNode> table = new HashMap<>();

    public static Database getInstance() {
        return database;
    }

    private Database() {
    }

    public int getSize() {
        return table.size();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * insert item into the database
     * @param id
     * @param l
     */
    @Override
    public void insert(long id, Listing l) {

        if (listingsList.getStartNode() == null) {
            listingsList.insertAtStart(l);
            table.put(id, listingsList.getStartNode());
        } else {
            table.put(id, listingsList.insertAtEnd(l));
        }
    }

    /**
     * find item using id
     * @param listingId
     * @return Listing matching the id, null if not found
     * TODO: use Optional<>
     */
    public Listing select(long listingId) {

        return (table.containsKey(listingId)) ? table.get(listingId).getListing() : null;
    }


    /**
     * delete item using id
     * @param listingId
     * @return
     */
    public boolean delete(long listingId) {

        if (!table.containsKey(listingId)) {
            throw new IllegalArgumentException("Listing ID: " + listingId + " doesn't exist!");
        }
        return delete(listingId, table.get(listingId));
    }


    /**
     * Print k items from the circular list
     * @param k
     */
    public void printKElementsInList(int k) {
        ListingNode node = listingsList.getStartNode();
        for (int i = 0; i < k; i++) {
            System.out.println(node.toString());
            node = node.getNext();
        }
    }

    /**
     * Uses Java 8 streams to print table contents
     */
    public void printDatabaseContents() {
        table.forEach((k, v) -> System.out.println("key: " + k + " value: " + v.toString()));
        System.out.println("size: " + table.size());
        System.out.println("************************");
    }

    /**
     * Create and populate database from id 1 to k
     * @param k
     */
    public void createDatabaseWithKItems(int k) {
        listingsList.insertAtStart(new Listing(1L, 101L, "title 1"));
        table.put(1L, listingsList.getStartNode());
        for (int i = 2; i <= k; i++) {
            table.put((long) i, listingsList.insertAtEnd(new Listing((long) i, 100L + i, "title " + i)));
        }
    }


    /**
     * A special database sweep (clean up) function.
     * Sweep using mathematical triangular series n(n+1)/2;
     */
    public void sweep() {
        ListingNode node = listingsList.getStartNode();

        int count = 1;
        int lastSkipped = 0;
        while (table.size() > 1) {

            int skip = (count * (count + 1)) / 2;
            for (int i = 1; i < skip - lastSkipped; i++) {
                node = node.getNext();
            }

            count++;
            ListingNode temp = node.getNext();
            delete(node.getListing().getListingId(), node);
            node = temp;
            lastSkipped = skip;
        }
    }


    /**
     * Delete the entry with the given unique listing id from the database
     *
     * @param listingId
     * @return
     */
    public boolean delete(long listingId, ListingNode node) {

        if (table.isEmpty()) {
            return false;
        }

        if (listingId == listingsList.getStartNode().getListing().getListingId() &&
                node.getListing().getListingId() == listingsList.getEndNode().getListing().getListingId()) {
            return isStartingAndEndingNode(listingId);
        } else if (listingId == listingsList.getStartNode().getListing().getListingId()) {
            return removeStartNode(listingId, node);
        } else if (listingId == listingsList.getEndNode().getListing().getListingId()) {
            return isEndingNode(listingId);
        }

        return removeNode(listingId, node);
    }

    private boolean removeNode(long listingId, ListingNode node) {
        table.remove(listingId);

        ListingNode temp = node.getNext();
        node.setListing(temp.getListing());
        node.setNext(temp.getNext());

        listingsList.setSize(listingsList.getSize() - 1);

        return true;
    }

    private boolean isEndingNode(long listingId) {
        ListingNode head = listingsList.getStartNode();
        ListingNode tail = listingsList.getStartNode();

        while (head.getListing().getListingId() != (listingsList.getEndNode().getListing().getListingId())) {
            tail = head;
            head = head.getNext();
        }

        listingsList.setEndNode(tail);
        listingsList.getEndNode().setNext(listingsList.getStartNode());
        listingsList.setSize(listingsList.getSize() - 1);
        table.remove(listingId);
        return true;
    }

    private boolean removeStartNode(long listingId, ListingNode node) {
        table.remove(listingId);

        ListingNode temp = node.getNext();

        node.setListing(temp.getListing());
        node.setNext(temp.getNext());

        listingsList.setStartNode(node);
        listingsList.getEndNode().setNext(node);
        listingsList.setSize(listingsList.getSize() - 1);
        return true;

    }

    private boolean isStartingAndEndingNode(long listingId) {
        table.remove(listingId);
        listingsList.setStartNode(null);
        listingsList.setEndNode(null);
        listingsList.setSize(0);
        return true;
    }

    private void resetDatabase() {
        table.clear();
        listingsList.setStartNode(null);
        listingsList.setEndNode(null);
    }
}
