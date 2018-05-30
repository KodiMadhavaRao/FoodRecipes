package foodrecepies.practise.com.foodrecepies.data.sp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by madhav on 3/5/2018.
 */

public class PersistentData {
    private static final String LOGIN = "Login";
    private static final String RECIPE = "Recipe";
    private static final String IS_SUCCESFULL = "isLogin";
    private static final String GLOBAL_ID = "id";

    public void setLoginValid(Context context, boolean issuccesful) {
        SharedPreferences.Editor editor = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE).edit();
        editor.putBoolean(IS_SUCCESFULL, issuccesful);
        editor.apply();
    }

    public boolean getLoginValidation(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
        return prefs.getBoolean(IS_SUCCESFULL, false);
    }

    public void setID(Context context, int id) {
        SharedPreferences.Editor editor = context.getSharedPreferences(RECIPE, Context.MODE_PRIVATE).edit();
        editor.putInt(GLOBAL_ID, id);
        editor.apply();
    }

    public int getID(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(RECIPE, Context.MODE_PRIVATE);
        return prefs.getInt(GLOBAL_ID, -1);
    }
}
