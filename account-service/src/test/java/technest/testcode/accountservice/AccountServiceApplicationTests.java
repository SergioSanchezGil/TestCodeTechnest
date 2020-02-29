package technest.testcode.accountservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import technest.testcode.accountservice.model.Account;
import technest.testcode.accountservice.repository.AccountRepository;
import technest.testcode.accountservice.service.AccountService;

import org.junit.Assert;

@SpringBootTest
class AccountServiceApplicationTests {

	@InjectMocks
	AccountService accountService;

	@Mock
	AccountRepository accountRepositoryMock;
	
	private List<Account> accountList;
	private static Account account1;
	private static Account account2;
	private static Account account3;
	private static Account account4;
	private static Account account5;
	private static Account account6;
	private static Account account7;
	private static Account account8;
	private static Account account9;
	
	@BeforeAll
	public static void setup() {
        
		account1 = new Account();
		account2 = new Account();
		account3 = new Account();
		account4 = new Account();
		account5 = new Account();
		account6 = new Account();
		account7 = new Account();
		account8 = new Account();
		account9 = new Account();
		
        account1.setId(1);
        account1.setBalance(500);
        account1.setCurrency(Account.Currency.Euro);
        account1.setName("A");
        account1.setTreasury(false);
        
        account2.setId(2);
        account2.setBalance(100);
        account2.setCurrency(Account.Currency.Euro);
        account2.setName("B");
        account2.setTreasury(true);
        
        account3.setId(3);
        account3.setBalance(350);
        account3.setCurrency(Account.Currency.Euro);
        account3.setName("C");
        account3.setTreasury(false);
        
        account4.setId(4);
        account4.setBalance(-100);
        account4.setCurrency(Account.Currency.Euro);
        account4.setName("D");
        account4.setTreasury(false);
        
        account5.setId(5);
        account5.setBalance(-100);
        account5.setCurrency(Account.Currency.Euro);
        account5.setName("E");
        account5.setTreasury(true);
        
        account6.setId(6);
        account6.setBalance(500);
        account6.setCurrency(Account.Currency.Euro);
        account6.setName("F");
        account6.setTreasury(false);
        
        account7.setId(7);
        account7.setBalance(100);
        account7.setCurrency(Account.Currency.Euro);
        account7.setName("G");
        account7.setTreasury(true);
        
        account8.setId(8);
        account8.setBalance(500);
        account8.setCurrency(Account.Currency.Euro);
        account8.setName("H");
        account8.setTreasury(false);
        
        account9.setId(9);
        account9.setBalance(100);
        account9.setCurrency(Account.Currency.Euro);
        account9.setName("I");
        account9.setTreasury(true);
        
    }
	
	
	@BeforeEach
	public void init() {
        accountList = new ArrayList<>();
        accountList.add(account1);
        accountList.add(account2);
        accountList.add(account3);
        accountList.add(account4);
        accountList.add(account5);
        accountList.add(account6);
        accountList.add(account7);
        accountList.add(account8);
        accountList.add(account9);
    }
	

	@Test
	void getAllAccounts() {
		
		Mockito.when(accountRepositoryMock.findAll()).thenReturn(accountList);
		ResponseEntity<List<Account>> response = accountService.getAllAccounts();
		
		Assert.assertEquals(200, response.getStatusCodeValue());
		Assert.assertEquals(9, response.getBody().size());
		
	}
	
	@Test
	void getAccountById() {
		
		Mockito.when(accountRepositoryMock.findById(1)).thenReturn(Optional.of(account1));
		ResponseEntity<Account> response = accountService.getAccountById(1);
		
		Assert.assertEquals(200, response.getStatusCodeValue());
		Assert.assertEquals(1, response.getBody().getId());
		Assert.assertEquals(500, response.getBody().getBalance(), 0);
		Assert.assertEquals(Account.Currency.Euro, response.getBody().getCurrency());
		Assert.assertEquals("A", response.getBody().getName());
		Assert.assertFalse(response.getBody().isTreasury());
		
	}
	
	@Test
	void saveAccountNoTreasuryNegativeBalance() {
		
		ResponseEntity<Integer> response = accountService.saveAccount(account4);
		
		Assert.assertEquals(400, response.getStatusCodeValue());
		
	}
	
	@Test
	void saveAccountTreasuryPositiveBalance() {
		
		ResponseEntity<Integer> response = accountService.saveAccount(account3);
		
		Assert.assertEquals(201, response.getStatusCodeValue());
		
	}
	
	@Test
	void saveAccountTreasuryNegativeBalance() {
		
		ResponseEntity<Integer> response = accountService.saveAccount(account5);
		
		Assert.assertEquals(201, response.getStatusCodeValue());
		
	}
	
	@Test
	void transferMoneyNegativeBalance() {
		
		ResponseEntity<String> response = accountService.transferMoney(1, 2, -5);
		
		Assert.assertEquals(400, response.getStatusCodeValue());

	}
	
	@Test
	void transferMoneyZeroBalance() {
		
		ResponseEntity<String> response = accountService.transferMoney(1, 2, -5);
		
		Assert.assertEquals(400, response.getStatusCodeValue());

	}
	
	@Test
	void transferMoneyPositiveBalance() {
		
		Mockito.when(accountRepositoryMock.findById(6)).thenReturn(Optional.of(account6));
		Mockito.when(accountRepositoryMock.findById(7)).thenReturn(Optional.of(account7));
		ResponseEntity<String> response = accountService.transferMoney(6, 7, 5);
		
		Assert.assertEquals(200, response.getStatusCodeValue());

	}
	
	@Test
	void transferMoneyNegativeBalanceNoTreasury() {
		
		Mockito.when(accountRepositoryMock.findById(6)).thenReturn(Optional.of(account6));
		Mockito.when(accountRepositoryMock.findById(7)).thenReturn(Optional.of(account7));
		ResponseEntity<String> response = accountService.transferMoney(6, 7, 600);
		
		Assert.assertEquals(403, response.getStatusCodeValue());

	}
	
	@Test
	void transferMoneyNegativeBalanceTreasury() {
		
		Mockito.when(accountRepositoryMock.findById(8)).thenReturn(Optional.of(account8));
		Mockito.when(accountRepositoryMock.findById(9)).thenReturn(Optional.of(account9));
		ResponseEntity<String> response = accountService.transferMoney(9, 8, 600);
		
		Assert.assertEquals(200, response.getStatusCodeValue());

	}
	

}
