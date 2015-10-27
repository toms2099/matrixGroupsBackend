package com.openmarket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

//@Component
public class StaticUserRepository implements UserRepository {
    @Override
    public List<String> getUsers(String keyword) {
        List<String> peeps = new ArrayList<>();

        // peeps.add("@toms:matrix.org");
        peeps.add("@alastaird:matrix.org");
        // peeps.add("@rwyrobek:matrix.org");
        // peeps.add("@zevans:matrix.org");

        // peeps.add("@+447473157070:matrix.openmarket.com");

        return peeps;
    }
}
