package entities;

import java.time.LocalDateTime;

public class Product {
    private static int products = 0;
    private final int ID;
    private String productName;
    private Categories category;
    private double rating;
    private final LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Product(String productName, Categories category, double rating) {
        this.ID = products;
        this.productName = productName;
        this.category = category;
        this.rating = rating;
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.modifiedDate = now;
        products++;
    }

    public int getID() {
        return ID;
    }

    public String getProductName() {
        return productName;
    }

    public Categories getCategory() {
        return category;
    }

    public double getRating() {
        return rating;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        setModifiedDate();
    }

    public void setCategory(Categories category) {
        this.category = category;
        setModifiedDate();
    }

    public void setRating(double rating) {
        this.rating = rating;
        setModifiedDate();
    }

    private void setModifiedDate() {
        this.modifiedDate = LocalDateTime.now();
    }
}
