import java.util.ArrayList;
import java.io.Serializable;

/**
 * A class that acts as a Wrapper for Twittior user. The class holds two instance
 * variables: an int keeping track of the User's index position, and a String
 * representing the User's name. It also has a static int variable that
 * keeps track of the user count.
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

public class User implements Serializable {

    private String name;
    private int indexPos;
    private int followerCount;
    private int followingCount;
    private ArrayList<String> followers;
    private ArrayList<String> following;
    private static int userCount = 0;

    public void increment(){
        userCount++;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    /**
     * A constructor for a User object with one parameter.
     * @param name
     *      The name of this User.
     */
    public User(String name){
        this.name = name;
        indexPos = userCount;
        increment();
        //increment();
        followerCount = 0;
        followingCount = 0;
        followers = new ArrayList<>();
        following = new ArrayList<>();
    }

    /**
     * A method that adds a follower's name to the followers ArrayList
     * to keep track of who is following the User.
     * @param name
     *      The name of the follower to be added.
     */
    public void addFollower(String name){
        followers.add(name);
    }

    /**
     * A method that adds a name to the followings ArrayList
     * to keep track of who the User is following.
     * @param name
     *      The name of the User that the current User is following.
     */
    public void addFollowing(String name){
        following.add(name);
    }

    /**
     * A method that removes a follower's name to the followers ArrayList
     * to keep track of who is following the User.
     * @param name
     *      The name of the follower to be removec.
     */
    public void removeFollower(String name){
        followers.remove(name);
    }

    /**
     * A method that removes a name to the followings ArrayList
     * to keep track of who the User is following.
     * @param name
     *      The name of the User that the current User is no longer following.
     */
    public void removeFollowing(String name){
        following.remove(name);
    }

    /**
     * An accessor method that returns the ArrayList of followers.
     * @return
     *      Returns an ArrayList of followers.
     */
    public ArrayList<String> getFollowers(){
        return followers;
    }

    /**
     * An accessor method that returns the ArrayList of followings.
     * @return
     *      Returns an ArrayList of followings.
     */
    public ArrayList<String> getFollowing(){
        return following;
    }

    /**
     * An accessor method that returns the name of the current
     * User object.
     * @return
     *      Returns the name of the User.
     */
    public String getName() {
        return name;
    }

    /**
     * A mutator method that sets the name of the current User object.
     * @param name
     *      The name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * An accessor method that returns the int value of the User's
     * index position.
     * @return
     *      Returns an int value of the index position.
     */
    public int getIndexPos() {
        return indexPos;
    }

    /**
     * A mutator method that sets the User's index position.
     * @param indexPos
     *      The index to be set.
     */
    public void setIndexPos(int indexPos) {
        this.indexPos = indexPos;
    }

    /**
     * An accessor method that returns the current user count
     * of all User objects.
     * @return
     *      Returns the current number of users.
     */
    public static int getUserCount() {
        return userCount;
    }

    /**
     * A mutator method that sets the current user count
     * of all User objects.
     * @param userCount
     *      The user count to be set.
     */
    public static void setUserCount(int userCount) {
        User.userCount = userCount;
    }


}
