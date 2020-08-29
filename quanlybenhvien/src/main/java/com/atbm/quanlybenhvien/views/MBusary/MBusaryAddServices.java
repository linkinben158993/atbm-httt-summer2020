package com.atbm.quanlybenhvien.views.MBusary;

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
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MBusaryAddServices extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private GenericStuff genericStuff = new GenericStuff();

	private DefaultTableModel tableServices;

	public DefaultTableModel getTableServices() {
		return tableServices;
	}

	public void setTableServices(DefaultTableModel tableServices) {
		this.tableServices = tableServices;
	}

	private JComboBox<String> comboBox_MaKB;
	private JComboBox<String> comboBox_MaDV;
	private JComboBox<String> comboBox_MoTa;

	private JTable tbl_DichVu;

	public JTable getTbl_DichVu() {
		return tbl_DichVu;
	}

	public void setTbl_DichVu(JTable tbl_DichVu) {
		this.tbl_DichVu = tbl_DichVu;
	}

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			MBusaryAddServices dialog = new MBusaryAddServices(new User(), new MBusaryService(new User()));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MBusaryAddServices(final User user, final MBusaryService prevFrame) {
		this.user = user;

		setBounds(100, 100, 640, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		{
			JLabel lblMaKB = new JLabel("Mã Khám Bệnh:");
			lblMaKB.setBounds(10, 11, 94, 14);
			contentPanel.add(lblMaKB);
		}
		{
			comboBox_MaKB = new JComboBox<String>();
			comboBox_MaKB.setBounds(114, 8, 100, 20);
			try {

				Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
						this.user.getPassword());
				String sql = "SELECT * FROM QLBV.KHAMBENH";
				PreparedStatement preparedStatement = conn.prepareStatement(sql);
				ResultSet res = preparedStatement.executeQuery();
				if (res.next() == false) {
					JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong khám bệnh!");

				} else {
					do {
						comboBox_MaKB.addItem(res.getString("MAKB"));

					} while (res.next());

					conn.close();
					comboBox_MaKB.setSelectedItem(null);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			contentPanel.add(comboBox_MaKB);
		}
		{
			JLabel lblMaDV = new JLabel("Mã Dịch Vụ:");
			lblMaDV.setBounds(10, 39, 94, 14);
			contentPanel.add(lblMaDV);
		}
		{
			comboBox_MaDV = new JComboBox<String>();
			comboBox_MaDV.setBounds(114, 36, 100, 20);
			contentPanel.add(comboBox_MaDV);
		}
		{
			JLabel lblMoTa = new JLabel("Mô Tả:");
			lblMoTa.setBounds(10, 67, 94, 14);
			contentPanel.add(lblMoTa);
		}
		{
			comboBox_MoTa = new JComboBox<String>();
			comboBox_MoTa.setBounds(114, 64, 100, 20);
			comboBox_MoTa.addItem("Chờ Dịch Vụ");
			comboBox_MoTa.addItem("Chờ Kết Quả");
			comboBox_MoTa.setSelectedItem(null);
			contentPanel.add(comboBox_MoTa);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(224, 10, 390, 81);
		contentPanel.add(scrollPane);

		draw_TableDichVu();
		tbl_DichVu = new JTable(tableServices);
		tbl_DichVu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tbl_DichVu);
		scrollPane.setViewportView(tbl_DichVu);

		JLabel lblNewLabel = new JLabel("Bảng Tra Cứu Dịch Vụ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(234, 103, 370, 14);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Thêm Mới Điều Phối");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (comboBox_MaDV.getSelectedItem() == null || comboBox_MaKB.getSelectedItem() == null
								|| comboBox_MoTa.getSelectedItem() == null) {
							JOptionPane.showMessageDialog(null, "Vui lòng không để trống các trường");
						} else {
							Connection conn = new ConnectionControl().createConnection(user.getUserName(),
									user.getPassword());
							PreparedStatement stmt = null;
							try {

								stmt = conn.prepareStatement("INSERT INTO QLBV.DIEUPHOIDICHVU VALUES (?,?,?)");
								stmt.setString(1, comboBox_MaKB.getSelectedItem().toString());
								stmt.setString(2, comboBox_MaDV.getSelectedItem().toString());
								stmt.setString(3, comboBox_MoTa.getSelectedItem().toString());

								stmt.executeUpdate();

								conn.close();

								JOptionPane.showMessageDialog(null, "Thêm mới điều phối dịch vụ thành công!");
							} catch (Exception e2) {
								e2.printStackTrace();
								try {
									conn.close();
								} catch (Exception e3) {
									e3.printStackTrace();
								}
								JOptionPane.showMessageDialog(null, "Thêm mới điều phối dịch vụ thất bại!");
							}
						}

						dispose();
						prevFrame.getTablePatients().fireTableDataChanged();
						prevFrame.draw_TableKhamBenh();
						prevFrame.getTbl_Patients().setModel(prevFrame.getTablePatients());
						genericStuff.resizeTable(prevFrame.getTbl_Patients());
						genericStuff.call_revapaint(prevFrame);
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

	@SuppressWarnings("serial")
	public void draw_TableDichVu() {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.DICHVU";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong lương!");

			} else {
				String[] columns = { "STT", "Mã DV", "Tên", "Giá", "Mô Tả", "Bác Sĩ" };
				tableServices = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {

					String[] data = { String.valueOf(i++), res.getString("MADV"), res.getString("TEN"),
							res.getString("GIA"), res.getString("MOTA") != null ? res.getString("MOTA") : "Chưa Có",
							res.getString("BACSI"), };
					tableServices.addRow(data);

					comboBox_MaDV.addItem(res.getString("MADV"));

				} while (res.next());

				conn.close();
				comboBox_MaDV.setSelectedItem(null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
