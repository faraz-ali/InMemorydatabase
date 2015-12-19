package main.com.database;

/**
 * @author Faraz Ali
 */
public class Listing {
    private Long listingId;
    private Long categoryId;
    private String title;

    public Listing(Long listingId, Long categoryId, String title) {
        this.listingId = listingId;
        this.categoryId = categoryId;
        this.title = title;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "listingId=" + listingId +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                '}';
    }
}