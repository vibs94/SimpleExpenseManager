
package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.db.DatabaseHandler;;

public class PersistentAccountDAO implements AccountDAO {

    private Context context;

    //Constructor
    public PersistentAccountDAO(Context context) {

        this.context = context;
    }

    @Override
    public List<String> getAccountNumbersList() {

        //Open the database connection
        DatabaseHandler handler = DatabaseHandler.getInstance(context);
        SQLiteDatabase db = handler.getReadableDatabase();

        //Query to select all account numbers from accounts table
        String query = "SELECT "+handler.getAccountNo()+" FROM " + handler.getAccountTable()+" ORDER BY " + handler.getAccountNo() + " ASC;";

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<String> resultSet = new ArrayList<>();

        //Add account numbers to a list
        while (cursor.moveToNext())
        {
            resultSet.add(cursor.getString(cursor.getColumnIndex(handler.accountNoNo)));
        }

        cursor.close();

        //Return the list of account numbers
        return resultSet;

    }

    @Override
    public List<Account> getAccountsList() {

        DatabaseHandler handler = DatabaseHandler.getInstance(context);
        SQLiteDatabase db = handler.getReadableDatabase();

        //Query to select all the details about all the accounts in accounts table
        String query = "SELECT * FROM " + handler.getAccountTable()+" ORDER BY "+handler.getAccountNo()+" ASC;";

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Account> resultSet = new ArrayList<>();


        while (cursor.moveToNext())
        {
            //create account object
            Account account = new Account(cursor.getString(cursor.getColumnIndex(handler.getAccountNo())),
                    cursor.getString(cursor.getColumnIndex(handler.getBankName())),
                    cursor.getString(cursor.getColumnIndex(handler.getAccountHolderName())),
                    cursor.getDouble(cursor.getColumnIndex(handler.getBalance())));
            //Add account details to a list
            resultSet.add(account);
        }

        cursor.close();

        //Return list of account objects
        return resultSet;

    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {

        DatabaseHandler handler = DatabaseHandler.getInstance(context);
        SQLiteDatabase db = handler.getReadableDatabase();

        //Query to get details of the a given account number
        String query = "SELECT * FROM " + handler.getAccountTable() + " WHERE " + handler.getAccountNo() + " =  '" + accountNo + "';";

        Cursor cursor = db.rawQuery(query, null);

        Account account = null;

        //create accunt object
        if (cursor.moveToFirst()) {
            account = new Account(cursor.getString(cursor.getColumnIndex(handler.getAccountNo())),
                    cursor.getString(cursor.getColumnIndex(handler.getBankName())),
                    cursor.getString(cursor.getColumnIndex(handler.getAccountHolderName())),
                    cursor.getDouble(cursor.getColumnIndex(handler.getBalance())));
        }
        //If account is not found throw an exception
        else {
            throw new InvalidAccountException("You have selected an invalid account number...!");
        }

        cursor.close();

        //Return the account object
        return account;
    }

    @Override
    public void addAccount(Account account) {

        Databasehanler handler = DatabaseHandler.getInstance(context);
        SQLiteDatabase db = handler.getWritableDatabase();

        //Save account details to the account table
        ContentValues values = new ContentValues();
        values.put(handler.getAccountNo(), account.getAccountNo());
        values.put(handler.getBankName(), account.getBankName());
        values.put(handler.getAccountHolderName(), account.getAccountHolderName());
        values.put(handler.getBalance(), account.getBalance());

        db.insert(handler.getAccountTable(), null, values);

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

        DatabaseHandler handler = DatabaseHandler.getInstance(context);
        SQLiteDatabase db = handler.getWritableDatabase();
        //Query to delete a particular account from the account table
        String query = "SELECT * FROM " + handler.getAccountTable() + " WHERE " + handler.getAccountNo() + " =  '" + accountNo + "';";

        Cursor cursor = db.rawQuery(query, null);

        Account account = null;

        //Delete the account if found in the table
        if (cursor.moveToFirst()) {
            account = new Account(cursor.getString(cursor.getColumnIndex(handler.getAccountNo())),
                    cursor.getString(cursor.getColumnIndex(handler.getBankName())),
                    cursor.getString(cursor.getColumnIndex(handler.getAccountHolderName())),
                    cursor.getDouble(cursor.getColumnIndex(handler.getBalance())));
            db.delete(handler.accountTable, handler.accountNo + " = ?", new String[] { accountNo });
            cursor.close();

        }
        //If account is not found throw an exception
        else {
            throw new InvalidAccountException("No such account found...!");
        }

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        DatabaseHandler handler = DatabaseHandler.getInstance(context);
        SQLiteDatabase db = handler.getWritableDatabase();

        ContentValues values = new ContentValues();

        //Retrieve the account details of the selected account
        Account account = getAccount(accountNo);

        //Update the balance if the account is found in the table
        if (account!=null) {

            double new_amount=0;

            //Deduct the amount is it is an expense
            if (expenseType.equals(ExpenseType.EXPENSE)) {
                new_amount = account.getBalance() - amount;
            }
            //Add the amount if it is an income
            else if (expenseType.equals(ExpenseType.INCOME)) {
                new_amount = account.getBalance() + amount;
            }

            //Query to update balance in the account table
            String strSQL = "UPDATE "+handler.getAccountTable()+" SET "+handler.getBalance()+" = "+new_amount+" WHERE "+handler.getAccountNo()+" = '"+ accountNo+"';";

            db.execSQL(strSQL);

        }
        //If account is not found throw an exception
        else {
            throw new InvalidAccountException("No such account found...!");
        }

    }
}