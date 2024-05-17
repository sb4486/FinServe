package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingTest {
	public static final String CHECKING_ACCOUNT_ID = "12345678";
	public static final double ACCOUNT_APR = 3.0;

	Checking checking;

	@BeforeEach
	public void setUp() {
		checking = new Checking(CHECKING_ACCOUNT_ID, ACCOUNT_APR, 0);
	}

	@Test
	public void account_created_with_0_balance() {
		double balance = checking.getBalance();

		assertEquals(0, balance);
	}
}