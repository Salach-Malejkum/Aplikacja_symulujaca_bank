package Package_StockExchange;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;


public class StockEchange implements Serializable {

    private static final long serialVersionUID = -6789563445954436766L;
    private ArrayList<Company> companyArrayList;


    public StockEchange(){

        companyArrayList = getStockExchangeData();

    }
    public void addCompanies(){

        companyArrayList.add(new Company("Firma 1", new BigDecimal(1000.0)));
        companyArrayList.add(new Company("Firma 2", new BigDecimal(1000.0)));
        companyArrayList.add(new Company("Firma 3", new BigDecimal(1000.0)));
        companyArrayList.add(new Company("Firma 4", new BigDecimal(1000.0)));

    }
    public void saveStockExchange(){

        try {

            FileOutputStream fos = new FileOutputStream("C:\\Users\\mateu\\OneDrive\\Dokumenty\\Projects\\PWRbank\\stock-exchange\\stockexchange.ser");
            ObjectOutputStream so = new ObjectOutputStream(fos);

            so.writeObject(companyArrayList);

            fos.close();
            so.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Company> getCompanyArrayList() {
        return companyArrayList;
    }

    public void setCompanyArrayList(ArrayList<Company> companyArrayList) {
        this.companyArrayList = companyArrayList;
    }

    public ArrayList<Company> getStockExchangeData(){

        try (FileInputStream fis = new FileInputStream("C:\\Users\\mateu\\OneDrive\\Dokumenty\\Projects\\PWRbank\\stock-exchange\\stockexchange.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            return (ArrayList<Company>) ois.readObject();

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }return null;
    }
}
