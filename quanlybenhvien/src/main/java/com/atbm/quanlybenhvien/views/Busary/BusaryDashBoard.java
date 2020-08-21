package com.atbm.quanlybenhvien.views.Busary;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.views.GenericStuff;

public class BusaryDashBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private GenericStuff genericStuff = new GenericStuff();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BusaryDashBoard frame = new BusaryDashBoard(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BusaryDashBoard(final User user) {
		this.user = user;

		setTitle("Trang Điều Hướng");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 750, 400);
		contentPane.add(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(0, 0, 750, 180);
		panel.add(panel_1);

		JPanel panelDash = new JPanel();
		panelDash.setBounds(10, 11, 100, 100);
		panel_1.add(panelDash);
		panelDash.setLayout(null);
		JLabel lblIconDash = new JLabel("");
		lblIconDash.setOpaque(true);
		lblIconDash.setBackground(Color.GRAY);
		lblIconDash.setBounds(0, 0, 100, 100);
		ImageIcon imageIcon_Finance = new ImageIcon(BusaryDashBoard.class.getResource("/images/Finance.png"));
		Image image_Finance = imageIcon_Finance.getImage();
		Image newImage_Finace = image_Finance.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		lblIconDash.setIcon(new ImageIcon(newImage_Finace));
		panelDash.add(lblIconDash);

		JLabel lblMnHnhChnh = new JLabel("Màn Hình Chính Nhân Viên Tài Vụ");
		lblMnHnhChnh.setHorizontalAlignment(SwingConstants.CENTER);
		lblMnHnhChnh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblMnHnhChnh.setBounds(120, 11, 280, 40);
		panel_1.add(lblMnHnhChnh);
		JLabel lblChoMngNhn = new JLabel("Chào Mừng Nhân Viên: " + user.getUserName());
		lblChoMngNhn.setHorizontalAlignment(SwingConstants.CENTER);
		lblChoMngNhn.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblChoMngNhn.setBounds(175, 47, 280, 40);
		panel_1.add(lblChoMngNhn);

		JPanel panelPatsStats = new JPanel();
		panelPatsStats.setLayout(null);
		panelPatsStats.setBackground(Color.LIGHT_GRAY);
		panelPatsStats.setBounds(10, 191, 100, 116);
		panel.add(panelPatsStats);
		JLabel lblPatsStats = new JLabel("<html><center>Thống Kê Bệnh Nhân</center></html>", SwingConstants.CENTER);
		lblPatsStats.setForeground(Color.BLACK);
		lblPatsStats.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPatsStats.setBounds(0, 86, 100, 30);
		panelPatsStats.add(lblPatsStats);
		JLabel lblIconPatsStats = new JLabel("");
		lblIconPatsStats.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon imageIcon_PatsStats = new ImageIcon(BusaryDashBoard.class.getResource("/images/PatientsStats.png"));
		Image image_PatsStats = imageIcon_PatsStats.getImage();
		Image newImage_PatsStats = image_PatsStats.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconPatsStats.setIcon(new ImageIcon(newImage_PatsStats));
		lblIconPatsStats.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				BusaryPatientsStats busaryPatientsStats = new BusaryPatientsStats(user);
				genericStuff.call_frame(busaryPatientsStats);
			}
		});
		genericStuff.hover(lblIconPatsStats, lblPatsStats, panelPatsStats, new Color(230, 230, 250), Color.DARK_GRAY,
				Color.BLACK, Color.LIGHT_GRAY);
		lblIconPatsStats.setBounds(10, 0, 80, 75);
		panelPatsStats.add(lblIconPatsStats);

		JPanel panelServiceStats = new JPanel();
		panelServiceStats.setLayout(null);
		panelServiceStats.setBackground(Color.LIGHT_GRAY);
		panelServiceStats.setBounds(131, 191, 100, 116);
		panel.add(panelServiceStats);
		JLabel lblServiceStats = new JLabel("<html><center>Thống Kê Dịch Vụ</center></html>", SwingConstants.CENTER);
		lblServiceStats.setForeground(Color.BLACK);
		lblServiceStats.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblServiceStats.setBounds(0, 86, 100, 30);
		panelServiceStats.add(lblServiceStats);
		JLabel lblIconServiceStats = new JLabel("");
		lblIconServiceStats.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon imageIcon_ServiceStats = new ImageIcon(BusaryDashBoard.class.getResource("/images/Service.png"));
		Image image_ServiceStats = imageIcon_ServiceStats.getImage();
		Image newImage_ServiceStats = image_ServiceStats.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconServiceStats.setIcon(new ImageIcon(newImage_ServiceStats));
		lblIconServiceStats.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				BusaryServiceStats busaryServiceStats = new BusaryServiceStats(user);
				genericStuff.call_frame(busaryServiceStats);
			}
		});
		genericStuff.hover(lblIconServiceStats, lblServiceStats, panelServiceStats, new Color(230, 230, 250),
				Color.DARK_GRAY, Color.BLACK, Color.LIGHT_GRAY);
		lblIconServiceStats.setBounds(10, 0, 80, 75);
		panelServiceStats.add(lblIconServiceStats);
	}

}