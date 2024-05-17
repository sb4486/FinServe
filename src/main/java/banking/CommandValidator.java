package banking;

public class CommandValidator {

	protected Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] tokenizedCommand = commandSplit(command);
		if (tokenizedCommand[0].equalsIgnoreCase("create")) {
			CreateValidator createValidator = new CreateValidator(bank);
			return createValidator.validate(command);
		} else if (tokenizedCommand[0].equalsIgnoreCase("deposit")) {
			DepositValidator depositValidator = new DepositValidator(bank);
			return depositValidator.validate(command);
		} else if (tokenizedCommand[0].equalsIgnoreCase("withdraw")) {
			WithdrawValidator withdrawValidator = new WithdrawValidator(bank);
			return withdrawValidator.validate(command);
		} else if (tokenizedCommand[0].equalsIgnoreCase("pass")) {
			PassTimeValidator passTimeValidator = new PassTimeValidator(bank);
			return passTimeValidator.validate(command);
		} else if (tokenizedCommand[0].equalsIgnoreCase("transfer")) {
			TransferValidator transferValidator = new TransferValidator(bank);
			return transferValidator.validate(command);
		} else {
			return false;
		}
	}

	protected String[] commandSplit(String command) {
		return command.split("\\s+");
	}

	protected boolean isValidCommandLength(String[] commandParts, int maxLength) {
		return commandParts.length == maxLength;
	}

	protected boolean isIDNumeric(String accountId) {
		return (accountId.matches("[0-9]+"));
	}

	protected boolean isIDLengthValid(String accountId) {
		return (accountId.length() == 8);
	}

	protected boolean doesAccountExist(String accountId) {
		return bank.getAccount(accountId) != null;
	}
}
