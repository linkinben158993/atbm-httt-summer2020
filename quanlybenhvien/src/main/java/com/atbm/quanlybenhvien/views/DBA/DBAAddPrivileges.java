package com.atbm.quanlybenhvien.views.DBA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class DBAAddPrivileges extends JDialog {

	private ValidateInBackGround validateInBackGround;

	private static final long serialVersionUID = 1L;
	private static JPanel contentPanel = new JPanel();
	private User user;
	private static GenericStuff genericStuff = new GenericStuff();

	private static JRadioButton rdbtnSelect;
	private static JRadioButton rdbtnInsert;
	private static JRadioButton rdbtnUpdate;
	private static JRadioButton rdbtnDelete;
	private static ButtonGroup buttonGroup = new ButtonGroup();

	private static DefaultComboBoxModel<String> options = new DefaultComboBoxModel<String>(
			new String[] { "Bảng", "Cột" });
	private static JComboBox<String> comboBox_Options;

	private static JLabel lblColumn = new JLabel("Cột:");
	private DefaultComboBoxModel<String> columns_Model;
	private static JComboBox<String> comboBox_Column = new JComboBox<String>();

	private JFrame prevFrame;

	public JFrame getPrevFrame() {
		return prevFrame;
	}

	public void setPrevFrame(JFrame prevFrame) {
		this.prevFrame = prevFrame;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			DBAAddPrivileges dialog = new DBAAddPrivileges(new User(), new DBAWorkSpace(new User()));
			dialog.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
			dialog.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DBAAddPrivileges(final User user, final DBAWorkSpace prevFrame) {

		this.user = user;
		this.prevFrame = prevFrame;

		setTitle("Thêm Mới Quyền");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 480, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblTenQuyen = new JLabel("Tên Quyền:");
			lblTenQuyen.setFont(new Font("Times New Roman", Font.BOLD, 12));
			lblTenQuyen.setBounds(10, 11, 80, 30);
			contentPanel.add(lblTenQuyen);
		}

		final JComboBox<String> comboBox_Roles = new JComboBox<String>();
		comboBox_Roles.setSelectedItem(null);
		if (getAllRole().isEmpty()) {
			System.out.println("Chưa có quyền!");
		} else {
			for (String item : getAllRole()) {
				comboBox_Roles.addItem(item);
			}
		}
		comboBox_Roles.setBounds(132, 11, 130, 30);
		contentPanel.add(comboBox_Roles);

		JLabel lblPrivs = new JLabel("Cho Phép:");
		lblPrivs.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblPrivs.setBounds(10, 52, 80, 30);
		contentPanel.add(lblPrivs);

		rdbtnSelect = new JRadioButton("SELECT");
		rdbtnSelect.setBounds(132, 52, 80, 30);
		contentPanel.add(rdbtnSelect);

		rdbtnInsert = new JRadioButton("INSERT");
		rdbtnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateInBackGround = new ValidateInBackGround();
				validateInBackGround.setDaemon(true);
				validateInBackGround.start();
			}
		});
		rdbtnInsert.setBounds(214, 52, 80, 30);
		contentPanel.add(rdbtnInsert);

		rdbtnUpdate = new JRadioButton("UPDATE");
		rdbtnUpdate.setBounds(296, 52, 80, 30);
		contentPanel.add(rdbtnUpdate);

		rdbtnDelete = new JRadioButton("DELETE");
		rdbtnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateInBackGround = new ValidateInBackGround();
				validateInBackGround.setDaemon(true);
				validateInBackGround.start();
			}
		});
		rdbtnDelete.setBounds(378, 52, 80, 30);
		contentPanel.add(rdbtnDelete);

		buttonGroup.add(rdbtnSelect);
		buttonGroup.add(rdbtnInsert);
		buttonGroup.add(rdbtnUpdate);
		buttonGroup.add(rdbtnDelete);

		JLabel lblDoiTuong = new JLabel("Đối Tượng:");
		lblDoiTuong.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblDoiTuong.setBounds(10, 134, 110, 30);
		contentPanel.add(lblDoiTuong);

		comboBox_Options = new JComboBox<String>(options);
		comboBox_Options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateInBackGround = new ValidateInBackGround();
				validateInBackGround.setDaemon(true);
				validateInBackGround.start();
			}
		});
		comboBox_Options.setSelectedItem(null);
		comboBox_Options.setBounds(132, 134, 130, 30);
		contentPanel.add(comboBox_Options);

		JLabel lblBang = new JLabel("Bảng:");
		lblBang.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblBang.setBounds(10, 175, 110, 30);
		contentPanel.add(lblBang);
		final DefaultComboBoxModel<String> tables_Model = new DefaultComboBoxModel<String>();
		try {
			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
			// Lấy tất cả các bảng thuộc quyền sở hữu của dba
			String sql = "SELECT * FROM DBA_TABLES WHERE OWNER = '" + user.getUserName() + "'";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet res = statement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có bảng nào thuộc quản lý của người dùng này!");
			} else {
				do {
					tables_Model.addElement(res.getString("TABLE_NAME").toString());
				} while (res.next());
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		final JComboBox<String> comboBox_Table = new JComboBox<String>(tables_Model);
		comboBox_Table.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (options.getSelectedItem() != null) {
					validateInBackGround = new ValidateInBackGround();
					validateInBackGround.setDaemon(true);
					validateInBackGround.start();
					if (!options.getSelectedItem().toString().equals("Bảng")) {
						if (columns_Model != null) {
							columns_Model.removeAllElements();
							for (String item : getListForCombobox("USER_TAB_COLUMNS",
									comboBox_Table.getSelectedItem().toString(), "COLUMN_NAME")) {
								columns_Model.addElement(item);
							}
						} else {
							columns_Model = new DefaultComboBoxModel<String>();
							for (String item : getListForCombobox("USER_TAB_COLUMNS",
									comboBox_Table.getSelectedItem().toString(), "COLUMN_NAME")) {
								columns_Model.addElement(item);
							}
						}

						lblColumn.setFont(new Font("Times New Roman", Font.BOLD, 12));
						lblColumn.setVisible(true);
						lblColumn.setBounds(10, 216, 110, 30);
						comboBox_Column.setModel(columns_Model);
						comboBox_Column.setVisible(true);
						comboBox_Column.setBounds(132, 216, 130, 30);
						contentPanel.add(lblColumn);
						contentPanel.add(comboBox_Column);

						// Mỗi lần chọn bảng mới thì render lại component của bảng.
						genericStuff.call_revapaint(contentPanel);
					} else {
						remove_DynamicComboBox();
					}
				}
			}
		});
		comboBox_Table.setBounds(132, 175, 130, 30);
		contentPanel.add(comboBox_Table);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 109, 444, 2);
		contentPanel.add(separator);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Thêm Quyền");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						// Thêm mới quyền trên đối tượng bảng
						if (comboBox_Options.getSelectedItem().toString().equals("Bảng")) {
							String selected_priv = "";
							String selected_role = "";
							String selected_table = "";

							int i = 0;

							for (Enumeration<AbstractButton> items = buttonGroup.getElements(); items
									.hasMoreElements();) {
								++i;
								AbstractButton item = items.nextElement();
								if (item.isSelected()) {
									System.out.println("Quyền được gán!");
									selected_priv = item.getText();
								}
							}
							System.out.println(i);

							if (comboBox_Roles.getSelectedItem().toString() == null) {
								JOptionPane.showMessageDialog(null, "Vui không bỏ trống tên quyền!");
							} else if (selected_priv.equals("")) {
								JOptionPane.showMessageDialog(null, "Vui lòng chọn quyền trước khi thêm!");
							} else if (comboBox_Table.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null, "Vui lòng chọn bảng trước khi thêm!");
							} else {
								try {
									selected_role = comboBox_Roles.getSelectedItem().toString();
									selected_table = comboBox_Table.getSelectedItem().toString();

									Connection conn = new ConnectionControl().createConnection(user.getUserName(),
											user.getPassword());

									// Cấp quyền cho role
									String sql = "GRANT " + selected_priv + " ON " + selected_table + " TO "
											+ selected_role;
									PreparedStatement statement_role = conn.prepareStatement(sql);
									statement_role.executeUpdate();

									conn.close();

									JOptionPane.showMessageDialog(null, "Thêm quyền " + selected_priv
											+ " cho đối tượng " + selected_role + " thành công!");

									dispose();

									buttonGroup.clearSelection();

									prevFrame.getUserTable().fireTableDataChanged();
									prevFrame.drawRole_Table();
									prevFrame.getTblRole().setModel(prevFrame.getUserRoleTable());

									genericStuff.resizeTable(prevFrame.getTblRole());

									genericStuff.call_revapaint(prevFrame);

								} catch (Exception e2) {
									e2.printStackTrace();
									JOptionPane.showMessageDialog(null,
											"Có lỗi xảy ra, thêm quyền cho đối tượng " + selected_role + " thất bại!");
									dispose();
									buttonGroup.clearSelection();
								}
							}
						}

						// Thêm mới đối quyền trên đơn vị cột
						else if (comboBox_Options.getSelectedItem().toString().equals("Cột")) {
							String selected_priv = "";
							String selected_role = "";
							String selected_table = "";
							String selected_column = "";
							for (Enumeration<AbstractButton> items = buttonGroup.getElements(); items
									.hasMoreElements();) {
								AbstractButton item = items.nextElement();
								if (item.isSelected()) {
									selected_priv = item.getText();
								}
							}

							if (comboBox_Roles.getSelectedItem().toString() == null) {
								JOptionPane.showMessageDialog(null, "Vui không bỏ trống tên quyền!");
							} else if (selected_priv.equals("")) {
								JOptionPane.showMessageDialog(null, "Vui lòng chọn quyền trước khi thêm!");
							} else if (comboBox_Table.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null, "Vui lòng chọn bảng trước khi thêm!");
							} else if (comboBox_Column.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null, "Vui lòng chọn cột trước khi thêm!");
							} else {
								try {
									selected_role = comboBox_Roles.getSelectedItem().toString();
									selected_table = comboBox_Table.getSelectedItem().toString();
									selected_column = comboBox_Column.getSelectedItem().toString();

									Connection conn = new ConnectionControl().createConnection(user.getUserName(),
											user.getPassword());
									String sql = "GRANT " + selected_priv + "(" + selected_column + ")" + " ON "
											+ selected_table + " TO " + selected_role;
									PreparedStatement statement_role = conn.prepareStatement(sql);
									statement_role.executeUpdate();

									conn.close();

									JOptionPane.showMessageDialog(null, "Thêm quyền " + selected_priv
											+ " cho đối tượng " + selected_role + " thành công!");

									dispose();

									prevFrame.getUserRoleColumn().fireTableDataChanged();
									prevFrame.drawRole_Column();
									prevFrame.getTblRole_Column().setModel(prevFrame.getUserRoleColumn());

									genericStuff.call_revapaint(prevFrame);

									genericStuff.resizeTable(prevFrame.getTblRole_Column());

									buttonGroup.clearSelection();

								} catch (Exception e2) {
									e2.printStackTrace();
									JOptionPane.showMessageDialog(null,
											"Có lỗi xảy ra, thêm quyền cho đối tượng " + selected_role + " thất bại!");
									dispose();
									buttonGroup.clearSelection();
								}
							}
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Hủy");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
						buttonGroup.clearSelection();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public List<String> getAllRole() {
		List<String> resString = new ArrayList<String>();
		try {
			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());

			// Lấy tất cả các bảng thuộc quyền sở hữu của dba
			String sql = "SELECT * FROM USER_ROLE_PRIVS";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet res = statement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Không có dòng nào trong bảng!");
				return null;
			} else {
				do {
					if (res.getString("GRANTED_ROLE").contains("ROLE")) {
						resString.add(res.getString("GRANTED_ROLE").toString());
					}
				} while (res.next());
			}

			// Close connection
			conn.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return resString;
	}

	// Hàm lấy danh sách bảng cho combobox để chọn
	public List<String> getListForCombobox(String tableNameFrom, String tableNameWhere, String neededColumn) {
		List<String> resString = new ArrayList<String>();
		try {
			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());

			// Lấy tất cả các bảng thuộc quyền sở hữu của dba
			String sql = "SELECT * FROM " + tableNameFrom + " WHERE TABLE_NAME = '" + tableNameWhere + "'";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet res = statement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Không có dòng nào trong bảng!");
				return null;
			} else {
				do {
					System.out.println(res.getString(neededColumn).toString());
					resString.add(res.getString(neededColumn).toString());
				} while (res.next());
			}

			// Close connection
			conn.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return resString;
	}

	// Nếu chọn phân quyền trên bảng thì xóa hết option của phân quyền trên cột cho
	// an toàn
	private static void remove_DynamicComboBox() {
		if (comboBox_Column != null || lblColumn != null) {
			System.out.println("Xóa mục cột!");
			lblColumn.setVisible(false);
			comboBox_Column.setVisible(false);
			comboBox_Column.removeAllItems();
			genericStuff.call_revapaint(contentPanel.getRootPane());
			if (comboBox_Column.isVisible()) {
				System.out.println("Chưa xóa được thành phần!");
			} else {
				System.out.println("Xóa được thành phần!");
			}
		} else {
			System.out.println("Không có gì để xóa!");
		}
	}

	private static class ValidateInBackGround extends Thread {
		@Override
		public void run() {
			System.out.println("Thread được chạy!");
			if (rdbtnInsert != null || rdbtnDelete != null) {
				if (rdbtnInsert.isSelected() || rdbtnDelete.isSelected()) {
					if (options != null) {
						if (options.getSelectedItem() == null) {
							// Chưa cần làm gì
						} else {
							if (options.getSelectedItem().toString().equals("Cột")) {
								System.out.println("Không được phân quyền tới cột cho Insert và Delete");
								JOptionPane.showMessageDialog(null,
										"Không được phân quyền tới cột cho Insert và Delete");
								options.setSelectedItem(null);
								rdbtnInsert.setSelected(false);
								rdbtnDelete.setSelected(false);
							} else {
								remove_DynamicComboBox();
							}
						}
					}
				} else {
					System.out.println("Thỏa");
					System.out.println(options.getSelectedItem().toString());
				}
			}
			if (options.getSelectedItem() != null) {
				if (options.getSelectedItem().toString().equals("Bảng")) {
					remove_DynamicComboBox();
				}
			}
		}
	}
}
