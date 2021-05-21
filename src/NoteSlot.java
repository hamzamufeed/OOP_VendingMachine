
public class NoteSlot extends Money{
	private String type;
	private int number;
	
	
	public NoteSlot(String type, int number) {
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
		if(this.type.equals("20$")) {
			return Math.floor((num + 20) * 100) / 100;
		}
		else if(this.type.equals("50$")) {
			return Math.floor((num + 50) * 100) / 100;
		}
		else return 0;
	}


	@Override
	public float updateCurrentBalance(double num) {
		if(this.type.equals("20$")) {
			return (float) (Math.floor((num + 20) * 100) / 100);
		}
		else if(this.type.equals("50$")) {
			return (float) (Math.floor((num + 50) * 100) / 100);
		}
		else return 0;
	}
	
	@Override
	public double AddBalance(double total) {
		if(this.type.contentEquals("20$")) {
			return ((20*this.number) + total);
		}
		else if(this.type.contentEquals("50$")) {
			return ((50*this.number) + total);
		}
		else return 0;
	}


	@Override
	public String toString() {
		return "NoteSlot [type=" + type + ", number=" + number + "]\n";
	}
	
	
}
