package banking;

import java.util.HashMap;

public class Bank {
	private HashMap<String, Account> accounts;

	Bank() {
		accounts = new HashMap<>();
	}

	public HashMap<String, Account> getAccounts() {
		return accounts;
	}

	public void addAccount(String ACCOUNT_TYPE, String ACCOUNT_ID, double ACCOUNT_APR) {
		if (ACCOUNT_TYPE.equals("banking.Checking")) {
			accounts.put(ACCOUNT_ID, new Checking(ACCOUNT_ID, ACCOUNT_APR, 0));
		} else if (ACCOUNT_TYPE.equals("banking.Savings")) {
			accounts.put(ACCOUNT_ID, new Savings(ACCOUNT_ID, ACCOUNT_APR, 0));
		}
	}

	public void addAccount(String ACCOUNT_TYPE, String ACCOUNT_ID, double ACCOUNT_APR, double Balance) {
		if (ACCOUNT_TYPE.equals("banking.CD")) {
			accounts.put(ACCOUNT_ID, new CD(ACCOUNT_ID, ACCOUNT_APR, Balance));
		}
	}

	public Account getAccount(String ACCOUNT_ID) {
		return accounts.get(ACCOUNT_ID);
	}
}