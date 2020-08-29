package com.atbm.quanlybenhvien.views.DBA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MAddAuditPol extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private GenericStuff genericStuff = new GenericStuff();

	private JComboBox<String> comboBox_Tables;
	private JComboBox<String> comboBox_Actions;

	private JTable tblAuditPols;

	private DefaultTableModel tableAudit;

	public DefaultTableModel getTableAudit() {
		return tableAudit;
	}

	public void setTableAudit(DefaultTableModel tableAudit) {
		this.tableAudit = tableAudit;
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
			MAddAuditPol dialog = new MAddAuditPol(new User());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MAddAuditPol(final User user) {
		this.user = user;

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		comboBox_Tables = new JComboBox<String>();
		comboBox_Tables.setBounds(10, 45, 144, 20);
		try {
			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
			PreparedStatement stmt = conn
					.prepareStatement("SELECT * FROM DBA_TABLES WHERE OWNER = ? ORDER BY TABLE_NAME");
			stmt.setString(1, user.getUserName());
			ResultSet res = stmt.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có bảng nào trong CSDL!");

			} else {
				do {
					comboBox_Tables.addItem(res.getString("TABLE_NAME"));
				} while (res.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		{
			JLabel lblTable = new JLabel("Bảng:");
			lblTable.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblTable.setBounds(10, 20, 46, 14);
			contentPanel.add(lblTable);
		}
		comboBox_Tables.setSelectedItem(null);
		contentPanel.add(comboBox_Tables);
		{
			JLabel lblGiamSat = new JLabel("Hoạt Động:");
			lblGiamSat.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblGiamSat.setBounds(164, 20, 144, 14);
			contentPanel.add(lblGiamSat);
		}
		{
			comboBox_Actions = new JComboBox<String>();
			comboBox_Actions.setBounds(164, 45, 144, 20);
			comboBox_Actions.addItem("SELECT");
			comboBox_Actions.addItem("INSERT");
			comboBox_Actions.addItem("UPDATE");
			comboBox_Actions.addItem("DELETE");
			comboBox_Actions.setSelectedItem(null);
			contentPanel.add(comboBox_Actions);
		}
		{
			JScrollPane scrollPane_AuditPols = new JScrollPane();
			scrollPane_AuditPols.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane_AuditPols.setBounds(10, 77, 414, 140);
			contentPanel.add(scrollPane_AuditPols);
			{
				draw_TableAuditPols();
				tblAuditPols = new JTable(tableAudit);
				tblAuditPols.setAutoCreateRowSorter(true);
				genericStuff.resizeTable(tblAuditPols);
				scrollPane_AuditPols.setViewportView(tblAuditPols);
			}
		}
		{
			JButton btnAdd = new JButton("Thêm");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (comboBox_Actions.getSelectedItem() == null || comboBox_Tables.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null, "Vui lòng không để trống các trường!");
					} else {
						Connection conn = null;
						try {
							conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
							String action = comboBox_Actions.getSelectedItem().toString();
							String table = comboBox_Tables.getSelectedItem().toString();
							String audit_pol_name = "AUDIT_POL_" + action + "_" + table;
							Statement statements = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);

							statements.addBatch("CREATE AUDIT POLICY " + audit_pol_name + " ACTIONS " + action
									+ " ON QLBV." + table);
							statements.addBatch("AUDIT POLICY " + audit_pol_name);

							statements.executeBatch();

							conn.close();

							JOptionPane.showMessageDialog(null,
									"Thêm chính sách thành công! Mặc định chính sách sẽ được mở!");
						} catch (Exception e2) {
							e2.printStackTrace();
							JOptionPane.showMessageDialog(null, "Thêm chính sách thất bại!");
							try {
								conn.close();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}

						getTableAudit().fireTableDataChanged();
						draw_TableAuditPols();
						tblAuditPols.setModel(getTableAudit());
						genericStuff.resizeTable(tblAuditPols);
					}
				}
			});
			btnAdd.setBounds(318, 44, 106, 23);
			contentPanel.add(btnAdd);
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Xong");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	@SuppressWarnings("serial")
	private void draw_TableAuditPols() {
		try {
			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
			// Lấy tất cả các quyền mà các tài khoản được quyền thực hiện trên các đối tượng
			// bảng
			String sql = "SELECT * FROM AUDIT_UNIFIED_POLICIES WHERE OBJECT_SCHEMA = 'QLBV'";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet res = statement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Người dùng này hay bảng này chưa được audit!");
			} else {
				String[] columns = { "STT", "Tên Chính Sách", "Giám Sát", "Hoạt Động", "Đối Tượng" };

				tableAudit = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					;

					String[] data = { String.valueOf(i++), res.getString("POLICY_NAME"), res.getString("OBJECT_NAME"),
							res.getString("AUDIT_OPTION"), res.getString("OBJECT_TYPE") };
					tableAudit.addRow(data);

				} while (res.next());
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
