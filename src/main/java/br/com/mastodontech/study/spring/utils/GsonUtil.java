package br.com.mastodontech.study.spring.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    public static String toPrettyJson (Object o) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(o);
    }

    public static String toJson (Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    public static <T> T fromJson (String json, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }
}
