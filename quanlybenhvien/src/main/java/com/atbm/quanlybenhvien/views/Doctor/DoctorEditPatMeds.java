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
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class DoctorEditPatMeds extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtMaKB;
	private JTextField txtMaT;
	private JTextField txtSoLuong;
	private JTextField txtMoTa;

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
			DoctorEditPatMeds dialog = new DoctorEditPatMeds(new User(), new String(), new String(), new Integer(0),
					new String(), new DoctorPatDetails(new User(), new String(), new String()));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DoctorEditPatMeds(final User user, final String maKB, final String maT, final int SoLuong, final String moTa,
			final DoctorPatDetails preFrame) {
		this.user = user;
		setBounds(100, 100, 350, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("Mã Khám Bệnh:");
			label.setHorizontalAlignment(SwingConstants.LEFT);
			label.setFont(new Font("Times New Roman", Font.BOLD, 12));
			label.setBounds(10, 14, 100, 14);
			contentPanel.add(label);
		}
		{
			txtMaKB = new JTextField(maKB);
			txtMaKB.setEditable(false);
			txtMaKB.setColumns(10);
			txtMaKB.setBounds(120, 11, 204, 20);
			contentPanel.add(txtMaKB);
		}
		{
			JLabel lblMaT = new JLabel("Mã Thuốc:");
			lblMaT.setHorizontalAlignment(SwingConstants.LEFT);
			lblMaT.setFont(new Font("Times New Roman", Font.BOLD, 12));
			lblMaT.setBounds(10, 45, 100, 14);
			contentPanel.add(lblMaT);
		}
		{
			txtMaT = new JTextField(maT);
			txtMaT.setEditable(false);
			txtMaT.setColumns(10);
			txtMaT.setBounds(120, 42, 204, 20);
			contentPanel.add(txtMaT);
		}
		{
			JLabel lblSoLuong = new JLabel("Số Lượng:");
			lblSoLuong.setHorizontalAlignment(SwingConstants.LEFT);
			lblSoLuong.setFont(new Font("Times New Roman", Font.BOLD, 12));
			lblSoLuong.setBounds(10, 76, 100, 14);
			contentPanel.add(lblSoLuong);
		}
		{
			txtSoLuong = new JTextField(String.valueOf(SoLuong));
			txtSoLuong.setColumns(10);
			txtSoLuong.setBounds(120, 73, 204, 20);
			contentPanel.add(txtSoLuong);
		}
		{
			JLabel label = new JLabel("Mô Tả:");
			label.setHorizontalAlignment(SwingConstants.LEFT);
			label.setFont(new Font("Times New Roman", Font.BOLD, 12));
			label.setBounds(10, 104, 100, 14);
			contentPanel.add(label);
		}
		{
			txtMoTa = new JTextField(moTa);
			txtMoTa.setColumns(10);
			txtMoTa.setBounds(120, 101, 204, 20);
			contentPanel.add(txtMoTa);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Sửa");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						try {
							int soLuong = Integer.valueOf(txtSoLuong.getText());

							Connection conn = new ConnectionControl().createConnection(user.getUserName(),
									user.getPassword());
							try {

								String sql = "UPDATE QLBV.VIEW_TOATHUOC SET SOLUONG = " + soLuong + ", MOTA = '"
										+ txtMoTa.getText() + "' WHERE MAKB = '" + maKB + "' AND MAT = '" + maT + "'";
								Statement stmt = conn.createStatement();
								stmt.executeUpdate(sql);
								conn.close();

							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Sửa toa thuốc thất bại!");
								conn.close();
							}

							JOptionPane.showMessageDialog(null, "Sửa toa thuốc thành công!");
							dispose();
							preFrame.getTableToaThuoc().fireTableDataChanged();
							preFrame.drawTable_ToaThuoc(maKB);
							preFrame.getTbl_ToaThuoc().setModel(preFrame.getTableToaThuoc());
							genericStuff.resizeTable(preFrame.getTbl_ToaThuoc());
							genericStuff.call_revapaint(preFrame);

						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng số lượng!");
						}

					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Hủy");
				buttonPane.add(cancelButton);
			}
		}
	}

}
