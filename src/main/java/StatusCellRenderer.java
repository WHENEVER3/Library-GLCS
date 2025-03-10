import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StatusCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if ("대출 중".equals(value)) {
            label.setForeground(Color.RED);
            label.setFont(label.getFont().deriveFont(Font.BOLD));
        } else if ("대출 가능".equals(value)) {
            label.setForeground(new Color(0, 128, 0)); // 진한 초록색으로 변경
            label.setFont(label.getFont().deriveFont(Font.PLAIN));
        } else {
            label.setForeground(Color.BLACK);
            label.setFont(label.getFont().deriveFont(Font.PLAIN));
        }

        return label;
    }
}
