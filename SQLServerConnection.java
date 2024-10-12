import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SQLServerConnection extends JFrame {
    private JTextField txtMaLop;
    private JTextField txtTenLop;
    private JButton btnThemMoi;

    public SQLServerConnection() {
        // Thiết lập giao diện
        setTitle("Thêm mới lớp");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblMaLop = new JLabel("Mã Lớp:");
        lblMaLop.setBounds(10, 20, 100, 25);
        add(lblMaLop);

        txtMaLop = new JTextField();
        txtMaLop.setBounds(120, 20, 150, 25);
        add(txtMaLop);

        JLabel lblTenLop = new JLabel("Tên Lớp:");
        lblTenLop.setBounds(10, 60, 100, 25);
        add(lblTenLop);

        txtTenLop = new JTextField();
        txtTenLop.setBounds(120, 60, 150, 25);
        add(txtTenLop);

        btnThemMoi = new JButton("Thêm mới");
        btnThemMoi.setBounds(100, 100, 150, 30);
        add(btnThemMoi);

        // Thêm sự kiện cho nút "Thêm mới"
        btnThemMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themLopMoi();
            }
        });
    }

    // Phương thức để thêm lớp mới vào CSDL
    private void themLopMoi() {
        String malop = txtMaLop.getText();
        String tenlop = txtTenLop.getText();
        
        if (malop.isEmpty() || tenlop.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin.");
            return;
        }

        // Kết nối với CSDL và thêm lớp mới
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Kết nối tới SQL Server
            String connectionUrl = "jdbc:sqlserver://KURO\\KUROSQLSV:1433;databaseName=QuanLyLop;user=sa;password=110704";

            conn = DriverManager.getConnection(connectionUrl);

            // Câu lệnh SQL thêm lớp mới
            String sql = "INSERT INTO tbl_lop (malop, tenlop) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, malop);
            pstmt.setString(2, tenlop);

            // Thực thi câu lệnh
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Thêm lớp thành công!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm lớp.");
        } finally {
            // Đóng kết nối
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Hiển thị giao diện
        SQLServerConnection form = new SQLServerConnection();
        form.setVisible(true);
    }
}
