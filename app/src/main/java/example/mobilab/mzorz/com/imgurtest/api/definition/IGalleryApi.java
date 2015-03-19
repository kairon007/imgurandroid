package example.mobilab.mzorz.com.imgurtest.api.definition;


import example.mobilab.mzorz.com.imgurtest.model.GalleryResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

public interface IGalleryApi {

    @GET("/gallery/{section}/{sort}/{window}/{page}")
    public void gallery(@Header("Authorization") String authorization, @Path("section") String section, @Path("sort") String sort, @Path("window") String window, @Path("page") int page, @Query("showViral") boolean viral, Callback<GalleryResponse> callback);

}
