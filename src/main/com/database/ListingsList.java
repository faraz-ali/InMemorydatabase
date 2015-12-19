package main.com.database;

/**
 * @author Faraz Ali
 */
public class ListingsList implements GenericListingsList {
    private ListingNode startNode;
    private ListingNode endNode;
    private int size;

    public ListingsList() {
        startNode = null;
        endNode = null;
        size = 0;
    }

    public boolean isEmpty() {
        return startNode == null;
    }

    public ListingNode getStartNode() {
        return startNode;
    }

    public void setStartNode(ListingNode startNode) {
        this.startNode = startNode;
    }

    public ListingNode getEndNode() {
        return endNode;
    }

    public void setEndNode(ListingNode endNode) {
        this.endNode = endNode;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void insertAtStart(Listing listing) {
        ListingNode node = new ListingNode(listing, null);

        node.setNext(startNode);

        if (startNode == null) {
            startNode = node;
            node.setNext(startNode);
            endNode = startNode;
        } else {
            endNode.setNext(node);
            startNode = node;
        }
        size++;
    }

    public ListingNode insertAtEnd(Listing listing) {
        ListingNode node = new ListingNode(listing, null);
        node.setNext(startNode);

        if (startNode == null) {
            startNode = node;
            node.setNext(startNode);
            endNode = startNode;
        } else {
            endNode.setNext(node);
            endNode = node;
            endNode.setNext(startNode);
        }
        size++;

        return endNode;
    }
}
