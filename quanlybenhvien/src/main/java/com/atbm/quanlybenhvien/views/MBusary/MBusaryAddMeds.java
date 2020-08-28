package com.atbm.quanlybenhvien.views.MBusary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;

public class MBusaryAddMeds extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private GenericStuff genericStuff = new GenericStuff();

	private User user;
	private JTable tbl_Meds;

	private DefaultTableModel tableMeds;

	public DefaultTableModel getTableMeds() {
		return tableMeds;
	}

	public void setTableMeds(DefaultTableModel tableMeds) {
		this.tableMeds = tableMeds;
	}

	private JComboBox<String> comboBox_MaKB;
	private JComboBox<String> comboBox_MaT;
	private JComboBox<String> comboBox_SoLuong;
	private JTextArea txtMoTa;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			MBusaryAddMeds dialog = new MBusaryAddMeds(new User(), new MBusaryMeds(new User()));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MBusaryAddMeds(final User user, final MBusaryMeds prevFrame) {
		this.user = user;

		setBounds(100, 100, 750, 320);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblMaKB = new JLabel("Mã Khám Bệnh: ");
		lblMaKB.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblMaKB.setBounds(10, 11, 83, 14);
		contentPanel.add(lblMaKB);
		{
			comboBox_MaKB = new JComboBox<String>();
			comboBox_MaKB.setMaximumRowCount(5);
			comboBox_MaKB.setBounds(103, 8, 140, 20);
			contentPanel.add(comboBox_MaKB);
		}
		{
			JLabel lblMaT = new JLabel("Mã Thuốc: ");
			lblMaT.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblMaT.setBounds(10, 39, 83, 14);
			contentPanel.add(lblMaT);
		}
		{
			comboBox_MaT = new JComboBox<String>();
			comboBox_MaT.setMaximumRowCount(5);
			comboBox_MaT.setBounds(103, 36, 140, 20);
			contentPanel.add(comboBox_MaT);
		}
		{
			JLabel lblSoLuong = new JLabel("Số Lượng:");
			lblSoLuong.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblSoLuong.setBounds(10, 67, 83, 14);
			contentPanel.add(lblSoLuong);
		}
		{
			comboBox_SoLuong = new JComboBox<String>();
			comboBox_SoLuong.setMaximumRowCount(5);
			comboBox_SoLuong.setBounds(103, 64, 140, 20);
			for (int i = 1; i < 11; i++) {
				comboBox_SoLuong.addItem(String.valueOf(i));
			}
			comboBox_SoLuong.setSelectedItem(null);
			contentPanel.add(comboBox_SoLuong);
		}
		{
			JLabel lblMoTa = new JLabel("Mô Tả:");
			lblMoTa.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblMoTa.setBounds(10, 92, 83, 14);
			contentPanel.add(lblMoTa);
		}
		{
			JScrollPane scrollPane_MoTa = new JScrollPane();
			scrollPane_MoTa.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane_MoTa.setBounds(103, 87, 140, 130);
			contentPanel.add(scrollPane_MoTa);
			{
				txtMoTa = new JTextArea();
				txtMoTa.setWrapStyleWord(true);
				txtMoTa.setLineWrap(true);
				scrollPane_MoTa.setViewportView(txtMoTa);
			}
		}
		{
			JScrollPane scrollPane_ToaThuoc = new JScrollPane();
			scrollPane_ToaThuoc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane_ToaThuoc.setBounds(253, 11, 471, 206);
			contentPanel.add(scrollPane_ToaThuoc);
			{
				draw_Component();
				comboBox_MaKB.setSelectedItem(null);
				comboBox_MaT.setSelectedItem(null);
				tbl_Meds = new JTable(tableMeds);
				genericStuff.resizeTable(tbl_Meds);
				scrollPane_ToaThuoc.setViewportView(tbl_Meds);
			}
		}
		{
			JLabel lblNewLabel = new JLabel("Bảng Tra Cứu Thông Tin Thuốc");
			lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(253, 223, 471, 14);
			contentPanel.add(lblNewLabel);
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Thêm Mới Toa Thuốc");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (comboBox_MaKB.getSelectedItem().toString().equals("")
								|| comboBox_MaT.getSelectedItem().toString().equals("")
								|| comboBox_SoLuong.getSelectedItem().toString().equals("")
								|| txtMoTa.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Vui lòng không để trống các trường!");
						} else {
							Connection conn = new ConnectionControl().createConnection(user.getUserName(),
									user.getPassword());
							PreparedStatement stmt = null;
							try {
								stmt = conn.prepareStatement("INSERT INTO QLBV.TOATHUOC VALUES (?,?,?,?,?)");
								stmt.setString(1, comboBox_MaKB.getSelectedItem().toString());
								stmt.setString(2, comboBox_MaT.getSelectedItem().toString());
								stmt.setString(3, comboBox_SoLuong.getSelectedItem().toString());
								stmt.setString(4, txtMoTa.getText());
								stmt.setNull(5, Types.DOUBLE);

								stmt.executeUpdate();
								conn.close();

								JOptionPane.showMessageDialog(null, "Thêm mới toa thuốc thành công!");

							} catch (SQLException e1) {
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, "Thêm mới toa thuốc thất bại!");
								try {
									conn.close();
								} catch (SQLException e2) {
									e2.printStackTrace();
								}
							}

							dispose();
							prevFrame.getTableMeds().fireTableDataChanged();
							prevFrame.draw_TableMeds();
							prevFrame.getTbl_Meds().setModel(prevFrame.getTableMeds());
							genericStuff.resizeTable(prevFrame.getTbl_Meds());
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

	@SuppressWarnings("serial")
	private void draw_Component() {
		try {
			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.THUOC";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong trực phòng!");

			} else {
				String[] columns = { "STT", "Mã Thuốc", "Tên Thuốc", "Mô Tả", "Giá" };
				tableMeds = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {

					String[] data = { String.valueOf(i++), res.getString("MAT"), res.getString("TEN"),
							res.getString("MOTA"), res.getString("GIA") };
					tableMeds.addRow(data);

					comboBox_MaT.addItem(res.getString("MAT"));
				} while (res.next());
			}

			String sql_comboboxes = "SELECT * FROM QLBV.KHAMBENH";
			preparedStatement = conn.prepareStatement(sql_comboboxes);
			ResultSet res_comboboxes = preparedStatement.executeQuery();
			if (res_comboboxes.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong khám bệnh!");
			} else {
				do {
					comboBox_MaKB.addItem(res_comboboxes.getString("MAKB"));
				} while (res_comboboxes.next());
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
