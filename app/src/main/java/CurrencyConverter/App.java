package CurrencyConverter;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;


public class App {

    static UserType userType;
    static Scanner scan = new Scanner(System.in);


    public static void userLogin(){
        String greeting = "Welcome to Currency Converter! Select user type (enter 1 or 2): \n1. Normal\n2. Admin";
        String input;

        while(true){

            System.out.println(greeting);

            input = scan.nextLine();

            try{
                if(Integer.parseInt(input) == 1){
                    userType = UserType.NORMAL;
                    break;
                }
                else if(Integer.parseInt(input) == 2){
                    userType = UserType.ADMIN;
                    break;
                }
                else{
                    throw new Exception();
                }
            }
            catch(Exception e){
                System.out.println("Invalid input, try again.");
                continue;
            }

        }
    }

    public static int mainMenu(){
        String input;
        int cmd;
        while(true){

            System.out.println("What do you want to do today? Enter numbers.");
            System.out.println("0. Exit");

            //Add new features navigation here
            System.out.println("1. Convert money");
            System.out.println("2. Display most popular currencies");
            System.out.println("3. Print summary of 2 currency's conversion rates.");
            if(userType == UserType.ADMIN){
                System.out.println("4. Set popular currencies"); 
                System.out.println("5. Update exchange rate");
                System.out.println("Note: if you want to add a new currency, update exchange rate and enter a new currency exchange rate record for that new currency.");
            }


            try{

                input = scan.nextLine();

                cmd = Integer.parseInt(input);

                if(cmd >= 0 || cmd < 4){
                    break;
                }
                else if(cmd == 4){
                    if(userType == UserType.ADMIN){
                        break;
                    }
                    else{
                        throw new Exception();
                    }
                }
                else if(cmd == 5){
                    if(userType == UserType.ADMIN){
                        break;
                    }
                    else{
                        throw new Exception();
                    }
                }
                else{
                    throw new Exception();
                }
            }
            catch(Exception e){
                System.out.println("Invalid input. Try again.");
                continue;
            }
        }

        return cmd;
    }

    public static void convertMoneyMenu(){
        while(true){
            System.out.print("Enter current currency code, e.g. AUD: ");
            String c1Str = scan.nextLine().toLowerCase();
            System.out.print("Enter target currency code, e.g. USD: ");
            String c2Str = scan.nextLine().toLowerCase();
            if(c1Str.equals(c2Str)){
                System.out.println("Current and target currency is the same.");
                continue;
            }
            System.out.print("Enter amount: ");
            double amount;
            try{
                amount = Double.parseDouble(scan.nextLine());
            }
            catch(Exception e){
                System.out.println("Invalid input. Try again.");
                System.out.println();
                continue;
            }
            double exchangeRate = CurrencyOperation.CurrencyRateFinder(c1Str, c2Str);
            double convertedAmount = exchangeRate * amount;
            if(exchangeRate == -1){
                System.out.println("Currency not found, try again.");
                System.out.println();
                continue;
            }
            else{
                System.out.println(amount + " " + c1Str.toUpperCase() + " is " + (double) Math.round(convertedAmount * 100) / 100 + " " + c2Str.toUpperCase() + ". Press Enter to return to main menu.");
            }
            
            String input = scan.nextLine();
            break;
        }
    }

    public static void displayPopularCurrencies() {
        // Entire table with values retrieved and displayed
        
        // Names of top 4 currencies have been defined.
        // System.out.println("+++++ Creating Table... +++++");

        ArrayList<Currency> popCurrs = CurrencyOperation.PopularCurrencies;
        String[][] exchangeTableData = new String[4][4]; // Currency grid

        // System.out.println("+++ Empty table created, populating table... ");

        for (int i = 0; i < exchangeTableData.length; i++) { // Iterating through empty grid
            for (int j = 0; j < exchangeTableData[i].length; j++) {
                // Row i col j
                if (i == j) {
                    exchangeTableData[i][j] = "-"; // Exch same currs are nulled
                } else {

                    if (popCurrs.get(i).getName().equals(popCurrs.get(j).getName())) {
                        exchangeTableData[i][j] = "-";
                    } else {

                        // Original code block is just this
                        if (popCurrs.get(i).getCurrentExchangeRate(popCurrs.get(j)) == null) {

                            exchangeTableData[i][j] = "-";
    
                        } else if (popCurrs.get(i).currentExchangeRates.get(popCurrs.get(j))[1] == null) {
    
                            String exchRate = String.valueOf(popCurrs.get(i).getCurrentExchangeRate(popCurrs.get(j))); 
                            exchangeTableData[i][j] = exchRate; // Just exchange rate
    
                        } else { // Both are not null
    
                            String exchRate = String.valueOf(popCurrs.get(i).getCurrentExchangeRate(popCurrs.get(j))); // Getting exchange rate
                            Double delta = popCurrs.get(i).currentExchangeRates.get(popCurrs.get(j))[1]; // Getting change since last update
    
                            if (delta > 0) { // Positive change
                                exchangeTableData[i][j] = exchRate + "(I)"; // I for increase
                            } else if (delta < 0) { // Negative change
                                exchangeTableData[i][j] = exchRate + "(D)"; // D for decrease
                            } else {
                                exchangeTableData[i][j] = exchRate;
                            }
                        }
                    }


                }
                // System.out.println("Populated Row " + (i+1) + " Col " + (j+1));
                
            }
        }

        System.out.println("|=========|===============|===============|===============|===============|"); // Border
        System.out.println("| From/to |      "
            + popCurrs.get(0).currencyName.toUpperCase()
            + "      |      "
            + popCurrs.get(1).currencyName.toUpperCase()
            + "      |      "
            + popCurrs.get(2).currencyName.toUpperCase()
            + "      |      "
            + popCurrs.get(3).currencyName.toUpperCase()
            + "      |"
        ); // Title row
        System.out.println("|=========|===============|===============|===============|===============|"); // Border

        for (int i = 0; i < exchangeTableData.length; i++) {
            System.out.print("|   " + popCurrs.get(i).currencyName.toUpperCase() + "   |"); // Row title

            for (int j = 0; j < exchangeTableData[i].length; j++) {
                StringBuilder sb = new StringBuilder();
                sb.append(" ");

                if (exchangeTableData[i][j].length() < 13) { // Will maintain box length (14 chars)
                    sb.append(exchangeTableData[i][j]);
                    for (int k = 0; k < (14 - exchangeTableData[i][j].length()); k++) {
                        sb.append(" ");
                    }
                } else { // If string happens to be longer, it will cut the string down and add an ellipsis
                    String shortened = exchangeTableData[i][j].substring(0,10) + "... ";
                    sb.append(shortened);
                }
                sb.append("|");
                System.out.print(sb.toString());
            }
            System.out.println("");
        }
        System.out.println("|=========|===============|===============|===============|===============|"); // Border

    }

    public static void printSummaryMenu(){
        String currency1;
        String currency2;
        LocalDate startDate;
        LocalDate endDate;
        while(true){
            System.out.print("Enter first currency, e.g. AUD: ");
            currency1 = scan.nextLine().toLowerCase();
            if(currency1.length() != 3){
                System.out.println("Not a valid currency, try again.");
                System.out.println();
                continue;
            }
            System.out.print("Enter second currency, e.g. USD: ");
            currency2 = scan.nextLine().toLowerCase();
            if(currency2.length() != 3){
                System.out.println("Not a valid currency, try again.");
                System.out.println();
                continue;
            }
            System.out.print("Enter start date in the following format: YYYY-MM-DD, e.g. 2024-01-01: ");
            try{
                startDate = LocalDate.parse(scan.nextLine());
            }
            catch(Exception e){
                System.out.println("Invalid input, try again.");
                System.out.println();
                continue;
            }
            System.out.print("Enter end date in the following format: YYYY-MM-DD, e.g. 2025-01-01: ");
            try{
                endDate = LocalDate.parse(scan.nextLine());
            }
            catch(Exception e){
                System.out.println("Invalid input, try again.");
                System.out.println();
                continue;
            }
            if(startDate.isAfter(endDate)){
                System.out.println("Error: start date is after end date, try again.");
                System.out.println();
                continue;
            }
            else{
                break;
            }
        }
        System.out.println();
        displaySummary(endDate, startDate, currency1, currency2);
        System.out.println();

    }

    public static void displaySummary(LocalDate start_date, LocalDate end_date, String currency_1, String currency_2){
        ArrayList<String> summary = CurrencyOperation.CurrencySummary(start_date, end_date, currency_1, currency_2);
        if (summary.size() == 0){
            System.out.println("No corresponding conversion rates.");
        }
        else{
        
            ArrayList<Double> rate = new ArrayList<>();
            for(int i = 0; i < summary.size(); i++){
                String[] list = summary.get(i).split(",");
                System.out.println("<" + list[0] + ">: " + list[1]);
                rate.add(Double.valueOf(list[1]));
            }
            DoubleSummaryStatistics stats = rate.stream().mapToDouble(Double::doubleValue).summaryStatistics();
            double average = stats.getAverage();
            double maximum = stats.getMax();
            double minimum = stats.getMin();
            double median = calculateMedian(rate);
            double standardDeviation = calculateStandardDeviation(rate, average);
            System.out.println("Average Rate: " + average);
            System.out.println("Median Rate: " + median);
            System.out.println("Maximum Rate: " + maximum);
            System.out.println("Minimum Rate: " + minimum);
            System.out.println("Standard Deviation: " + standardDeviation);
        }

    }
    private static double calculateMedian(ArrayList<Double> list) {
        list.sort(Double::compareTo); // Sort the list
        int size = list.size();
        if (size % 2 == 0) {
            return (list.get(size / 2 - 1) + list.get(size / 2)) / 2.0; // Even case
        } else {
            return list.get(size / 2); // Odd case
        }
    }

    
    private static double calculateStandardDeviation(ArrayList<Double> list, double mean) {
        double sumOfSquares = 0.0;
        for (double value : list) {
            sumOfSquares += Math.pow(value - mean, 2);
        }
        double variance = sumOfSquares / list.size();
        return Math.sqrt(variance);
    }

    public static void setPopularCurrenciesMenu(){
        while(true){
            System.out.print("Top 4 currencies are: ");
            for(int i = 0; i < 4; i++){
                System.out.print(CurrencyOperation.PopularCurrencies.get(i).getName().toUpperCase());
                if(i != 3){
                    System.out.print(", ");
                }
            }
            System.out.println();
            System.out.println("Enter code of currency to replace, e.g. AUD: ");
            String oldCurrency = scan.nextLine().toLowerCase();
            boolean inList = false;
            for(String s: CurrencyOperation.LoadTopCurrency()){
                if(s.equals(oldCurrency)){
                    inList = true;
                    break;
                }
            }
            if(inList == false){
                System.out.println("Currency not found, try again.");
                System.out.println();
                continue;
            }
            System.out.println();
            System.out.println("Enter code of currency to add, e.g. USD: ");
            String newCurrency = scan.nextLine().toLowerCase();
            if(newCurrency.contains(",")){
                System.out.println("Invalid input, try again");
                System.out.println();
                continue;
            }
            if(newCurrency.equals(oldCurrency)){
                System.out.println("New currency is the same as previous one. Try again.");
                System.out.println();
                continue;
            }
            inList = false;
            for(String s: CurrencyOperation.LoadTopCurrency()){
                if(s.equals(newCurrency)){
                    inList = true;
                }
            }
            if(inList == true){
                System.out.println("The currency you want to add is already in the popular list.");
                continue;
            }
            else{
                CurrencyOperation.ModifyTopCurrency(oldCurrency, newCurrency);
                CurrencyOperation.LoadCurrencyObjects();
                System.out.println("Succesfully added!");
                System.out.print("Now the top 4 currencies are: ");
                for(int i = 0; i < 4; i++){
                    System.out.print(CurrencyOperation.PopularCurrencies.get(i).getName().toUpperCase());
                    if(i != 3){
                        System.out.print(", ");
                    }
                }
                System.out.println();
                System.out.println("Press Enter to return to the main menu");
                String toMenu = scan.nextLine();
                break;
            }
        }
    }

    public static void updateExchangeRateMenu(){
        while(true){
            System.out.print("Enter first currency code, e.g. AUD: ");
            String currency1 = scan.nextLine().toLowerCase();
            if(currency1.contains(",")){
                System.out.println("Invalid input, try again");
                System.out.println();
                continue;
            }
            System.out.print("Enter second currency code, e.g. USD: ");
            String currency2 = scan.nextLine().toLowerCase();
            if(currency2.contains(",")){
                System.out.println("Invalid input, try again");
                System.out.println();
                continue;
            }
            double rate = 0;
            System.out.print("Enter the rate of conversion, e.g. 0.1234: ");
            try{
                rate = Double.parseDouble(scan.nextLine());
                if(rate <= 0){
                    throw new Exception();
                }
            }
            catch(Exception e){
                System.out.println("Invalid input, try again.");
                System.out.println();
                continue;
            }
            CurrencyOperation.CurrencyRateStore(currency1, currency2, rate);
            break;
        }
        System.out.println("Currency exchange rate updated. Press Enter to return to main menu.");
        String toMenu = scan.nextLine();
    }
    
    public static void main(String[] args){
        CurrencyOperation.LoadCurrencyObjects();
        userLogin();

        while(true){
            int input = mainMenu();

            if(input == 0){
                System.out.println("Goodbye!");
                System.exit(input);
            }
            else if(input == 1){
                convertMoneyMenu();
            }
            else if(input == 2){
                displayPopularCurrencies();
            }
            else if(input == 3){
                printSummaryMenu();
            }
            else if(input == 4){
                setPopularCurrenciesMenu();
            }
            else if(input == 5){
                updateExchangeRateMenu();
            }
        }
    }
}
