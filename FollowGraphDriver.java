/**
 * A driver class that also contains an saves and loads a FollowerGraph object.
 * This FollowerGraph Object is viewed as a graph that shows what Twittior users follow each other.
 * Using this class, we can remove and add users as well as request the shortest path.
 *
 * @author
 * Nicole Niemiec
 * #112039349
 * nicole.niemiec@stonybrook.edu
 * CSE 214 R08 FELIX
 * DECEMBER 3RD, HW #7
 *
 * @version 1
 */

import java.io.*;
import java.util.ArrayList;
//import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.Serializable;

public class FollowGraphDriver implements Serializable{

    //int userCount = User.getUserCount();

    public static FollowerGraph follows = load();
    private int count;

    /**
     * The main method where the program runs.
     * @param args
     *      Where the program runs.
     */
    public static void main(String[] args) {

        //Scanner input = new Scanner(System.in);
        main_menu();

    }


    /**
     * A method that loads a FollowerGraph object into a new File in an input stream.
     * @return
     *      Returns a FollowerGraph object.
     */
    public static FollowerGraph load(){
        try{

            FileInputStream file = new FileInputStream("follow_graph.obj");
            ObjectInputStream inStream = new ObjectInputStream(file);
            FollowerGraph follows = (FollowerGraph) inStream.readObject();
            User.setUserCount(follows.userCount);
            System.out.println("Object saved.");
            return follows;

        } catch(Exception ex){

            System.out.println("File not found, entering empty object.");
            return new FollowerGraph();

        }

    }

    /**
     * A method that saves the FollowerGraph object into an output stream.
     * @param follows
     *      The current graph.
     */
    public static void save(FollowerGraph follows){
        try{

            FileOutputStream file = new FileOutputStream("follow_graph.obj");
            ObjectOutputStream outStream = new ObjectOutputStream(file);
            follows.userCount = User.getUserCount();

            //FollowerGraph follows = new FollowerGraph()
            outStream.writeObject(follows);
            System.out.println("Program saved at follow_graph.obj");

        }
        catch (Exception ex){

            System.out.println("Error");

        }
    }

    /**
     * The main method menu.
     */
    public static void main_menu(){

        String command = "";

        Scanner input = new Scanner(System.in);

        do {

            System.out.println("Main Menu:");
            System.out.println("(U) \t Add User");
            System.out.println("(C) \t Add Connection");
            System.out.println("(AU) \t Load all Users");
            System.out.println("(AC) \t Load all Connections");
            System.out.println("(P) \t Print all Users");
            System.out.println("(L) \t Print all Loops");
            System.out.println("(RU) \t Remove User");
            System.out.println("(RC) \t Remove Connection");
            System.out.println("(SP) \t Find Shortest Path");
            System.out.println("(AP) \t Find All Paths");
            System.out.println("(Q) \t Quit");

            //input.nextLine();
            System.out.println("Please enter a selection: ");
            command = input.nextLine().toUpperCase();

            switch (command) {
                case "U":
                    main_addUser();
                    break;
                case "C":
                    main_addConnection();
                    break;
                case "AU":
                    main_loadAllUsers();
                    break;
                case "AC":
                    main_loadAllConnections();
                    break;
                case "P":
                    main_printAllUsers();
                    break;
                case "L":
                    main_printAllLoops();
                    break;
                case "RU":
                    main_removeUser();
                    break;
                case "RC":
                    main_removeConnection();
                    break;
                case "SP":
                    main_findShortestPath();
                    break;
                case "AP":
                    main_findAllPaths();
                    break;
                case "Q":
                    System.out.println("Program terminating....");
                    save(follows);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }


        }while(!command.equalsIgnoreCase("Q"));

        input.close();

    }

    /**
     * A main method to add a User to the current graph.
     */
    public static void main_addUser(){

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the name of the user: ");
        String name = in.nextLine();
        boolean contains = false;
        int i = 0;

        while(!contains && i < follows.getUsers().size()){
            if (follows.getUsers().get(i).getName().equalsIgnoreCase(name)) {
                contains = true;
            }

            i++;
        }

        if(!contains){
            follows.addUser(name);
            System.out.println(name + " added.");
        }
        else {
            System.out.println("Invalid: User already exists.");
        }

    }

    /**
     * A main method that adds a connection between two Users by
     * calling the method in the FollowerGraph class.
     */
    public static void main_addConnection(){

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the source of the connection to add: ");
        String userFrom = in.nextLine();
        System.out.println("Please enter the destination of the connection to add: ");
        String userTo = in.nextLine();

        try{
            follows.addConnection(userFrom, userTo);
        }catch (Exception ex){
            System.out.println("Invalid: One or more Users not found.");
        }

    }

    /**
     * A main method that loads in all the Users from a file and
     * calls the loadAllUsers method from the FollowerGraph class.
     */
    public static void main_loadAllUsers(){

        Scanner in = new Scanner(System.in);
        System.out.println("Enter a file name: ");
        String name = in.nextLine();

        try {
            follows.loadAllUsers(name);
        }catch(FileNotFoundException ex){
            System.out.println("File not found.");
        }
        //in.close();

    }

    /**
     * A main method that loads in all the Connections from a file and calls
     * the loadAllConnections method from the FollowerGraph class.
     */
    public static void main_loadAllConnections(){

        Scanner in = new Scanner(System.in);
        System.out.println("Enter a file name: ");
        String name = in.nextLine();

        follows.loadAllConnections(name);
        //in.close();
    }

    /**
     * Main method that prints all of the Users sorted by a given comparator.
     */
    public static void main_printAllUsers() {

        Scanner in = new Scanner(System.in);

        boolean valid = false;

        do {

            System.out.println("Print Menu: ");
            System.out.println("(SA) \t Sort by User Name");
            System.out.println("(SB) \t Sort by User followers");
            System.out.println("(SC) \t Sort by User following");
            System.out.println("(Q) \t Quit");

            String command = in.nextLine().toUpperCase();

            switch (command) {

                case "SA":
                    main_sortByUserName();
                    valid = false;
                    break;
                case "SB":
                    main_sortByUserFollowers();
                    valid = false;
                    break;
                case "SC":
                    main_sortByUserFollowing();
                    valid = false;
                    break;
                case "Q":
                    valid = true;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");

            }

        }while (!valid);
    }

    /**
     * Main method that sorts the User objects by their name.
     */
    public static void main_sortByUserName(){

        follows.printAllUsers(new NameComparator());

    }

    /**
     * Main method that sorts the User objects by their Following count.
     */
    public static void main_sortByUserFollowing(){

        follows.printAllUsers(new FollowingComparator());

    }

    /**
     * Main method that sorts the User objects by their Followers count.
     */
    public static void main_sortByUserFollowers(){

        follows.printAllUsers(new FollowersComparator());

    }

    /**
     * Main method that calls the printAllLoops method from the
     * FollowerGraph class and prints out the loops.
     */
    public static void main_printAllLoops(){

        Scanner input = new Scanner(System.in);

        System.out.println("There are no loops.");

    }

    /**
     * Main method that removes a user.
     */
    public static void main_removeUser(){

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the name of the user: ");
        String name = in.nextLine();
        boolean contains = false;
        int i = 0;
        int index = -1;
        int arrayIndex = -1;

        while(!contains && i < follows.getUsers().size()){
            if (follows.getUsers().get(i).getName().equalsIgnoreCase(name)) {
                contains = true;
                index = follows.getUsers().get(i).getIndexPos();
                arrayIndex = i;
            }
            i++;
        }

        if(contains){
            ArrayList<String> followers = follows.getUsers().get(arrayIndex).getFollowers();
            ArrayList<String> following = follows.getUsers().get(arrayIndex).getFollowing();

            int x = 0;

            while(followers.size() != 0){
                follows.removeConnection(followers.get(x), follows.getUsers().get(arrayIndex).getName());
            }

            x = 0;

            while(following.size() != 0){
                follows.removeConnection(follows.getUsers().get(arrayIndex).getName(), following.get(x));
            }


            //follows.getUsers().get(i).setIndexPos(-1);
            follows.getUsers().remove(arrayIndex);

            System.out.println(name + " was removed successfully.");
        }
        else {
            System.out.println("Invalid: User doesn't exist.");
        }

    }

    /**
     * A main method that removes a connection between two Users by calling the
     * removeConnection calls from the FollowerGraph class.
     */
    public static void main_removeConnection(){

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the source of the connection to remove: ");
        String userFrom = in.nextLine();
        System.out.println("Please enter the destination of the connection to remove: ");
        String userTo = in.nextLine();

        try{
            follows.removeConnection(userFrom, userTo);
        } catch(Exception ex){
            System.out.println("Invalid: One or more Users not found.");
        }

    }

    /**
     * A main method that finds the shortest Path between two Users by calling
     * the shortestPath method from the FollowerGraph class.
     */
    public static void main_findShortestPath(){

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the source of the connection: ");
        String name1 = in.nextLine();
        System.out.println("Please enter the destination of the connection: ");
        String name2 = in.nextLine();


        try{
            System.out.println(follows.shortestPath(name1, name2));
        }catch(Exception ex){
            System.out.println("Both Users must exist.");
        }

    }

    /**
     * A main method that finds all the paths between two Users by calling
     * the findAllPaths method from the FollowerGraph class.
     */
    public static void main_findAllPaths(){

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the source of the connection: ");
        String userFrom = in.nextLine();
        System.out.println("Please enter the destination of the connection: ");
        String userTo = in.nextLine();

        try{
            List<String> paths = follows.allPaths(userFrom, userTo);

            for(int i = 0; i < paths.size(); i++){
                System.out.println(paths.get(i));
            }

        }catch(Exception ex){
            System.out.println("Both must be in the arraylist.");
        }

    }




}
