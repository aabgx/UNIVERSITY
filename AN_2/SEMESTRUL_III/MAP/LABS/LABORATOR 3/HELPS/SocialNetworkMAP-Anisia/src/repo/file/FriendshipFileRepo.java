package repo.file;

import domain.Friendship;
import domain.Pair;
import domain.User;
import domain.validators.Validator;

import java.util.List;

public class FriendshipFileRepo extends FileRepo<Pair<Long, Long>, Friendship>{

    public FriendshipFileRepo(String fName, Validator<Friendship> val) {
        super(fName, val);
    }

    /**
     * Returns string representation of given Friendship object
     * @param entity, Friendship, entity to be cpnverted
     * @return String, representation of Friendship
     */
    @Override
    public String entityToString(Friendship entity) {
        return entity.getId().getFirst()+";"+entity.getId().getSecond();
    }

    /**
     * Creates new Friendship with given attributes
     * @param attributes, List<String>, list of attributes
     * @return Friendship, new created friendship
     */
    @Override
    public Friendship StringToEntity(List<String> attributes) {
        Friendship f= new Friendship(Long.parseLong(attributes.get(0)), Long.parseLong(attributes.get(1)));
        return f;
    }
}
