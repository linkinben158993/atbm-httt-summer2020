package com.atbm.quanlybenhvien.views.DBA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class DBACRUDRole extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private User user;
	private JTextField txtRoleName;
	private JTextField txtRoleAd;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			DBACRUDRole dialog = new DBACRUDRole(new User());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DBACRUDRole(final User user) {
		this.user = user;

		setTitle("Tạo Mới Quyền");
		setBounds(100, 100, 360, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblRoleName = new JLabel("Tên Quyền:");
			lblRoleName.setFont(new Font("Times New Roman", Font.BOLD, 12));
			lblRoleName.setBounds(10, 11, 80, 30);
			contentPanel.add(lblRoleName);
		}

		txtRoleName = new JTextField();
		txtRoleName.setBounds(164, 16, 160, 20);
		contentPanel.add(txtRoleName);
		txtRoleName.setColumns(10);
		{
			JLabel lblRoleSchema = new JLabel("Người Quản Trị Quyền:");
			lblRoleSchema.setFont(new Font("Times New Roman", Font.BOLD, 12));
			lblRoleSchema.setBounds(10, 52, 150, 30);
			contentPanel.add(lblRoleSchema);
		}

		txtRoleAd = new JTextField();
		txtRoleAd.setColumns(10);
		txtRoleAd.setBounds(164, 57, 160, 20);
		contentPanel.add(txtRoleAd);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Thêm Quyền");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						if (txtRoleName.getText().equals("") || txtRoleAd.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Vui lòng điền đủ các trường!");
						} else {
							Connection conn = new ConnectionControl().createConnection(user.getUserName(),
									user.getPassword());
							String new_Role = "C##" + txtRoleName.getText();
							String sql_create = "CREATE ROLE " + new_Role;

							String sql_grant_to_role = "GRANT CREATE SESSION TO " + new_Role;
							// Nên thay chỗ này bằng txtbox còn lại nhưng mặc định là txtbox đấy sẽ luôn
							// phải điền lúc demo là QLBV @@
							String dba = txtRoleAd.getText();
							String sql_grant_to_user = "GRANT " + new_Role + " TO " + dba + " WITH ADMIN OPTION";

							try {

								Statement statements = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
										ResultSet.CONCUR_UPDATABLE);

								statements.addBatch(sql_create);
								statements.addBatch(sql_grant_to_role);
								statements.addBatch(sql_grant_to_user);

								statements.executeBatch();

								conn.close();

								JOptionPane.showMessageDialog(null, "Tạo mới quyền " + new_Role + " thành công!");
							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Tạo quyền mới thất bại!");
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
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
