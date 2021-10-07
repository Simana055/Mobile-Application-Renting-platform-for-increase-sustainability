package com.example.myapplication_rentme.model;

public class UserListData {
    private String name;
    private String email;
    private String imgSrc;
    private String role;
    private boolean expanded;
    public UserListData() {
        this.email = "";
        this.imgSrc = "";
        this.name = "";
        this.expanded = false;
        this.role = "";
    }
    public UserListData(String name,String email,String role) {
        this.email = email;
        this.imgSrc = "";
        this.name = name;
        this.role = role;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTitle() {
        return name;
    }
    public void setTitle(String title) {
        this.name= title;
    }
    public String getImgSrc() {
        return imgSrc;
    }
    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public boolean getExpanded(){return this.expanded;}
    public void setExpanded(boolean isExpanded){this.expanded = isExpanded;}
}
