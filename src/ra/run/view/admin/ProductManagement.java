package ra.run.view.admin;

import ra.entity.Category;
import ra.entity.Product;
import ra.services.CategoryServiceImpl;
import ra.util.IOFile;
import ra.util.InputMethods;
import ra.util.validate.ProductValidation;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ProductManagement {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    static CategoryServiceImpl categoryService = new CategoryServiceImpl();
    public static void showProduct() {
        while (true) {
            System.out.println(ANSI_PURPLE + "╔════════════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║        Welcome to Hi-chan Shop             ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "╠════════════════════════════════════════════╣" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    1. Hiển thị sản phẩm " + ANSI_RESET + ANSI_PURPLE + "               ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    2. Thêm sản phẩm" + ANSI_RESET + ANSI_PURPLE + "                    ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    3. Sửa sản phẩm" + ANSI_RESET + ANSI_PURPLE + "                     ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    4. Xóa sản phẩm" + ANSI_RESET + ANSI_PURPLE + "                     ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    5. Tìm kiếm sản phẩm theo tên" + ANSI_RESET + ANSI_PURPLE + "       ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    6. Hiển thị sản phẩm theo danh mục" + ANSI_RESET + ANSI_PURPLE + "  ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    7. Quay Lại" + ANSI_RESET + ANSI_PURPLE + "                         ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "╚ " + ANSI_RESET + ANSI_CYAN + "  Nhập lựa chọn ⭣ ⭣ ⭣" + ANSI_RESET + ANSI_PURPLE + "                 ═╝" + ANSI_RESET);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    displayProduct();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    searchProductByName();
                    break;
                case 6:
                    displayProductByCategory();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Nhập sai vui lòng nhập lại");
            }
        }
    }
    public static void displayProduct() {
        IOFile<Product> productIO = new IOFile<>();
        try {
            List<Product> products = productIO.readFromFile(IOFile.PRODUCT_PATH);
            if (products.isEmpty()) {
                System.out.println("Danh sách sản phẩm trống.");
            } else {
                System.out.println("Danh sách sản phẩm:");
                for (Product product : products) {
                    System.out.println(product);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi đọc dữ liệu sản phẩm: " + e.getMessage());
        }
    }

    public static void addProduct() {
        try {
            List<Product> products = IOFile.readFromFile(IOFile.PRODUCT_PATH);
            List<Category> categories = categoryService.getAll();
            System.out.print("Bạn muốn thêm bao nhiêu sản phẩm : ");
            int numberOfProducts;
            while(true){
                try{
                    numberOfProducts = Integer.parseInt(InputMethods.getString().trim());
                    if (numberOfProducts > 0) {
                        break;
                    }else{
                        System.out.println("Số lượng phải lớn hơn 0. Vui lòng nhập lại.");
                    }
                }catch (NumberFormatException e){
                    System.out.println("Vui lòng nhập một số nguyên hợp lệ.");
                }
            }
            for (int i = 0; i < numberOfProducts; i++) {
                String name;
                while(true){
                    System.out.println(ANSI_PURPLE + "Nhập tên sản phẩm " + (i+1) + ": " + ANSI_RESET);
                    name = InputMethods.getString().trim();
                    if (!name.isEmpty()){
                        if (!ProductValidation.isValidCategoryName(name)){
                            System.out.println("không được nhập mỗi số vào tên sản phẩm. Vui Lòng Nhập Lại!!");
                        }else{
                            break;
                        }
                    }else {
                        System.out.println("Tên danh mục không được để trống.");
                    }
                }

                while(true){
                    System.out.println(ANSI_PURPLE + "Nhập mô tả sản phẩm : " + ANSI_RESET);
                    String productDes = InputMethods.getString().trim();
                    if (productDes.isEmpty()){
                        System.out.println("Mô tả không được trống. Vui lòng nhập lại!!");
                    }if (!ProductValidation.isValidProductDescription(productDes)){
                        System.out.println("Mô tả phải có ít nhất 1 ký tự (Không phải khoảng trắng) : ");
                    }else {
                        break;
                    }
                }

                while(true){
                    System.out.println(ANSI_PURPLE + "Nhập giá sản phẩm: " + ANSI_RESET);
                    String productPrice = InputMethods.getString().trim();
                    if (productPrice.isEmpty()){
                        System.out.println("giá sản phẩm không được trống. Vui lòng nhập lại!!");
                    }if(!ProductValidation.isValidProductPrice(productPrice)){
                        System.out.println("giá sản phẩm phải lớn hơn 1000. Vui lòng nhập lại!!");
                    }else{
                        break;
                    }
                }

                while(true){
                    System.out.println(ANSI_PURPLE + "Nhập số lượng sản phẩm: " + ANSI_RESET);
                    int quantity = Integer.parseInt(InputMethods.getString());
                    if(!ProductValidation.isValidProductQuantity(String.valueOf(quantity))){
                        System.out.println("Số lượng không hợp lệ. Vui lòng nhập lại.");
                    }else{
                        break;
                    }
                }
                // hiển thị danh mục
                System.out.println("Chọn danh mục cho sản phẩm:");
                for (int n = 0; n < categories.size(); n++) {
                    System.out.println((n + 1) + ". " + categories.get(n).getCategoryName());
                }
                // nhập danh mục
                System.out.print("Nhập số tương ứng với danh mục: ");
                int categoryChoice = Integer.parseInt(InputMethods.getString());
                if (categoryChoice < 1 || categoryChoice > categories.size()) {
                    System.out.println("Lựa chọn không hợp lệ.");
                    return;
                }
                Category selectedCategory = categories.get(categoryChoice - 1);

                Product newProduct = new Product();
                products.add(newProduct);
            }

            IOFile.writeToFile(IOFile.PRODUCT_PATH, products);
            System.out.println("Sản phẩm đã được thêm thành công!");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Dữ liệu nhập vào không hợp lệ. Vui lòng nhập lại.");
        }
    }



    public static void updateProduct() {
        IOFile<Product> productIO = new IOFile<>();
        try {
            List<Product> products = productIO.readFromFile(IOFile.PRODUCT_PATH);
            if (products.isEmpty()) {
                System.out.println("Danh sách sản phẩm trống.");
                return;
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập mã sản phẩm cần sửa: ");
            String inputId = scanner.nextLine();
            int id;
            try {
                id = Integer.parseInt(inputId);
            } catch (NumberFormatException e) {
                System.out.println("Mã sản phẩm không hợp lệ. Vui lòng nhập lại.");
                return;
            }
            Product productToUpdate = null;
            for (Product product : products) {
                if (product.getProductId() == id) {
                    productToUpdate = product;
                    break;
                }
            }
            if (productToUpdate == null) {
                System.out.println("Không tìm thấy sản phẩm với mã: " + id);
                return;
            }
            System.out.print("Nhập tên sản phẩm mới (hiện tại: " + productToUpdate.getProductName() + "): ");
            String name = scanner.nextLine().trim();
            System.out.print("Nhập giá sản phẩm mới (hiện tại: " + productToUpdate.getProductPrice() + "): ");
            double price = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Nhập số lượng sản phẩm mới (hiện tại: " + productToUpdate.getQuantity() + "): ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());
            List<Category> categories = categoryService.getAll();
            System.out.println("Chọn danh mục mới cho sản phẩm (danh mục hiện tại: " + productToUpdate.getCategory().getCategoryName() + "):");
            for (int i = 0; i < categories.size(); i++) {
                if (!categories.get(i).getCategoryName().equals(productToUpdate.getCategory().getCategoryName())) {
                    System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
                }
            }
            System.out.print("Nhập số tương ứng với danh mục mới: ");
            int categoryChoice = Integer.parseInt(scanner.nextLine().trim());
            if (categoryChoice < 1 || categoryChoice > categories.size()) {
                System.out.println("Lựa chọn không hợp lệ.");
                return;
            }
            Category selectedCategory = categories.get(categoryChoice - 1);
            productToUpdate.setProductName(name);
            productToUpdate.setProductPrice(price);
            productToUpdate.setQuantity(quantity);
            productToUpdate.setCategory(selectedCategory);
            productIO.writeToFile(IOFile.PRODUCT_PATH, products);
            System.out.println("Sản phẩm đã được cập nhật thành công!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Dữ liệu nhập vào không hợp lệ. Vui lòng nhập lại.");
        }
    }


    private static Category getCategoryByName(String categoryName) {
        IOFile<Category> categoryIO = new IOFile<>();
        try {
            List<Category> categories = categoryIO.readFromFile(IOFile.CATEGORY_PATH);
            return categories.stream()
                    .filter(cat -> cat.getCategoryName().equalsIgnoreCase(categoryName))
                    .findFirst()
                    .orElse(null);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi đọc danh mục: " + e.getMessage());
            return null;
        }
    }


    public static void deleteProduct() {
        IOFile<Product> productIO = new IOFile<>();
        try {
            // Read the list of products from the file
            List<Product> products = productIO.readFromFile(IOFile.PRODUCT_PATH);
            if (products.isEmpty()) {
                System.out.println("Danh sách sản phẩm trống.");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập mã sản phẩm cần xóa: ");
            // Read the product ID from the user input
            String inputId = scanner.nextLine();
            int id;
            try {
                // Convert the input to an integer
                id = Integer.parseInt(inputId);
            } catch (NumberFormatException e) {
                System.out.println("Mã sản phẩm không hợp lệ. Vui lòng nhập lại.");
                return;
            }

            Product productToDelete = null;
            for (Product product : products) {
                // Compare the product ID using primitive int comparison
                if (product.getProductId() == id) {
                    productToDelete = product;
                    break;
                }
            }
            if (productToDelete == null) {
                System.out.println("Không tìm thấy sản phẩm với mã: " + id);
                return;
            }

            // Remove the product from the list and write the updated list to the file
            products.remove(productToDelete);
            productIO.writeToFile(IOFile.PRODUCT_PATH, products);
            System.out.println("Sản phẩm đã được xóa thành công!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
    }


    public static void searchProductByName() {
        IOFile<Product> productIO = new IOFile<>();
        try {
            List<Product> products = productIO.readFromFile(IOFile.PRODUCT_PATH);
            if (products.isEmpty()) {
                System.out.println("Danh sách sản phẩm trống.");
                return;
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập tên sản phẩm cần tìm: ");
            String name = scanner.nextLine().trim().toLowerCase();
            boolean found = false;
            for (Product product : products) {
                if (product.getProductName().toLowerCase().contains(name)) {
                    System.out.println("Tìm thấy sản phẩm:");
                    System.out.println(product);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Không tìm thấy sản phẩm với tên: " + name);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
        }
    }


    public static void displayProductByCategory() {
        IOFile<Product> productIO = new IOFile<>();
        try {
            List<Product> products = productIO.readFromFile(IOFile.PRODUCT_PATH);
            if (products.isEmpty()) {
                System.out.println("Danh sách sản phẩm trống.");
                return;
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập danh mục sản phẩm cần xem: ");
            String categoryName = scanner.nextLine().trim();
            boolean found = false;
            System.out.println("Sản phẩm theo danh mục " + categoryName + ":");
            for (Product product : products) {
                if (product.getCategory().getCategoryName().equalsIgnoreCase(categoryName)) {
                    System.out.println(product);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Không tìm thấy sản phẩm với danh mục: " + categoryName);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi hiển thị sản phẩm theo danh mục: " + e.getMessage());
        }
    }


}