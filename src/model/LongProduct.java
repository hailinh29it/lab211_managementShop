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
public class LongProduct extends Product {
    private String manuDate;
    private String expiredDate;

    public LongProduct(String code, String name, String type,String manuDate, String expiredDate) {
        super(code, name, type);
        this.manuDate = manuDate;
        this.expiredDate = expiredDate;
    }

    public String getManuDate() {
        return manuDate;
    }

    public void setManuDate(String manuDate) {
        this.manuDate = manuDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }
    
   
    @Override
    public String toString() {
        return String.format("|%-10s|%-10s|%-10s|%-10s|%-20s|%-20s|%-20d|",type,code,name,null,manuDate,expiredDate,inventoryNumber);
    }

   
    
}
