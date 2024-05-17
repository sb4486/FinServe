package banking;

public class PassTimeValidator extends CommandValidator {
	public PassTimeValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String command) {
		String[] tokenizedCommand = commandSplit(command);
		int timeToPass;

		if (!isValidCommandLength(tokenizedCommand, 2)) {
			return false;
		}

		try {
			timeToPass = Integer.parseInt(tokenizedCommand[1]);
		} catch (NumberFormatException e) {
			return false;
		}

		if (!isValidPassTime(timeToPass)) {
			return false;
		}

		return true;
	}

	private boolean isValidPassTime(int timeToPass) {
		return (timeToPass >= 1) && (timeToPass <= 60);
	}
}