package com.atbm.quanlybenhvien.views.DBA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
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
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class MAuditPolicy extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private GenericStuff genericStuff = new GenericStuff();

	private MAuditPolicy currFrame;

	private JTable tblAuditPol;

	public JTable getTblAuditPol() {
		return tblAuditPol;
	}

	public void setTblAuditPol(JTable tblAuditPol) {
		this.tblAuditPol = tblAuditPol;
	}

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
			MAuditPolicy dialog = new MAuditPolicy(new User());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MAuditPolicy(final User user) {
		this.user = user;

		setBounds(100, 100, 500, 380);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		this.currFrame = (MAuditPolicy) SwingUtilities.getWindowAncestor(contentPanel);

		JScrollPane scrollPane_AuditPol = new JScrollPane();
		scrollPane_AuditPol.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_AuditPol.setBounds(10, 80, 464, 217);
		contentPanel.add(scrollPane_AuditPol);

		draw_TableAuditPols();
		tblAuditPol = new JTable(tableAudit);
		tblAuditPol.setAutoCreateRowSorter(true);
		genericStuff.resizeTable(tblAuditPol);
		scrollPane_AuditPol.setViewportView(tblAuditPol);

		JButton btnAddPol = new JButton("<html><center>Thêm Mới<br>Chính Sách</center></html>");
		btnAddPol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MAddAuditPol mAddAuditPol = new MAddAuditPol(user, currFrame);
				genericStuff.call_dialog(mAddAuditPol);
			}
		});
		btnAddPol.setBounds(10, 11, 101, 58);
		contentPanel.add(btnAddPol);

		JButton btnDisable = new JButton("<html><center>Ngưng<br>Chính Sách</center></html>");
		btnDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblAuditPol.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn chính sách để ngưng!");
				} else {
					Connection conn = null;
					try {
						conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
						PreparedStatement stmt = conn.prepareStatement(
								"NOAUDIT POLICY " + tblAuditPol.getValueAt(tblAuditPol.getSelectedRow(), 2).toString());
						stmt.executeUpdate();
						conn.close();

						JOptionPane.showMessageDialog(null, "Ngưng chính sách thành công!");
					} catch (Exception e2) {
						e2.printStackTrace();
						JOptionPane.showMessageDialog(null, "Ngưng chính sách thất bại!");
					}

					getTableAudit().fireTableDataChanged();
					draw_TableAuditPols();
					tblAuditPol.setModel(getTableAudit());
					genericStuff.resizeTable(tblAuditPol);
				}
			}
		});
		btnDisable.setBounds(121, 11, 101, 58);
		contentPanel.add(btnDisable);

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
	public void draw_TableAuditPols() {
		try {
			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
			// Lấy tất cả các quyền mà các tài khoản được quyền thực hiện trên các đối tượng
			// bảng
			String sql = "SELECT * FROM AUDIT_UNIFIED_ENABLED_POLICIES";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet res = statement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Người dùng này hay bảng này chưa được audit!");
			} else {
				String[] columns = { "STT", "Đối Tượng Giám Sát", "Tên Chính Sách", "Đối Tượng" };

				tableAudit = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {
					;

					String[] data = { String.valueOf(i++), res.getString("USER_NAME"), res.getString("POLICY_NAME"),
							res.getString("ENTITY_NAME") };
					tableAudit.addRow(data);

				} while (res.next());
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
