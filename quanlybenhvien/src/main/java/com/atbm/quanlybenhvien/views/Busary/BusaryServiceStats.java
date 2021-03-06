package com.atbm.quanlybenhvien.views.Busary;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class BusaryServiceStats extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private GenericStuff genericStuff = new GenericStuff();

	private DefaultTableModel tableDichVu;

	public DefaultTableModel getTableDichVu() {
		return tableDichVu;
	}

	public void setTableDichVu(DefaultTableModel tableDichVu) {
		this.tableDichVu = tableDichVu;
	}

	private JTable tbl_DichVu;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BusaryServiceStats frame = new BusaryServiceStats(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BusaryServiceStats(final User user) {
		this.user = user;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 440, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane_DichVu = new JScrollPane();
		scrollPane_DichVu.setBounds(10, 11, 400, 289);
		contentPane.add(scrollPane_DichVu);

		draw_TableDichVu();
		tbl_DichVu = new JTable(tableDichVu);
		tbl_DichVu.setAutoCreateRowSorter(true);
		genericStuff.resizeTable(tbl_DichVu);
		scrollPane_DichVu.setViewportView(tbl_DichVu);

		JLabel label_13 = new JLabel("2020 Nhóm 23 - Demo Quản Lý Bệnh Viện");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_13.setBounds(45, 307, 330, 14);
		contentPane.add(label_13);
	}

	@SuppressWarnings("serial")
	private void draw_TableDichVu() {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.DICHVU";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong toa thuốc!");

			} else {
				String[] columns = { "STT", "Mã DV", "Tên DV", "Giá", "Mô Tả", "Bác Sĩ" };
				tableDichVu = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {

					String[] data = { String.valueOf(i++), res.getString("MADV"), res.getString("TEN"),
							res.getString("GIA"), res.getString("MOTA") != null ? res.getString("CHIPHI") : "Chưa Tính",
							res.getString("BACSI") };
					tableDichVu.addRow(data);

				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
