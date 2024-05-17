package banking;

public class Savings extends Account {
	public Savings(String ACCOUNT_ID, double ACCOUNT_APR, double startingBalance) {
		super(ACCOUNT_ID, ACCOUNT_APR, startingBalance, 2500, 1000, true, true);
	}

	@Override
	public void timePassHandler() {
		this.ACCOUNT_WITHDRAWABLE = true;
	}

	@Override
	public void uponSuccessfulWithdraw() {
		this.ACCOUNT_WITHDRAWABLE = false;
	}
}