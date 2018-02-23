package hms.codefest.elves.data;

import java.util.HashMap;
import java.util.Map;

public class UserRepo {

    private Map<String, User> users = new HashMap<>();
    private Map<String, User> usersById = new HashMap<>();

    public void init() {
        User user1 = new User("ranjana", "U5c2208351ef1a0aeea2be23596710c95", "R@njanaH745");
        User user2 = new User("aroshar", "U5c2208351ef1a0aeea2be23596710c95", "A#rosha@456");
        User user3 = new User("prabathw", "U5c2208351ef1a0aeea2be23596710c95", "PrabathW@567");
        users.put("ranjana", user1);
        users.put("aroshar", user2);
        users.put("prabathw", user3);
        usersById.put("U5c2208351ef1a0aeea2be23596710c95", user2);
    }

    public User findUser(String userName) {
        if(users.containsKey(userName)) {
            return users.get(userName);
        } else {
            return new User("DummyUser", "1", "");
        }
    }

    public User findUserById(String userId) {
        if(usersById.containsKey(userId)) {
            return usersById.get(userId);
        } else {
            return new User("DummyUser", "1", "");
        }
    }

}
