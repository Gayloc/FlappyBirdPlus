package com.gayloc.flappyBirdPlus;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.gayloc.flappyBirdPlusServer.User;

public class Client {
    private static final String BASE_URL = "http://localhost:8000";
    private static final Gson gson = new Gson();
    private static final HttpClient client = HttpClient.newHttpClient();

    public void saveToLocal(User user) {
        String content = gson.toJson(user);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.json"))) {
            writer.write(content);
            System.out.println("文件写入成功！");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public User getFromLocal() {
        User result = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            List<String> lines = Files.readAllLines(Paths.get("user.json"));
            for (String line : lines) {
                stringBuilder.append(line);
            }

            result = parseJsonResponse(stringBuilder.toString(), new TypeToken<User>(){}.getType());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public User[] getTopUsers() {
        String topUsersResponse = null;
        try {
            topUsersResponse = sendGetRequest("/getTopUsers");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        List<User> topUsers = parseJsonResponse(topUsersResponse, new TypeToken<List<User>>(){}.getType());
        System.out.println("Top Users Response: " + topUsers);

        if (topUsers != null) {
            return topUsers.toArray(new User[0]);
        }

        return new User[0];
    }

    public boolean addScore(User user) {
        boolean result = false;
        String addScoreResponse = null;
        try {
            addScoreResponse = sendPostRequest("/addScore", Map.of("name", user.getName(), "score", String.valueOf(user.getScore())));
            result = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Add Score Response: " + addScoreResponse);

        return result;
    }

    public int getScore(String name) {
        String userScoreResponse = null;
        try {
            userScoreResponse = sendGetRequest("/getUserScore?name=John");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int userScore = gson.fromJson(userScoreResponse, Integer.class);
        System.out.println("User Score Response: " + userScore);

        return userScore;
    }

    public boolean deleteScore(String name) {
        boolean result = false;
        String removeScoreResponse = null;
        try {
            removeScoreResponse = sendPostRequest("/removeScore", Map.of("name", "John"));
            result = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        boolean removeScore = gson.fromJson(removeScoreResponse, Boolean.class);
        System.out.println("Remove Score Response: " + removeScore);

        return result;
    }

    private static String sendGetRequest(String endpoint) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + endpoint))
                .GET()
                .build();

        HttpResponse<String> response = Client.client.send(request, BodyHandlers.ofString());
        return response.body();
    }

    private static String sendPostRequest(String endpoint, Map<String, String> params) throws Exception {
        StringBuilder form = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!form.isEmpty()) {
                form.append("&");
            }
            form.append(entry.getKey()).append("=").append(entry.getValue());
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + endpoint))
                .POST(BodyPublishers.ofString(form.toString()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = Client.client.send(request, BodyHandlers.ofString());
        return response.body();
    }

    private static <T> T parseJsonResponse(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
