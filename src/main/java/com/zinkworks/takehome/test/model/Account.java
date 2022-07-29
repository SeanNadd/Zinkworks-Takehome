package com.zinkworks.takehome.test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Account {
    private Integer accountNr, pin, balance, overdraft;

    public Integer updateBalance(Integer withdrawal){
        this.balance -= withdrawal;
        return this.balance;
    }
}
