package com.example.libraryapp.data;

public class Member {
    private int id;
    private String name;
    private String passwd;
    private String date;
    private int cnt;

    public Member(int id, String name, String passwd, String date, int cnt) {
        this.id = id;
        this.name = name;
        this.passwd = passwd;
        this.date = date;
        this.cnt = cnt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
