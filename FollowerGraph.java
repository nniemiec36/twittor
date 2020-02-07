/**
 * A class that acts as a Wrapper for Twittior following and followers list. This
 * class keeps track of who follows each other by using an boolean adjacency matrix.
 * It also holds an ArrayList of Users.
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

public class FollowerGraph implements Serializable {

    private ArrayList<User> users;
    public static final int MAX_USERS = 100;
    private boolean[][] connections;
    public int userCount;

    public int[][] follows;
    public int count = 0;

    /**
     * A constructor method that creates a FollowerGraph object and instantiates the
     * variables within the object.
     */
    public FollowerGraph(){

        users = new ArrayList<>();
        connections = new boolean[MAX_USERS][MAX_USERS];
        follows = new int[MAX_USERS][2]; //first is followers, second is following
        count = users.size();
        userCount = users.size();

    }

    /**
     * An accessor method that returns the ArrayList of User objects.
     * @return
     *      Returns an ArrayList of Users.
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * A method that adds another User to the current ArrayList of Users
     * @param userName
     *      The name of the User object to be created and added.
     */
    public void addUser(String userName){

        User u1 = new User(userName);
        users.add(u1);
        u1.increment();
        count++;
        //User.setUserCount(User.getUserCount()+1);

    }

    /**
     * A method that adds a connection between two User objects and updates
     * both the int and boolean arrays.
     * @param userFrom
     *      The User that follows another User.
     * @param userTo
     *      The User being followed.
     */
    public void addConnection(String userFrom, String userTo){

        //User u1;
        //User u2;
        int i = -1, j = -1;

        for(int x = 0; x < users.size(); x++) {
            if (users.get(x).getName().equals(userFrom)) {
                i = users.get(x).getIndexPos();
                //u1 = users.get(x);
            }

        }

        for(int x = 0; x < users.size(); x++){
            if(users.get(x).getName().equals(userTo)){
                j = users.get(x).getIndexPos();
                //u2 = users.get(x);
            }
        }

        if(i == -1 || j == -1 ){
            System.out.println("One or more Users not found.");
        }
        else{
            connections[i][j] = true;
            users.get(j).addFollower(userFrom);
            users.get(i).addFollowing(userTo);
            follows[j][0]++;
            follows[i][1]++;
            System.out.println(users.get(i).getName() +", " + users.get(j).getName() + " has been added.");

        }

    }

    /**
     * A method that removes a connection between two User objects and updates
     * both the int and boolean arrays accordingly.
     * @param userFrom
     *      The User that unfollows another User.
     * @param userTo
     *      The User being unfollowed.
     */
    public void removeConnection(String userFrom, String userTo){

        int i = -1, j = -1;

        for(int x = 0; x < users.size(); x++){
            if(users.get(x).getName().equals(userFrom)){
                i = users.get(x).getIndexPos();
            }

            if(users.get(x).getName().equals(userTo)){
                j = users.get(x).getIndexPos();
            }
        }

        if(i == -1 || j == -1){
            System.out.println("One or more Users not found.");
        }
        else{
            connections[i][j] = false;
            users.get(j).removeFollower(userFrom);
            users.get(i).removeFollowing(userTo);
            follows[j][0]--;
            follows[i][1]--;
            System.out.println("Connection removed.");
        }


    }

    /**
     * A method that finds the shortest possible path between two Users.
     * @param userFrom
     *      The first User's name.
     * @param userTo
     *      The second User's name.
     * @return
     *      Returns the ShortestPath as a String
     * @throws Exception
     *      Throws an Exception if there are no paths available.
     */
    public String shortestPath(String userFrom, String userTo) throws Exception{

        int source = -1;
        int dest = -1;

        for(int x = 0; x < users.size(); x++){
            if(users.get(x).getName().equals(userFrom))
                source = users.get(x).getIndexPos();
        }

        for(int x = 0; x < users.size(); x++){
            if(users.get(x).getName().equals(userTo))
                dest = users.get(x).getIndexPos();
        }

        if(source == -1 || dest == -1)
            throw new Exception();

        Double[][] dist = new Double[users.size()][users.size()];

        for(int x = 0; x < dist.length; x++){
            for(int y = 0; y < dist[x].length; y++)
                dist[x][y] = Double.POSITIVE_INFINITY;
        }

        int[][] next = new int[users.size()][users.size()];

        for(int x = 0; x < next.length; x++){
            for(int y = 0; y < next[x].length; y++)
               next[x][y] = -1;
        }

        for(int x = 0; x < connections.length; x++){
            for(int y = 0; y < connections[x].length; y++){
                if(connections[x][y]){
                    dist[x][y] = 1.0;
                    next[x][y] = users.get(y).getIndexPos();
                }
            }
        }

        for(int k = 0; k < users.size(); k++){
            for(int i = 0; i < users.size(); i++){
                for(int j = 0; j < users.size(); j++){
                    if(dist[i][k] + dist[k][j] < dist[i][j]){
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }

        String path = "";

        if(next[source][dest] == -1)
            return null;
        path += userFrom + " --> ";
        while(source != dest){
            source = next[source][dest];
            path += users.get(source).getName() + " --> ";
        }

        String[] paths = path.split(" ");

        String shortestPath = "";

        for(int i = 0; i < paths.length; i++){
            shortestPath += paths[i] + " ";
            if(i != paths.length - 1)
                shortestPath += "--> ";
        }

        shortestPath = path.substring(0, path.lastIndexOf(" --> "));

        return shortestPath;
    }

    /**
     * A method that finds all the paths between two Users.
     * @param userFrom
     *      The first User's name.
     * @param userTo
     *      The second User's name.
     * @return
     *      Returns a List of Strings of different Paths.
     */
    public List<String> allPaths(String userFrom, String userTo){

        ArrayList<String> list = new ArrayList<String>();
        boolean[] visited = new boolean[users.size()];
        String path = "";

        //int userCount = 0;

        int indexTo = -1;
        int indexFrom = -1;

        for(int i = 0; i < users.size(); i++){
            if(userTo.equals(users.get(i).getName()))
                indexTo = users.get(i).getIndexPos();
        }

        for(int i = 0; i < users.size(); i++){
            if(userFrom.equals(users.get(i).getName()))
                indexFrom = users.get(i).getIndexPos();
        }

        for(int i = 0; i < connections[indexFrom].length; i++){

                if(connections[indexFrom][i]){
                    path += userFrom + " ";
                    if(indexFrom != indexTo)
                        dfs(i, visited, list, path, indexTo, userTo);
            }
            path = "";
        }

        return list;
    }

    /**
     * Depth first traversal method.
     * @param i
     *      Starting position.
     * @param visited
     *      Visited boolean matrix.
     * @param list
     *      List of different paths.
     * @param path
     *      Current path.
     * @param indexTo
     *      Index of the destination.
     * @param userTo
     *      String of the destination's name.
     */
    public void dfs(int i, boolean[] visited,
                    ArrayList<String> list, String path, int indexTo, String userTo){

        //int[] connections2 = new int[users.get(i).getFollowing().size()];

            visited[i] = true;
            path += users.get(i).getName() + " ";

            for(int j = 0; j < users.size(); j++){

                if(connections[i][j]) {
                    path += users.get(j).getName() + " ";
                    System.out.println(path);
                }
                if(indexTo == j){
                    //path += userTo;
                    list.add(path);
                }
                if(!visited[j] && indexTo != j){
                    dfs(j, visited, list, path, indexTo, userTo);
                }
            }

    }

    /**
     * A method that finds all the loops in the graph.
     * @return
     *      Returns all the Loops in a String List.
     */
    public List<String> findAllLoops() {
//
        return null;
    }

    /**
     * A method that prints all the Users in the ArrayList sorted by a specific Comparator
     * that is passed as a parameter.
     * @param comp
     *      The Comparator being used to sort all of the
     *      Users of the ArrayList.
     */
    public void printAllUsers(Comparator comp){

            ArrayList<User> usersSorted = new ArrayList<>(users.size());

            for(int x = 0; x < users.size(); x++){
                usersSorted.add(users.get(x));
            }

            //Collections.copy(usersSorted, users);

            Collections.sort(usersSorted, comp);

            System.out.printf("%-30s%-30s%-30s", "User Name", "Number of Followers"
                    , "Number Following");
            System.out.println();
            for(int i = 0; i < users.size(); i++){
                System.out.printf("%-30s%-30s%-30s",
                        usersSorted.get(i).getName(),
                        usersSorted.get(i).getFollowers().size(),
                        usersSorted.get(i).getFollowing().size());
                System.out.println();
            }

    }

    /**
     * A method that prints all the followers of a certain User
     * in the ArrayList.
     * @param userName
     *      The User whose followers to be searched and printed.
     */
    public void printAllFollowers(String userName){

        int i = 0;

        ArrayList<String> followers = new ArrayList<>();

        for(int x = 0; x < users.size(); x++){
            if(users.get(x).getName().equals(userName))
                i = users.get(x).getIndexPos();
        }

        for(int j = 0; j < users.size(); j++){
            if(connections[i][j])
                followers.add(users.get(j).getName());
        }

        for(int x = 0; x < followers.size(); x++){
            System.out.print(followers.get(x));
            if(x != followers.size() - 1)
                System.out.print(", ");
        }

    }

    /**
     * A method that prints all the following of a certain User in
     * the ArrayList.
     * @param userName
     *      The User whose following to be searched and printed.
     */
    public void printAllFollowing(String userName){
        int user = 0;

        ArrayList<String> following = new ArrayList<>();

        for(int x = 0; x < users.size(); x++){
            if(users.get(x).getName().equals(userName))
                user = users.get(x).getIndexPos();
        }

        for(int i = 0; i < users.size(); i++){
            if(connections[i][user])
                following.add(users.get(i).getName());
        }

        for(int x = 0; x < following.size(); x++){
            System.out.print(following.get(x));
            if(x != following.size() - 1)
                System.out.print(", ");
        }
    }

    /**
     * A method that loads all the User objects from a file, creates them, and adds
     * them into the ArrayList of Users.
     * @param filename
     *      The name of the file to be opened.
     */
    public void loadAllUsers(String filename) throws FileNotFoundException{

        File file = new File(filename);

        if(file.exists()){
            try{
                Scanner read = new Scanner(file);
                String line = "";
                while(read.hasNextLine()){
                    User u1 = new User(line = read.nextLine());
                    users.add(u1);
                    System.out.println(line + " has been added.");
                }

            }catch(FileNotFoundException ex){
                //throw new FileNotFoundException();
                System.out.println("File not found.");
            }
        }else{
            throw new FileNotFoundException("File not found.");
        }



    }

    /**
     * A method that loads all the User objects from a file, creates the connections between them,
     * and adds them into the boolean and int arrays.
     * @param filename
     *      The name of the file to be opened.
     */
    public void loadAllConnections(String filename){

        File file = new File(filename);

        if(file.exists()) {
            try {
                Scanner read = new Scanner(file);
                String line = "";
                while (read.hasNextLine()) {
                    line = read.nextLine();
                    String[] connection = line.split(",");
                    //if(connection.length == 2)
                    addConnection(connection[0], connection[1].trim());
                }
            } catch (FileNotFoundException ex) {
                System.out.println("File not found");
            }
        }
        else {
            System.out.println("File does not exist.");
        }

    }


}
