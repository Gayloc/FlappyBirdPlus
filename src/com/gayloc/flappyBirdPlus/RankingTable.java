/**
* @Description 显示排行榜的窗口
* @Author 古佳乐
* @Date 2024/6/15-下午10:14
*/

package com.gayloc.flappyBirdPlus;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.util.Objects;

/**
* {@code @Description} 窗口类
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/15-下午10:14
*/
public class RankingTable extends JFrame {
    // 组件
    private JPanel panel1;
    private JTable table;
    private JScrollPane scrollPane;

    // 表格的数据模型
    RankingTableModel rankingTableModel;

    /**
    * {@code @Description} 构造函数，从客户端中获取数据
    * {@code @Param} Client client
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午10:14
    */
    RankingTable(Client client) {
        User[] users = client.getTopUsers();

        // 如果获取失败就不显示窗口
        if (users.length == 0) {
            return;
        }

        // 更新表格数据
        rankingTableModel.updateData(users);

        this.setContentPane(panel1);

        try {
            this.setIconImage(ImageIO.read(Objects.requireNonNull(App.class.getResource("/images/icon.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setTitle("排行榜");
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
    * {@code @Description} 初始化自定义组件
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午10:14
    */
    private void createUIComponents() {
        table = new JTable();
        rankingTableModel = new RankingTableModel();
        table.setModel(rankingTableModel);
    }
}

/**
* {@code @Description} 定义表格数据模型
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/15-下午10:14
*/
class RankingTableModel extends AbstractTableModel {
    // 默认数据为空
    private User[] topUsers = new User[0];

    /**
    * {@code @Description} 更新表格数据
    * {@code @Param} User[] users
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午10:14
    */
    public void updateData(User[] users) {
        topUsers = users;
        fireTableDataChanged();
    }

    /**
    * {@code @Description} 获取行数
    * {@code @Param} void
    * {@code @return} int
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午10:14
    */
    @Override
    public int getRowCount() {
        return topUsers.length;
    }

    /**
    * {@code @Description} 获取列数
    * {@code @Param} void
    * {@code @return} int
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午10:14
    */
    @Override
    public int getColumnCount() {
        return 2;
    }

    /**
    * {@code @Description} 获取数据
    * {@code @Param} int rowIndex, int columnIndex
    * {@code @return} Object
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午10:14
    */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return topUsers[rowIndex].getName();
        } else {
            return topUsers[rowIndex].getScore();
        }
    }

    /**
    * {@code @Description} 获取表头
    * {@code @Param} int column
    * {@code @return} String
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午10:14
    */
    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "姓名";
        } else {
            return "分数";
        }
    }
}