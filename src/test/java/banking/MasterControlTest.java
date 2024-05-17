package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	MasterControl masterControl;
	ArrayList<String> input;

	private void assertSingleCommand(String command, ArrayList<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank),
				new CommandStorage(bank));
	}

	@Test
	public void invalid_create_command() {
		input.add("creat checking 12345678 1.0");
		ArrayList<String> actual = masterControl.start(input);
		assertSingleCommand("creat checking 12345678 1.0", actual);
	}

	@Test
	public void invalid_deposit_command() {
		input.add("depositt 12345678 100");
		ArrayList<String> actual = masterControl.start(input);
		assertSingleCommand("depositt 12345678 100", actual);
	}

	@Test
	public void invalid_create_commands() {
		input.add("creat checking 12345678 1.0");
		input.add("depositt 12345678 100");

		ArrayList<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depositt 12345678 100", actual.get(1));
	}

	@Test
	public void unrecognized_command() {
		input.add("add CD 87654321 5.0 1500");

		ArrayList<String> actual = masterControl.start(input);
		assertSingleCommand("add cd 87654321 5.0 1500", actual);
	}

	@Test
	public void unrecognized_parameters() {
		input.add("create invalid data");

		ArrayList<String> actual = masterControl.start(input);
		assertSingleCommand("create invalid data", actual);
	}

	@Test
	public void withdraw_cd() {
		input.add("create CD 12345678 2.1 1500");
		input.add("withdraw 12345678 100");

		ArrayList<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Cd 12345678 1500.00 2.10", actual.get(0));
		assertEquals("withdraw 12345678 100", actual.get(1));
	}

	@Test
	public void withdraw_partial_balance_cd_after_6_months_passed() {
		input.add("create CD 12345678 2.1 1500");
		input.add("withdraw 12345678 100");
		input.add("pass 6");
		input.add("withdraw 12345678 200");

		ArrayList<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Cd 12345678 1564.28 2.10", actual.get(0));
		assertEquals("withdraw 12345678 100", actual.get(1));
		assertEquals("withdraw 12345678 200", actual.get(2));
	}

	@Test
	public void withdraw_full_balance_cd_after_6_months_passed() {
		input.add("create CD 12345678 2.1 1500");
		input.add("withdraw 12345678 100");
		input.add("pass 6");
		input.add("withdraw 12345678 1600");

		ArrayList<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Cd 12345678 1564.28 2.10", actual.get(0));
		assertEquals("withdraw 12345678 100", actual.get(1));
		assertEquals("withdraw 12345678 1600", actual.get(2));
	}

	@Test
	public void withdraw_partial_balance_cd_after_12_months_passed() {
		input.add("create CD 12345678 2.1 1500");
		input.add("withdraw 12345678 100");
		input.add("pass 12");
		input.add("withdraw 12345678 1200");

		ArrayList<String> actual = masterControl.start(input);
		System.out.println(actual);
		assertEquals(3, actual.size());
		assertEquals("Cd 12345678 1631.32 2.10", actual.get(0));
		assertEquals("withdraw 12345678 100", actual.get(1));
		assertEquals("withdraw 12345678 1200", actual.get(2));
	}

	@Test
	public void withdraw_exact_balance_cd_after_12_months_passed() {
		input.add("create CD 12345678 2.1 1500");
		input.add("withdraw 12345678 100");
		input.add("pass 12");
		input.add("withdraw 12345678 1631.32");

		ArrayList<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Cd 12345678 0.00 2.10", actual.get(0));
		assertEquals("Withdraw 12345678 1631.32", actual.get(1));
		assertEquals("withdraw 12345678 100", actual.get(2));
	}

	@Test
	public void withdraw_more_than_balance_cd_after_12_months_passed() {
		input.add("create CD 12345678 2.1 1500");
		input.add("withdraw 12345678 100");
		input.add("pass 12");
		input.add("withdraw 12345678 1700");

		ArrayList<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Cd 12345678 0.00 2.10", actual.get(0));
		assertEquals("Withdraw 12345678 1700", actual.get(1));
		assertEquals("withdraw 12345678 100", actual.get(2));
	}

	@Test
	public void withdraw_reset_test_cd() {
		input.add("create CD 12345678 2.1 1500");
		input.add("withdraw 12345678 100"); // fail
		input.add("pass 12");
		input.add("withdraw 12345678 1700");
		input.add("pass 12");
		input.add("withdraw 12345678 1800"); // fail

		ArrayList<String> actual = masterControl.start(input);
		System.out.println(actual);
		assertEquals(2, actual.size());
		assertEquals("withdraw 12345678 100", actual.get(0));
		assertEquals("withdraw 12345678 1800", actual.get(1));
	}

	@Test
	public void withdraw_savings() {
		input.add("create savings 12345678 2.1");
		input.add("deposit 12345678 400");
		input.add("withdraw 12345678 200");

		ArrayList<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Savings 12345678 200.00 2.10", actual.get(0));
		assertEquals("Deposit 12345678 400", actual.get(1));
		assertEquals("Withdraw 12345678 200", actual.get(2));
	}

	@Test
	public void withdraw_savings_over_limit() {
		input.add("create savings 12345678 2.1");
		input.add("deposit 12345678 400");
		input.add("withdraw 12345678 500");

		ArrayList<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Savings 12345678 0.00 2.10", actual.get(0));
		assertEquals("Deposit 12345678 400", actual.get(1));
		assertEquals("Withdraw 12345678 500", actual.get(2));
	}

	@Test
	public void withdraw_twice_in_one_month_fail_savings() {
		input.add("create savings 12345678 2.1");
		input.add("deposit 12345678 1000");
		input.add("withdraw 12345678 250");
		input.add("withdraw 12345678 200");
		input.add("pass 1");
		input.add("withdraw 12345678 150");
		input.add("withdraw 12345678 100");
		input.add("pass 2");
		input.add("withdraw 12345678 50");

		ArrayList<String> actual = masterControl.start(input);
		assertEquals(7, actual.size());
		assertEquals("Savings 12345678 553.41 2.10", actual.get(0));
		assertEquals("Deposit 12345678 1000", actual.get(1));
		assertEquals("Withdraw 12345678 250", actual.get(2));
		assertEquals("Withdraw 12345678 150", actual.get(3));
		assertEquals("Withdraw 12345678 50", actual.get(4));
		assertEquals("withdraw 12345678 200", actual.get(5));
		assertEquals("withdraw 12345678 100", actual.get(6));
	}

	@Test
	void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000"); //
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

	@Test
	void test_creating_and_depositing_into_checking_account() {
		input.add("creAte cHecKing 12345678 0.01");
		input.add("Deposit 12345678 500");
		input.add("Pass 1");
		input.add("Create savings 98765432 0.6");
		input.add("Deposit 98765432 300");
		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 500.00 0.01", actual.get(0));
		assertEquals("Deposit 12345678 500", actual.get(1));
		assertEquals("Savings 98765432 300.00 0.60", actual.get(2));
		assertEquals("Deposit 98765432 300", actual.get(3));
	}

	@Test
	public void transfer_regular_test() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000"); //
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 100");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");

		List<String> actual = masterControl.start(input);

		assertEquals(8, actual.size());
		assertEquals("Savings 12345678 800.40 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 100", actual.get(2));
		assertEquals("Checking 98765432 200.00 0.01", actual.get(3));
		assertEquals("Deposit 98765432 300", actual.get(4));
		assertEquals("Transfer 98765432 12345678 100", actual.get(5));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(6));
		assertEquals("Deposit 12345678 5000", actual.get(7));
	}

	@Test
	void test_with_closing_savings_accounts_then_depositing_again() {
		input.add("Create savings 98765432 0.6");
		input.add("Deposit 98765432 300");
		input.add("Withdraw 98765432 400");
		input.add("creAte cd 23456789 1.2 2000");
		input.add("Deposit 98765432 1000");
		input.add("Pass 3");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 98765432 1001.50 0.60", actual.get(0));
		assertEquals("Deposit 98765432 300", actual.get(1));
		assertEquals("Withdraw 98765432 400", actual.get(2));
		assertEquals("Deposit 98765432 1000", actual.get(3));
		assertEquals("Cd 23456789 2024.13 1.20", actual.get(4));
	}

	@Test
	void test_transfer_between_checking_and_savings_then_withdrawing() {
		input.add("create checking 87654321 3.1");
		input.add("create savings 12345678 0.6");
		input.add("deposit 87654321 1000");
		input.add("transfer 87654321 12345678 400");
		input.add("pass 1");
		input.add("withdraw 12345678 200");
		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Checking 87654321 601.55 3.10", actual.get(0));
		assertEquals("Deposit 87654321 1000", actual.get(1));
		assertEquals("Transfer 87654321 12345678 400", actual.get(2));
		assertEquals("Savings 12345678 200.20 0.60", actual.get(3));
		assertEquals("Transfer 87654321 12345678 400", actual.get(4));
		assertEquals("Withdraw 12345678 200", actual.get(5));

	}

	@Test
	public void test_cd_account_less_withdrawal_after_12_months() {
		input.add("create CD 13579246 2.1 1500");
		input.add("withdraw 13579246 300");
		input.add("pass 12");
		input.add("withdraw 13579246 1500");
		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Cd 13579246 1631.32 2.10", actual.get(0));
		assertEquals("withdraw 13579246 300", actual.get(1));
		assertEquals("withdraw 13579246 1500", actual.get(2));
	}

	@Test
	public void test_cd_account_exact_withdraw_after_12_months() {
		input.add("create CD 13579246 2.1 1500");
		input.add("withdraw 13579246 100");
		input.add("pass 12");
		input.add("withdraw 13579246 1631.32");
		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Cd 13579246 0.00 2.10", actual.get(0));
		assertEquals("Withdraw 13579246 1631.32", actual.get(1));
		assertEquals("withdraw 13579246 100", actual.get(2));
	}

	@Test
	public void test_cd_account_withdraw_more_than_balance_after_12_months() {
		input.add("create CD 12345678 2.1 1500");
		input.add("withdraw 12345678 100");
		input.add("pass 12");
		input.add("withdraw 12345678 1700");
		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Cd 12345678 0.00 2.10", actual.get(0));
		assertEquals("Withdraw 12345678 1700", actual.get(1));
		assertEquals("withdraw 12345678 100", actual.get(2));
	}

	@Test
	public void test_savings_withdraw_twice_in_a_month() {
		input.add("create savings 12345678 2.1");
		input.add("deposit 12345678 1000");
		input.add("withdraw 12345678 250");
		input.add("withdraw 12345678 200");
		input.add("pass 1");
		input.add("withdraw 12345678 150");
		input.add("withdraw 12345678 100");
		input.add("pass 2");
		input.add("withdraw 12345678 50");
		List<String> actual = masterControl.start(input);

		assertEquals(7, actual.size());
		assertEquals("Savings 12345678 553.41 2.10", actual.get(0));
		assertEquals("Deposit 12345678 1000", actual.get(1));
		assertEquals("Withdraw 12345678 250", actual.get(2));
		assertEquals("Withdraw 12345678 150", actual.get(3));
		assertEquals("Withdraw 12345678 50", actual.get(4));
		assertEquals("withdraw 12345678 200", actual.get(5));
		assertEquals("withdraw 12345678 100", actual.get(6));
	}
}