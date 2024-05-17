package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
	public static final String CHECKING_ACCOUNT_ID = "12345678";
	public static final String SAVINGS_ACCOUNT_ID = "10000001";
	public static final String CD_ACCOUNT_ID = "89456185";

	public static final double ACCOUNT_APR = 3.0;
	public static int startingBalance = 50;

	Checking checking;
	Savings savings;
	CD cd;

	@BeforeEach
	public void setUp() {
		checking = new Checking(CHECKING_ACCOUNT_ID, ACCOUNT_APR, 0);
		savings = new Savings(SAVINGS_ACCOUNT_ID, ACCOUNT_APR, 0);
		cd = new CD(CD_ACCOUNT_ID, ACCOUNT_APR, startingBalance);
	}

	@Test
	public void account_created_with_0_balance() {
		double balance = checking.getBalance();

		assertEquals(0, balance);
	}

	@Test
	public void set_specific_apr_value() {
		double apr = checking.getAPR();

		assertEquals(ACCOUNT_APR, apr);
	}

	@Test
	public void deposit_once_to_balance() {
		final double DEPOSIT_AMOUNT = 5.3;

		double originalBalance = checking.getBalance();
		checking.deposit(DEPOSIT_AMOUNT);
		double balance = checking.getBalance();

		assertEquals(originalBalance + DEPOSIT_AMOUNT, balance);
	}

	@Test
	public void deposit_twice_to_balance() {
		final double DEPOSIT_AMOUNT = 5.3;
		final double DEPOSIT_AMOUNT_TWO = 7.7;

		double originalBalance = checking.getBalance();
		checking.deposit(DEPOSIT_AMOUNT);
		checking.deposit(DEPOSIT_AMOUNT_TWO);
		double balance = checking.getBalance();

		assertEquals(originalBalance + DEPOSIT_AMOUNT + DEPOSIT_AMOUNT_TWO, balance);
	}

	@Test
	public void withdraw_once_from_balance() {
		final double DEPOSIT_AMOUNT = 5.3;
		final double WITHDRAWAL_AMOUNT = 4.3;

		checking.deposit(DEPOSIT_AMOUNT);
		double originalBalance = checking.getBalance();
		checking.withdraw(WITHDRAWAL_AMOUNT);
		double balance = checking.getBalance();

		assertEquals(originalBalance - WITHDRAWAL_AMOUNT, balance);
	}

	@Test
	public void withdraw_twice_from_balance() {
		final double DEPOSIT_AMOUNT = 5.3;
		final double WITHDRAWAL_AMOUNT = 1.3;
		final double WITHDRAWAL_AMOUNT_TWO = 3.0;

		checking.deposit(DEPOSIT_AMOUNT);
		double originalBalance = checking.getBalance();
		checking.withdraw(WITHDRAWAL_AMOUNT);
		checking.withdraw(WITHDRAWAL_AMOUNT_TWO);
		double balance = checking.getBalance();

		assertEquals(originalBalance - WITHDRAWAL_AMOUNT - WITHDRAWAL_AMOUNT_TWO, balance);
	}

	@Test
	public void withdrawal_above_balance_puts_balance_to_zero_checking() {
		final double DEPOSIT_AMOUNT = 5.3;
		final double WITHDRAWAL_AMOUNT = 1000;

		checking.deposit(DEPOSIT_AMOUNT);
		checking.withdraw(WITHDRAWAL_AMOUNT);
		double balance = checking.getBalance();

		assertEquals(0, balance);
	}

	@Test
	public void withdraw_amount_more_than_starting_balance_sets_balance_to_zero_cd() {
		final double WITHDRAWAL_AMOUNT = 51;
		cd.withdraw(WITHDRAWAL_AMOUNT);

		assertEquals(0, cd.getBalance());
	}

	@Test
	public void withdraw_amount_equal_to_balance_sets_balance_to_zero_checking() {
		final double DEPOSIT_AMOUNT = 50;
		final double WITHDRAWAL_AMOUNT = 50;

		checking.deposit(DEPOSIT_AMOUNT);
		checking.withdraw(WITHDRAWAL_AMOUNT);

		assertEquals(0, checking.getBalance());
	}

	@Test
	public void withdraw_nothing_from_empty_account_checking() {
		final double WITHDRAWAL_AMOUNT = 0;
		checking.withdraw(WITHDRAWAL_AMOUNT);

		assertEquals(0, checking.getBalance());
	}

	@Test
	public void withdraw_nothing_checking() {
		final double DEPOSIT_AMOUNT = 50;
		final double WITHDRAWAL_AMOUNT = 0;

		checking.deposit(DEPOSIT_AMOUNT);
		checking.withdraw(WITHDRAWAL_AMOUNT);

		assertEquals(50, checking.getBalance());
	}

	@Test
	public void withdraw_amount_just_under_balance_leaves_small_balance() {
		final double DEPOSIT_AMOUNT = 50;
		final double WITHDRAWAL_AMOUNT = 49.99;

		checking.deposit(DEPOSIT_AMOUNT);
		checking.withdraw(WITHDRAWAL_AMOUNT);

		assertEquals((DEPOSIT_AMOUNT - WITHDRAWAL_AMOUNT), checking.getBalance());
	}

	@Test
	public void withdraw_amount_just_over_sets_balance_to_zero_checking() {
		final double DEPOSIT_AMOUNT = 50;
		final double WITHDRAWAL_AMOUNT = 50.01;

		checking.deposit(DEPOSIT_AMOUNT);
		checking.withdraw(WITHDRAWAL_AMOUNT);

		assertEquals(0, checking.getBalance());
	}
}