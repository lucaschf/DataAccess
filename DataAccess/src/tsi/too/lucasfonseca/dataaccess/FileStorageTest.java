package tsi.too.lucasfonseca.dataaccess;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import tsi.too.lucasfonseca.dataaccess.api.IStorage;
import tsi.too.lucasfonseca.dataaccess.model.Product;

public class FileStorageTest {
    IStorage<Product> storage;

    public FileStorageTest(IStorage<Product> storage) {
        super();
        this.storage = storage;
    }

    public void launchTest() {
        testInsertion();
        testFetch();

        System.out.println("Listing test\n");
        testList();

        updateTest();

        deleteTest();

        try {
            storage.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testInsertion() {
        System.out.println("Insertion test.\n");
        Random random = new Random();

        for (int i = 1; i <= 10; i++) {
            var p = new Product((long) i, "Product" + i, Math.abs(random.nextDouble()), Math.abs(random.nextInt()));
            System.out.println("Inserting product: " + p.getName());

            try {
                storage.insert(p);
                System.out.println("Product successfully inserted.\n");
            } catch (IOException | IllegalArgumentException e) {
                System.out.printf("%s\n%n", e.getMessage());
            }
        }
    }

    private void testFetch() {
        System.out.println("\nSearch test.\n");

        var ids = new Long[]{generateId(), generateId() + 10};

        try {
            for (long id : ids) {
                System.out.println("Searching product with id: " + id);

                var found = storage.get(new Product(id));
                if (found.isPresent())
                    System.out.printf("Found: %s\n%n", found.get());
                else
                    System.out.printf("No product with id %d found\n%n", id);
            }
        } catch (IOException e) {
            System.out.printf("%s\n%n", e.getMessage());
        }
    }

    private long generateId() {
        long count;

        try {
            count = storage.count();
        } catch (IOException e) {
            return 0;
        }

        if (count == 1)
            return 1;

        if (count == 0)
            return 0;

        return ThreadLocalRandom.current().nextInt(1, (int) count);
    }

    private void testList() {
        try {
            var list = storage.all();
            list.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n");
    }

    private void updateTest() {
        var id = generateId();

        System.out.println("Updating test\n");

        try {
            testList();

            var newData = new Product(id, "Product updated", 2.50, 10);

            System.out.printf("Updating product of id: %d%n", id);

            storage.update(newData);
            System.out.println("Product successfully updated\n");
        } catch (IOException | IllegalArgumentException e) {
            System.out.printf("%s\n%n", e.getMessage());
        }

        System.out.println();
        testList();
    }

    private void deleteTest() {
        System.out.println("Deleting test\n");

        try {
            testList();

            var id = generateId();
            System.out.printf("Deleting product with id: %d%n", id);

            storage.delete(new Product(id));
            System.out.println("Product successfully deleted\n");

        } catch (IOException e) {
            System.out.printf("%s\n%n", e.getMessage());
        }

        testList();
    }
}