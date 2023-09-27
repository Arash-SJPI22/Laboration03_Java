package service;

import entities.Categories;
import entities.Product;
import org.junit.jupiter.api.Test;

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
        warehouse.addNewProduct("Rx580", Categories.GPU, 5);
        Product testProduct = warehouse.getAllProducts().get(0);

        assertTrue(testProduct.getProductName().equals("Rx580") &&
                testProduct.getCategory().equals(Categories.GPU) &&
                testProduct.getRating() == 5.0);
    }
    @Test
    void testChangeProductShouldThrowExceptionWhenIDIsWrong() {
        Warehouse warehouse = new Warehouse();

        assertThrows(IllegalArgumentException.class, () -> warehouse.changeProduct(919, "AMD", Categories.GPU, 1));
    }

    @Test
    void testChangeProductShouldThrowExceptionWhenProductNameIsNull() {
        Warehouse warehouse = new Warehouse();
        warehouse.addNewProduct("i9", Categories.CPU, 3);

        assertThrows(IllegalArgumentException.class, () -> warehouse.changeProduct(0, null, Categories.GPU, 1));
    }

    @Test
    void testGetAllProductsShouldNotBeEmpty() {
        Warehouse warehouse = new Warehouse();

        warehouse.addNewProduct("AMD 1", Categories.CPU, 2);
        warehouse.addNewProduct("AMD 2", Categories.GPU, 3);
        warehouse.addNewProduct("RADEON", Categories.GPU, 5);
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



}