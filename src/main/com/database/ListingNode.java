package main.com.database;

/**
 *
 * @author Faraz Ali
 */
public class ListingNode {
    private Listing listing;
    private ListingNode next;


    public ListingNode(Listing listing, ListingNode next) {
        this.listing = listing;
        this.next = next;
    }

    public ListingNode() {
    }

    public ListingNode getNext() {
        return next;
    }

    public void setNext(ListingNode next) {
        this.next = next;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    @Override
    public String toString() {
        return "ListingNode{" +
                listing.toString() +
                '}';
    }

}
