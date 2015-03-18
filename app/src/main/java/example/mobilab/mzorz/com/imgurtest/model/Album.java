package example.mobilab.mzorz.com.imgurtest.model;

import java.util.List;

/**
 * Created by mariozorz on 3/18/15.
 */
public class Album extends BaseModel {

    public long datetime;
    public String cover;
    public int cover_width;
    public int cover_height;
    public String account_url;
    public int account_id;
    public String privacy;
    public String layout;
    public int views;
    public int ups;
    public int downs;
    public int score;
    public boolean is_album;
    public String vote;
    public boolean favorite;
    public boolean nsfw;
    public int comment_count;
    public String topic;
    public int topic_id;
    public int images_count;
    public List<Image> images;
}