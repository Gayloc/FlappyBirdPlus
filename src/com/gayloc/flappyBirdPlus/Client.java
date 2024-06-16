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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.swing.*;

public class Client {
    private static String BASE_URL;
    private static final Gson gson = new Gson();
    private static final HttpClient client = HttpClient.newHttpClient();

    Client(String url) {
        BASE_URL = url;
    }

    public void saveToLocal(User user) {
        String content = gson.toJson(user);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.json", StandardCharsets.UTF_8))) {
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
            List<String> lines = Files.readAllLines(Paths.get("user.json"), StandardCharsets.UTF_8);
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

        JOptionPane.showMessageDialog(null, "获取数据失败", "错误", JOptionPane.ERROR_MESSAGE);
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
            userScoreResponse = sendGetRequest("/getUserScore?name="+name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (userScoreResponse == null) {
            JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
            return 0;
        }

        return gson.fromJson(userScoreResponse, Integer.class);
    }

    public boolean deleteScore(String name) {
        boolean result = false;
        String removeScoreResponse = null;
        try {
            removeScoreResponse = sendPostRequest("/removeScore", Map.of("name", name));
            result = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        gson.fromJson(removeScoreResponse, Boolean.class);

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

    private static String sendPostRequest(String endpoint, Map<String, Object> params) throws Exception {
        String json = gson.toJson(params);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + endpoint))
                .POST(BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        return response.body();
    }

    private static <T> T parseJsonResponse(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
