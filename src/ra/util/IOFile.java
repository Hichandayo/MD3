package ra.util;

import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile<T> {
    public static final String CUSTOMER_PATH = "src/ra/data/users.txt";
    public static final String CATEGORY_PATH = "src/ra/data/catalog.txt";
    public static final String PRODUCT_PATH = "src/ra/data/product.txt";
    public static final String CART_PATH = "src/ra/data/cart.txt";
    public static final String LIKE_PRODUCTS = "src/ra/data/likeProduct.txt";
    public static final String ORDER_PATH = "src/ra/data/order.txt";
    public static final String PLACE_PATH = "src/ra/data/place.txt";


   //đọc tệp tin
   public static <T> List<T> readFromFile(String filePath) throws IOException, ClassNotFoundException {

       FileInputStream fis = null;
       ObjectInputStream ois = null;
       try{
           fis = new FileInputStream(filePath);
           ois = new ObjectInputStream(fis);
           return (List<T>) ois.readObject();
       }catch (FileNotFoundException e){
           System.err.println("File không tồn tại");
           e.printStackTrace();
       }catch (EOFException e){
           System.err.println("Chưa Có File Nào, Hãy Thêm Vào!!");
           e.printStackTrace();
       } catch (IOException | ClassNotFoundException e){
           e.printStackTrace();
       }  finally {
           try{
               if (ois!=null){
                   ois.close();
               }
               if (fis!=null){
                   fis.close();
               }
           }catch (IOException e){
               e.printStackTrace();
           }
       }
       return new ArrayList<>();
   }
   //ghi tệp tin
   public static <T> void writeToFile(String filePath, List<T> objects) throws IOException {
       FileOutputStream fos = null;
       ObjectOutputStream oos = null;
       try{
           fos = new FileOutputStream(new File(filePath));
           oos = new ObjectOutputStream(fos);
           oos.writeObject(objects);
       }catch (IOException e){
           e.printStackTrace();
       }  finally {
           try{
               if (oos!=null){
                   oos.close();
               }
               if (fos!=null){
                   fos.close();
               }
           }catch (IOException e){
               e.printStackTrace();
           }
       }
   }
}
