package lk.ac.mrt.cse.dbs.simpleexpensemanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    protected static final String dbName = "140640A";
    private static DatabaseHelper databaseHelper = null;
    private static final int database_version = 1;

    private static final String accountTable = "Accounts";
    private static final String accountNoNo = "accountNo";
    private static final String bankName = "bankName";
    private static final String accountHolderName = "accountHolderName";
    private static final String balance = "balance";

    private static final String transactionTable = "transations";
    private static final String transaction_id = "transaction_id";
    private static final String date = "date";
    private static final String accountNo = "accountNo";
    private static final String expenseType = "expenseType";
    private static final String amount = "amount";


    public DatabaseHelper(Context context) {

        super(context, dbName, null, database_version);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null)
            databaseHelper = new DatabaseHelper(context);
        return databaseHelper;
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String accountTable = "CREATE TABLE "+accountTable+"("+accountNoNo+" VARCHAR(20) NOT NULL PRIMARY KEY,"+bankName+" VARCHAR(100) NULL,"+accountHolderName+" VARCHAR(100) NULL,"+balance+" DECIMAL(10,2) NULL );"

        String transactionTable = "CREATE TABLE "+getTransactionTable()+"("+transaction_id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+accountNo+" VARCHAR(20) NOT NULL,"+date+" DATE NULL,"+amount+" DECIMAL(10,2) NULL,"+expenseType+" VARCHAR(100) NULL, FOREIGN KEY("+accountNo+") REFERENCES "+accountTable+"("+accountNoNo+"));

        sqLiteDatabase.execSQL(accountTable);
        sqLiteDatabase.execSQL(transactionTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int j) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + accountTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + transactionTable);
        onCreate(sqLiteDatabase);

    }

    public static String getAccountTable() {
        return accountTable;
    }

    public static String getAccountNoNo() {
        return accountNoNo;
    }

    public static String getBankName() {
        return bankName;
    }

    public static String getAccountHolderName() {
        return accountHolderName;
    }

    public static String getBalance() {
        return balance;
    }

    public static String getTransactionTable() {
        return transactionTable;
    }

    public static String getTransaction_id() {
        return transaction_id;
    }

    public static String getDate() {
        return date;
    }

    public static String getAccountNo() {
        return accountNo;
    }

    public static String getExpenseType() {
        return expenseType;
    }

    public static String getAmount() {
        return amount;
    }
}
