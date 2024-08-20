package ra.run.view.users;

import ra.entity.Order;
import ra.entity.Product;
import ra.services.OrderServiceImpl;
import ra.services.ProductServiceImpl;
import ra.util.InputMethods;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ra.run.Main.clearUserLoggedIn;
import static ra.util.IOFile.LIKE_PRODUCTS;
import static ra.util.IOFile.*;

public class UserMenu {
    static Home home = new Home();
    static Cart cart = new Cart();
    static FeedBack feedBack = new FeedBack();
    static UserInfo userInfo = new UserInfo();
    static OrderServiceImpl orderService = new OrderServiceImpl();
    static ProductServiceImpl productService = new ProductServiceImpl();
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    private static int getCurrentUserId() {
        return 1;
    }

    public void userMenu() throws IOException, ClassNotFoundException {
        while (true) {
        System.out.println(ANSI_PURPLE + "╔═════════════════════════════════════╗" + ANSI_RESET);
        System.out.println(ANSI_PURPLE +"║ Welcome to Hi-chan Shop             ║"+ ANSI_RESET);
        System.out.println(ANSI_PURPLE + "╠═════════════════════════════════════╣" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    1. Trang chủ " + ANSI_RESET + ANSI_PURPLE + "                    ║" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    2. Giỏ hàng" + ANSI_RESET + ANSI_PURPLE + "                      ║" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    3. Trang liên hệ" + ANSI_RESET + ANSI_PURPLE + "                 ║" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    4. Thông tin người dùng" + ANSI_RESET + ANSI_PURPLE + "          ║" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    5. Lịch sử mua hàng" + ANSI_RESET + ANSI_PURPLE + "              ║" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    6. Thêm vào yêu thích" + ANSI_RESET + ANSI_PURPLE + "            ║" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    7. Tìm kiếm sản phẩm theo giá" + ANSI_RESET + ANSI_PURPLE + "    ║" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    8. Sản phẩm theo danh mục" + ANSI_RESET + ANSI_PURPLE + "        ║" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    9. Đăng Xuất" + ANSI_RESET + ANSI_PURPLE + "                     ║" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "╚ " + ANSI_RESET + ANSI_CYAN + "  Nhập lựa chọn ⭣ ⭣ ⭣" + ANSI_RESET + ANSI_PURPLE + "              ═╝" + ANSI_RESET);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    home.home();
                    break;
                case 2:
                    cart.showCart();
                    break;
                case 3:
                    feedBack.feedBack();
                    break;
                case 4:
                    userInfo.UserInfo();
                    break;
                case 5:
                    orderHistory();
                    break;
                case 6:
                    addToFavorites();
                    break;
                case 7:
                    viewProductsByPriceRange();
                    break;
                case 8:
                    viewProductsByCategory();
                    break;
                case 9:
                    logout();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    public void logout() {
        try {
            clearUserLoggedIn();
            System.out.println("Đăng xuất thành công!");
        } catch (IOException e) {
            System.err.println("Không thể thực hiện đăng xuất. Chi tiết: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void orderHistory() {
        List<Order> orders = loadOrdersFromFile(PLACE_PATH);

        if (orders.isEmpty()) {
            System.out.println("Bạn chưa có đơn hàng nào.");
        } else {
            System.out.println("Lịch sử mua hàng:");
            for (Order order : orders) {
                System.out.println(order.toStringForOrderHistory());
            }
        }
    }


    private static List<Order> loadOrdersFromFile(String filePath) {
        List<Order> orders = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 4) { // Đảm bảo có đủ phần
                    try {
                        // Phân tích dữ liệu dựa trên định dạng file cung cấp
                        String orderId = parts[0].split(": ")[1];
                        String productName = parts[1].split(": ")[1];
                        double price = Double.parseDouble(parts[2].split(": ")[1]);
                        int quantity = Integer.parseInt(parts[3].split(": ")[1]);

                        // Tạo đối tượng Product
                        Product product = new Product();
                        product.setProductName(productName);
                        product.setProductPrice(price);
                        product.setQuantity(quantity);

                        // Tạo đối tượng Order
                        List<Product> products = new ArrayList<>();
                        products.add(product);


                        String userId = "";
                        double totalAmount = price * quantity;
                        LocalDate date = LocalDate.now();
                        boolean status = false;
                        boolean delivered = false;

                        Order order = new Order(orderId, userId, products, totalAmount, date, status, delivered);
                        orders.add(order);
                    } catch (Exception e) {
                        System.err.println("Dữ liệu không hợp lệ trong tệp đơn hàng: " + line);
                    }
                } else {
                    System.err.println("Dữ liệu không hợp lệ trong tệp đơn hàng: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc tệp đơn hàng: " + e.getMessage());
        }

        return orders;
    }

    public static void addToFavorites() {
        List<Product> productList = productService.getAll();
        System.out.println("Danh sách sản phẩm:");
        for (Product product : productList) {
            System.out.println(product);
        }

        System.out.print("Nhập ID sản phẩm để thêm vào danh sách yêu thích: ");
        int productId = InputMethods.getInteger();

        Product product = productService.getProductById(productId);
        if (product != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LIKE_PRODUCTS, true))) {
                writer.write(product.getProductId() + ": " + product.getProductName()); // Ví dụ định dạng ghi vào tệp
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Có lỗi xảy ra khi lưu sản phẩm yêu thích: " + e.getMessage());
            }
            System.out.println("Sản phẩm đã được thêm vào danh sách yêu thích.");
        } else {
            System.out.println("Sản phẩm không tồn tại.");
        }
    }



    public static void viewProductsByPriceRange() {
        double minPrice = Double.parseDouble(InputMethods.getString());
        double maxPrice = Double.parseDouble(InputMethods.getString());
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào trong khoảng giá này.");
        } else {
            System.out.println("Sản phẩm trong khoảng giá:");
            products.forEach(System.out::println);
        }
    }

    public static void viewProductsByCategory() {
        System.out.print("Nhập ID danh mục để xem sản phẩm: ");
        int categoryId = InputMethods.getInteger();
        List<Product> products = productService.getProductsByCategory(categoryId);
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào thuộc danh mục này.");
        } else {
            System.out.println("Sản phẩm theo danh mục:");
            products.forEach(System.out::println);
        }
    }
}
