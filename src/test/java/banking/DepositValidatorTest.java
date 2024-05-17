package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	DepositValidator depositValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		depositValidator = new DepositValidator(bank);

		bank.addAccount("banking.Savings", "12345678", 0.5);
		bank.addAccount("banking.Checking", "23456789", 0);
		bank.addAccount("banking.CD", "34567890", 1.5, 1500);
	}

	/////////////////////////
	//// SAVINGS TESTS ////
	/////////////////////

	// Valid Test Cases
	@Test
	public void successful_deposit_command_savings() {
		boolean validatedCommand = depositValidator.validate("deposit 12345678 2500");
		assertTrue(validatedCommand);
	}

	@Test
	public void deposit_zero_savings() {
		boolean validatedCommand = depositValidator.validate("deposit 12345678 0");
		assertTrue(validatedCommand);
	}

	// Invalid Test Cases
	@Test
	public void deposit_over_limit_savings() {
		boolean validatedCommand = depositValidator.validate("deposit 12345678 2501");
		assertFalse(validatedCommand);
	}

	@Test
	public void negative_deposit_amount_savings() {
		boolean validatedCommand = depositValidator.validate("deposit 12345678 -1");
		assertFalse(validatedCommand);
	}

	@Test
	public void account_id_less_than_8_characters_savings() {
		boolean validatedCommand = depositValidator.validate("deposit 1234567 100");
		assertFalse(validatedCommand);
	}

	@Test
	public void account_id_more_than_8_characters_savings() {
		boolean validatedCommand = depositValidator.validate("deposit 123456789 100");
		assertFalse(validatedCommand);
	}

	@Test
	public void account_id_with_non_digit_characters_savings() {
		boolean validatedCommand = depositValidator.validate("deposit 1234ABCD 100");
		assertFalse(validatedCommand);
	}

	/////////////////////////
	//// CHECKING TESTS ////
	///////////////////////

	// Valid Test Cases
	@Test
	public void successful_deposit_command_checking() {
		boolean validatedCommand = depositValidator.validate("deposit 23456789 1000");
		assertTrue(validatedCommand);
	}

	@Test
	public void deposit_zero_checking() {
		boolean validatedCommand = depositValidator.validate("deposit 23456789 0");
		assertTrue(validatedCommand);
	}

	// Invalid Test Cases
	@Test
	public void deposit_over_limit_checking() {
		boolean validatedCommand = depositValidator.validate("deposit 23456789 1001");
		assertFalse(validatedCommand);
	}

	@Test
	public void negative_deposit_amount_checking() {
		boolean validatedCommand = depositValidator.validate("deposit 12345678 -1");
		assertFalse(validatedCommand);
	}

	@Test
	public void account_id_less_than_8_characters_checking() {
		boolean validatedCommand = depositValidator.validate("deposit 2345678 100");
		assertFalse(validatedCommand);
	}

	@Test
	public void account_id_more_than_8_characters_checking() {
		boolean validatedCommand = depositValidator.validate("deposit 234567890 100");
		assertFalse(validatedCommand);
	}

	@Test
	public void account_id_with_non_digit_characters_checking() {
		boolean validatedCommand = depositValidator.validate("deposit 2345ABCD 100");
		assertFalse(validatedCommand);
	}

	///////////////////
	//// banking.CD TESTS ////
	/////////////////

	// Invalid Test Cases
	@Test
	public void deposit_into_cd() {
		boolean validatedCommand = depositValidator.validate("deposit 34567890 30");
		assertFalse(validatedCommand);
	}

	// Invalid Test Cases
	@Test
	public void account_id_less_than_8_characters_cd() {
		boolean validatedCommand = depositValidator.validate("deposit 3456789 100");
		assertFalse(validatedCommand);
	}

	@Test
	public void account_id_more_than_8_characters_cd() {
		boolean validatedCommand = depositValidator.validate("deposit 345678901 100");
		assertFalse(validatedCommand);
	}

	@Test
	public void account_id_with_non_digit_characters_cd() {
		boolean validatedCommand = depositValidator.validate("deposit 3456ABCD 100");
		assertFalse(validatedCommand);
	}

	////////////////////////
	//// GENERAL TESTS ////
	//////////////////////

	// Invalid Test Cases
	@Test
	public void account_does_not_exist() {
		boolean validatedCommand = depositValidator.validate("deposit 88888888 500");
		assertFalse(validatedCommand);
	}

	@Test
	public void deposit_amount_is_string() {
		boolean validatedCommand = depositValidator.validate("deposit 12345678 50C");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_amount_argument() {
		boolean validatedCommand = depositValidator.validate("deposit 12345678");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_id_argument() {
		boolean validatedCommand = depositValidator.validate("deposit 500");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_deposit_argument() {
		boolean validatedCommand = depositValidator.validate("12345678 500");
		assertFalse(validatedCommand);
	}

	@Test
	public void invalid_account_id_format() {
		boolean validatedCommand = depositValidator.validate("deposit 1234ABCD 100");
		assertFalse(validatedCommand);
	}

	@Test
	public void extra_arguments() {
		boolean validatedCommand = depositValidator.validate("deposit 23456789 1000 500");
		assertFalse(validatedCommand);
	}
}