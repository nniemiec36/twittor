/**
 * A class that implements a Comparator to compare the values
 * between two follower counts of two Users.
 *
 * @author
 * Nicole Niemiec
 * CSE 214 R08 FELIX
 * DECEMBER 3RD, HW #7
 *
 * @version 1
 */

import java.util.Comparator;
import java.io.Serializable;

public class FollowersComparator implements Comparator<User>{

    public int compare(User u1, User u2){
        int pos1 = u1.getIndexPos();
        int pos2 = u2.getIndexPos();

        if(u1.getFollowers().size() == u2.getFollowers().size())
            return 0;
        else if(u1.getFollowers().size() < u2.getFollowers().size())
            return 1;
        else
            return -1;

    }

}
