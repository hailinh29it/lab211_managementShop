/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Menu;
import controller.ProductManage;
import java.util.Scanner;
import myutils.myToys;

/**
 *
 * @author Nguyễn Hải Linh
 */
public class DemoApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();
        ProductManage Manage = new ProductManage();
        Manage.loadOrderFromFile(); // mỗi lần chạy là load lên
        Manage.loadProductFromFile();
        boolean checkLoop = true;
        while (checkLoop) {
            menu.showMenuMain();
            int choice = myToys.getInt("Input your choice: ", 1);
            switch (choice) {
                case 1:
                    menu.showMenuProducts();
                    int choice_product = myToys.getInt("Input your choice: ", 1);
                    switch (choice_product) {
                        case 1:
                            Manage.addProduct();
                            break;
                        case 2:
                            Manage.updateProductMain();
                            break;
                        case 3:
                            Manage.deleteProduct();
                            break;
                        case 4:
                            Manage.showCollection();
                            break;
                        case 5:
                            break;
                    }
                    break;
                case 2:
                    menu.showMenuWarehouse();
                    int choice_warehouse = myToys.getInt("Input your choice: ", 1);
                    switch (choice_warehouse) {
                        case 1:
                            Manage.creatImportReceipt();
                            break;
                        case 2:
                            Manage.creatExportReceipt();
                            break;
                        case 3:
                            break;

                    }
                    break;
                case 3:
                    menu.showMenuReport();
                    int choice_report = myToys.getInt("Input your choice: ", 1);
                    switch (choice_report) {
                        case 1:
                            Manage.ShowProductExpired();
                            break;
                        case 2:
                            Manage.showProductSelling();
                            break;
                        case 3:
                            Manage.showProductRunningOutOfStock();
                            break;
                        case 4:
                            Manage.ImportorExportReceipt();
                            break;
                        case 5:
                            break;
                    }

                    break;
                case 4:
                    menu.showMenuStore();
                    int choice_store = myToys.getInt("Input your choice: ", 1);
                    switch (choice_store) {
                        case 1:
                            Manage.storeData();
                            break;
                        case 2:
                            Manage.storeData();
                            break;
                        case 3:
                            break;
                    }
                    break;
                case 5:
                    checkLoop = false;
                    break;
            }
        }
        System.out.println("   ------  EXITED  ------");

    }
}
