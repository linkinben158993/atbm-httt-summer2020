package com.atbm.quanlybenhvien.views.MHumanResources;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.util.DateFormatter;
import com.atbm.quanlybenhvien.views.GenericStuff;

public class MHREditAssignment extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private GenericStuff genericStuff = new GenericStuff();

	private DefaultTableModel tabelNhanVien;

	public DefaultTableModel getTabelNhanVien() {
		return tabelNhanVien;
	}

	public void setTabelNhanVien(DefaultTableModel tabelNhanVien) {
		this.tabelNhanVien = tabelNhanVien;
	}

	private UtilDateModel datePickerModel_Start;

	public UtilDateModel getDatePickerModel_Start() {
		return datePickerModel_Start;
	}

	private JTable tbl_NV;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			MHREditAssignment dialog = new MHREditAssignment(new User(), new MHRAssignment(new User()), new String(),
					new String());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	public MHREditAssignment(final User user, final MHRAssignment prevFrame, String phong, String nhanVien) {
		this.user = user;

		setBounds(100, 100, 780, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblPhong = new JLabel("Phòng:");
			lblPhong.setBounds(10, 11, 80, 14);
			contentPanel.add(lblPhong);
		}

		final JComboBox<String> comboBox_Phong = new JComboBox<String>();
		comboBox_Phong.setEnabled(false);
		comboBox_Phong.setMaximumRowCount(5);
		comboBox_Phong.setBounds(100, 8, 135, 20);
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.PHONG ORDER BY MAP";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();

			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong nhân viên!");

			} else {
				do {
					comboBox_Phong.addItem(res.getString("MAP"));
				} while (res.next());
				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		comboBox_Phong.setSelectedItem(phong);
		contentPanel.add(comboBox_Phong);

		JLabel lblNhanVien = new JLabel("Nhân Viên:");
		lblNhanVien.setBounds(10, 39, 80, 14);
		contentPanel.add(lblNhanVien);

		final JComboBox<String> comboBox_NV = new JComboBox<String>();
		comboBox_NV.setEnabled(false);
		comboBox_NV.setMaximumRowCount(5);
		comboBox_NV.setBounds(100, 36, 135, 20);
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.NHANVIEN ORDER BY MANV";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();

			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong nhân viên!");

			} else {
				String[] columns = { "STT", "Mã Nhân Viên", "Họ Tên", "Phòng", "Chức Vụ" };
				tabelNhanVien = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					String[] data = { String.valueOf(i++), res.getString("MANV"), res.getString("HOTEN"),
							res.getString("PHONG"), res.getString("CHUCVU") };
					tabelNhanVien.addRow(data);

					comboBox_NV.addItem(res.getString("MANV"));
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		comboBox_NV.setSelectedItem(nhanVien);
		contentPanel.add(comboBox_NV);

		JLabel lblSoNgay = new JLabel("Số Ngày:");
		lblSoNgay.setBounds(10, 67, 80, 14);
		contentPanel.add(lblSoNgay);
		final JComboBox<String> comboBox_SoNgay = new JComboBox<String>();
		comboBox_SoNgay.setMaximumRowCount(5);
		comboBox_SoNgay.setBounds(100, 64, 135, 20);
		for (int i = 1; i < 11; i++) {
			comboBox_SoNgay.addItem(String.valueOf(i));
		}
		comboBox_SoNgay.setSelectedItem(null);
		contentPanel.add(comboBox_SoNgay);

		JLabel lblBatDau = new JLabel("Ngày Bắt Đầu:");
		lblBatDau.setBounds(10, 92, 80, 14);
		contentPanel.add(lblBatDau);

		Properties p = new Properties();
		p.put("Ngày", LocalDate.now().getDayOfMonth());
		p.put("Tháng", LocalDate.now().getMonth());
		p.put("Năm", LocalDate.now().getYear());

		Date today = new Date();

		this.datePickerModel_Start = new UtilDateModel(today);
		JDatePanelImpl datePanelImpl_Start = new JDatePanelImpl(datePickerModel_Start, p);
		JDatePickerImpl datePickerImpl_Start = new JDatePickerImpl(datePanelImpl_Start, new DateFormatter());
		datePickerImpl_Start.setLocation(100, 95);
		datePickerImpl_Start.setSize(135, 25);
		contentPanel.add(datePickerImpl_Start);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(245, 11, 509, 135);
		contentPanel.add(scrollPane);

		tbl_NV = new JTable(tabelNhanVien);
		genericStuff.resizeTable(tbl_NV);
		scrollPane.setViewportView(tbl_NV);

		JLabel lblNewLabel = new JLabel("Bảng Tra Cứu Thông Tin Nhân Viên");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(245, 157, 509, 14);
		contentPanel.add(lblNewLabel);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Sửa Trực Phòng");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (comboBox_SoNgay.getSelectedItem() == null) {
							JOptionPane.showMessageDialog(null, "Vui lòng chọn đầy đủ các mục khi thêm!");
						} else {
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
							Date dateStart_comp = null;
							try {
								dateStart_comp = sdf
										.parse(sdf.format(getDatePickerModel_Start().getValue()).toString());
							} catch (ParseException e3) {
								e3.printStackTrace();
							}
							Timestamp time_Tu = new Timestamp(dateStart_comp.getTime());
							Timestamp time_Den = new Timestamp(time_Tu.getTime() + (1000 * 60 * 60 * 24
									* Integer.parseInt(comboBox_SoNgay.getSelectedItem().toString())));
							Connection conn = new ConnectionControl().createConnection(user.getUserName(),
									user.getPassword());
							PreparedStatement stmt = null;
							try {
								stmt = conn.prepareStatement(
										"UPDATE QLBV.TRUCPHONG SET TU = ?, DEN = ? WHERE PHONG = ? AND NHANVIEN = ?");
								stmt.setTimestamp(1, time_Tu);
								stmt.setTimestamp(2, time_Den);
								stmt.setString(3, comboBox_Phong.getSelectedItem().toString());
								stmt.setString(4, comboBox_NV.getSelectedItem().toString());
								stmt.executeUpdate();
								conn.close();

								JOptionPane.showMessageDialog(null, "Sửa trực phòng thành công!");

							} catch (SQLException e1) {
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, "Sửa trực phòng thất bại!");
								try {
									conn.close();
								} catch (SQLException e2) {
									e2.printStackTrace();
								}
							}

							dispose();
							prevFrame.getTableTrucPhong().fireTableDataChanged();
							prevFrame.draw_TableTrucPhong();
							prevFrame.getTbl_TrucPhong().setModel(prevFrame.getTableTrucPhong());
							genericStuff.resizeTable(prevFrame.getTbl_TrucPhong());
							genericStuff.call_revapaint(prevFrame);
						}
					}
				});
				okButton.setActionCommand("Thay Đổi Thông Tin Trực Phòng");
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
