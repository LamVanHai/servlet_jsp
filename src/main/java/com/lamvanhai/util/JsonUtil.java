package com.lamvanhai.util;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JsonUtil {

    public static <T> T toClass(HttpServletRequest request, Class<T> tClass) {
        StringBuilder json = new StringBuilder();
        String line;
         
        try {
            while ((line = request.getReader().readLine()) != null) {
                json.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(json.toString(), tClass);
    }
}
