package foodrecepies.practise.com.foodrecepies;

import android.app.Application;

public class RecipeApplication extends Application {
    private static RecipeApplication appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.appContext = this;
    }

    public Application getApplicationContext() {
        return appContext;
    }
}
