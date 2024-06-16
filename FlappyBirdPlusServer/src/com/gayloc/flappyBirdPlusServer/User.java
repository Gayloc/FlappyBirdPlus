/**
* @Description 用户的数据模型
* @Author 古佳乐
* @Date 2024/6/15-下午1:13
*/

package com.gayloc.flappyBirdPlusServer;

/**
* {@code @Description} 用户的数据模型
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/15-下午1:13
*/
public class User {

    // 姓名和分数
    private String name;
    private int score;

    /**
    * {@code @Description} 用户的构造函数
    * {@code @Param} String name, int score
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    public User(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
    * {@code @Description} 获取用户姓名
    * {@code @return} String
    * {@code @Param} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    public String getName() {
        return name;
    }

    /**
    * {@code @Description} 设置用户姓名
    * {@code @Param} String name
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * {@code @Description} 获取用户分数
    * {@code @Param} void
    * {@code @return} int
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    public int getScore() {
        return score;
    }

    /**
    * {@code @Description} 设置用户分数
    * {@code @Param} int score
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    public void setScore(int score) {
        this.score = score;
    }

    /**
    * {@code @Description} 重写 toString 方法
    * {@code @Param} void
    * {@code @return} String
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
