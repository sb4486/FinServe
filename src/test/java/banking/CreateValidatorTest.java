package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {
	CreateValidator createValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		createValidator = new CreateValidator(bank);

		bank.addAccount("banking.Savings", "12345678", 0.5);
		bank.addAccount("banking.Checking", "23456789", 0);
		bank.addAccount("banking.CD", "34567890", 1.5, 1500);
	}

	/////////////////////////
	//// CHECKING TESTS ////
	///////////////////////

	// Valid Test Cases
	@Test
	public void empty_bank_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking 87654321 5.0");
		assertTrue(validatedCommand);
	}

	@Test
	public void multiple_accounts_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking 87654321 5.0");
		assertTrue(validatedCommand);
	}

	@Test
	public void apr_at_lower_boundary_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking 87654321 0.0");
		assertTrue(validatedCommand);
	}

	@Test
	public void apr_at_upper_boundary_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking 87654321 10.0");
		assertTrue(validatedCommand);
	}

	// Invalid Test Cases

	@Test
	public void string_based_id_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking ABCDEFGH 5.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_account_id_and_apr_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_account_type_checking() {
		boolean validatedCommand = createValidator.validate("create 12345678 5.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_apr_argument_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking 12345678");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_id_argument_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking 5.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void incorrect_id_length_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking 1234567890 5.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void negative_account_id_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking -1234567 5.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void apr_less_than_range_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking 12345678 -1.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void apr_more_than_range_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking 12345678 11.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void extra_arguments_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking 12345678 5.0 30");
		assertFalse(validatedCommand);
	}

	@Test
	public void duplicate_account_id_checking() {
		boolean validatedCommand = createValidator.validate("create banking.Checking 12345678 5.0 30");
		assertFalse(validatedCommand);
	}

	/////////////////////////
	//// SAVINGS TESTS ////
	/////////////////////

	// Valid Test Cases
	@Test
	public void empty_bank_savings() {
		boolean validatedCommand = createValidator.validate("create banking.Savings 87654321 5.0");
		assertTrue(validatedCommand);
	}

	@Test
	public void multiple_accounts_savings() {
		boolean validatedCommand = createValidator.validate("create banking.Savings 87654321 5.0");
		assertTrue(validatedCommand);
	}

	// Invalid Test Cases
	@Test
	public void string_based_id_savings() {
		boolean validatedCommand = createValidator.validate("create banking.Savings ABCDEFGH 5.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_account_id_and_apr_savings() {
		boolean validatedCommand = createValidator.validate("create banking.Savings");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_account_type_savings() {
		boolean validatedCommand = createValidator.validate("create 12345678 5.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_apr_argument_savings() {
		boolean validatedCommand = createValidator.validate("create banking.Savings 12345678");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_id_argument_savings() {
		boolean validatedCommand = createValidator.validate("create banking.Savings 5.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void incorrect_id_length_savings() {
		boolean validatedCommand = createValidator.validate("create banking.Savings 1234567890 5.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void negative_account_id_savings() {
		boolean validatedCommand = createValidator.validate("create banking.Savings -1234567 5.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void apr_less_than_range_savings() {
		boolean validatedCommand = createValidator.validate("create banking.Savings 12345678 -1.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void apr_more_than_range_savings() {
		boolean validatedCommand = createValidator.validate("create banking.Savings 12345678 11.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void extra_arguments_savings() {
		boolean validatedCommand = createValidator.validate("create banking.Savings 12345678 5.0 30");
		assertFalse(validatedCommand);
	}

	@Test
	public void duplicate_account_id_savings() {
		boolean validatedCommand = createValidator.validate("create banking.Savings 12345678 5.0 30");
		assertFalse(validatedCommand);
	}

	///////////////////
	//// banking.CD TESTS ////
	/////////////////

	// Valid Test Cases
	@Test
	public void empty_bank_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 87654321 5.0 1500");
		assertTrue(validatedCommand);
	}

	@Test
	public void multiple_accounts_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 87654321 5.0 1000");
		assertTrue(validatedCommand);
	}

	@Test
	public void starting_balance_at_lower_boundary_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 87654321 5.0 1000");
		assertTrue(validatedCommand);
	}

	@Test
	public void starting_balance_at_upper_boundary_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 87654321 5.0 10000");
		assertTrue(validatedCommand);
	}

	// Invalid Test Cases
	@Test
	public void string_based_id_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD ABCDEFGH 5.0 1500");
		assertFalse(validatedCommand);
	}

	@Test
	public void starting_balance_lower_than_range_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 99999999 5.0 999");
		assertFalse(validatedCommand);
	}

	@Test
	public void starting_balance_higher_than_range_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 99999999 5.0 10001");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_account_id_and_apr_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_account_type_cd() {
		boolean validatedCommand = createValidator.validate("create 99999999 5.0 0");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_balance_argument_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 99999999 0.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_apr_argument_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 99999999 1500");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_id_argument_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 5.0 1500");
		assertFalse(validatedCommand);
	}

	@Test
	public void incorrect_id_length_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 1234567890 5.0 1500");
		assertFalse(validatedCommand);
	}

	@Test
	public void negative_account_id_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD -1234567 5.0 1500");
		assertFalse(validatedCommand);
	}

	@Test
	public void apr_less_than_range_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 99999999 -1.0 1500");
		assertFalse(validatedCommand);
	}

	@Test
	public void apr_more_than_range_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 99999999 11.0 1500");
		assertFalse(validatedCommand);
	}

	@Test
	public void negative_balance_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 99999999 5.0 -10.0");
		assertFalse(validatedCommand);
	}

	@Test
	public void extra_arguments_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 99999999 5.0 1500 30");
		assertFalse(validatedCommand);
	}

	@Test
	public void alphanumeric_balance_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 99999999 5.0 1500A");
		assertFalse(validatedCommand);
	}

	@Test
	public void string_based_balance_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 99999999 5.0 ABCDEFGH");
		assertFalse(validatedCommand);
	}

	@Test
	public void duplicate_account_id_cd() {
		boolean validatedCommand = createValidator.validate("create banking.CD 12345678 5.0 1500");
		assertFalse(validatedCommand);
	}

	////////////////////////
	//// GENERAL TESTS ////
	//////////////////////

	// Invalid Test Cases
	@Test
	public void create_missing_from_command() {
		boolean validatedCommand = createValidator.validate("banking.CD 12345678 5.0 ABCDEFGH");
		assertFalse(validatedCommand);
	}

	@Test
	public void missing_arguments_general() {
		boolean validatedCommand = createValidator.validate("create");
		assertFalse(validatedCommand);
	}

	@Test
	public void undefined_account_type() {
		boolean validatedCommand = createValidator.validate("create banking.unknown 87654321 5.0");
		assertFalse(validatedCommand);
	}
}