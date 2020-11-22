package tsi.too.dataaccess.model;

public class TestRepository extends FileRepository<Test>{

	public TestRepository(Class<Test> clazz, String fileName) {
		super(clazz, fileName);
	}
	
	@Override
	public void close() throws Exception {
		try {
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
