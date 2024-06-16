package com.gayloc.flappyBirdPlus;

import javax.swing.*;
import java.awt.event.*;

public class InputName extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField;

    private final boolean isForce;
    private boolean choice = false;

    public String getInput() {
        return textField.getText();
    }

    public boolean getChoice() {
        return choice;
    }

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

        this.pack();
        this.setLocationRelativeTo(null);
        this.setTitle("设置姓名");
        this.setVisible(true);
    }

    private void onOK() {
        if (textField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请输入姓名");
            return;
        }

        choice = true;
        dispose();
    }

    private void onCancel() {
        if (isForce) {
            System.exit(0);
        }
        choice = false;
        dispose();
    }
}
