/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author  Nguyễn Hải Linh
 */
public class Product implements Serializable{
    protected String code; // mã sp
    protected String name; // tên sp
    protected String type;
    protected int inventoryNumber = 0 ; // số lượng hàng trong kho

    public Product(String code, String name, String type) {
        this.code = code;
        this.name = name;
        this.type = type;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(int inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }
}
