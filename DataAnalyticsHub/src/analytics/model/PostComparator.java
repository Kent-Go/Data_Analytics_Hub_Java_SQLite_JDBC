/*

 * PostComparator.java
 * 
 * Version: 1.0
 *
 * Date: 01/10/2023
 * 
 * © 2023 Go Chee Kin.
 * 
 * All rights reserved.
 */
package analytics.model;

import java.util.Comparator;

/**
 * 
 * The PostComparator class implement the Comparator interface for Post object
 * to sort post based on likes in descending order
 */
public class PostComparator implements Comparator<Post> {

    /**
     * The overridden method to compare Post objects based on likes
     * 
     * @param postObject1 The first Post object
     * @param postObject2 The second Post object
     * 
     * @return 1 if second Post object's like is greater
     * @return -1 if first Post object's like is greater
     * @return 0 if both Post objects' like is equal
     * 
     */
    @Override
    public int compare(Post postObject1, Post postObject2) {
	if (postObject1.getLikes() < postObject2.getLikes())
	    return 1;
	else if (postObject1.getLikes() > postObject2.getLikes())
	    return -1;
	return 0;
    }
}
