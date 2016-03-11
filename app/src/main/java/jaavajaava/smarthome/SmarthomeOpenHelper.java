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

    /*****************************
     * Database name and version *
     *****************************/
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SmarthomeDB";

    /*************************
     * Creating SQL commands *
     *************************/
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    /**************
     * User table *
     **************/
    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE " + SmarthomeContract.User.TABLE_NAME + " (" +
                    SmarthomeContract.User._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SmarthomeContract.User.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                    SmarthomeContract.User.COLUMN_NAME_PASSWORD + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ESTATE_TABLE =
            "DROP TABLE IF EXISTS " + SmarthomeContract.User.TABLE_NAME;

    /****************
     * Estate table *
     ****************/

    private static final String SQL_CREATE_ESTATE_TABLE =
            "CREATE TABLE " + SmarthomeContract.Estate.TABLE_NAME + " (" +
                    SmarthomeContract.Estate._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SmarthomeContract.Estate.COLUMN_NAME_ESTATENAME + TEXT_TYPE + COMMA_SEP +
                    SmarthomeContract.Estate.COLUMN_NAME_ADDRESS + TEXT_TYPE + COMMA_SEP +
                    SmarthomeContract.Estate.COLUMN_NAME_UID + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_USER_TABLE =
            "DROP TABLE IF EXISTS " + SmarthomeContract.Estate.TABLE_NAME;

    /******************
     * Constructor    *
     * @param context *
     ******************/
    public SmarthomeOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // Create tables when database is created
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_ESTATE_TABLE);
    }

    // Delete tables and replace them with new ones
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USER_TABLE);
        db.execSQL(SQL_DELETE_ESTATE_TABLE);
        onCreate(db);
    }

    /**********************
     * User table methods *
     **********************/

    // Add user to user table
    public void addUser(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SmarthomeContract.User.COLUMN_NAME_USERNAME, name);
        values.put(SmarthomeContract.User.COLUMN_NAME_PASSWORD, password);

        db.insert(SmarthomeContract.User.TABLE_NAME, null, values);
    }

    // Remove user with given id if it's not admin and return boolean about it being admin
    public boolean deleteUser(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(
                SmarthomeContract.User.TABLE_NAME,
                new String[]{SmarthomeContract.User.COLUMN_NAME_USERNAME},
                SmarthomeContract.User._ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        cursor.moveToFirst();
        if(cursor.getString(0).equals("admin")) {
            return Boolean.TRUE;
        }
        else {
            db.delete(SmarthomeContract.User.TABLE_NAME,
                    SmarthomeContract.User._ID + " =?",
                    new String[]{String.valueOf(id)});

            return Boolean.FALSE;
        }
    }

    // Return cursor containing whole user table
    public Cursor getUsers() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                SmarthomeContract.User.TABLE_NAME,
                new String[]{SmarthomeContract.User._ID, SmarthomeContract.User.COLUMN_NAME_USERNAME, SmarthomeContract.User.COLUMN_NAME_PASSWORD},
                null,
                null,
                null,
                null,
                null
        );

        return cursor;
    }

    // Check login info and return cursor
    public Cursor getUserWithPassword(String user, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        addAdmin();

        Cursor cursor = db.query(
                SmarthomeContract.User.TABLE_NAME,
                new String[]{SmarthomeContract.User._ID, SmarthomeContract.User.COLUMN_NAME_USERNAME, SmarthomeContract.User.COLUMN_NAME_PASSWORD},
                SmarthomeContract.User.COLUMN_NAME_USERNAME + "=? AND " + SmarthomeContract.User.COLUMN_NAME_PASSWORD + "=?",
                new String[]{user, password},
                null,
                null,
                null
        );

        return cursor;
    }

    // Check if user table is empty and add admin if it is
    public void addAdmin() {
        SQLiteDatabase db = this.getWritableDatabase();

        String count = "SELECT count(*) FROM " + SmarthomeContract.User.TABLE_NAME;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);

        if(icount < 1){
            addUser("admin", "admin");
        }
    }

    /************************
     * Estate table methods *
     ************************/

    // Get estates for user with given id
    public Cursor getUserEstates(long uid) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                SmarthomeContract.Estate.TABLE_NAME,
                null,
                SmarthomeContract.Estate.COLUMN_NAME_UID + "=?",
                new String[]{String.valueOf(uid)},
                null,
                null,
                null
        );

        if(cursor.getCount() > 0){
            return cursor;
        }

        else{
            return null;
        }
    }

    // Add estate
    public void addEstate(String name, String address, String uid) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SmarthomeContract.Estate.COLUMN_NAME_ESTATENAME, name);
        values.put(SmarthomeContract.Estate.COLUMN_NAME_ADDRESS, address);
        values.put(SmarthomeContract.Estate.COLUMN_NAME_UID, uid);

        db.insert(SmarthomeContract.Estate.TABLE_NAME ,null, values);
    }

    /*

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SmarthomeDB";

    public SmarthomeOpenHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE users ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME + " TEXT, " +
                KEY_PASSWORD + " TEXT )";
        String CREATE_ESTATES_TABLE = "CREATE TABLE estates ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME + " TEXT, " +
                KEY_ADDRESS + " TEXT, " +
                KEY_UID + " TEXT )";
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_ESTATES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        this.onCreate(db);
    }

    // Table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_ESTATES = "estates";

    // Users table column names
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_UID = "uid";

    private static final String[] USERS_COLUMNS = {KEY_ID,KEY_NAME,KEY_PASSWORD};

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PASSWORD, user.getPassword());

        db.insert(TABLE_USERS, null, values);

        db.close();
    }

    public User getUser(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_USERS,
                        USERS_COLUMNS,
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

    public Boolean deleteUser(long uid) {
        SQLiteDatabase db = this.getWritableDatabase();

        if(getUser(uid).name.equals("admin")) {
            return Boolean.TRUE;
        }

        db.delete(TABLE_USERS,
                KEY_ID + " =?",
                new String[]{String.valueOf(uid)});

        db.close();
        return Boolean.FALSE;
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

    public void addEstate(String name, String address, String uid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_ADDRESS, address);
        values.put(KEY_UID, uid);

        db.insert(TABLE_ESTATES, null, values);

        db.close();
    }

    public Cursor getEstates() {

        String query = "SELECT * FROM " + TABLE_ESTATES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Cursor getUserEstates(long uid) {
        Cursor cursor = getReadableDatabase().query(TABLE_ESTATES,
                new String[] {KEY_NAME, KEY_ADDRESS, KEY_UID},
                KEY_UID + "=? ",
                new String[]{String.valueOf(uid)},
                null,
                null,
                null);

        return cursor;
    }
    */
}