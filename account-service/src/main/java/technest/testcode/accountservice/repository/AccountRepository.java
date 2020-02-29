package technest.testcode.accountservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import technest.testcode.accountservice.model.Account;

/**
 * Account repository
 * 
 * @author sergio.sanchez.gil
 * 
 */

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

}
