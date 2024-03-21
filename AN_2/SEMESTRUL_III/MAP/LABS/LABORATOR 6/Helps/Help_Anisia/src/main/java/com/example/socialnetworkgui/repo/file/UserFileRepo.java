package com.example.socialnetworkgui.repo.file;

import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.validators.Validator;

import java.util.List;

/**
 * Implements entityToString and stringToEntity in base class
 */
public class UserFileRepo extends FileRepo<Long, User>{
    public UserFileRepo(String fName, Validator<User> val) {
        super(fName, val);
    }

    /**
     * Implements method in abstarct FileRepo class
     * @param entity, given entity
     * @return String, string representation of entity
     */
    @Override
    public String entityToString(User entity) {
        return entity.getId()+";"+entity.getFirstName()+";"+entity.getLastName()+";"+entity.getEmail();
    }

    /**
     * Returns entity with given attributes
     * @param attributes, List<String>, list of attributes
     * @return User, user created with given attributes
     */
    @Override
    public User StringToEntity(List<String> attributes) {
        User u= new User(attributes.get(1), attributes.get(2), attributes.get(3));
        u.setId(Long.parseLong(attributes.get(0)));
        //id-ul se genereaza automat in service
        return u;
    }
}
