/**
* @Description 程序入口
* @Author 古佳乐
* @Date 2024/6/13-下午1:45
*/

package com.gayloc.flappyBirdPlus;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
* {@code @Description} 程序主类
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/13-下午1:45
*/
public class App {

    // 两个全局静态布尔变量，设定是否显示碰撞箱和坐标
    public static Boolean showLocation = false;
    public static Boolean showBBox = false;

    // 用来储存当前登录的用户和创建的客户端
    private static User user;
    private static Client client;

    /**
    * {@code @Description} 创建游戏窗口
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    private static void initWindow() {

        JFrame window = new JFrame("FlappyBirdPlus");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Board board = new Board(client);
        window.add(board);

        JMenuBar menuBar = getMenuBar();
        window.setJMenuBar(menuBar);

        window.addKeyListener(board);
        window.addMouseListener(board);

        try {
            window.setIconImage(ImageIO.read(Objects.requireNonNull(App.class.getResource("/images/icon.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    /**
    * {@code @Description} 设置菜单栏
    * {@code @Param} void
    * {@code @return} JMenuBar
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    private static JMenuBar getMenuBar() {

        Font font = new Font("微软雅黑", Font.PLAIN, 12);
        EmptyBorder boarder = new EmptyBorder(0, 0, 0, 0);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        menuBar.setForeground(Color.BLACK);

        JMenu fileMenu = new JMenu("文件");
        fileMenu.setBackground(Color.WHITE);
        fileMenu.setForeground(Color.BLACK);
        fileMenu.setFont(font);
        fileMenu.setBorder(boarder);

        JMenu helpMenu = new JMenu("帮助");
        helpMenu.setBackground(Color.WHITE);
        helpMenu.setForeground(Color.BLACK);
        helpMenu.setFont(font);
        helpMenu.setBorder(boarder);

        JMenu rankMenu = new JMenu("排行榜");
        rankMenu.setBackground(Color.WHITE);
        rankMenu.setForeground(Color.BLACK);
        rankMenu.setFont(font);
        rankMenu.setBorder(boarder);

        JMenuItem exitItem = new JMenuItem("退出");
        exitItem.addActionListener(e -> System.exit(0));
        exitItem.setFont(font);
        exitItem.setBackground(Color.WHITE);
        exitItem.setForeground(Color.BLACK);
        exitItem.setBorder(boarder);

        JMenuItem aboutItem = new JMenuItem("关于");
        aboutItem.setBorder(boarder);
        aboutItem.setFont(font);
        aboutItem.setBackground(Color.WHITE);
        aboutItem.setForeground(Color.BLACK);
        aboutItem.addActionListener(e -> new AboutDlg());

        JMenuItem locationItem = new JMenuItem("位置显示");
        locationItem.setBorder(boarder);
        locationItem.setFont(font);
        locationItem.setBackground(Color.WHITE);
        locationItem.setForeground(Color.BLACK);
        locationItem.addActionListener(e -> showLocation = !showLocation);
        helpMenu.add(locationItem);

        JMenuItem bboxItem = new JMenuItem("碰撞显示");
        bboxItem.setBorder(boarder);
        bboxItem.setFont(font);
        bboxItem.setBackground(Color.WHITE);
        bboxItem.setForeground(Color.BLACK);
        bboxItem.addActionListener(e -> showBBox = !showBBox);
        helpMenu.add(bboxItem);

        JMenuItem deleteScoreItem = getDeleteScoreItem(boarder, font);
        rankMenu.add(deleteScoreItem);

        JMenuItem setNameItem = getSetNameItem(boarder, font);
        rankMenu.add(setNameItem);

        JMenuItem rankingTableItem = new JMenuItem("查看排行榜");
        rankingTableItem.setBorder(boarder);
        rankingTableItem.setFont(font);
        rankingTableItem.setBackground(Color.WHITE);
        rankingTableItem.setForeground(Color.BLACK);
        rankingTableItem.addActionListener(e -> new RankingTable(client));
        rankMenu.add(rankingTableItem);

        // 放在最下面的
        fileMenu.add(exitItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(rankMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }

    /**
    * {@code @Description} 设置删除分数的选项 
    * {@code @Param} EmptyBorder boarder, Font font 
    * {@code @return} JMenuItem 
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午9:46
    */
    private static JMenuItem getDeleteScoreItem(EmptyBorder boarder, Font font) {
        JMenuItem deleteScoreItem = new JMenuItem("删除分数记录");
        deleteScoreItem.setBorder(boarder);
        deleteScoreItem.setFont(font);
        deleteScoreItem.setBackground(Color.WHITE);
        deleteScoreItem.setForeground(Color.BLACK);
        deleteScoreItem.addActionListener(e -> {
            int selection = JOptionPane.showConfirmDialog(null, "确认删除分数");
            if (selection == 0) {
                if (client.deleteScore(user.getName())) {
                    JOptionPane.showMessageDialog(null, "删除成功");
                    user.setScore(0);
                    client.saveToLocal(user);
                    Board.getBestScoreText().updateUser(user);
                }
            }
        });
        return deleteScoreItem;
    }

    /**
     * {@code @Description} 设置设置姓名的选项
     * {@code @Param} EmptyBorder boarder, Font font
     * {@code @return} JMenuItem
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/15-下午9:08
     */
    private static JMenuItem getSetNameItem(EmptyBorder boarder, Font font) {
        JMenuItem setNameItem = new JMenuItem("修改姓名");
        setNameItem.setBorder(boarder);
        setNameItem.setFont(font);
        setNameItem.setBackground(Color.WHITE);
        setNameItem.setForeground(Color.BLACK);
        setNameItem.addActionListener(e -> {
            InputName inputName = new InputName(false);
            if (inputName.getChoice()) {
                int score = client.getScore(inputName.getInput());
                client.saveToLocal(new User(inputName.getInput(), score));
                user = client.getFromLocal();
                Board.getBestScoreText().updateUser(user);
            }
        });
        return setNameItem;
    }

    /**
    * {@code @Description} 获取当前登录用户对象
    * {@code @Param} void
    * {@code @return} User
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午6:15
    */
    public static User getUser() {
        return user;
    }

    /**
    * {@code @Description} 主函数
    * {@code @Param} String[] args
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午10:50
    */
    public static void main(String[] args) {
        // 默认的服务器路径
        String serverUrl = "http://47.115.212.24:8000";

        // 如果参数中带有 test 则改服务器地址为本地服务器地址
        if (args.length != 0) {
            if (Objects.equals(args[0], "test")) {
                serverUrl = "http://localhost:8000";
            }
        }
        // 创建客户端用于通信
        client = new Client(serverUrl);

        // 从本地配置文件获取用户信息
        user = client.getFromLocal();

        // 如果无法从本地获取信息
        if (user == null) {
            // 弹出对话框要求输入，isForce 为 true
            InputName inputName = new InputName(true);

            // 从服务器查询对应分数信息
            int score = client.getScore(inputName.getInput());
            // 保存到本地
            client.saveToLocal(new User(inputName.getInput(), score));
            user = client.getFromLocal();
        } else {
            // 更新本地用户分数信息，实现多端同步
            user.setScore(client.getScore(user.getName()));
        }

        // 关于使用 invokeLater 的原因 https://stackoverflow.com/a/22534931/4655368
        SwingUtilities.invokeLater(App::initWindow);
    }
}
