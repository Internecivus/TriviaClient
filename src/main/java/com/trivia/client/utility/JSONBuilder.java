package com.trivia.client.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by faust. Part of TriviaClient Project. All rights reserved. 2018
 */
public class JSONBuilder {
    private ObjectNode json;

    public static String toJSON(Object entity) {
        String json = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            if (entity != null) {
                json = mapper.writeValueAsString(entity);
            }
            json = mapper.writeValueAsString(entity.getClass());
        }
        catch (JsonProcessingException e) {

        }
        return json;
    }

    public static Object fromJSONtoEntity(String JSON, Class entityClass) {
        ObjectMapper mapper = new ObjectMapper();
        Object obj;
        try {
            obj = entityClass.newInstance();
        }
        catch (Exception ex) { return null; }

        try {
            mapper.readerForUpdating(obj).readValue(JSON);
        }
        catch (java.io.IOException e) {
        }

        return obj;
    }
}
