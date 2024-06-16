/**
* @Description 服务器的测试用例
* @Author 古佳乐
* @Date 2024/6/15-下午1:13
*/

package com.gayloc.flappyBirdPlusServer;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
* {@code @Description} 测试类
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/15-下午1:13
*/
class ControllerTest {

    /**
    * {@code @Description} 测试 getTopUsers()
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    @org.junit.jupiter.api.Test
    void getTopUsers() {
        Controller controller = new Controller();

        assertEquals("[User{name='胡振鹏', score=100}]", Arrays.toString(controller.getTopUsers()));
    }

    /**
     * {@code @Description} 测试 getUserScore()
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/15-下午1:13
     */
    @org.junit.jupiter.api.Test
    void getUserScore() {
        Controller controller = new Controller();

        assertEquals(0, controller.getUserScore("一个不存在的用户"));
    }

    /**
     * {@code @Description} 测试 addScore()
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/15-下午1:13
     */
    @org.junit.jupiter.api.Test
    void addScore() {
        Controller controller = new Controller();

        assertTrue(controller.addScore(new User(
                "胡振鹏",
                100
        )));
    }

}