package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final String ACCOUNT_ID = "12345678";
	public static final double ACCOUNT_APR = 3.0;
	public static int startingBalance = 50;

	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
	}

	@Test
	public void bank_has_no_accounts_upon_creation() {
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	public void add_one_account_to_bank() {
		bank.addAccount("banking.Checking", ACCOUNT_ID, ACCOUNT_APR);
		assertEquals(ACCOUNT_ID, bank.getAccounts().get(ACCOUNT_ID).getID());
	}

	@Test
	public void add_two_accounts_to_bank() {
		bank.addAccount("banking.CD", ACCOUNT_ID, ACCOUNT_APR, startingBalance);
		bank.addAccount("banking.Savings", ACCOUNT_ID + 1, ACCOUNT_APR);

		assertEquals(ACCOUNT_ID + 1, bank.getAccounts().get(ACCOUNT_ID + 1).getID());
	}

	@Test
	public void retrieve_account() {
		bank.addAccount("banking.CD", ACCOUNT_ID, ACCOUNT_APR, startingBalance);
		Account account = bank.getAccount(ACCOUNT_ID);

		assertEquals(ACCOUNT_ID, account.getID());
	}

	@Test
	public void deposit_money_once_by_id() {
		bank.addAccount("banking.Checking", ACCOUNT_ID, ACCOUNT_APR);
		Account account = bank.getAccount(ACCOUNT_ID);

		account.deposit(50);
		assertEquals(bank.getAccount(ACCOUNT_ID).getBalance(), account.getBalance());
	}

	@Test
	public void deposit_money_twice_by_id() {
		bank.addAccount("banking.CD", ACCOUNT_ID, ACCOUNT_APR, startingBalance);
		bank.addAccount("banking.Savings", ACCOUNT_ID + 1, ACCOUNT_APR);
		Account account = bank.getAccount(ACCOUNT_ID + 1);
		account.deposit(50);
		account.deposit(20);

		assertEquals(bank.getAccount(ACCOUNT_ID + 1).getBalance(), account.getBalance());
	}

	@Test
	public void withdraw_money_once_by_id() {
		bank.addAccount("banking.CD", ACCOUNT_ID, ACCOUNT_APR, startingBalance);
		bank.addAccount("banking.Savings", ACCOUNT_ID + 1, ACCOUNT_APR);
		bank.addAccount("banking.Checking", ACCOUNT_ID + 2, ACCOUNT_APR);
		Account account = bank.getAccount(ACCOUNT_ID + 2);
		account.withdraw(20);

		assertEquals(bank.getAccount(ACCOUNT_ID + 2).getBalance(), account.getBalance());
	}

	@Test
	public void withdraw_money_twice_by_id() {
		bank.addAccount("banking.CD", ACCOUNT_ID, ACCOUNT_APR, startingBalance);
		bank.addAccount("banking.Checking", ACCOUNT_ID + 1, ACCOUNT_APR);
		Account account = bank.getAccount(ACCOUNT_ID);
		account.withdraw(20);
		account.withdraw(10);

		assertEquals(bank.getAccount(ACCOUNT_ID).getBalance(), account.getBalance());
	}
}