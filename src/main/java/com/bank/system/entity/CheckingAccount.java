package com.bank.system.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CHECKING")
public class CheckingAccount extends Account {

    private double overdraftLimit = 5000.0;

    @Override
    public boolean withdraw(double amount) {
        // Checking allows overdraft protection line rules
        if (getBalance() + overdraftLimit >= amount) {
            setBalance(getBalance() - amount);
            return true;
        }
        return false;
    }
}