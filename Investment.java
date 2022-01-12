package ePortfolio;
 /**
  * public class Investment is the superclass used to handle all investments 
  */
public abstract class Investment {
    protected String type;
    protected String symbol;
    protected String name;
    protected int quantity;
    protected Double price;
    protected Double bookValue;
    protected Double gain = 0.0;

    public abstract Double buy(int quantity, Double price);
    public abstract Double sell(int quantity, Double price);

    //getters
    /**
     * This method gets the type
     * @return String this returns the investment's type
     */
    public String getType(){
        return this.type;
    }
    /**
     * This method gets the symbol
     * @return String this returns the investment's symbol
     */
    public String getSymbol(){
        return this.symbol;
    }
    /**
     * This method gets the name
     * @return String this returns the investment's name
     */
    public String getName(){
        return this.name;
    }
    /**
     * This method gets the quantity
     * @return int this returns the investment's quantity
     */
    public int getQuantity(){
        return this.quantity;
    }
    /**
     * This method gets the price
     * @return Double this returns the investment's price
     */
    public Double getPrice(){
        return this.price;
    }
    /**
     * This method gets the bookValue
     * @return Double this returns the investment's bookValue
     */
    public Double getBookValue(){
        return this.bookValue;
    }
    /**
     * This method gets the gain
     * @return Double this returns the investment's gain
     */
    public Double getGain(){
        return this.gain;
    }
    //setters
    /**
     * This method modifies the investment's type
     */
    public void setType(String type){
        this.type = type;
    }
    /**
     * This method modifies the investment's symbol
     */
    public void setSymbol(String symbol){
        this.symbol = symbol;
    }
    /**
     * This method modifies the investment's name
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * This method modifies the investment's quantity
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    /**
     * This method modifies the investment's price
     */
    public void setPrice(Double price){
        this.price = price;
    }
    /**
     * This method modifies the investment's bookValue
     */
    public void setBookValue(Double bookValue){
        this.bookValue = bookValue;
    }
    /**
     * This methods returns all the information for a
     * specific investemnt
     */
    public String toString() {  //outputting investment information
        return "\n\nSymbol: " + symbol + "\nName: " + name + "\nQuantity: " + quantity + "\nPrice: " + price + "\nBook value: " + bookValue +"\n\n";
    }
    /**
     * method for file output 
     */
    public String toOutput(String type){
        return "type = \"" + type + "\"\nsymbol = \"" + symbol + "\"\nname = \"" + name + "\"\nquantity = \"" + quantity + "\"\nprice = \"" + price + "\"\nBookvalue = \"" + bookValue +"\"\n\n";
    }
}

 /**
  * class Stock is a subclass used to calculate stock values
  */
class Stock extends Investment{
    final Double COMMISSION = 9.99;

    /**
     * public Stock is used to set the members
     * @param symbol    first parameter to Stock method
     * @param name      second parameter to Stock method
     * @param quantity  third parameter to Stock method
     * @param price     fourth parameter to Stock method
     */
    public Stock(String symbol, String name, int quantity, Double price){
        this.type = "stock";
        this.symbol = symbol;
        this.name = name; 
        this.quantity = quantity;
        this.price = price;    
    }
    /**
     * public Stock is used to set the members
     * @param symbol    first parameter to Stock method
     * @param name      second parameter to Stock method
     * @param quantity  third parameter to Stock method
     * @param price     fourth parameter to Stock method
     * @param bookValue     fifth parameter to Stock method
     */
    public Stock(String symbol, String name, int quantity, Double price, Double bookValue){
        this.type = "stock";
        this.symbol = symbol;
        this.name = name; 
        this.quantity = quantity;
        this.price = price;
        this.bookValue = bookValue;
    }
    /**
     * the buy method is used to calculate the bookValue
     * based on the quantity and price
     * @param quantity  first parameter to buy method
     * @param price     second parameter to buy method
     * @return          Double this returns the calculated bookValue
     */
    public Double buy(int quantity, Double price){
        bookValue = (quantity * price) + COMMISSION;
        return bookValue;
    }
    /**
     * the sell method is used to calculate the payment received
     * when selling a stock and also calculates the gain per sell.
     * @param quantity  first parameter to buy method
     * @param price     second parameter to buy method
     * @return          Double this returns the calculated payment
     */
    public Double sell(int quantity, Double price){
        Double payment = (quantity * price) - COMMISSION;
        this.gain = payment - this.bookValue * (((double)quantity)/(double)this.quantity);
        return payment;
    }
    /**
     * This methods checks if the two objects are equal
     * @param other first parameter to equals method
     * @return  if it is a match it returns each of the members
     */
    public boolean equals(Object other){   //checking if two objects are equal
        if (((Stock)other) == null){
            return false;
        }
        else{
            return symbol == ((Stock)other).symbol && name == ((Stock)other).name && quantity == ((Stock)other).quantity && price == ((Stock)other).price && bookValue == ((Stock)other).bookValue;
        }
    }
}

 /**
  * class MutualFund is a subclass used to calculate mutualfund values
  */
class MutualFund extends Investment{
    final Double REDEMPTION = 45.00;

    /**
     * public MutualFund is used to set the members
     * @param symbol    first parameter to MutualFund method
     * @param name      second parameter to MutualFund method
     * @param quantity  third parameter to MutualFund method
     * @param price     fourth parameter to MutualFund method
     */
    public MutualFund(String symbol, String name, int quantity, Double price){
        this.type = "mutualfund";
        this.symbol = symbol;
        this.name = name; 
        this.quantity = quantity;
        this.price = price;
    }
    /**
     * public MutualFund is used to set the members
     * @param symbol    first parameter to MutualFund method
     * @param name      second parameter to MutualFund method
     * @param quantity  third parameter to MutualFund method
     * @param price     fourth parameter to MutualFund method
     * @param bookValue     fifth parameter to MutualFund method
     */
    public MutualFund(String symbol, String name, int quantity, Double price, Double bookValue){
        this.type = "mutualfund";
        this.symbol = symbol;
        this.name = name; 
        this.quantity = quantity;
        this.price = price;
        this.bookValue = bookValue;
    }
    /**
     * the buy method is used to calculate the bookValue
     * based on the quantity and price
     * @param quantity  first parameter to buy method
     * @param price     second parameter to buy method
     * @return          Double this returns the calculated bookValue
     */
    public Double buy(int quantity, Double price){
        bookValue = quantity * price;
        return bookValue;
    }
    /**
     * the sell method is used to calculate the payment received
     * when selling a stock and also calculates the gain per sell.
     * @param quantity  first parameter to buy method
     * @param price     second parameter to buy method
     * @return          Double this returns the calculated payment
     */
    public Double sell(int quantity, Double price){
        Double payment = (quantity * price) - REDEMPTION;
        this.gain = payment - this.bookValue * (((double)quantity)/(double)this.quantity);
        return payment;
    }
    /**
     * This methods checks if the two objects are equal
     * @param other first parameter to equals method
     * @return  if it is a match it returns each of the members
     */
    public boolean equals(Object other){   //checking if two objects are equal
        if (((MutualFund)other) == null){
            return false;
        }
        else{
            return symbol == ((MutualFund)other).symbol && name == ((MutualFund)other).name && quantity == ((MutualFund)other).quantity && price == ((MutualFund)other).price && bookValue ==((MutualFund)other).bookValue;
        }
    }
}
