package com.gayloc.flappyBirdPlus;

import javax.swing.*;
import java.awt.event.*;

public class InputName extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField;

    private final boolean isForce;

    public String getInput() {
        return textField.getText();
    }

    public InputName(boolean isForceParam) {
        isForce = isForceParam;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // 点击 X 时调用 onCancel()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // 遇到 ESCAPE 时调用 onCancel()
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void onOK() {
        // 在此处添加您的代码
        dispose();
    }

    private void onCancel() {
        if (isForce) {
            JOptionPane.showMessageDialog(contentPane, "请输入姓名");
            return;
        }
        dispose();
    }
}
