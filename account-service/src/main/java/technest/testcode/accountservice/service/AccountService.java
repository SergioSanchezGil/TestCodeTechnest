package technest.testcode.accountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

}
