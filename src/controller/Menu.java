/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


/**
 *
 * @author Nguyễn Hải Linh
 */
public class Menu {
        
    public void showMenuMain() {
        
        System.out.println("   ------MAIN MENU------");
        System.out.println("1. Manage products");
        System.out.println("2. Manage Warehouse");
        System.out.println("3. Report");
        System.out.println("4. Store data to files");
        System.out.println("5. Close the application");
    }

    public void showMenuProducts() {
        System.out.println("   ------PRODUCT MENU------");
        System.out.println("1.1  Add a product");
        System.out.println("1.2  Update product information");
        System.out.println("1.3  Delete product.");
        System.out.println("1.4  Show all product.");
        System.out.println("1.5  Back to Main Menu");
    }
    public void showMenuWarehouse() {
        System.out.println("   ------WAREHOUSE MENU------");
        System.out.println("2.1  Create an import receipt");
        System.out.println("2.2  Create an export receipt");
        System.out.println("2.3  Back to Main Menu");
    }
    public void showMenuReport() {
        System.out.println("   ------REPORT MENU------");
        System.out.println("3.1  Products that have expired");
        System.out.println("3.2  The products that the store is selling");
        System.out.println("3.3  Products that are running out of stock (sorted in ascending order)");
        System.out.println("3.4  Import/export receipt of a product.");
        System.out.println("3.5  Back to Main Menu");
    }
    public void showMenuStore() {
        System.out.println("   ------STORE MENU------");
        System.out.println("4.1  Store to file product.dat");
        System.out.println("4.2  Store to file warehouse.dat");
        System.out.println("4.3  Back to Main Menu");
    }
}
