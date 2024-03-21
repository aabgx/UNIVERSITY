package repos.file;

import domain.Friendship;

public class FriendshipRepository extends FileRepository<Friendship> {

    public FriendshipRepository(String fileName) {
        super(fileName);
    }

    @Override
    protected Friendship lineToEntity(String line) {
        String []attributes = line.split(",");
        String id = attributes[0];
        String friendOneId = attributes[1];
        String friendTwoId = attributes[2];
        return new Friendship(id, friendOneId, friendTwoId);
    }

    @Override
    protected String entityToLine(Friendship friendship) {
        return friendship.getId() + "," + friendship.getFriendOneId() + "," + friendship.getFriendTwoId();
    }

    @Override
    public void update(Friendship entity) {

    }
}
