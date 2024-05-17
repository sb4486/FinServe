package banking;

public class Checking extends Account {
	public Checking(String ACCOUNT_ID, double ACCOUNT_APR, double startingBalance) {
		super(ACCOUNT_ID, ACCOUNT_APR, startingBalance, 1000, 400, true, true);
	}
}