package com.atbm.quanlybenhvien.views.Specialist;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;

public class SpecialistEditPatient extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private GenericStuff genericStuff = new GenericStuff();

	private User user;
	private JTextField txtMaBN;
	private JTextField txtHoTen;
	private JComboBox<String> comboBox_NamSinh;
	private JTextField txtDiaChi;
	private JTextField txtDT;
	private JTextArea txtMoTa;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			SpecialistEditPatient dialog = new SpecialistEditPatient(new User(), new String(), new String(),
					new String(), new String(), new String(), new String(), new SpecialistPatients(new User()));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SpecialistEditPatient(final User user, final String maBN, final String hoTen, final String namSinh,
			final String diaChi, final String dienThoai, final String trieuChung, final SpecialistPatients prevFrame) {
		this.user = user;

		setBounds(100, 100, 330, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblMaBN = new JLabel("Mã Bệnh Nhân:");
		lblMaBN.setBounds(10, 11, 73, 14);
		contentPanel.add(lblMaBN);
		txtMaBN = new JTextField(maBN);
		txtMaBN.setBounds(93, 8, 180, 20);
		contentPanel.add(txtMaBN);
		txtMaBN.setColumns(10);
		txtMaBN.setEditable(false);

		JLabel lblHoTen = new JLabel("Họ Tên:");
		lblHoTen.setBounds(10, 39, 73, 14);
		contentPanel.add(lblHoTen);
		txtHoTen = new JTextField(hoTen);
		txtHoTen.setColumns(10);
		txtHoTen.setBounds(93, 36, 180, 20);
		contentPanel.add(txtHoTen);

		JLabel lblNmSinh = new JLabel("Năm Sinh:");
		lblNmSinh.setBounds(10, 67, 73, 14);
		contentPanel.add(lblNmSinh);
		comboBox_NamSinh = new JComboBox<String>();
		for (int i = Calendar.getInstance().get(Calendar.YEAR) - 100; i <= Calendar.getInstance()
				.get(Calendar.YEAR); i++) {
			comboBox_NamSinh.addItem(String.valueOf(i));
		}
		comboBox_NamSinh.setBounds(93, 67, 180, 20);
		comboBox_NamSinh.setSelectedItem(namSinh);
		contentPanel.add(comboBox_NamSinh);

		JLabel lblDiaChi = new JLabel("Địa Chỉ:");
		lblDiaChi.setBounds(10, 95, 73, 14);
		contentPanel.add(lblDiaChi);
		txtDiaChi = new JTextField(diaChi);
		txtDiaChi.setColumns(10);
		txtDiaChi.setBounds(93, 92, 180, 20);
		contentPanel.add(txtDiaChi);

		JLabel lblDienThoai = new JLabel("Điện Thoại:");
		lblDienThoai.setBounds(10, 123, 73, 14);
		contentPanel.add(lblDienThoai);
		txtDT = new JTextField(dienThoai);
		txtDT.setColumns(10);
		txtDT.setBounds(93, 120, 180, 20);
		contentPanel.add(txtDT);

		JLabel lblMTTriu = new JLabel("Triệu Chứng:");
		lblMTTriu.setBounds(10, 151, 73, 14);
		contentPanel.add(lblMTTriu);
		JScrollPane scrollPane_MoTa = new JScrollPane();
		scrollPane_MoTa.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_MoTa.setBounds(93, 151, 180, 66);
		contentPanel.add(scrollPane_MoTa);
		txtMoTa = new JTextArea(trieuChung);
		txtMoTa.setWrapStyleWord(true);
		txtMoTa.setLineWrap(true);
		scrollPane_MoTa.setViewportView(txtMoTa);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Sửa Thông Tin Bệnh Nhân");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (unchanged_Info(hoTen, namSinh, diaChi, dienThoai, trieuChung)) {
							JOptionPane.showMessageDialog(null, "Vui lòng không bỏ trống các trường!");
						} else {
							Connection conn = new ConnectionControl().createConnection(user.getUserName(),
									user.getPassword());
							PreparedStatement stmt = null;
							try {

								stmt = conn.prepareStatement(
										"UPDATE QLBV.BENHNHAN" + " SET HOTEN = ? " + ", NAMSINH = ? " + ", DIACHI = ? "
												+ ", DIENTHOAI = ? " + ", TRIEUCHUNG = ?" + "WHERE MABN = ?");
								stmt.setString(1, txtHoTen.getText());
								stmt.setInt(2, Integer.parseInt(comboBox_NamSinh.getSelectedItem().toString()));
								stmt.setString(3, txtDiaChi.getText());
								stmt.setString(4, txtDT.getText().toString());
								stmt.setString(5, txtMoTa.getText());
								stmt.setString(6, txtMaBN.getText());

								stmt.executeUpdate();
								conn.close();

								JOptionPane.showMessageDialog(null, "Chỉnh sửa thông tinh bệnh nhân thành công!");
							} catch (Exception e2) {
								e2.printStackTrace();
								JOptionPane.showMessageDialog(null, "Chỉnh sửa thông tinh bệnh nhân thất bại!");
								try {
									conn.close();
								} catch (SQLException e3) {
									e3.printStackTrace();
								}
							}

							dispose();
							prevFrame.getTablePatients().fireTableDataChanged();
							prevFrame.draw_TableKhamBenh();
							prevFrame.getTbl_Patients().setModel(prevFrame.getTablePatients());
							genericStuff.resizeTable(prevFrame.getTbl_Patients());
							genericStuff.call_revapaint(prevFrame);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Hủy");
				cancelButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						dispose();
					}

				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private boolean unchanged_Info(String hoTen, String namSinh, String diaChi, String dienThoai, String trieuChung) {
		if (namSinh.equals(comboBox_NamSinh.getSelectedItem().toString()) && hoTen.equals(txtHoTen.getText())
				&& diaChi.equals(txtDiaChi.getText()) && trieuChung.equals(txtMoTa.getText())) {
			return true;
		}
		return false;
	}
}
