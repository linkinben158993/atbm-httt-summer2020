package com.atbm.quanlybenhvien.views.MHumanResources;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.util.DateFormatter;
import com.atbm.quanlybenhvien.views.GenericStuff;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class MHREditDay extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private GenericStuff genericStuff = new GenericStuff();

	private UtilDateModel datePickerModel_Day;

	public UtilDateModel getDatePickerModel_Day() {
		return datePickerModel_Day;
	}

	public void setDatePickerModel_Day(UtilDateModel datePickerModel_Day) {
		this.datePickerModel_Day = datePickerModel_Day;
	}

	private User user;
	private JTextField txtMaNV;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			MHREditDay dialog = new MHREditDay(new User(), new String(), new String(), new MHREmployee(new User()));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MHREditDay(final User user, final String maNV, final String thoiGian, final MHREmployee prevFrame) {
		this.user = user;

		setBounds(100, 100, 310, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		Properties p = new Properties();
		p.put("Ngày", LocalDate.now().getDayOfMonth());
		p.put("Tháng", LocalDate.now().getMonth());
		p.put("Năm", LocalDate.now().getYear());

		Date editDay = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");

		try {
			editDay = sdf.parse(thoiGian);
		} catch (ParseException e3) {
			e3.printStackTrace();
		}

		this.datePickerModel_Day = new UtilDateModel(editDay);
		JDatePanelImpl datePanelImpl_Start = new JDatePanelImpl(datePickerModel_Day, p);
		JDatePickerImpl datePickerImpl_Start = new JDatePickerImpl(datePanelImpl_Start, new DateFormatter());
		datePickerImpl_Start.setLocation(149, 47);
		datePickerImpl_Start.setSize(135, 25);
		contentPanel.add(datePickerImpl_Start);
		{
			JLabel lblDayEdit = new JLabel("Ngày Công:");
			lblDayEdit.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblDayEdit.setBounds(10, 47, 129, 25);
			contentPanel.add(lblDayEdit);
		}
		{
			JLabel lblMaNV = new JLabel("Mã Nhân Viên:");
			lblMaNV.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblMaNV.setBounds(10, 11, 129, 25);
			contentPanel.add(lblMaNV);
		}

		txtMaNV = new JTextField(maNV);
		txtMaNV.setEditable(false);
		txtMaNV.setBounds(149, 11, 135, 25);
		contentPanel.add(txtMaNV);
		txtMaNV.setColumns(10);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Thay Đổi Chấm Công");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
						SimpleDateFormat sdf_Where = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date dateEdit = null;
						Date time_Where = null;

						try {
							dateEdit = sdf.parse(sdf.format(getDatePickerModel_Day().getValue()).toString());
							time_Where = sdf_Where.parse(thoiGian);

						} catch (ParseException e3) {
							e3.printStackTrace();
						}

						Connection conn = new ConnectionControl().createConnection(user.getUserName(),
								user.getPassword());
						PreparedStatement stmt = null;
						try {
							stmt = conn.prepareStatement(
									"UPDATE QLBV.CHAMCONG SET THOIGIAN = ? WHERE MANV = ? AND THOIGIAN = ?");

							stmt.setTimestamp(1, new Timestamp(dateEdit.getTime()));
							stmt.setString(2, maNV);
							stmt.setTimestamp(3, new Timestamp(time_Where.getTime()));
							stmt.executeUpdate();
							conn.close();

							JOptionPane.showMessageDialog(null, "Sửa chấm công thành công!");

						} catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Sửa chấm công thất bại!");
							try {
								conn.close();
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
						}

						dispose();
						prevFrame.getTableChamCong().fireTableDataChanged();
						prevFrame.draw_ChamCong();
						prevFrame.getTbl_ChamCong().setModel(prevFrame.getTableChamCong());
						genericStuff.resizeTable(prevFrame.getTbl_ChamCong());
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
}
