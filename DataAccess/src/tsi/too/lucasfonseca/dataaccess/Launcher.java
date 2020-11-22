package tsi.too.lucasfonseca.dataaccess;

import java.io.IOException;

import tsi.too.lucasfonseca.dataaccess.model.Product;
import tsi.too.lucasfonseca.dataaccess.utils.Connection;

public class Launcher {

	public static void main(String[] args) {
		launch();
	}

	private static void launch() {
		try {
			new FileStorageTest(Connection.openConnection("product.bin", Product.class)).launch();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
