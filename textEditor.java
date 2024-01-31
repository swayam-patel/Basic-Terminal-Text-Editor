import java.util.Scanner;
import java.io.*;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

public class textEditor {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String n = "";
        for (;!n.equals("4");) {
            Scanner s = new Scanner(System.in);
            System.out.println("\n~Main Menu\n" + "\n1.Create text file\n2.Read file\n3.Edit file\n4.Exit\n");
            n = s.nextLine();
            switch(n) {
                case("1"): createFile();
                break;
                case("2"): readFile();
                break;
                case("3"): editMenu();
                break;
            }
        }
        sc.close();
    }

    //Create File
    public static void createFile() {
        System.out.println("Enter the path where you want to store the file with a slash(/) at the end.");
        Scanner s = new Scanner(System.in);
        String filePath = s.nextLine();
            try {
                System.out.println("Enter the file name");
                File file = new File(filePath + sc.next()+".txt");//Give an extension to the file (here - .txt)

                if(!file.exists()) {
                  file.createNewFile();//creating the file on the given path
                  System.out.println("\n\n\nFile created successfully\n\n\n");//success message
                } else {
                  System.out.println("\n\n\nThis file already exists\n\n\n");
                }
            } catch(IOException e) {
                System.out.println(e);
            }

        s.close();
    }

    //Read file
    public static void readFile() {
        System.out.println("Enter the file path");
        Scanner s = new Scanner(System.in);
        String filePath = s.nextLine();
        File file = new File(filePath);//Making a file object from the file path entered
        if(file.exists() && !file.isDirectory()) {
            try{
                FileInputStream fin=new FileInputStream(filePath);
                int i=0;
                System.out.println("\n\n\n");
                while((i=fin.read())!=-1){
                    System.out.print((char)i);//Infinite loop printing characters one-by-one
                }
                System.out.println("\n\n\n");
                fin.close();
            }catch(Exception e){
              System.out.println(e);//printing exception if any occured
            }
        } else {
          System.out.println("File does not exist.");
        }
        s.close();
    }

    public static void editMenu() {
        String n = "";
        Scanner s = new Scanner(System.in);
        loop: while(!n.equals("6")) {//loop continued till 6 is typed
            System.out.println("\n~Edit Menu\n" +
            "\n1.Add text\n2.Clear text\n3.Cut text\n4.Copy text\n5.Paste copied text\n6.Back to main menu\n");
            n = s.nextLine();
            switch(n) {
                case("1"): addText();
                break;
                case("2"): clearText();
                break;
                case("3"): cutText();
                break;
                case("4"): copyText();
                break;
                case("5"): pasteText();
                break;
            }
        }
    }

    //Add text
    public static void addText() {
        System.out.println("Enter the file path");
        Scanner s = new Scanner(System.in);
        String filePath = s.nextLine();//take the file path from the user
        File file = new File(filePath);
        if(file.exists() && !file.isDirectory()) {
          try {
              PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
              System.out.println("Add the text");
              printWriter.println(sc.nextLine());//Directly printing user input in the file
              printWriter.close();//closing the printwriter
              System.out.println("\n\n\nAdded Text Successfully\n\n\n");
          } catch (IOException e) {
            System.out.println(e);
          }
        } else {
          System.out.println("File does not exist.");
        }
    }

    //clear text
    public static void clearText() {
      System.out.println("Enter the file path");
      Scanner s = new Scanner(System.in);
      String filePath = s.nextLine();
      File file = new File(filePath);
      if(file.exists() && !file.isDirectory()) {//checking if file is present on the given path and is not a directory
        try {
          PrintWriter printWriter = new PrintWriter(file);
          printWriter.print("");//Just print blank space to clear the file
          printWriter.close();
          System.out.println("\n\n\nFile cleared Successfully.\n\n\n");
        } catch (FileNotFoundException e) {
          System.out.println(e);//print exception if file not found
        }
      } else {
        System.out.println("File does not exist.");
      }
      s.close();
    }

    //cut text
    public static void cutText(){
      System.out.println("Enter the file path");
      Scanner s = new Scanner(System.in);
      String filePath = s.nextLine();
      File file = new File(filePath);
      String fileText = "";
      if(file.exists() && !file.isDirectory()) {
        try{
          FileInputStream fin=new FileInputStream(filePath);
          int i=0;
          while((i=fin.read())!=-1){
            fileText += (char)i;//storing the file text in a string
          }
          fin.close();//closing the fileinputstream
        }catch(Exception e){
          System.out.println(e);
        }

        StringSelection selection = new StringSelection(fileText);//Make a stringselection object
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);//Storing the file text in the clipboard

        try {
          PrintWriter printWriter = new PrintWriter(file);
          printWriter.print("");//clear the file
          printWriter.close();
          System.out.println("\n\n\nText Cut Successfully\n\n\n");
        } catch (FileNotFoundException e) {
          System.out.println(e);
        }
      } else {
        System.out.println("File does not exist.");//tell the user that the file does not exist
      }
      s.close();
    }

    //Copy text
    public static void copyText(){
      System.out.println("Enter the file path");
      Scanner s = new Scanner(System.in);
      String filePath = s.nextLine();
      File file = new File(filePath);
      String fileText = "";
      if(file.exists() && !file.isDirectory()) {
        try{
          FileInputStream fin=new FileInputStream(filePath);
          int i=0;
          while((i=fin.read())!=-1){
            fileText += (char)i;//read the text character-by-character and store it in the string variable
          }
          fin.close();//closing the input stream
        }catch(Exception e){
          System.out.println(e);
        }
        StringSelection selection = new StringSelection(fileText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();//getting the clipborad object
        clipboard.setContents(selection, selection);//setting the string in the clipboard
        System.out.println("\n\n\nText Copied Successfully\n\n\n");
      } else {
        System.out.println("File does not exist.");
      }
      s.close();
    }

    //paste text
    public static void pasteText(){
      System.out.println("Enter the file path");
      Scanner s = new Scanner(System.in);
      String filePath = s.nextLine();
      File file = new File(filePath);
      String pasteText = "";
      if(file.exists() && !file.isDirectory()) {
        try {
          Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
          pasteText = (String) clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor);//get the copied text from clipboard
          if(pasteText == "" || pasteText == " "){
            System.out.println("\n\n\nNo text was copied\n\n\n");
          } else {
            try {
              PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));//make a print writer object
              printWriter.println(pasteText);//pasting text
              printWriter.close();//closing the printwriter
              System.out.println("\n\n\nText Pasted\n\n\n");
            } catch (IOException e) {
              System.out.println(e);
            }
          }
        } catch (UnsupportedFlavorException e){
          System.out.println(e);
        } catch (IOException e) {
          System.out.println(e);
        }
      } else {
        System.out.println("File does not exist.");
      }
      s.close();
    }
}
