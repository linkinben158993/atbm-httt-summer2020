package com.atbm.quanlybenhvien.views.Doctor;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.awt.Image;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class DoctorPatDetails extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private DoctorPatDetails currFrame;

	public DoctorPatDetails getCurrFrame() {
		return currFrame;
	}

	public void setCurrFrame(DoctorPatDetails currFrame) {
		this.currFrame = currFrame;
	}

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

	public JTable getTbl_DichVu() {
		return tbl_DichVu;
	}

	public void setTbl_DichVu(JTable tbl_DichVu) {
		this.tbl_DichVu = tbl_DichVu;
	}

	private JTable tbl_ToaThuoc;

	public JTable getTbl_ToaThuoc() {
		return tbl_ToaThuoc;
	}

	public void setTbl_ToaThuoc(JTable tbl_ToaThuoc) {
		this.tbl_ToaThuoc = tbl_ToaThuoc;
	}

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
		setBounds(100, 100, 800, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.currFrame = (DoctorPatDetails) SwingUtilities.getWindowAncestor(contentPane);

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
		tbl_DichVu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
			JButton themBtn_DichVu = new JButton("Thêm Mới");
			themBtn_DichVu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					DoctorAddServices doctorAddServices = new DoctorAddServices(user, maKB, currFrame);
					genericStuff.call_dialog(doctorAddServices);
				}
			});
			buttonPanel_DichVu.add(themBtn_DichVu);
		}
		{
			JButton suaBtn_DichVu = new JButton("Sửa Dịch Vụ");
			suaBtn_DichVu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (tbl_DichVu.getSelectedRow() == -1) {
						JOptionPane.showMessageDialog(null, "Vui lòng chọn cột cần sửa!");
					} else {
						String maDV = tbl_DichVu.getValueAt(tbl_DichVu.getSelectedRow(), 2).toString();
						DoctorEditServices doctorEditServices = new DoctorEditServices(user, maKB, maDV, currFrame);
						genericStuff.call_dialog(doctorEditServices);
					}
				}
			});
			buttonPanel_DichVu.add(suaBtn_DichVu);
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
					DoctorAddPatMeds doctorAddPatMeds = new DoctorAddPatMeds(user, maKB, currFrame);
					genericStuff.call_dialog(doctorAddPatMeds);
				}
			});
			buttonPanel_ToaThuoc.add(themBtn);
		}
		{
			JButton cancelButton = new JButton("Sửa Toa Thuốc");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (tbl_ToaThuoc.getSelectedRow() == -1) {
						JOptionPane.showMessageDialog(null, "Vui lòng chọn toa thuốc để chỉnh sửa!");
					} else {
						String maT = tbl_ToaThuoc.getValueAt(tbl_ToaThuoc.getSelectedRow(), 2).toString();
						int SoLuong = Integer
								.valueOf(tbl_ToaThuoc.getValueAt(tbl_ToaThuoc.getSelectedRow(), 3).toString());
						String moTa = tbl_ToaThuoc.getValueAt(tbl_ToaThuoc.getSelectedRow(), 4) != null
								? tbl_ToaThuoc.getValueAt(tbl_ToaThuoc.getSelectedRow(), 5).toString()
								: "";
						DoctorEditPatMeds doctorEditPatMeds = new DoctorEditPatMeds(user, maKB, maT, SoLuong, moTa,
								currFrame);
						genericStuff.call_dialog(doctorEditPatMeds);
					}
				}
			});
			buttonPanel_ToaThuoc.add(cancelButton);
		}

		JPanel panelBack = new JPanel();
		panelBack.setLayout(null);
		panelBack.setBackground(Color.LIGHT_GRAY);
		panelBack.setBounds(674, 392, 100, 110);
		contentPane.add(panelBack);
		JLabel lblBack = new JLabel("Quay Lại", SwingConstants.CENTER);
		lblBack.setForeground(Color.BLACK);
		lblBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblBack.setBounds(0, 86, 100, 14);
		JLabel lblIconBack = new JLabel();
		lblIconBack.setBounds(10, 11, 80, 80);
		panelBack.add(lblIconBack);
		ImageIcon imageIcon_Back = new ImageIcon(DoctorPatDetails.class.getResource("/images/Back.png"));
		Image image_Back = imageIcon_Back.getImage();
		Image newImage_Back = image_Back.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconBack.setIcon(new ImageIcon(newImage_Back));
		lblIconBack.setBounds(10, 11, 80, 80);
		lblIconBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				DoctorDashBoard doctorDashBoard = new DoctorDashBoard(user);
				genericStuff.call_frame(doctorDashBoard);
			}
		});
		genericStuff.hover(lblIconBack, lblBack, panelBack, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		panelBack.add(lblBack);

		JLabel label_13 = new JLabel("2020 Nhóm 23 - Demo Quản Lý Bệnh Viện");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_13.setBounds(239, 488, 330, 14);
		contentPane.add(label_13);
	}

	@SuppressWarnings("serial")
	public void drawTable_DichVu(String maKB) {
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
	public void drawTable_ToaThuoc(String maKB) {
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
