package jaavajaava.smarthome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Antti on 15.2.2016.
 */
public class SmarthomeOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SmarthomeDB";

    public SmarthomeOpenHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE users ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME + " TEXT, " +
                KEY_PASSWORD + " TEXT )";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        this.onCreate(db);
    }

    // Users table name
    private static final String TABLE_USERS = "users";

    // Users table column names
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";

    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_PASSWORD};

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PASSWORD, user.getPassword());

        db.insert(TABLE_USERS, null, values);

        db.close();
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_USERS,
                        COLUMNS,
                        KEY_ID + "=? ",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        User user = new User();
        user.setId(Integer.parseInt(cursor.getString(0)));
        user.setName(cursor.getString(1));
        user.setPassword(cursor.getString(2));

        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new LinkedList<User>();

        String query = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        User user = null;
        if(cursor.moveToFirst()) {
            do{
                user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setPassword(cursor.getString(2));

                users.add(user);
            } while (cursor.moveToNext());
        }
        return users;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("password", user.getPassword());

        int i = db.update(TABLE_USERS,
                values,
                KEY_ID+" =?",
                new String[] {String.valueOf(user.getId())});

        db.close();

        return i;
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USERS,
                KEY_ID+" =?",
                new String[]{String.valueOf(user.getId())});

        db.close();
    }

    public boolean foundFromDatabase(String name, String pass) {
        Cursor cursor = getReadableDatabase().query(TABLE_USERS,
                new String[]{KEY_NAME},
                KEY_NAME + "=? AND " + KEY_PASSWORD + "=?",
                new String[]{name, pass},
                null,
                null,
                null,
                null);

        return cursor.getCount() > 0;
    }

    public Cursor getUsernames() {
        Cursor cursor = getReadableDatabase().query(TABLE_USERS,
                new String[] {KEY_ID, KEY_NAME},
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }
}
