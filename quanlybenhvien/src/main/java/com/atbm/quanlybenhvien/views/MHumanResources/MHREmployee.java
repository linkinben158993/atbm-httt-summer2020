package com.atbm.quanlybenhvien.views.MHumanResources;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;

public class MHREmployee extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private GenericStuff genericStuff = new GenericStuff();

	private MHREmployee currFrame;

	public MHREmployee getCurrFrame() {
		return currFrame;
	}

	public void setCurrFrame(MHREmployee currFrame) {
		this.currFrame = currFrame;
	}

	private DefaultTableModel tableNhanVien;

	public DefaultTableModel getTableNhanVien() {
		return tableNhanVien;
	}

	public void setTableNhanVien(DefaultTableModel tableNhanVien) {
		this.tableNhanVien = tableNhanVien;
	}

	private DefaultTableModel tableChamCong;

	public DefaultTableModel getTableChamCong() {
		return tableChamCong;
	}

	private JTable tbl_NhanVien;

	private JTable tbl_ChamCong;

	public JTable getTbl_NhanVien() {
		return tbl_NhanVien;
	}

	public void setTbl_NhanVien(JTable tbl_NhanVien) {
		this.tbl_NhanVien = tbl_NhanVien;
	}

	public JTable getTbl_ChamCong() {
		return tbl_ChamCong;
	}

	public void setTbl_ChamCong(JTable tbl_ChamCong) {
		this.tbl_ChamCong = tbl_ChamCong;
	}

	public void setTableChamCong(DefaultTableModel tableChamCong) {
		this.tableChamCong = tableChamCong;
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
					MHREmployee frame = new MHREmployee(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MHREmployee(final User user) {
		this.user = user;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1040, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.currFrame = (MHREmployee) SwingUtilities.getWindowAncestor(contentPane);

		JLabel lblNhanVien = new JLabel("Bảng Thông Tin Nhân Viên");
		lblNhanVien.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhanVien.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNhanVien.setBounds(10, 11, 500, 39);
		contentPane.add(lblNhanVien);

		JScrollPane scrollPane_NhanVien = new JScrollPane();
		scrollPane_NhanVien.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_NhanVien.setBounds(10, 50, 500, 160);
		contentPane.add(scrollPane_NhanVien);

		draw_TableNhanVien();
		tbl_NhanVien = new JTable(tableNhanVien);
		tbl_NhanVien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tbl_NhanVien);
		scrollPane_NhanVien.setViewportView(tbl_NhanVien);

		JLabel lblChamCong = new JLabel("Bảng Chấm Công");
		lblChamCong.setHorizontalAlignment(SwingConstants.CENTER);
		lblChamCong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblChamCong.setBounds(520, 16, 500, 28);
		contentPane.add(lblChamCong);

		JScrollPane scrollPane_ChamCong = new JScrollPane();
		scrollPane_ChamCong.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_ChamCong.setBounds(520, 50, 500, 160);
		contentPane.add(scrollPane_ChamCong);

		draw_ChamCong();
		tbl_ChamCong = new JTable(tableChamCong);
		tbl_ChamCong.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tbl_ChamCong);
		scrollPane_ChamCong.setViewportView(tbl_ChamCong);

		JButton lblAddEmp = new JButton("<html><center>Thêm<br>Nhân Viên</center></html>");
		lblAddEmp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MHRAddEmps mhrAddEmps = new MHRAddEmps(user, currFrame);
				genericStuff.call_dialog(mhrAddEmps);
			}
		});
		lblAddEmp.setBounds(10, 223, 122, 33);
		contentPane.add(lblAddEmp);

		JButton btnEditEmp = new JButton("<html><center>Sửa<br>Nhân Viên</center></html>");
		btnEditEmp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbl_NhanVien.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn trực phòng cần sửa!");
				} else {

				}
			}
		});
		btnEditEmp.setBounds(142, 221, 122, 37);
		contentPane.add(btnEditEmp);

		JButton btnDeleteEmp = new JButton("<html><center>Xóa<br>Nhân Viên</center></html>");
		btnDeleteEmp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbl_NhanVien.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần xóa!");
				} else {
					Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
					PreparedStatement stmt = null;
					try {
						stmt = conn.prepareStatement("DELETE FROM QLBV.NHANVIEN WHERE MANV = ?");
						stmt.setString(1, tbl_NhanVien.getValueAt(tbl_NhanVien.getSelectedRow(), 1).toString());
						stmt.executeUpdate();
						conn.close();

						JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công!");

					} catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Xóa nhân viên thất bại!");
						try {
							conn.close();
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}

					dispose();
					getTableNhanVien().fireTableDataChanged();
					draw_TableNhanVien();
					getTbl_NhanVien().setModel(getTableNhanVien());
					genericStuff.resizeTable(getTbl_NhanVien());
					genericStuff.call_revapaint(getTbl_NhanVien());
				}
			}
		});
		btnDeleteEmp.setBounds(274, 221, 122, 37);
		contentPane.add(btnDeleteEmp);

		JButton btnAddDays = new JButton("<html><center>Thêm<br>Chấm Công</center></html>");
		btnAddDays.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MHRAddDay mhrAddDay = new MHRAddDay(user, currFrame);
				genericStuff.call_dialog(mhrAddDay);
			}
		});
		btnAddDays.setBounds(520, 221, 124, 35);
		contentPane.add(btnAddDays);

		JButton btnEditDay = new JButton("<html><center>Sửa<br>Chấm Công</center></html>");
		btnEditDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbl_ChamCong.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn chấm công để sửa!");
				} else {
					MHREditDay mhrEditDay = new MHREditDay(user,
							tbl_ChamCong.getValueAt(tbl_ChamCong.getSelectedRow(), 1).toString(),
							tbl_ChamCong.getValueAt(tbl_ChamCong.getSelectedRow(), 2).toString(), currFrame);
					genericStuff.call_dialog(mhrEditDay);
				}
			}
		});
		btnEditDay.setBounds(654, 221, 124, 35);
		contentPane.add(btnEditDay);

		JButton btnDeleteDay = new JButton("<html><center>Xóa<br>Chấm Công</center></html>");
		btnDeleteDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbl_ChamCong.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn chấm công để xóa!");
				} else {
					Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
					PreparedStatement stmt = null;
					try {
						stmt = conn.prepareStatement("DELETE FROM QLBV.CHAMCONG WHERE MANV = ? AND THOIGIAN = ?");
						stmt.setString(1, tbl_ChamCong.getValueAt(tbl_ChamCong.getSelectedRow(), 1).toString());
						Date deleteDay = null;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try {
							deleteDay = sdf.parse(tbl_ChamCong.getValueAt(tbl_ChamCong.getSelectedRow(), 2).toString());
						} catch (ParseException e3) {
							e3.printStackTrace();
						}
						stmt.setTimestamp(2, new Timestamp(deleteDay.getTime()));
						stmt.executeUpdate();
						conn.close();

						JOptionPane.showMessageDialog(null, "Xóa chấm công thành công!");

					} catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Xóa chấm công thất bại!");
						try {
							conn.close();
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}

					getTableChamCong().fireTableDataChanged();
					draw_ChamCong();
					getTbl_ChamCong().setModel(getTableChamCong());
					genericStuff.resizeTable(getTbl_ChamCong());
					genericStuff.call_revapaint(getTbl_ChamCong());

				}
			}
		});
		btnDeleteDay.setBounds(788, 221, 124, 35);
		contentPane.add(btnDeleteDay);

		JPanel panelBack = new JPanel();
		panelBack.setLayout(null);
		panelBack.setBackground(Color.LIGHT_GRAY);
		panelBack.setBounds(920, 221, 100, 110);
		contentPane.add(panelBack);
		JLabel lblBack = new JLabel("Quay Lại", SwingConstants.CENTER);
		lblBack.setForeground(Color.BLACK);
		lblBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblBack.setBounds(0, 86, 100, 14);
		JLabel lblIconBack = new JLabel();
		lblIconBack.setBounds(10, 11, 80, 80);
		panelBack.add(lblIconBack);
		ImageIcon imageIcon_Back = new ImageIcon(MHREmployee.class.getResource("/images/Back.png"));
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
		label_13.setBounds(182, 296, 730, 14);
		contentPane.add(label_13);
	}

	@SuppressWarnings("serial")
	public void draw_TableNhanVien() {
		try {

			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
			String sql = "SELECT * FROM QLBV.NHANVIEN ORDER BY MANV";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();

			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong nhân viên!");

			} else {
				String[] columns = { "STT", "MANV", "Họ Tên", "Phòng", "Chức Vụ" };
				tableNhanVien = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {

					String[] data = { String.valueOf(i++), res.getString("MANV"), res.getString("HOTEN"),
							res.getString("PHONG"), res.getString("CHUCVU") };
					tableNhanVien.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	public void draw_ChamCong() {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.CHAMCONG ORDER BY THOIGIAN";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();

			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong chấm công!");

			} else {

				String[] columns = { "STT", "Mã MV", "Thời Gian" };

				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

				tableChamCong = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					Timestamp ts = Timestamp.valueOf(res.getString("THOIGIAN"));
					LocalDateTime lcdt = ts.toLocalDateTime();

					String[] data = { String.valueOf(i++), res.getString("MANV"),
							dateTimeFormatter.format(lcdt).toString() };
					tableChamCong.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}