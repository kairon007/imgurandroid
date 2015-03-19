package example.mobilab.mzorz.com.imgurtest.api;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import example.mobilab.mzorz.com.imgurtest.Application;
import example.mobilab.mzorz.com.imgurtest.api.definition.IGalleryApi;
import example.mobilab.mzorz.com.imgurtest.model.BaseModel;
import example.mobilab.mzorz.com.imgurtest.model.GalleryDeserializer;
import example.mobilab.mzorz.com.imgurtest.model.GalleryResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class GalleryService {

    private static final GalleryService instance = new GalleryService();

    private static final String TAG = GalleryService.class.getName();

    private IGalleryApi service;

    private GalleryService() {

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .registerTypeAdapter(BaseModel.class, new GalleryDeserializer())
                .create();

        service = new RestAdapter.Builder()
                .setLogLevel(ApiConfig.getInstance().getLogLevel())
                .setEndpoint(ApiConfig.getInstance().getEndPoint())
                .setConverter(new GsonConverter(gson))
                .build().create(IGalleryApi.class);
    }

    public void gallery(String authorization, String section, String sort, String window, int page, boolean viral) {
        Application.getEventBus().post(new Event.GalleryRequestStartEvent());
        service.gallery(authorization, section, sort, window, page, viral, new Callback<GalleryResponse>() {

            @Override
            public void success(GalleryResponse resp, Response response) {
                Application.getEventBus().post(new Event.GalleryRequestCompleteEvent(resp));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (retrofitError != null) {
                    Log.i(TAG, retrofitError.getMessage());
                    if (retrofitError.getResponse() != null) {
                        try {
                            Error error = (Error) retrofitError.getBodyAs(Error.class);
                            Application.getEventBus().post(new Event.GalleryRequestFailEvent(error));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Application.getEventBus().post(new Event.GalleryRequestFailEvent(null));
                        }

                    } else {
                        Error error = new Error();
                        error.detail = retrofitError.getMessage();
                        Application.getEventBus().post(new Event.GalleryRequestFailEvent(error));
                    }
                } else {
                    Application.getEventBus().post(new Event.GalleryRequestFailEvent(null));
                }
            }
        });
    }


    public static GalleryService getInstance() {
        return instance;
    }
}
