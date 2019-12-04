import java.io.*;
import java.util.*;
import java.util.Scanner.*;

class Application {

    public static void main(String[] args) {
        Application program = new Application();
        if (args.length == 0) {
            System.err.println("\nThere are no files to import tables from.");
            System.err.println("Use: java Application nameofFile.txt\n");
            System.exit(1);
        }
        else program.run(args);
    }

    void run(String[] args) {
        Scanner input = new Scanner(System.in);
        Database app = new Database();
        app.readTables(args);
        while (true) {
            System.out.println("\n\t\tDatabase Commands\n" +
            "use 'enter table name';    select 'enter primary key value';\n"+
            "update 'enter field name' (current record);\n" +
            "insert (current table);    display (current table);\n"+
            "displayAll (all tables);   save (current table);\n"+
            "delete (current record);   quit\n");
            System.out.print("Enter a command followed by the name of a\n" +
            "table, key value, or field specified in single quotes: ");
            String temp = input.nextLine();
            String[] command = temp.split("\\s+");
            switch (command[0]) {
                case "use": if (command.length == 2) {
                                app.useTable(command[1]);
                                app.setCurrentRecord(-1);
                            }
                            else System.err.println("\n--> No Table Specified");
                            break;
                case "display": app.displayTable();
                                break;
                case "displayAll":  app.displayAllTables();
                                    break;
                case "insert":  app.getCurrentTable().insertRecord();
                                break;
                case "select":  if(command.length == 2) {
                                    app.setCurrentRecord(
                                    app.getCurrentTable().selectRecord(
                                    command[1]));
                                }
                                else {
                                    System.err.println(
                                    "\n--> No Record Specified");
                                }
                                break;
                case "delete":  if(app.getCurrRecordInd() < 0) {
                                    System.err.println("\n--> First select a "+
                                    "record, then try delete");
                                }
                                else {
                                    app.getCurrentTable().deleteRecord(
                                    app.getCurrRecordInd());
                                    app.setCurrentRecord(-1);
                                }
                                break;
                case "update":  if(app.getCurrRecordInd() >= 0 &&
                                command.length==2) {
                                    app.getCurrentTable().updateRecord(
                                    app.getCurrRecordInd(), command[1]);
                                }
                                else {
                                    System.err.println("\n--> First select a "+
                                    "record, then try: update " +
                                    "'enter field name'");
                                }
                                break;
                case "save":    app.saveTable();
                                break;
                case "quit":    System.exit(1);
                default: System.err.println("\n--> Invalid Command, Try Again");
            }
        }
    }
}
