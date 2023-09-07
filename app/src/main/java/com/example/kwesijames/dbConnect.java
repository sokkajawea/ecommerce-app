package com.example.kwesijames;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class dbConnect extends SQLiteOpenHelper {
    //database name and version
    private static String dbName= "KwesiJames";
    private static int dbVersion = 11;
    private Context context;

    //database columns and tables for the users
    private static String usersTable="users";
    private static String userID = "id";
    private static String userUsername = "Username";
    private static String userFirstname = "Firstname";
    private static String userLastname = "Lastname";
    private static String userPassword = "Password";
    private static String userAdmin = "Admin";

    //database column and name for the categories
    private static String categoriesTable="categories";
    public static String catID = "id";
    public static String catName = "catName";

    //database column and name for the products
    private static String prodTable="products";
    public static String prodID = "id";
    public static String prodName = "Name";
    public static String prodPrice = "Price";
    public static String prodDescription = "Description";
    public static String prodListPrice = "List_Price";
    public static String prodRetailPrice = "Retail_Price";
    private static String prodDateCreated = "Date_Created";
    public static String prodDateUpdated = "Date_Updated";
    public static String prodCategory ="Category";

    //database column and name for the order
    private static String orderTable = "orders";
    private static String orderID ="orderId";
    private static String orderUser ="orderUser";
    private static String orderItems ="orderItems";

    private static String cartTable = "cart";
    private static String cartItemsTab = "cart_items";



    public dbConnect(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="CREATE TABLE "+usersTable+"("+userID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+userUsername+" TEXT UNIQUE, "+userFirstname+" TEXT, "+userLastname+" TEXT, "+userPassword+" TEXT, "+userAdmin+" INTEGER DEFAULT 0)";
        sqLiteDatabase.execSQL(query);

        String catQuery = "CREATE TABLE "+categoriesTable+"("+catID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+catName+" TEXT)";
        sqLiteDatabase.execSQL(catQuery);

        String prodQuery="CREATE TABLE "+prodTable+"("+prodID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+prodName+" TEXT, "+prodPrice+" DOUBLE, "+prodDescription+" TEXT, "+prodListPrice+" DOUBLE, "+prodRetailPrice+" DOUBLE, "+prodDateCreated+" DATE, "+prodCategory+" TEXT, " +prodDateUpdated+" DATE)";
        sqLiteDatabase.execSQL(prodQuery);


     //String orderQuery="CREATE TABLE "+prodTable+"("+prodID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+prodName+" TEXT, "+prodPrice+" DOUBLE, "+prodDescription+" TEXT, "+prodListPrice+" DOUBLE, "+prodRetailPrice+" DOUBLE, "+prodDateCreated+" DATE, "+prodCategory+" TEXT, " +prodDateUpdated+" DATE)";


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+prodTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+cartItemsTab);
        onCreate(sqLiteDatabase);
    }

    //user functions ---------------------------------------------------------------------

    public void addUser(users user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(userFirstname, user.getFirstname());
        values.put(userLastname, user.getLastname());
        values.put(userUsername, user.getUsername());
        values.put(userPassword, user.getPassword());
        values.put(userAdmin, user.getAdmin());
        db.insert(usersTable, null, values);
    }

    public boolean updateUser(users user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(userUsername, user.getUsername());
        values.put(userFirstname, user.getFirstname());
        values.put(userLastname, user.getLastname());
        values.put(userPassword, user.getPassword());
        int endResult = db.update(usersTable, values, userID + "=?", new String[]{String.valueOf(user.getId())});
        if(endResult > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteUser(users user){
        SQLiteDatabase db = this.getWritableDatabase();
        int endResult = db.delete(usersTable, userID = "=?", new String[]{String.valueOf(user.getId())});
        if(endResult > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkUser(users user){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+usersTable+" WHERE "+userUsername+" =? AND "+userPassword+" =?";
        Cursor cursor = db.rawQuery(query, new String[]{user.getUsername(), user.getPassword()});
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    public Cursor getUserData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", "Username", "Firstname", "Lastname", "Password"};
        String selection = "Username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(usersTable, columns, selection, selectionArgs, null, null, null);
        return cursor;
    }


    public boolean isUsernameTaken(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean isTaken = false;

        try {
            String query = "SELECT * FROM " + usersTable + " WHERE " + userUsername + " = ?";
            cursor = db.rawQuery(query, new String[]{username});

            if (cursor != null && cursor.moveToFirst()) {
                isTaken = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return isTaken;
    }


    //categories functions ---------------------------------------------------------------------

    public Cursor getCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + categoriesTable, null);
        return cursor;
    }

    public Cursor findCategoryById(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + categoriesTable + " WHERE " + catID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});
        return cursor;
    }

    public boolean updateCat(category category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(catName, category.getTitle());
        int endResult = db.update(categoriesTable, values, catID + "=?", new String[]{String.valueOf(category.getId())});
        if(endResult > 0){
            return true;
        }else{
            return false;
        }
    }

    public void addCat(category category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(catName, category.getTitle());
        db.insert(categoriesTable, null, values);
    }

    public Cursor findCategory() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + categoriesTable + " ORDER BY RANDOM() LIMIT 4";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean deleteCat(category category){
        SQLiteDatabase db = this.getWritableDatabase();
        int endResult = db.delete(categoriesTable, catID+"=?", new String[]{String.valueOf(category.getId())});
        if(endResult > 0){
            return true;
        }else{
            return false;
        }
    }


    //product functions ---------------------------------------------------------------------

    public Cursor findProductsByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + prodTable + " WHERE " + prodCategory + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{category});
        return cursor;
    }



    public void addProd(String name, double price, String description,String category, double listPrice, double retailPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
        values.put(prodName, name);
        values.put(prodPrice, price);
        values.put(prodDescription, description);
        values.put(prodCategory, category);
        values.put(prodListPrice, listPrice);
        values.put(prodRetailPrice, retailPrice);
        values.put(prodDateCreated, date);
        values.put(prodDateUpdated, date);
        db.insert(prodTable, null, values);
    }

    public boolean updateProd(int id, String name, double price, String description,String category, double listPrice, double retailPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
        values.put(prodName, name);
        values.put(prodPrice, price);
        values.put(prodDescription, description);
        values.put(prodCategory, category);
        values.put(prodListPrice, listPrice);
        values.put(prodRetailPrice, retailPrice);
        values.put(prodDateUpdated, date);
        int endResult = db.update(prodTable, values, prodID + "=?", new String[]{String.valueOf(id)});
        if(endResult > 0){
            return true;
        }else{
            return false;
        }
    }


    public boolean deleteProd(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int endResult = db.delete(prodTable, prodID = String.valueOf(id), null);
        if(endResult > 0){
            return true;
        }else{
            return false;
        }
    }

    public Cursor getProducts10() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query ="SELECT * FROM " + prodTable + " ORDER BY RANDOM() LIMIT 10";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getAllProds() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT DISTINCT * FROM " + prodTable;
        return db.rawQuery(query, null);
    }




  /*  public void addToUserCart(String user, List<product> productIds) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(user, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> currentItems = sharedPreferences.getStringSet("cartItems", new HashSet<>());

        List<String> cartItemsList = new ArrayList<>(currentItems);

        for (product productId : productIds) {
            String item = productId.getProdName() + " - $" + productId.getPrice();
            cartItemsList.add(item);
        }

        editor.putStringSet("cartItems", new HashSet<>(cartItemsList));
        editor.apply();
    }

*/



}
