package com.example.myandroidcouchbase;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.View;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.javascript.JavaScriptReplicationFilterCompiler;
import com.couchbase.lite.javascript.JavaScriptViewCompiler;
import com.couchbase.lite.listener.Credentials;
import com.couchbase.lite.listener.LiteListener;
import com.couchbase.lite.replicator.Replication;
import com.couchbase.lite.util.ZipUtils;

import java.io.IOException;

public class DataManager {

    public Database database;

    private static DataManager instance = null;
    private Replication mPush;
    private Replication mPull;

    protected DataManager(Context context) {
        Manager manager = null;
        try {
            // 1
            manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
            // 2
            database = manager.getExistingDatabase("quizzdroid");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

// 3
        if (database == null) {
            try {
                ZipUtils.unzip(context.getAssets().open("quizzdroid.cblite2.zip"), manager.getContext().getFilesDir());
                // 4
                database = manager.getDatabase("quizzdroid");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }

        View.setCompiler(new JavaScriptViewCompiler());
        Database.setFilterCompiler(new JavaScriptReplicationFilterCompiler());

        Credentials credentials = new Credentials(null, null);
        LiteListener liteListener = new LiteListener(manager, 5984, credentials);

        Thread thread = new Thread(liteListener);
        thread.start();

    }

    public static DataManager getSharedInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }
}
