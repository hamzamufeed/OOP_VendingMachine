import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class VendingMachine implements WorkFlow{
	private static double total_balance;
	private static float current_balance;
	private static double credit_balance;
	private static VendingMachine vendingMachine = null;
	private static ArrayList<CoinSlot> machineCoins;
	private static ArrayList<CoinSlot> currentCoins;
	private static ArrayList<NoteSlot> machineNotes;
	private static ArrayList<NoteSlot> currentNotes;
	private static SnackSlot[] machineSnacks;

	public VendingMachine() {
		Inialize();
	}

	public static VendingMachine getInstance(){
		if (vendingMachine == null)
			vendingMachine = new VendingMachine();
		return vendingMachine;
	}

	public static double getTotal_balance() {
		return total_balance;
	}

	public static void setTotal_balance(double total_balance) {
		VendingMachine.total_balance = total_balance;
	}

	public static float getCurrent_balance() {
		return current_balance;
	}

	public static void setCurrent_balance(float current_balance) {
		VendingMachine.current_balance = current_balance;
	}

	public static double getCredit_balance() {
		return credit_balance;
	}

	public static void setCredit_balance(double credit_balance) {
		VendingMachine.credit_balance = credit_balance;
	}

	public static ArrayList<CoinSlot> getMachineCoins() {
		return machineCoins;
	}

	public static void setMachineCoins(ArrayList<CoinSlot> machineCoins) {
		VendingMachine.machineCoins = machineCoins;
	}

	public static ArrayList<CoinSlot> getCurrentCoins() {
		return currentCoins;
	}

	public static void setCurrentCoins(ArrayList<CoinSlot> currentCoins) {
		VendingMachine.currentCoins = currentCoins;
	}

	public static ArrayList<NoteSlot> getMachineNotes() {
		return machineNotes;
	}

	public static void setMachineNotes(ArrayList<NoteSlot> machineNotes) {
		VendingMachine.machineNotes = machineNotes;
	}

	public static ArrayList<NoteSlot> getCurrentNotes() {
		return currentNotes;
	}

	public static void setCurrentNotes(ArrayList<NoteSlot> currentNotes) {
		VendingMachine.currentNotes = currentNotes;
	}

	public static SnackSlot[] getMachineSnacks() {
		return machineSnacks;
	}

	public static void setMachineSnacks(SnackSlot[] machineSnacks) {
		VendingMachine.machineSnacks = machineSnacks;
	}

	@Override
	public void Inialize() {
		machineCoins = new ArrayList<>();
		machineNotes = new ArrayList<>();
		currentCoins = new ArrayList<>();
		currentNotes = new ArrayList<>();
		total_balance = 0;
		current_balance = 0;
		credit_balance = 0;
		FillSnacks();
		FillMoney();
	}

	@Override
	public void FillSnacks() {
		machineSnacks = new SnackSlot[ROW*COLUMN];
		Random rand = new Random();
		for(int i=0; i<ROW*COLUMN; i++)
			machineSnacks[i] = new SnackSlot("Item"+(i+1), 
					1 + Math.round((Math.random() * (10 - 1))* 10d)/ 10d, rand.nextInt(15)+1, i+1);
	}

	@Override
	public void FillMoney() {
		String[] moneyTypes = {"10c","20c","50c","1$","20$","50$"};
		NoteSlot noteslot;
		CoinSlot coinslot;
		for(int i = 0; i<moneyTypes.length;i++) { 
			if(moneyTypes[i].equals("20$") || moneyTypes[i].equals("50$")) {
				noteslot = new NoteSlot(moneyTypes[i],ThreadLocalRandom.current().nextInt(5, 10));
				currentNotes.add(new NoteSlot(moneyTypes[i], 0));
				machineNotes.add(noteslot);
				total_balance = noteslot.AddBalance(total_balance);
			}
			else {
				coinslot = new CoinSlot(moneyTypes[i],ThreadLocalRandom.current().nextInt(50, 100));
				currentCoins.add(new CoinSlot(moneyTypes[i], 0));
				machineCoins.add(coinslot);
				total_balance = coinslot.AddBalance(total_balance);
			}
		}
	}

	@Override
	public void Start() {
		Scanner in = null;
		int choice;
		String buy;
		while(true) {
			try {
				System.out.println("--------------------------------------------------------------------------");
				for(int i=1;i<=ROW*COLUMN;i++) {
					System.out.print("[Number: "+machineSnacks[i-1].getNumber()+", Product:"+machineSnacks[i-1].getItem()+"]  ||  ");
					if(i%3 == 0)
						System.out.println();
				}
				System.out.println("\n-------------------------------------------------------------------------------------");
				System.out.print("Enter Snack Number: ");
				if(current_balance != 0)
					System.out.print("\nOr Enter 99 to return chnage: ");
				in = new Scanner(System.in);
				choice = in.nextInt();
				if(choice < 1 || choice > 25) {
					System.out.println("Invalid Input! Please Try again");
				}
				if(choice == 99 & current_balance != 0) {
					ReturnChange();
				}
				else if(machineSnacks[choice-1].getCount() == 0)
					System.out.println("Empty Slot");
				else {
					System.out.println("Snack: "+machineSnacks[choice-1].getItem()+" is Valid at Price:"+
							machineSnacks[choice-1].getPrice()+" with amount: "+machineSnacks[choice-1].getCount());

					while(true) {
						try {
							System.out.print("\nWould you like to buy this item? (y/n)? ");
							in = new Scanner(System.in);
							buy = in.next();
							if(buy.toLowerCase().charAt(0) == 'y') {
								InsertMoney(machineSnacks[choice-1]);
								break;
							}
							else if(buy.toLowerCase().charAt(0) == 'n')
								break;
						} catch (Exception e) {
							System.out.println("Invalid Input! Please Try again");
						}
					}						
				}
			} catch (Exception e) {
				System.out.println("Invalid Input! Please Try again");
			}
		}
	}

	@Override
	public void InsertMoney(SnackSlot snack) {
		Scanner in = null;
		int choice;
		while(true) {
			try {
				System.out.print("\nWould you like to pay using:\n1- Coins (Machine only accepts 10c, 20c, 50c & 1$)"
						+ "\n2- Notes (Machine only accepts 20$ & 50$)"
						+ "\n3- Credit Card"
						+ "\n4- Go Back\nInput:");
				in = new Scanner(System.in);
				choice = in.nextInt();
				if(choice < 0 || choice > 4) {
					System.out.println("Invalid Input! Please Try again");
				}
				else if (choice == 1){
					ValidateCoins(snack);
					ReturnChange();
					break;
				}
				else if (choice == 2){
					ValidateNotes(snack);
					ReturnChange();
					break;
				}
				else if (choice == 3){
					ValidateCard(snack);
					ReturnChange();
					break;
				}
				else if (choice == 4){
					break;
				}
			} catch (Exception e) {
				System.out.println("Invalid Input! Please Try again");
			}
		}
	}

	@Override
	public void ValidateCoins(SnackSlot snack) {
		Scanner in = null;
		String coin;
		int counter_10=0,counter_20=0,counter_50=0,counter_1=0;
		while(current_balance < snack.getPrice()) {
			try {
				System.out.println("Current Balance: "+current_balance+"$");
				System.out.print("\nInsert Coin or 9 to stop: ");
				in = new Scanner(System.in);
				coin = in.next();
				if(coin.equals("10")) {
					System.out.println("Validating...\nInserted: "+coin);
					counter_10++;
					currentCoins.get(0).setNumber(counter_10);
					machineCoins.get(0).setNumber(machineCoins.get(0).getNumber()+1);
					current_balance = currentCoins.get(0).updateCurrentBalance(current_balance);
					total_balance = currentCoins.get(0).updateTotalBalance(total_balance);
				}
				else if(coin.equals("20")) {
					System.out.println("Validating...\nInserted: "+coin);
					counter_20++;
					currentCoins.get(1).setNumber(counter_20);
					machineCoins.get(1).setNumber(machineCoins.get(1).getNumber()+1);
					current_balance = currentCoins.get(1).updateCurrentBalance(current_balance);
					total_balance = currentCoins.get(1).updateTotalBalance(total_balance);
				}
				else if(coin.equals("50")) {
					System.out.println("Validating...\nInserted: "+coin);
					counter_50++;
					currentCoins.get(2).setNumber(counter_50);
					machineCoins.get(2).setNumber(machineCoins.get(2).getNumber()+1);
					current_balance = currentCoins.get(2).updateCurrentBalance(current_balance);
					total_balance = currentCoins.get(2).updateTotalBalance(total_balance);
				}			
				else if(coin.equals("1")) {
					System.out.println("Validating...\nInserted: "+coin);
					counter_1++;
					currentCoins.get(3).setNumber(counter_1);
					machineCoins.get(3).setNumber(machineCoins.get(3).getNumber()+1);
					current_balance = currentCoins.get(3).updateCurrentBalance(current_balance);
					total_balance = currentCoins.get(3).updateTotalBalance(total_balance);
				}
				else if(coin.equals("9")) {
					System.out.println("Current Balance: "+current_balance+"$");
					break;
				}
				else {
					System.out.println("Returned! Machine only accepts 10c, 20c, 50c & 100c (1$)");
				}
			} catch (Exception e) {
				System.out.println("Invalid! Machine only accepts 10c, 20c, 50c & (1$)");
			}
		}
		System.out.println("\nDispensing Snack... Done");
		snack.setCount(snack.getCount()-1);
		current_balance-=snack.getPrice();
		System.out.println("Current Balance:"+current_balance+"$");
	}

	@Override
	public void ValidateNotes(SnackSlot snack) {
		Scanner in = null;
		String note;
		int counter_20=0,counter_50=0;
		while(current_balance < snack.getPrice()) {
			try {
				System.out.println("Current Balance: "+current_balance+"$");
				System.out.print("\nInsert Notes or 9 to stop: ");
				in = new Scanner(System.in);
				note = in.next();
				if(note.equals("20")) {
					System.out.println("Validating...\nInserted: "+note);
					counter_20++;
					currentNotes.get(0).setNumber(counter_20);
					machineNotes.get(0).setNumber(machineNotes.get(0).getNumber()+1);
					current_balance = currentNotes.get(0).updateCurrentBalance(current_balance);
					total_balance = currentNotes.get(0).updateTotalBalance(total_balance);
				}
				else if(note.equals("50")) {
					System.out.println("Validating...\nInserted: "+note);
					counter_50++;
					currentNotes.get(1).setNumber(counter_50);
					machineNotes.get(1).setNumber(machineNotes.get(1).getNumber()+1);
					current_balance = currentNotes.get(1).updateCurrentBalance(current_balance);
					total_balance = currentNotes.get(1).updateTotalBalance(total_balance);
				}
				else if(note.equals("9")) {
					System.out.println("Current Balance: "+current_balance+"$");
					break;
				}
				else 
					System.out.println("Returned! Machine only accepts 20$ & 50$");
			} catch (Exception e) {
				System.out.println("Invalid! Machine only accepts 20$ & 50$");
			}
		}
		System.out.println("\nDispensing Snack... Done");
		snack.setCount(snack.getCount()-1);
		current_balance-=snack.getPrice();
		System.out.println("Current Balance:"+current_balance+"$");
	}

	@Override
	public void ValidateCard(SnackSlot snack) {
		Scanner in = null;
		String card;
		while(true) {
			try {
				System.out.print("\nInsert Credit Card (Enter 1) or 9 to cancel:");
				in = new Scanner(System.in);
				card = in.next();
				if(card.equals("1")) {
					System.out.println("Validating...\nCard Inserted ");
					double num = ThreadLocalRandom.current().nextInt(100, 500);
					System.out.println("Card Balance:"+num+"$");
					System.out.println("Snack Price:"+snack.getPrice()+"$");
					snack.setCount(snack.getCount()-1);
					System.out.println("\nDispensing Snack");
					credit_balance += snack.getPrice();
					System.out.println("Card Balance:"+(num-snack.getPrice())+"$\nDone");
					break;
				}
				else if(card.equals("9")) {
					System.out.println("Cancelling...");
					break;
				}
				else 
					System.out.println("\nInsert Credit Card (Enter 1):");
			} catch (Exception e) {
				System.out.println("\nInsert Credit Card (Enter 1):");
			}
		}
	}


	@Override
	public void ReturnChange() {
		if(current_balance == 0)
			System.out.println("Change: "+0);
		else {
			Scanner in = null;
			String choice;
			while(true) {
				try {
					System.out.print("\nWould you like to take change now? (y/n)? ");
					in = new Scanner(System.in);
					choice = in.next();
					if(choice.toLowerCase().charAt(0) == 'y') {
						CalculateChange();
						break;
					}
					else if(choice.toLowerCase().charAt(0) == 'n')
						break;
				} catch (Exception e) {
					System.out.println("Invalid Input! Please Try again");
				}
			}
		}
	}

	@Override
	public void CalculateChange() {
		System.out.println("Change: "+current_balance);
		while(current_balance != 0 && total_balance != 0) {
			if(current_balance == 50 && machineNotes.get(1).getNumber() != 0) {
				System.out.println("Dispensing 50$");
				current_balance-=50;
				total_balance-=50;
				current_balance = (float) (Math.floor((current_balance) * 100) / 100);
				total_balance = (float) (Math.floor((total_balance) * 100) / 100);
				machineNotes.get(1).setNumber(machineNotes.get(1).getNumber() - 1);
			}
			else if(current_balance < 50 && current_balance >= 20 && machineNotes.get(0).getNumber() != 0) {
				System.out.println("Dispensing 20$");
				current_balance-=20;
				total_balance-=20;
				current_balance = (float) (Math.floor((current_balance) * 100) / 100);
				total_balance = (float) (Math.floor((total_balance) * 100) / 100);
				machineNotes.get(0).setNumber(machineNotes.get(0).getNumber() - 1);
			}
			else if(current_balance < 20 && current_balance >= 1 && machineCoins.get(3).getNumber() != 0) {
				current_balance-=1;
				total_balance-=1;
				current_balance = (float) (Math.floor((current_balance) * 100) / 100);
				total_balance = (float) (Math.floor((total_balance) * 100) / 100);
				System.out.println("Dispensing 1$");
				machineCoins.get(3).setNumber(machineCoins.get(3).getNumber() - 1);
			}
			else if(current_balance >= 0.5 && machineCoins.get(2).getNumber() != 0) {
				current_balance-=0.5;
				total_balance-=0.5;
				current_balance = (float) (Math.floor((current_balance) * 100) / 100);
				total_balance = (float) (Math.floor((total_balance) * 100) / 100);
				System.out.println("Dispensing 50c$");
				machineCoins.get(2).setNumber(machineCoins.get(2).getNumber() - 1);
			}
			else if(current_balance >= 0.2 && machineCoins.get(1).getNumber() != 0) {
				current_balance-=0.2;
				total_balance-=0.2;
				current_balance = (float) (Math.floor((current_balance) * 100) / 100);
				total_balance = (float) (Math.floor((total_balance) * 100) / 100);
				System.out.println("Dispensing 20c$");
				machineCoins.get(1).setNumber(machineCoins.get(1).getNumber() - 1);
			}
			else if(current_balance >= 0.1 && machineCoins.get(0).getNumber() != 0) {
				current_balance-=0.1;
				total_balance-=0.1;
				current_balance = (float) (Math.floor((current_balance) * 100) / 100);
				total_balance = (float) (Math.floor((total_balance) * 100) / 100);
				System.out.println("Dispensing 10c$");
				machineCoins.get(0).setNumber(machineCoins.get(0).getNumber() - 1);
			}
			else {
				BigDecimal a = new BigDecimal(current_balance);
				BigDecimal b = a.setScale(1, RoundingMode.UP);
				current_balance = b.floatValue();
				a = new BigDecimal(total_balance);
				b = a.setScale(2, RoundingMode.UP);
				total_balance = b.floatValue();
				//System.out.println("Current Balance: "+current_balance);
				if(current_balance != 0)
					continue;
				else
					break;
			}
		}
	}
}
