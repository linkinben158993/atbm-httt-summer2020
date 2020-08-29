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
import javax.swing.JDialog;
import javax.swing.JLabel;
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
import javax.swing.JComboBox;

public class MHRAddDay extends JDialog {
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private GenericStuff genericStuff = new GenericStuff();

	private JComboBox<String> comboBox_NV;

	private UtilDateModel datePickerModel_Day;

	public UtilDateModel getDatePickerModel_Day() {
		return datePickerModel_Day;
	}

	public void setDatePickerModel_Day(UtilDateModel datePickerModel_Day) {
		this.datePickerModel_Day = datePickerModel_Day;
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
			MHRAddDay dialog = new MHRAddDay(new User(), new MHREmployee(new User()));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MHRAddDay(final User user, final MHREmployee prevFrame) {
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

		Date editDay = new Date();

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
		{
			comboBox_NV = new JComboBox<String>();
			comboBox_NV.setMaximumRowCount(5);
			comboBox_NV.setBounds(149, 11, 135, 20);
			try {

				Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
						this.user.getPassword());
				String sql = "SELECT * FROM QLBV.NHANVIEN ORDER BY MANV";
				PreparedStatement preparedStatement = conn.prepareStatement(sql);
				ResultSet res = preparedStatement.executeQuery();

				if (res.next() == false) {
					JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong nhân viên!");

				} else {
					do {
						comboBox_NV.addItem(res.getString("MANV"));
					} while (res.next());

					conn.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			contentPanel.add(comboBox_NV);
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Thêm Chấm Công");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
						Date dateAdd = null;

						try {
							dateAdd = sdf.parse(sdf.format(getDatePickerModel_Day().getValue()).toString());
						} catch (ParseException e3) {
							e3.printStackTrace();
						}

						Connection conn = new ConnectionControl().createConnection(user.getUserName(),
								user.getPassword());
						PreparedStatement stmt = null;
						try {

							stmt = conn.prepareStatement("INSERT INTO QLBV.CHAMCONG VALUES (?,?)");
							stmt.setString(1, comboBox_NV.getSelectedItem().toString());
							stmt.setTimestamp(2, new Timestamp(dateAdd.getTime()));
							stmt.executeUpdate();
							conn.close();

							JOptionPane.showMessageDialog(null, "Thêm chấm công thành công!");

						} catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Thêm chấm công thất bại!");
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
