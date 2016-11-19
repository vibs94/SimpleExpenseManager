package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistTransactionDAO;

public class PersistExpenseManager extends ExpenseManager {

    private Context context;

    public PersistExpenseManager() {

        setup();
    }

    @Override
    public void setup() {
        /*** Begin generating dummy data for In-Memory implementation ***/



        TransactionDAO persistTransactionDAO = new PersistTransactionDAO(context);
        setTransactionsDAO(persistTransactionDAO);

        AccountDAO persistAccountDAO = new PersistAccountDAO(context);
        setAccountsDAO(persistAccountDAO);
    }
}
