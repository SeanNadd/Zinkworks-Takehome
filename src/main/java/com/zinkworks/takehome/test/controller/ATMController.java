package com.zinkworks.takehome.test.controller;

import com.zinkworks.takehome.test.service.ATMService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ATMController {
    private ATMService atmService;

    @RequestMapping(
            value = "{account}/{pin}/balance",
            method = RequestMethod.GET,
            produces = "text/plain")
    String balance(@PathVariable Integer account, @PathVariable Integer pin){
        try {
            return atmService.getBalance(atmService.checkAccount(account, pin));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(
            value = "{account}/{pin}/withdraw/{amount}",
            method = RequestMethod.GET,
            produces = "text/plain")
    String withdraw(@PathVariable Integer amount,
                    @PathVariable Integer account, @PathVariable Integer pin){
        try {
            return atmService.withdraw(atmService.checkAccount(account, pin), amount);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
