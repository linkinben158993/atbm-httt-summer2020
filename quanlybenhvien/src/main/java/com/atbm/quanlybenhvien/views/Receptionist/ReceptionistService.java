package com.atbm.quanlybenhvien.views.Receptionist;

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
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReceptionistService extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private ReceptionistService currFrame;

	public ReceptionistService getCurrFrame() {
		return currFrame;
	}

	public void setCurrFrame(ReceptionistService currFrame) {
		this.currFrame = currFrame;
	}

	private GenericStuff genericStuff = new GenericStuff();

	private User user;

	private JTable tblDieuPhoi;

	public JTable getTblDieuPhoi() {
		return tblDieuPhoi;
	}

	public void setTblDieuPhoi(JTable tblDieuPhoi) {
		this.tblDieuPhoi = tblDieuPhoi;
	}

	private DefaultTableModel tableDieuPhoi;

	public DefaultTableModel getTableDieuPhoi() {
		return tableDieuPhoi;
	}

	public void setTableDieuPhoi(DefaultTableModel tableDieuPhoi) {
		this.tableDieuPhoi = tableDieuPhoi;
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
					ReceptionistService frame = new ReceptionistService(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ReceptionistService(final User user) {
		this.user = user;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.currFrame = (ReceptionistService) SwingUtilities.getWindowAncestor(contentPane);

		JScrollPane scrollPane_DieuPhoi = new JScrollPane();
		scrollPane_DieuPhoi.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_DieuPhoi.setBounds(10, 11, 314, 150);
		contentPane.add(scrollPane_DieuPhoi);

		draw_TableDieuPhoi();
		tblDieuPhoi = new JTable(tableDieuPhoi);
		tblDieuPhoi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tblDieuPhoi);
		scrollPane_DieuPhoi.setViewportView(tblDieuPhoi);

		JLabel lblDieuPhoi = new JLabel("Hàng Chờ Điều Phối");
		lblDieuPhoi.setHorizontalAlignment(SwingConstants.CENTER);
		lblDieuPhoi.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblDieuPhoi.setBounds(55, 172, 228, 14);
		contentPane.add(lblDieuPhoi);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 196, 348, 8);
		contentPane.add(separator);

		JPanel panelBack = new JPanel();
		panelBack.setBounds(364, 131, 100, 110);
		panelBack.setLayout(null);
		panelBack.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panelBack);
		JLabel lblBack = new JLabel("Quay Lại", SwingConstants.CENTER);
		lblBack.setForeground(Color.BLACK);
		lblBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblBack.setBounds(0, 86, 100, 14);
		JLabel lblIconBack = new JLabel();
		lblIconBack.setBounds(10, 11, 80, 80);
		panelBack.add(lblIconBack);
		ImageIcon imageIcon_Back = new ImageIcon(ReceptionistService.class.getResource("/images/Back.png"));
		Image image_Back = imageIcon_Back.getImage();
		Image newImage_Back = image_Back.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconBack.setIcon(new ImageIcon(newImage_Back));
		lblIconBack.setBounds(10, 11, 80, 80);
		lblIconBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				ReceptionistDashBoard receptionistDashBoard = new ReceptionistDashBoard(user);
				genericStuff.call_frame(receptionistDashBoard);
			}
		});
		genericStuff.hover(lblIconBack, lblBack, panelBack, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		panelBack.add(lblBack);

		JLabel label_13 = new JLabel("2020 Nhóm 23 - Demo Quản Lý Bệnh Viện");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_13.setBounds(10, 227, 330, 14);
		contentPane.add(label_13);

		JButton btnServiceAdd = new JButton("Thêm Mới");
		btnServiceAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				genericStuff.call_dialog(new ReceptionistAddServices(user, currFrame));
			}
		});
		btnServiceAdd.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnServiceAdd.setBounds(334, 11, 130, 23);
		contentPane.add(btnServiceAdd);

		JButton btnServiceEdit = new JButton("Sửa Điều Phối");
		btnServiceEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblDieuPhoi.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dịch vụ để sửa");
				} else {
					ReceptionistEditServices receptionistEditServices = new ReceptionistEditServices(user, currFrame,
							tblDieuPhoi.getValueAt(tblDieuPhoi.getSelectedRow(), 1).toString(),
							tblDieuPhoi.getValueAt(tblDieuPhoi.getSelectedRow(), 2).toString(),
							tblDieuPhoi.getValueAt(tblDieuPhoi.getSelectedRow(), 3).toString());
					genericStuff.call_dialog(receptionistEditServices);
				}
			}
		});
		btnServiceEdit.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnServiceEdit.setBounds(334, 45, 130, 23);
		contentPane.add(btnServiceEdit);

		JButton btnServiceDelete = new JButton("Xóa Điều Phối");
		btnServiceDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblDieuPhoi.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dịch vụ để sửa");
				} else {
					Connection conn = null;
					try {
						conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
						PreparedStatement stmt = conn
								.prepareStatement("DELETE FROM QLBV.DIEUPHOIDICHVU WHERE MAKB = ? AND MADV = ?");
						stmt.setString(1, tblDieuPhoi.getValueAt(tblDieuPhoi.getSelectedRow(), 1).toString());
						stmt.setString(2, tblDieuPhoi.getValueAt(tblDieuPhoi.getSelectedRow(), 2).toString());
						stmt.executeUpdate();

						conn.close();

						JOptionPane.showMessageDialog(null, "Xóa điều phối thành công!");

					} catch (Exception e2) {
						e2.printStackTrace();
						try {
							conn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

						JOptionPane.showMessageDialog(null, "Xóa điều phối thất bại!");
					}
					currFrame.getTableDieuPhoi().fireTableDataChanged();
					currFrame.draw_TableDieuPhoi();
					currFrame.tblDieuPhoi.setModel(currFrame.getTableDieuPhoi());
					genericStuff.resizeTable(tblDieuPhoi);
					genericStuff.call_revapaint(currFrame);
				}
			}
		});
		btnServiceDelete.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnServiceDelete.setBounds(334, 79, 130, 23);
		contentPane.add(btnServiceDelete);
	}

	@SuppressWarnings("serial")
	public void draw_TableDieuPhoi() {
		Connection conn = null;
		try {
			conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM QLBV.DIEUPHOIDICHVU ORDER BY MAKB");
			ResultSet res = stmt.executeQuery();

			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có điều phối nào được thực hiện!");
			} else {
				String[] columns = { "STT", "Mã KB", "Mã DV", "Mô Tả" };
				tableDieuPhoi = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					String[] data = { String.valueOf(i++), res.getString("MAKB"), res.getString("MADV"),
							res.getString("MOTA") != null ? res.getString("MOTA") : "Chưa Có" };
					tableDieuPhoi.addRow(data);
				} while (res.next());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}