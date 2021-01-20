package kata.controller;

import kata.model.BankClient;
import kata.model.MoneyOperation;
import kata.model.StatementOperation;
import kata.services.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private BankClient bankClient;

    @GetMapping("/balance")
    @ResponseBody
    public Number getBalance() {
        return accountManager.getBalance();
    }

    @PostMapping(path = "/deposit", consumes = "application/json")
    @ResponseBody
    public Number deposit(@RequestBody MoneyOperation operation) {
        accountManager.deposit(operation.getValue());
        return accountManager.getBalance();
    }

    @PostMapping(path = "/withdraw", consumes = "application/json")
    @ResponseBody
    public Number withdraw(@RequestBody MoneyOperation operation) {
        accountManager.withdraw(operation.getValue());
        return accountManager.getBalance();
    }

    @GetMapping("/print-statement")
    @ResponseBody
    public List<StatementOperation> printStatement() {
        return accountManager.printStatement();
    }
}
