package com.atbm.quanlybenhvien.views.Seller;

import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.GenericStuff;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;

public class SellerPresDetails extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private JTable tbl_PresDetail;

	public JTable getTbl_PresDetail() {
		return tbl_PresDetail;
	}

	public void setTbl_PresDetail(JTable tbl_PresDetail) {
		this.tbl_PresDetail = tbl_PresDetail;
	}

	private DefaultTableModel tableDetailPres;

	public DefaultTableModel getTableDetailPres() {
		return tableDetailPres;
	}

	public void setTableDetailPres(DefaultTableModel tableDetailPres) {
		this.tableDetailPres = tableDetailPres;
	}

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private GenericStuff genericStuff = new GenericStuff();

	public static void main(String[] args) {
		try {
			SellerPresDetails dialog = new SellerPresDetails(new User(), new String());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SellerPresDetails(final User user, final String maKB) {
		this.user = user;

		setBounds(100, 100, 650, 400);
		getContentPane().setLayout(null);

		JScrollPane scrollPane_PresDetail = new JScrollPane();
		scrollPane_PresDetail.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_PresDetail.setBounds(10, 11, 614, 299);
		getContentPane().add(scrollPane_PresDetail);
		draw_PresDetail(maKB);
		tbl_PresDetail = new JTable(tableDetailPres);
		genericStuff.resizeTable(tbl_PresDetail);
		scrollPane_PresDetail.setViewportView(tbl_PresDetail);

		JButton btnNewButton = new JButton("Tính Chi Phí Đã Chọn");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tbl_PresDetail.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn những hàng cần cập nhật chi phí!");
				} else {
					// Không được cập nhật những dòng đã có sẵn chi phí vì đã được tính toán trước
					// khi có sự thay đổi giá thuốc
					int[] selected_rows = tbl_PresDetail.getSelectedRows();
					boolean pass = pass_TinhChiPhi(selected_rows);
					if (pass) {

						Connection conn = new ConnectionControl().createConnection(user.getUserName(),
								user.getPassword());
						PreparedStatement stmt = null;
						try {
							stmt = conn
									.prepareStatement("UPDATE QLBV.TOATHUOC SET CHIPHI = ? WHERE MAKB = ? AND MAT = ?");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

						for (int item : selected_rows) {
							try {
								stmt.setBigDecimal(1, new BigDecimal(tbl_PresDetail.getValueAt(item, 7).toString()));
								stmt.setString(2, tbl_PresDetail.getValueAt(item, 1).toString());
								stmt.setString(3, tbl_PresDetail.getValueAt(item, 2).toString());
								stmt.addBatch();
							} catch (NumberFormatException | SQLException e1) {
								e1.printStackTrace();
							}
						}

						try {
							stmt.executeBatch();
						} catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Cập nhật thông tin hóa đơn thất bại!");
						}

						try {
							conn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

						JOptionPane.showMessageDialog(null, "Cập nhật thông tin hóa đơn thành công!");
						getTableDetailPres().fireTableDataChanged();
						draw_PresDetail(maKB);
						getTbl_PresDetail().setModel(tableDetailPres);
						genericStuff.resizeTable(getTbl_PresDetail());
						genericStuff.call_revapaint(contentPanel);
					}
				}
			}
		});
		btnNewButton.setBounds(10, 321, 160, 35);
		getContentPane().add(btnNewButton);

		JButton btnTnhChiPh = new JButton("Tính Chi Phí Tất Cả");
		btnTnhChiPh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("UPDATE QLBV.TOATHUOC SET CHIPHI = ? WHERE MAKB = ? AND MAT = ?");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				for (int i = 0; i < tbl_PresDetail.getRowCount(); i++) {
					if (tbl_PresDetail.getValueAt(i, 8).toString().equals("Chưa Tính")) {
						try {
							stmt.setBigDecimal(1, new BigDecimal(tbl_PresDetail.getValueAt(i, 7).toString()));
							stmt.setString(2, tbl_PresDetail.getValueAt(i, 1).toString());
							stmt.setString(3, tbl_PresDetail.getValueAt(i, 2).toString());
							stmt.addBatch();
						} catch (NumberFormatException | SQLException e1) {
							e1.printStackTrace();
						}
					} else {
						continue;
					}
				}

				try {
					stmt.executeBatch();
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cập nhật thông tin hóa đơn thất bại!");
				}

				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, "Cập nhật thông tin hóa đơn thành công!");
				getTableDetailPres().fireTableDataChanged();
				draw_PresDetail(maKB);
				getTbl_PresDetail().setModel(tableDetailPres);
				genericStuff.resizeTable(getTbl_PresDetail());
				genericStuff.call_revapaint(contentPanel);
			}
		});
		btnTnhChiPh.setBounds(180, 321, 146, 35);
		getContentPane().add(btnTnhChiPh);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

	}

	private boolean pass_TinhChiPhi(int[] selected_rows) {
		for (int i : selected_rows) {
			if (!tbl_PresDetail.getValueAt(i, 8).toString().equals("Chưa Tính")) {
				JOptionPane.showMessageDialog(null, "Chỉ được tính toán trên những Toa Thuốc chưa được tính chi phí");
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("serial")
	private void draw_PresDetail(String maKB) {
		try {

			Connection conn = new ConnectionControl().createConnection(this.user.getUserName(),
					this.user.getPassword());
			String sql = "SELECT TOATHUOC.MAKB MAKB, TOATHUOC.MAT MAT, TOATHUOC.CHIPHI, THUOC.TEN TEN, TOATHUOC.MOTA MOTA, THUOC.GIA DONGIA, TOATHUOC.SOLUONG SOLUONG, THUOC.GIA*TOATHUOC.SOLUONG TONGGIA\r\n"
					+ "FROM QLBV.TOATHUOC\r\n" + "INNER JOIN QLBV.THUOC\r\n" + "ON TOATHUOC.MAT = THUOC.MAT\r\n"
					+ "WHERE TOATHUOC.MAKB = '" + maKB + "'";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			if (res.next() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có dữ liệu trong toa thuốc!");

			} else {
				String[] columns = { "STT", "Mã Khám Bệnh", "Mã Thuốc", "Tên Thuốc", "Mô Tả", "Giá", "Số Lượng",
						"Tổng Giá", "Chi Phí" };
				tableDetailPres = new DefaultTableModel(columns, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				int i = 1;
				do {

					String[] data = { String.valueOf(i++), res.getString("MAKB"), res.getString("MAT"),
							res.getString("TEN"), res.getString("MOTA"), res.getString("DONGIA"),
							res.getString("SOLUONG"), res.getString("TONGGIA"),
							res.getString("CHIPHI") != null ? res.getString("CHIPHI") : "Chưa Tính" };
					tableDetailPres.addRow(data);

				} while (res.next());

				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
