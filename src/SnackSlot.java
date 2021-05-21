
public class SnackSlot {
	private String item;
	private double price;
	private int count;
	private int number;

	public SnackSlot(String item, double price, int count, int number) {
		this.item = item;
		this.price = price;
		this.count = count;
		this.number = number;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		return "SnackSlot [item=" + item + ", price=" + price + ", count=" + count + ", number=" + number + "]\n";
	}
}
