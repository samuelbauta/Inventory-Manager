import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;


public class InventoryWrapper {

    //create observable list
    ObservableList<Item> data = FXCollections.observableArrayList();

    //returns the observable list -COMPLETE
    public ObservableList<Item> getData() {
        return data;
    }

    //add item to the list -COMPLETE
    public void addItem(Item newItem) {
        getData().addAll(newItem);
    }

    //removes the item from list -COMPLETE
    public void removeItem(Item index) {
        getData().remove(index);
    }

    //clear the list -COMPLETE
    public void clearList() {
        getData().clear();
    }


    //TEST
    public void saveList() {
        createHtml();
    }

    //creating html page and file
    public void createHtml() {
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
            //writes the first tag of the html format into a string
            wr.write("<table>\n");
            //for every item in the list, write the data into a table row
            for(Item item : getData()){
                wr.write("<tr>\n" + "<th>" + item.getSerial() + "    " + item.getPrice() + "    " +  item.getName() + "</th>\n" + "</tr>");
            }//finish the format with a table close
            wr.write("\n</table");

            //close file
         wr.close();
        }catch (Exception e){

        }
    }

    public void createJson(){

    }

    public void createTsv(){
        //access data from inventory
        this.getData();
        try {
            //creates filter, save dialogue window, and file
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter tsvFilter = new FileChooser.ExtensionFilter("TSV Files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(tsvFilter);
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

        }
    }

    public void loadHtml(){

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter htmlFilter = new FileChooser.ExtensionFilter("Html Files (*.html)", "*.html");
        fileChooser.getExtensionFilters().add(htmlFilter);

        File file = fileChooser.showOpenDialog(null);

        try{

            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                String line[] = reader.nextLine().split("<table>");
                
            }

        }catch(Exception e){

        }

    }

    public void loadJson(){

    }

    public void loadTsv()throws IOException {

        //   inventory.getData().clear();

            //filter file type to text files only
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Htm files (*.txt)", "*.txt");
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(filter);
            //opens load catalogue
            File file = fileChooser.showOpenDialog(null);

            try{
                //scan the file
                Scanner reader = new Scanner(file);
                while(reader.hasNextLine()){
                    //read into string array
                    String[] line = reader.nextLine().split(", ");
                    String price = line[0];
                    String serial = line[1];
                    String name = line[2];

         //           inventory.addItem(new Item(price,serial,name));
         //           tableView.setItems(inventory.getData());
                }
            }catch(Exception e){
                System.out.println("null");
            }

    }

}

