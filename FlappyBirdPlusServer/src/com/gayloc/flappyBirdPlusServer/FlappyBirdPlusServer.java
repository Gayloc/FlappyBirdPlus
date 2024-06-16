/**
* @Description 服务器程序
* @Author 古佳乐
* @Date 2024/6/15-下午5:14
*/

package com.gayloc.flappyBirdPlusServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

/**
 * {@code @Description} 服务器程序
 * {@code @Author} 古佳乐
 * {@code @Date} 2024/6/15-下午5:14
 */
public class FlappyBirdPlusServer {

    // 两个成员变量，分别储存 controller 和一个 gson 对象
    private static Controller controller;
    private static final Gson gson = new GsonBuilder().create();

    /**
    * {@code @Description} 服务器程序主函数
    * {@code @Param} String[] args
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午5:14
    */
    public static void main(String[] args) throws IOException {
        // 实例化一个 Controller 对象
        controller = new Controller();

        // 新建 http server
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // 添加路由
        server.createContext("/addScore", new AddScoreHandler());
        server.createContext("/getTopUsers", new GetTopUsersHandler());
        server.createContext("/getUserScore", new GetUserScoreHandler());
        server.createContext("/removeScore", new RemoveScoreHandler());

        server.setExecutor(null);

        // 启动服务器
        server.start();
        System.out.println("Server started on port 8000");
        System.out.println("输入 help 命令查看帮助信息");

        // 进入主循环，检查用户命令输入
        commandLine();
        server.stop(0);
    }

    /**
    * {@code @Description} 接收用户命令输入
    * {@code @return}  void
    * {@code @Param} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/16-上午11:20
    */
    private static void commandLine() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            switch (command) {
                case "stop" -> {
                    //执行关闭服务器命令
                    controller.stop();
                    return;
                }
                case "help" -> {
                    //打印帮助信息
                    System.out.println("Available commands:");
                    System.out.println("stop - stop the server");
                    System.out.println("help - print this message");
                    System.out.println("about - print about message");
                }
                case "about" -> {
                    //打印关于信息
                    System.out.println("About FlappyBirdPlusServer:");
                    System.out.println("这是 FlappyBirdPlus 的服务端，用于排行榜功能");
                    System.out.println("姓名 - 古佳乐");
                    System.out.println("学号 - 8008123201");
                    System.out.println("班级 - 计算机Ⅱ类2307班");
                    System.out.println("版本 - 1.0");
                    System.out.println("完成时间 - 2024年6月16日");
                }
                //错误的命令输入
                default -> System.out.println("未知命令");
            }
        }
    }

    /**
    * {@code @Description} 处理 addScore 请求
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午5:14
    */
    static class AddScoreHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            //判断请求类型
            if ("POST".equals(exchange.getRequestMethod())) {
                Reader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);

                //解析请求体
                User user = gson.fromJson(reader, User.class);
                boolean result = controller.addScore(user);

                //构造响应体
                String response = gson.toJson(result);
                //指定编码为UTF-8
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    /**
     * {@code @Description} 处理 getTpUsers 请求
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/15-下午5:14
     */
    static class GetTopUsersHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                User[] topUsers = controller.getTopUsers();

                String response = gson.toJson(topUsers);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    /**
     * {@code @Description} 处理 getUserScore 请求
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/15-下午5:14
     */
    static class GetUserScoreHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String userName = exchange.getRequestURI().getQuery().split("=")[1];

                int score = controller.getUserScore(userName);
                String response = gson.toJson(score);

                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    /**
     * {@code @Description} 处理 removeUserScore 请求
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/15-下午5:14
     */
    static class RemoveScoreHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // 这里出过严重 bug 所以保留调试信息
                // 打印请求方法
                System.out.println("Request Method: " + exchange.getRequestMethod());

                Reader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);

                // 打印请求体内容
                StringBuilder body = new StringBuilder();
                int c;
                while ((c = reader.read()) != -1) {
                    body.append((char) c);
                }
                String requestBody = body.toString();
                System.out.println("Request Body: " + requestBody);

                // 重新创建 Reader 来解析 JSON
                reader = new StringReader(requestBody);
                String userName = (String) gson.fromJson(reader, Map.class).get("name");
                System.out.println("Parsed userName: " + userName);

                boolean result = controller.removeScore(userName);
                String response = gson.toJson(result);

                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }
}
