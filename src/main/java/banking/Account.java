package banking;

public abstract class Account {
	private final String ACCOUNT_ID;
	private final double ACCOUNT_APR;
	public Double MAX_DEPOSIT_AMOUNT;
	public Double MAX_WITHDRAW_AMOUNT;
	public int ACCOUNT_AGE = 0;
	public boolean ACCOUNT_WITHDRAWABLE;
	public boolean ACCOUNT_TRANSFERABLE;
	private double Balance;

	protected Account(String ACCOUNT_ID, double ACCOUNT_APR, double ACCOUNT_STARTING_BALANCE, double MAX_DEPOSIT_AMOUNT,
			double MAX_WITHDRAW_AMOUNT, boolean ACCOUNT_WITHDRAWABLE, boolean ACCOUNT_TRANSFERABLE) {
		this.ACCOUNT_ID = ACCOUNT_ID;
		this.ACCOUNT_APR = ACCOUNT_APR;
		this.Balance = ACCOUNT_STARTING_BALANCE;
		this.MAX_DEPOSIT_AMOUNT = MAX_DEPOSIT_AMOUNT;
		this.MAX_WITHDRAW_AMOUNT = MAX_WITHDRAW_AMOUNT;
		this.ACCOUNT_WITHDRAWABLE = ACCOUNT_WITHDRAWABLE;
		this.ACCOUNT_TRANSFERABLE = ACCOUNT_TRANSFERABLE;
	}

	public void deposit(double depositAmount) {
		this.Balance += depositAmount;
	}

	public double getBalance() {
		return this.Balance;
	}

	public double getAPR() {
		return this.ACCOUNT_APR;
	}

	public String getID() {
		return this.ACCOUNT_ID;
	}

	public Double getDeposit() {
		return this.MAX_DEPOSIT_AMOUNT;
	}

	public Double getWithdraw() {
		return this.MAX_WITHDRAW_AMOUNT;
	}

	public String getType() {
		return this.getClass().getSimpleName();
	}

	public int getAge() {
		return this.ACCOUNT_AGE;
	}

	public void withdraw(double withdrawalAmount) {
		if (Math.max(withdrawalAmount, this.Balance) == withdrawalAmount) {
			this.Balance = 0;
		} else {
			this.Balance -= withdrawalAmount;
		}
	}

	public boolean isTransferable() {
		return this.ACCOUNT_TRANSFERABLE;
	}

	public boolean isWithdrawable() {
		return this.ACCOUNT_WITHDRAWABLE;
	}

	public void addMonth() {
		this.ACCOUNT_AGE += 1;
	}

	public void timePassHandler() {
		;
	}

	public void uponSuccessfulWithdraw() {
		;
	}
}