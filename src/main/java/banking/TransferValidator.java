package banking;

public class TransferValidator extends CommandValidator {
	private Bank bank;

	public TransferValidator(Bank bank) {
		super(bank);
		this.bank = bank;
	}

	@Override
	public boolean validate(String command) {
		String[] tokenizedCommand = commandSplit(command);
		double transferAmount;

		if (!isValidCommandLength(tokenizedCommand, 4)) {
			return false;
		}

		String sendingAccountID = tokenizedCommand[1];
		String receivingAccountID = tokenizedCommand[2];

		try {
			transferAmount = Double.parseDouble(tokenizedCommand[3]);
		} catch (NumberFormatException e) {
			return false;
		}

		if (!accountIDChecks(sendingAccountID) || !accountIDChecks(receivingAccountID)
				|| isTransferredAccountSame(sendingAccountID, receivingAccountID)
				|| !isValidTransferAmount(sendingAccountID, transferAmount) || !isTransferable(receivingAccountID)) {
			return false;
		}

		return true;
	}

	private boolean accountIDChecks(String accountID) {
		if (!isIDNumeric(accountID) || !isIDLengthValid(accountID) || !doesAccountExist(accountID)) {
			return false;
		}
		return true;
	}

	private boolean isTransferable(String receivingAccountID) {
		return bank.getAccount(receivingAccountID).isTransferable();
	}

	private boolean isTransferredAccountSame(String sendingAccount, String receivingAccount) {
		return (sendingAccount.equalsIgnoreCase(receivingAccount));
	}

	private boolean isValidTransferAmount(String sendingAccountID, double transferAmount) {
		return (transferAmount >= 0.0) && (transferAmount <= bank.getAccount(sendingAccountID).getWithdraw());
	}
}