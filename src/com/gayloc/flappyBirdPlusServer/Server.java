package com.gayloc.flappyBirdPlusServer;

public interface Server {
    void start();
    void stop();
    boolean addScore(User user);
    User[] getTopUsers();
    int getUserScore(String userName);
    boolean removeScore(String userName);
}
