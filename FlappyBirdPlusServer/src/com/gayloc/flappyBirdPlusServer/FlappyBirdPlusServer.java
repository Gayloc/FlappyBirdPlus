package com.gayloc.flappyBirdPlusServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FlappyBirdPlusServer {
    private static Controller controller;
    private static final Gson gson = new GsonBuilder().create();

    public static void main(String[] args) throws IOException {
        controller = new Controller();

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/addScore", new AddScoreHandler());
        server.createContext("/getTopUsers", new GetTopUsersHandler());
        server.createContext("/getUserScore", new GetUserScoreHandler());
        server.createContext("/removeScore", new RemoveScoreHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8000");

        commandLine();
        server.stop(0);
    }

    private static void commandLine() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            if (command.equals("stop")) {
                controller.stop();
                return;
            } else {
                System.out.println("未知命令");
            }
        }
    }

    static class AddScoreHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String[] params = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8).split("&");
                String name = params[0].split("=")[1];
                int score = Integer.parseInt(params[1].split("=")[1]);

                User user = new User(name, score);
                boolean result = controller.addScore(user);

                String response = gson.toJson(result);
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    static class GetTopUsersHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                User[] topUsers = controller.getTopUsers();

                String response = gson.toJson(topUsers);
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    static class GetUserScoreHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String userName = exchange.getRequestURI().getQuery().split("=")[1];

                int score = controller.getUserScore(userName);
                String response = gson.toJson(score);

                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    static class RemoveScoreHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String userName = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8).split("=")[1];

                boolean result = controller.removeScore(userName);
                String response = gson.toJson(result);

                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }
}
