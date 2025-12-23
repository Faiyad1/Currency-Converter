package CurrencyConverter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class AppTest {

    @Test
    public void displaySummaryTest() {
        App.displaySummary(LocalDate.of(2024,9,11),
                           LocalDate.of(2024,9,2),
                "aud","usd");
    }

    @Test
    public void displayPopularCurrenciesTest() {
        CurrencyOperation.LoadCurrencyObjects();
        App.displayPopularCurrencies();
    }

    @Test
    public void displayPopularCurrenciesNullTest() {
        ArrayList<Currency> testCurrs = new ArrayList<>();

        Currency USD = new Currency("USD");
        Currency EUR = new Currency("EUR");
        Currency AUD = new Currency("AUD");
        Currency JPY = new Currency("JPY");

        USD.currentExchangeRates.put(EUR, new Double[]{0.85, 0.01});
        USD.currentExchangeRates.put(AUD, new Double[]{1.30, 0.02});
        USD.currentExchangeRates.put(JPY, new Double[]{110.50, -0.5});

        EUR.currentExchangeRates.put(USD, new Double[]{1.18, 0.02});
        EUR.currentExchangeRates.put(AUD, new Double[]{1.53, 0.01});
        EUR.currentExchangeRates.put(JPY, new Double[]{130.20,0.30});

        AUD.currentExchangeRates.put(USD, new Double[]{0.77, -0.01});
        AUD.currentExchangeRates.put(EUR, new Double[]{0.65, 0.00});
        AUD.currentExchangeRates.put(JPY, new Double[]{185.00, -1.00});
        
        JPY.currentExchangeRates.put(USD, new Double[]{0.0090,null});
        JPY.currentExchangeRates.put(EUR, new Double[]{null, null});
        JPY.currentExchangeRates.put(AUD, new Double[]{0.0117,0.0002});

        testCurrs.add(USD);
        testCurrs.add(EUR);
        testCurrs.add(AUD);
        testCurrs.add(JPY);

        if (CurrencyOperation.PopularCurrencies.size() > 0) {
            CurrencyOperation.PopularCurrencies.clear();
        }
        
        for (Currency curr : testCurrs) {
            CurrencyOperation.PopularCurrencies.add(curr);
        }

        App.displayPopularCurrencies();
    }

    @Test
    public void displayPopularCurrenciesBoxLengthHandlingTest() {
        ArrayList<Currency> testCurrs = new ArrayList<>();

        Currency USD = new Currency("USD");
        Currency EUR = new Currency("EUR");
        Currency AUD = new Currency("AUD");
        Currency JPY = new Currency("JPY");

        USD.currentExchangeRates.put(EUR, new Double[]{0.85, 0.01});
        USD.currentExchangeRates.put(AUD, new Double[]{1.30, 0.02});
        USD.currentExchangeRates.put(JPY, new Double[]{110.50, -0.5});

        EUR.currentExchangeRates.put(USD, new Double[]{1.1111111118, 0.02});
        EUR.currentExchangeRates.put(AUD, new Double[]{1.53, 0.01});
        EUR.currentExchangeRates.put(JPY, new Double[]{130.20,0.30});

        AUD.currentExchangeRates.put(USD, new Double[]{0.77, -0.01});
        AUD.currentExchangeRates.put(EUR, new Double[]{0.65, 0.00});
        AUD.currentExchangeRates.put(JPY, new Double[]{185.00, -1.00});
        
        JPY.currentExchangeRates.put(USD, new Double[]{0.0090,-0.001});
        JPY.currentExchangeRates.put(EUR, new Double[]{10000.000007, 0.008});
        JPY.currentExchangeRates.put(AUD, new Double[]{0.0117,0.0002});

        testCurrs.add(USD);
        testCurrs.add(EUR);
        testCurrs.add(AUD);
        testCurrs.add(JPY);

        if (CurrencyOperation.PopularCurrencies.size() > 0) {
            CurrencyOperation.PopularCurrencies.clear();
        }
        
        for (Currency curr : testCurrs) {
            CurrencyOperation.PopularCurrencies.add(curr);
        }

        App.displayPopularCurrencies();
    }

    @Test
    public void displayPopularCurrenciesDuplicateCurrTest() { 
        // Can display handle a duplicate currency in the top 4?
        // In theory, display should output "-" when it detects two of the same currency

        // Log: Build fails this test case


        ArrayList<Currency> testCurrs = new ArrayList<>();

        Currency USD = new Currency("USD");
        Currency EUR = new Currency("EUR");
        Currency AUD = new Currency("AUD");

        USD.currentExchangeRates.put(EUR, new Double[]{0.85, 0.01});
        USD.currentExchangeRates.put(AUD, new Double[]{1.30, 0.02});

        EUR.currentExchangeRates.put(USD, new Double[]{1.1111111118, 0.02});
        EUR.currentExchangeRates.put(AUD, new Double[]{1.53, 0.01});

        AUD.currentExchangeRates.put(USD, new Double[]{0.77, -0.01});
        AUD.currentExchangeRates.put(EUR, new Double[]{0.65, 0.00});

        testCurrs.add(USD);
        testCurrs.add(EUR);
        testCurrs.add(AUD);
        testCurrs.add(USD); // Duplicate

        if (CurrencyOperation.PopularCurrencies.size() > 0) {
            CurrencyOperation.PopularCurrencies.clear();
        }
        
        for (Currency curr : testCurrs) {
            CurrencyOperation.PopularCurrencies.add(curr);
        }

        App.displayPopularCurrencies();

    }

}
