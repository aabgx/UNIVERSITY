package com.example.socialnetworkgui.repo.file;

import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.Pair;
import com.example.socialnetworkgui.domain.validators.Validator;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.socialnetworkgui.utils.Constants.DATE_TIME_FORMATTER;

public class FriendshipFileRepo extends FileRepo<Pair<Long, Long>, Friendship>{
    public FriendshipFileRepo(String fName, Validator<Friendship> val) {
        super(fName, val);
    }

    /**
     * Returns string representation of given Friendship object
     * @param entity, Friendship, entity to be converted
     * @return String, representation of Friendship
     */
    @Override
    public String entityToString(Friendship entity) {

        return entity.getId().getFirst()+";"+entity.getId().getSecond()+";"+entity.getDate().format(DATE_TIME_FORMATTER);
    }

    /**
     * Creates new Friendship with given attributes
     * @param attributes, List<String>, list of attributes
     * @return Friendship, new created friendship
     */
    @Override
    public Friendship StringToEntity(List<String> attributes) {
        String dateString= attributes.get(2);
        LocalDateTime dateFromString= LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
        Friendship f= new Friendship(Long.parseLong(attributes.get(0)), Long.parseLong(attributes.get(1)), dateFromString);
        return f;
    }
}
