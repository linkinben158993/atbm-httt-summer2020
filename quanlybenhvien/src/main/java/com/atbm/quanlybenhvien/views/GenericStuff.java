package com.atbm.quanlybenhvien.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class GenericStuff extends JFrame {
	private static final long serialVersionUID = 1L;

	public int draggedAtX;
	public int draggedAtY;

	public GenericStuff() {

	}

	// Khi có component được render động thì gọi hàm để repaint lại
	public void call_revapaint(Component component) {
		// component.validate();
		component.invalidate();
		component.revalidate();
		component.repaint();
	}

	// Gọi show JFrame
	public void call_frame(JFrame frame) {
		frame.setLocationRelativeTo(null);
		// frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	// Gọi show JDialog
	public void call_dialog(JDialog jDialog) {
		jDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		jDialog.setLocationRelativeTo(null);
		jDialog.setVisible(true);
	}

	// Gọi chung nếu muốn sử dụng hover cho nút custom bằng panel
	public void hover(JLabel label, final JLabel innerlabel, final JPanel jPanel, final Color colorHoverInner,
			final Color colorHoverPanel, final Color colorAfterInner, final Color colorAfterPanel) {
		label.addMouseListener(new MouseListener() {

			public void mouseExited(MouseEvent e) {
				innerlabel.setForeground(colorAfterInner);
				jPanel.setBackground(colorAfterPanel);
			}

			public void mouseEntered(MouseEvent e) {
				innerlabel.setForeground(colorHoverInner);
				jPanel.setBackground(colorHoverPanel);
			}

			public void mouseClicked(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}
		});
	}

	// Gọi chung nếu muốn tạo hộp thoại confirm.
	@SuppressWarnings("static-access")
	public int confirmDialog(String message, String title, String yes_opt, String no_opt, String default_opt) {
		JOptionPane confirm = new JOptionPane();
		int res = confirm.showOptionDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, new String[] { yes_opt, no_opt }, default_opt);
		dispose();
		return res;
	}

	public JFileChooser chooseFile(String message) {
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma-separated Values", "csv");
		fileChooser.setFileFilter(filter);
		fileChooser.setCurrentDirectory(new File("."));
		fileChooser.setDialogTitle("Chọn File CSV Để Thêm Mới Sinh Viên");
		return fileChooser;
	}

	public void resizeTable(JTable table) {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int column = 0; column < table.getColumnCount(); column++) {
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
			int preferredWidth = tableColumn.getMinWidth();
			int maxWidth = tableColumn.getMaxWidth();

			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
				Component c = table.prepareRenderer(cellRenderer, row, column);
				int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
				preferredWidth = Math.max(preferredWidth, width);

				if (preferredWidth >= maxWidth) {
					preferredWidth = maxWidth;
					break;
				}
			}
			tableColumn.setPreferredWidth(preferredWidth - 10);

			tableColumn.setCellRenderer(centerRenderer);
		}
	}
}
