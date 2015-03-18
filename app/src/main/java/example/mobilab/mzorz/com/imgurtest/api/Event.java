package example.mobilab.mzorz.com.imgurtest.api;

import example.mobilab.mzorz.com.imgurtest.model.GalleryResponse;

/**
 * Created by mariozorz on 3/18/15.
 */
public class Event<T> {
    public final T object;

    public Event(T object) {
        this.object = object;
    }

    public static class GalleryRequestStartEvent {

    }

    public static class GalleryRequestFailEvent extends Event<Error> {

        public GalleryRequestFailEvent(Error object) {
            super(object);
        }
    }

    public static class GalleryRequestCompleteEvent extends Event<GalleryResponse> {

        public GalleryRequestCompleteEvent(GalleryResponse object) {
            super(object);
        }
    }

}