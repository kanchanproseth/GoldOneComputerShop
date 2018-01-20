package com.kanchanproseth.goldonecomputershop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kanchanproseth on 12/19/17.
 */

public class BlogModel {

    @SerializedName("Blogs")
    public List<Blogs> Blogs;

    public static class Blogs {
        @SerializedName("blog_title")
        public String blog_title;
        @SerializedName("posted_date")
        public String posted_date;
        @SerializedName("author")
        public String Author;
        @SerializedName("short_desc")
        public String short_desc;
        @SerializedName("long_desc")
        public String long_desc;
        @SerializedName("image_uri")
        public String image_uri;
    }
}
