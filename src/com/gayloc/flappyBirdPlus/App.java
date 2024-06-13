package com.gayloc.flappyBirdPlus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class App {
    public static Boolean displayLocation = false;

    private static void initWindow() {

        JFrame window = new JFrame("家吧扑棱飞");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = getMenuBar();
        window.setJMenuBar(menuBar);

        Board board = new Board();
        window.add(board);

        window.addKeyListener(board);
        window.addMouseListener(board);
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

        JMenuItem locationItem = new JMenuItem("启用/关闭位置显示");
        locationItem.setBorder(boarder);
        locationItem.setFont(font);
        locationItem.setBackground(Color.WHITE);
        locationItem.setForeground(Color.BLACK);
        locationItem.addActionListener(e -> displayLocation = !displayLocation);
        helpMenu.add(locationItem);

        //放在最下面的
        fileMenu.add(exitItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }

    public static void main(String[] args) {
        // invokeLater() is used here to prevent our graphics processing from
        // blocking the GUI. https://stackoverflow.com/a/22534931/4655368
        // this is a lot of boilerplate code that you shouldn't be too concerned about.
        // just know that when main runs it will call initWindow() once.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initWindow();
            }
        });
    }
}
