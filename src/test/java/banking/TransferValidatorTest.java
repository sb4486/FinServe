package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferValidatorTest {
	TransferValidator transferValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		transferValidator = new TransferValidator(bank);

		bank.addAccount("banking.Savings", "12345678", 0.5);
		bank.addAccount("banking.Checking", "23456789", 0);
		bank.addAccount("banking.CD", "34567890", 1.5, 1500);
		bank.addAccount("banking.Savings", "45678901", 0.5);
		bank.addAccount("banking.Checking", "56789012", 0);

		bank.getAccount("12345678").deposit(950);
		bank.getAccount("23456789").deposit(350);
		bank.getAccount("45678901").deposit(750);
		bank.getAccount("56789012").deposit(150);
	}

	// Valid Test Cases
	@Test
	public void savings_to_checking() {
		boolean actual = transferValidator.validate("Transfer 12345678 23456789 200");
		assertTrue(actual);
	}

	@Test
	public void checking_to_savings() {
		boolean actual = transferValidator.validate("Transfer 23456789 12345678 200");
		assertTrue(actual);
	}

	@Test
	public void savings_to_savings() {
		boolean actual = transferValidator.validate("Transfer 12345678 45678901 200");
		assertTrue(actual);
	}

	@Test
	public void checking_to_checking() {
		boolean actual = transferValidator.validate("Transfer 23456789 56789012 200");
		assertTrue(actual);
	}

	@Test
	public void withdraw_amount_more_than_balance() {
		boolean actual = transferValidator.validate("Transfer 12345678 23456789 600");
		assertTrue(actual);
	}

	@Test
	public void transfer_zero_amount() {
		boolean actual = transferValidator.validate("Transfer 12345678 23456789 0");
		assertTrue(actual);
	}

	@Test
	public void transfer_withdrawal_limit() {
		boolean actual = transferValidator.validate("Transfer 12345678 23456789 400");
		assertTrue(actual);
	}

	@Test
	public void transfer_at_boundary_savings_to_checking() {
		boolean actual = transferValidator.validate("Transfer 12345678 23456789 1000");
		assertTrue(actual);
	}

	// Invalid Test Cases
	@Test
	public void non_numeric_sending_account_id() {
		boolean actual = transferValidator.validate("Transfer ABCDEFGH 23456789 200");
		assertFalse(actual);
	}

	@Test
	public void savings_to_cd() {
		boolean actual = transferValidator.validate("Transfer 12345678 34567890 200");
		assertFalse(actual);
	}

	@Test
	public void cd_to_savings() {
		boolean actual = transferValidator.validate("Transfer 34567890 12345678 200");
		assertFalse(actual);
	}

	@Test
	public void non_numeric_receiving_account_id() {
		boolean actual = transferValidator.validate("Transfer 12345678 ABCDEFGH 200");
		assertFalse(actual);
	}

	@Test
	public void non_numeric_transfer_amount() {
		boolean actual = transferValidator.validate("Transfer 12345678 23456789 ABC");
		assertFalse(actual);
	}

	@Test
	public void negative_withdrawal_amount() {
		boolean actual = transferValidator.validate("Transfer 12345678 23456789 -100");
		assertFalse(actual);
	}

	@Test
	public void sending_account_does_not_exist() {
		boolean actual = transferValidator.validate("Transfer 12345678 99999999 200");
		assertFalse(actual);
	}

	@Test
	public void receiving_account_does_not_exist() {
		boolean actual = transferValidator.validate("Transfer 99999999 12345678 200");
		assertFalse(actual);
	}

	@Test
	public void same_account_transferred() {
		boolean actual = transferValidator.validate("Transfer 12345678 12345678 200");
		assertFalse(actual);
	}

	@Test
	public void extra_arguments() {
		boolean actual = transferValidator.validate("Transfer 12345678 23456789 200 5");
		assertFalse(actual);
	}

	@Test
	public void transfer_under_limit_savings_to_checking() {
		boolean actual = transferValidator.validate("Transfer 12345678 23456789 -1");
		assertFalse(actual);
	}

	@Test
	public void transfer_under_limit_checking_to_savings() {
		boolean actual = transferValidator.validate("Transfer 23456789 12345678 -1");
		assertFalse(actual);
	}

	@Test
	public void transfer_over_limit_savings_to_checking() {
		boolean actual = transferValidator.validate("Transfer 12345678 23456789 1050");
		assertFalse(actual);
	}

	@Test
	public void transfer_over_limit_checking_to_savings() {
		boolean actual = transferValidator.validate("Transfer 23456789 12345678 450");
		assertFalse(actual);
	}

	@Test
	public void transfer_over_boundary_checking_to_savings() {
		boolean actual = transferValidator.validate("Transfer 23456789 12345678 1000");
		assertFalse(actual);
	}
}