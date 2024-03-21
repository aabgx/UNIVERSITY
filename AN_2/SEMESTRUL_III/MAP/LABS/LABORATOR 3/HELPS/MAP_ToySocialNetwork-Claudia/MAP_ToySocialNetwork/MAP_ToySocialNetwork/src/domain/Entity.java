package domain;

import java.util.Objects;
import java.util.Random;

public class Entity {
    protected final String id;

    /**
     * Generate a random id for the entity
     * @return String
     */
    private static String generateId() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Entity constructor
     * @param id
     */
    public Entity(String id) {
        if(id == null) {
            id = generateId();
        }
        this.id = id;
    }

    /**
     * Ig getter
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Comparison function
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id.equals(entity.id);
    }

    /**
     * Hashcode function
     * @return String
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id='" + id + '\'' +
                '}';
    }
}
