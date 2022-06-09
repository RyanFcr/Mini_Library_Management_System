//package database;
//
//import database.book;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Scanner;


public class library {

    static Connection conn;

    public static void main(String args[]) {

        start("root", "123456");
    }

    public static void start(String userid, String passwd) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db1?characterEncoding=utf8&useSSL=true&serverTimezone=GMT",
                    userid, passwd);

            System.out.println("********************************************************");
            System.out.println("\t\t\tWelcome to my Library");
            System.out.println("********************************************************");

            Scanner reader = new Scanner(System.in);

            while (true) {
                System.out.println("1.query book 2.borrow book 3.return book 4.add book 5.delete book 0.exit");
                System.out.println("please input the number:");
                int choice = reader.nextInt();
                switch (choice) {
                    case 0:
                        conn.close();
                        return;
                    case 1:
                        check_Book();
                        break;
                    case 2:
                        borrow_Book();
                        break;
                    case 3:
                        return_Book();
                        break;
                    case 4:
                        add_Book();
                        break;
                    case 5:
                        delete_Book();
                        break;
                    default:
                        System.out.println("number false");
                }
            }
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
    }

    static void check_Book() throws SQLException {
        String query;//查询语句
        int choice = 0;//存放用户选项
        PreparedStatement stmt1;

        Scanner reader = new Scanner(System.in);

        while (true) {
            System.out.println("1.query all 2.query by title 3.query by bno 0.exit");
            System.out.println("");
            choice = reader.nextInt();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    //执行SQL语句
                    query = "SELECT * FROM book";

                    Statement stmt = conn.createStatement();
                    ResultSet rset = stmt.executeQuery(query);

                    System.out.println("Result as follows");
                    System.out.println("********************************************************************************");
                    System.out.println("bno\tcategory\ttitle\tpress\tyear\tauthor\tprice\ttotal\tstock");
                    System.out.println("********************************************************************************");

                    while (rset.next()) {
                        System.out.println(rset.getString("bno") + "\t" + rset.getString("category") +
                                "\t" + rset.getString("title") + "\t" + rset.getString("press") +
                                "\t" + rset.getInt("year") + "\t" + rset.getString("author") +
                                "\t" + rset.getInt("price") + "\t" + rset.getInt("total") + "\t" + rset.getInt("stock"));

                    }
                    stmt.close();

                    break;


                case 2:
                    //按书名查找
                    query = "SELECT * FROM book where title =?";
                    stmt1 = conn.prepareStatement(query);

                    System.out.println("Please input book title");
                    String name_ = reader.next();
                    stmt1.setString(1, name_);
                    ResultSet rset1 = stmt1.executeQuery();
                    while (rset1.next()) {
                        System.out.println(rset1.getInt("bno") + "\t" + rset1.getString("category") +
                                "\t" + rset1.getString("title") + "\t" + rset1.getString("press") +
                                "\t" + rset1.getInt("year") + "\t" + rset1.getString("author") +
                                "\t" + rset1.getInt("price") + "\t" + rset1.getInt("total") + "\t" + rset1.getInt("stock"));
                    }
                    stmt1.close();

                    break;


                case 3:
                    //按书号查询
                    query = "SELECT * FROM book where bno =?";
                    stmt1 = conn.prepareStatement(query);

                    System.out.println("Please input book bno");
                    int bno_ = reader.nextInt();
                    stmt1.setInt(1, bno_);
                    ResultSet rset2 = stmt1.executeQuery();
                    while (rset2.next()) {
                        System.out.println(rset2.getInt("bno") + "\t" + rset2.getString("category") +
                                "\t" + rset2.getString("title") + "\t" + rset2.getString("press") +
                                "\t" + rset2.getInt("year") + "\t" + rset2.getString("author") +
                                "\t" + rset2.getInt("price") + "\t" + rset2.getInt("total") + "\t" + rset2.getInt("stock"));
                    }
                    stmt1.close();

                    break;
                default:
                    System.out.println("number false");

            }
        }
    }

    static void borrow_Book() throws SQLException {
        System.out.println("input book title to borrow(0.exit)");

        String query;//查询语句
        String sql;//更新语句
        int choice = 0;//存放用户选项
        int stock_;
        PreparedStatement stmt;
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("1.input book title to borrow 0.exit");
            System.out.println("please input the number:");
            choice = reader.nextInt();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    query = "SELECT * FROM book where title =?";
                    stmt = conn.prepareStatement(query);

                    System.out.println("Please input the title");
                    String title = reader.next();
                    stmt.setString(1, title);
                    ResultSet rset = stmt.executeQuery();
                    if (rset.next()) {

                        stock_ = rset.getInt("stock");

                        boolean isBorrowed = (stock_ == 0);
                        if (isBorrowed) {
                            System.out.println("book has been borrowed");
                            break;
                        }
                    } else {
                        System.out.println("not exist");
                        break;
                    }
                    sql = "update book set stock = ? where title = ?";
                    PreparedStatement stmt2 = conn.prepareStatement(sql);

//                    System.out.println("请输入书名");
//                    String title_ = reader.next();

                    stmt2.setInt(1, stock_ - 1);
                    stmt2.setString(2, title);
                    int ret = stmt2.executeUpdate();
                    if (ret == 1) {
                        System.out.println("update finish");
                    } else {
                        System.out.println("update false");
                    }
                    break;
                default:
                    System.out.println("number false");

            }

        }
    }

    static void return_Book() throws SQLException {
        System.out.println("Please input book title(0 exit)");
        String query;//查询语句
        String sql;//更新语句
        int choice = 0;//存放用户选项
        int stock_;

        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("1.input book title to return 0.exit");
            System.out.println("please input the number:");
            choice = reader.nextInt();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    query = "SELECT * FROM book where title =?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    System.out.println("Please input book title");
                    String title = reader.next();
                    stmt.setString(1, title);
                    ResultSet rset = stmt.executeQuery();
                    if (rset.next()) {
                        stock_ = rset.getInt("stock");
                        boolean isBorrowed = (rset.getInt("stock") == rset.getInt("total"));
                        if (isBorrowed) {
                            System.out.println("not belong to us");
                            break;
                        }
                    } else {
                        System.out.println("not exist");
                        break;
                    }
                    sql = "update book set stock = ? where title = ?";
                    PreparedStatement stmt2 = conn.prepareStatement(sql);


//                    String title_ = reader.next();
                    stmt2.setInt(1, stock_ + 1);
                    stmt2.setString(2, title);
                    int ret = stmt2.executeUpdate();
                    if (ret == 1) {
                        System.out.println("update finish");
                    } else {
                        System.out.println("update failure");
                    }
                    break;
                default:
                    System.out.println("number false");
            }
        }
    }
    static void add_Book () throws SQLException {

        System.out.println("Please input book information(0 quit)");
        String sql;//插入语句
        int choice = 0;//存放用户选项

        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("1.add book 0.exit");
            System.out.println("please input the number:");
            choice = reader.nextInt();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    System.out.println("add book");
                    book book = new book();
                    System.out.println("Please input bno");
                    int bno_ = reader.nextInt();
                    book.setBno(bno_);
                    System.out.println("Please input category");
                    String category_ = reader.next();
                    book.setCategory(category_);
                    System.out.println("Please input title");
                    String title_ = reader.next();
                    book.setTitle(title_);
                    System.out.println("Please input press");
                    String press_ = reader.next();
                    book.setPress(press_);
                    System.out.println("Please input year");
                    int year_ = reader.nextInt();
                    book.setYear(year_);
                    System.out.println("Please input author");
                    String author_ = reader.next();
                    book.setAuthor(author_);
                    System.out.println("Please input price");
                    int price_ = reader.nextInt();
                    book.setPrice(price_);
                    System.out.println("Please input total");
                    int total_ = reader.nextInt();
                    book.setTotal(total_);
                    book.setStock(total_);
                    sql = "insert into book values(?,?,?,?,?,?,?,?,?)";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setInt(1, book.getBno());
                    statement.setString(2, book.getCategory());
                    statement.setString(3, book.getTitle());
                    statement.setString(4, book.getPress());
                    statement.setInt(5, book.getYear());
                    statement.setString(6, book.getAuthor());
                    statement.setInt(7, book.getPrice());
                    statement.setInt(8, book.getTotal());
                    statement.setInt(9, book.getStock());
                    book.toString();
                    int ret = statement.executeUpdate();
                    if (ret != 1) {
                        System.out.println("add failure");
                    } else {
                        System.out.println("add success");
                    }
                    break;
                default:
                    System.out.println("number false");


            }
        }
    }
    static void delete_Book () throws SQLException {
            System.out.println("Please input title(0 exit)");

            String sql;//删除语句
            int choice = 0;//存放用户选项

            Scanner reader = new Scanner(System.in);
            while (true) {
                System.out.println("1.please input title 0.exit");
                System.out.println("please input the number:");
                choice = reader.nextInt();
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        System.out.println("Please input book title");
                        String title_ = reader.next();
                        sql = "delete from book where title = ?";
                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.setString(1, title_);
                        int ret = statement.executeUpdate();
                        if (ret != 1) {
                            System.out.println("delete failure");
                            break;
                        } else {
                            System.out.println("delete success");
                            break;
                        }
                    default:
                        System.out.println("number false");
                }
            }
        }

}

