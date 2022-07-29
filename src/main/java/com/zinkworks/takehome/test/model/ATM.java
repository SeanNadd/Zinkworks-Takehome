package com.zinkworks.takehome.test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ATM {

    private Map<Integer, Integer> bills;

    public void addBill(Integer denomination){
        Integer existingBills = bills.get(denomination);
        bills.replace(denomination, existingBills + 1);
    }

    public boolean removeBill(Integer denomination)  {
        Integer existingBills = bills.get(denomination);
        if(existingBills == 0){
            return false;
        }
        bills.replace(denomination, existingBills - 1);
        return true;
    }

    public Integer getTotalMoney(){
        return bills.entrySet().stream().mapToInt(entry -> entry.getKey() * entry.getValue()).sum();
    }

    public Integer getBillsForDenomination(Integer denomination){
        return bills.get(denomination);
    }

}
