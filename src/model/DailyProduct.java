/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author  Nguyễn Hải Linh
 */
public class DailyProduct extends Product{
     private String size;

    public DailyProduct(String code, String name,String size, String type) {
        super(code, name, type);
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
     
    
    @Override
    public String toString() {
        return String.format("|%-10s|%-10s|%-10s|%-10s|%-20s|%-20s|%-20d|",type,code,name,size,null,null,inventoryNumber);
    }

  
}
