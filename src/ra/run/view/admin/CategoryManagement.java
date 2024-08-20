package ra.run.view.admin;

import ra.entity.Category;
import ra.entity.Product;
import ra.util.IOFile;
import ra.util.InputMethods;
import ra.util.validate.ProductValidation;

import java.io.IOException;
import java.util.List;

public class CategoryManagement {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static void showCategory() {
        while (true) {
            System.out.println("1. Hiển thị danh mục");
            System.out.println("2. Thêm danh mục");
            System.out.println("3. Sửa danh mục");
            System.out.println("4. Xóa danh mục");
            System.out.println("5. Tìm kiếm danh mục theo tên");
            System.out.println("6. Hiển thị sản phẩm theo danh mục");
            System.out.println("7. Quay Lại");
            System.out.print("Hãy nhập lựa chọn: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    displayCategory();
                    break;
                case 2:
                    addCategory();
                    break;
                case 3:
                    updateCategory();
                    break;
                case 4:
                    deleteCategory();
                    break;
                case 5:
                    searchCategoryByName();
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

    public static void displayCategory() {
        IOFile<Category> categoryIO = new IOFile<>();
        try {
            List<Category> categories = categoryIO.readFromFile(IOFile.CATEGORY_PATH);
            if (categories.isEmpty()) {
                System.out.println("Danh sách danh mục trống.");
            } else {
                for (Category category : categories) {
                    System.out.println(category);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi hiển thị danh mục: " + e.getMessage());
        }
    }

    public static void addCategory() {
        try {
            List<Category> categories = IOFile.readFromFile(IOFile.CATEGORY_PATH);
            System.out.print("Bạn muốn thêm bao nhiêu loại danh mục : ");
            int numberOfCategories;
            while (true) {
                try {
                    numberOfCategories = Integer.parseInt(InputMethods.getString().trim());
                    if (numberOfCategories > 0) {
                        break;
                    } else {
                        System.out.println("Số lượng phải lớn hơn 0. Vui lòng nhập lại.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Vui lòng nhập một số nguyên hợp lệ.");
                }
            }
            for (int i = 0; i < numberOfCategories; i++) {
                String name;
                while (true) {
                    System.out.print("Nhập tên danh mục " + (i + 1) + ":");
                    name = InputMethods.getString().trim();
                    if (!name.isEmpty()) {
                        String finalName = name;
                        boolean exists = categories.stream().anyMatch(c -> c.getCategoryName().equalsIgnoreCase(finalName));
                        if (exists) {
                            System.out.println("Danh mục đã tồn tại. Vui lòng nhập tên khác.");
                        }if (!ProductValidation.isValidCategoryName(name)){
                            System.out.println("không được nhập mỗi số vào tên danh mục. Vui Lòng Nhập Lại!!");
                        }
                        else {
                            break;
                        }
                    } else {
                        System.out.println("Tên danh mục không được để trống.");
                    }
                }
                Category newCategory = new Category(name);
                categories.add(newCategory);
            }
            // Lưu danh sách danh mục sau khi người dùng hoàn thành việc nhập
            IOFile.writeToFile(IOFile.CATEGORY_PATH, categories);
            System.out.println("Tất cả danh mục đã được lưu.");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi thêm danh mục: " + e.getMessage());
        }
    }


    public static void updateCategory() {
        IOFile<Category> categoryIO = new IOFile<>();
        try {
            List<Category> categories = categoryIO.readFromFile(IOFile.CATEGORY_PATH);
            if (categories.isEmpty()) {
                System.out.println("Danh sách danh mục trống.");
                return;
            }

            System.out.print("Nhập mã danh mục cần sửa: ");
            String idString = InputMethods.getString().trim();
            int id;
            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                System.out.println("Mã danh mục không hợp lệ.");
                return;
            }

            Category categoryToUpdate = null;
            for (Category category : categories) {
                if (category.getCategoryId() == id) {
                    categoryToUpdate = category;
                    break;
                }
            }
            if (categoryToUpdate == null) {
                System.out.println("Không tìm thấy danh mục với mã: " + id);
                return;
            }

            System.out.print("Nhập tên danh mục mới (hiện tại: " + categoryToUpdate.getCategoryName() + "): ");
            String name = InputMethods.getString().trim();

            categoryToUpdate.setCategoryName(name);
            categoryIO.writeToFile(IOFile.CATEGORY_PATH, categories);
            System.out.println("Danh mục đã được cập nhật thành công!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi cập nhật danh mục: " + e.getMessage());
        }
    }


    public static void deleteCategory() {
        IOFile<Category> categoryIO = new IOFile<>();
        try {
            List<Category> categories = categoryIO.readFromFile(IOFile.CATEGORY_PATH);
            if (categories.isEmpty()) {
                System.out.println("Danh sách danh mục trống.");
                return;
            }

            System.out.print("Nhập mã danh mục cần xóa: ");
            String idString = InputMethods.getString().trim();
            int id;
            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                System.out.println("Mã danh mục không hợp lệ.");
                return;
            }

            Category categoryToDelete = null;
            for (Category category : categories) {
                if (category.getCategoryId() == id) {
                    categoryToDelete = category;
                    break;
                }
            }
            if (categoryToDelete == null) {
                System.out.println("Không tìm thấy danh mục với mã: " + id);
                return;
            }

            categories.remove(categoryToDelete);
            categoryIO.writeToFile(IOFile.CATEGORY_PATH, categories);
            System.out.println("Danh mục đã được xóa thành công!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi xóa danh mục: " + e.getMessage());
        }
    }


    public static void searchCategoryByName() {
        IOFile<Category> categoryIO = new IOFile<>();
        try {
            List<Category> categories = categoryIO.readFromFile(IOFile.CATEGORY_PATH);
            if (categories.isEmpty()) {
                System.out.println("Danh sách danh mục trống.");
                return;
            }

            System.out.print("Nhập tên danh mục cần tìm: ");
            String name = InputMethods.getString().trim();

            boolean found = false;
            for (Category category : categories) {
                if (category.getCategoryName().contains(name)) {
                    System.out.println(category);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Không tìm thấy danh mục với tên: " + name);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi tìm kiếm danh mục: " + e.getMessage());
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

            System.out.print("Nhập tên danh mục để hiển thị sản phẩm: ");
            String category = InputMethods.getString().trim();

            boolean found = false;
            for (Product product : products) {
                if (product.getCategory().equals(category)) {
                    System.out.println(product);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Không có sản phẩm trong danh mục: " + category);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi hiển thị sản phẩm theo danh mục: " + e.getMessage());
        }
    }

}
