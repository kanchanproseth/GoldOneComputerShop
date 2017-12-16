package com.kanchanproseth.goldonecomputershop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kanchanproseth on 12/15/17.
 */

public class MyData {

    @SerializedName("categories")
    public List<Categories> categories;


    public static class List_product {
        @SerializedName("product_name")
        public String product_name;
        @SerializedName("short_desc")
        public String short_desc;
        @SerializedName("long_desc")
        public String long_desc;
        @SerializedName("price")
        public String price;
        @SerializedName("image_uri")
        public String image_uri;
    }

    public static class Asset {
        @SerializedName("asset_model")
        public String asset_model;
        @SerializedName("list_product")
        public List<List_product> list_product;
    }

    public static class Categories {
        @SerializedName("category_id")
        public String category_id;
        @SerializedName("category_name")
        public String category_name;
        @SerializedName("asset")
        public List<Asset> asset;
    }
}
