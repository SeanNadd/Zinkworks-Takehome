package com.zinkworks.takehome.test.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ATMServiceTest {

    ATMServiceImpl atmService;

    @BeforeEach
    void setUp() throws Exception {
        atmService = new ATMServiceImpl();
        atmService.afterPropertiesSet();
    }

    @Test
    @DisplayName("Calculate bills correctly")
    void testCalculateBills(){
        assertEquals(Arrays.asList(50,50,50,20,10),atmService.calculateBills(180));
    }

    @Test
    @DisplayName("Test successful withdrawal")
    void testWithdrawSuccess(){
        StringBuilder str = new StringBuilder();
        str.append("Withdrawal successful" + System.getProperty("line.separator"));
        str.append("€20 -> 1" + System.getProperty("line.separator"));
        str.append("€10 -> 1" + System.getProperty("line.separator"));
        str.append(System.getProperty("line.separator") + "Your new balance is: 770");
        assertEquals(str.toString(),
                atmService.withdraw(atmService.accounts.get(0), 30));
    }

    @Test
    @DisplayName("Test withdraw insufficient funds (account)")
    void testWithdrawInsufficientFundsAccount(){
        assertEquals("Amount of withdrawal exceeds account funds",
                atmService.withdraw(atmService.accounts.get(0), 3000));
    }

    @Test
    @DisplayName("Test withdraw insufficient funds (ATM)")
    void testWithdrawInsufficientFundsATM(){
        Map<Integer, Integer> bills = new HashMap<>();
        bills.put(100, 0);
        bills.put(50, 1);
        bills.put(20, 1);
        bills.put(10, 1);
        bills.put(5, 1);
        atmService.atm.setBills(bills);

        assertEquals("This ATM machine doesn't have funds to withdraw that amount",
                atmService.withdraw(atmService.accounts.get(0), 800));
    }

}
