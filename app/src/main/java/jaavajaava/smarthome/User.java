package jaavajaava.smarthome;

/**
 * Created by Antti on 15.2.2016.
 */
public class User {
    int _id;
    String name;
    String password;

    public User() {

    }

    public User(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
