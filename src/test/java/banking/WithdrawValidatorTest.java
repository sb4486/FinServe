package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawValidatorTest {
	WithdrawValidator withdrawValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		withdrawValidator = new WithdrawValidator(bank);

		bank.addAccount("banking.Savings", "12345678", 0.5);
		bank.addAccount("banking.Checking", "23456789", 0);
		bank.addAccount("banking.CD", "34567890", 1.5, 400);

		bank.getAccount("12345678").deposit(950);
		bank.getAccount("23456789").deposit(300);
	}

	// Valid Test Cases

	@Test
	public void withdraw_upper_boundary_savings() {
		boolean actual = withdrawValidator.validate("withdraw 12345678 200");
		assertTrue(actual);
	}

	@Test
	public void withdraw_lower_boundary_savings() {
		boolean actual = withdrawValidator.validate("withdraw 12345678 0");
		assertTrue(actual);
	}

	@Test
	public void withdraw_upper_boundary_checking() {
		boolean actual = withdrawValidator.validate("withdraw 23456789 400");
		assertTrue(actual);
	}

	@Test
	public void withdraw_lower_boundary_checking() {
		boolean actual = withdrawValidator.validate("withdraw 23456789 0");
		assertTrue(actual);
	}

	@Test
	public void withdraw_more_than_balance_checking() {
		boolean actual = withdrawValidator.validate("withdraw 23456789 400");
		assertTrue(actual);
	}

	@Test
	public void withdraw_more_than_balance_savings() {
		boolean actual = withdrawValidator.validate("withdraw 23456789 400");
		assertTrue(actual);
	}

	// Invalid Test Cases

	@Test
	public void withdraw_cd() {
		boolean actual = withdrawValidator.validate("withdraw 34567890 25");
		assertFalse(actual);
	}

	@Test
	public void withdraw_negative_amount_checking() {
		boolean actual = withdrawValidator.validate("withdraw 23456789 -1");
		assertFalse(actual);
	}

	@Test
	public void withdraw_negative_amount_savings() {
		boolean actual = withdrawValidator.validate("withdraw 12345678 -1");
		assertFalse(actual);
	}

	@Test
	public void invalid_command_length() {
		boolean actual = withdrawValidator.validate("withdraw 34567890 1000 20");
		assertFalse(actual);
	}

	@Test
	public void non_numeric_balance_checking() {
		boolean actual = withdrawValidator.validate("withdraw 12345678 ABCD");
		assertFalse(actual);
	}

	@Test
	public void non_numeric_id() {
		boolean actual = withdrawValidator.validate("withdraw ABCDEFGH 1000");
		assertFalse(actual);
	}

	@Test
	public void account_id_more_than_8_characters_savings() {
		boolean validatedCommand = withdrawValidator.validate("withdraw 123456789 100");
		assertFalse(validatedCommand);
	}

	@Test
	public void account_does_not_exist() {
		boolean validatedCommand = withdrawValidator.validate("withdraw 99999999 100");
		assertFalse(validatedCommand);
	}

	@Test
	public void withdraw_nothing_cd() {
		boolean validatedCommand = withdrawValidator.validate("withdraw 34567890 0");
		assertFalse(validatedCommand);
	}

	@Test
	public void cd_boundary_test() {
		boolean validatedCommand = withdrawValidator.validate("withdraw 34567890 400");
		assertFalse(validatedCommand);
	}

	@Test
	public void cd_negative_test() {
		boolean validatedCommand = withdrawValidator.validate("withdraw 34567890 -1");
		assertFalse(validatedCommand);
	}

	@Test
	public void withdraw_more_than_limit_cd() {
		boolean validatedCommand = withdrawValidator.validate("withdraw 34567890 500");
		assertFalse(validatedCommand);
	}
}