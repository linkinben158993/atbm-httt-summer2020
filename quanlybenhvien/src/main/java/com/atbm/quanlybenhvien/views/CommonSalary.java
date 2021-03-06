package com.atbm.quanlybenhvien.views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.util.DateFormatter;

public class CommonSalary extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane = new JPanel();

	private UtilDateModel datePickerModel_Start;

	public UtilDateModel getDatePickerModel_Start() {
		return datePickerModel_Start;
	}

	public void setDatePickerModel_Start(UtilDateModel datePickerModel_Start) {
		this.datePickerModel_Start = datePickerModel_Start;
	}

	private UtilDateModel datePickerModel_End;

	public UtilDateModel getDatePickerModel_End() {
		return datePickerModel_End;
	}

	public void setDatePickerModel_End(UtilDateModel datePickerModel_End) {
		this.datePickerModel_End = datePickerModel_End;
	}

	private JTextField txtLuongCoBan;
	private JTextField txtMaNV;
	private static JTextField txtLuong;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		try {
			CommonSalary dialog = new CommonSalary(new User());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CommonSalary(final User user) {
		this.user = user;

		setTitle("Thống Kê Lương!");
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblMaNV = new JLabel("Mã Nhân Viên:");
		lblMaNV.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblMaNV.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaNV.setBounds(10, 16, 90, 40);
		contentPane.add(lblMaNV);

		txtMaNV = new JTextField(user.getUserName());
		txtMaNV.setEditable(false);
		txtMaNV.setColumns(10);
		txtMaNV.setBounds(110, 26, 135, 20);
		contentPane.add(txtMaNV);

		JLabel lblLuongCoBan = new JLabel("Lương Cơ Bản:");
		lblLuongCoBan.setHorizontalAlignment(SwingConstants.CENTER);
		lblLuongCoBan.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblLuongCoBan.setBounds(10, 67, 90, 40);
		contentPane.add(lblLuongCoBan);
		txtLuongCoBan = new JTextField();
		try {
			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT TO_NUMBER(QLBV.FNC_DECRYPT_LUONG(MANV, LUONGCOBAN)) AS LUONGCOBAN FROM QLBV.LUONG WHERE MANV = SYS_CONTEXT('userenv','SESSION_USER') AND TRANGTHAI = 1");
			ResultSet res = stmt.executeQuery();
			if (res.next() == false) {
				txtLuongCoBan.setText("Chưa Có");
			} else {
				do {
					txtLuongCoBan.setText(res.getString("LUONGCOBAN"));
				} while (res.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		txtLuongCoBan.setEditable(false);
		txtLuongCoBan.setBounds(110, 77, 135, 20);
		txtLuongCoBan.setColumns(10);
		contentPane.add(txtLuongCoBan);

		JSeparator separator_NonEdit = new JSeparator();
		separator_NonEdit.setBounds(10, 105, 235, 2);
		contentPane.add(separator_NonEdit);

		Properties p = new Properties();
		p.put("Ngày", LocalDate.now().getDayOfMonth());
		p.put("Tháng", LocalDate.now().getMonth());
		p.put("Năm", LocalDate.now().getYear());

		Date today = new Date();

		this.datePickerModel_Start = new UtilDateModel(today);
		JDatePanelImpl datePanelImpl_Start = new JDatePanelImpl(datePickerModel_Start, p);
		JDatePickerImpl datePickerImpl_Start = new JDatePickerImpl(datePanelImpl_Start, new DateFormatter());
		datePickerImpl_Start.setLocation(110, 118);
		datePickerImpl_Start.setSize(135, 25);

		this.datePickerModel_End = new UtilDateModel(today);
		JDatePanelImpl datePanelImpl_End = new JDatePanelImpl(datePickerModel_End, p);
		JDatePickerImpl datePickerImpl_End = new JDatePickerImpl(datePanelImpl_End, new DateFormatter());
		datePickerImpl_End.setLocation(110, 154);
		datePickerImpl_End.setSize(135, 25);

		JLabel lblDayStart = new JLabel("Ngày Bắt Đầu:");
		lblDayStart.setHorizontalAlignment(SwingConstants.CENTER);
		lblDayStart.setBounds(10, 118, 90, 25);
		contentPane.add(lblDayStart);
		contentPane.add(datePickerImpl_Start);

		JLabel lblDayEnd = new JLabel("Ngày Kết Thúc:");
		lblDayEnd.setHorizontalAlignment(SwingConstants.CENTER);
		lblDayEnd.setBounds(10, 154, 90, 25);
		contentPane.add(lblDayEnd);
		contentPane.add(datePickerImpl_End);

		JButton btnCalSal = new JButton("Tính Lương");
		btnCalSal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
				txtLuong.setText(tinhLuongCuaToi(dateFormat.format(getDatePickerModel_Start().getValue()),
						dateFormat.format(getDatePickerModel_End().getValue()), user.getUserName()));
			}
		});
		btnCalSal.setBounds(0, 201, 110, 23);
		contentPane.add(btnCalSal);

		JSeparator separator_Edit = new JSeparator();
		separator_Edit.setBounds(10, 188, 235, 2);
		contentPane.add(separator_Edit);

		JLabel lblLuong = new JLabel("Lương:");
		lblLuong.setHorizontalAlignment(SwingConstants.CENTER);
		lblLuong.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblLuong.setBounds(10, 221, 90, 40);
		contentPane.add(lblLuong);

		txtLuong = new JTextField();
		txtLuong.setEditable(false);
		txtLuong.setColumns(10);
		txtLuong.setBounds(110, 231, 135, 20);
		contentPane.add(txtLuong);
	}

	private String tinhLuongCuaToi(String dateStart, String dateEnd, String maNV) {
		String luong = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
		Date dateStart_comp = null;
		Date dateEnd_comp = null;
		try {
			dateStart_comp = sdf.parse(dateStart);
			dateEnd_comp = sdf.parse(dateEnd);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		if (dateStart_comp.compareTo(dateEnd_comp) > 0) {
			JOptionPane.showMessageDialog(null, "Nhập ngày sai! Không thể tính lương!");
			return null;
		} else {
			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			try {
				String sql = "SELECT \r\n" + "ROUND(TO_NUMBER(QLBV.FNC_DECRYPT_LUONG(MANV, LUONGCOBAN))/26 * \r\n"
						+ "    (\r\n" + "    SELECT COUNT(*)\r\n" + "    FROM QLBV.CHAMCONG\r\n" + "    WHERE MANV = '"
						+ maNV + "' \r\n" + "    AND \r\n" + "    THOIGIAN BETWEEN TO_DATE('" + dateStart
						+ " 12:00 A.M.', 'DD-MM-YY HH:MI A.M.') \r\n" + "    AND \r\n" + "    TO_DATE('" + dateEnd
						+ " 11:59 P.M.', 'DD-MM-YY HH:MI A.M.')\r\n" + "    ) + PHUCAP\r\n" + ") LUONG\r\n"
						+ "FROM QLBV.LUONG\r\n" + "WHERE MANV = '" + maNV + "' AND TRANGTHAI = 1";
				PreparedStatement preparedStatement = conn.prepareStatement(sql);
				ResultSet res = preparedStatement.executeQuery();
				if (res.next() == false) {
					JOptionPane.showMessageDialog(null, "Chưa đủ dữ liệu để tính lương!");
					txtLuong.setText("");
				} else {
					luong = res.getString("LUONG");
				}
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (!conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return luong;
		}
	}
}
