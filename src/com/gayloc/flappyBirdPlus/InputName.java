/**
* @Description 设置姓名的对话框
* @Author 古佳乐
* @Date 2024/6/15-下午6:15
*/

package com.gayloc.flappyBirdPlus;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

/**
* {@code @Description} 设置姓名的对话框
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/15-下午6:15
*/
public class InputName extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField;

    private final boolean isForce; // 是否强制用户输入姓名
    private boolean choice = false; // 储存用户的选择

    /**
    * {@code @Description} 获取输入的内容
    * {@code @Param} void
    * {@code @return} String
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午6:15
    */
    public String getInput() {
        return textField.getText();
    }

    /**
    * {@code @Description} 获取用户的选择
    * {@code @Param} void
    * {@code @return} boolean
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午6:15
    */
    public boolean getChoice() {
        return choice;
    }

    /**
    * {@code @Description} 构造函数，判断是否强制用户输入做出不同的反应
    * {@code @Param} boolean isForceParam
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午6:15
    */
    public InputName(boolean isForceParam) {
        isForce = isForceParam;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // 点击 X 时调用 onCancel()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {onCancel();}
        });

        // 遇到 ESCAPE 时调用 onCancel()
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        try {
            this.setIconImage(ImageIO.read(Objects.requireNonNull(App.class.getResource("/images/icon.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.pack();
        this.setLocationRelativeTo(null);
        this.setTitle("设置姓名");
        this.setVisible(true);
    }

    /**
    * {@code @Description} 点击 Ok 按钮触发
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午6:15
    */
    private void onOK() {
        if (textField.getText().isEmpty()) {
            // 用户的输入不能为空
            JOptionPane.showMessageDialog(null, "请输入姓名");
            return;
        }

        choice = true;
        dispose();
    }

    /**
    * {@code @Description} 点击 Cancel, X, Esc 触发
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午6:15
    */
    private void onCancel() {
        if (isForce) {
            // 在强制状态下取消输入视为退出游戏
            System.exit(0);
        }
        choice = false;
        dispose();
    }
}
