package com.atbm.quanlybenhvien.views.Specialist;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class SpecialistAddPatients extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private GenericStuff genericStuff = new GenericStuff();

	private User user;
	private JTextField txtMaBN;
	private JTextField txtHoTen;
	private JTextField txtDiaChi;
	private JTextField txtDT;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			SpecialistAddPatients dialog = new SpecialistAddPatients(new User(), new SpecialistPatients(new User()));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SpecialistAddPatients(final User user, final SpecialistPatients prevFrame) {
		this.user = user;
		setBounds(100, 100, 330, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblMaBN = new JLabel("Mã Bệnh Nhân:");
		lblMaBN.setBounds(10, 11, 73, 14);
		contentPanel.add(lblMaBN);

		txtMaBN = new JTextField();
		txtMaBN.setBounds(93, 8, 180, 20);
		contentPanel.add(txtMaBN);
		txtMaBN.setColumns(10);

		JLabel lblHoTen = new JLabel("Họ Tên:");
		lblHoTen.setBounds(10, 39, 73, 14);
		contentPanel.add(lblHoTen);

		txtHoTen = new JTextField();
		txtHoTen.setColumns(10);
		txtHoTen.setBounds(93, 36, 180, 20);
		contentPanel.add(txtHoTen);

		JLabel lblNmSinh = new JLabel("Năm Sinh:");
		lblNmSinh.setBounds(10, 67, 73, 14);
		contentPanel.add(lblNmSinh);

		final JComboBox<String> comboBox_NamSinh = new JComboBox<String>();
		for (int i = Calendar.getInstance().get(Calendar.YEAR) - 100; i <= Calendar.getInstance()
				.get(Calendar.YEAR); i++) {
			comboBox_NamSinh.addItem(String.valueOf(i));
		}
		comboBox_NamSinh.setBounds(93, 67, 180, 20);
		contentPanel.add(comboBox_NamSinh);

		JLabel lblDiaChi = new JLabel("Địa Chỉ:");
		lblDiaChi.setBounds(10, 95, 73, 14);
		contentPanel.add(lblDiaChi);

		txtDiaChi = new JTextField();
		txtDiaChi.setColumns(10);
		txtDiaChi.setBounds(93, 92, 180, 20);
		contentPanel.add(txtDiaChi);

		JLabel lblDienThoai = new JLabel("Điện Thoại:");
		lblDienThoai.setBounds(10, 123, 73, 14);
		contentPanel.add(lblDienThoai);

		txtDT = new JTextField();
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

		final JTextArea txtMoTa = new JTextArea();
		txtMoTa.setWrapStyleWord(true);
		txtMoTa.setLineWrap(true);
		scrollPane_MoTa.setViewportView(txtMoTa);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Thêm Mới Bệnh Nhân");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtMaBN.getText().equals("") || txtHoTen.getText().equals("")
								|| comboBox_NamSinh.getSelectedItem().toString().equals("")
								|| txtMoTa.getText().toString().equals("")) {
							JOptionPane.showMessageDialog(null, "Vui lòng không bỏ trống các trường!");
						}
						if (!txtMaBN.getText().matches("(BN)([0-9]){2,}")) {
							JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng mã bệnh nhân!");
							JOptionPane.showMessageDialog(null, "Mẫu BN<Mã Khám Bệnh>: BN01");
						} else {
							Connection conn = new ConnectionControl().createConnection(user.getUserName(),
									user.getPassword());
							PreparedStatement stmt = null;
							try {

								stmt = conn.prepareStatement("INSERT INTO QLBV.BENHNHAN VALUES (?,?,?,?,?,?,?)");
								stmt.setString(1, txtMaBN.getText());
								stmt.setString(2, txtHoTen.getText());
								stmt.setInt(3, Integer.parseInt(comboBox_NamSinh.getSelectedItem().toString()));
								stmt.setString(4, txtDiaChi.getText().toString());
								stmt.setString(5, txtDT.getText().toString());
								stmt.setString(6, txtMoTa.getText());
								stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

								stmt.executeUpdate();
								conn.close();

								JOptionPane.showMessageDialog(null, "Thêm mới bệnh nhân	thành công!");
							} catch (Exception e2) {
								e2.printStackTrace();
								JOptionPane.showMessageDialog(null, "Thêm mới bệnh nhân thất bại!");
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
}
