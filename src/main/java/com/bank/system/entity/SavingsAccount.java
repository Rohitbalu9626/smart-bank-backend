package com.bank.system.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SAVINGS")
public class SavingsAccount extends Account {

    @Override
    public boolean withdraw(double amount) {
        // Enforce basic savings criteria (e.g., cannot go below 0)
        if (getBalance() >= amount) {
            setBalance(getBalance() - amount);
            return true;
        }
        return false;
    }
}