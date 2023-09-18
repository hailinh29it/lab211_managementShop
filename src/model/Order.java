/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author  Nguyễn Hải Linh
 */
public class Order implements Serializable{
     private String OrderCode; // ma xuat nhap khau 7 chữ số
    // String mã sản phẫm / Integer là số lượng sản phẩm
    private HashMap<String, Integer> listItem = new HashMap<>();
    private String type; // nhap hay xuat
    private String oderDate; // ngay tao don hang 

    public Order(String OrderCode, String type, String oderDate) {
        this.OrderCode = OrderCode;
        //this.listItem = listItem;
        this.type = type;
        this.oderDate = oderDate;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String OrderCode) {
        this.OrderCode = OrderCode;
    }

    public HashMap<String, Integer> getListItem() {
        return listItem;
    }

    public void setListItem(HashMap<String, Integer> listItem) {
        this.listItem = listItem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOderDate() {
        return oderDate;
    }

    public void setOderDate(String oderDate) {
        this.oderDate = oderDate;
    }
    public void showOrder() {
        System.out.println("|Type:     |Order Code:         |Order Date:         |Code Product:       |Inventory           |");
        System.out.printf("|%-10s|%-20s|%-20s",type,OrderCode,oderDate); 
        for (String key : listItem.keySet()) {
            int inventory =  listItem.get(key);
            System.out.printf("|%-20s|%-20d|",key,inventory);
        }
        System.out.println("");
    }
     

}

