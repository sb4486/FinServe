package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	CommandStorage commandStorage;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandStorage = new CommandStorage(bank);
	}

	@Test
	public void invalid_command_is_stored() {
		commandStorage.addInvalidCommand("creat checking 12345678 1.0");
		assertEquals("creat checking 12345678 1.0", commandStorage.getInvalidCommands().get(0));
	}

	@Test
	public void multiple_invalid_commands_are_stored() {
		commandStorage.addInvalidCommand("creat savings 12345678 1.0");
		commandStorage.addInvalidCommand("createe checking 12345678 1.0");
		commandStorage.addInvalidCommand("deposit 12345666 50");
		assertEquals(3, commandStorage.getInvalidCommands().size());
	}

	@Test
	public void valid_command_is_stored() {
		commandStorage.addValidCommand("create checking 12345678 1.0");
		assertEquals("create checking 12345678 1.0", commandStorage.getValidCommands().get(0));
	}

	@Test
	public void multiple_valid_commands_are_stored() {
		commandStorage.addValidCommand("create savings 12345678 1.0");
		commandStorage.addValidCommand("create checking 12345678 1.0");
		commandStorage.addValidCommand("deposit 12345666 50");
		assertEquals(3, commandStorage.getValidCommands().size());
	}
}