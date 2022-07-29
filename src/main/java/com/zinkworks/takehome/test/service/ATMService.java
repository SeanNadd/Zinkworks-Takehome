package com.zinkworks.takehome.test.service;

import com.zinkworks.takehome.test.model.Account;

public interface ATMService {
    Account checkAccount(Integer accountNumber, Integer pin) throws Exception ;
    String withdraw(Account account, Integer amount);
    String getBalance(Account account);
}
