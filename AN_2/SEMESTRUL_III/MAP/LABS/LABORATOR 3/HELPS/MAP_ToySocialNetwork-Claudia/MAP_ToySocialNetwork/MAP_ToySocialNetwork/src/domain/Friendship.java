package domain;

public class Friendship extends Entity {
    private final String friendOneId;
    private final String friendTwoId;

    public Friendship(String id, String friendOne, String friendTwo) {
        super(id);
        this.friendOneId = friendOne;
        this.friendTwoId = friendTwo;
    }

    /**
     * Getter for first friend id
     * @return String
     */
    public String getFriendOneId() {
        return friendOneId;
    }

    /**
     * Getter for second friend id
     * @return String
     */
    public String getFriendTwoId() {
        return friendTwoId;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id='" + id + '\'' +
                ", friendOneId='" + friendOneId + '\'' +
                ", friendTwoId='" + friendTwoId + '\'' +
                '}';
    }
}
