package playground.main;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SalesInfo implements Serializable {
    private String customer;
    private double amount;
    private int year;

    public SalesInfo(String cust, double amt, int year) {
        this.customer = cust;
        this.amount = amt;
        this.year = year;
    }
}