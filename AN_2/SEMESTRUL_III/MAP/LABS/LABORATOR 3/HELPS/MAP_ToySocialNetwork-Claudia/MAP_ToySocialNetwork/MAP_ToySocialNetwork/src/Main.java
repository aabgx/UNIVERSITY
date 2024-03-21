import domain.Friendship;
import domain.User;
import repos.db.FriendshipsDbRepository;
import repos.db.UsersDbRepository;
import repos.file.FriendshipRepository;
import repos.Repository;
import repos.file.UsersRepository;
import service.Service;
import ui.UI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/social_network", "postgres", "ubb22");

            //Repository<User> userRepository = new UsersRepository("users.txt");
            Repository<User> userRepository = new UsersDbRepository(connection);

            //Repository<Friendship> friendshipRepository = new FriendshipRepository("friends.txt");
            Repository<Friendship> friendshipRepository = new FriendshipsDbRepository(connection);

            Service service = new Service(userRepository, friendshipRepository);

            UI ui = new UI(service);

            ui.start();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
