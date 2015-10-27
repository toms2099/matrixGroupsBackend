package com.openmarket;

import java.util.List;

public interface UserRepository {
    List<String> getUsers(final String keyword);
    
    List<String> getGroups();
}
