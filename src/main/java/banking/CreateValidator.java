package banking;

public class CreateValidator extends CommandValidator {
	public CreateValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String command) {
		String[] tokenizedCommand = commandSplit(command);

		if ((!isValidCommandLength(tokenizedCommand, 4) && !isValidCommandLength(tokenizedCommand, 5))) {
			return false;
		}

		String accountType = tokenizedCommand[1], accountID = tokenizedCommand[2];
		double accountAPR;

		try {
			accountAPR = Double.parseDouble(tokenizedCommand[3]);
		} catch (NumberFormatException e) {
			return false;
		}

		if (!isIDNumeric(accountID) || !isIDLengthValid(accountID) || !isValidAccountType(accountType)
				|| doesAccountExist(accountID) || !isAPRInRange(accountAPR)) {
			return false;
		}

		if (accountType.equalsIgnoreCase("banking.CD")) {
			return isValidCDParameters(tokenizedCommand);
		}

		return true;
	}

	private boolean isValidAccountType(String accountType) {
		switch (accountType.toLowerCase()) {
		case "banking.cd":
		case "banking.savings":
		case "banking.checking":
			return true;
		default:
			return false;
		}
	}

	private boolean isAPRInRange(double apr) {
		return apr >= 0.0 && apr <= 10.0;
	}

	private boolean isValidCDParameters(String[] tokenizedCommand) {
		if (tokenizedCommand.length != 5) {
			return false;
		}
		try {
			double startingCDBalance = Double.parseDouble(tokenizedCommand[4]);
			return isCDBalanceInRange(startingCDBalance);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isCDBalanceInRange(double startingCDBalance) {
		return startingCDBalance >= 1000 && startingCDBalance <= 10000;
	}
}