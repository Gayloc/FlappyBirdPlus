/**
* @Description 客户端，用于与服务端通信
* @Author 古佳乐
* @Date 2024/6/15-下午5:14
*/

package com.gayloc.flappyBirdPlus;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
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

/**
* {@code @Description} 客户端，用于与服务端通信
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/15-下午5:14
*/
public class Client {
    // 储存服务器地址
    private static String BASE_URL;
    // 新建 gson 对象
    private static final Gson gson = new Gson();
    // 新建 http 客户端
    private static final HttpClient client = HttpClient.newHttpClient();

    /**
    * {@code @Description} 构造函数，初始化服务器地址
    * {@code @Param} String url
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午10:41
    */
    Client(String url) {
        BASE_URL = url;
    }

    /**
    * {@code @Description} 保存用户信息到本地
    * {@code @Param} User user
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午5:14
    */
    public void saveToLocal(User user) {
        String content = gson.toJson(user);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.json", StandardCharsets.UTF_8))) {
            writer.write(content);
            System.out.println("文件写入成功！");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
    * {@code @Description} 从本地读取用户信息
    * {@code @Param} void
    * {@code @return} User
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午5:14
    */
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

    /**
    * {@code @Description} 从服务器获取排行榜信息
    * {@code @Param} void
    * {@code @return} User[]
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午5:14
    */
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

    /**
    * {@code @Description} 向服务器添加分数信息或更新分数
    * {@code @Param}
    * {@code @return}
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午5:14
    */
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

    /**
    * {@code @Description} 从服务器获取分数信息
    * {@code @Param} String name
    * {@code @return} int
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午5:14
    */
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

    /**
    * {@code @Description} 删除服务器上的分数信息，本地归零
    * {@code @Param} String name
    * {@code @return} boolean
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午5:14
    */
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

    /**
    * {@code @Description} 向服务器发送 Get 请求时调用
    * {@code @Param} String endpoint
    * {@code @return} String
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午5:14
    */
    private static String sendGetRequest(String endpoint) {
        HttpResponse<String> response;

        try {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + endpoint))
                .GET()
                .build();
            response = Client.client.send(request, BodyHandlers.ofString());

        } catch (IOException | InterruptedException | URISyntaxException e) {
            // 网络错误时不抛出错误，只输出错误信息
            System.out.println(e.getMessage());
            return null;
        }

        return response.body();
    }

    /**
     * {@code @Description} 向服务器发送 Post 请求时调用
     * {@code @Param} String endpoint
     * {@code @return} String
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/15-下午5:14
     */
    private static String sendPostRequest(String endpoint, Map<String, Object> params){
        String json = gson.toJson(params);
        HttpResponse<String> response;
        HttpRequest request;

        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + endpoint))
                    .POST(BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .build();
            response = client.send(request, BodyHandlers.ofString());

        } catch (URISyntaxException | IOException | InterruptedException e) {
            // 网络错误时不抛出错误，只输出错误信息
            System.out.println(e.getMessage());
            return null;
        }

        return response.body();
    }

    /**
    * {@code @Description} 用于解析响应体的方法
    * {@code @Param} String json, Type type
    * {@code @return} 泛型 T
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午5:14
    */
    private static <T> T parseJsonResponse(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
