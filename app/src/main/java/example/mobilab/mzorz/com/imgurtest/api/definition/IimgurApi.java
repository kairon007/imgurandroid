package example.mobilab.mzorz.com.imgurtest.api.definition;


import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface IimgurApi {

    @GET("/events/activities/{id}")
    public void activity(@Path("id") Long id, @Header("Authorization") String authorization, Callback<Activity> callback);

}
