package com.example.entity;

import jakarta.persistence.*;

@Entity
public class EntityBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column
    String title;

    @Column
    String content;

    @Column
    String author;

    @Column
    String date;

    @Column
    int userid;
    
    public EntityBoard(){
        this.id = -1;
        this.title = "";
        this.content = "";
        this.author = "";
        this.date = "";
        this.userid = 1;
    }

    public EntityBoard(int id, String Title, String Content, String Author, String Date, int Userid){
        super();
        this.id = id;
        this.title = Title;
        this.content = Content;
        this.author = Author;
        this.date = Date;
        this.userid = Userid;
    }
    @Override
    public String toString() {
        return "EntityBoard{" +
                "Title='" + title + '\'' +
                ", id=" + id +
                ", Content='" + content + '\'' +
                ", Author='" + author + '\'' +
                ", Date='" + date + '\'' +
                ", Userid='" + userid + '\'' +
                '}';
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String Title) {
        title = Title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String Content) {
        content = Content;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String Author) {
        author = Author;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String Date) {
        date = Date;
    }
    public int getUserid() {
        return userid;
    }
    public void setUserid(int Userid) {
        userid = Userid;
    }
}
