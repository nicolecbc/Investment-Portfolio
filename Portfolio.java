package ePortfolio;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.io.*;
import java.util.List;

/**
 * The portfolio class holds the main code where the main and the action listener
 * classes are called. This program provides a list of menu options to choose from
 * to handle a user's stock and mutual fund investments. The user has the option
 * to buy, sell, update prices, compute gains, and search for specific investments.
 * @author Nicole Bogoljubsky
 */
public class Portfolio extends JFrame {
    //declare menu
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("Commands");
    private JMenuItem itemBuy = new JMenuItem ("Buy investment");
    private JMenuItem itemSell = new JMenuItem ("Sell investment");
    private JMenuItem itemUpdate = new JMenuItem ("Update investments");
    private JMenuItem itemGain = new JMenuItem ("Get total gain");
    private JMenuItem itemSearch = new JMenuItem ("Search investment");
    private JMenuItem itemQuit = new JMenuItem ("Quit");
    //declare panels
    private JPanel controlPanel = new JPanel();
    //declare labels
    private JLabel welcomeLabel = new JLabel("     Welcome to ePortfolio");
    private JLabel label_subtitle = new JLabel();
    private JLabel label_output = new JLabel();
    private JLabel label_type = new JLabel("Type");
    private JLabel label_symbol = new JLabel("Symbol");
    private JLabel label_name = new JLabel("Name");
    private JLabel label_keywords = new JLabel("Name keywords");
    private JLabel label_quantity = new JLabel("Quantity");
    private JLabel label_price = new JLabel("Price");
    private JLabel label_totalGain = new JLabel("Total gain");
    private JLabel label_lowPrice = new JLabel("Low Price");
    private JLabel label_highPrice = new JLabel("High Price");
    //declare combo boxes
    String[] dropdownList = {"Stock", "Mutual Fund"};
    private JComboBox dropdown = new JComboBox(dropdownList);
    //declare textFields
    private JTextField textbox_symbol = new JTextField();
    private JTextField textbox_name = new JTextField();
    private JTextField textbox_quantity = new JTextField();
    private JTextField textbox_price = new JTextField();
    private JTextField textbox_totalGain = new JTextField();
    private JTextField textbox_keywords = new JTextField();
    private JTextField textbox_lowPrice = new JTextField();
    private JTextField textbox_highPrice = new JTextField();
    //declare text areas/scroll
    private JTextArea instructionMessage = new JTextArea(10,5);
    private JTextArea text_output = new JTextArea();
    private JScrollPane scroll = new JScrollPane(text_output);
    //declare buttons
    private JButton button_reset = new JButton("Reset");
    private JButton button_buy = new JButton("Buy");
    private JButton button_sell = new JButton("Sell");
    private JButton button_next = new JButton("Next");
    private JButton button_prev = new JButton("Prev");
    private JButton button_save = new JButton("Save");
    private JButton button_search = new JButton("Search");

    Map<String, List<Integer>> keywords = new HashMap<String, List<Integer>>();
    ArrayList<Investment> arrInvestments = new ArrayList<Investment>();
    ArrayList<String> tokenizedNames = new ArrayList<String>();
    ArrayList<Integer> indexesMatched = new ArrayList<Integer>();
    boolean stop = false;
    int userOption;
    boolean found = false;
    boolean foundInvestment = false;
    int i=0;
    int j=0;
    int position=0;
    int deleted=0;
    int count=0;
    String type;
    String userSymbol; 
    String userName; 
    int userQuantity;
    Double userPrice;
    Double userBookValue;
    Double totalGain = 0.0;
    String searchSymbol;
    boolean foundSymbol = false;
    String tempName;
    boolean foundKeyword = false;
    String searchKeywords[] = new String [10];
    boolean foundPrice = false;
    String searchLowPrice;
    String searchHighPrice;

    /**
     * constructor to create the user interface
     */
    public Portfolio(){
        super();
        prepareGUI();
    }
    /**
     * listener_buy recieves action events from menu option itemBuy
     */
    private class listener_buy implements ActionListener {
        /**
         * This method is invoked when the menu buy action occurs
         */
        public void actionPerformed(ActionEvent e) {
            controlPanel.removeAll();
            controlPanel.setLayout(null);
            //subtitle, reset, buy
            label_subtitle.setText("Buying an investment");
            label_subtitle.setSize(200,30);
            label_subtitle.setLocation(5,0);
            button_reset.setSize(100,30);
            button_reset.setLocation(360,70);
            button_buy.setSize(100,30);
            button_buy.setLocation(360,110);
            controlPanel.add(label_subtitle);
            controlPanel.add(button_reset);
            controlPanel.add(button_buy);
            //type
            label_type.setSize(200,25);
            label_type.setLocation(30,30);
            controlPanel.add(label_type);
            dropdown.setSize(200,25);
            dropdown.setLocation(100, 30);
            controlPanel.add(dropdown);
            //symbol
            label_symbol.setSize(200,25);
            label_symbol.setLocation(30,60);
            controlPanel.add(label_symbol);
            textbox_symbol.setSize(200,25);
            textbox_symbol.setLocation(100, 60);
            textbox_symbol.setEditable(true);
            textbox_symbol.setText("");
            controlPanel.add(textbox_symbol);
            //name
            label_name.setSize(200,25);
            label_name.setLocation(30,90);
            controlPanel.add(label_name);
            textbox_name.setSize(200,25);
            textbox_name.setLocation(100, 90);
            textbox_name.setEditable(true);
            textbox_name.setText("");
            controlPanel.add(textbox_name);
            //quantity
            label_quantity.setSize(200,25);
            label_quantity.setLocation(30,120);
            controlPanel.add(label_quantity);
            textbox_quantity.setSize(200,25);
            textbox_quantity.setLocation(100, 120);
            textbox_quantity.setEditable(true);
            textbox_quantity.setText("");
            controlPanel.add(textbox_quantity);
            //price
            label_price.setSize(200,25);
            label_price.setLocation(30,150);
            controlPanel.add(label_price);
            textbox_price.setSize(200,25);
            textbox_price.setLocation(100, 150);
            textbox_price.setEditable(true);
            textbox_price.setText("");
            controlPanel.add(textbox_price);
            //output
            label_output.setText("Messages");
            label_output.setSize(200,25);
            label_output.setLocation(5,190);
            text_output.setSize(475,200);
            text_output.setLocation(5,225);
            text_output.setEditable(false);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setSize(475,200);
            scroll.setLocation(5,225);
            controlPanel.add(scroll);
            controlPanel.add(label_output);

            controlPanel.repaint();
        }
     }
    /**
     * listener_button_buy recieves action events from button_buy
     */
    private class listener_button_buy implements ActionListener {
        /**
         * This method is invoked when the button buy action occurs
         */
        public void actionPerformed(ActionEvent e) {
            if(dropdown.getSelectedIndex()==0){
                userOption = 1; //stock selected
            }
            else{
                userOption = 2; //mutualfund selected
            }
            userSymbol = textbox_symbol.getText();
        
            found = false;
            for(i=0; i<arrInvestments.size(); i++){   //checking if symbol exists in investments
                String currentSymbol = arrInvestments.get(i).getSymbol();
                found = currentSymbol.equals(userSymbol);
                if(found==true){
                    break;
                }
                else{
                    found = false;
                }
            }
            
            if(found==true){//if symbol found
                //get input for new quantity and price
                userQuantity = Integer.parseInt(textbox_quantity.getText());
                userPrice = Double.parseDouble(textbox_price.getText());
                //increase the quantity, update the price, and adjust the bookValue for the existing investment
                int currentQuantity = arrInvestments.get(i).getQuantity();
                arrInvestments.get(i).setQuantity(currentQuantity + userQuantity);
                arrInvestments.get(i).setPrice(userPrice);
                Double currentBookValue = arrInvestments.get(i).getBookValue();
                Double newBookValue = 0.0;
                if(userOption == 1){
                    newBookValue = currentBookValue + ((Stock)arrInvestments.get(i)).buy(userQuantity, userPrice);
                }
                else if(userOption == 2){
                    newBookValue = currentBookValue + ((MutualFund)arrInvestments.get(i)).buy(userQuantity, userPrice);
                }
                arrInvestments.get(i).setBookValue(newBookValue);
                //print updated investments
                text_output.append("\n");
                text_output.append(arrInvestments.toString().replace('[', ' ').replace(']', ' ').replace(',', ' ').trim());
            }
            
            else if (found==false){//if symbol not found
                //get input for new quantity, price and name
                userQuantity = Integer.parseInt(textbox_quantity.getText());
                userPrice = Double.parseDouble(textbox_price.getText());
                userName = textbox_name.getText();

                //create new investement from given information
                if(userOption == 1){               
                    Stock newStock = new Stock(userSymbol, userName, userQuantity, userPrice);  
                    arrInvestments.add(newStock);
                    ((Stock)arrInvestments.get(arrInvestments.size()-1)).buy(userQuantity, userPrice);  
                }
                else if(userOption == 2){
                    MutualFund newMutualFund = new MutualFund(userSymbol, userName, userQuantity, userPrice);
                    arrInvestments.add(newMutualFund);
                    ((MutualFund)arrInvestments.get(arrInvestments.size()-1)).buy(userQuantity, userPrice);
                }

                //tokenizing name
                String[] splitName = userName.split(" ");
                for(i=0; i < splitName.length; i++){
                    tokenizedNames.add(splitName[i].toLowerCase()); //lowercase for better search
                }
                //mapping keywords onto hashmap
                for(i=0; i < tokenizedNames.size(); i++){
                    if(keywords.containsKey(tokenizedNames.get(i))){ //keyword found, add index to list
                        keywords.get(tokenizedNames.get(i)).add(position);
                    }
                    else{   //keyword not found, create new entry
                        keywords.put(tokenizedNames.get(i), new ArrayList<Integer>());
                        keywords.get(tokenizedNames.get(i)).add(position);
                    }
                }
                position++;
                tokenizedNames.clear();                  
                //System.out.println(keywords);
                //print updated investments
                text_output.setText("");
                text_output.append("\n");
                text_output.append(arrInvestments.toString().replace('[', ' ').replace(']', ' ').replace(',', ' ').trim());
            }
        controlPanel.repaint(); //updating the panel 
        }
    }
    /**
     * listener_sell recieves action events from menu option itemSell
     */
    private class listener_sell implements ActionListener {
        /**
         * This method is invoked when the menu sell action occurs
         */
        public void actionPerformed(ActionEvent e) {
            controlPanel.removeAll();
            controlPanel.setLayout(null);
            //subtitle, reset, buy
            label_subtitle.setText("Selling an investment");
            label_subtitle.setSize(200,30);
            label_subtitle.setLocation(5,0);
            button_reset.setSize(100,30);
            button_reset.setLocation(360,70);
            button_sell.setSize(100,30);
            button_sell.setLocation(360,110);
            controlPanel.add(label_subtitle);
            controlPanel.add(button_reset);
            controlPanel.add(button_sell);
            //symbol
            label_symbol.setSize(200,25);
            label_symbol.setLocation(30,60);
            controlPanel.add(label_symbol);
            textbox_symbol.setSize(200,25);
            textbox_symbol.setLocation(100, 60);
            textbox_symbol.setEditable(true);
            textbox_symbol.setText("");
            controlPanel.add(textbox_symbol);
            //quantity
            label_quantity.setSize(200,25);
            label_quantity.setLocation(30,105);
            controlPanel.add(label_quantity);
            textbox_quantity.setSize(200,25);
            textbox_quantity.setLocation(100, 105);
            textbox_quantity.setEditable(true);
            textbox_quantity.setText("");
            controlPanel.add(textbox_quantity);
            //price
            label_price.setSize(200,25);
            label_price.setLocation(30,150);
            controlPanel.add(label_price);
            textbox_price.setSize(200,25);
            textbox_price.setLocation(100, 150);
            textbox_price.setEditable(true);
            textbox_price.setText("");
            controlPanel.add(textbox_price);
            //output
            label_output.setText("Messages");
            label_output.setSize(200,25);
            label_output.setLocation(5,190);
            text_output.setSize(475,200);
            text_output.setLocation(5,225);
            text_output.setEditable(false);
            text_output.setLineWrap(true);
            text_output.setWrapStyleWord(true);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setSize(475,200);
            scroll.setLocation(5,225);
            controlPanel.add(scroll);
            controlPanel.add(label_output);

            controlPanel.repaint();
        }
    }
    /**
     * listener_button_sell recieves action events from button_sell
     */
    private class listener_button_sell implements ActionListener {
        /**
         * This method is invoked when the button sell action occurs
         */
        public void actionPerformed(ActionEvent e) {
            userSymbol = textbox_symbol.getText();

            //checking if symbol exists in investments
            for(i=0; i<arrInvestments.size(); i++){   
                String currentSymbol = arrInvestments.get(i).getSymbol();
                foundInvestment = currentSymbol.equals(userSymbol);
                
                //found symbol
                if(foundInvestment==true){    
                    userQuantity = Integer.parseInt(textbox_quantity.getText());
                    userPrice = Double.parseDouble(textbox_price.getText());

                    //check available quantity is greater than or equal to the requested quantity
                    int currentQuantity = arrInvestments.get(i).getQuantity();
                    int remainingQuantity = currentQuantity - userQuantity;
                    Double payment = 0.0;
                    if(currentQuantity >= userQuantity){
                        if(arrInvestments.get(i) instanceof Stock){ //sell stock
                            payment = ((Stock)arrInvestments.get(i)).sell(userQuantity, userPrice);
                        }
                        else if(arrInvestments.get(i) instanceof MutualFund){   //sell mutualfund
                            payment = ((MutualFund)arrInvestments.get(i)).sell(userQuantity, userPrice);
                        }
                        text_output.append("\nPayment recieved: " + payment);

                        //if quantity remains greater than 0, update bookValue
                        arrInvestments.get(i).setQuantity(remainingQuantity);
                        if(remainingQuantity > 0){ 
                            Double newBookValue = (arrInvestments.get(i).getBookValue() * (Double.valueOf(remainingQuantity)/Double.valueOf(currentQuantity)));
                            arrInvestments.get(i).setBookValue(newBookValue); 
                        }
                        else{ //if quantity remains 0 or less, delete                
                            //tokenizing name
                            userName = arrInvestments.get(i).getName();
                            String[] splitName = userName.split(" ");
                            for(int j=0; j < splitName.length; j++){
                                tokenizedNames.add(splitName[j].toLowerCase()); //lowercase for better search
                            }
                            //removing keywords from hashmap
                            for(int j=0; j < tokenizedNames.size(); j++){
                                //if one index left in entry, delete entry
                                if(keywords.get(tokenizedNames.get(j)).size()==1){   
                                    deleted = keywords.get(tokenizedNames.get(j)).get(0);   //record deleted position
                                    keywords.remove(tokenizedNames.get(j));
                                }
                                else{   //more than one index left in entry, remove only desired index
                                    deleted = keywords.get(tokenizedNames.get(j)).get(i);   //record deleted position
                                    keywords.get(tokenizedNames.get(j)).remove(i);
                                }                                    
                            }

                            //update positions
                            Set<String> set = keywords.keySet();
                            for(String key : set){  //iterate through map
                                for(Integer value : keywords.get(key)){ //iterate through list positions
                                    if(value > deleted){
                                        int index = keywords.get(key).indexOf(value);
                                        keywords.get(key).set(index, value-1);  //decrement all index values greater than deleted value by one
                                    }
                                }
                            }
                            position--;
                            tokenizedNames.clear();                  
                            arrInvestments.remove(i); //deleting investment

                            //System.out.println(keywords);
                        }
                        text_output.setText("");
                        text_output.append("\n");
                        text_output.append(arrInvestments.toString().replace('[', ' ').replace(']', ' ').replace(',', ' ').trim());
                    }
                    break;
                }
            }
            //symbol not found
            if(foundInvestment == false){   
                text_output.append("You do not own this investment.");
            }
        }
    }
    /**
     * listener_update recieves action events from menu option itemUpdate
     */
    private class listener_update implements ActionListener {
        /**
         * This method is invoked when the menu update action occurs
         */
        public void actionPerformed(ActionEvent e) {
            j=0;
            controlPanel.removeAll();
            controlPanel.setLayout(null);
            //subtitle, reset, buy
            label_subtitle.setText("Updating investments");
            label_subtitle.setSize(200,30);
            label_subtitle.setLocation(5,0);
            button_prev.setSize(100,30);
            button_prev.setLocation(360,50);
            button_next.setSize(100,30);
            button_next.setLocation(360,100);
            button_save.setSize(100,30);
            button_save.setLocation(360,150);
            controlPanel.add(label_subtitle);
            controlPanel.add(button_prev);
            controlPanel.add(button_next);
            controlPanel.add(button_save);        
            //symbol
            label_symbol.setSize(200,25);
            label_symbol.setLocation(30,60);
            controlPanel.add(label_symbol);
            textbox_symbol.setSize(200,25);
            textbox_symbol.setLocation(100, 60);
            textbox_symbol.setEditable(false);
            controlPanel.add(textbox_symbol);
            //name
            label_name.setSize(200,25);
            label_name.setLocation(30,105);
            controlPanel.add(label_name);
            textbox_name.setSize(200,25);
            textbox_name.setLocation(100, 105);
            textbox_name.setEditable(false);
            controlPanel.add(textbox_name);
            //price
            label_price.setSize(200,25);
            label_price.setLocation(30,150);
            controlPanel.add(label_price);
            textbox_price.setSize(200,25);
            textbox_price.setLocation(100, 150);
            textbox_price.setText("");
            controlPanel.add(textbox_price);
            //output
            label_output.setText("Messages");
            label_output.setSize(200,25);
            label_output.setLocation(5,190);
            text_output.setSize(475,200);
            text_output.setLocation(5,225);
            text_output.setEditable(false);
            text_output.setLineWrap(true);
            text_output.setWrapStyleWord(true);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setSize(475,200);
            scroll.setLocation(5,225);
            controlPanel.add(scroll);
            controlPanel.add(label_output);

            //setting first investment as default
            if(j>=0){
                textbox_symbol.setText(arrInvestments.get(0).getSymbol());
                textbox_name.setText(arrInvestments.get(0).getName());
                button_prev.setVisible(false);
                button_next.setVisible(true);
            }
            controlPanel.repaint();
        }
    }
    /**
     * listener_button_prev recieves action events from button_prev
     */
    private class listener_button_prev implements ActionListener {
        /**
         * This method is invoked when the button prev action occurs
         */
        public void actionPerformed(ActionEvent e) {
            arrInvestments.trimToSize();
            textbox_price.setText("");
            if(j==0){
                button_prev.setVisible(false);
                button_next.setVisible(true);
                String currentSymbol = arrInvestments.get(j).getSymbol();
                String currentName = arrInvestments.get(j).getName();
                textbox_symbol.setText(currentSymbol);
                textbox_name.setText(currentName);
            }
            else{
                j--;
                if(j==0){
                    button_prev.setVisible(false);
                    button_next.setVisible(true);
                    String currentSymbol = arrInvestments.get(j).getSymbol();
                    String currentName = arrInvestments.get(j).getName();
                    textbox_symbol.setText(currentSymbol);
                    textbox_name.setText(currentName);
                }
                else{
                    button_prev.setVisible(true);
                    button_next.setVisible(true);
                    String currentSymbol = arrInvestments.get(j).getSymbol();
                    String currentName = arrInvestments.get(j).getName();
                    textbox_symbol.setText(currentSymbol);
                    textbox_name.setText(currentName);
                }
            }
        }
    }
    /**
     * listener_button_next recieves action events from button_next
     */
    private class listener_button_next implements ActionListener {
        /**
         * This method is invoked when the button next action occurs
         */
        public void actionPerformed(ActionEvent e) {
            arrInvestments.trimToSize();
            textbox_price.setText("");
            if(j==arrInvestments.size()-1){
                button_next.setVisible(false);
                button_prev.setVisible(true);
                String currentSymbol = arrInvestments.get(j).getSymbol();
                String currentName = arrInvestments.get(j).getName();
                textbox_symbol.setText(currentSymbol);
                textbox_name.setText(currentName);
            }
            else{
                j++;
                if(j==arrInvestments.size()-1){
                    button_next.setVisible(false);
                    button_prev.setVisible(true);
                    String currentSymbol = arrInvestments.get(j).getSymbol();
                    String currentName = arrInvestments.get(j).getName();
                    textbox_symbol.setText(currentSymbol);
                    textbox_name.setText(currentName);
                }
                else{
                    button_next.setVisible(true);
                    button_prev.setVisible(true);
                    String currentSymbol = arrInvestments.get(j).getSymbol();
                    String currentName = arrInvestments.get(j).getName();
                    textbox_symbol.setText(currentSymbol);
                    textbox_name.setText(currentName);   
                }
            }
        }
    }
    /**
     * listener_button_save recieves action events from button_save
     */
    private class listener_button_save implements ActionListener {
        /**
         * This method is invoked when the button save action occurs
         */
        public void actionPerformed(ActionEvent e) {
            Double newPrice = Double.parseDouble(textbox_price.getText());
            arrInvestments.get(j).setPrice(newPrice);
            text_output.append("\n\n");
            text_output.append(arrInvestments.toString().replace('[', ' ').replace(']', ' ').replace(',', ' ').trim());
        }
    }
    /**
     * listener_gain recieves action events from menu option itemGain
     */
    private class listener_gain implements ActionListener {
        /**
         * This method is invoked when the menu gain action occurs
         */
        public void actionPerformed(ActionEvent e) {
            controlPanel.removeAll();
            controlPanel.setLayout(null);
            //subtitle, reset, buy
            label_subtitle.setText("Getting total gain");
            label_subtitle.setSize(200,30);
            label_subtitle.setLocation(5,0);
            controlPanel.add(label_subtitle);
            //total gain
            label_totalGain.setSize(200,25);
            label_totalGain.setLocation(30,30);
            controlPanel.add(label_totalGain);
            textbox_totalGain.setSize(200,25);
            textbox_totalGain.setLocation(100, 30);
            textbox_totalGain.setEditable(false);
            controlPanel.add(textbox_totalGain);
            //output individual gains
            label_output.setText("Individual gains");
            label_output.setSize(200,25);
            label_output.setLocation(5,90);
            text_output.setSize(475,300);
            text_output.setLocation(5,120);
            text_output.setEditable(false);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setSize(475,300);
            scroll.setLocation(5,120);
            controlPanel.add(scroll);
            controlPanel.add(label_output);

            controlPanel.repaint();

            //sum up new gains for each investment to totalGain
            for(i=0; i<arrInvestments.size(); i++){
                totalGain = totalGain + arrInvestments.get(i).getGain();
            }
            textbox_totalGain.setText(Double.toString(totalGain));
            
            //individual gains
            for(i=0; i<arrInvestments.size(); i++){ 
                Double individualGain = arrInvestments.get(i).getGain();
                text_output.append("\n\n");
                text_output.append(arrInvestments.get(i).getName() + " " + arrInvestments.get(i).getSymbol() + " gain: ");
                text_output.append(Double.toString(individualGain));
            }
        }
    }
    /**
     * listener_search recieves action events from menu option itemSearch
     */
    private class listener_search implements ActionListener {
        /**
         * This method is invoked when the menu get total gain action occurs
         */
        public void actionPerformed(ActionEvent e) {
            controlPanel.removeAll();
            controlPanel.setLayout(null);
            //subtitle, reset, buy
            label_subtitle.setText("Searching investments");
            label_subtitle.setSize(200,30);
            label_subtitle.setLocation(5,0);
            button_reset.setSize(100,30);
            button_reset.setLocation(360,70);
            button_search.setSize(100,30);
            button_search.setLocation(360,110);
            controlPanel.add(label_subtitle);
            controlPanel.add(button_reset);
            controlPanel.add(button_search);
            //symbol
            label_symbol.setSize(200,25);
            label_symbol.setLocation(30,60);
            controlPanel.add(label_symbol);
            textbox_symbol.setSize(200,25);
            textbox_symbol.setLocation(130, 60);
            textbox_symbol.setEditable(true);
            textbox_symbol.setText("");
            controlPanel.add(textbox_symbol);
            //name keywords
            label_keywords.setSize(200,25);
            label_keywords.setLocation(30,90);
            controlPanel.add(label_keywords);
            textbox_keywords.setSize(200,25);
            textbox_keywords.setLocation(130, 90);
            textbox_keywords.setEditable(true);
            textbox_keywords.setText("");
            controlPanel.add(textbox_keywords);
            //low price
            label_lowPrice.setSize(200,25);
            label_lowPrice.setLocation(30,120);
            controlPanel.add(label_lowPrice);
            textbox_lowPrice.setSize(200,25);
            textbox_lowPrice.setLocation(130, 120);
            textbox_lowPrice.setEditable(true);
            textbox_lowPrice.setText("");
            controlPanel.add(textbox_lowPrice);
            //high price
            label_highPrice.setSize(200,25);
            label_highPrice.setLocation(30,150);
            controlPanel.add(label_highPrice);
            textbox_highPrice.setSize(200,25);
            textbox_highPrice.setLocation(130, 150);
            textbox_highPrice.setEditable(true);
            textbox_highPrice.setText("");
            controlPanel.add(textbox_highPrice);
            //output
            label_output.setText("Search results");
            label_output.setSize(200,25);
            label_output.setLocation(5,190);
            text_output.setSize(475,200);
            text_output.setLocation(5,225);
            text_output.setEditable(false);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setSize(475,200);
            scroll.setLocation(5,225);
            controlPanel.add(scroll);
            controlPanel.add(label_output);

            controlPanel.repaint();
        }
    }
    /**
     * listener_button_search recieves action events from button_search
     */
    private class listener_button_search implements ActionListener {
        /**
         * This method is invoked when the button search action occurs
         */
        public void actionPerformed(ActionEvent e) {
            searchSymbol = textbox_symbol.getText();
            searchKeywords = textbox_keywords.getText().split("[ ]+");
            searchLowPrice = textbox_lowPrice.getText();
            searchHighPrice = textbox_highPrice.getText();

            //HASHMAP SEARCH KEYWORDS
            if(!searchKeywords[0].isEmpty()){
                if(searchKeywords[0].isEmpty()){ //If left blank
                    foundKeyword=true;
                }
                else{
                    Map<String, List<Integer>> keywordsCopy = new HashMap<>();
                    Set<String> set = keywords.keySet();
                    for(int j=0; j<searchKeywords.length; j++){//loop through all search keywords
                        if(set.contains(searchKeywords[j])){    //only store matched keywords onto temporary map
                            keywordsCopy.put(searchKeywords[j], keywords.get(searchKeywords[j]));
                            foundKeyword=true;
                        }
                    }
                    //compare matched keywords to extract intersection 
                    Set<String> set2 = keywordsCopy.keySet();
                    Set<Integer> check = new HashSet<Integer>();
                    int size = 0;
                    int smallestSize = 10000; //assume a big size
                    for(String key : set2){  //iterate through map
                        size = keywordsCopy.get(key).size();   //getting size of smallest list
                        if(size < smallestSize){
                            smallestSize = size;
                            for(Integer value : keywords.get(key)){ //iterate through list positions
                                check.add(value);
                            }
                        }
                    }
                    for(String key : set2){ //find intersection 
                        keywordsCopy.get(key).retainAll(check);
                    }                 
                    //PRINTING MATCHED KEYWORD INVESTMENTS//
                    for(String key : set2){  //iterate through map
                        for(int j=0; j < smallestSize; j++){  
                            int p = keywordsCopy.get(key).get(j);
                            text_output.append("\n\n");
                            text_output.append("Matches found:");
                            text_output.append("\n");
                            text_output.append(arrInvestments.get(p).toString().replace('[', ' ').replace(']', ' ').replace(',', ' ').trim());
                        }
                        break;
                    }
                }
            }    
            
            else{
                //SEARCH SYMBOL AND PRICE//
                foundSymbol = false; //reset
                foundPrice = false; //reset
                for(i=0; i<arrInvestments.size(); i++){
                    //SEARCHING SYMBOL
                    if(searchSymbol.isEmpty()){ //If left blank
                        foundSymbol=true;
                    }
                    else{
                        String currentSymbol = arrInvestments.get(i).getSymbol();
                        foundSymbol = currentSymbol.equalsIgnoreCase(searchSymbol);
                    }   
                    //SEARCHING PRICES 
                    if(searchLowPrice.isEmpty()){  //if left blank
                        foundPrice = true;
                    }
                    else if(arrInvestments.get(i).getPrice() >= Double.parseDouble(searchLowPrice)){
                        foundPrice = true;
                    }
                    if(searchHighPrice.isEmpty()){  //if left blank
                        foundPrice = true;
                    }
                    else if(arrInvestments.get(i).getPrice() <= Double.parseDouble(searchHighPrice)){
                        foundPrice = true;
                    }

                    //PRINTING INVESTMENTS MATCHED
                    if(foundSymbol==true || foundPrice==true){ //if left blank or combinations of blank
                        text_output.append("\n\n");
                        text_output.append("Matches found:");
                        text_output.append("\n");
                        text_output.append(arrInvestments.get(i).toString().replace('[', ' ').replace(']', ' ').replace(',', ' ').trim());
                    }                             
                }               
            }      
        }
    }
    /**
     * listener_quit recieves action events from menu option itemQuit
     */
    private class listener_quit implements ActionListener {
        /**
         * This method is invoked when the menu quit action occurs
         */
        public void actionPerformed(ActionEvent e) {
           //exit program
           System.exit(0);
        }
     }
    /**
     * listener_button_reset recieves action events from button_reset
     */
    private class listener_button_reset implements ActionListener {
        /**
         * This method is invoked when the button reset action occurs
         */
        public void actionPerformed(ActionEvent e) {
            //reseting text fields and text area
            textbox_symbol.setText("");
            textbox_name.setText("");
            textbox_quantity.setText("");
            textbox_price.setText("");
            textbox_totalGain.setText("");
            textbox_highPrice.setText("");
            textbox_lowPrice.setText("");
            textbox_keywords.setText("");
        }
    }
    
    /**
    * This method is invoked when called from the main method to 
    * the initial interface.
    */
    private void prepareGUI(){
        //creating a frame window
        setTitle("ePortfolio");
        setSize(500, 500);
        setLayout(new GridLayout(1, 1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        instructionMessage.setText("Choose a command from the 'Commands' menu to buy or sell an investment, update prices for all investments, get gain for the portfolio, search for relevant investments, or quit the program.");
        instructionMessage.setWrapStyleWord(true);
        instructionMessage.setLineWrap(true);
        instructionMessage.setOpaque(false);
        instructionMessage.setEditable(false);
        instructionMessage.setFocusable(false);
        
        controlPanel.setLayout(new GridLayout(2, 1));
        controlPanel.add(welcomeLabel);
        controlPanel.add(instructionMessage);
        
        //adding items to menu
        menu.add(itemBuy);
        menu.add(itemSell);
        menu.add(itemUpdate);
        menu.add(itemGain);
        menu.add(itemSearch);
        menu.add(itemQuit);
        menuBar.add(menu);  //adding menu tab to menu bar
        setJMenuBar(menuBar);

        //adding actions to menu items
        itemBuy.addActionListener(new listener_buy());
        itemSell.addActionListener(new listener_sell());
        itemUpdate.addActionListener(new listener_update());
        itemGain.addActionListener(new listener_gain());
        itemSearch.addActionListener(new listener_search());
        itemQuit.addActionListener(new listener_quit());
        //adding actions to menu items/buttons
        button_buy.addActionListener(new listener_button_buy());
        button_sell.addActionListener(new listener_button_sell());
        button_reset.addActionListener(new listener_button_reset());
        button_prev.addActionListener(new listener_button_prev());
        button_next.addActionListener(new listener_button_next());
        button_save.addActionListener(new listener_button_save());
        button_search.addActionListener(new listener_button_search());

        add(controlPanel);
    }
    
    /**
     * This is the main method which creates the pop up window
     * @param args 
     */
    public static void main(String[] args) {     
        //creating pop up window
        Portfolio Portfolio = new Portfolio();
        Portfolio.setVisible(true);
    }
}

