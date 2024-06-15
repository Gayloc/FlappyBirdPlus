package com.gayloc.flappyBirdPlusServer;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @org.junit.jupiter.api.Test
    void getTopUsers() {
        Controller controller = new Controller();

        assertEquals("[User{name='胡振鹏', score=100}]", Arrays.toString(controller.getTopUsers()));
    }

    @org.junit.jupiter.api.Test
    void getUserScore() {
        Controller controller = new Controller();

        assertEquals(0, controller.getUserScore("一个不存在的用户"));
    }

    @org.junit.jupiter.api.Test
    void addScore() {
        Controller controller = new Controller();

        assertTrue(controller.addScore(new User(
                "胡振鹏",
                100
        )));
    }

}