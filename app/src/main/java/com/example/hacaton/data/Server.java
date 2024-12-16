package com.example.hacaton.data;

import fi.iki.elonen.NanoHTTPD;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Server extends NanoHTTPD {

    private static final String TASKS_JSON = "{\n" +
            "  \"logic\": [\n" +
            "    {\"question\": \"Завжди знаходиться у роті, а проковтнути не можна?\", \"answer\": \"язик\"}\n" +
            "  ],\n" +
            "  \"math\": [\n" +
            "    {\"question\": \"Обрахуй будь-ласка: 9 : 3 =\", \"answer\": \"3\"}\n" +
            "  ],\n" +
            "  \"ukrainian\": [\n" +
            "    {\"question\": \"Доповніть речення: Zrtcm ... htxtyyz\", \"answer\": \"fghjkl\"}\n" +
            "  ],\n" +
            "  \"english\": [\n" +
            "    {\"question\": \"What is the capital of France?\", \"answer\": \"Paris\"}\n" +
            "  ]\n" +
            "}";

    public Server() throws IOException {
        super(8080);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("Server started on port 8080");
    }

    @Override
    public Response serve(IHTTPSession session) {
        Map<String, String> params = session.getParms();
        String courseType = params.get("course");

        try {
            JSONObject jsonObject = new JSONObject(TASKS_JSON);
            JSONArray tasks = jsonObject.getJSONArray(courseType);
            return newFixedLengthResponse(Response.Status.OK, "application/json", tasks.toString());
        } catch (Exception e) {
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "Error: " + e.getMessage());
        }
    }
}