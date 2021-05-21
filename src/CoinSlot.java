
public class CoinSlot extends Money{
	private String type;
	private int number;

	public CoinSlot(String type, int number) {
		this.type = type;
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public double updateTotalBalance(double num) {
		if(this.type.equals("10c")) {
			return Math.floor((num + 0.1) * 100) / 100;
		}
		else if(this.type.equals("20c")) {
			return Math.floor((num + 0.2) * 100) / 100;
		}
		else if(this.type.equals("50c")) {
			return Math.floor((num + 0.5) * 100) / 100;
		}
		else if(this.type.equals("1$")) {
			return Math.floor((num + 1) * 100) / 100;
		}
		else return 0;
	}

	@Override
	public float updateCurrentBalance(double num) {
		if(this.type.equals("10c")) {
			return (float) (Math.floor((num + 0.1) * 100) / 100);
		}
		else if(this.type.equals("20c")) {
			return (float) (Math.floor((num + 0.2) * 100) / 100);
		}
		else if(this.type.equals("50c")) {
			return (float) (Math.floor((num + 0.5) * 100) / 100);
		}
		else if(this.type.equals("1$")) {
			return (float) (Math.floor((num + 1) * 100) / 100);
		}
		else return 0;
	}

	@Override
	public double AddBalance(double total) {
		if(this.type.contentEquals("10c")) {
			return (total +(0.1*this.number));
		}
		else if(this.type.contentEquals("20c")) {
			return (total +(0.2*this.number));
		}
		else if(this.type.contentEquals("50c")) {
			return (total +(0.5*this.number));
		}
		else if(this.type.contentEquals("1$")) {
			return (total +(1*this.number));
		}
		else return 0;
	}

	@Override
	public String toString() {
		return "CoinSlot [type=" + type + ", number=" + number + "\n]";
	}

}
