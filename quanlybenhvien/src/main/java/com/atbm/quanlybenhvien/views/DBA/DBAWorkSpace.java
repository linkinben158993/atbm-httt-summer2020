package com.atbm.quanlybenhvien.views.DBA;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;
import com.atbm.quanlybenhvien.views.Login;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DBAWorkSpace extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User user;

	private DefaultTableModel userTable;
	private DefaultTableModel userRoleColumn;
	private DefaultTableModel userRoleTable;

	public DefaultTableModel getUserTable() {
		return userTable;
	}

	public void setUserTable(DefaultTableModel userTable) {
		this.userTable = userTable;
	}

	public DefaultTableModel getUserRoleColumn() {
		return userRoleColumn;
	}

	public void setUserRoleColumn(DefaultTableModel userRoleColumn) {
		this.userRoleColumn = userRoleColumn;
	}

	public DefaultTableModel getUserRoleTable() {
		return userRoleTable;
	}

	public void setUserRoleTable(DefaultTableModel userRoleTable) {
		this.userRoleTable = userRoleTable;
	}

	private JTable tblRole_Table;
	private JTable tblRole_Column;
	private JTable tblUserRole;

	public JTable getTblRole() {
		return tblRole_Table;
	}

	public void setTblRole(JTable tblRole) {
		this.tblRole_Table = tblRole;
	}

	public JTable getTblRole_Column() {
		return tblRole_Column;
	}

	public void setTblRole_Column(JTable tblRole_Column) {
		this.tblRole_Column = tblRole_Column;
	}

	public JTable getTblUserRole() {
		return tblUserRole;
	}

	public void setTblUserRole(JTable tblUserRole) {
		this.tblUserRole = tblUserRole;
	}

	private GenericStuff genericStuff = new GenericStuff();

	private DBAWorkSpace currFrame;

	public DBAWorkSpace getCurrFrame() {
		return currFrame;
	}

	public void setCurrFrame(DBAWorkSpace currFrame) {
		this.currFrame = currFrame;
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
					DBAWorkSpace frame = new DBAWorkSpace(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DBAWorkSpace(final User user) {
		this.user = user;

		setTitle("Trang Quản Trị Cơ Sở Dữ Liệu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.currFrame = (DBAWorkSpace) SwingUtilities.getWindowAncestor(contentPane);

		JButton btnAddRole = new JButton("Cấp Quyền");
		btnAddRole.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DBAAddPrivileges dbaAddPrivileges = new DBAAddPrivileges(user, getCurrFrame());
				genericStuff.call_dialog(dbaAddPrivileges);
			}
		});
		btnAddRole.setBounds(1055, 11, 119, 23);
		contentPane.add(btnAddRole);

		JButton btnEditRole = new JButton("Thu Hồi Quyền");
		btnEditRole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] role_col = tblRole_Column.getSelectedRows();
				int[] role_tbl = tblRole_Table.getSelectedRows();
				if (role_col.length == 0 && role_tbl.length == 0) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn quyền trên bảng hoặc trên cột để thu hồi!");
				} else if (role_col.length > 0) {
					String selected_role = tblRole_Column.getValueAt(role_col[0], 1).toString();
					String selected_priv = tblRole_Column.getValueAt(role_col[0], 2).toString();
					String selected_table = tblRole_Column.getValueAt(role_col[0], 3).toString();
					// String selected_col = tblRole_Column.getValueAt(role_col[0], 4).toString();

					Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());

					try {

						String sql = "REVOKE " + selected_priv + " ON " + selected_table + " FROM " + selected_role;
						PreparedStatement statement_role = conn.prepareStatement(sql);
						statement_role.executeUpdate();
						conn.close();

						System.out.println(sql);

						JOptionPane.showMessageDialog(null,
								"Thu hồi quyền " + selected_priv + " cho đối tượng " + selected_role + " thành công!");

						currFrame.getUserTable().fireTableDataChanged();
						currFrame.drawRole_Table();
						currFrame.getTblRole().setModel(currFrame.getUserTable());

						currFrame.getUserRoleColumn().fireTableDataChanged();
						currFrame.drawRole_Column();
						currFrame.getTblRole_Column().setModel(currFrame.getUserRoleColumn());

						genericStuff.call_revapaint(currFrame);

						genericStuff.resizeTable(currFrame.getTblRole());
						genericStuff.resizeTable(currFrame.getTblRole_Column());

					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Thu hồi quyền " + selected_priv + " cho đối tượng " + selected_role + " thất bại!");
					}
				} else if (role_tbl.length > 0) {
					String selected_role = tblRole_Table.getValueAt(role_tbl[0], 1).toString();
					String selected_priv = tblRole_Table.getValueAt(role_tbl[0], 2).toString();
					String selected_table = tblRole_Table.getValueAt(role_tbl[0], 3).toString();
					// String selected_col = tblRole_Column.getValueAt(role_col[0], 4).toString();

					Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());

					try {

						String sql = "REVOKE " + selected_priv + " ON " + selected_table + " FROM " + selected_role;
						PreparedStatement statement_role = conn.prepareStatement(sql);
						statement_role.executeUpdate();
						conn.close();

						System.out.println(sql);

						JOptionPane.showMessageDialog(null,
								"Thu hồi quyền " + selected_priv + " cho đối tượng " + selected_role + " thành công!");

						currFrame.getUserTable().fireTableDataChanged();
						currFrame.drawRole_Table();
						currFrame.getTblRole().setModel(currFrame.getUserTable());

						currFrame.getUserRoleColumn().fireTableDataChanged();
						currFrame.drawRole_Column();
						currFrame.getTblRole_Column().setModel(currFrame.getUserRoleColumn());

						genericStuff.call_revapaint(currFrame);

						genericStuff.resizeTable(currFrame.getTblRole());
						genericStuff.resizeTable(currFrame.getTblRole_Column());

					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Thu hồi quyền " + selected_priv + " cho đối tượng " + selected_role + " thất bại!");
					}
				}
			}
		});
		btnEditRole.setBounds(1055, 45, 119, 23);
		contentPane.add(btnEditRole);

		JScrollPane scrollPane_RoleTable = new JScrollPane();
		scrollPane_RoleTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_RoleTable.setBounds(10, 11, 472, 180);
		contentPane.add(scrollPane_RoleTable);
		drawRole_Table();
		tblRole_Table = new JTable(userTable);
		tblRole_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblRole_Table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				tblRole_Column.clearSelection();
				tblRole_Column.getColumnModel().getSelectionModel().clearSelection();
				System.out.println("Bỏ chọn Role Column");
			}
		});
		genericStuff.resizeTable(tblRole_Table);
		scrollPane_RoleTable.setViewportView(tblRole_Table);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(492, 11, 553, 180);
		contentPane.add(scrollPane);
		drawRole_Column();
		tblRole_Column = new JTable(userRoleColumn);
		tblRole_Column.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblRole_Column.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				tblRole_Table.clearSelection();
				tblRole_Table.getColumnModel().getSelectionModel().clearSelection();
				System.out.println("Bỏ chọn Role Table");
			}
		});
		genericStuff.resizeTable(tblRole_Column);
		scrollPane.setViewportView(tblRole_Column);

		JScrollPane scrollPane_UserWithRole = new JScrollPane();
		scrollPane_UserWithRole.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_UserWithRole.setBounds(10, 240, 1035, 180);
		contentPane.add(scrollPane_UserWithRole);
		drawuserRole_Table();
		tblUserRole = new JTable(userRoleTable);
		tblUserRole.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tblUserRole);
		scrollPane_UserWithRole.setViewportView(tblUserRole);

		JPanel panelBack = new JPanel();
		panelBack.setLayout(null);
		panelBack.setBackground(Color.LIGHT_GRAY);
		panelBack.setBounds(1074, 351, 100, 110);
		contentPane.add(panelBack);
		JLabel lblBack = new JLabel("Quay Lại", SwingConstants.CENTER);
		lblBack.setForeground(Color.BLACK);
		lblBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblBack.setBounds(0, 86, 100, 14);
		panelBack.add(lblBack);
		JLabel lblIconBack = new JLabel();
		ImageIcon imageIcon_Back = new ImageIcon(Login.class.getResource("/images/Back.png"));
		Image image_Back = imageIcon_Back.getImage();
		Image newImage_Back = image_Back.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconBack.setIcon(new ImageIcon(newImage_Back));
		lblIconBack.setBounds(10, 11, 80, 80);
		lblIconBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				DBADashBoard dbaDashBoard = new DBADashBoard(user);
				genericStuff.call_frame(dbaDashBoard);
			}
		});
		genericStuff.hover(lblIconBack, lblBack, panelBack, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		panelBack.add(lblIconBack);

		JButton btnGrantRole = new JButton("Cấp Quyền");
		btnGrantRole.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DBAGrantRole dbaGrantRole = new DBAGrantRole(user, currFrame);
				genericStuff.call_dialog(dbaGrantRole);
			}
		});
		btnGrantRole.setBounds(1055, 240, 119, 23);
		contentPane.add(btnGrantRole);

		JButton btnRevokeRole = new JButton("Thu Hồi Quyền");
		btnRevokeRole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tblUserRole.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn quyền của người dùng để thu hồi!");
				} else {
					String selected_grantee = tblUserRole.getValueAt(tblUserRole.getSelectedRow(), 1).toString();
					String selected_role = tblUserRole.getValueAt(tblUserRole.getSelectedRow(), 2).toString();

					Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());

					try {

						String sql_revoke = "REVOKE " + selected_role + " FROM " + selected_grantee;
						PreparedStatement preparedStatement_revoke = conn.prepareStatement(sql_revoke);
						preparedStatement_revoke.executeUpdate();
						conn.close();

						JOptionPane.showMessageDialog(null, "Thu hồi quyền " + selected_role + " từ người dùng "
								+ selected_grantee + " thành công!");

						currFrame.getUserRoleTable().fireTableDataChanged();
						currFrame.drawuserRole_Table();
						currFrame.getTblUserRole().setModel(currFrame.getUserRoleTable());

						genericStuff.call_revapaint(currFrame);

						genericStuff.resizeTable(currFrame.getTblUserRole());

					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Thu hồi quyền " + selected_role + " từ người dùng " + selected_grantee + " thất bại!");
					}
				}
			}
		});
		btnRevokeRole.setBounds(1055, 274, 119, 23);
		contentPane.add(btnRevokeRole);

		JLabel lblTitleRole_Table = new JLabel("CÁC QUYỀN TRÊN BẢNG HIỆN TẠI TRÊN HỆ THỐNG");
		lblTitleRole_Table.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblTitleRole_Table.setBounds(10, 202, 400, 14);
		contentPane.add(lblTitleRole_Table);

		JLabel lblTitleRole_Column = new JLabel("CÁC QUYỀN TRÊN CỘT HIỆN TẠI TRÊN HỆ THỐNG");
		lblTitleRole_Column.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblTitleRole_Column.setBounds(492, 202, 328, 14);
		contentPane.add(lblTitleRole_Column);

		JLabel lblUserWithRole = new JLabel("CÁC NGƯỜI DÙNG VỚI QUYỀN TRÊN BẢNG HIỆN TẠI TRÊN HỆ THỐNG");
		lblUserWithRole.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblUserWithRole.setBounds(10, 431, 565, 14);
		contentPane.add(lblUserWithRole);
	}

	@SuppressWarnings("serial")
	public void drawuserRole_Table() {
		try {
			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
			// Lấy tất cả các quyền mà các tài khoản được quyền thực hiện trên các đối tượng
			// bảng
			String sql = "SELECT DRV.GRANTEE, DRV.GRANTED_ROLE, UTP.PRIVILEGE, UTP.TYPE, UTP.TABLE_NAME, UTP.GRANTABLE "
					+ "FROM DBA_ROLE_PRIVS DRV \r\n" + "JOIN USER_TAB_PRIVS UTP "
					+ "ON DRV.GRANTED_ROLE = UTP.GRANTEE\r\n" + "WHERE UTP.OWNER = '" + user.getUserName()
					+ "' AND DRV.GRANTEE != '" + user.getUserName() + "' ORDER BY DRV.GRANTEE";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet res = statement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Không có tài khoản hoặc chưa có quyền được cấp cho tài khoản!");
			} else {
				String[] columns = { "STT", "Người Dùng", "Tên Quyền", "Quyền", "Đối Tượng", "Bảng", "Cấp quyền" };
				userRoleTable = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					String[] data = { String.valueOf(i++), res.getString("GRANTEE"), res.getString("GRANTED_ROLE"),
							res.getString("PRIVILEGE"), res.getString("TYPE"), res.getString("TABLE_NAME"),
							res.getString("GRANTABLE") };
					userRoleTable.addRow(data);
				} while (res.next());
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	public void drawRole_Table() {
		try {
			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
			// Lấy tất cả các quyền mà các tài khoản được quyền thực hiện trên các đối tượng
			// bảng
			String sql = "SELECT * FROM USER_TAB_PRIVS UTP WHERE UTP.OWNER = '" + user.getUserName()
					+ "' ORDER BY UTP.GRANTEE";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet res = statement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có quyền nào được cấp trên bảng!");
			} else {
				String[] columns = { "STT", "Tên Quyền", "Quyền", "Bảng" };
				userTable = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					String[] data = { String.valueOf(i++), res.getString("GRANTEE"), res.getString("PRIVILEGE"),
							res.getString("TABLE_NAME") };
					userTable.addRow(data);

				} while (res.next());
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	public void drawRole_Column() {
		try {
			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
			// Lấy tất cả các quyền mà các tài khoản được quyền thực hiện trên các đối tượng
			// bảng
			String sql = "SELECT * FROM USER_COL_PRIVS UCP WHERE UCP.OWNER = '" + user.getUserName()
					+ "' ORDER BY UCP.GRANTEE";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet res = statement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có quyền nào được cấp trên cột!");
			} else {
				String[] columns = { "STT", "Tên Quyền", "Quyền", "Bảng", "Cột" };
				userRoleColumn = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					String[] data = { String.valueOf(i++), res.getString("GRANTEE"), res.getString("PRIVILEGE"),
							res.getString("TABLE_NAME"), res.getString("COLUMN_NAME") };
					userRoleColumn.addRow(data);

				} while (res.next());
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
