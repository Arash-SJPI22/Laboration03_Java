package service;

import entities.Categories;
import entities.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static entities.Categories.*;
import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    @Test
    void testAddNewProductShouldThrowExceptionWhenProductNameIsEmpty() {
        Warehouse warehouse = new Warehouse();

        assertThrows(IllegalArgumentException.class, () -> warehouse.addNewProduct("", Categories.HDD, 5));
    }

    @Test
    void testAddNewProductShouldEqualTrue() {
        Warehouse warehouse = new Warehouse();
        warehouse.addNewProduct("Rx580", GPU, 5);
        Product testProduct = warehouse.getAllProducts().get(0);

        assertTrue(testProduct.getProductName().equals("Rx580") &&
                testProduct.getCategory().equals(GPU) &&
                testProduct.getRating() == 5.0);
    }
    @Test
    void testChangeProductShouldThrowExceptionWhenIDIsWrong() {
        Warehouse warehouse = new Warehouse();

        assertThrows(IllegalArgumentException.class, () -> warehouse.changeProduct(919, "AMD", GPU, 1));
    }

    @Test
    void testChangeProductShouldThrowExceptionWhenProductNameIsNull() {
        Warehouse warehouse = new Warehouse();
        warehouse.addNewProduct("i9", Categories.CPU, 3);

        assertThrows(IllegalArgumentException.class, () -> warehouse.changeProduct(0, null, GPU, 1));
    }

    @Test
    void testGetAllProductsShouldNotBeEmpty() {
        Warehouse warehouse = new Warehouse();

        warehouse.addNewProduct("AMD 1", Categories.CPU, 2);
        warehouse.addNewProduct("AMD 2", GPU, 3);
        warehouse.addNewProduct("RADEON", GPU, 5);
        warehouse.addNewProduct("SeaGate", Categories.HDD, 2);
        warehouse.addNewProduct("KingSton", Categories.RAM, 5);

        assertEquals(5, warehouse.getAllProducts().size());
    }

    @Test
    void testFindProductOnIDWhenIDIsWrong() {
        Warehouse warehouse = new Warehouse();

        assertThrows(IllegalArgumentException.class, () -> warehouse.getProductOnID(93284982));
    }

    @Test
    void testFindProductOnID(){
        Warehouse warehouse = new Warehouse();
        warehouse.addNewProduct("AMD 1", Categories.CPU, 2);

        assertFalse(warehouse.getAllProducts().isEmpty());
    }

    @Test
    void testFindProductsByCategoryAndSort() {
        Warehouse warehouse = new Warehouse();
        warehouse.addNewProduct("Z", CPU, 2);
        warehouse.addNewProduct("C", CPU, 3);
        warehouse.addNewProduct("A", GPU, 5);
        warehouse.addNewProduct("B", CPU, 2);
        warehouse.addNewProduct("D", CPU, 5);

        List<Product> result = warehouse.getProductsInCategory(CPU);

        assertTrue(result.get(0).getProductName().equals("B") &&
                result.get(1).getProductName().equals("C") &&
                result.get(2).getProductName().equals("D") &&
                result.get(3).getProductName().equals("Z")&&
                result.size() == 4);
    }

    @Test
    void testToGetNewProductsShouldBeEmpty() {
        Warehouse warehouse = new Warehouse();
        warehouse.addNewProduct("Z", CPU, 2);
        warehouse.addNewProduct("C", CPU, 3);

        assertTrue( warehouse.getNewProductsAfter(LocalDateTime.now()).isEmpty());
    }

    @Test
    void testToGetNewProductsSinceLastYearShouldResultIn2() {
        Warehouse warehouse = new Warehouse();
        warehouse.addNewProduct("Z", CPU, 2);
        warehouse.addNewProduct("C", CPU, 3);

        assertEquals(2, warehouse.getNewProductsAfter(LocalDateTime.of(2022,8,10,10,1)).size());
    }

    @Test
    void testToGetAllModifiedProductsSinceCreating() {
        Warehouse warehouse = new Warehouse();
        warehouse.addNewProduct("Z", CPU, 2);
        warehouse.addNewProduct("C", CPU, 3);
        warehouse.addNewProduct("A", GPU, 5);
        warehouse.addNewProduct("B", CPU, 2);
        warehouse.addNewProduct("D", CPU, 5);

        warehouse.changeProduct((warehouse.getAllProducts().stream().filter(product -> product.getProductName().equals("Z")).findAny().orElse(null).getID()), "Z", CPU, 2);
        warehouse.changeProduct((warehouse.getAllProducts().stream().filter(product -> product.getProductName().equals("D")).findAny().orElse(null).getID()), "D", CPU, 5);

        assertEquals(2, warehouse.getModifiedProducts().size());
    }

    @Test
    void testGetAllCategoriesWithProducts() {
        Warehouse warehouse = new Warehouse();
        warehouse.addNewProduct("Z", HDD, 2);
        warehouse.addNewProduct("C", CPU, 3);
        warehouse.addNewProduct("A", GPU, 5);

        assertEquals(List.of(HDD, CPU, GPU), warehouse.getCategoriesWithProducts());
    }

    @Test
    void testNumberOfProductsInCategory() {
        Warehouse warehouse = new Warehouse();
        warehouse.addNewProduct("Z", CPU, 2);
        warehouse.addNewProduct("C", GPU, 3);
        warehouse.addNewProduct("D", CPU, 5);

        assertEquals(2, warehouse.getProductNumberInCategory(CPU));
    }

    @Test
    void testMapWithFirstLetterAndNumerOfProducts() {
        Warehouse warehouse = new Warehouse();
        warehouse.addNewProduct("Zorro", CPU, 2);
        warehouse.addNewProduct("Corro", GPU, 3);
        warehouse.addNewProduct("Zappa", CPU, 5);

        assertEquals(2, warehouse.getMapWithFirstLetterAndNumerOfProducts().get("Z"));
    }

    @Test
    void testProductWithMaxRatingCreatedThisMonthSortedByNewestFirst() {
        Warehouse warehouse = new Warehouse();
        warehouse.addNewProduct("Z", CPU, 10);
        warehouse.addNewProduct("C", GPU, 9.9);
        warehouse.addNewProduct("D", CPU, 10);

        assertEquals("D", warehouse.getProductWithMaxRatingCreatedThisMonthSortedByNewestFirst().get(0).getProductName());
        assertEquals(2, warehouse.getProductWithMaxRatingCreatedThisMonthSortedByNewestFirst().size());

    }
}