package com.atbm.quanlybenhvien.views.Doctor;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DoctorPatDetails extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private DefaultTableModel tableDichVu;

	public DefaultTableModel getTableDichVu() {
		return tableDichVu;
	}

	public void setTableDichVu(DefaultTableModel tableDichVu) {
		this.tableDichVu = tableDichVu;
	}

	private DefaultTableModel tableToaThuoc;

	public DefaultTableModel getTableToaThuoc() {
		return tableToaThuoc;
	}

	public void setTableToaThuoc(DefaultTableModel tableToaThuoc) {
		this.tableToaThuoc = tableToaThuoc;
	}

	private GenericStuff genericStuff = new GenericStuff();
	private JTable tbl_DichVu;
	private JTable tbl_ToaThuoc;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorPatDetails frame = new DoctorPatDetails(new User(), new String(), new String());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DoctorPatDetails(final User user, final String maKB, final String benhNhan) {
		this.user = user;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Dịch Vụ Bệnh Nhân Đã Sử Dụng:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 11, 184, 14);
		contentPane.add(lblNewLabel);
		drawTable_DichVu(maKB);
		JScrollPane scrollPane_DichVu = new JScrollPane();
		scrollPane_DichVu.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_DichVu.setBounds(10, 40, 350, 300);
		contentPane.add(scrollPane_DichVu);
		tbl_DichVu = new JTable(tableDichVu);
		genericStuff.resizeTable(tbl_DichVu);
		scrollPane_DichVu.setViewportView(tbl_DichVu);

		JLabel lblNewLabel_1 = new JLabel("Thuốc Đã Kê Cho Bệnh Nhân:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_1.setBounds(385, 11, 184, 14);
		contentPane.add(lblNewLabel_1);
		drawTable_ToaThuoc(maKB);
		JScrollPane scrollPane_ToaThuoc = new JScrollPane();
		scrollPane_ToaThuoc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_ToaThuoc.setBounds(385, 40, 389, 300);
		contentPane.add(scrollPane_ToaThuoc);
		tbl_ToaThuoc = new JTable(tableToaThuoc);
		genericStuff.resizeTable(tbl_ToaThuoc);
		scrollPane_ToaThuoc.setViewportView(tbl_ToaThuoc);

		JPanel buttonPanel_DichVu = new JPanel();
		buttonPanel_DichVu.setBounds(10, 351, 350, 30);
		contentPane.add(buttonPanel_DichVu);
		buttonPanel_DichVu.setLayout(new FlowLayout(FlowLayout.LEFT));
		getContentPane().add(buttonPanel_DichVu, BorderLayout.NORTH);
		{
			JButton themBtn = new JButton("Thêm Mới");
			themBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					DoctorAddServices doctorAddServices = new DoctorAddServices(user, maKB);
					genericStuff.call_dialog(doctorAddServices);
				}
			});
			buttonPanel_DichVu.add(themBtn);
		}
		{
			JButton cancelButton = new JButton("Sửa Dịch Vụ");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			buttonPanel_DichVu.add(cancelButton);
		}

		JPanel buttonPanel_ToaThuoc = new JPanel();
		buttonPanel_ToaThuoc.setBounds(385, 351, 389, 30);
		contentPane.add(buttonPanel_DichVu);
		buttonPanel_ToaThuoc.setLayout(new FlowLayout(FlowLayout.LEFT));
		getContentPane().add(buttonPanel_ToaThuoc, BorderLayout.NORTH);
		{
			JButton themBtn = new JButton("Thêm Mới");
			themBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			buttonPanel_ToaThuoc.add(themBtn);
		}
		{
			JButton cancelButton = new JButton("Sửa Toa Thuốc");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			buttonPanel_ToaThuoc.add(cancelButton);
		}
	}

	@SuppressWarnings("serial")
	private void drawTable_DichVu(String maKB) {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.VIEW_DIEUPHOIDICHVU DPDV JOIN QLBV.DICHVU DV ON DPDV.MADV = DV.MADV WHERE DPDV.MAKB = '"
					+ maKB + "'";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong khám bệnh!");

			} else {
				String[] columns = { "STT", "Mã Khám Bệnh", "Mã Dịch Vụ", "Dịch Vụ", "Mô Tả" };
				tableDichVu = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					String[] data = { String.valueOf(i++), res.getString("MAKB"), res.getString("MADV"),
							res.getString("TEN"), res.getString("MOTA") != null ? res.getString("MOTA") : "Chưa Có" };
					tableDichVu.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	private void drawTable_ToaThuoc(String maKB) {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.VIEW_TOATHUOC TT JOIN QLBV.THUOC T ON TT.MAT = T.MAT WHERE TT.MAKB =  '"
					+ maKB + "'";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong khám bệnh!");

			} else {
				String[] columns = { "STT", "Mã Khám Bệnh", "Mã Thuốc", "Số Lượng", "Thuốc", "Mô Tả" };
				tableToaThuoc = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				// Mô tả của Toa Thuốc, không lấy mô tả của Thuốc để tiện cho việc chỉnh sửa vì
				// quyền của BS chỉ giới hạn ở quyền xem ở Thuốc và đã có chức năng xem thuốc
				// cho BS
				do {
					String[] data = { String.valueOf(i++), res.getString("MAKB"), res.getString("MAT"),
							res.getString("SOLUONG"), res.getString("TEN"),
							res.getString("MOTA") != null ? res.getString("MOTA") : "Chưa Có" };
					tableToaThuoc.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
