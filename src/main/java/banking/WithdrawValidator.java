package banking;

public class WithdrawValidator extends CommandValidator {
	private Bank bank;

	public WithdrawValidator(Bank bank) {
		super(bank);
		this.bank = bank;
	}

	@Override
	public boolean validate(String command) {
		String[] tokenizedCommand = commandSplit(command);

		if (!isValidCommandLength(tokenizedCommand, 3)) {
			return false;
		}

		String accountID = tokenizedCommand[1];
		String account = tokenizedCommand[2];

		if (!isIDNumeric(accountID) || !isIDLengthValid(accountID) || !doesAccountExist(accountID)) {
			return false;
		}

		if (!isValidWithdrawAmount(accountID, account) || !isWithdrawable(accountID)) {
			return false;
		}

		return true;
	}

	private boolean isValidWithdrawAmount(String accountID, String account) {
		double amount;

		try {
			amount = Double.parseDouble(account);
		} catch (NumberFormatException e) {
			return false;
		}

		if (bank.getAccount(accountID).getType().equalsIgnoreCase("cd")) {
			return (amount >= bank.getAccount(accountID).getWithdraw());
		} else {
			return (amount >= 0.0) && (amount <= bank.getAccount(accountID).getWithdraw());
		}

	}

	private boolean isWithdrawable(String accountID) {
		return bank.getAccount(accountID).isWithdrawable();
	}
}