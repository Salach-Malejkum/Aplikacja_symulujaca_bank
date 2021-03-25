package Package_GUI.Bank;

import Package_StockExchange.Company;
import Package_StockExchange.StockEchange;
import Package_User.Payment;
import Package_User.User;
import java.util.ArrayList;
import java.util.Collections;

public class PaymentHistoryData {


    /*
    //
    //      Metoda zwracajaca historie przy uruchomieniu
    //
     */
    public static String [][] getPaymentData(Object object) {
        final StringBuilder sb = new StringBuilder();
            ArrayList<Payment> payments = (ArrayList<Payment>) ((User)object).getPaymenthistory().clone();
            Collections.reverse(payments);

            for (int i = 0; i < payments.size(); i++) {
                sb.append(payments.get(i).getDate() + "  " + payments.get(i).getAccoutnumb() + "  " +
                        payments.get(i).getTitle() + "  " + payments.get(i).getAmount() + " \n");
            }

            //extract each line from the sb by splitting after the newline character \n
            final String[] rows = sb.toString().split("\n");
            //each line represent a row, so the numbers of lines is the number of rows in our matrix
            final int totalRows = rows.length;
            //the lenght of each line, if we replace space with nothing, represents the number of columns
            //columns have the same number of elements for each line so we can read it from the first row, row[0]. it will be the same for all the others
            final int totalColumns = rows[0].replace("  ", "  ").length();
            //create matrix with proper size
            final String[][] matrix = new String[totalRows][totalColumns];

            //initialize first row index
            int currentRow = 0;
            //initialize first column index
            int currentColumn = 0;

            for (String row : rows) { //for each row
                row = row.substring(0, row.length() - 1); //remove last space character
                final String[] elements = row.split("  "); //store each element of the row in an array by splitting after the space character
                for (final String element : elements) { //for each element
                    //add element in matrix at correct position
                    matrix[currentRow][currentColumn] = String.valueOf(element); //convert string to int
                    //increment column count for the next element
                    currentColumn++;
                }
                //increment row count for the next row
                currentRow++;
                //reset column counter for the next row
                currentColumn = 0;
            }
            return matrix;
        }
    }
