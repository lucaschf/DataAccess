package tsi.too.lucasfonseca.dataaccess.model;

import tsi.too.lucasfonseca.dataaccess.api.MyComparable;

public class Test implements MyComparable<Test> {
	private static final long serialVersionUID = 2;

	private String name;
	private Long id;

	public Test(String name, long id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int compareTo(Test o) {
		return id.compareTo(o.id);
	}

	@Override
	public String toString() {
		return "Test [name=" + name + ", id=" + id + "]";
	}
}
