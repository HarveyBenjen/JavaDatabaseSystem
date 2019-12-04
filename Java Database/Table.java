import java.io.*;
import java.util.*;
import java.util.Scanner.*;

class Table {
    private List<Record> table = new ArrayList<>();
    private Record fields;
    private String title;
    private String key;
    private int keyIndex;
    private String fileName;

    private static void main(String[] args) {
    }

    //----------CONSTRUCTOR---------------

    Table(Record fields) {
        this.fields = fields;
    }
    //----------GETTERS---------------

    public String getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }

    public Record getRecord(int index) {
        return table.get(index);
    }

    public String getKey() {
        return key;
    }

    public Record getFields() {
        return fields;
    }

    public int getLength() {
        return table.size();
    }
    //----------SETTERS---------------

    public void setTitle(String input) {
        title = input;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setKey(String input) {
        for (int i=0; i<fields.getLength(); i++) {
            if (input.equals(fields.getData(i))) {
                key = input;
                keyIndex = i;
                return;
            }
        }
        System.err.println("\n--> That field does not exist");
        System.exit(1);
    }
    //----------METHODS---------------

    public void addRecord(Record record) {
        table.add(record);
    }

    // Asks the user to enter a new set of data to be inserted into
    // the current table
    private Record createRecord() {
        Scanner scan = new Scanner(System.in);
        fields.displayRecord();
        System.out.print("\n\n--> Enter data for each column field, separated"+
        " by semicolons :\n");
        return (new Record(scan.nextLine()));
    }

    // Adds the user's new set of data to the current table
    public void insertRecord() {
        Record record = createRecord();
        if(record.getLength() == fields.getLength()) {
            addRecord(record);
            System.out.println("\n--> Record has been added to the table");
        }
        else {
            System.err.println("\n--> The number of data does not match the " +
            "number of fields");
        }
    }

    // selects the current record based on a specific value of the
    // primary key in the current table
    public int selectRecord(String keyData) {
        for (int i=0; i<table.size(); i++) {
            if (keyData.equals(table.get(i).getData(keyIndex))) {
                System.out.println("\n--> Your current record is:\n");
                table.get(i).displayRecord();
                System.out.println();
                return i;
            }
        }
        System.err.println("\n--> Primary key data does not match any records");
        return -1;
    }

    // Returns false if "String value" is equal to any Primary Key data.
    // It is only used below in updateRecord
    private boolean checkKeyData(String data) {
        for (int i=0; i<table.size(); i++) {
            if (data.equals(table.get(i).getData(keyIndex))) {
                return false;
            }
        }
        return true;
    }

    // Changes the data of a specific field in the current table
    public void updateRecord(int index, String field) {
        Scanner input = new Scanner(System.in);
        for (int i=0; i<fields.getLength(); i++) {
            if (field.equals(fields.getData(i))) {
                System.out.print("\nWhat do you want to change the data under "
                + field + " to? : ");
                if (field.equals(key)) {
                    while (!checkKeyData(input.next())) {
                        System.err.println("\n--> That primary key data is not"
                        + " unique \nTry Again : ");
                    }
                }
                table.get(index).setData(i, input.next());
                System.out.println("\n--> The data has been updated");
                return;
            }
        }
        System.err.println("\n--> Record not Updated! Check field name");
    }

    // Deletes the current record in the current table
    public void deleteRecord(int index) {
        System.out.println("\n--> The following record is deleted :\n");
        table.get(index).displayRecord();
        System.out.println();
        table.remove(index);
    }
}
