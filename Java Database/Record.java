import java.io.*;
import java.util.*;
import java.util.Scanner.*;

class Record {
    private List<String> record;

    private static void main(String[] args) {
    }

    //----------CONSTRUCTOR---------------
    Record(String currentLine) {
        record = new ArrayList<String>(Arrays.asList(currentLine.split(";")));
        for (int i=0; i<record.size(); i++) {
            record.set(i, record.get(i).trim());
        }
    }
    //----------GETTERS---------------

    public String getData(int index) {
        return record.get(index);
    }

    public int getLength() {
        return record.size();
    }
    //----------SETTERS---------------

    public void setData(int index, String input) {
        record.set(index, input);
    }
    //----------METHODS---------------

    public void displayRecord() {
        for (String n : record) System.out.printf("%15s", n);
    }
}
