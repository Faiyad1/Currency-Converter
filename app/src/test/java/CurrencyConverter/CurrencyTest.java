package CurrencyConverter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class CurrencyTest {

    private Currency usd;
    private Currency eur;

    @BeforeEach
    public void setup(){
        usd = new Currency("USD");
        eur = new Currency("EUR");

        usd.currentExchangeRates.put(eur, new Double[]{0.85, 0.0});
    }

    @Test
    public void getCurrentExchangeRateTest(){
        assertEquals(0.85, usd.getCurrentExchangeRate(eur),"Fail to get current exchange rate");
    }

    @Test
    public void setExchangeRateTest(){
        usd.setExchangeRate(eur, 0.90);
        assertEquals(0.90, usd.getCurrentExchangeRate(eur),"fail to set exchange rate");
    }

    @Test
    public void getNameTest(){
        assertEquals("USD", usd.getName(), "Fail to get currency name.");
    }
}
