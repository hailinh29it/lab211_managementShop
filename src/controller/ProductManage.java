package controller;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.DailyProduct;
import model.LongProduct;
import model.Order;
import model.Product;
import myutils.myToys;

/**
 * @author Nguyễn Hải Linh
 */
public class ProductManage {

    List<Product> listProduct = new ArrayList();
    List<Order> listOrder = new ArrayList();
    String fileProductName = "product.dat";
    String fileOrderName = "warehouse.dat";
//1

    public Product inputProduct(String status) {
        // tạo type sẽ dễ quản lý hơn
        // Nhập type hoặc kiểm tra nếu đang trong trạng thái update
        String typeProduct = "";
        // nếu trạng thái update thì khỏi nhập type, chỉ add mới nhập thôi
        if (!(status.equals("update"))) { // add
            while (true) {
                typeProduct = myToys.checkType("Input type of Product (DAILY/LONG): ", status).trim().toUpperCase();
                if (typeProduct.equals("DAILY") || typeProduct.equals("LONG")) {
                    break;
                } else {
                    System.out.println("--Invalid type! Please enter DAILY or LONG.");
                }
            }
        } // nhưng mà update hay add thì vẫn phải nhập code ,name
        String codeProduct = "";
        while (true) {
            codeProduct = myToys.checkString("Input code of Product: ", status).trim().toUpperCase();
            // Kiểm tra xem code đã tồn tại trong danh sách sản phẩm hay chưa
            if (!checkDuplicated(codeProduct)) {
                break;
            } else {
                System.out.println("--Code already exists! Please enter a different code.");
            }
        }
        String nameProduct = "";
        while (true) {
            nameProduct = myToys.checkString("Input name of Product: ", status).trim().toUpperCase();
            // Kiểm tra xem name đã tồn tại trong danh sách sản phẩm hay chưa
            if (!checkDuplicated(nameProduct)) {
                break;
            } else {
                System.out.println("--Name already exists! Please enter a different name.");
            }
        }

        if (typeProduct.equals("DAILY") && !status.equals("update")) {
            String sizeProduct = "";
            while (true) {
                sizeProduct = myToys.checkString("Input size of Product (S/M/L): ", status).trim().toUpperCase();
                if (sizeProduct.equals("S") || sizeProduct.equals("M") || sizeProduct.equals("L")) {
                    break;
                } else {
                    System.out.println("--Invalid size! Please enter S, M, or L.");
                }
            }
            return new DailyProduct(codeProduct, nameProduct, sizeProduct, typeProduct);
        } else {
            if (status.equals("update")) {
                // trạng thái cập nhật-> không cần nhập ngày sản xuất và hết hạn lại
                // vì lúc add sp đã ktra rồi
                // sử dụng sản phẩm cũ cho việc cập nhật.
                return new LongProduct(codeProduct, nameProduct, typeProduct, "", "");
            } else { //add
                while (true) {
                    String manuProduct = myToys.checkString("Input manufacturing date: ", status);
                    String expiredProduct = myToys.checkString("Input expired date: ", status);
                    if (myToys.checkDateValid(manuProduct) && myToys.checkDateValid(expiredProduct) && myToys.compareDates(manuProduct, expiredProduct)) {
                        return new LongProduct(codeProduct, nameProduct, typeProduct, manuProduct, expiredProduct);
                    } else {
                        System.out.println("--Date wrong input again! ");
                    }
                }
            }
        }
    }
    // tìm sp = code

    public Product getProductbyCode(String code) {
        code = code.trim().toUpperCase();
        for (int i = 0; i < listProduct.size(); i++) {
            if (listProduct.get(i).getCode().equals(code)) {
                return listProduct.get(i);
            }
        }
        return null;
    }

    public boolean checkDuplicated(String ID) {

        for (int i = 0; i < listProduct.size(); i++) {
            if (ID.toUpperCase().equals(listProduct.get(i).getCode())) {
                return true;
            }
        }
        for (int i = 0; i < listProduct.size(); i++) {
            if (ID.toUpperCase().equals(listProduct.get(i).getName())) {
                return true;
            }
        }
        return false;
    }

    public void updateProduct(Product oldProduct) {
        // type k update
        Product newProduct = inputProduct("update");
        System.out.println("--Before Update");
        System.out.println("|Type      |Code      |Name      |Size      |Manufactoring Date  |Expiration Date     |Inventory Number    |");
        System.out.println(oldProduct.toString());
        // code
        String oldCode = oldProduct.getCode();
        String newCode = newProduct.getCode();
        if (!newCode.equals("")) {
            oldProduct.setCode(newCode);
        }

        // name
        String oldName = oldProduct.getName();
        String newName = newProduct.getName();
        if (!newName.equals("")) {
            oldProduct.setName(newName);
        }
        // size / nếu daily thì cần update size 
        if (oldProduct instanceof DailyProduct && newProduct instanceof DailyProduct) {
            String oldSize = ((DailyProduct) oldProduct).getSize();
            String newSize = ((DailyProduct) newProduct).getSize();
            if (!newSize.equals("")) {
                ((DailyProduct) oldProduct).setSize(newSize);
            }

        } else if (oldProduct instanceof LongProduct && newProduct instanceof LongProduct) {
            String oldManu = ((LongProduct) oldProduct).getManuDate();
            String newManu = ((LongProduct) newProduct).getManuDate();
            if (!newManu.equals("")) {
                ((LongProduct) oldProduct).setManuDate(newManu);
            }

            String oldExpired = ((LongProduct) oldProduct).getExpiredDate();
            String newExpired = ((LongProduct) newProduct).getExpiredDate();
            if (!newExpired.equals("")) {
                ((LongProduct) oldProduct).setExpiredDate(newExpired);
            }
        }

        System.out.println("--After updating");
        System.out.println("|Type      |Code      |Name      |Size      |Manufactoring Date  |Expiration Date     |Inventory Number    |");
        System.out.println(oldProduct.toString());
    }

    //1.1
    public void addProduct() {
        while (true) {
            Product new_Product = inputProduct("add");
            listProduct.add(new_Product);
            System.out.println("--ADD SUCCESSFUL--");
            String choice = myToys.checkString("Do you want to add more Product? ( Yes/No )", "add").trim().toUpperCase();
            if (!choice.trim().toUpperCase().equals("YES")) {
                break;
            }
        }
    }

    //1.2
    public void updateProductMain() {
        while (true) {
            String code = myToys.checkString("Input code you want to Update: ", "update");
            Product old_product = getProductbyCode(code);
            if (old_product == null) {
                System.out.println("--Product does not exsit! Please input again!");
            } else {
                updateProduct(old_product);
                break;
            }
        }
        System.out.println("--Update successful");
    }
    // nếu sản phẫm đả xuất hiện trong biên lai thì ko dc delete

    //1.3
    public void deleteProduct() {
        while (true) {
            String code = myToys.checkString("Input code to delete Product", "delete");
            Product product = getProductbyCode(code); // tìm thông tin sp hiện có trong shop
            Product productInReceipt = getProductWareHouse(code); // sp đã có trong biên lai thì ko dc delete
            if (product == null) {
                System.out.println("--Product does NOT exsit!");
                System.out.println("--Remove fail");
            }
            if (productInReceipt != null) {
                System.out.println("--Product exsited in Receipt! Can NOT remove!");
            } else {
                listProduct.remove(product);
                System.out.println("--Remove succesfull");
            }
            String choice = myToys.checkString("Do you want to continue delete Product? ( Yes/No )", "add").trim().toUpperCase();
            if (!choice.trim().toUpperCase().equals("YES")) {
                break;
            }

        }
    }
//1.4

    public void showCollection() {
        if (listProduct.isEmpty()) {
            System.out.println("--List is Empty can't Show");
        } else {
            System.out.println("|Type      |Code      |Name      |Size      |Manufactoring Date  |Expiration Date     |Inventory Number    |");
            for (Product product : listProduct) {
                System.out.println(product.toString());
            }
        }
    }

    // mã số code tự tăng, đảm bảo rằng nó là độc nhất ( dùng chung cho các biên lai )
    // dùng chung cho và đồng nhất tăng lên khi mỗi biên lai dc tạo
    static int self_IncrementingCode = 1;

    //2
    public Product getProductWareHouse(String code) {
        code = code.trim().toUpperCase();
        // duyệt ds các biên lai
        for (Order order : listOrder) {
            if (order.getListItem().containsKey(code)) { // duyệt coi biên lai đã có 
                // mã sp đấy chưa
                // nếu có rồi thì duyệt qua list để trã về sp
                for (Product product : listProduct) {
                    if (product.getCode().equals(code)) {
                        return product;
                    }
                }
            }

        }
        return null; // ko có trã về null
    }

    //  2.1  // biên lai NHẬP
    public void creatImportReceipt() {
        createReceipt("import");
     
    }

    // 2.2  // biên lai XUẤT // BIÊN LAI VÍ DỤ NHẬP MÀ NHẬP SAI CODE NÓ SẼ BREAK;
     // ĐIỀU NÀY LÀM  INCRE++. KHI NÀO TẠO ĐƯỢC BIÊN LAI MỚI ++/ FIX LỖI NÀY
    public void creatExportReceipt() {
        createReceipt("export");
       
    }

    public void createReceipt(String receiptType) {
        // Tạo biên lai
        String codeImport = String.format("%07d", self_IncrementingCode);
        String orderDate = myToys.DateInSystem("dd-MM-yyyy");
        Order order = new Order(codeImport, receiptType, orderDate);

        // Nhập sản phẩm
        String codeItem = myToys.checkString("Input CODE of Item: ", "import").trim().toUpperCase();
        Product product = getProductbyCode(codeItem); // lấy sản phẩm trog shop
        // nếu sản phẩm có trong shop mới tạo đc biên lai
        if (product != null) { // Sản phẩm đã tồn tại trong kho -> có thể tạo receipt
            System.out.println("Code: " + product.getCode() + " Product exists in warehouse");
            int quantity;
            // kiểm tra type receipt nó là import hay export
            if ("import".equals(receiptType)) {
                quantity = myToys.getInt("Input Quantity to receipt: ", 0);
                // sau đó set số lượng kho // vì là nhập nên +
                product.setInventoryNumber(quantity + product.getInventoryNumber());
                // nếu nó là xuất kho
            } else if ("export".equals(receiptType)) {
                quantity = myToys.getInt("Input Quantity EXPORT to receipt: ", 0);
                // kiểm tra số lượng xuất nếu xuất to hơn sl kho thì ko xuất dc
                if (quantity > product.getInventoryNumber()) {
                    System.out.println("--Export Fail"); // đưa ra thông báo
                    System.out.println("--Number export larger than inventory number!!");
                    return; // kết thúc nếu sl xuất ko hợp lệ
                }
                // nếu hợp lệ thì set sl kho = kho - xuất
                product.setInventoryNumber(product.getInventoryNumber() - quantity);
            } else {
                System.out.println("--Invalid receipt type");
                return; // kết thúc nếu type ko  hợp lệ
            }
            // .put để gán giá trị cho hasmap  vối key là codeItem và giá trị là sl kho Inventory
            order.getListItem().put(codeItem, product.getInventoryNumber());

            // sau cùng là thêm biên lai vào ds biên lai
            listOrder.add(order);
            self_IncrementingCode++; // hoàn thành biên lai thì tăng
            System.out.println("--Add Receipt successfull!");
        } else {// đưa ra thông báo vì ko có product trong kho
            System.out.println("--Receipt Fail");
            System.out.println("--Product does not exist in warehouse");
        }
    }
// 5
// đưa dữ liệu xuống file (bao gồm product.dat và warehouse.dat)
//5.1

    public void storeData() {
        //lưu product list xuống file product.dat 
        if (listProduct.isEmpty()) {
            System.out.println("--Empty list! Can't Store data in product.dat");
            return;
        }
        try {
            // mở 1 tệp tin đễ ghi
            FileOutputStream f = new FileOutputStream(fileProductName);
            ObjectOutputStream fo = new ObjectOutputStream(f); // tạo đối tượng để ghi các đối tượng java xuống
            for (Product product : listProduct) {
                fo.writeObject(product); // ghi từng sp vào
            }
            fo.close(); // đóng
            f.close(); // đóng
            System.out.println("--Product.dat file saved! "); // thông báo
        } catch (IOException e) {
            System.out.println(e);
        }
             //  lưu order list xuống warehouse.dat file
        if (listOrder.isEmpty()) {
            System.out.println("--Empty Order list. Can't store in warhouse.dat ");
            return;
        }
        try {// mở 1 tệp tin đễ ghi
            FileOutputStream f = new FileOutputStream(fileOrderName);
            ObjectOutputStream fo = new ObjectOutputStream(f);// tạo đối tượng để ghi các đối tượng java xuống
            for (Order order : listOrder) {
                fo.writeObject(order); // ghi từng order trong list order
            }
            fo.close();
            f.close(); // đóng
            System.out.println("--Warehouse.dat file saved!"); // đưa ra thông báo
        } catch (IOException e) {
            System.out.println(e);
        }
    }
 // nên gộp lại là vì khi tạo sp chưa có inven khi ta lưu vào product.dat
 // inven =0, và khi nhập xuất rồi ta lưu vào warehouse.dat thì lưu inven
 // nhưng chưa cập nhật lại product.dat nên khi ta kthuc ctrinh. và chạy lại
 // ta chọn show product list thì nó load lên nhma load cái cũ nên inven bị = 0 hết!
    
    
//5.2

//    public void storeDataWarehouse() {
//        //lưu order list xuống warehouse.dat file
//        if (listOrder.isEmpty()) {
//            System.out.println("--Empty Order list. Can't store in warhouse.dat ");
//            return;
//        }
//        try {// mở 1 tệp tin đễ ghi
//            FileOutputStream f = new FileOutputStream(fileOrderName);
//            ObjectOutputStream fo = new ObjectOutputStream(f);// tạo đối tượng để ghi các đối tượng java xuống
//            for (Order order : listOrder) {
//                fo.writeObject(order); // ghi từng order trong list order
//            }
//            fo.close();
//            f.close(); // đóng
//            System.out.println("--Warehouse.dat file saved!"); // đưa ra thông báo
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }

    //tải dữ liệu trong file lên
    public void loadProductFromFile() {
        if (listProduct.size() > 0) {
            listProduct.clear();
        } // nếu trong list có phần tử  thì clear đi. để ds sẽ trống
        // trước khi load dữ liệu từ file lên để tránh bị chồng lên dữ lịu cũ.

        try {
            File f = new File(fileProductName); // tạo đối tượng file  f 
            //rồi tham chiếu đến tệp có tên fileProductName
            if (!f.exists()) {
                return;
            }
            FileInputStream fi = new FileInputStream(f);// tạo luồn đầu vào để đọc dữ liệu từ tệp f( đọc các đối tượng từ tệp tin)
            ObjectInputStream oi = new ObjectInputStream(fi);// tạo đối tượng để đọc đối tượng từ luồn đầu vào ( đối tượng Product)
            Product product;
            while (true) {
                try {
                    product = (Product) oi.readObject(); // đọc đối tượng và éo kiểu nó về đối tượng product
                    listProduct.add(product); // gán vào list
                } catch (EOFException e) {
                    break; // kết thúc vòng lặp khi gặp exception
                }
            }
            oi.close();
            fi.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadOrderFromFile() {
        if (listOrder.size() > 0) {
            listOrder.clear();
        }

        try {
            File f = new File(fileOrderName);
            if (!f.exists()) {
                return;
            }
            FileInputStream fs = new FileInputStream(f); // tạo luồn đầu vào để đọc dữ liệu từ tệp f( đọc các đối tượng từ tệp tin)
            ObjectInputStream oi = new ObjectInputStream(fs); // tạo đối tượng để đọc đối tượng từ luồn đầu vào ( đối tượng Order)
            Order order;
            while (true) {
                try {
                    order = (Order) oi.readObject();// đọc đối tượng và éo kiểu nó về đối tượng order
                    listOrder.add(order);
                } catch (EOFException e) {
                    break; // kết thúc vòng lặp khi gặp exception
                }
            }
            oi.close();
            fs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
// 4
//4.1

    public void ShowProductExpired() {
        // tạo 1 list r gán bởi list trả về với trạng thái là hết hạn
        List<Product> product_expired = CompareDate(listProduct, "expired");
        System.out.println("|Type      |Code      |Name      |Size      |Manufactoring Date  |Expiration Date     |Inventory Number    |");
        // duyệt list rồi in ra
        for (int i = 0; i < product_expired.size(); i++) {
            System.out.println(product_expired.get(i).toString());
        }
    }
//4.2

    public void showProductSelling() {
        // tạo 1 list r gán bởi list trả về với trạng thái là đang bán (selling)
        List<Product> product_selling = CompareDate(listProduct, "selling");
        System.out.println("|Type      |Code      |Name      |Size      |Manufactoring Date  |Expiration Date     |Inventory Number    |");
        // sau khi có list rồi thì duyệt từng product trong list r in ra
        for (int i = 0; i < product_selling.size(); i++) {
            System.out.println(product_selling.get(i).toString());
        }
    }
//4.3

    public void showProductRunningOutOfStock() {
        // show các sản phẩm trong listproduct sắp hết hàng 0 < inventory < = 3
        // gán list bởi result
        List<Product> product = ProductRunningOutOfStock();
        // dùng comparator, đưa data type là 1 object Product 

        Comparator<Product> comparator = (product1, product2) // đưa 2 object vào so sánh
                // kiểu int ( lấy sl kho product 1 - sl kho product 2)
                -> Integer.compare(product1.getInventoryNumber(), product2.getInventoryNumber());
        Collections.sort(product, comparator);
        // sau khi sort thì in ra list
        System.out.println("|Type      |Code      |Name      |Size      |Manufactoring Date  |Expiration Date     |Inventory Number    |");
        for (int i = 0; i < product.size(); i++) {
            System.out.println(product.get(i).toString());
        }
    }

    public List<Product> ProductRunningOutOfStock() {
        // tạo list 
        List<Product> result = new ArrayList(); // ds của result
        // duyệt list product trả về  sl < = 3
        for (int i = 0; i < listProduct.size(); i++) {
            if (!(listProduct.get(i).getInventoryNumber() > 3) && listProduct.get(i).getInventoryNumber() > 0) {
                result.add(listProduct.get(i)); // thỏa dk  0 < sl < = 3 thì add
            }
        }
        return result; // trả về
    }

    public List<Product> CompareDate(List<Product> listproduct, String status) {
        // new list để trả về list mong mún
        List<Product> result = new ArrayList<>();
        // lay ra ngay thang nam hiện hành
        int dayNow = Integer.parseInt(myToys.DateInSystem("dd").trim());
        int monthNow = Integer.parseInt(myToys.DateInSystem("MM").trim());
        int yearNow = Integer.parseInt(myToys.DateInSystem("yyyy").trim());
        // lấy ra ngày tháng  năm hết hạn
        // kiểm tra coi trạng thái có phải là ngày hết hạn hay ko ( để điều hướng)
        if (status.equals("expired")) { // nếu cần tìm list hết hạn
            for (int i = 0; i < listproduct.size(); i++) {
                // duyệt list và kiểm tra nếu nó là longproduct vì chỉ long product ms có nsx và hsd
                if (listproduct.get(i) instanceof LongProduct) {
                    // lấy chuỗi ngày hết hạn
                    String long_product = ((LongProduct) listproduct.get(i)).getExpiredDate().trim();
                    // đưa về mảng để quản lý ngày tháng năm tương ứng
                    String date[] = long_product.split("[-]");
                    int dayExpired = Integer.parseInt(date[0]);
                    int monthExpired = Integer.parseInt(date[1]);
                    int yearExpired = Integer.parseInt(date[2]);
                    // kiểm tra từng ngày tháng năm với ngày tháng năm hiện hành
                    if (yearNow > yearExpired
                            || (yearNow == yearExpired && monthNow > monthExpired)
                            || (yearNow == yearExpired && monthNow == monthExpired && dayNow > dayExpired)) {
                        result.add(listproduct.get(i)); // thỏa dk trã về list hết hạn
                    }
                }
            }
            return result; // còn ko thì return list ( trả về list rỗng vì ko có longproduct nào xuất hiện cả) 
        } else { // trường hợp selling ( vì đưa thẳng expired rồi nên else luôn)
            for (int i = 0; i < listproduct.size(); i++) {
                // tương tự duyệt coi hsd còn dùng dc ko( ngược vs cái kia)
                if (listproduct.get(i) instanceof LongProduct && listproduct.get(i).getInventoryNumber() > 0) {
                    String long_product = ((LongProduct) listproduct.get(i)).getExpiredDate().trim();
                    String date[] = long_product.split("[-]");
                    int dayExpired = Integer.parseInt(date[0]);
                    int monthExpired = Integer.parseInt(date[1]);
                    int yearExpired = Integer.parseInt(date[2]);

                    if (yearNow > yearExpired
                            || (yearNow == yearExpired && monthNow > monthExpired)
                            || (yearNow == yearExpired && monthNow == monthExpired && dayNow > dayExpired)) {
                        //result_expired.add(listproduct.get(i));
                    } else {
                        // do ngược nên else mới add
                        result.add(listproduct.get(i));
                    }
                }
            }
            return result; // ko thược case nào thì return list rỗng
        }
    }
// 4.4

    public void ImportorExportReceipt() {
        // show tất cả biên lai trước đó của MỘT  product
        // nhập code của product bạn mún in ra biên lai
        String productCode = myToys.checkString("Input the product code: ", "receipt");
        Product product = getProductWareHouse(productCode); // lấy sp từ kho
        if (product == null) { // kiểm tra
            System.out.println("--Product does not exist!");
        } else {  // nếu có r thì duyệt từng biên lai trong ds biên lai chứa code product
            for (Order order : listOrder) {
                if (order.getListItem().containsKey(product.getCode())) {
                    order.showOrder();
                }
            }
        }
    }
}
