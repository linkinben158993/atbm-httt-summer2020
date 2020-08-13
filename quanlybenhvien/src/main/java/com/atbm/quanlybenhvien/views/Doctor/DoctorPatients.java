package com.atbm.quanlybenhvien.views.Doctor;

import java.awt.Color;
import java.awt.EventQueue;
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

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DoctorPatients extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User user;

	private GenericStuff genericStuff = new GenericStuff();

	private JTable tblPatients;

	public JTable getTblPatients() {
		return tblPatients;
	}

	public void setTblPatients(JTable tblPatients) {
		this.tblPatients = tblPatients;
	}

	private DefaultTableModel tablePatients;

	public DefaultTableModel getTablePatients() {
		return tablePatients;
	}

	public void setTablePatients(DefaultTableModel tablePatients) {
		this.tablePatients = tablePatients;
	}

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
					DoctorPatients frame = new DoctorPatients(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DoctorPatients(final User user) {
		this.user = user;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Bệnh Nhân Của Tôi:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 28, 300, 14);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane_Patients = new JScrollPane();
		scrollPane_Patients.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_Patients.setBounds(10, 53, 430, 297);
		contentPane.add(scrollPane_Patients);

		draw_TblPatients();
		tblPatients = new JTable(tablePatients);
		tblPatients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tblPatients);
		scrollPane_Patients.setViewportView(tblPatients);

		JButton btnDetails = new JButton("Chi Tiết");
		btnDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tblPatients.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn ca bệnh để xem chi tiết!");
				} else {
					String maKB = tblPatients.getValueAt(tblPatients.getSelectedRow(), 1).toString();
					String benhNhan = tblPatients.getValueAt(tblPatients.getSelectedRow(), 2).toString();
					dispose();
					DoctorPatDetails doctorPatDetails = new DoctorPatDetails(user, maKB, benhNhan);
					genericStuff.call_frame(doctorPatDetails);
				}
			}
		});
		btnDetails.setBounds(484, 53, 100, 23);
		contentPane.add(btnDetails);

		JPanel panelBack = new JPanel();
		panelBack.setLayout(null);
		panelBack.setBackground(Color.LIGHT_GRAY);
		panelBack.setBounds(484, 251, 100, 110);
		contentPane.add(panelBack);
		JLabel lblBack = new JLabel("Quay Lại", SwingConstants.CENTER);
		lblBack.setForeground(Color.BLACK);
		lblBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblBack.setBounds(0, 86, 100, 14);
		JLabel lblIconBack = new JLabel();
		lblIconBack.setBounds(10, 11, 80, 80);
		panelBack.add(lblIconBack);
		ImageIcon imageIcon_Back = new ImageIcon(DoctorPatients.class.getResource("/images/Back.png"));
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
	}

	@SuppressWarnings("serial")
	private void draw_TblPatients() {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.VIEW_KHAMBENH";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong khám bệnh!");

			} else {
				String[] columns = { "STT", "Mã Khám Bệnh", "Bệnh Nhân", "Phòng", "Thời Gian", "Mô Tả" };
				tablePatients = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					String[] data = { String.valueOf(i++), res.getString("MAKB"), res.getString("BENHNHAN"),
							res.getString("PHONG"), res.getString("THOIGIAN"),
							res.getString("MOTA") != null ? res.getString("MOTA") : "Chưa Có" };
					tablePatients.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
