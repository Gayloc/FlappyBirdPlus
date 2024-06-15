package com.gayloc.flappyBirdPlus;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class RankingTable extends JFrame {
    private JPanel panel1;
    private JTable table;
    private JScrollPane scrollPane;

    RankingTableModel rankingTableModel;

    RankingTable(Client client) {
        User[] users = client.getTopUsers();

        if (users.length == 0) {
            return;
        }

        rankingTableModel.updateData(users);

        this.setContentPane(panel1);
        this.setTitle("排行榜");
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void createUIComponents() {
        table = new JTable();
        rankingTableModel = new RankingTableModel();
        table.setModel(rankingTableModel);
    }
}

class RankingTableModel extends AbstractTableModel {
    private User[] topUsers = new User[0];

    public void updateData(User[] users) {
        topUsers = users;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return topUsers.length;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return topUsers[rowIndex].getName();
        } else {
            return topUsers[rowIndex].getScore();
        }
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "姓名";
        } else {
            return "分数";
        }
    }
}