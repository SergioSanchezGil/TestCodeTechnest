package technest.testcode.accountservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import technest.testcode.accountservice.model.Account;
import technest.testcode.accountservice.repository.AccountRepository;

/**
 * Account service
 * 
 * @author sergio.sanchez.gil
 *
 */

@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepository;

	/**
	 * Save/update an account.
	 * 
	 * @param account information to save/update
	 * @return the response with the status code, header and the account id
	 *         saved/updated (body)
	 */
	public ResponseEntity<Integer> saveAccount(Account account) {
		if (!account.isTreasury() && account.getBalance() < 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header(HttpHeaders.CONTENT_TYPE, "application/json")
					.body(-1);
		} else {
			accountRepository.save(account);
			return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.CONTENT_TYPE, "application/json")
					.body(account.getId());
		}

	}
	
	/**
	 * Get list of all accounts.
	 * @return the response with the status code, header and the list (body)
	 */
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = new ArrayList<Account>();
		accountRepository.findAll().forEach(account -> accounts.add(account));
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE, "application/json").body(accounts);
	}
	
	/**
	 * Get an account by id.
	 * @param id of the account
	 * @return the response with the status code, header and the account (body)
	 */
	public ResponseEntity<Account> getAccountById(int id) {
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE, "application/json")
				.body(accountRepository.findById(id).get());
	}
	
	/**
	 * Transfer money between two accounts.
	 * @param source account id
	 * @param target account id
	 * @param amount to transfer
	 * @return the response with the status code, header and an information message (body)
	 */
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<String> transferMoney(int sourceId, int targetId, double amount) {
		
		// negative or zero amount
		if (amount <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header(HttpHeaders.CONTENT_TYPE, "application/json")
					.body("Error, invalid amount");

		}
		
		Account source = accountRepository.findById(sourceId).get();
		
		// negative balance and no treasury account
		if (source.getBalance() - amount < 0 && !source.isTreasury()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).header(HttpHeaders.CONTENT_TYPE, "application/json")
					.body("Error, only treasury accounts can have a negative balance");
		}

		Account target = accountRepository.findById(targetId).get();

		source.setBalance(source.getBalance() - amount);
		target.setBalance(target.getBalance() + amount);

		saveAccount(source);
		saveAccount(target);

		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE, "application/json")
				.body("Successful transfer");

	}

}
