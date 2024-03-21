package repos.file;
import constants.Constants;
import domain.User;

import java.time.LocalDate;


public class UsersRepository extends FileRepository<User> {

    /**
     * Constructor for User repository
     * @param fileName String
     */
    public UsersRepository(String fileName) {
        super(fileName);
    }

    @Override
    protected User lineToEntity(String line) {
        String []attributes = line.split(",");
        String id = attributes[0];
        String firstName = attributes[1];
        String lastName = attributes[2];
        String email = attributes[3];
        String passwordHash = attributes[4];
        LocalDate birthday = LocalDate.parse(attributes[5], Constants.STANDARD_DATE_FORMAT);

        return new User(id, firstName, lastName, email, passwordHash, birthday);
    }

    @Override
    protected String entityToLine(User user) {
        return user.getId() + "," + user.getFirstName() + "," + user.getLastName() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getBirthday().format(Constants.STANDARD_DATE_FORMAT);
    }

    @Override
    public void update(User entity) {

    }
}
