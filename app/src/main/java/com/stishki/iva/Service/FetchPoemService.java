package com.stishki.iva.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.stishki.iva.Database.DatabaseManager;
import com.stishki.iva.Database.PoemContract;
import com.stishki.iva.Entity.Poem;
import com.stishki.iva.Helper.PoemHelper;
import com.stishki.iva.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FetchPoemService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_NEW = "getnewpoems";
    private static final String ACTION_BAZ = "com.stishki.iva.Service.action.BAZ";

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "FetchPoemService";

    SharedPreferences sharedPreferences;


    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.stishki.iva.Service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.stishki.iva.Service.extra.PARAM2";

    public FetchPoemService() {
        super("FetchPoemService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, FetchPoemService.class);
        intent.setAction(ACTION_NEW);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, FetchPoemService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DatabaseManager manager = new DatabaseManager(getApplicationContext());
        if (intent != null) {
            String action = intent.getStringExtra(ACTION_NEW);

            Log.d(TAG, "Service Started!");

            if (ACTION_NEW.equals(action)) {

                //TODO ВЫЗОВ МЕТОДА ПОЛУЧЕНИЯ 50 ПОСЛЕДНИХ СООБЩЕНИЙ

                    JSONObject jsonObject = new JSONObject();
                    String s = PoemHelper.getNewPoems(jsonObject);
                try {
                    JSONArray array = new JSONArray(s);
                    for (int i=0;i<array.length(); i++) {
                        Poem p = new Poem();

                        JSONObject object = array.getJSONObject(i);
                        manager.insert(
                                PoemContract.NewPoemEntry.TABLE_NAME,
                                p.getGenre(),
                                p.getContent(),
                                p.getAuthor(),
                                Integer.parseInt(object.getString("likes")),
                                Integer.parseInt(object.getString("dislikes"))
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ArrayList<ArrayList<Object>> arrayLists = manager.getAllRowsAsArrays(PoemContract.NewPoemEntry.TABLE_NAME);


                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
