package example.mobilab.mzorz.com.imgurtest.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by mariozorz on 3/18/15.
 */
public class GalleryDeserializer implements JsonDeserializer<BaseModel> {


    @Override
    public BaseModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        boolean isAlbum = json.getAsJsonObject().get("is_album").getAsBoolean();
        if (isAlbum)
            return context.deserialize(json, Album.class);
        else
            return context.deserialize(json, Image.class);
    }
}

