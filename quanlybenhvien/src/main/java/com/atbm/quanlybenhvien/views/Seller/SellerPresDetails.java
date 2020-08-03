package com.atbm.quanlybenhvien.views.Seller;

import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;

import javax.swing.JTable;
import javax.swing.JScrollPane;

public class SellerPresDetails extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private JTable tbl_PresDetail;

	private DefaultTableModel tableDetailPres;

	public DefaultTableModel getTableDetailPres() {
		return tableDetailPres;
	}

	public void setTableDetailPres(DefaultTableModel tableDetailPres) {
		this.tableDetailPres = tableDetailPres;
	}

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			SellerPresDetails dialog = new SellerPresDetails(new User(), new String());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SellerPresDetails(User user, String maKB) {
		this.user = user;

		setBounds(100, 100, 600, 400);
		getContentPane().setLayout(null);

		JScrollPane scrollPane_PresDetail = new JScrollPane();
		scrollPane_PresDetail.setBounds(10, 11, 564, 339);
		getContentPane().add(scrollPane_PresDetail);
		draw_PresDetail(maKB);
		tbl_PresDetail = new JTable(tableDetailPres);
		scrollPane_PresDetail.setViewportView(tbl_PresDetail);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

	}

	@SuppressWarnings("serial")
	private void draw_PresDetail(String maKB) {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT TOATHUOC.MAKB MAKB, THUOC.TEN TEN, TOATHUOC.MOTA MOTA, THUOC.GIA DONGIA, TOATHUOC.SOLUONG SOLUONG, THUOC.GIA*TOATHUOC.SOLUONG TONGGIA\r\n"
					+ "FROM QLBV.TOATHUOC\r\n" + "INNER JOIN QLBV.THUOC\r\n" + "ON TOATHUOC.MAT = THUOC.MAT\r\n"
					+ "WHERE TOATHUOC.MAKB = '" + maKB + "'";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong toa thuốc!");

			} else {
				String[] columns = { "STT", "Mã Khám Bệnh", "Tên Thuốc", "Mô Tả", "Giá", "Số Lượng", "Tổng Giá" };
				tableDetailPres = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					String[] data = { String.valueOf(i++), res.getString("MAKB"), res.getString("TEN"),
							res.getString("MOTA"), res.getString("DONGIA"), res.getString("SOLUONG"),
							res.getString("TONGGIA") };
					tableDetailPres.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
