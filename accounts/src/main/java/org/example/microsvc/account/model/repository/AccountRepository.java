package org.example.microsvc.account.model.repository;

import org.example.microsvc.account.model.data.AccountModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountRepository extends CrudRepository<AccountModel,Long> {
    List<AccountModel> findByUserId(String userId);
    AccountModel findByAccountId(String accountId);
    @Transactional
    void deleteByAccountId(String accountId);

    @Transactional
    @Modifying
    @Query("update AccountModel a set a.balance = ?1 where a.accountId = ?2")
    void updateAccountByAccountId(Double balance, String accountId);
}
