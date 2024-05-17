package banking;

import java.util.ArrayList;

public class CommandProcessor {
	protected Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void execute(String command) {
		String[] tokenizedCommand = (command.split("\\s+"));

		switch (tokenizedCommand[0].toLowerCase()) {
		case "create":
			createHandler(tokenizedCommand);
			break;
		case "deposit":
			depositHandler(tokenizedCommand);
			break;
		case "withdraw":
			withdrawHandler(tokenizedCommand);
			break;
		case "pass":
			passTimeHandler(tokenizedCommand);
			break;
		case "transfer":
			transferHandler(tokenizedCommand);
			break;
		default:
			break;
		}

	}

	private void createHandler(String[] tokenizedCommand) {
		String accountType = tokenizedCommand[1], accountID = tokenizedCommand[2];
		double accountAPR = Double.parseDouble(tokenizedCommand[3]), startingBalance = 0.0;

		if (tokenizedCommand.length == 5) {
			startingBalance = Double.parseDouble(tokenizedCommand[4]);
		}

		addAccount(accountType, accountID, accountAPR, startingBalance);
	}

	private void depositHandler(String[] tokenizedCommand) {
		String accountID = tokenizedCommand[1];
		double depositAmount = Double.parseDouble(tokenizedCommand[2]);

		bank.getAccount(accountID).deposit(depositAmount);
	}

	private void withdrawHandler(String[] tokenizedCommand) {
		String accountID = tokenizedCommand[1];
		double withdrawAmount = Double.parseDouble(tokenizedCommand[2]);

		bank.getAccount(accountID).uponSuccessfulWithdraw();
		bank.getAccount(accountID).withdraw(withdrawAmount);
	}

	private void passTimeHandler(String[] tokenizedCommand) {
		int timeToPass = Integer.parseInt(tokenizedCommand[1]);
		ArrayList<String> accountsToRemove = new ArrayList<>();

		for (int month = 1; month <= timeToPass; month++) {
			for (Account account : new ArrayList<>(bank.getAccounts().values())) {
				account.addMonth();
				if (account.getBalance() == 0) {
					accountsToRemove.add(account.getID());
					continue;
				}

				if (account.getBalance() < 100) {
					account.withdraw(25);
				}

				account.timePassHandler();
				handleAPRInterest(account);
			}
		}

		String[] accountsToRemoveArray = accountsToRemove.toArray(new String[0]);
		for (String accountId : accountsToRemoveArray) {
			bank.getAccounts().remove(accountId);
		}
	}

	private void transferHandler(String[] tokenizedCommand) {
		Account sendingAccount = bank.getAccount(tokenizedCommand[1]);
		Account receivingAccount = bank.getAccount(tokenizedCommand[2]);
		double transferAmount = Double.parseDouble(tokenizedCommand[3]);

		double sendingAmountBalance = sendingAccount.getBalance();
		if (Math.max(transferAmount, sendingAmountBalance) == transferAmount) {
			transferAmount = sendingAmountBalance;
		}

		sendingAccount.withdraw(transferAmount);
		receivingAccount.deposit(transferAmount);
	}

	private void handleAPRInterest(Account account) {
		double accountAPR = account.getAPR();
		double monthlyRate = accountAPR / 100 / 12;
		double originalBalance = account.getBalance();
		double newBalance = account.getBalance();
		int iterations = account.getType().equalsIgnoreCase("CD") ? 4 : 1;

		for (int i = 0; i < iterations; i++) {
			double interest = newBalance * monthlyRate;
			newBalance += interest;
		}
		account.deposit(newBalance - originalBalance);
	}

	private void addAccount(String accountType, String accountID, double accountAPR, double startingBalance) {
		switch (accountType.toLowerCase()) {
		case "banking.cd":
			bank.addAccount("banking.CD", accountID, accountAPR, startingBalance);
			break;
		case "banking.checking":
			bank.addAccount("banking.Checking", accountID, accountAPR);
			break;
		case "banking.savings":
			bank.addAccount("banking.Savings", accountID, accountAPR);
			break;
		default:
			break;
		}
	}
}