package com.company;


import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadExcelFileDemo
{
    private static String path = null;
    private static String filename = null;
    private static File file;
    private static FileReader filereader;
    private static CSVReader csvReader;
    private static int month = 0;
    private static int year = 0;
    private static HashMap<String, Double> fieldsValues = new HashMap<String, Double>();

    public ReadExcelFileDemo(int month, int year, String filename){
        this.filename = filename;
        this.month = month;
        this.year = year;
        /// Input filestream
        //obtaining input bytes from a file
       initialiseFile(filename);
    }

    private void initialiseFile(String filename){
        file = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"src"+System.getProperty("file.separator")+"main"+System.getProperty("file.separator")+"java"+System.getProperty("file.separator")+"testdata"+System.getProperty("file.separator")+filename);
        FileReader filereader = null;
        try {
            filereader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("File Not found : " + e);
            e.printStackTrace();
        }

        // create csvReader object passing
        // file reader as a parameter
         csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
    }

    public double calculatingMonthlyCharges() {
        List<String[]> allData = null;
        try {
            allData = csvReader.readAll();
        } catch (IOException e) {
            System.out.println("IOException occurred :" + e);
            e.printStackTrace();
        } catch (CsvException e) {
            System.out.println("CsvException occurred" + e);
            e.printStackTrace();
        }
        // we are going to read data line by line
        for (String[] row : allData) {
            if (!row[2].equalsIgnoreCase("Auto recharge for wallet")) {
                String outString = convertDateFormat(row[1].replaceAll("(?<=\\d)(st|nd|rd|th)\\b", ""));
                if (fieldsValues.containsKey(outString)) {
                    fieldsValues.put(outString, fieldsValues.get(outString) + Double.parseDouble(row[3]));
                } else {
                    fieldsValues.put(outString, Double.parseDouble(row[3]));
                }
            }
        }
        for (Map.Entry<String, Double> entry : fieldsValues.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println(String.format("%02d", month) + "-" + year);
        if (fieldsValues.get(String.format("%02d", month) + "-" + year) == null) {
            System.out.println("No data matching the month and year");
            return 0.00;
        } else {
            return fieldsValues.get(String.format("%02d", month) + "-" + year);
        }
    }

    private String convertDateFormat(String inDate){
        SimpleDateFormat inFormat = new SimpleDateFormat("MMM dd yyyy, hh:mm:ss a");
        Date date = null;
        try {
            date = inFormat.parse(inDate);
        } catch (ParseException e) {
            System.out.println("Unable to parse the date" + e);
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("MM-yyyy");
        String outString = outFormat.format(date);
        return outString;
    }

}