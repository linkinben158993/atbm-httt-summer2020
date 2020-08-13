package com.atbm.quanlybenhvien.views.Doctor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DoctorAddServices extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private User user;
	private JTextField txtMaKB;
	private JTextField txtMoTa;
	private GenericStuff genericStuff = new GenericStuff();

	private JTable tbl_DichVu;

	public JTable getTbl_DichVu() {
		return tbl_DichVu;
	}

	public void setTbl_DichVu(JTable tbl_DichVu) {
		this.tbl_DichVu = tbl_DichVu;
	}

	private DefaultTableModel table_DichVu;

	public DefaultTableModel getTable_DichVu() {
		return table_DichVu;
	}

	public void setTable_DichVu(DefaultTableModel table_DichVu) {
		this.table_DichVu = table_DichVu;
	}

	private DefaultComboBoxModel<String> comboModelDichVu;

	public DefaultComboBoxModel<String> getComboModelDichVu() {
		return comboModelDichVu;
	}

	public void setComboModelDichVu(DefaultComboBoxModel<String> comboModelDichVu) {
		this.comboModelDichVu = comboModelDichVu;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			DoctorAddServices dialog = new DoctorAddServices(new User(), new String(),
					new DoctorPatDetails(new User(), new String(), new String()));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DoctorAddServices(final User user, final String maKB, final DoctorPatDetails preFrame) {
		this.user = user;

		setBounds(100, 100, 550, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblMaKB = new JLabel("Mã Khám Bệnh:");
			lblMaKB.setHorizontalAlignment(SwingConstants.LEFT);
			lblMaKB.setFont(new Font("Times New Roman", Font.BOLD, 12));
			lblMaKB.setBounds(10, 11, 100, 14);
			contentPanel.add(lblMaKB);
		}

		txtMaKB = new JTextField();
		txtMaKB.setBounds(120, 8, 86, 20);
		contentPanel.add(txtMaKB);
		txtMaKB.setColumns(10);
		txtMaKB.setText(maKB);
		txtMaKB.setEditable(false);

		{
			JLabel lblMaDV = new JLabel("Mã Dịch Vụ:");
			lblMaDV.setHorizontalAlignment(SwingConstants.LEFT);
			lblMaDV.setFont(new Font("Times New Roman", Font.BOLD, 12));
			lblMaDV.setBounds(10, 42, 100, 14);
			contentPanel.add(lblMaDV);
		}
		{
			JLabel lblMT = new JLabel("Mô Tả:");
			lblMT.setHorizontalAlignment(SwingConstants.LEFT);
			lblMT.setFont(new Font("Times New Roman", Font.BOLD, 12));
			lblMT.setBounds(10, 73, 100, 14);
			contentPanel.add(lblMT);
		}
		{
			txtMoTa = new JTextField();
			txtMoTa.setColumns(10);
			txtMoTa.setBounds(120, 70, 86, 20);
			contentPanel.add(txtMoTa);
		}

		draw_ComboBoxTableDichVu();
		final JComboBox<String> comboBox_DichVu = new JComboBox<String>(comboModelDichVu);
		comboBox_DichVu.setSelectedItem(null);
		comboBox_DichVu.setBounds(120, 39, 86, 20);
		contentPanel.add(comboBox_DichVu);
		{
			JLabel lblLookUp = new JLabel("Bảng Tra Cứu Dịch Vụ");
			lblLookUp.setFont(new Font("Times New Roman", Font.BOLD, 12));
			lblLookUp.setBounds(216, 11, 184, 14);
			contentPanel.add(lblLookUp);
		}
		{
			JScrollPane scrollPane_DichVu = new JScrollPane();
			scrollPane_DichVu.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane_DichVu.setBounds(216, 39, 308, 78);
			contentPanel.add(scrollPane_DichVu);
			{
				tbl_DichVu = new JTable(table_DichVu);
				genericStuff.resizeTable(tbl_DichVu);
				scrollPane_DichVu.setViewportView(tbl_DichVu);
			}
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Thêm");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						if (comboBox_DichVu.getSelectedItem() == null) {
							JOptionPane.showMessageDialog(null, "Vui lòng chọn dịch vụ muốn thêm!");
						} else {
							String maDV = comboBox_DichVu.getSelectedItem().toString();
							String moTa = txtMoTa.getText();

							Connection conn = new ConnectionControl().createConnection(user.getUserName(),
									user.getPassword());
							try {

								String sql = "INSERT INTO QLBV.view_dieuphoidichvu VALUES ('" + maKB + "', '" + maDV
										+ "', '" + moTa + "')";
								Statement stmt = conn.createStatement();
								stmt.executeUpdate(sql);
								conn.close();

							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Thêm mới dịch vụ thất bại!");
								try {
									conn.close();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
							JOptionPane.showMessageDialog(null, "Thêm mới dịch vụ thành công!");

							dispose();
							preFrame.getTableDichVu().fireTableDataChanged();
							preFrame.drawTable_DichVu(maKB);
							preFrame.getTbl_DichVu().setModel(preFrame.getTableDichVu());
							genericStuff.resizeTable(preFrame.getTbl_DichVu());
							genericStuff.call_revapaint(preFrame);
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

	@SuppressWarnings("serial")
	private void draw_ComboBoxTableDichVu() {
		comboModelDichVu = new DefaultComboBoxModel<String>();
		table_DichVu = new DefaultTableModel();
		try {
			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
			// Lấy tất cả các bảng thuộc quyền sở hữu của dba
			String sql = "SELECT * FROM QLBV.DICHVU";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet res = statement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dịch vụ nào!");
			} else {
				String[] columns = { "STT", "Mã Dịch Vụ", "Dịch Vụ", "Giá", "Bác Sĩ" };
				table_DichVu = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					String[] data = { String.valueOf(i++), res.getString("MADV"), res.getString("TEN"),
							res.getString("GIA"), res.getString("BACSI") };
					table_DichVu.addRow(data);
					comboModelDichVu.addElement(res.getString("MADV").toString());
				} while (res.next());
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
