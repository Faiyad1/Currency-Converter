package CurrencyConverter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


public class CurrencyOperationTest {
    
    @Test
    public void LoadCurrencyObjectsTest() {
        CurrencyOperation.LoadCurrencyObjects();
        assertFalse(CurrencyOperation.PopularCurrencies.isEmpty(), "Popular currencies are unable to load");
    }

    @Test
    public void LoadTopCurrencyTest() {
        String[] TopCurrencies = CurrencyOperation.LoadTopCurrency();
        assertEquals(TopCurrencies.length, 4, "Incomplete access of popular currencies");
    }

    @Test
    public void ModifyTopCurrencyTest() {
        String[] TopCurrencies = CurrencyOperation.LoadTopCurrency();
        CurrencyOperation.ModifyTopCurrency(TopCurrencies[0], "tes");
        String[] NewTopCurrencies = CurrencyOperation.LoadTopCurrency();
        assertEquals(NewTopCurrencies[0], "tes", "Unsuccessful modification of popular currencies");
        CurrencyOperation.ModifyTopCurrency("tes",TopCurrencies[0]);
    }

    @Test
    public void CurrencyRateFinderTestNormalOrder() {
        double rate =  CurrencyOperation.CurrencyRateFinder("aud","usd");
        assertTrue(rate > 0, "Unsuccessful in retriving currency rate");
    }

    @Test
    public void CurrencyRateFinderTestReverseOrder() {
        double rate =  CurrencyOperation.CurrencyRateFinder("eur","aud");
        assertTrue(rate > 0, "Unsuccessful in retriving currency rate where currency names are in inverse order");
    }

    @Test
    public void CurrencyRateFinderTestNotFound() {
        double rate =  CurrencyOperation.CurrencyRateFinder("aud","est");
        assertEquals(rate, -1, "Unsuccessful in handling program where the particular currency rate is not stored");
    }

    @Test
    public void CurrencyRateStoreTest() {
        CurrencyOperation.CurrencyRateStore("tst","tot",0.5);
        double rate =  CurrencyOperation.CurrencyRateFinder("tot","tst");
        assertEquals(rate, 2, "Unsuccessful in storing currency rate");
        try{
            String path = "src/main/resources/";
            BufferedReader br = new BufferedReader(new FileReader(path + "data.csv"));
            ArrayList<String> recordList = new ArrayList<>();
            String record;

            while ((record = br.readLine()) != null){
                recordList.add(record);
            }

            recordList.remove(recordList.size() - 1);

            BufferedWriter wrt = new BufferedWriter(new FileWriter(path + "data.csv"));
            
            for (String row : recordList){
                wrt.write(row);
                wrt.newLine();
            }

            wrt.close();
        } catch (Exception e) {}
    }

    @Test
    public void CurrencySummaryTest() {
        ArrayList<String> ratesList = CurrencyOperation.CurrencySummary(LocalDate.of(2024,9,11),
                                          LocalDate.of(2024,9,2),
                                          "aud","usd");
        assertEquals(ratesList.get(1), "2024-09-11,0.68", 
                     "Unsuccessful in retriving relevant currency rate records for displaying summary");
    }
}
