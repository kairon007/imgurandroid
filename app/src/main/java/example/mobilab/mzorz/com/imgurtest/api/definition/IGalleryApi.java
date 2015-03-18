package example.mobilab.mzorz.com.imgurtest.api.definition;


import example.mobilab.mzorz.com.imgurtest.model.GalleryResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

public interface IGalleryApi {

    @GET("gallery/{section}/{sort}/{page}?showViral={viral}")
    public void gallery(@Header("Authorization") String authorization, @Path("section") String section, @Path("sort") String sort, @Path("page") int page, @Path("viral") boolean viral, Callback<GalleryResponse> callback);

}
