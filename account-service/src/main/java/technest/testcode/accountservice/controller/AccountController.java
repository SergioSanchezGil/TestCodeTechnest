package technest.testcode.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import technest.testcode.accountservice.model.Account;
import technest.testcode.accountservice.service.AccountService;

/**
 * Account controller
 * 
 * @author sergio.sanchez.gil
 * 
 */

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {

	@Autowired
	AccountService accountService;

	/**
	 * Save/update an account.
	 * 
	 * @param account information to save/update
	 * @return the response with the status code, header and the account id
	 *         saved/updated (body)
	 */
	@PostMapping("/")
	private ResponseEntity<Integer> saveAccount(@RequestBody Account account) {
		return accountService.saveAccount(account);
	}

}