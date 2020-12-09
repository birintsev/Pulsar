package moduleCore.entities.client.objects;
import moduleCore.entities.User;

public class UserObject {
    private User user = new User();
    public User getUserObject(){
        user.setId(null);
        user.setAge(18);
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setUsername("Username");
        user.setPassword("pass");
        user.setPhoneNumber("+389");
        return user;
    }
}
