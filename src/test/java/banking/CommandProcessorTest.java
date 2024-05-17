package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	final String checking_test_command = "create checking 12345678 1.0";
	final String savings_test_command = "create savings 12345678 1.0";
	final String cd_test_command = "create cd 12345678 1.0 1500";
	CommandProcessor commandProcessor;
	Bank bank;

	///////////////////////
	//// CREATE TESTS ////
	/////////////////////

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	// Checking Accounts
	@Test
	public void bank_created_has_correct_ID_checking() {
		commandProcessor.execute(checking_test_command);
		assertEquals("12345678", bank.getAccount("12345678").getID());
	}

	@Test
	public void bank_created_has_correct_APR_checking() {
		commandProcessor.execute(checking_test_command);
		assertEquals(1.0, bank.getAccount("12345678").getAPR());
	}

	@Test
	public void bank_created_has_correct_starting_balance_checking() {
		commandProcessor.execute(checking_test_command);
		assertEquals(0.0, bank.getAccount("12345678").getBalance());
	}

	// Savings Accounts
	@Test
	public void bank_created_has_correct_ID_savings() {
		commandProcessor.execute(savings_test_command);
		assertEquals("12345678", bank.getAccount("12345678").getID());
	}

	@Test
	public void bank_created_has_correct_APR_savings() {
		commandProcessor.execute(savings_test_command);
		assertEquals(1.0, bank.getAccount("12345678").getAPR());
	}

	@Test
	public void bank_created_has_correct_starting_balance_savings() {
		commandProcessor.execute(savings_test_command);
		assertEquals(0.0, bank.getAccount("12345678").getBalance());
	}

	// CD Accounts
	@Test
	public void bank_created_has_correct_ID_cd() {
		commandProcessor.execute(cd_test_command);
		assertEquals("12345678", bank.getAccount("12345678").getID());
	}

	@Test
	public void bank_created_has_correct_APR_cd() {
		commandProcessor.execute(cd_test_command);
		assertEquals(1.0, bank.getAccount("12345678").getAPR());
	}

	@Test
	public void bank_created_has_correct_starting_balance_cd() {
		commandProcessor.execute(cd_test_command);
		assertEquals(1500, bank.getAccount("12345678").getBalance());
	}

	////////////////////////
	//// DEPOSIT TESTS ////
	//////////////////////

	// Checking Accounts
	@Test
	public void balance_deposit_from_empty_account_checking() {
		commandProcessor.execute(checking_test_command);
		commandProcessor.execute("deposit 12345678 100");
		assertEquals(100, bank.getAccount("12345678").getBalance());
	}

	@Test
	public void balance_deposit_checking() {
		commandProcessor.execute(checking_test_command);
		commandProcessor.execute("deposit 12345678 150");
		commandProcessor.execute("deposit 12345678 100");
		assertEquals(250, bank.getAccount("12345678").getBalance());
	}

	// Savings Accounts
	@Test
	public void balance_deposit_from_empty_account_savings() {
		commandProcessor.execute(savings_test_command);
		commandProcessor.execute("deposit 12345678 100");
		assertEquals(100, bank.getAccount("12345678").getBalance());
	}

	@Test
	public void balance_deposit_savings() {
		commandProcessor.execute(savings_test_command);
		commandProcessor.execute("deposit 12345678 150");
		commandProcessor.execute("deposit 12345678 100");
		assertEquals(250, bank.getAccount("12345678").getBalance());
	}

	// No Test Cases for CD Accounts, Since Deposit Commands Are Invalid For
	// Them

	/////////////////////////
	//// WITHDRAW TESTS ////
	///////////////////////

	@Test
	public void balance_withdraw_from_empty_account_checking() {
		commandProcessor.execute(checking_test_command);
		commandProcessor.execute("withdraw 12345678 100");
		assertEquals(0, bank.getAccount("12345678").getBalance());
	}

	@Test
	public void balance_withdraw_checking() {
		commandProcessor.execute(checking_test_command);
		commandProcessor.execute("deposit 12345678 150");
		commandProcessor.execute("withdraw 12345678 100");
		assertEquals(50, bank.getAccount("12345678").getBalance());
	}

	@Test
	public void balance_withdraw_savings() {
		commandProcessor.execute(savings_test_command);
		commandProcessor.execute("deposit 12345678 350");
		commandProcessor.execute("withdraw 12345678 150");
		assertEquals(200, bank.getAccount("12345678").getBalance());
	}

	/////////////////////////
	//// WITHDRAW TESTS ////
	///////////////////////

	@Test
	public void balance_pass_time_cd() {
		commandProcessor.execute(cd_test_command);
		commandProcessor.execute("pass 1");
		assertEquals(1505.0062534729457, bank.getAccount("12345678").getBalance());
	}

	@Test
	public void monthly_reduce_balance_to_zero_fees() {
		commandProcessor.execute(savings_test_command);
		commandProcessor.execute("deposit 12345678 50");
		commandProcessor.execute("pass 3");
		assertEquals(0, bank.getAccount("12345678").getBalance());
	}

	@Test
	public void account_removed_with_zero_balance() {
		commandProcessor.execute(savings_test_command);
		commandProcessor.execute("pass 1");
		assertNull(bank.getAccount("12345678"));
	}

	@Test
	public void account_removed_with_zero_balance_eventually() {
		commandProcessor.execute(savings_test_command);
		commandProcessor.execute("deposit 12345678 50");
		commandProcessor.execute("pass 4");
		assertNull(bank.getAccount("12345678"));
	}

	@Test
	public void account_removed_with_under_boundary_balance_removed() {
		commandProcessor.execute(savings_test_command);
		commandProcessor.execute("deposit 12345678 99");
		commandProcessor.execute("pass 5");
		assertNull(bank.getAccount("12345678"));
	}

	@Test
	public void account_removed_with_boundary_balance_eventually() {
		commandProcessor.execute(savings_test_command);
		commandProcessor.execute("deposit 12345678 100");
		commandProcessor.execute("pass 4");
		assertEquals(100.3337502315297, bank.getAccount("12345678").getBalance());
	}

	/////////////////////////
	//// TRANSFER TESTS ////
	///////////////////////

	@Test
	public void transfer_savings_to_checking() {
		commandProcessor.execute("create savings 12345678 1.0");
		commandProcessor.execute("create checking 87654321 3.0");
		commandProcessor.execute("deposit 12345678 100");
		commandProcessor.execute("transfer 12345678 87654321 50");
		assertEquals(50, bank.getAccount("87654321").getBalance());
	}

	@Test
	public void transfer_savings_to_savings() {
		commandProcessor.execute("create savings 12345678 1.0");
		commandProcessor.execute("create savings 87654321 3.0");
		commandProcessor.execute("deposit 12345678 100");
		commandProcessor.execute("transfer 12345678 87654321 50");
		assertEquals(50, bank.getAccount("87654321").getBalance());
	}

	@Test
	public void transfer_checking_to_savings() {
		commandProcessor.execute("create checking 12345678 1.0");
		commandProcessor.execute("create savings 87654321 3.0");
		commandProcessor.execute("deposit 12345678 100");
		commandProcessor.execute("transfer 12345678 87654321 50");
		assertEquals(50, bank.getAccount("87654321").getBalance());
	}

	@Test
	public void transfer_checking_to_checking() {
		commandProcessor.execute("create checking 12345678 1.0");
		commandProcessor.execute("create checking 87654321 3.0");
		commandProcessor.execute("deposit 12345678 100");
		commandProcessor.execute("transfer 12345678 87654321 50");
		assertEquals(50, bank.getAccount("87654321").getBalance());
	}

	@Test
	public void transfer_edge_boundary() {
		commandProcessor.execute("create checking 12345678 1.0");
		commandProcessor.execute("create checking 87654321 3.0");
		commandProcessor.execute("deposit 12345678 100");
		commandProcessor.execute("transfer 12345678 87654321 100");
		assertEquals(100, bank.getAccount("87654321").getBalance());
		assertEquals(0, bank.getAccount("12345678").getBalance());
	}

	@Test
	public void transfer_over_limit() {
		commandProcessor.execute("create checking 12345678 1.0");
		commandProcessor.execute("create checking 87654321 3.0");
		commandProcessor.execute("deposit 12345678 100");
		commandProcessor.execute("transfer 12345678 87654321 150");
		assertEquals(100, bank.getAccount("87654321").getBalance());
		assertEquals(0, bank.getAccount("12345678").getBalance());
	}
}