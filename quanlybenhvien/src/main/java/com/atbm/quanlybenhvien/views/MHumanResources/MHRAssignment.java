package com.atbm.quanlybenhvien.views.MHumanResources;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class MHRAssignment extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private GenericStuff genericStuff = new GenericStuff();

	private MHRAssignment currFrame;

	public MHRAssignment getCurrFrame() {
		return currFrame;
	}

	public void setCurrFrame(MHRAssignment currFrame) {
		this.currFrame = currFrame;
	}

	private DefaultTableModel tableTrucPhong;

	public DefaultTableModel getTableTrucPhong() {
		return tableTrucPhong;
	}

	public void setTableTrucPhong(DefaultTableModel tableTrucPhong) {
		this.tableTrucPhong = tableTrucPhong;
	}

	public DefaultTableModel getTablePhong() {
		return tablePhong;
	}

	public void setTablePhong(DefaultTableModel tablePhong) {
		this.tablePhong = tablePhong;
	}

	private JTable tbl_TrucPhong;

	public JTable getTbl_TrucPhong() {
		return tbl_TrucPhong;
	}

	public void setTbl_TrucPhong(JTable tbl_TrucPhong) {
		this.tbl_TrucPhong = tbl_TrucPhong;
	}

	private JTable tbl_Phong;

	public JTable getTbl_Phong() {
		return tbl_Phong;
	}

	public void setTbl_Phong(JTable tbl_Phong) {
		this.tbl_Phong = tbl_Phong;
	}

	private DefaultTableModel tablePhong;

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
					MHRAssignment frame = new MHRAssignment(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MHRAssignment(final User user) {
		this.user = user;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.currFrame = (MHRAssignment) SwingUtilities.getWindowAncestor(contentPane);

		JLabel lblTrucPhong = new JLabel("Bảng Thông Tin Ca Trực Phòng");
		lblTrucPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrucPhong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblTrucPhong.setBounds(10, 11, 730, 39);
		contentPane.add(lblTrucPhong);

		JScrollPane scrollPane_TrucPhong = new JScrollPane();
		scrollPane_TrucPhong.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_TrucPhong.setBounds(10, 50, 1030, 160);
		contentPane.add(scrollPane_TrucPhong);

		draw_TableTrucPhong();
		tbl_TrucPhong = new JTable(tableTrucPhong);
		tbl_TrucPhong.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tbl_TrucPhong);
		scrollPane_TrucPhong.setViewportView(tbl_TrucPhong);

		JLabel lblPhong = new JLabel("Bảng Thông Tin Các Phòng Của Bệnh Viện");
		lblPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPhong.setBounds(10, 218, 730, 28);
		contentPane.add(lblPhong);

		JScrollPane scrollPane_Phong = new JScrollPane();
		scrollPane_Phong.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_Phong.setBounds(10, 246, 1030, 160);
		contentPane.add(scrollPane_Phong);

		draw_Phong();
		tbl_Phong = new JTable(tablePhong);
		tbl_Phong.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tbl_Phong);
		scrollPane_Phong.setViewportView(tbl_Phong);

		JButton lblAddAssign = new JButton("<html><center>Thêm Mới<br>Trực Phòng</center></html>");
		lblAddAssign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MHRAddAssigment mhrAddAssigment = new MHRAddAssigment(user, currFrame);
				genericStuff.call_dialog(mhrAddAssigment);
			}
		});
		lblAddAssign.setBounds(1050, 50, 122, 33);
		contentPane.add(lblAddAssign);

		JButton btnEditAssign = new JButton("<html><center>Sửa Trực<br>Phòng</center></html>");
		btnEditAssign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbl_TrucPhong.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn trực phòng cần sửa!");
				} else {
					MHREditAssignment mhrEditAssignment = new MHREditAssignment(user, currFrame,
							tbl_TrucPhong.getValueAt(tbl_TrucPhong.getSelectedRow(), 1).toString(),
							tbl_TrucPhong.getValueAt(tbl_TrucPhong.getSelectedRow(), 2).toString());
					genericStuff.call_dialog(mhrEditAssignment);
				}
			}
		});
		btnEditAssign.setBounds(1050, 94, 122, 37);
		contentPane.add(btnEditAssign);

		JButton btnDeleteAssign = new JButton("<html><center>Xóa Trực<br>Phòng</center></html>");
		btnDeleteAssign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbl_TrucPhong.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn trực phòng cần sửa!");
				} else {
					Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
					PreparedStatement stmt = null;
					try {
						stmt = conn.prepareStatement("DELETE FROM QLBV.TRUCPHONG WHERE PHONG = ? AND NHANVIEN = ?");
						stmt.setString(1, tbl_TrucPhong.getValueAt(tbl_TrucPhong.getSelectedRow(), 1).toString());
						stmt.setString(2, tbl_TrucPhong.getValueAt(tbl_TrucPhong.getSelectedRow(), 2).toString());
						stmt.executeUpdate();
						conn.close();

						JOptionPane.showMessageDialog(null, "Xóa trực phòng thành công!");

					} catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Xóa trực phòng thất bại!");
						try {
							conn.close();
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}

					getTableTrucPhong().fireTableDataChanged();
					draw_TableTrucPhong();
					getTbl_TrucPhong().setModel(getTableTrucPhong());
					genericStuff.resizeTable(getTbl_TrucPhong());
					genericStuff.call_revapaint(getTbl_Phong());

				}
			}
		});
		btnDeleteAssign.setBounds(1050, 142, 122, 37);
		contentPane.add(btnDeleteAssign);

		JButton btnAddRoom = new JButton("Thêm Phòng");
		btnAddRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddRoom.setBounds(1048, 243, 124, 23);
		contentPane.add(btnAddRoom);

		JButton btnEditRoom = new JButton("Sửa Phòng");
		btnEditRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditRoom.setBounds(1048, 277, 124, 23);
		contentPane.add(btnEditRoom);

		JButton btnDeleteRoom = new JButton("Xóa Phòng");
		btnDeleteRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDeleteRoom.setBounds(1048, 312, 124, 23);
		contentPane.add(btnDeleteRoom);

		JPanel panelBack = new JPanel();
		panelBack.setLayout(null);
		panelBack.setBackground(Color.LIGHT_GRAY);
		panelBack.setBounds(1084, 351, 100, 110);
		contentPane.add(panelBack);
		JLabel lblBack = new JLabel("Quay Lại", SwingConstants.CENTER);
		lblBack.setForeground(Color.BLACK);
		lblBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblBack.setBounds(0, 86, 100, 14);
		JLabel lblIconBack = new JLabel();
		lblIconBack.setBounds(10, 11, 80, 80);
		panelBack.add(lblIconBack);
		ImageIcon imageIcon_Back = new ImageIcon(MHRDashBoard.class.getResource("/images/Back.png"));
		Image image_Back = imageIcon_Back.getImage();
		Image newImage_Back = image_Back.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconBack.setIcon(new ImageIcon(newImage_Back));
		lblIconBack.setBounds(10, 11, 80, 80);
		lblIconBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				MHRDashBoard mhrDashBoard = new MHRDashBoard(user);
				genericStuff.call_frame(mhrDashBoard);
			}
		});
		genericStuff.hover(lblIconBack, lblBack, panelBack, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		panelBack.add(lblBack);

		JLabel label_13 = new JLabel("2020 Nhóm 23 - Demo Quản Lý Bệnh Viện");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_13.setBounds(10, 447, 730, 14);
		contentPane.add(label_13);
	}

	@SuppressWarnings("serial")
	public void draw_TableTrucPhong() {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.TRUCPHONG ORDER BY PHONG";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();

			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong lương!");

			} else {
				String[] columns = { "STT", "Phòng", "Nhân Viên", "Từ", "Đến" };
				tableTrucPhong = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					Timestamp ts_Tu = Timestamp.valueOf(res.getString("TU"));
					LocalDateTime lcdt_Tu = ts_Tu.toLocalDateTime();

					Timestamp ts_Den = Timestamp.valueOf(res.getString("DEN"));
					LocalDateTime lcdt_Den = ts_Den.toLocalDateTime();

					String[] data = { String.valueOf(i++), res.getString("PHONG"), res.getString("NHANVIEN"),
							dateTimeFormatter.format(lcdt_Tu).toString(),
							dateTimeFormatter.format(lcdt_Den).toString() };
					tableTrucPhong.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	public void draw_Phong() {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.PHONG ORDER BY MAP";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();

			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong lương!");

			} else {
				String[] columns = { "STT", "Mã Phòng", "Tên Phòng", "Vị Trí", "Mô Tả" };
				tablePhong = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {

					String[] data = { String.valueOf(i++), res.getString("MAP"), res.getString("TEN"),
							res.getString("VITRI"), res.getString("MOTA") != null ? res.getString("MOTA") : "Chưa Có" };
					tablePhong.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}