/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author  Nguyễn Hải Linh
 */
public class myToys  {

    private static Scanner sc = new Scanner(System.in);
    // msg: yêu cầu mún nhập
    // trạng thể để phân biệt trường hợp add hay update ( để bỏ qua thông tin ko mún update)
    public static String checkString(String msg, String status) {
        // vong lap su dung de nguoi dung nhap den khi dung 
        while (true) {
            System.out.println(msg);
            // allow user input a string 
            String input_raw = sc.nextLine();
            if (status.equals("update") && input_raw.trim().isEmpty()) {
                return input_raw;
            }
            // input == null or do dai = 0 => rong 
            if (input_raw == null || input_raw.length() == 0) {
                // error
                System.err.println("Must input a string not empty !!!");
                System.out.println("Please enter again!");
                continue;
            }
            return input_raw;
        }
    }
                     // kiểm tra ngày tháng năm coi hợp lệ ko   
    public static boolean checkDateValid(String date) {
        // 10-12-2023
     
               String[] arrayOfdate = date.trim().split("[-]"); // 10 12 2023
        int day = Integer.parseInt(arrayOfdate[0]);
        int month = Integer.parseInt(arrayOfdate[1]);
        int year = Integer.parseInt(arrayOfdate[2]);
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            if (month == 2) {
                if (day < 1 || day > 29) {
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            if (month == 2) {
                if (day < 1 || day > 28) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month < 1 || month > 12 || day < 1 || day > daysInMonth[month]) {
            return false;
        } else {
            return true;
        }
      
      

    }
    // nhập int với min là giá trị nhỏ nhất
    public static int getInt(String msg, int min) {
        boolean check = true;
        int number = 0;
        do {
            try {
                System.out.print(msg);
                number = Integer.parseInt(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be larger min!");
                } else {
                    check = false;
                }
            } catch (Exception e) {
                System.out.println("Please Input Number!!! ");
            }

        } while (check || number < min);
        return number;
    }
    // kiểm tra coi nó daily hay long để loại trường hợp nếu bạn muốn update
    // thì bạn đâu cần nhập lại type
    public static String checkType(String msg, String status) {
        while(true){
            String type = checkString(msg,status).trim().toUpperCase();
            if(type.trim().isEmpty()){
                return type;
            }
            if(!(type.equals("DAILY")) && !(type.equals("LONG")) ){
                System.err.println("Input type of product is 'Daily' or 'Long' ! Please input again !");
                continue;
            }
            
            return type;
            
        }
    }
 
    public static String DateInSystem(String msg){
        Date date = new Date(); // tạo một ngày giờ hệ thống 
        // định dạng ngày-tháng-năm 
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");  
        // định dạng ngày
        SimpleDateFormat formatDay = new SimpleDateFormat("dd");
        // định dạng tháng
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
        // đạnh dạng năm
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        
        String strDate = formatDate.format(date);
        String strDay = formatDay.format(date);
        String strMonth = formatMonth.format(date);
        String strYear = formatYear.format(date);
        
        if(msg.equals("dd-MM-yyyy")){
            return strDate;
        }
        else if (msg.equals("dd")){
            return strDay;
    }
        else if(msg.equals("MM")){
            return strMonth;
    }
        else if(msg.equals("yyyy")){
            return strYear;
        }
       return "";
    }
    public static boolean compareDates(String manufacturingDate, String expirationDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date manuDate = dateFormat.parse(manufacturingDate);
            Date expDate = dateFormat.parse(expirationDate);
            // So sánh ngày sản xuất và ngày hết hạn
            return expDate.after(manuDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Trong trường hợp xảy ra lỗi khi phân tích ngày
        }
}
}
