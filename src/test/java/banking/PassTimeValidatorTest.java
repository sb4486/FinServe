package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeValidatorTest {
	PassTimeValidator passTimeValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		passTimeValidator = new PassTimeValidator(bank);
	}

	// Valid Tests
	@Test
	public void pass_lower_boundary() {
		boolean actual = passTimeValidator.validate("Pass 1");
		assertTrue(actual);
	}

	@Test
	public void pass_upper_boundary() {
		boolean actual = passTimeValidator.validate("Pass 60");
		assertTrue(actual);
	}

	// Invalid Tests
	@Test
	public void pass_time_over_limit() {
		boolean actual = passTimeValidator.validate("Pass 61");
		assertFalse(actual);
	}

	@Test
	public void pass_time_under_limit() {
		boolean actual = passTimeValidator.validate("Pass 0");
		assertFalse(actual);
	}

	@Test
	public void non_numeric_time_amount() {
		boolean actual = passTimeValidator.validate("Pass ONE");
		assertFalse(actual);
	}

	@Test
	public void extra_command_parameters() {
		boolean actual = passTimeValidator.validate("Pass 1 2");
		assertFalse(actual);
	}
}