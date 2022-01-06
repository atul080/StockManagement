/*
* @author Atul Srivastav
* @date 01/01/2022
* Stock Management System with buy and sell functionality.
* */
package com.stockmanagement;

import org.json.simple.JSONArray;

import java.io.FileReader;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.text.ParseException;
import java.util.Scanner;

public class StockAccount {
    public static double amount=50000;
    public static Scanner sc=new Scanner(System.in);
    public static JSONObject jsonObject = new JSONObject();
    public static void valueOf()
    {
        System.out.println(amount);
    }

    public static void credit(double balance)
    {
        amount=amount+balance;
        System.out.println("Balance crdeited.");
    }

    public static void debit(double balance)
    {
        if(amount<balance)
        {
            System.out.println("In sufficient balance.");
        }
        else
        {
            amount=amount-balance;
            System.out.println("Balance debited.");
        }
    }
    public static void operations(int opt,JSONArray js)
    {
        System.out.println("******** Select stock from below to buy  : ********\n");

        for (int i =0; i < js.size();i++) {
            JSONObject obj = (JSONObject) js.get(i);
            String name = (String) obj.get("name");
            System.out.println(i+1+" "+name);
        }
        int option=sc.nextInt();
        JSONObject obj = (JSONObject) js.get(option-1);
        double price = (double) obj.get("price");
        obj = (JSONObject) js.get(option-1);
        long shares=(long)obj.get("no_of_shares");
        if(opt==1)
            shares=buy(shares,price);
        else
            shares=sell(shares,price);
        obj.put("no_of_shares", shares);
        js.remove(option-1);
        js.add(option-1,obj);
        try {
            //writing new stock details to the stock.json file.
            FileWriter file = new FileWriter("src/data/stock.json");
            file.write(js.toJSONString());
            file.close();
            System.out.println("Stock bought.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long buy(long shares,double price)
    {
        if(amount>price)
        {
            shares=shares+1;
            debit(price);
        }
        return shares;
    }


    public static long sell(long shares,double price)
    {
        shares=shares-1;
        credit(price);
        return shares;
    }

}




