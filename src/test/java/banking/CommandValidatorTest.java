package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);

		bank.addAccount("banking.Savings", "12345678", 0.5);
		bank.addAccount("banking.Checking", "23456789", 0);
		bank.addAccount("banking.CD", "34567890", 1.5, 1500);
	}

	// Valid Commands

	@Test
	public void valid_deposit_command() {
		boolean actual = commandValidator.validate("deposit 12345678 30");
		assertTrue(actual);
	}

	@Test
	public void valid_withdraw_command() {
		boolean actual = commandValidator.validate("withdraw 12345678 30");
		assertTrue(actual);
	}

	@Test
	public void valid_pass_time_command() {
		boolean actual = commandValidator.validate("Pass 4");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command() {
		boolean actual = commandValidator.validate("Transfer 12345678 23456789 100");
		assertTrue(actual);
	}

	// Invalid Commands

	@Test
	public void invalid_test_command() {
		boolean actual = commandValidator.validate("add 12345678 30");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command() {
		boolean actual = commandValidator.validate("deposit 9999999 30");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command() {
		boolean actual = commandValidator.validate("withdraw 9999999 30");
		assertFalse(actual);
	}

	@Test
	public void invalid_pass_time_command() {
		boolean actual = commandValidator.validate("Pass -1");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command() {
		boolean actual = commandValidator.validate("Transfer 12345678 23456789 -100");
		assertFalse(actual);
	}
}