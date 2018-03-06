package me.max.megaeconomy.economy;

import java.math.BigInteger;
import java.util.Map;

public class Economy {

    private String name;
    private String displayname;
    private String symbol;
    private Map<String, BigInteger> balances;

    public Economy(String name, String displayname, String symbol, Map<String, BigInteger> balances){
        this.name = name;
        this.displayname = displayname;
        this.symbol = symbol;
        this.balances = balances;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getDisplayname() {
        return displayname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, BigInteger> getBalances() {
        return balances;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setBalances(Map<String, BigInteger> balances) {
        this.balances = balances;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


}
