package tsi.too.lucasfonseca.dataaccess;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import tsi.too.lucasfonseca.dataaccess.api.IStorage;
import tsi.too.lucasfonseca.dataaccess.model.Product;

public class FileStorageTest {
	IStorage<Product> storage;
	Random random = new Random();

	public FileStorageTest(IStorage<Product> storage) {
		super();
		this.storage = storage;
	}

	public void launch() {
		testInsertion();
		testFetch();

		System.out.println("Teste de listagem.\n");
		testeList();

		updateTest();

		deleteTest();

	}

	private void testInsertion() {

		System.out.println("Teste de inserção.\n");

		for (int i = 1; i <= 10; i++) {
			var p = new Product(Long.valueOf(i), "Product" + i, Math.abs(random.nextDouble()),
					Math.abs(random.nextInt()));
			System.out.println("inserindo item: " + p.getName());

			try {
				storage.create(p);
				System.out.println("Item inserido com sucesso.\n");
			} catch (IOException e) {
				System.out.println("falha ao inserir.\n");
			}
		}
	}

	private void testFetch() {
		System.out.println("\nTeste de busca.\n");

		var ids = new Long[] { generateId(), generateId() + 10 };

		try {
			for (long id : ids) {
				System.out.println("Searching product with id: " + id);

				var found = storage.get(new Product(id));
				if (found.isPresent())
					System.out.println(String.format("Found: %s\n", found.get()));
				else
					System.out.println(String.format("Not found product with id %d\n", id));
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private long generateId() {
		long count;

		try {
			count = storage.count();
		} catch (IOException e) {
			return 0;
		}

		return (long) ThreadLocalRandom.current().nextInt(1, (int) count);
	}

	private void testeList() {
		try {
			var list = storage.all();
			list.forEach(p -> System.out.println(p));
		} catch (IOException e) {
			System.out.println(e);
		}

		System.out.println("\n");
	}

	private void updateTest() {
		var id = generateId();

		System.out.println("\nTeste de atualizacao\n");

		try {
			testeList();
			var newData = new Product(id, "UpdatedProduct", 2.50, 10);

			System.out.println("Atualizando produto de id: " + id);

			storage.update(newData);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		testeList();
	}

	private void deleteTest() {
		var id = generateId();
		System.out.println("Teste de remoção\n");

		try {
			testeList();

			System.out.println("Removendo produto de id: " + id);

			storage.delete(new Product(id));
			System.out.println("Produto removido com sucesso\n");

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		testeList();
	}
}
