package br.com.augustoccesar.model.filter.test;

import br.com.augustoccesar.model.filter.FilterBuilder;

import javax.persistence.OneToOne;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main class used for tests.
 *
 * Created by augustoccesar on 1/29/16.
 */
public class MainTest {
    public static void main(String[] args){
        User user = new User();
        Profile profile = new Profile();

        profile.setId((long)1);
        profile.setFirstName("Augusto Cesar");
        profile.setLastName("Freitas e Silva");

        user.setId((long)1);
        user.setEmail("augusto.acfs@gmail.com");
        user.setUsername("augustoccesar");
        user.setProfile(profile);

        Comment c1 = new Comment((long)1, "Comment 1", (long)1400000, null);
        Comment c2 = new Comment((long)2, "Comment 2", (long)1600000, null);
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(c1);
        comments.add(c2);

        user.setComments(comments);

        new FilterBuilder(user).select("id", "profile.id", "profile.firstName", "comments.id", "comments.text").build();
    }
}
