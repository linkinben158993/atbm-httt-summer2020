package com.atbm.quanlybenhvien.views.MBusary;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;
import com.atbm.quanlybenhvien.views.Busary.BusaryDashBoard;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MBusaryMeds extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private MBusaryMeds currFrame;

	private GenericStuff genericStuff = new GenericStuff();

	private JTable tbl_Meds;

	public JTable getTbl_Meds() {
		return tbl_Meds;
	}

	public void setTbl_Meds(JTable tbl_Meds) {
		this.tbl_Meds = tbl_Meds;
	}

	private DefaultTableModel tableMeds;

	public DefaultTableModel getTableMeds() {
		return tableMeds;
	}

	public void setTableMeds(DefaultTableModel tableMeds) {
		this.tableMeds = tableMeds;
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
					MBusaryMeds frame = new MBusaryMeds(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MBusaryMeds(final User user) {
		this.user = user;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.currFrame = (MBusaryMeds) SwingUtilities.getWindowAncestor(contentPane);

		JScrollPane scrollPane_Meds = new JScrollPane();
		scrollPane_Meds.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_Meds.setBounds(10, 11, 750, 335);
		contentPane.add(scrollPane_Meds);

		draw_TableMeds();
		tbl_Meds = new JTable(tableMeds);
		tbl_Meds.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genericStuff.resizeTable(tbl_Meds);
		scrollPane_Meds.setViewportView(tbl_Meds);

		JPanel panelBack = new JPanel();
		panelBack.setLayout(null);
		panelBack.setBackground(Color.LIGHT_GRAY);
		panelBack.setBounds(784, 261, 100, 110);
		contentPane.add(panelBack);
		JLabel lblBack = new JLabel("Quay Lại", SwingConstants.CENTER);
		lblBack.setForeground(Color.BLACK);
		lblBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblBack.setBounds(0, 86, 100, 14);
		JLabel lblIconBack = new JLabel();
		lblIconBack.setBounds(10, 11, 80, 80);
		panelBack.add(lblIconBack);
		ImageIcon imageIcon_Back = new ImageIcon(BusaryDashBoard.class.getResource("/images/Back.png"));
		Image image_Back = imageIcon_Back.getImage();
		Image newImage_Back = image_Back.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconBack.setIcon(new ImageIcon(newImage_Back));
		lblIconBack.setBounds(10, 11, 80, 80);
		lblIconBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				MBusaryDashBoard mBusaryDashBoard = new MBusaryDashBoard(user);
				genericStuff.call_frame(mBusaryDashBoard);
			}
		});
		genericStuff.hover(lblIconBack, lblBack, panelBack, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		panelBack.add(lblBack);

		JLabel label_13 = new JLabel("2020 Nhóm 23 - Demo Quản Lý Bệnh Viện");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_13.setBounds(200, 357, 330, 14);
		contentPane.add(label_13);

		JButton btnAddMeds = new JButton("Thêm Thuốc");
		btnAddMeds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MBusaryAddMeds mBusaryAddMeds = new MBusaryAddMeds(user, currFrame);
				genericStuff.call_dialog(mBusaryAddMeds);
			}
		});
		btnAddMeds.setBounds(770, 11, 114, 23);
		contentPane.add(btnAddMeds);

		JButton btnEditMeds = new JButton("Sửa Thuốc");
		btnEditMeds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbl_Meds.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn toa thuốc để chỉnh sửa!");
				} else {
					MBusaryEditMed mBusaryEditMed = new MBusaryEditMed(user, currFrame,
							tbl_Meds.getValueAt(tbl_Meds.getSelectedRow(), 1).toString(),
							tbl_Meds.getValueAt(tbl_Meds.getSelectedRow(), 4).toString(),
							tbl_Meds.getValueAt(tbl_Meds.getSelectedRow(), 7).toString(),
							tbl_Meds.getValueAt(tbl_Meds.getSelectedRow(), 6).toString());
					genericStuff.call_dialog(mBusaryEditMed);
				}
			}
		});
		btnEditMeds.setBounds(770, 45, 114, 23);
		contentPane.add(btnEditMeds);

		JButton btnDeleteMeds = new JButton("Xóa Thuốc");
		btnDeleteMeds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbl_Meds.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn toa thuốc để chỉnh sửa!");
				} else {
					Connection conn = null;
					try {
						conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
						PreparedStatement stmt = conn
								.prepareStatement("DELETE FROM QLBV.TOATHUOC WHERE MAKB = ? AND MAT = ?");
						stmt.setString(1, tbl_Meds.getValueAt(tbl_Meds.getSelectedRow(), 1).toString());
						stmt.setString(2, tbl_Meds.getValueAt(tbl_Meds.getSelectedRow(), 4).toString());
						stmt.executeUpdate();
						conn.close();
						JOptionPane.showMessageDialog(null, "Xóa toa thuốc thành công!");

					} catch (Exception e2) {
						e2.printStackTrace();
						try {
							conn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Xóa toa thuốc thất bại!");
					}
				}
				getTableMeds().fireTableDataChanged();
				draw_TableMeds();
				getTbl_Meds().setModel(getTableMeds());
				genericStuff.resizeTable(getTbl_Meds());
				genericStuff.call_revapaint(contentPane);
			}
		});
		btnDeleteMeds.setBounds(770, 79, 114, 23);
		contentPane.add(btnDeleteMeds);
	}

	@SuppressWarnings("serial")
	public void draw_TableMeds() {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT * FROM QLBV.TOATHUOC TT " + "JOIN QLBV.KHAMBENH KB ON TT.MAKB = KB.MAKB "
					+ "JOIN QLBV.BENHNHAN BN ON KB.BENHNHAN = BN.MABN " + "JOIN QLBV.THUOC T ON TT.MAT = T.MAT "
					+ "ORDER BY TT.MAKB";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong lương!");

			} else {
				String[] columns = { "STT", "Mã KB", "Họ Tên", "Triệu Chứng", "Mã Thuốc", "Tên", "Số Lượng", "Mô Tả" };
				tableMeds = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					String[] data = { String.valueOf(i++), res.getString("MAKB"), res.getString("HOTEN"),
							res.getString("TRIEUCHUNG"), res.getString("MAT"), res.getString("TEN"),
							res.getString("SOLUONG"), res.getString("MOTA") };
					tableMeds.addRow(data);
				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
