import domain.Friendship;
import domain.Pair;
import domain.User;
import domain.exceptions.ValidationException;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import domain.validators.Validator;
import repo.file.FileRepo;
import repo.file.FriendshipFileRepo;
import repo.file.UserFileRepo;
import repo.memory.InMemoryRepo;
import service.Service;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Validator<User> validator= new UserValidator();
        UserFileRepo uRepo= new UserFileRepo("src/users.csv", validator);


        Validator<Friendship> valF= new FriendshipValidator(uRepo);
        FriendshipFileRepo fRepo= new FriendshipFileRepo("src/friendships.csv", valF);

        Service service= new Service(uRepo, fRepo);
        UI ui= new UI(service);
        ui.run();

    }
}