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

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class MBusaryAddPatients extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private GenericStuff genericStuff = new GenericStuff();

	private JTextField txtMaKB;
	private JTable tbl_TrucPhong;
	private DefaultTableModel tableTrucPhong;

	private JButton okButton;
	private JButton cancelButton;

	public DefaultTableModel getTableTrucPhong() {
		return tableTrucPhong;
	}

	public void setTableTrucPhong(DefaultTableModel tableTrucPhong) {
		this.tableTrucPhong = tableTrucPhong;
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
			MBusaryAddPatients dialog = new MBusaryAddPatients(new User(), new MBusaryPatients(new User()));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MBusaryAddPatients(final User user, final MBusaryPatients prevFrame) {
		this.user = user;

		setBounds(100, 100, 769, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblMaKB = new JLabel("Mã Khám Bệnh: ");
		lblMaKB.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblMaKB.setBounds(10, 11, 83, 14);
		contentPanel.add(lblMaKB);

		txtMaKB = new JTextField();
		txtMaKB.setBounds(103, 8, 140, 20);
		contentPanel.add(txtMaKB);
		txtMaKB.setColumns(10);

		JLabel lblBenhNhan = new JLabel("Bệnh Nhân:");
		lblBenhNhan.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblBenhNhan.setBounds(10, 39, 83, 14);
		contentPanel.add(lblBenhNhan);
		final JComboBox<String> comboBox_BN = new JComboBox<String>();
		comboBox_BN.setMaximumRowCount(5);
		comboBox_BN.setBounds(103, 36, 140, 20);
		try {
			Connection conn = null;
			try {
				conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM QLBV.BENHNHAN");
				ResultSet res = stmt.executeQuery();
				if (res.next() == false) {
					JOptionPane.showMessageDialog(null, "Chưa có bệnh nhân nào!");
				} else {
					do {
						comboBox_BN.addItem(res.getString("MABN"));
					} while (res.next());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		comboBox_BN.setSelectedItem(null);
		contentPanel.add(comboBox_BN);
		JLabel lblPhong = new JLabel("Phòng: ");
		lblPhong.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblPhong.setBounds(10, 67, 83, 14);
		contentPanel.add(lblPhong);
		final JComboBox<String> comboBox_Phong = new JComboBox<String>();
		comboBox_Phong.setMaximumRowCount(5);
		comboBox_Phong.setBounds(103, 64, 140, 20);
		contentPanel.add(comboBox_Phong);

		JLabel lblBacSi = new JLabel("Bác Sĩ:");
		lblBacSi.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblBacSi.setBounds(10, 95, 83, 14);
		contentPanel.add(lblBacSi);
		final JComboBox<String> comboBox_BS = new JComboBox<String>();
		comboBox_BS.setMaximumRowCount(5);
		comboBox_BS.setBounds(103, 92, 140, 20);
		contentPanel.add(comboBox_BS);

		try {
			Connection conn = null;
			try {
				conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT NHANVIEN, PHONG FROM QLBV.TRUCPHONG WHERE NHANVIEN LIKE '%BS%' GROUP BY (NHANVIEN,PHONG)");
				ResultSet res = stmt.executeQuery();
				if (res.next() == false) {
					JOptionPane.showMessageDialog(null, "Chưa có phân công nào!");
				} else {
					do {
						comboBox_Phong.addItem(res.getString("PHONG"));
						comboBox_BS.addItem(res.getString("NHANVIEN"));
					} while (res.next());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		comboBox_Phong.setSelectedItem(null);
		comboBox_BS.setSelectedItem(null);

		JLabel lblMoTa = new JLabel("Mô Tả:");
		lblMoTa.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblMoTa.setBounds(10, 120, 83, 14);
		contentPanel.add(lblMoTa);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(103, 115, 140, 102);
		contentPanel.add(scrollPane);
		final JTextArea txtMoTa = new JTextArea();
		scrollPane.setViewportView(txtMoTa);
		txtMoTa.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		txtMoTa.setWrapStyleWord(true);
		txtMoTa.setLineWrap(true);

		JScrollPane scrollPane_TrucPhong = new JScrollPane();
		scrollPane_TrucPhong.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_TrucPhong.setBounds(253, 11, 500, 181);
		contentPanel.add(scrollPane_TrucPhong);

		draw_TableTrucPhong();
		tbl_TrucPhong = new JTable(tableTrucPhong);
		tbl_TrucPhong.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tbl_TrucPhong);
		scrollPane_TrucPhong.setViewportView(tbl_TrucPhong);

		JLabel lblNewLabel = new JLabel("Bảng Trực Phòng Bác Sĩ");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(253, 203, 500, 14);
		contentPanel.add(lblNewLabel);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Thêm Mới");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtMaKB.getText().equals("") || comboBox_BN.getSelectedIndex() == -1
								|| comboBox_Phong.getSelectedIndex() == -1 || comboBox_BS.getSelectedIndex() == -1
								|| txtMoTa.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Vui lòng không để trống các trường!");
						} else {
							if (!txtMaKB.getText().matches("(KB)([0-9]){2,}")) {
								JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng mã khám bệnh!");
								JOptionPane.showMessageDialog(null, "Mẫu KB<Mã Khám Bệnh>: KB01");
							} else {
								Connection conn = new ConnectionControl().createConnection(user.getUserName(),
										user.getPassword());
								PreparedStatement stmt = null;
								try {
									stmt = conn.prepareStatement("INSERT INTO QLBV.KHAMBENH VALUES (?,?,?,?,?,?,?)");
									stmt.setString(1, txtMaKB.getText());
									stmt.setString(2, comboBox_BN.getSelectedItem().toString());
									stmt.setString(3, comboBox_Phong.getSelectedItem().toString());
									stmt.setString(4, comboBox_BS.getSelectedItem().toString());
									stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
									stmt.setString(6, txtMoTa.getText());
									stmt.setNull(7, Types.DOUBLE);

									stmt.executeUpdate();
									conn.close();

									JOptionPane.showMessageDialog(null, "Thêm mới khám bệnh thành công!");

								} catch (SQLException e1) {
									e1.printStackTrace();
									JOptionPane.showMessageDialog(null, "Thêm mới khám bệnh thất bại!");
									try {
										conn.close();
									} catch (SQLException e2) {
										e2.printStackTrace();
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
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Hủy");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { txtMaKB, comboBox_BN, comboBox_Phong,
				comboBox_BS, txtMoTa, okButton, cancelButton }));
	}

	@SuppressWarnings("serial")
	private void draw_TableTrucPhong() {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.TRUCPHONG WHERE NHANVIEN LIKE '%BS%' ORDER BY PHONG";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong trực phòng!");

			} else {
				String[] columns = { "STT", "Bác Sĩ", "Phòng", "Từ Ngày", "Đến Ngày" };
				tableTrucPhong = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					Timestamp ts_Tu = Timestamp.valueOf(res.getString("TU"));
					LocalDateTime lcdt_Tu = ts_Tu.toLocalDateTime();

					Timestamp ts_Den = Timestamp.valueOf(res.getString("DEN"));
					LocalDateTime lcdt_Den = ts_Den.toLocalDateTime();

					String[] data = { String.valueOf(i++), res.getString("NHANVIEN"), res.getString("PHONG"),
							dateTimeFormatter.format(lcdt_Tu).toString(),
							dateTimeFormatter.format(lcdt_Den).toString() };
					tableTrucPhong.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}