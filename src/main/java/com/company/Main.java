package com.company;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter month with values from 1 to 12");
        int month = myObj.nextInt();
        System.out.println("Username is: " + month);

        System.out.println("Enter year with values from 2000 to 2999");
        int  year = myObj.nextInt();
        System.out.println("Username is: " + year);

        String filename = "1689676107570-transaction-logs.csv";
        if((month > 0 && month < 13) && (year > 2000 && year < 2999)){
            ReadExcelFileDemo calculateMonthlyCharges = new ReadExcelFileDemo(month,year,filename);
            System.out.println("Monthly Charges for the month of "+month+" and year"+ year+ "is " +calculateMonthlyCharges.calculatingMonthlyCharges());
        }else{
            System.out.println("Given month/year is not valid");
        }
    }
}
