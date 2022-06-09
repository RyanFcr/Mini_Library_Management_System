//package database;
public class book {
    private  int bno;

    private String category;
    private  String title;
    private String press;
    private int year;
    private String author;
    private int price;//单位是分
    private int total;
    private  int stock;//剩余

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "book{" +
                "bno=" + bno +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", press=" + press +'\'' +
                ", year=" + year +'\'' +
                ", author=" + author +'\'' +
                ", price=" + price +'\'' +
                ", total='" + total + '\'' +
                ", stock=" + stock +
                '}';
    }
}