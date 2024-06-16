/**
* @Description 服务器程序实现的接口
* @Author 古佳乐
* @Date 2024/6/15-下午1:13
*/

package com.gayloc.flappyBirdPlusServer;

public interface Server {
    void start();
    void stop();
    boolean addScore(User user);
    User[] getTopUsers();
    int getUserScore(String userName);
    boolean removeScore(String userName);
}
