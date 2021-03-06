package com.atbm.quanlybenhvien.views.Doctor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class DoctorEditServices extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private GenericStuff genericStuff = new GenericStuff();

	private User user;
	private JTextField txtMaKB;
	private JTextField txtMoTa;
	private JTextField txtMaDV;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			DoctorEditServices dialog = new DoctorEditServices(new User(), new String(), new String(),
					new DoctorPatDetails(new User(), new String(), new String()));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DoctorEditServices(final User user, final String maKB, final String maDV, final DoctorPatDetails preFrame) {
		setBounds(100, 100, 300, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblMaKB = new JLabel("Mã Khám Bệnh:");
		lblMaKB.setHorizontalAlignment(SwingConstants.LEFT);
		lblMaKB.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblMaKB.setBounds(10, 14, 100, 14);
		contentPanel.add(lblMaKB);
		txtMaKB = new JTextField(maKB);
		txtMaKB.setEditable(false);
		txtMaKB.setColumns(10);
		txtMaKB.setBounds(120, 11, 154, 20);
		contentPanel.add(txtMaKB);

		JLabel lblMaDV = new JLabel("Mã Dịch Vụ:");
		lblMaDV.setHorizontalAlignment(SwingConstants.LEFT);
		lblMaDV.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblMaDV.setBounds(10, 45, 100, 14);
		contentPanel.add(lblMaDV);
		txtMaDV = new JTextField(maDV);
		txtMaDV.setEditable(false);
		txtMaDV.setColumns(10);
		txtMaDV.setBounds(120, 42, 154, 20);
		contentPanel.add(txtMaDV);

		JLabel label_2 = new JLabel("Mô Tả:");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Times New Roman", Font.BOLD, 12));
		label_2.setBounds(10, 76, 100, 14);
		contentPanel.add(label_2);
		txtMoTa = new JTextField();
		txtMoTa.setColumns(10);
		txtMoTa.setBounds(120, 73, 154, 20);
		contentPanel.add(txtMoTa);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnEdit = new JButton("Sửa");
				btnEdit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (txtMoTa.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Vui lòng nhập nội dung cần chỉnh sửa!");
						} else {
							Connection conn = new ConnectionControl().createConnection(user.getUserName(),
									user.getPassword());

							try {
								String sql = "UPDATE QLBV.view_dieuphoidichvu SET MOTA = '" + txtMoTa.getText()
										+ "' WHERE MAKB = '" + maKB + "' AND MADV = '" + maDV + "'";
								Statement stmt = conn.createStatement();
								stmt.executeUpdate(sql);
								conn.close();

							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Chỉnh sửa thất bại!");
								try {
									conn.close();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}

							JOptionPane.showMessageDialog(null, "Chỉnh sửa thông tin khám bệnh thành công!");
							dispose();
							preFrame.getTableDichVu().fireTableDataChanged();
							preFrame.drawTable_DichVu(maKB);
							preFrame.getTbl_DichVu().setModel(preFrame.getTableDichVu());
							genericStuff.resizeTable(preFrame.getTbl_DichVu());
							genericStuff.call_revapaint(preFrame);
						}
					}
				});
				btnEdit.setActionCommand("OK");
				buttonPane.add(btnEdit);
				getRootPane().setDefaultButton(btnEdit);
			}
			{
				JButton btnHuy = new JButton("Hủy");
				btnHuy.setActionCommand("Cancel");
				buttonPane.add(btnHuy);
			}
		}
	}
}
