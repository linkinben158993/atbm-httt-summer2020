package com.atbm.quanlybenhvien.views.Specialist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;

public class SpecialistService extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private SpecialistService currFrame;

	public SpecialistService getCurrFrame() {
		return currFrame;
	}

	public void setCurrFrame(SpecialistService currFrame) {
		this.currFrame = currFrame;
	}

	private GenericStuff genericStuff = new GenericStuff();

	private DefaultTableModel tablePatients;

	public DefaultTableModel getTablePatients() {
		return tablePatients;
	}

	public void setTablePatients(DefaultTableModel tablePatients) {
		this.tablePatients = tablePatients;
	}

	private JTable tbl_Patients;

	public JTable getTbl_Patients() {
		return tbl_Patients;
	}

	public void setTbl_Patients(JTable tbl_Patients) {
		this.tbl_Patients = tbl_Patients;
	}

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpecialistService frame = new SpecialistService(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SpecialistService(final User user) {
		this.user = user;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.currFrame = (SpecialistService) SwingUtilities.getWindowAncestor(contentPane);

		JScrollPane scrollPane_Patients = new JScrollPane();
		scrollPane_Patients.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_Patients.setBounds(10, 11, 550, 334);
		contentPane.add(scrollPane_Patients);
		draw_TableKhamBenh();
		tbl_Patients = new JTable(tablePatients);
		tbl_Patients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tbl_Patients);
		scrollPane_Patients.setViewportView(tbl_Patients);

		JPanel buttonPane = new JPanel();
		buttonPane.setSize(400, 33);
		buttonPane.setLocation(10, 356);
		buttonPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		getContentPane().add(buttonPane, BorderLayout.LINE_END);
		{
			JButton btnThemBN = new JButton("Thêm Bệnh Nhân");
			btnThemBN.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					SpecialistAddServices specialistAddServices = new SpecialistAddServices(user, currFrame);
					genericStuff.call_dialog(specialistAddServices);
				}
			});
			buttonPane.add(btnThemBN);
		}
		{
			JButton btnSuaBN = new JButton("Sửa Bệnh Nhân");
			btnSuaBN.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (tbl_Patients.getSelectedRow() == -1) {
						JOptionPane.showMessageDialog(null, "Vui lòng chọn điều phối dịch vụ để sửa!");
					} else {

					}
				}
			});
			buttonPane.add(btnSuaBN);
		}
		{
			JButton btnXoaBN = new JButton("Xóa Bệnh Nhân");
			btnXoaBN.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (tbl_Patients.getSelectedRow() == -1) {
						JOptionPane.showMessageDialog(null, "Vui lòng chọn điều phối dịch vụ để xóa!");
					} else {
						Connection conn = null;
						try {
							conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
							PreparedStatement stmt = conn
									.prepareStatement("DELETE FROM QLBV.DIEUPHOIDICHVU WHERE MAKB = ? AND MADV = ?");
							stmt.setString(1, tbl_Patients.getValueAt(tbl_Patients.getSelectedRow(), 1).toString());
							stmt.setString(2, tbl_Patients.getValueAt(tbl_Patients.getSelectedRow(), 3).toString());
							stmt.executeUpdate();
							conn.close();
							JOptionPane.showMessageDialog(null, "Xóa điều phối dịch vụ công!");

						} catch (Exception e2) {
							e2.printStackTrace();
							try {
								conn.close();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "Xóa điều phối dịch vụ thất bại!");
						}
					}
					getTablePatients().fireTableDataChanged();
					draw_TableKhamBenh();
					getTbl_Patients().setModel(getTablePatients());
					genericStuff.resizeTable(getTbl_Patients());
					genericStuff.call_revapaint(contentPane);
				}
			});
			buttonPane.add(btnXoaBN);
		}
		contentPane.add(buttonPane);

		JPanel panelBack = new JPanel();
		panelBack.setLayout(null);
		panelBack.setBackground(Color.LIGHT_GRAY);
		panelBack.setBounds(484, 356, 100, 110);
		contentPane.add(panelBack);
		JLabel lblBack = new JLabel("Quay Lại", SwingConstants.CENTER);
		lblBack.setForeground(Color.BLACK);
		lblBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblBack.setBounds(0, 86, 100, 14);
		JLabel lblIconBack = new JLabel();
		lblIconBack.setBounds(10, 11, 80, 80);
		panelBack.add(lblIconBack);
		ImageIcon imageIcon_Back = new ImageIcon(SpecialistService.class.getResource("/images/Back.png"));
		Image image_Back = imageIcon_Back.getImage();
		Image newImage_Back = image_Back.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconBack.setIcon(new ImageIcon(newImage_Back));
		lblIconBack.setBounds(10, 11, 80, 80);
		lblIconBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				SpecialistDashBoard specialistDashBoard = new SpecialistDashBoard(user);
				genericStuff.call_frame(specialistDashBoard);
			}
		});
		genericStuff.hover(lblIconBack, lblBack, panelBack, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		panelBack.add(lblBack);

		JLabel label_13 = new JLabel("2020 Nhóm 23 - Demo Quản Lý Bệnh Viện");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_13.setBounds(10, 452, 464, 14);
		contentPane.add(label_13);
	}

	@SuppressWarnings("serial")
	public void draw_TableKhamBenh() {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.DIEUPHOIDICHVU DPDV JOIN QLBV.KHAMBENH KB ON DPDV.MAKB = KB.MAKB ORDER BY DPDV.MAKB";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong lương!");

			} else {
				String[] columns = { "STT", "Mã KB", "Mã BN", "Mã DV", "Thời Gian", "Mã Bác Sĩ", "Mô Tả" };
				tablePatients = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					Timestamp ts = Timestamp.valueOf(res.getString("THOIGIAN"));
					LocalDateTime lcdt = ts.toLocalDateTime();

					String[] data = { String.valueOf(i++), res.getString("MAKB"), res.getString("BENHNHAN"),
							res.getString("MADV"), dateTimeFormatter.format(lcdt).toString(), res.getString("BACSI"),
							res.getString("MOTA") };
					tablePatients.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
