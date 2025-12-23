package CurrencyConverter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.time.LocalDate;

public class CurrencyOperation {  

    public static ArrayList<Currency> PopularCurrencies = new ArrayList<>();
    private static String path = "src/main/resources/";

    //Initializes all currencies and their values
    public static void LoadCurrencyObjects(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "TopCurrencies.csv"));
            String[] TopCurrencies = br.readLine().split(",");
            
            PopularCurrencies = new ArrayList<>();
            for (int i = 0; i < 4; i++){
                Currency currency = new Currency(TopCurrencies[i]);
                PopularCurrencies.add(currency);
            }

            for (int i = 0; i < 4; i++){
                Currency currency_one = PopularCurrencies.get(i);

                for (int j=0; j < 4; j++){
                    Currency currency_two = PopularCurrencies.get(j);

                    if (i != j){
                        currency_one.currentExchangeRates.put(
                            currency_two, 
                            CurrencyTrend(TopCurrencies[i],TopCurrencies[j]));
                    }
                }
                
            } 
        } catch (Exception e) {}
    }

    //Gets rates and trend for the currency object
    private static Double[] CurrencyTrend(String currency_1, String currency_2){
        Double latest_rate = null;
        Double trend = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "data.csv"));
            
            ArrayList<String> recordList = new ArrayList<>();
            String record;
            Double previous_rate = null;

            while ((record = br.readLine()) != null){
                recordList.add(record);
            }

            for (int i = recordList.size() - 1; i >= 0; i--){

                String row = recordList.get(i);
                String[] values = row.split(",");

                //Finding the latest and previous currency rates
                if (values[1].equals(currency_1) && values[2].equals(currency_2)){
                    if (latest_rate == null){
                        latest_rate = Double.parseDouble(values[3]);
                    }
                    else {
                        previous_rate = Double.parseDouble(values[3]);
                        break;
                    }
                }

                if (values[2].equals(currency_1) && values[1].equals(currency_2)){
                    if (latest_rate == null){
                        latest_rate =  1 / Double.parseDouble(values[3]);
                    }
                    else {
                        previous_rate = 1 / Double.parseDouble(values[3]);
                        break;
                    }
                }
            }

            //Sets the status/trend of the currencies (positive or negative)
            if (latest_rate != null && previous_rate != null){
                if (latest_rate > previous_rate){trend = 1.0;}
                else if (latest_rate < previous_rate){trend = -1.0;}
                else {trend = 0.0;}
            }
        } catch (Exception e) {}

        return new Double[]{(double) Math.round(latest_rate * 100)  /100, trend};
    }

    //Gets top four popular currencies (for admin)
    public static String[] LoadTopCurrency(){
        String[] TopCurrencies = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "TopCurrencies.csv"));
            TopCurrencies = br.readLine().split(",");
        } catch (Exception e) {}
        
        return TopCurrencies;
    }

    //Modifies top four popular currencies (for admin)
    public static void ModifyTopCurrency(String old_currency, String new_currency){
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "TopCurrencies.csv"));
            String[] TopCurrencies = br.readLine().split(",");

            //Replacing the currency
            for (int i=0; i < 4; i++){
                if (TopCurrencies[i].equals(old_currency)){
                    TopCurrencies[i] = new_currency;
                }
            }

            //Writing into csv file
            BufferedWriter wrt = new BufferedWriter(new FileWriter(path + "TopCurrencies.csv"));

            String row = TopCurrencies[0] + "," + 
                         TopCurrencies[1] + "," + 
                         TopCurrencies[2] + "," + 
                         TopCurrencies[3];
            
            wrt.write(row);
            wrt.newLine();
            wrt.close();

        } catch (Exception e) {}
    }

    //Gets exchange rate for currency conversion
    public static double CurrencyRateFinder(String currency_1, String currency_2){
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "data.csv"));
            ArrayList<String> recordList = new ArrayList<>();
            String record;

            while ((record = br.readLine()) != null){
                recordList.add(record);
            }

            for (int i = recordList.size() - 1; i >= 0; i--){

                String row = recordList.get(i);
                String[] values = row.split(",");

                if (values[1].equals(currency_1) && values[2].equals(currency_2)){
                    return Double.parseDouble(values[3]);
                }
                if (values[2].equals(currency_1) && values[1].equals(currency_2)){
                    return (double) Math.round((1 / Double.parseDouble(values[3]) * 100) ) /100;
                }
            }
        } catch (Exception e) {}

        return -1; //if the rate is not found
    }
                                                                            
    //Stores exchange rate in csv file for complete history
    public static void CurrencyRateStore(String currency_1, 
                                         String currency_2, 
                                         double rate){
        try {
            BufferedWriter wrt = new BufferedWriter(new FileWriter(path + "data.csv", true));

            String record = LocalDate.now() + "," + 
                            currency_1 + "," + 
                            currency_2 + "," + 
                            (double) Math.round(rate * 100) /100;

            wrt.write(record);
            wrt.newLine();
            wrt.close();

        }catch (Exception e) {}
    }

    //Generates Summary between two dates
    public static ArrayList<String> CurrencySummary(LocalDate start_date, 
                                                    LocalDate end_date,
                                                    String currency_1, 
                                                    String currency_2){
        ArrayList<String> ratesList = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "data.csv"));
            
            ArrayList<String> recordList = new ArrayList<>();
            ratesList = new ArrayList<>();
            String record;

            //Selects all rates between the dates
            while ((record = br.readLine()) != null){
                String[] values = record.split(",");

                if (!start_date.isBefore(LocalDate.parse(values[0]))){
                    recordList.add(record);
                    if (end_date.isAfter(LocalDate.parse(values[0]))){
                        break;
                    }
                }
            }

            //Filters only the required rates
            for (String row : recordList){
                String[] values = row.split(",");

                if (values[1].equals(currency_1) && values[2].equals(currency_2)){
                    String new_row = values[0] + "," + values[3];
                    ratesList.add(new_row);
                }

                //Arranges the oreder of currency and fixex the rate
                if (values[2].equals(currency_1) && values[1].equals(currency_2)){
                    String new_row = values[0] + "," + 
                                     (double) Math.round((1 / Double.parseDouble(values[3]) * 100) ) /100;
                    ratesList.add(new_row);
                }
            }
        } catch (Exception e) {}

        return ratesList;
    }
}
