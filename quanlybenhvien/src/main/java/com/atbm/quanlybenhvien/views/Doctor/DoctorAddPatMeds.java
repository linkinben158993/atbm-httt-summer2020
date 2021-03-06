package com.atbm.quanlybenhvien.views.Doctor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
import java.sql.Statement;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DoctorAddPatMeds extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private GenericStuff genericStuff = new GenericStuff();

	private JTextField txtMaKB;
	private JTextField txtMoTa;

	private DefaultComboBoxModel<String> comboModelThuoc;

	public DefaultComboBoxModel<String> getComboModelThuoc() {
		return comboModelThuoc;
	}

	public void setComboModelThuoc(DefaultComboBoxModel<String> comboModelThuoc) {
		this.comboModelThuoc = comboModelThuoc;
	}

	private DefaultTableModel table_Thuoc;

	public DefaultTableModel getTable_Thuoc() {
		return table_Thuoc;
	}

	public void setTable_Thuoc(DefaultTableModel table_Thuoc) {
		this.table_Thuoc = table_Thuoc;
	}

	private JTable tbl_Thuoc;
	private JTextField txtSoLuong;

	public static void main(String[] args) {
		try {
			DoctorAddPatMeds dialog = new DoctorAddPatMeds(new User(), new String(),
					new DoctorPatDetails(new User(), new String(), new String()));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DoctorAddPatMeds(final User user, final String maKB, final DoctorPatDetails preFrame) {
		this.user = user;

		setBounds(100, 100, 750, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblInfo = new JLabel("Thêm Mới Toa Thuốc:");
		lblInfo.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblInfo.setBounds(0, 14, 184, 14);
		contentPanel.add(lblInfo);

		JLabel lblMaKB = new JLabel("Mã Khám Bệnh:");
		lblMaKB.setHorizontalAlignment(SwingConstants.LEFT);
		lblMaKB.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblMaKB.setBounds(0, 41, 100, 14);
		contentPanel.add(lblMaKB);

		txtMaKB = new JTextField();
		txtMaKB.setText(maKB);
		txtMaKB.setEditable(false);
		txtMaKB.setColumns(10);
		txtMaKB.setBounds(110, 38, 86, 20);
		contentPanel.add(txtMaKB);

		draw_ComboBoxTableDichVu();
		JLabel lblMaT = new JLabel("Mã Thuốc:");
		lblMaT.setHorizontalAlignment(SwingConstants.LEFT);
		lblMaT.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblMaT.setBounds(0, 72, 100, 14);
		contentPanel.add(lblMaT);
		final JComboBox<String> comboBox_MaT = new JComboBox<String>(comboModelThuoc);
		comboBox_MaT.setSelectedItem(null);
		comboBox_MaT.setBounds(110, 69, 86, 20);
		contentPanel.add(comboBox_MaT);

		JLabel lblMoTa = new JLabel("Mô Tả:");
		lblMoTa.setHorizontalAlignment(SwingConstants.LEFT);
		lblMoTa.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblMoTa.setBounds(0, 103, 100, 14);
		contentPanel.add(lblMoTa);
		txtMoTa = new JTextField();
		txtMoTa.setColumns(10);
		txtMoTa.setBounds(110, 100, 86, 20);
		contentPanel.add(txtMoTa);

		JLabel lblLookUp = new JLabel("Bảng Tra Cứu Thuốc");
		lblLookUp.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblLookUp.setBounds(206, 14, 184, 14);
		contentPanel.add(lblLookUp);
		JScrollPane scrollPane_Thuoc = new JScrollPane();
		scrollPane_Thuoc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_Thuoc.setBounds(206, 40, 518, 177);
		contentPanel.add(scrollPane_Thuoc);
		tbl_Thuoc = new JTable(table_Thuoc);
		genericStuff.resizeTable(tbl_Thuoc);
		scrollPane_Thuoc.setViewportView(tbl_Thuoc);

		JLabel lblSoLuong = new JLabel("Số Lượng:");
		lblSoLuong.setHorizontalAlignment(SwingConstants.LEFT);
		lblSoLuong.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblSoLuong.setBounds(0, 131, 100, 14);
		contentPanel.add(lblSoLuong);

		txtSoLuong = new JTextField();
		txtSoLuong.setColumns(10);
		txtSoLuong.setBounds(110, 128, 86, 20);
		contentPanel.add(txtSoLuong);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnThem = new JButton("Thêm Mới");
				btnThem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (comboBox_MaT.getSelectedItem() == null) {
							JOptionPane.showMessageDialog(null, "Vui lòng chọn toa thuốc để thêm mới!");
						} else {
							String maThuoc = comboBox_MaT.getSelectedItem().toString();
							int soLuong = 0;
							try {
								soLuong = Integer.valueOf(txtSoLuong.getText());
								String moTa = txtMoTa.getText();
								Connection conn = new ConnectionControl().createConnection(user.getUserName(),
										user.getPassword());

								try {
									String sql = "INSERT INTO QLBV.VIEW_TOATHUOC VALUES ('" + maKB + "','" + maThuoc
											+ "'," + soLuong + ",'" + moTa + "')";
									Statement stmt = conn.createStatement();
									stmt.executeUpdate(sql);

									JOptionPane.showMessageDialog(null, "Thêm mới toa thuốc thành công!");

									conn.close();

								} catch (Exception e) {
									e.printStackTrace();
									JOptionPane.showMessageDialog(null, "Thêm mới thất bại!");
									conn.close();
								}

								dispose();
								preFrame.getTableToaThuoc().fireTableDataChanged();
								preFrame.drawTable_ToaThuoc(maKB);
								preFrame.getTbl_ToaThuoc().setModel(preFrame.getTableToaThuoc());
								genericStuff.resizeTable(preFrame.getTbl_ToaThuoc());
								genericStuff.call_revapaint(preFrame);

							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng ở số lượng!");
							}
						}
					}
				});
				buttonPane.add(btnThem);
				getRootPane().setDefaultButton(btnThem);
			}
			{
				JButton bthHuy = new JButton("Hủy");
				buttonPane.add(bthHuy);
			}
		}
	}

	@SuppressWarnings("serial")
	private void draw_ComboBoxTableDichVu() {
		comboModelThuoc = new DefaultComboBoxModel<String>();
		table_Thuoc = new DefaultTableModel();
		try {
			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
			// Lấy tất cả các bảng thuộc quyền sở hữu của dba
			String sql = "SELECT * FROM QLBV.THUOC";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet res = statement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dịch vụ nào!");
			} else {
				String[] columns = { "STT", "Mã Thuốc", "Tên", "Giá", "Mô Tả" };
				table_Thuoc = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					String[] data = { String.valueOf(i++), res.getString("MAT"), res.getString("TEN"),
							res.getString("GIA"), res.getString("MOTA") };
					table_Thuoc.addRow(data);
					comboModelThuoc.addElement(res.getString("MAT").toString());
				} while (res.next());
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
