
public interface WorkFlow {
	final static int ROW = 5;
	final static int COLUMN = 5;
	
	public void Inialize();
	
	public void FillMoney();
	
	public void FillSnacks();
	
	public void Start();
	
	public void InsertMoney(SnackSlot snack);
	
	public void ValidateCoins(SnackSlot snack);
	
	public void ValidateNotes(SnackSlot snack);
	
	public void ValidateCard(SnackSlot snack);
	
	public void ReturnChange();
	
	public void CalculateChange();
}
