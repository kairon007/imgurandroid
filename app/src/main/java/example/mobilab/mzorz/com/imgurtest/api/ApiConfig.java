package example.mobilab.mzorz.com.imgurtest.api;

import retrofit.RestAdapter;

/**
 * Created by mariozorz on 3/18/15.
 */
public class ApiConfig {

    private static ApiConfig instance = new ApiConfig();

    private String endPoint;
    private RestAdapter.LogLevel logLevel;

    private ApiConfig() {
        endPoint = "https://api.imgur.com/3";
        logLevel = RestAdapter.LogLevel.FULL;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public RestAdapter.LogLevel getLogLevel() {
        return logLevel;
    }

    public static ApiConfig getInstance() {
        return instance;
    }
}
