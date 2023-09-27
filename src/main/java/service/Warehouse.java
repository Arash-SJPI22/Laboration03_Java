package service;

import entities.Categories;
import entities.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Warehouse {

    private List<Product> productList = new ArrayList<>();

    public void addNewProduct(String productName, Categories category, double rating) {
        checkProductName(productName);

        productList.add(new Product(productName, category, rating));
    }

    public void changeProduct(int ID, String productName, Categories category, double rating) {
        checkProductID(ID);
        checkProductName(productName);

        productList.stream()
                .filter(product -> product.getID() == ID)
                .limit(1)
                .forEach(product -> {
            product.setProductName(productName);
            product.setCategory(category);
            product.setRating(rating);
        });
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(productList);
    }

    public Product getProductOnID(int ID) {
        checkProductID(ID);
        return productList.stream()
                .filter(product -> product.getID() == ID)
                .findAny()
                .orElse(null);
    }

    public List<Product> getProductsInCategory(Categories category) {
        return productList.stream()
                .filter(product -> product.getCategory() == category)
                .sorted(Comparator.comparing(Product::getProductName))
                .toList();
    }

    private void checkProductName(String productName) {
        if (productName == null || productName.isEmpty())
            throw new IllegalArgumentException("Product name can not be empty or null!");
    }
    private void checkProductID (int ID) {
        if (productList.stream().noneMatch(product -> product.getID() == ID))
            throw new IllegalArgumentException("Bad product ID!");
    }
}
