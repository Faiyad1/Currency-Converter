package CurrencyConverter;

import java.util.HashMap;


class Currency{
    public String currencyName;
    public HashMap<Currency, Double[]> currentExchangeRates = new HashMap<Currency, Double[]>(); //double[] consists of two elements: 0 -> exch rate, 1 -> diff between last and current rate

    public Currency(String currencyName){
        this.currencyName = currencyName;
    }

    public Double getCurrentExchangeRate(Currency targetCurrency){
        return this.currentExchangeRates.get(targetCurrency)[0];
    }
    
    public void setExchangeRate(Currency targetCurrency, Double newRate){
        Double[] newDouble = {newRate, newRate - this.currentExchangeRates.get(targetCurrency)[0]}; //second element to print increase/decrease
        this.currentExchangeRates.put(targetCurrency, newDouble);
    }

    public String getName(){
        return this.currencyName;
    }
}