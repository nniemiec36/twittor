/**
 * A class that implements a Comparator to compare the values
 * between two names of the Users.
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

public class NameComparator implements Comparator<User> {
    public int compare(User u1, User u2){
        return (u1.getName().compareTo(u2.getName()));
    }
}
