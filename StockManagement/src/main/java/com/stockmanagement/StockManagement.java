/*
* @author Atul Srivastav
* @date 01/01/2022
* Stock Management System with buy and sell functionality.
* */
package com.stockmanagement;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.text.ParseException;
import java.util.Scanner;

public class StockManagement {
    //making object of scanner and JSONArray
    public static Scanner sc = new Scanner(System.in);
    public static JSONArray stockList = new JSONArray();
    public static JSONParser jsonParser = new JSONParser();

    //main method
    public static void main(String[] args) {
        System.out.println("********* Stock Management *********");
        getInputFromUser(); //calling menu options method
    }
    /*
    *   getInputFromUser method to give menu options.
    *   user will enter his option.
    *   on basis of particular option, methods will be called.
    * */
    private static void getInputFromUser(){
        JSONArray jsonArray=new JSONArray();
        try {
            jsonArray = (JSONArray) jsonParser.parse(new FileReader("src/data/stock.json"));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println("Which operation do you want to perform ?\n1.Add Stock \n2.Print stock report \n3.Buy Stock \n4.Sell Stock \n5.Credit \n6.Debit \n7.View Balance \n8.Exit");
        int ch = sc.nextInt();
        switch (ch){
            case 1:
                addStock();
                break;
            case 2:
                printStock();
                break;
            case 3:
                StockAccount.operations(1,jsonArray);
                break;
            case 4:
                StockAccount.operations(2,jsonArray);
                break;
            case 5:
                System.out.println("Enter amount to credit.");
                int amt=sc.nextInt();
                StockAccount.credit(amt);
                break;
            case 6:
                System.out.println("Enter amount to debit.");
                int amtDbt=sc.nextInt();
                StockAccount.debit(amtDbt);
                break;
            case 7:
                StockAccount.valueOf();
                break;
            case 8:
                System.exit(0);
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
    //print stock method to print all stock details.
    public static void printStock() {
        System.out.println("***** print stock *******");

        try {
            //reading json file.
            JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("src/data/stock.json"));
            for (int i =0; i < jsonArray.size();i++)
            {
                System.out.printf("******** Stock %s ********\n",i+1);
                JSONObject obj = (JSONObject) jsonArray.get(i);
                String name = (String) obj.get("name");
                long shares = (long) obj.get("no_of_shares");
                Double price = (Double) obj.get("price");
                System.out.println("Stock Name : " +name);
                System.out.println("Number of Shares : " +shares);
                System.out.println("Stock price : " +price);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            System.out.println("File IO Exception");
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        getInputFromUser();
    }
    /*
    *   addStock method to add a new stock.
    *   takes stock details.
    *   writes to the json file.
     */
    public static void addStock() {
        System.out.println("******* Add stock *******");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Stock Name : ");
        String stockName = sc.nextLine();
        System.out.println("Enter number of shares: ");
        int noOfShares = sc.nextInt();
        System.out.println("Enter share price: ");
        double sharePrice = sc.nextDouble();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", stockName); // Assiging the accepted value to the JSON Object
        jsonObject.put("no_of_shares", noOfShares);
        jsonObject.put("price", sharePrice);
        stockList.add(jsonObject); // Adding the JSON Object into the JSON Array..
        try {
            //writing new stock details to the stock.json file.
            FileWriter file = new FileWriter("src/data/stock.json");
            file.write(stockList.toJSONString());
            file.close();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("added: "+jsonObject);
        getInputFromUser();
    }
}