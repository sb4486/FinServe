package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDTest {
	public static final String CD_ACCOUNT_ID = "89456185";
	public static final double ACCOUNT_APR = 3.0;
	public double Balance = 500;

	CD cd;

	@BeforeEach
	public void setUp() {
		cd = new CD(CD_ACCOUNT_ID, ACCOUNT_APR, Balance);
	}

	@Test
	public void account_created_with_specified_balance() {
		double balance = cd.getBalance();

		assertEquals(500, balance);
	}
}