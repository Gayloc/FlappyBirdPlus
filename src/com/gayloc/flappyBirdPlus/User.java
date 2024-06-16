/**
* @Description 定义用户数据模型 
* @Author 古佳乐
* @Date 2024/6/15-下午1:13
*/

package com.gayloc.flappyBirdPlus;

/**
* {@code @Description} 用户类
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/15-下午1:13
*/
public class User {

    private String name; // 姓名
    private int score; // 分数

    /**
    * {@code @Description} 构造函数，初始化姓名和分数 
    * {@code @Param} String name, int score
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    public User(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
    * {@code @Description} 获取姓名 
    * {@code @Param} void 
    * {@code @return} String 
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    public String getName() {
        return name;
    }

    /**
    * {@code @Description} 设置姓名（实际上更新用户需要更新整个对象所以没有用到） 
    * {@code @Param} String Name 
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * {@code @Description} 获取分数 
    * {@code @Param} void 
    * {@code @return} int 
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    public int getScore() {
        return score;
    }

    /**
    * {@code @Description} 设置分数 
    * {@code @Param} int score 
    * {@code @return} void 
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    public void setScore(int score) {
        this.score = score;
    }

    /**
    * {@code @Description} 重写 toString 方法以方便控制台日志输出 
    * {@code @Param}  
    * {@code @return}  
    * {@code @Author} 古佳乐
    * {@code @Date} -
    */
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
