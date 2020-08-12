package com.atbm.quanlybenhvien.views.DBA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DBAGrantRole extends JDialog {

	private static final long serialVersionUID = 1L;

	private JFrame prevFrame;

	public JFrame getPrevFrame() {
		return prevFrame;
	}

	public void setPrevFrame(JFrame prevFrame) {
		this.prevFrame = prevFrame;
	}

	private final JPanel contentPanel = new JPanel();

	private GenericStuff genericStuff = new GenericStuff();

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			DBAGrantRole dialog = new DBAGrantRole(new User(), new DBAWorkSpace(new User()));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DBAGrantRole(final User user, final DBAWorkSpace preFrame) {
		this.user = user;

		this.prevFrame = preFrame;

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblRoleName = new JLabel("Tên Quyền:");
		lblRoleName.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblRoleName.setBounds(10, 11, 112, 30);
		contentPanel.add(lblRoleName);
		final JComboBox<String> comboBox_RoleName = new JComboBox<String>();
		if (getAll_ObjectRole().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Quản trị chưa tạo quyền gì cả!");
		} else {
			for (String item : getAll_ObjectRole()) {
				comboBox_RoleName.addItem(item);
			}
		}
		comboBox_RoleName.setSelectedItem(null);
		comboBox_RoleName.setBounds(132, 11, 130, 30);
		contentPanel.add(comboBox_RoleName);

		JLabel lblUser = new JLabel("Tên Người Dùng:");
		lblUser.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblUser.setBounds(10, 52, 112, 30);
		contentPanel.add(lblUser);
		final JComboBox<String> comboBox_Username = new JComboBox<String>();
		if (getAll_User().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Chưa có người dùng nào trên hệ thống!");
		} else {
			for (String item : getAll_User()) {
				comboBox_Username.addItem(item);
			}
		}
		comboBox_Username.setSelectedItem(null);
		comboBox_Username.setBounds(132, 52, 130, 30);
		contentPanel.add(comboBox_Username);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (comboBox_RoleName.getSelectedItem() == null
								|| comboBox_Username.getSelectedItem() == null) {
							JOptionPane.showMessageDialog(null, "Vui lòng chọn quyền và người dùng!");
						} else {
							String grantee = comboBox_Username.getSelectedItem().toString();
							String granted_role = comboBox_RoleName.getSelectedItem().toString();

							Connection conn = new ConnectionControl().createConnection(user.getUserName(),
									user.getPassword());
							try {
								String sql = "GRANT " + granted_role + " TO " + grantee;
								PreparedStatement preparedStatement = conn.prepareStatement(sql);
								preparedStatement.executeUpdate();

								conn.close();

								JOptionPane.showMessageDialog(null,
										"Thêm quyền " + granted_role + " cho người dùng " + grantee + " thành công!");

								preFrame.getUserRoleTable().fireTableDataChanged();
								preFrame.drawuserRole_Table();
								preFrame.getTblUserRole().setModel(preFrame.getUserRoleTable());

								genericStuff.call_revapaint(preFrame);

								genericStuff.resizeTable(preFrame.getTblUserRole());

								dispose();

							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null,
										"Thêm quyền " + granted_role + " cho người dùng " + grantee + " thất bại!");
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private List<String> getAll_ObjectRole() {
		List<String> res_Roles = new ArrayList<String>();

		Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
		try {
			String sql = "SELECT * FROM USER_ROLE_PRIVS\r\n" + "WHERE GRANTED_ROLE LIKE '%ROLE_%'\r\n"
					+ "ORDER BY GRANTED_ROLE";
			PreparedStatement preparedStatement_Object_Role = conn.prepareStatement(sql);
			ResultSet res = preparedStatement_Object_Role.executeQuery();
			if (res.next() == false) {
				return null;
			} else {
				do {
					if (res.getString("GRANTED_ROLE").contains("ROLE")) {
						res_Roles.add(res.getString("GRANTED_ROLE").toString());
					}
				} while (res.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res_Roles;
	}

	private List<String> getAll_User() {
		List<String> res_Users = new ArrayList<String>();

		Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
		try {
			String sql = "SELECT * FROM ALL_USERS\r\n" + "WHERE\r\n"
					+ "USERNAME LIKE '%BS%' AND USERNAME NOT LIKE '%DBS%'\r\n" + "OR\r\n" + "USERNAME LIKE '%NV%'\r\n"
					+ "OR\r\n" + "USERNAME LIKE '%USER%'\r\n" + "ORDER BY USERNAME";
			PreparedStatement preparedStatement_User = conn.prepareStatement(sql);
			ResultSet res = preparedStatement_User.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có người dùng nào trên hệ thống!");
				return null;
			} else {
				do {
					res_Users.add(res.getString("USERNAME").toString());
				} while (res.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res_Users;
	}
}
