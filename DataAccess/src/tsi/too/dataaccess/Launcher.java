package tsi.too.dataaccess;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import tsi.too.dataaccess.model.DataAccessObjectException;
import tsi.too.dataaccess.model.Test;
import tsi.too.dataaccess.model.TestRepository;

public class Launcher {

	public static void main(String[] args) throws IOException {

		try (TestRepository repo = new TestRepository(Test.class, "test.bin")) {
			repo.connect();

			var t = new Test("Joaoszun", 1);

			repo.insert(t);
			repo.insert(new Test("Elias Kosmos", 2));
			repo.insert(new Test("dsa", 3));
			repo.insert(new Test("Joaozun", 4));

			t.setName("joaquinssssssss");
			repo.update(t);

			System.out.println(repo.list());

		} catch (DataAccessObjectException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
}
