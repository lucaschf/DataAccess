package tsi.too.lucasfonseca.dataaccess.model;

import tsi.too.lucasfonseca.dataaccess.api.MyComparable;

public class Product implements MyComparable<Product> {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private double unitaryPrice;
	private int quantity;

	public Product(Long id, String name, double unitaryPrice, int quantity) {
		super();
		this.id = id;
		this.name = name;
		this.unitaryPrice = unitaryPrice;
		this.quantity = quantity;
	}

	public Product(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getUnitaryPrice() {
		return unitaryPrice;
	}

	public void setUnitaryPrice(double unitaryPrice) {
		this.unitaryPrice = unitaryPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return String.format("id=%s, name=%s, unitaryPrice=%1.2f, quantity=%d", id, name, unitaryPrice, quantity);
	}

	@Override
	public int compareTo(Product o) {
		return id.compareTo(o.getId());
	}
}
