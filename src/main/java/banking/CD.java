package banking;

public class CD extends Account {
	public CD(String ACCOUNT_ID, double ACCOUNT_APR, double startingBalance) {
		super(ACCOUNT_ID, ACCOUNT_APR, startingBalance, 0.00, 0.00, false, false);
	}

	@Override
	public void timePassHandler() {
		if (this.getAge() >= 12) {
			this.ACCOUNT_WITHDRAWABLE = true;
			this.MAX_WITHDRAW_AMOUNT = this.getBalance();
		}
	}

	@Override
	public void uponSuccessfulWithdraw() {
		this.ACCOUNT_WITHDRAWABLE = false;
		this.ACCOUNT_AGE = 0;
		this.MAX_WITHDRAW_AMOUNT = 0.00;
	}
}