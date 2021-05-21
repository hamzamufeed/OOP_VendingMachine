
public class CardSlot extends Money{
	private double charge;

	public CardSlot(double charge) {
		this.charge = charge;
	}
	
	public double getCharge() {
		return charge;
	}

	public void setCharge(double charge) {
		this.charge = charge;
	}

	@Override
	public double updateTotalBalance(double num) {
		return 0;
	}

	@Override
	public float updateCurrentBalance(double num) {
		return 0;		
	}

	@Override
	public double AddBalance(double total) {
		return (total+this.charge);
	}

	@Override
	public String toString() {
		return "CardSlot [charge=" + charge + "]";
	}

	
}
