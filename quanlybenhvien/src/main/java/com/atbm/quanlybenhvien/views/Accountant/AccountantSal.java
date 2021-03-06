package com.atbm.quanlybenhvien.views.Accountant;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AccountantSal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private DefaultTableModel tableNVien;

	public DefaultTableModel getTableNVien() {
		return tableNVien;
	}

	public void setTableNVien(DefaultTableModel tableNVien) {
		this.tableNVien = tableNVien;
	}

	private GenericStuff genericStuff = new GenericStuff();

	private User user;
	private JTable tblLuongNhanVien;

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
					AccountantSal frame = new AccountantSal(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AccountantSal(final User user) {
		this.user = user;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelBack = new JPanel();
		panelBack.setLayout(null);
		panelBack.setBackground(Color.LIGHT_GRAY);
		panelBack.setBounds(374, 151, 100, 110);
		getContentPane().add(panelBack);
		JLabel lblBack = new JLabel("Quay Lại", SwingConstants.CENTER);
		lblBack.setForeground(Color.BLACK);
		lblBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblBack.setBounds(0, 86, 100, 14);
		JLabel lblIconBack = new JLabel();
		lblIconBack.setBounds(10, 11, 80, 80);
		panelBack.add(lblIconBack);
		ImageIcon imageIcon_Back = new ImageIcon(AccountantDashBoard.class.getResource("/images/Back.png"));
		Image image_Back = imageIcon_Back.getImage();
		Image newImage_Back = image_Back.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconBack.setIcon(new ImageIcon(newImage_Back));
		lblIconBack.setBounds(10, 11, 80, 80);
		lblIconBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				AccountantDashBoard accountantDashBoard = new AccountantDashBoard(user);
				genericStuff.call_frame(accountantDashBoard);
			}
		});
		genericStuff.hover(lblIconBack, lblBack, panelBack, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		panelBack.add(lblBack);

		JScrollPane scrollPane_Luong = new JScrollPane();
		scrollPane_Luong.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_Luong.setBounds(10, 11, 354, 200);
		contentPane.add(scrollPane_Luong);

		draw_SalTbl();
		tblLuongNhanVien = new JTable(tableNVien);
		tblLuongNhanVien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tblLuongNhanVien);
		scrollPane_Luong.setViewportView(tblLuongNhanVien);

		JLabel lblNewLabel = new JLabel("Bảng Lương Của Nhân Viên");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel.setBounds(49, 236, 288, 14);
		contentPane.add(lblNewLabel);

		JButton btnNewButton = new JButton("<html><center>Tính Lương Nhân Viên</center></html>");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] thongtinNV = { getTableNVien().getValueAt(tblLuongNhanVien.getSelectedRow(), 1).toString(),
						getTableNVien().getValueAt(tblLuongNhanVien.getSelectedRow(), 2).toString(),
						getTableNVien().getValueAt(tblLuongNhanVien.getSelectedRow(), 3).toString(),
						getTableNVien().getValueAt(tblLuongNhanVien.getSelectedRow(), 4).toString() };
				AccountantSalDetails accountantSalDetails = new AccountantSalDetails(user, thongtinNV);
				genericStuff.call_dialog(accountantSalDetails);
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnNewButton.setVerticalAlignment(SwingConstants.TOP);
		btnNewButton.setBounds(374, 11, 100, 45);
		contentPane.add(btnNewButton);
	}

	// Hàm vẽ bảng
	@SuppressWarnings("serial")
	private void draw_SalTbl() {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT LUONG.*, TO_NUMBER(QLBV.FNC_DECRYPT_LUONG(LUONG.MANV, LUONG.LUONGCOBAN)) AS LUONGCOBAN_DECRYPT FROM QLBV.LUONG LUONG";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong lương!");

			} else {
				String[] columns = { "STT", "Mã NV", "Lương Cơ Bản", "Trạng Thái", "Phụ Cấp" };
				tableNVien = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					String[] data = { String.valueOf(i++), res.getString("MANV"), res.getString("LUONGCOBAN_DECRYPT"),
							res.getString("TRANGTHAI"), res.getString("PHUCAP") };
					tableNVien.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
