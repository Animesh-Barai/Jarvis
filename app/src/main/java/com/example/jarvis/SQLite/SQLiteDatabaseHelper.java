package com.example.jarvis.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Gravity;
import android.widget.Toast;

import com.example.jarvis.Journal.Journal;
import com.example.jarvis.Todo.Task;
import com.example.jarvis.UserHandling.User;
import com.example.jarvis.Wallet.Record;

import java.util.ArrayList;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    /*** Database ***/
    private static final String DATABASE_NAME = "database_local.db";

    /*** Others ***/
    private Context context;
    private static int VERSION_NUMBER = 10;
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    /*** TABLE USER ***/
    private static final String TABLE_USER = "table_user";
    private static final String USER_ID = "uid";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_NAME = "user_name";
    private static final String USER_PHOTO = "user_photo";
    private static final String USER_DEVICE = "user_device";
    private static final String USER_SYNC_TIME = "user_syncTime";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" +
            USER_ID + " TEXT PRIMARY KEY, " +
            USER_EMAIL + " TEXT NOT NULL, " +
            USER_NAME + " TEXT NOT NULL, " +
            USER_PHOTO + " TEXT NOT NULL, " +
            USER_DEVICE + " TEXT NOT NULL, " +
            USER_SYNC_TIME + " TEXT NOT NULL); ";


    /*** TABLE TODO ***/
    private static final String TABLE_TODO = "table_todo";
    private static final String TODO_TITLE = "todo_title";
    private static final String TODO_DESCRIPTION = "todo_description";

    private static final String TODO_YEAR = "todo_year";
    private static final String TODO_MONTH = "todo_month";
    private static final String TODO_DAY = "todo_day";

    private static final String TODO_HOUR = "todo_hour";
    private static final String TODO_MINUTE = "todo_minute";

    private static final String TODO_REMINDER_STATE = "todo_reminderState";
    private static final String TODO_IS_COMPLETED = "todo_isCompleted";
    private static final String TODO_IS_DELETED = "todo_isDeleted";
    private static final String TODO_IS_IGNORED = "todo_isIgnored";
    private static final String TODO_UPDATE_TIMESTAMP ="todo_updateTimestamp";


    private static final String CREATE_TABLE_TODO = "CREATE TABLE " + TABLE_TODO + "(" +
            TODO_TITLE + " TEXT NOT NULL, " +
            TODO_DESCRIPTION + " TEXT, " +

            TODO_YEAR + " TEXT NOT NULL, " +
            TODO_MONTH + " TEXT NOT NULL, " +
            TODO_DAY + " TEXT NOT NULL, " +

            TODO_HOUR + " TEXT, " +
            TODO_MINUTE + " TEXT, " +

            TODO_REMINDER_STATE + " INTEGER, " +
            TODO_IS_COMPLETED + " INTEGER, " +
            TODO_IS_DELETED + " INTEGER, " +
            TODO_IS_IGNORED + " INTEGER, " +
            TODO_UPDATE_TIMESTAMP + " TEXT, " +
            "PRIMARY KEY(" + TODO_TITLE + ", " + TODO_YEAR + ", " + TODO_MONTH + ", " + TODO_DAY +  ")); ";


    /*** TABLE WALLET ***/
    private static final String TABLE_WALLET = "table_wallet";
    private static final String WALLET_TITLE = "wallet_title";
    private static final String WALLET_DESCRIPTION = "wallet_description";

    private static final String WALLET_YEAR = "wallet_year";
    private static final String WALLET_MONTH = "wallet_month";
    private static final String WALLET_DAY = "wallet_day";

    private static final String WALLET_TYPE = "wallet_type";
    private static final String WALLET_AMOUNT = "wallet_amount";

    private static final String WALLET_IS_DELETED = "wallet_isDeleted";
    private static final String WALLET_IS_IGNORED = "wallet_isIgnored";
    private static final String WALLET_UPDATE_TIMESTAMP = "wallet_updateTimestamp";


    private static final String CREATE_TABLE_WALLET = "CREATE TABLE " + TABLE_WALLET + "(" +
            WALLET_TITLE + " TEXT NOT NULL, " +
            WALLET_DESCRIPTION + " TEXT, " +

            WALLET_YEAR + " TEXT NOT NULL, " +
            WALLET_MONTH + " TEXT NOT NULL, " +
            WALLET_DAY + " TEXT NOT NULL, " +

            WALLET_TYPE + " INTEGER NOT NULL, " +
            WALLET_AMOUNT + " TEXT, " +
            WALLET_IS_DELETED + " INTEGER, " +
            WALLET_IS_IGNORED + " INTEGER, " +
            WALLET_UPDATE_TIMESTAMP + " TEXT, " +

            "PRIMARY KEY(" + WALLET_TITLE + ", " + WALLET_YEAR  + ", " + WALLET_MONTH + ", " + WALLET_DAY + ", " + WALLET_TYPE + ")); ";


    /***Table Journal***/
    // image_link, file_name
    private static final String TABLE_JOURNAL = "table_journal";
    private static final String JOURNAL_TITLE = "journal_title";
    private static final String JOURNAL_DESCRIPTION = "journal_description";

    private static final String JOURNAL_YEAR = "journal_year";
    private static final String JOURNAL_MONTH = "journal_month";
    private static final String JOURNAL_DAY = "journal_day";

    private static final String JOURNAL_HOUR = "journal_hour";
    private static final String JOURNAL_MINUTE = "journal_minute";

    private static final String JOURNAL_IMAGE_LINK = "journal_imageLink";
    private static final String JOURNAL_FILE_LINK = "journal_fileLink";

    private static final String CREATE_TABLE_JOURNAL = "CREATE TABLE " + TABLE_JOURNAL + "(" +
            JOURNAL_TITLE + " TEXT, " +
            JOURNAL_DESCRIPTION + " TEXT, " +

            JOURNAL_YEAR + " TEXT NOT NULL, " +
            JOURNAL_MONTH + " TEXT NOT NULL, " +
            JOURNAL_DAY + " TEXT NOT NULL, " +

            JOURNAL_HOUR + " TEXT, " +
            JOURNAL_MINUTE + " TEXT, " +

            JOURNAL_IMAGE_LINK + " TEXT NOT NULL, " +
            JOURNAL_FILE_LINK + " TEXT NOT NULL, " +

            "PRIMARY KEY(" + JOURNAL_FILE_LINK + ")); ";


    /*** Constructor ***/
    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            /*** To Create Tables ***/
            sqLiteDatabase.execSQL(CREATE_TABLE_USER);
            sqLiteDatabase.execSQL(CREATE_TABLE_TODO);
            sqLiteDatabase.execSQL(CREATE_TABLE_WALLET);
        }
        catch (Exception e){
            showToast("Exception : " + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            // To Drop & Recreate Tables
            sqLiteDatabase.execSQL(DROP_TABLE+TABLE_USER);
            sqLiteDatabase.execSQL(DROP_TABLE+TABLE_TODO);
            sqLiteDatabase.execSQL(DROP_TABLE+TABLE_WALLET);
            sqLiteDatabase.execSQL(DROP_TABLE+TABLE_JOURNAL);
            onCreate(sqLiteDatabase);
        } catch (Exception e) {
            showToast("Exception : " + e);
        }
    }

    public void refreshDatabase(SQLiteDatabase sqLiteDatabase){
        try {
            sqLiteDatabase.execSQL(DROP_TABLE + TABLE_USER);
            sqLiteDatabase.execSQL(DROP_TABLE + TABLE_TODO);
            sqLiteDatabase.execSQL(DROP_TABLE + TABLE_WALLET);
            onCreate(sqLiteDatabase);
        } catch (Exception e){
            showToast("Exception : " + e);
        }
    }

    /*** Query on TABLE_USER ***/

    public void insertUser(User user){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String uid = user.getUid();
        String email = user.getEmail();
        String name = user.getName();
        String photo = user.getPhoto();
        String device = user.getDevice();
        String syncTime = user.getSyncTime();

        contentValues.put(USER_ID, uid);
        contentValues.put(USER_EMAIL, email);
        contentValues.put(USER_NAME, name);
        contentValues.put(USER_PHOTO, photo);
        contentValues.put(USER_DEVICE, device);
        contentValues.put(USER_SYNC_TIME, syncTime);

        Boolean result = findUser(uid);
        if(!result) {
            sqLiteDatabase.insert(TABLE_USER, null, contentValues);
        }
    }

    public Boolean findUser(String uid){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();


        Cursor cursor = sqLiteDatabase.query(TABLE_USER,
                null,
                USER_ID +  " = ?",
                new String[] {uid},
                null,
                null,
                null);


        cursor.moveToPosition(0);

        if(cursor.getCount()==0)
            return false;

        return true;
    }

    public User getUser(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *" +
                " FROM " + TABLE_USER + ";", null);

        cursor.moveToPosition(0);
        return new User(cursor.getString(cursor.getColumnIndex(USER_ID)),
                cursor.getString(cursor.getColumnIndex(USER_EMAIL)),
                cursor.getString(cursor.getColumnIndex(USER_NAME)),
                cursor.getString(cursor.getColumnIndex(USER_PHOTO)),
                cursor.getString(cursor.getColumnIndex(USER_DEVICE)),
                cursor.getString(cursor.getColumnIndex(USER_SYNC_TIME))
        );
    }

    public String getUid(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *" +
                " FROM " + TABLE_USER + ";", null);

        cursor.moveToPosition(0);
        String result = cursor.getString(cursor.getColumnIndex(USER_ID));
        return result;
    }


    public String getSyncTime(String uid){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_USER,
                null,
                USER_ID +  " = ?",
                new String[] {uid},
                null,
                null,
                null);

        cursor.moveToPosition(0);

        String result = cursor.getString(cursor.getColumnIndex(USER_SYNC_TIME));
        return result;
    }

    public void updateSyncTime(String uid){
        SQLiteDatabase sqLiteDatabase1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursor = sqLiteDatabase1.query(TABLE_USER,
                null,
                USER_ID +  " = ?",
                new String[] {uid},
                null,
                null,
                null);

        cursor.moveToPosition(0);

        // Getting Current Timestamp
        Long tsLong = System.currentTimeMillis()/1000;
        String timestamp = tsLong.toString();

        contentValues.put(USER_ID, cursor.getString(0));
        contentValues.put(USER_EMAIL, cursor.getString(1));
        contentValues.put(USER_NAME, cursor.getString(2));
        contentValues.put(USER_PHOTO, cursor.getString(3));
        contentValues.put(USER_DEVICE, cursor.getString(4));
        contentValues.put(USER_SYNC_TIME, timestamp);

        sqLiteDatabase1.update(TABLE_USER, contentValues,
                USER_ID + " = ?",
                new String[] {uid});

        sqLiteDatabase1.close();
    }


    /*** Query on TABLE_TODO ***/

    public long insertTodo(Task task){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String title = task.getTitle();
        String description = task.getDescription();

        String year = task.getYear();
        String month = task.getMonth();
        String day = task.getDay();

        String hour = task.getHour();
        String minute = task.getMinute();

        Integer reminderState = task.getReminderState();
        Integer isCompleted = task.getIsCompleted();
        Integer isDeleted =  task.getIsDeleted();
        Integer isIgnored =  task.getIsIgnored();

        String updateTimestamp = task.getUpdateTimestamp();

        contentValues.put(TODO_TITLE, title);
        contentValues.put(TODO_DESCRIPTION, description);

        contentValues.put(TODO_YEAR, year);
        contentValues.put(TODO_MONTH, month);
        contentValues.put(TODO_DAY, day);

        contentValues.put(TODO_HOUR, hour);
        contentValues.put(TODO_MINUTE, minute);

        contentValues.put(TODO_REMINDER_STATE, reminderState);
        contentValues.put(TODO_IS_COMPLETED, isCompleted);
        contentValues.put(TODO_IS_DELETED, isDeleted);
        contentValues.put(TODO_IS_IGNORED, isIgnored);
        contentValues.put(TODO_UPDATE_TIMESTAMP, updateTimestamp);

        long rowId = sqLiteDatabase.insert(TABLE_TODO, null, contentValues);
        return rowId;
    }

    public void insertAllTodos(ArrayList<Task> tasks){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        for(Task task: tasks) {

            ContentValues contentValues = new ContentValues();

            String title = task.getTitle();
            String description = task.getDescription();

            String year = task.getYear();
            String month = task.getMonth();
            String day = task.getDay();

            String hour = task.getHour();
            String minute = task.getMinute();

            Integer reminderState = task.getReminderState();
            Integer isCompleted = task.getIsCompleted();
            Integer isDeleted = task.getIsDeleted();
            Integer isIgnored = task.getIsIgnored();

            String updateTimestamp = task.getUpdateTimestamp();

            contentValues.put(TODO_TITLE, title);
            contentValues.put(TODO_DESCRIPTION, description);

            contentValues.put(TODO_YEAR, year);
            contentValues.put(TODO_MONTH, month);
            contentValues.put(TODO_DAY, day);

            contentValues.put(TODO_HOUR, hour);
            contentValues.put(TODO_MINUTE, minute);

            contentValues.put(TODO_REMINDER_STATE, reminderState);
            contentValues.put(TODO_IS_COMPLETED, isCompleted);
            contentValues.put(TODO_IS_DELETED, isDeleted);
            contentValues.put(TODO_IS_IGNORED, isIgnored);
            contentValues.put(TODO_UPDATE_TIMESTAMP, updateTimestamp);

            long rowId = sqLiteDatabase.insert(TABLE_TODO, null, contentValues);
        }
    }

    public void updateTodo(Task task, String oldYear, String oldMonth, String oldDay, String oldTitle){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String title = task.getTitle();
        String description = task.getDescription();

        String year = task.getYear();
        String month = task.getMonth();
        String day = task.getDay();

        String hour = task.getHour();
        String minute = task.getMinute();

        Integer reminderState = task.getReminderState();
        Integer isCompleted = task.getIsCompleted();
        Integer isDeleted =  task.getIsDeleted();
        Integer isIgnored =  task.getIsIgnored();

        String updateTimestamp = task.getUpdateTimestamp();

        contentValues.put(TODO_TITLE, title);
        contentValues.put(TODO_DESCRIPTION, description);

        contentValues.put(TODO_YEAR, year);
        contentValues.put(TODO_MONTH, month);
        contentValues.put(TODO_DAY, day);

        contentValues.put(TODO_HOUR, hour);
        contentValues.put(TODO_MINUTE, minute);

        contentValues.put(TODO_REMINDER_STATE, reminderState);
        contentValues.put(TODO_IS_COMPLETED, isCompleted);
        contentValues.put(TODO_IS_DELETED, isDeleted);
        contentValues.put(TODO_IS_IGNORED, isIgnored);
        contentValues.put(TODO_UPDATE_TIMESTAMP, updateTimestamp);

        sqLiteDatabase.update(TABLE_TODO, contentValues,
                TODO_YEAR + " = ? AND " + TODO_MONTH + " = ? AND " + TODO_DAY + " = ? AND " + TODO_TITLE + " = ?",
                new String[] {oldYear, oldMonth, oldDay, oldTitle});
    }

    public Task findTodo(String year, String month, String day, String title){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_TODO,
                null,
                TODO_YEAR + " = ? AND " + TODO_MONTH + " = ? AND " + TODO_DAY + " = ? AND " + TODO_TITLE + " = ?",
                new String[] {year, month, day, title},
                null,
                null,
                TODO_YEAR + " ASC, " + TODO_MONTH  + " ASC, " + TODO_DAY  + " ASC");


        cursor.moveToPosition(0);
        sqLiteDatabase.close();

        return new Task(cursor.getString(cursor.getColumnIndex(TODO_TITLE)),
                cursor.getString(cursor.getColumnIndex(TODO_DESCRIPTION)),

                cursor.getString(cursor.getColumnIndex(TODO_YEAR)),
                cursor.getString(cursor.getColumnIndex(TODO_MONTH)),
                cursor.getString(cursor.getColumnIndex(TODO_DAY)),

                cursor.getString(cursor.getColumnIndex(TODO_HOUR)),
                cursor.getString(cursor.getColumnIndex(TODO_MINUTE)),

                Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_REMINDER_STATE))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_IS_COMPLETED))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_IS_DELETED))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_IS_IGNORED))),
                cursor.getString(cursor.getColumnIndex(TODO_UPDATE_TIMESTAMP))
        );
    }

    public void deleteTodo(String year, String month, String day, String title){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Finding the task
        // Setting isDelete = 1
        // Updating that on database
        // Temporarily Delete Done
        Task task = findTodo(year, month, day, title);
        task.setIsDeleted(1);
        this.updateTodo(task, year, month, day, title);

        sqLiteDatabase.close();
    }

    public void permanentlyDeleteTodo(String year, String month, String day, String title){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Permanently Delete Done
        int i = sqLiteDatabase.delete(TABLE_TODO,
                TODO_YEAR + " = ? AND " + TODO_MONTH + " = ? AND " + TODO_DAY + " = ? AND " + TODO_TITLE + " = ?",
                new String[] {year, month, day, title});

        sqLiteDatabase.close();
    }

    public void restoreTodo(String year, String month, String day, String title){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Task task = findTodo(year, month, day, title);
        task.setIsDeleted(0);
        this.updateTodo(task, year, month, day, title);

        sqLiteDatabase.close();
    }

    public ArrayList<Task> loadTodoItems(){
        ArrayList<Task> tasks = new ArrayList<Task>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT "

                + TODO_TITLE + ", "
                + TODO_DESCRIPTION + ", "
                + TODO_YEAR + ", "
                + TODO_MONTH + ", "
                + TODO_DAY + ", "
                + TODO_HOUR + ", "
                + TODO_MINUTE + ", "
                + TODO_REMINDER_STATE + ", "
                + TODO_IS_COMPLETED + ", "
                + TODO_IS_DELETED + ", "
                + TODO_IS_IGNORED + ", "
                + TODO_UPDATE_TIMESTAMP +

                " FROM " + TABLE_TODO +
                " WHERE " + TODO_IS_DELETED + " = 0 AND " + TODO_IS_IGNORED + " = 0" +
                " ORDER BY " + TODO_YEAR + ", " + TODO_MONTH + ", " + TODO_DAY + ", " + TODO_IS_COMPLETED + ", " + TODO_TITLE + ";", null);

        cursor.moveToPosition(0);
        if(cursor.getCount() == 0){
          showToast("No Data Found");
        }
        else{
            do{
                String title = cursor.getString(cursor.getColumnIndex(TODO_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(TODO_DESCRIPTION));

                String year = cursor.getString(cursor.getColumnIndex(TODO_YEAR));
                String month = cursor.getString(cursor.getColumnIndex(TODO_MONTH));
                String day = cursor.getString(cursor.getColumnIndex(TODO_DAY));

                String hour = cursor.getString(cursor.getColumnIndex(TODO_HOUR));
                String minute = cursor.getString(cursor.getColumnIndex(TODO_MINUTE));

                Integer reminderState = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_REMINDER_STATE)));
                Integer isCompleted = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_IS_COMPLETED)));
                Integer isDeleted = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_IS_DELETED)));
                Integer isIgnored = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_IS_IGNORED)));

                String updateTimestamp = cursor.getString(cursor.getColumnIndex(TODO_UPDATE_TIMESTAMP));

                tasks.add(new Task(title, description, year, month, day, hour, minute, reminderState, isCompleted, isDeleted, isIgnored, updateTimestamp));
            }while(cursor.moveToNext());
        }

        return tasks;
    }

    public ArrayList<Task> syncTodoItems(){
        ArrayList<Task> tasks = new ArrayList<Task>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT "

                + TODO_TITLE + ", "
                + TODO_DESCRIPTION + ", "
                + TODO_YEAR + ", "
                + TODO_MONTH + ", "
                + TODO_DAY + ", "
                + TODO_HOUR + ", "
                + TODO_MINUTE + ", "
                + TODO_REMINDER_STATE + ", "
                + TODO_IS_COMPLETED + ", "
                + TODO_IS_DELETED + ", "
                + TODO_IS_IGNORED + ", "
                + TODO_UPDATE_TIMESTAMP +

                " FROM " + TABLE_TODO +
                " ORDER BY " + TODO_YEAR + ", " + TODO_MONTH + ", " + TODO_DAY + ", " + TODO_IS_COMPLETED + ", " + TODO_TITLE + ";", null);

        cursor.moveToPosition(0);
        if(cursor.getCount() == 0){
            showToast("No Data Found");
        }
        else{
            do{
                String title = cursor.getString(cursor.getColumnIndex(TODO_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(TODO_DESCRIPTION));

                String year = cursor.getString(cursor.getColumnIndex(TODO_YEAR));
                String month = cursor.getString(cursor.getColumnIndex(TODO_MONTH));
                String day = cursor.getString(cursor.getColumnIndex(TODO_DAY));

                String hour = cursor.getString(cursor.getColumnIndex(TODO_HOUR));
                String minute = cursor.getString(cursor.getColumnIndex(TODO_MINUTE));

                Integer reminderState = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_REMINDER_STATE)));
                Integer isCompleted = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_IS_COMPLETED)));
                Integer isDeleted = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_IS_DELETED)));
                Integer isIgnored = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_IS_IGNORED)));

                String updateTimestamp = cursor.getString(cursor.getColumnIndex(TODO_UPDATE_TIMESTAMP));

                tasks.add(new Task(title, description, year, month, day, hour, minute, reminderState, isCompleted, isDeleted, isIgnored, updateTimestamp));
            }while(cursor.moveToNext());
        }

        return tasks;
    }

    public ArrayList<Task> loadTodoBinItems(){
        ArrayList<Task> tasks = new ArrayList<Task>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT "

                + TODO_TITLE + ", "
                + TODO_DESCRIPTION + ", "
                + TODO_YEAR + ", "
                + TODO_MONTH + ", "
                + TODO_DAY + ", "
                + TODO_HOUR + ", "
                + TODO_MINUTE + ", "
                + TODO_REMINDER_STATE + ", "
                + TODO_IS_COMPLETED + ", "
                + TODO_IS_DELETED + ", "
                + TODO_IS_IGNORED + ", "
                + TODO_UPDATE_TIMESTAMP +

                " FROM " + TABLE_TODO +
                " WHERE " + TODO_IS_DELETED + " = 1 AND " + TODO_IS_IGNORED + " = 0" +
                " ORDER BY " + TODO_YEAR + ", " + TODO_MONTH + ", " + TODO_DAY + ", " + TODO_IS_COMPLETED + ", " + TODO_TITLE + ";", null);

        cursor.moveToPosition(0);
        if(cursor.getCount() == 0){
            showToast("No Data Found");
        }
        else{
            do{
                String title = cursor.getString(cursor.getColumnIndex(TODO_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(TODO_DESCRIPTION));

                String year = cursor.getString(cursor.getColumnIndex(TODO_YEAR));
                String month = cursor.getString(cursor.getColumnIndex(TODO_MONTH));
                String day = cursor.getString(cursor.getColumnIndex(TODO_DAY));

                String hour = cursor.getString(cursor.getColumnIndex(TODO_HOUR));
                String minute = cursor.getString(cursor.getColumnIndex(TODO_MINUTE));

                Integer reminderState = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_REMINDER_STATE)));
                Integer isCompleted = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_IS_COMPLETED)));
                Integer isDeleted = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_IS_DELETED)));
                Integer isIgnored = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TODO_IS_IGNORED)));

                String updateTimestamp = cursor.getString(cursor.getColumnIndex(TODO_UPDATE_TIMESTAMP));

                tasks.add(new Task(title, description, year, month, day, hour, minute, reminderState, isCompleted, isDeleted, isIgnored, updateTimestamp));
            }while(cursor.moveToNext());
        }

        return tasks;
    }



    /*** Query on TABLE_WALLET ***/

    public long insertRecord(Record record){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String title = record.getTitle();
        String description = record.getDescription();

        String year = record.getYear();
        String month = record.getMonth();
        String day = record.getDay();

        Integer type = record.getType();
        String amount = record.getAmount();

        Integer isDeleted = record.getIsDeleted();
        Integer isIgnored = record.getIsIgnored();

        String updateTimestamp = record.getUpdateTimestamp();

        contentValues.put(WALLET_TITLE, title);
        contentValues.put(WALLET_DESCRIPTION, description);

        contentValues.put(WALLET_YEAR, year);
        contentValues.put(WALLET_MONTH, month);
        contentValues.put(WALLET_DAY, day);

        contentValues.put(WALLET_TYPE, type);
        contentValues.put(WALLET_AMOUNT, amount);

        contentValues.put(WALLET_IS_DELETED, isDeleted);
        contentValues.put(WALLET_IS_IGNORED, isIgnored);

        contentValues.put(WALLET_UPDATE_TIMESTAMP, updateTimestamp);

        long rowId = sqLiteDatabase.insert(TABLE_WALLET, null, contentValues);
        return rowId;
    }

    public void insertAllRecords(ArrayList<Record> records){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        for(Record record:records) {

            ContentValues contentValues = new ContentValues();

            String title = record.getTitle();
            String description = record.getDescription();

            String year = record.getYear();
            String month = record.getMonth();
            String day = record.getDay();

            Integer type = record.getType();
            String amount = record.getAmount();

            Integer isDeleted = record.getIsDeleted();
            Integer isIgnored = record.getIsIgnored();

            String updateTimestamp = record.getUpdateTimestamp();

            contentValues.put(WALLET_TITLE, title);
            contentValues.put(WALLET_DESCRIPTION, description);

            contentValues.put(WALLET_YEAR, year);
            contentValues.put(WALLET_MONTH, month);
            contentValues.put(WALLET_DAY, day);

            contentValues.put(WALLET_TYPE, type);
            contentValues.put(WALLET_AMOUNT, amount);

            contentValues.put(WALLET_IS_DELETED, isDeleted);
            contentValues.put(WALLET_IS_IGNORED, isIgnored);

            contentValues.put(WALLET_UPDATE_TIMESTAMP, updateTimestamp);

            long rowId = sqLiteDatabase.insert(TABLE_WALLET, null, contentValues);

        }
    }

    public void updateRecord(Record record, String oldYear, String oldMonth, String oldDay, String oldTitle, Integer oldType){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String title = record.getTitle();
        String description = record.getDescription();

        String year = record.getYear();
        String month = record.getMonth();
        String day = record.getDay();

        Integer type = record.getType();
        String amount = record.getAmount();

        Integer isDeleted = record.getIsDeleted();
        Integer isIgnored = record.getIsIgnored();

        String updateTimestamp = record.getUpdateTimestamp();

        contentValues.put(WALLET_TITLE, title);
        contentValues.put(WALLET_DESCRIPTION, description);

        contentValues.put(WALLET_YEAR, year);
        contentValues.put(WALLET_MONTH, month);
        contentValues.put(WALLET_DAY, day);

        contentValues.put(WALLET_TYPE, type);
        contentValues.put(WALLET_AMOUNT, amount);

        contentValues.put(WALLET_IS_DELETED, isDeleted);
        contentValues.put(WALLET_IS_IGNORED, isIgnored);

        contentValues.put(WALLET_UPDATE_TIMESTAMP, updateTimestamp);

        sqLiteDatabase.update(TABLE_WALLET, contentValues,
                WALLET_YEAR + " = ? AND " + WALLET_MONTH + " = ? AND " + WALLET_DAY + " = ? AND " + WALLET_TITLE + " = ? AND " + WALLET_TYPE + " = ?",
                new String[] {oldYear, oldMonth, oldDay, oldTitle, oldType+""});

        sqLiteDatabase.close();
    }

    public Record findRecord(String year, String month, String day, String title, Integer type) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_WALLET,
                null,
                WALLET_YEAR + " = ? AND " + WALLET_MONTH + " = ? AND " + WALLET_DAY + " = ? AND " + WALLET_TITLE + " = ? AND " + WALLET_TYPE + " = ?",
                new String[]{year, month, day, title, type + ""},
                null,
                null,
                WALLET_YEAR + " ASC, " + WALLET_MONTH  + " ASC, " + WALLET_DAY  + " ASC");

        cursor.moveToPosition(0);
        sqLiteDatabase.close();


        return new Record(
                cursor.getString(cursor.getColumnIndex(WALLET_TITLE)),
                cursor.getString(cursor.getColumnIndex(WALLET_DESCRIPTION)),

                cursor.getString(cursor.getColumnIndex(WALLET_YEAR)),
                cursor.getString(cursor.getColumnIndex(WALLET_MONTH)),
                cursor.getString(cursor.getColumnIndex(WALLET_DAY)),

                Integer.parseInt(cursor.getString(cursor.getColumnIndex(WALLET_TYPE))),
                cursor.getString(cursor.getColumnIndex(WALLET_AMOUNT)),

                Integer.parseInt(cursor.getString(cursor.getColumnIndex(WALLET_IS_DELETED))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(WALLET_IS_IGNORED))),

                cursor.getString(cursor.getColumnIndex(WALLET_UPDATE_TIMESTAMP))
        );

    }

    public void deleteRecord(String year, String month, String day, String title, Integer type){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Finding the record
        // Setting isDelete = 1
        // Updating that on database
        // Temporarily Delete Done
        Record record = findRecord(year, month, day, title, type);
        record.setIsDeleted(1);
        this.updateRecord(record, year, month, day, title, type);

        sqLiteDatabase.close();
    }

    public void restoreRecord(String year, String month, String day, String title, Integer type){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();


        Record record = findRecord(year, month, day, title, type);
        record.setIsDeleted(0);
        this.updateRecord(record, year, month, day, title, type);

        sqLiteDatabase.close();
    }

    public void permanentlyDeleteRecord(String year, String month, String day, String title, Integer type){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Permanently Delete Done
        int i = sqLiteDatabase.delete(TABLE_WALLET,
                WALLET_YEAR + " = ? AND " + WALLET_MONTH + " = ? AND " + WALLET_DAY + " = ? AND " + WALLET_TITLE + " = ? AND " + WALLET_TYPE + " = ?",
                new String[]{year, month, day, title, type + ""});

        sqLiteDatabase.close();
    }

    public ArrayList<Record> loadWalletItems(){
        ArrayList<Record> records = new ArrayList<Record>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_WALLET +
                " WHERE " + WALLET_IS_DELETED + " == 0 AND " + WALLET_IS_IGNORED + " == 0" +
                " ORDER BY " + WALLET_YEAR + ", " + WALLET_MONTH + ", " + WALLET_DAY + ";", null);

        cursor.moveToPosition(0);
        if(cursor.getCount() == 0){
            showToast("No Data Found");
        }
        else{
            do{
                String title = cursor.getString(cursor.getColumnIndex(WALLET_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(WALLET_DESCRIPTION));

                String year = cursor.getString(cursor.getColumnIndex(WALLET_YEAR));
                String month = cursor.getString(cursor.getColumnIndex(WALLET_MONTH));
                String day = cursor.getString(cursor.getColumnIndex(WALLET_DAY));

                Integer type = Integer.parseInt(cursor.getString(cursor.getColumnIndex(WALLET_TYPE)));
                String amount = cursor.getString(cursor.getColumnIndex(WALLET_AMOUNT));

                Integer isDeleted = Integer.parseInt(cursor.getString(cursor.getColumnIndex(WALLET_IS_DELETED)));
                Integer isIgnored = Integer.parseInt(cursor.getString(cursor.getColumnIndex(WALLET_IS_IGNORED)));

                String updateTimestamp = cursor.getString(cursor.getColumnIndex(WALLET_UPDATE_TIMESTAMP));

                records.add(new Record(title, description, year, month, day, type, amount, isDeleted, isIgnored, updateTimestamp));
            }while (cursor.moveToNext());
        }

        return records;
    }

    public ArrayList<Record> syncWalletItems(){
        ArrayList<Record> records = new ArrayList<Record>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_WALLET +
                " ORDER BY " + WALLET_YEAR + ", " + WALLET_MONTH + ", " + WALLET_DAY + ";", null);

        cursor.moveToPosition(0);
        if(cursor.getCount() == 0){
            showToast("No Data Found");
        }
        else{
            do{
                String title = cursor.getString(cursor.getColumnIndex(WALLET_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(WALLET_DESCRIPTION));

                String year = cursor.getString(cursor.getColumnIndex(WALLET_YEAR));
                String month = cursor.getString(cursor.getColumnIndex(WALLET_MONTH));
                String day = cursor.getString(cursor.getColumnIndex(WALLET_DAY));

                Integer type = Integer.parseInt(cursor.getString(cursor.getColumnIndex(WALLET_TYPE)));
                String amount = cursor.getString(cursor.getColumnIndex(WALLET_AMOUNT));

                Integer isDeleted = Integer.parseInt(cursor.getString(cursor.getColumnIndex(WALLET_IS_DELETED)));
                Integer isIgnored = Integer.parseInt(cursor.getString(cursor.getColumnIndex(WALLET_IS_IGNORED)));

                String updateTimestamp = cursor.getString(cursor.getColumnIndex(WALLET_UPDATE_TIMESTAMP));

                records.add(new Record(title, description, year, month, day, type, amount, isDeleted, isIgnored, updateTimestamp));
            }while (cursor.moveToNext());
        }

        return records;
    }

    public ArrayList<Record> loadWalletBinItems(){
        ArrayList<Record> records = new ArrayList<Record>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_WALLET +
                " WHERE " + WALLET_IS_DELETED + " = 1 AND " + WALLET_IS_IGNORED + " = 0" +
                " ORDER BY " + WALLET_YEAR + ", " + WALLET_MONTH + ", " + WALLET_DAY + ";", null);

        cursor.moveToPosition(0);
        if(cursor.getCount() == 0){
            showToast("No Data Found");
        }
        else{
            do{
                String title = cursor.getString(cursor.getColumnIndex(WALLET_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(WALLET_DESCRIPTION));

                String year = cursor.getString(cursor.getColumnIndex(WALLET_YEAR));
                String month = cursor.getString(cursor.getColumnIndex(WALLET_MONTH));
                String day = cursor.getString(cursor.getColumnIndex(WALLET_DAY));

                Integer type = Integer.parseInt(cursor.getString(cursor.getColumnIndex(WALLET_TYPE)));
                String amount = cursor.getString(cursor.getColumnIndex(WALLET_AMOUNT));

                Integer isDeleted = Integer.parseInt(cursor.getString(cursor.getColumnIndex(WALLET_IS_DELETED)));
                Integer isIgnored = Integer.parseInt(cursor.getString(cursor.getColumnIndex(WALLET_IS_IGNORED)));

                String updateTimestamp = cursor.getString(cursor.getColumnIndex(WALLET_UPDATE_TIMESTAMP));

                records.add(new Record(title, description, year, month, day, type, amount, isDeleted, isIgnored, updateTimestamp));
            }while (cursor.moveToNext());
        }

        return records;
    }

    /*** Query on TABLE_JOURNAL ***/

    public long insertJournal(Journal journal){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String title = journal.getTitle();
        String description = journal.getDescription();

        String year = journal.getYear();
        String month = journal.getMonth();
        String day = journal.getDay();

        String hour = journal.getHour();
        String minute = journal.getMinute();

        String imageLink = journal.getImageLink();
        String fileLink = journal.getFileLink();

        contentValues.put(JOURNAL_TITLE, title);
        contentValues.put(JOURNAL_DESCRIPTION, description);

        contentValues.put(JOURNAL_YEAR, year);
        contentValues.put(JOURNAL_MONTH, month);
        contentValues.put(JOURNAL_DAY, day);

        contentValues.put(JOURNAL_HOUR, hour);
        contentValues.put(JOURNAL_MINUTE, minute);

        contentValues.put(JOURNAL_IMAGE_LINK, imageLink);
        contentValues.put(JOURNAL_FILE_LINK, fileLink);


        long rowId = sqLiteDatabase.insert(TABLE_JOURNAL, null, contentValues);
        return rowId;
    }

    public void insertAllJournals(ArrayList<Journal> journals){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        for(Journal journal:journals) {

            ContentValues contentValues = new ContentValues();

            String title = journal.getTitle();
            String description = journal.getDescription();

            String year = journal.getYear();
            String month = journal.getMonth();
            String day = journal.getDay();

            String hour = journal.getHour();
            String minute = journal.getMinute();

            String imageLink = journal.getImageLink();
            String fileLink = journal.getFileLink();

            contentValues.put(JOURNAL_TITLE, title);
            contentValues.put(JOURNAL_DESCRIPTION, description);

            contentValues.put(JOURNAL_YEAR, year);
            contentValues.put(JOURNAL_MONTH, month);
            contentValues.put(JOURNAL_DAY, day);

            contentValues.put(JOURNAL_HOUR, hour);
            contentValues.put(JOURNAL_MINUTE, minute);

            contentValues.put(JOURNAL_IMAGE_LINK, imageLink);
            contentValues.put(JOURNAL_FILE_LINK, fileLink);


            long rowId = sqLiteDatabase.insert(TABLE_JOURNAL, null, contentValues);

        }
    }

    public Journal findRecord(String fileLink) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_JOURNAL,
                null,
                JOURNAL_FILE_LINK + " = ?",
                new String[]{fileLink},
                null,
                null,
                JOURNAL_YEAR + " ASC, " + JOURNAL_MONTH  + " ASC, " + JOURNAL_DAY  + " ASC");

        cursor.moveToPosition(0);
        sqLiteDatabase.close();

        return new Journal(
                cursor.getString(cursor.getColumnIndex(JOURNAL_TITLE)),
                cursor.getString(cursor.getColumnIndex(JOURNAL_DESCRIPTION)),

                cursor.getString(cursor.getColumnIndex(JOURNAL_YEAR)),
                cursor.getString(cursor.getColumnIndex(JOURNAL_MONTH)),
                cursor.getString(cursor.getColumnIndex(JOURNAL_DAY)),

                cursor.getString(cursor.getColumnIndex(JOURNAL_HOUR)),
                cursor.getString(cursor.getColumnIndex(JOURNAL_MINUTE)),

                cursor.getString(cursor.getColumnIndex(JOURNAL_IMAGE_LINK)),
                cursor.getString(cursor.getColumnIndex(JOURNAL_FILE_LINK))
        );

    }


    public void deleteJournal(String fileLink){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Permanently Delete Done
        int i = sqLiteDatabase.delete(TABLE_JOURNAL,
                JOURNAL_FILE_LINK + " = ?",
                new String[]{fileLink});

        sqLiteDatabase.close();
    }

    public ArrayList<Journal> loadAllJournalItems(){
        ArrayList<Journal> journals = new ArrayList<Journal>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_JOURNAL +
                " ORDER BY " + JOURNAL_YEAR + ", " + JOURNAL_MONTH + ", " + JOURNAL_DAY + ";", null);

        cursor.moveToPosition(0);
        if(cursor.getCount() == 0){
            showToast("No Data Found");
        }
        else{
            do{
                String title = cursor.getString(cursor.getColumnIndex(JOURNAL_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(JOURNAL_DESCRIPTION));

                String year = cursor.getString(cursor.getColumnIndex(JOURNAL_YEAR));
                String month = cursor.getString(cursor.getColumnIndex(JOURNAL_MONTH));
                String day = cursor.getString(cursor.getColumnIndex(JOURNAL_DAY));

                String hour = cursor.getString(cursor.getColumnIndex(JOURNAL_HOUR));
                String minute = cursor.getString(cursor.getColumnIndex(JOURNAL_MINUTE));

                String imageLink = cursor.getString(cursor.getColumnIndex(JOURNAL_IMAGE_LINK));
                String fileLink = cursor.getString(cursor.getColumnIndex(JOURNAL_FILE_LINK));

                journals.add(new Journal(title, description, year, month, day, hour, minute, imageLink, fileLink));
            }while (cursor.moveToNext());
        }

        return journals;
    }



    /*** Additional Functions ***/

    public void showToast(String message){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
