package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsTest {
	public static final String SAVINGS_ACCOUNT_ID = "10000001";
	public static final double ACCOUNT_APR = 3.0;

	Savings savings;

	@BeforeEach
	public void setUp() {
		savings = new Savings(SAVINGS_ACCOUNT_ID, ACCOUNT_APR, 0);
	}

	@Test
	public void account_created_with_0_balance() {
		double balance = savings.getBalance();

		assertEquals(0, balance);
	}
}