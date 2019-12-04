import java.io.*;
import java.util.*;
import java.util.Scanner.*;

class Database {
    private List<Table> database = new ArrayList<>();
    private int numOfTables;
    private Table currentTable = null;
    private int currentRecordInd = -1;

    private static void main(String[] args) {
    }

    //----------GETTERS---------------

    public Table getTable(int index) {
        return database.get(index);
    }

    public Table getCurrentTable() {
        return currentTable;
    }

    public int getCurrRecordInd() {
        return currentRecordInd;
    }
    //----------SETTERS---------------

    public void setCurrentTable(Table table) {
        currentTable = table;
    }

    public void setCurrentRecord(int index) {
        currentRecordInd = index;
    }
    //----------METHODS---------------

    public void displayTable() {
        System.out.println();
        currentTable.getFields().displayRecord();
        System.out.println("\n");
        for (int i=0; i<currentTable.getLength(); i++) {
            currentTable.getRecord(i).displayRecord();
            System.out.println();
        }
    }

    public void displayAllTables() {
        for (int i=0; i<database.size(); i++) {
            currentTable = database.get(i);
            displayTable();
            System.out.println();
        }
    }

    // Initializes the title and primary key of each table
    // that has been read in from a text file
    private void setupTable() {
        Scanner input = new Scanner(System.in);
        displayTable();
        System.out.print("\nWhat do you want to name this table? : ");
        currentTable.setTitle(input.next());
        System.out.print("Set the Primary Key for this table : ");
        currentTable.setKey(input.next());
    }

    // Reads the tables in from text files
    public void readTables(String[] args) {
        FileReader reader = null;
        BufferedReader buffer = null;
        String currentLine;
        Record fields = null, currentRecord = null;
        numOfTables = args.length;
        for (int i=0; i<numOfTables; i++) {
            try {
                reader = new FileReader(args[i]);
                buffer = new BufferedReader(reader);
                currentLine = buffer.readLine();
                fields = new Record(currentLine);
                currentTable = new Table(fields); //Assigns fields to table
                currentTable.setFileName(args[i]);
                while ((currentLine = buffer.readLine()) != null) {
                    currentRecord = new Record(currentLine);
                    currentTable.addRecord(currentRecord);
                }
                setupTable();
            } catch(Exception e) {e.printStackTrace();}
            finally {
    			try {
    				if (buffer != null) buffer.close();
    				if (reader != null) reader.close();
    			} catch (IOException e) {e.printStackTrace();}
            }
            database.add(currentTable);
        }
        currentTable = database.get(0);
        System.out.println("\n--> Your Current Table is " +
        currentTable.getTitle());
    }

    // Switches the current table 
    public void useTable(String name) {
        for (int i=0; i<numOfTables; i++) {
            if (name.equals(database.get(i).getTitle())) {
                currentTable = database.get(i);
                System.out.println("\n--> Table has been switched to " +
                currentTable.getTitle());
                return;
            }
        }
        System.err.println("\n--> That table does not exist");
    }

    // Writes the table to the same file that it was read from
    public void saveTable() {
        FileWriter writer = null;
        BufferedWriter buffer = null;
        try {
            writer = new FileWriter(currentTable.getFileName());
            buffer = new BufferedWriter(writer);
            for (int i=0; i<currentTable.getFields().getLength(); i++) {
                buffer.write(currentTable.getFields().getData(i) + "; ");
            }
            buffer.write("\n");
            for (int i=0; i<currentTable.getLength(); i++) {
                for (int j=0; j<currentTable.getRecord(i).getLength(); j++) {
                    buffer.write(currentTable.getRecord(i).getData(j) + "; ");
                }
                buffer.write("\n");
            }
        } catch (IOException e) {e.printStackTrace();}
        finally {
            try {
                if (buffer != null) buffer.close();
                if (writer != null) writer.close();
            } catch (IOException e) {e.printStackTrace();}
        }
        System.out.println("\n--> The table has been saved");
    }
}
