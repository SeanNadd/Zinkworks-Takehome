package com.zinkworks.takehome.test.service.impl;

import com.zinkworks.takehome.test.model.ATM;
import com.zinkworks.takehome.test.model.Account;
import com.zinkworks.takehome.test.service.ATMService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ATMServiceImpl implements ATMService, InitializingBean {

    protected List<Account> accounts;
    protected ATM atm;


    protected static final int[] denom = {100, 50, 20, 10, 5};


    @Override
    public String withdraw(Account account, Integer amount) {

        if(account.getBalance() + account.getOverdraft() < amount){
            return "Amount of withdrawal exceeds account funds";
        }

        if(atm.getTotalMoney() < amount){
            return "This ATM machine doesn't have funds to withdraw that amount";
        }

        account.updateBalance(amount);
        return formatResponse(calculateBills(amount), account);
    }

    @Override
    public String getBalance(Account account) {
        StringBuilder str = new StringBuilder();
        str.append("Your account balance is: " + account.getBalance() + System.getProperty("line.separator"));
        str.append("Maximum withdrawal amount: " + (account.getBalance() + account.getOverdraft()));
        return str.toString();
    }

    protected List<Integer> calculateBills(Integer amount){
        int a = amount;
        int i = 0;
        List<Integer> dispense = new ArrayList<>();

        while (a > 0){
            if((a - denom[i] >= 0) && atm.getBillsForDenomination(denom[i]) > 0){
                a -= denom[i];
                atm.removeBill(denom[i]);
                dispense.add(denom[i]);
            }else{
                i++;
            }
        }

        return dispense;
    }

    protected String formatResponse(List<Integer> dispensedBills, Account account){
        StringBuilder str = new StringBuilder();

        str.append("Withdrawal successful" + System.getProperty("line.separator"));

        Map<Integer, Long> billCount = dispensedBills.stream()
                .collect(Collectors.groupingBy(a -> a, Collectors.counting()));
        billCount.forEach((k,v) -> str.append("€" + k + " -> " + v + System.getProperty("line.separator")));

        str.append(System.getProperty("line.separator") + "Your new balance is: " + account.getBalance());
        return str.toString();
    }

   @Override
    public Account checkAccount(Integer accountNumber, Integer pin) throws Exception {
        Account account = accounts.stream().filter(a -> a.getAccountNr().equals(accountNumber)).findFirst().orElse(null);

        if(account == null){
            throw new Exception("Account not found");
        }

        if(!account.getPin().equals(pin)){
            throw new Exception("Authentication Error - Please enter the correct PIN number for this account");
        }

        return account;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Initialize Accounts
        accounts = new ArrayList<>();
        accounts.add(new Account(123456789, 1234, 800, 200));
        accounts.add(new Account(987654321, 4321, 1230, 150));

        // Initialize ATM
        atm = new ATM();
        Map<Integer, Integer> bills = new HashMap<>();
        bills.put(100, 0);
        bills.put(50, 10);
        bills.put(20, 30);
        bills.put(10, 30);
        bills.put(5, 20);
        atm.setBills(bills);
    }
}
