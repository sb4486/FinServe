package banking;

public class DepositValidator extends CommandValidator {
	private Bank bank;

	public DepositValidator(Bank bank) {
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
		String amountStr = tokenizedCommand[2];

		if (!isIDNumeric(accountID) || !isIDLengthValid(accountID) || !doesAccountExist(accountID)) {
			return false;
		}

		if (!isValidDepositAmount(accountID, amountStr)) {
			return false;
		}

		return true;
	}

	private boolean isValidDepositAmount(String accountId, String amountStr) {
		double amount;

		try {
			amount = Double.parseDouble(amountStr);
		} catch (NumberFormatException e) {
			return false;
		}

		return (amount >= 0.0) && (amount <= bank.getAccount(accountId).getDeposit());
	}
}