package banking;

import java.util.ArrayList;

public class MasterControl {

	private CommandValidator commandValidator;
	private CommandProcessor commandProcessor;
	private CommandStorage commandStorage;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage) {
		this.commandValidator = commandValidator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
	}

	public ArrayList<String> start(ArrayList<String> input) {
		for (String command : input) {
			command = preprocessCommand(command);
			if (commandValidator.validate(command)) {
				commandStorage.addValidCommand(command);
				commandProcessor.execute(command);
			} else {
				commandStorage.addInvalidCommand(command);
			}
		}
		return commandStorage.getOutput();
	}

	private String preprocessCommand(String command) {
		command = replaceAccountType(command, "savings");
		command = replaceAccountType(command, "checking");
		command = replaceAccountType(command, "cd");
		return command;
	}

	private String replaceAccountType(String command, String accountType) {
		return command.replaceAll("(?i)" + accountType, "banking." + accountType);
	}
}