package com.atbm.quanlybenhvien.views.Seller;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SellerPres extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tbl_Pres;

	private DefaultTableModel tablePrescription;

	public DefaultTableModel getTablePrescription() {
		return tablePrescription;
	}

	public void setTablePrescription(DefaultTableModel tablePrescription) {
		this.tablePrescription = tablePrescription;
	}

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private GenericStuff genericStuff = new GenericStuff();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SellerPres frame = new SellerPres(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SellerPres(final User user) {
		this.user = user;

		setTitle("Thống Kê Theo Toa Thuốc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblToaThuoc = new JLabel("THỐNG KÊ CÁC TOA THUÔC HIẸN TẠI");
		lblToaThuoc.setBounds(10, 336, 425, 14);
		lblToaThuoc.setFont(new Font("Times New Roman", Font.BOLD, 12));
		contentPane.add(lblToaThuoc);

		JScrollPane scrollPane_Pres = new JScrollPane();
		scrollPane_Pres.setBounds(10, 11, 300, 314);
		scrollPane_Pres.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane_Pres);
		draw_Prescription();
		tbl_Pres = new JTable(tablePrescription);
		tbl_Pres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tbl_Pres);
		scrollPane_Pres.setViewportView(tbl_Pres);

		JPanel panelBack = new JPanel();
		panelBack.setBounds(320, 240, 100, 110);
		panelBack.setLayout(null);
		panelBack.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panelBack);
		JLabel lblIconBack = new JLabel();
		lblIconBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				SellerDashBoard sellerDashBoard = new SellerDashBoard(user);
				genericStuff.call_frame(sellerDashBoard);
			}
		});
		lblIconBack.setBounds(10, 11, 80, 80);
		panelBack.add(lblIconBack);
		JLabel lblBack = new JLabel("Quay Lại", SwingConstants.CENTER);
		lblBack.setForeground(Color.BLACK);
		lblBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		ImageIcon imageIcon_Back = new ImageIcon(SellerPres.class.getResource("/images/Back.png"));
		Image image_Back = imageIcon_Back.getImage();
		Image newImage_Back = image_Back.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconBack.setIcon(new ImageIcon(newImage_Back));
		lblBack.setBounds(0, 86, 100, 14);
		genericStuff.hover(lblIconBack, lblBack, panelBack, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		panelBack.add(lblBack);

		JButton btnDetail = new JButton("Xem Chi Tiết");
		btnDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected_MaKB = tablePrescription.getValueAt(tbl_Pres.getSelectedRow(), 1).toString();
				SellerPresDetails sellerPresDetails = new SellerPresDetails(user, selected_MaKB);
				genericStuff.call_dialog(sellerPresDetails);
			}
		});
		btnDetail.setBounds(320, 11, 115, 23);
		contentPane.add(btnDetail);
	}

	// Hàm vẽ bảng
	@SuppressWarnings("serial")
	public void draw_Prescription() {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT SUM(THUOC.GIA * TOATHUOC.SOLUONG) AS TONGGIA, TOATHUOC.MAKB\r\n"
					+ "FROM QLBV.TOATHUOC\r\n" + "INNER JOIN QLBV.THUOC\r\n" + "ON TOATHUOC.MAT = THUOC.MAT\r\n"
					+ "GROUP BY TOATHUOC.MAKB\r\n" + "ORDER BY TOATHUOC.MAKB ASC";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong toa thuốc!");

			} else {
				String[] columns = { "STT", "Mã Khám Bệnh", "Tổng Tiền" };
				tablePrescription = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					String[] data = { String.valueOf(i++), res.getString("MAKB"), res.getString("TONGGIA") };
					tablePrescription.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
