package com.casper.testDrivenDevelopment.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class  Book implements Serializable {
    private String title;
    private String press="";
    private String isbn="";
    private String authorNames="";
    private double price=0;
    private Date publishDay=null;
    private int coverResourceId;

    public Book(String title, int coverResourceId) {
        this.title=title;
        this.coverResourceId=coverResourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getAuthorNames() {
        return authorNames;
    }

    public void setListAuthors(String authorNames) {
        this.authorNames = authorNames;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getPublishDay() {
        return publishDay;
    }

    public void setPublishDay(Date publishDay) {
        this.publishDay = publishDay;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }

    public void setCoverResourceId(int coverResourceId) {
        this.coverResourceId = coverResourceId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
