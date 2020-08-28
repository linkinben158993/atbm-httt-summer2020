package com.atbm.quanlybenhvien.views.MBusary;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;
import com.atbm.quanlybenhvien.views.Busary.BusaryDashBoard;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;

public class MBusaryPatients extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private MBusaryPatients currFrame;

	public MBusaryPatients getCurrFrame() {
		return currFrame;
	}

	public void setCurrFrame(MBusaryPatients currFrame) {
		this.currFrame = currFrame;
	}

	private GenericStuff genericStuff = new GenericStuff();
	private DefaultTableModel tablePatients;

	public DefaultTableModel getTablePatients() {
		return tablePatients;
	}

	public void setTablePatients(DefaultTableModel tablePatients) {
		this.tablePatients = tablePatients;
	}

	private JTable tbl_Patients;

	public JTable getTbl_Patients() {
		return tbl_Patients;
	}

	public void setTbl_Patients(JTable tbl_Patients) {
		this.tbl_Patients = tbl_Patients;
	}

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MBusaryPatients frame = new MBusaryPatients(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MBusaryPatients(final User user) {
		this.user = user;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.currFrame = (MBusaryPatients) SwingUtilities.getWindowAncestor(contentPane);

		JScrollPane scrollPane_Patients = new JScrollPane();
		scrollPane_Patients.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_Patients.setBounds(10, 11, 654, 334);
		contentPane.add(scrollPane_Patients);
		draw_TableKhamBenh();
		tbl_Patients = new JTable(tablePatients);
		tbl_Patients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tbl_Patients);
		scrollPane_Patients.setViewportView(tbl_Patients);

		JButton btnAddPatient = new JButton("<html><center>Thêm Mới<br>Khám Bệnh</center></html>");
		btnAddPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MBusaryAddPatients busaryAddPatients = new MBusaryAddPatients(user, currFrame);
				genericStuff.call_dialog(busaryAddPatients);
			}
		});
		btnAddPatient.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnAddPatient.setBounds(674, 11, 100, 51);
		contentPane.add(btnAddPatient);

		JButton btnEditPatient = new JButton("<html><center>Sửa<br>Khám Bệnh</center></html>");
		btnEditPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbl_Patients.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn khám bệnh để sửa!");
				} else {
					MBusaryEditPatient mBusaryEditPatient = new MBusaryEditPatient(user, currFrame,
							tbl_Patients.getValueAt(tbl_Patients.getSelectedRow(), 1).toString(),
							tbl_Patients.getValueAt(tbl_Patients.getSelectedRow(), 2).toString(),
							tbl_Patients.getValueAt(tbl_Patients.getSelectedRow(), 6).toString(),
							tbl_Patients.getValueAt(tbl_Patients.getSelectedRow(), 5).toString(),
							tbl_Patients.getValueAt(tbl_Patients.getSelectedRow(), 8).toString());

					genericStuff.call_dialog(mBusaryEditPatient);
				}
			}
		});
		btnEditPatient.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnEditPatient.setBounds(674, 73, 100, 51);
		contentPane.add(btnEditPatient);

		JButton btnDeletePatient = new JButton("<html><center>Xóa<br>Khám Bệnh</center></html>");
		btnDeletePatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tbl_Patients.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn khám bệnh để xóa!");
				} else {
					Connection conn = null;
					try {
						conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
						PreparedStatement stmt = conn.prepareStatement("DELETE FROM QLBV.KHAMBENH WHERE MAKB = ?");
						stmt.setString(1, tbl_Patients.getValueAt(tbl_Patients.getSelectedRow(), 1).toString());
						stmt.executeUpdate();
						conn.close();
						JOptionPane.showMessageDialog(null, "Xóa khám bệnh thành công!");

					} catch (Exception e2) {
						e2.printStackTrace();
						try {
							conn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Xóa khám bệnh thất bại!");
					}
				}
				getTablePatients().fireTableDataChanged();
				draw_TableKhamBenh();
				getTbl_Patients().setModel(getTablePatients());
				genericStuff.resizeTable(getTbl_Patients());
				genericStuff.call_revapaint(contentPane);
			}
		});
		btnDeletePatient.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnDeletePatient.setBounds(674, 135, 100, 51);
		contentPane.add(btnDeletePatient);

		JPanel panelBack = new JPanel();
		panelBack.setLayout(null);
		panelBack.setBackground(Color.LIGHT_GRAY);
		panelBack.setBounds(684, 271, 100, 110);
		contentPane.add(panelBack);
		JLabel lblBack = new JLabel("Quay Lại", SwingConstants.CENTER);
		lblBack.setForeground(Color.BLACK);
		lblBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblBack.setBounds(0, 86, 100, 14);
		JLabel lblIconBack = new JLabel();
		lblIconBack.setBounds(10, 11, 80, 80);
		panelBack.add(lblIconBack);
		ImageIcon imageIcon_Back = new ImageIcon(BusaryDashBoard.class.getResource("/images/Back.png"));
		Image image_Back = imageIcon_Back.getImage();
		Image newImage_Back = image_Back.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconBack.setIcon(new ImageIcon(newImage_Back));
		lblIconBack.setBounds(10, 11, 80, 80);
		lblIconBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				MBusaryDashBoard busaryDashBoard = new MBusaryDashBoard(user);
				genericStuff.call_frame(busaryDashBoard);
			}
		});
		genericStuff.hover(lblIconBack, lblBack, panelBack, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		panelBack.add(lblBack);

		JLabel label_13 = new JLabel("2020 Nhóm 23 - Demo Quản Lý Bệnh Viện");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_13.setBounds(10, 356, 614, 14);
		contentPane.add(label_13);
	}

	@SuppressWarnings("serial")
	public void draw_TableKhamBenh() {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.KHAMBENH JOIN QLBV.BENHNHAN ON KHAMBENH.BENHNHAN = BENHNHAN.MABN ORDER BY MAKB";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong lương!");

			} else {
				String[] columns = { "STT", "Mã KB", "Bệnh Nhân", "Họ Tên", "Thời Gian", "Phòng", "Bác Sĩ", "Chi Phí",
						"Mô Tả" };
				tablePatients = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					Timestamp ts = Timestamp.valueOf(res.getString("THOIGIAN"));
					LocalDateTime lcdt = ts.toLocalDateTime();

					String[] data = { String.valueOf(i++), res.getString("MAKB"), res.getString("BENHNHAN"),
							res.getString("HOTEN"), dateTimeFormatter.format(lcdt).toString(), res.getString("PHONG"),
							res.getString("BACSI"),
							res.getString("CHIPHI") != null ? res.getString("CHIPHI") : "Chưa Tính",
							res.getString("MOTA") };
					tablePatients.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
