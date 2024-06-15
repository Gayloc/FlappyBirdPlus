package com.gayloc.flappyBirdPlus;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class App {

    public static Boolean showLocation = false;
    public static Boolean showBBox = false;

    private static User user;
    private static Client client;

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
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(null, aboutItem.getText()));

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

        //放在最下面的
        fileMenu.add(exitItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(rankMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }

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

    public static User getUser() {
        return user;
    }

    public static void main(String[] args) {
        if (Objects.equals(args[0], "test")) {
            client = new Client("http://localhost:8000");
        } else {
            client = new Client("http://47.115.212.24:8000");
        }

        user = client.getFromLocal();
        if (user == null) {
            InputName inputName = new InputName(true);
            int score = client.getScore(inputName.getInput());
            client.saveToLocal(new User(inputName.getInput(), score));
            user = client.getFromLocal();
        } else {
            user.setScore(client.getScore(user.getName()));
        }

        // https://stackoverflow.com/a/22534931/4655368
        SwingUtilities.invokeLater(App::initWindow);
    }
}
