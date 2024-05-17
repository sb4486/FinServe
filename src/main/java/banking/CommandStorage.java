package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CommandStorage {
	protected Bank bank;
	ArrayList<String> invalidCommandList = new ArrayList<>();
	ArrayList<String> validCommandList = new ArrayList<>();
	ArrayList<String> outputList = new ArrayList<>();
	DecimalFormat decimalFormat = new DecimalFormat("0.00");

	public CommandStorage(Bank bank) {
		this.bank = bank;
		this.decimalFormat.setRoundingMode(RoundingMode.FLOOR);

	}

	public void addInvalidCommand(String command) {
		invalidCommandList.add(command.replace("banking.", ""));
	}

	public void addValidCommand(String command) {
		validCommandList.add(command.replace("banking.", ""));
	}

	public ArrayList<String> getValidCommands() {
		return validCommandList;
	}

	public ArrayList<String> getInvalidCommands() {
		return invalidCommandList;
	}

	public ArrayList<String> getOutput() {
		outputList.addAll(getValidOutput(getValidCommands()));
		outputList.addAll(getInvalidCommands());

		return outputList;
	}

	public ArrayList<String> getValidOutput(ArrayList<String> validCommands) {
		ArrayList<String> orderedOutputs = new ArrayList<>();
		for (String command : validCommands) {
			String[] commandArray = command.split("\\s+");
			if ("create".equalsIgnoreCase(commandArray[0])) {
				String accountID = commandArray[2];
				if (bank.getAccounts().containsKey(accountID)) {
					for (String commandRevised : validCommands) {
						if (commandRevised.contains(accountID)) {
							orderedOutputs.add(commandRevised);
						}
					}
				}
			}
		}
		return beautifyOutput(orderedOutputs);
	}

	public ArrayList<String> beautifyOutput(ArrayList<String> validCommands) {
		ArrayList<String> beautifiedOutput = new ArrayList<>();
		for (String command : validCommands) {
			String[] commandArray = command.split("\\s+");
			switch (commandArray[0].toLowerCase()) {
				case "create":
					Account account = bank.getAccount(commandArray[2]);
					String accountType = capitalize(account.getType());
					String accountID = account.getID();
					String accountBalance = decimalFormat.format((account.getBalance()));
					String accountAPR = decimalFormat.format((account.getAPR()));

					beautifiedOutput.add(accountType + " " + accountID + " " + accountBalance + " " + accountAPR);
					break;
				case "deposit":
				case "withdraw":
					beautifiedOutput.add(capitalize(commandArray[0]) + " " + commandArray[1] + " " + commandArray[2]);
					break;
				case "transfer":
					beautifiedOutput
							.add(capitalize(commandArray[0]) + " " + commandArray[1] + " " + commandArray[2] + " "
									+ commandArray[3]);
					break;
				default:
					break;
			}
		}
		return beautifiedOutput;
	}

	private String capitalize(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
	}
}