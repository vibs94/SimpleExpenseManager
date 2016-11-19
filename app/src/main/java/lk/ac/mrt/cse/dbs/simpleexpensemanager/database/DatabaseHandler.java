package lk.ac.mrt.cse.dbs.simpleexpensemanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {

    protected static final String dbName = "140640A";
    private static DatabaseHandler databaseHandler = null;
    private static final int databaseVersion = 1;

    private static final String accountTable = "Accounts";
    private static final String accountNo = "accountNo";
    private static final String bankName = "bankName";
    private static final String accountHolderName = "accountHolderName";
    private static final String balance = "balance";

    private static final String transactionTable = "Transations";
    private static final String transactionId = "transactionId";
    private static final String date = "date";
    private static final String expenseType = "expenseType";
    private static final String amount = "amount";


    private DatabaseHandler(Context context) {

        super(context, dbName, null, databaseVersion);
    }


    //singleton design pattern applied to keep onle one instance

    public static DatabaseHandler getInstance(Context context) {
        if (databaseHandler == null)
            databaseHandler = new DatabaseHandler(context);
        return databaseHandler;
    }

    //create two tables "Accounts" and "Transaction"

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String accountTableSQL = "CREATE TABLE "+accountTable+"("+accountNo+" VARCHAR(20) NOT NULL PRIMARY KEY,"+bankName+" VARCHAR(100) NULL,"+accountHolderName+" VARCHAR(100) NULL,"+balance+" DECIMAL(10,2) NULL );";

        String transactionTableSQL = "CREATE TABLE "+transactionTable+"("+transactionId+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+accountNo+" VARCHAR(20) NOT NULL,"+date+" DATE NULL,"+amount+" DECIMAL(10,2) NULL,"+expenseType+" VARCHAR(100) NULL, FOREIGN KEY("+accountNo+") REFERENCES "+accountTable+"("+accountNo+");";

        sqLiteDatabase.execSQL(accountTableSQL);
        sqLiteDatabase.execSQL(transactionTableSQL);

    }

    //Drop table if exist before creation of tables

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int j) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+accountTable+" ;");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+transactionTable+" ;");
        onCreate(sqLiteDatabase);

    }

    ////////////////////getters7//////////////////////

    public static String getAccountTable() {
        return accountTable;
    }

    public String getAccountNo() {
        return accountNo;
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

    public static String getTransactionId() {
        return transactionId;
    }

    public static String getDate() {
        return date;
    }

    public static String getExpenseType() {
        return expenseType;
    }

    public static String getAmount() {
        return amount;
    }
}
