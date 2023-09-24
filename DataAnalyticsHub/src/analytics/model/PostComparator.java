package analytics.model;

import java.util.Comparator;

public class PostComparator implements Comparator<Post> {
    @Override
    public int compare(Post postObject1, Post postObject2) {
	if (postObject1.getLikes() < postObject2.getLikes())
	    return 1;
	else if (postObject1.getLikes() > postObject2.getLikes())
	    return -1;
	return 0;
    }
}
