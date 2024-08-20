package ra.run.view.admin;


import ra.run.Main;

import java.util.Scanner;

public class AdminMenu {
    static UserManagement userManagement = new UserManagement();
    static ProductManagement productManagement = new ProductManagement();
    static OrderManagement orderManagement = new OrderManagement();
    static CategoryManagement categoryManagement = new CategoryManagement();
    static Scanner sc = new Scanner(System.in);

    public static void adminMenu(){
        while (true) {
            System.out.println("----------ADMIN MENU----------");
            System.out.println("1. Quản lý người dùng");
            System.out.println("2. Quản lý sản phẩm");
            System.out.println("3. Quản lý danh mục");
            System.out.println("4. Quản lý hoá đơn");
            System.out.println("5. Đăng Xuất");
            System.out.print("Hãy nhập lựa chọn: ");
            byte choice = Byte.parseByte(sc.nextLine());
            switch (choice) {
                case 1:
                    userManagement.showUser();
                    break;
                case 2:
                    productManagement.showProduct();
                    break;
                case 3:
                    categoryManagement.showCategory();
                    break;
                case 4:
                    orderManagement.showOrder();
                    break;
                case 5:
                    System.out.println("Đăng xuất");
                    Main.BeginMenu();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại.");
            }
        }
    }
}

