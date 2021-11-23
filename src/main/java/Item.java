/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Samuel Bauta
 */
public class Item {

    //declaring variables of Item class
    private String price;
    private String serial;
    private String name;

    public Item(String price, String serial, String name) {
        this.price = price;
        this.serial = serial;
        this.name = name;
    }

    //create setters and getters to be accessed elsewhere
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}







/*ublic class Item {

    public Item(String price, String serialNo, String name){

    }

    private String price;
    private String serialNo;
    private String name;


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

 */

