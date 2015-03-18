package example.mobilab.mzorz.com.imgurtest;


import de.greenrobot.event.EventBus;

/**
 * Created by mariozorz on 3/18/15.
 */
public class Application extends android.app.Application {

    private static final String TAG = "Application";

    private static Application _instance;

    public Application() {
        super();
        _instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getEventBus().register(this);
    }

    public static Application get() {
        return _instance;
    }

    public static EventBus getEventBus() {
        return EventBus.getDefault();
    }

}

