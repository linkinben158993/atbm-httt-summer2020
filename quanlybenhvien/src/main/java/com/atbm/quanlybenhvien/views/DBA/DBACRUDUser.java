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

public class DBACRUDUser extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtUsername;
	private JTextField txtOwner;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			DBACRUDUser dialog = new DBACRUDUser(new User());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DBACRUDUser(final User user) {
		this.user = user;

		setBounds(100, 100, 360, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblTnNgiDng = new JLabel("Tên Người Dùng:");
		lblTnNgiDng.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblTnNgiDng.setBounds(10, 11, 150, 30);
		contentPanel.add(lblTnNgiDng);

		JLabel label_1 = new JLabel("Người Quản Trị Quyền:");
		label_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		label_1.setBounds(10, 52, 150, 30);
		contentPanel.add(label_1);

		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(164, 16, 160, 20);
		contentPanel.add(txtUsername);

		txtOwner = new JTextField();
		txtOwner.setColumns(10);
		txtOwner.setBounds(164, 57, 160, 20);
		contentPanel.add(txtOwner);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						Connection conn = new ConnectionControl().createConnection(user.getUserName(),
								user.getPassword());
						String default_password = "1234";
						String uName_Final = "C##" + txtUsername.getText().trim();
						String sql_create = "CREATE USER " + uName_Final + " IDENTIFIED BY " + default_password;
						String sql_grant_basic = "GRANT CREATE SESSION TO " + uName_Final;
						String sql_grant_basic_1 = "GRANT CONNECT TO " + uName_Final;

						System.out.println(sql_create);

						try {

							Statement statements = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);

							statements.addBatch(sql_create);
							statements.addBatch(sql_grant_basic);
							statements.addBatch(sql_grant_basic_1);

							statements.executeBatch();

							conn.close();

							JOptionPane.showMessageDialog(null, "Tạo mới quyền " + uName_Final + " thành công!");
							JOptionPane.showMessageDialog(null,
									"Mật khẩu mặc định " + uName_Final + ": " + default_password);
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Tạo người dùng mới thất bại!");
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
}
