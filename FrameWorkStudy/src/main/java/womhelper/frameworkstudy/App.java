package womhelper.frameworkstudy;

import android.app.Application;

import womhelper.frameworkstudy.utils.AppContextUtil;

/**
 * Created by Walkud on 16/9/26.
 */

public class App extends Application {

    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        AppContextUtil.init(this);
    }

    public static App getContext() {
        return app;
    }
}
