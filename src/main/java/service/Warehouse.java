package service;

import entities.Categories;
import entities.Product;

import java.time.LocalDateTime;
import java.util.*;
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

    public List<Product> getNewProductsAfter(LocalDateTime date) {
        return productList.stream()
                .filter(product -> product.getCreatedDate().isAfter(date))
                .sorted(Comparator.comparing(Product::getCreatedDate))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return productList.stream()
                .filter(product -> product.getModifiedDate().isAfter(product.getCreatedDate()))
                .toList();
    }

    public List<Categories> getCategoriesWithProducts() {
        return productList.stream()
                .map(Product::getCategory)
                .distinct()
                .toList();
    }

    public long getProductNumberInCategory(Categories category) {
        return productList.stream()
                .filter(product -> product.getCategory().equals(category))
                .count();
    }

    public Map<String, Long> getMapWithFirstLetterAndNumerOfProducts () {
        return productList.stream()
                .collect(Collectors.groupingBy(product -> product.getProductName().substring(0, 1), Collectors.counting()));

    }

    public List<Product> getProductWithMaxRatingCreatedThisMonthSortedByNewestFirst () {
        return  productList.stream()
                .filter(product -> product.getRating() == 10)
                .filter(product -> product.getCreatedDate().isAfter(LocalDateTime.now().minusMonths(1)))
                .sorted(Comparator.comparing(Product::getCreatedDate).reversed())
                .toList();
    }

    // Private methods
    private void checkProductName(String productName) {
        if (productName == null || productName.isEmpty())
            throw new IllegalArgumentException("Product name can not be empty or null!");
    }
    private void checkProductID (int ID) {
        if (productList.stream().noneMatch(product -> product.getID() == ID))
            throw new IllegalArgumentException("Bad product ID!");
    }
}
