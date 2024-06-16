/**
* @Description 关于对话框
* @Author 古佳乐
* @Date 2024/6/16-上午11:53
*/

package com.gayloc.flappyBirdPlus;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

/**
* {@code @Description} 关于对话框
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/16-上午11:53
*/
public class AboutDlg extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel iconLabel;
    private JLabel text;

    public AboutDlg() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.pack();

        try {
            this.setIconImage(ImageIO.read(Objects.requireNonNull(App.class.getResource("/images/icon.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setLocationRelativeTo(null);
        this.setTitle("关于");
        this.setVisible(true);
    }

    private void onOK() {
        dispose();
    }

}
