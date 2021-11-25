/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Samuel Bauta
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class InventoryWrapper {

    ObservableList<Item> data = FXCollections.observableArrayList();

    //create observable list aka inventory
    public ObservableList<Item> getData() {
        return data;
    }

    //add item to the inventory
    public void addItem(Item newItem) {
        getData().addAll(newItem);
    }

    //removes item at given index from inventory
    public void removeItem(Item index) {
        getData().remove(index);
    }

    //clears the inventory
    public void clearList() {
        getData().clear();
    }

    //creates html page and file
    public void saveHtml() {
        //access data from inventory
        this.getData();
     try {
            //creates filter, save dialogue window, and file
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter htmlFilter = new FileChooser.ExtensionFilter("Html Files (*.html)", "*.html");
            fileChooser.getExtensionFilters().add(htmlFilter);
            File file = fileChooser.showSaveDialog(new Stage());
            //writer to write into file
            BufferedWriter wr = new BufferedWriter(new FileWriter(file));
            //for every item in the list, write the data into a table row
            wr.write("<table><tr>\n<th>SerialNumber:</th>\n<th>Price:</th>\n<th>Name:</th>\n</tr>\n");
            for(Item item : getData()){
                wr.write("<tr>");
                wr.write("\t<td>" + item.getSerial() + "</td>\n");
                wr.write("\t<td>" + item.getPrice() + "</td>\n");
                wr.write("\t<td>" + item.getName() + "</td>\n");
                wr.write("</tr>\n");
            }//finish the format with a table close
            wr.write("</table\n");
            //close file
         wr.close();
        }catch (Exception e){
         e.printStackTrace();
        }
    }

    //creates json file
    public void saveJson() throws IOException {
        this.getData();
        //displays the save window
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("Json Files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(jsonFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        //writer to write into file
        BufferedWriter wr = new BufferedWriter(new FileWriter(file));
        //object to store data in json format
        JSONObject object = new JSONObject();
        //looping and adding each item attribute to the array, so we can store in a json object
        JSONArray array = new JSONArray();

        try{
            //loops through every item in the inventory
            for(Item item : getData()){
                //temp object will store each attribute
                JSONObject temp = new JSONObject();
                //putting all the attributes in temp item
                temp.put("Serial No",item.getSerial());
                temp.put("Price",item.getPrice());
                temp.put("Name",item.getName());


                array.add(temp);
            }
            object.put("inventory", array);
            wr.write(object.toJSONString());
            wr.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //creates tsv file
    public void saveTsv(){
        //access data from inventory
        this.getData();
        try {
            //filters to only text files
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter tsvFilter = new FileChooser.ExtensionFilter("TSV Files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(tsvFilter);
            //open load window
            File file = fileChooser.showSaveDialog(new Stage());
            //writer to write into file
            PrintWriter wr = new PrintWriter(new FileWriter(file));
            //writes header row into text file
            wr.printf("Serial No.\tPrice\tName\t");
            //for every item in the inventory, write to file, separate with tabs
            for(Item item : getData()){
                wr.printf("%n%1$13s\t%2$1s\t%3$2s\t",item.getSerial(),item.getPrice(),item.getName());
            }
            //close file
            wr.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //loads html file into inventory
    public void loadHtml() throws IOException {
        //filter file type to html files only
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter htmlFilter = new FileChooser.ExtensionFilter("Html Files (*.html)", "*.html");
        fileChooser.getExtensionFilters().add(htmlFilter);
        //open load catalogue
        File html = fileChooser.showOpenDialog(null);
        Document doc;
        //use jsoup parser to parse the html file
        try {
            doc = Jsoup.parse(html, "UTF-8");
            ArrayList<String> htmlItems = new ArrayList<>();
            Element table = doc.select("table").get(0); //select the first table.
            Elements rows = table.select("tr");

            for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                Element row = rows.get(i);
                Elements cols = row.select("td");

                String serial = cols.get(0).text();
                String price = cols.get(1).text();
                String name = cols.get(2).text();

                data.add(new Item(price, serial, name));
            }

        } catch (Exception e) {
            //prints rip if error occurs because that's how I felt 998/999 times that I implemented this wrong and method fail :'(
            System.out.println("rip");
            e.printStackTrace();
        }
    }

    //loads json file into inventory
    public void loadJson(){
        this.getData();
        //filter to only json files
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Json files (*.json)", "*.json");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(filter);
        //opens load catalogue
        File file = fileChooser.showOpenDialog(null);
        //parser to parse the json data
        JSONParser parser = new JSONParser();
        try{
            //parse the json data
            Object pObject = parser.parse(new FileReader(file));
            //store that data into a json object by casting obj to be JSON
            JSONObject jObject = (JSONObject) pObject;
            //store that object data into a json array
            JSONArray jArray = (JSONArray) jObject.get("inventory");
            //loop through every item object in the array
            for(Object items : jArray){
                //case items to be json
                JSONObject itemObject = (JSONObject) items;
                //assign string values to their corresponding json data
                String serial = (String) itemObject.get("Serial No");
                String price = (String) itemObject.get("Price");
                String name = (String) itemObject.get("Name");
                //create new item with string data
                if(!serial.matches("Serial No")){
                    if(!price.matches("Price")){
                        if(!name.matches("Name")){
                            Item item = new Item(price, serial, name);
                            //add item to the inventory
                            data.add(item);
                        }
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //loads tsv file into inventory
    public void loadTsv()throws IOException {

            //filter file type to text files only
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Tsv files (*.txt)", "*.txt");
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(filter);
            //opens load catalogue
            File file = fileChooser.showOpenDialog(null);
            try{
                //scan the file
                Scanner reader = new Scanner(file);
                while(reader.hasNextLine()){
                    //read into string array
                    String[] line = reader.nextLine().split("\t");
                    String price = line[0];
                    String serial = line[1];
                    String name = line[2];
                    Item item;
                    data.add(new Item(serial,price,name));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
    }
}