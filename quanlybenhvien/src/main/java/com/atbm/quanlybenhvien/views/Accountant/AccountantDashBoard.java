package com.atbm.quanlybenhvien.views.Accountant;

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
import com.atbm.quanlybenhvien.views.Login;

public class AccountantDashBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private GenericStuff genericStuff = new GenericStuff();

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
					AccountantDashBoard frame = new AccountantDashBoard(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AccountantDashBoard(final User user) {
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
		ImageIcon imageIcon_Medicines = new ImageIcon(Login.class.getResource("/images/Medicines.png"));
		Image image_Medicines = imageIcon_Medicines.getImage();
		Image newImage_Medicines = image_Medicines.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		lblIconDash.setIcon(new ImageIcon(newImage_Medicines));
		panelDash.add(lblIconDash);

		JLabel lblMnHnhChnh = new JLabel("Màn Hình Chính Nhân Viên Kế Toán");
		lblMnHnhChnh.setHorizontalAlignment(SwingConstants.CENTER);
		lblMnHnhChnh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblMnHnhChnh.setBounds(120, 11, 280, 40);
		panel_1.add(lblMnHnhChnh);
		JLabel lblChoMngNhn = new JLabel("Chào Mừng Nhân Viên: " + user.getUserName());
		lblChoMngNhn.setHorizontalAlignment(SwingConstants.CENTER);
		lblChoMngNhn.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblChoMngNhn.setBounds(175, 47, 280, 40);
		panel_1.add(lblChoMngNhn);

		JPanel panelStats = new JPanel();
		panelStats.setLayout(null);
		panelStats.setBackground(Color.LIGHT_GRAY);
		panelStats.setBounds(10, 191, 100, 116);
		panel.add(panelStats);
		JLabel lblStats = new JLabel("<html>Thống Kê Lương</html>", SwingConstants.CENTER);
		lblStats.setForeground(Color.BLACK);
		lblStats.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblStats.setBounds(0, 86, 100, 30);
		panelStats.add(lblStats);
		JLabel lblIconStats = new JLabel("");
		lblIconStats.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon imageIcon_Stats = new ImageIcon(AccountantDashBoard.class.getResource("/images/Statistic.png"));
		Image image_Stats = imageIcon_Stats.getImage();
		Image newImage_Stats = image_Stats.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconStats.setIcon(new ImageIcon(newImage_Stats));
		lblIconStats.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				AccountantSal accountantSal = new AccountantSal(user);
				genericStuff.call_frame(accountantSal);
			}
		});
		genericStuff.hover(lblIconStats, lblStats, panelStats, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		lblIconStats.setBounds(10, 0, 80, 75);
		panelStats.add(lblIconStats);

		JPanel panelBack = new JPanel();
		panelBack.setLayout(null);
		panelBack.setBackground(Color.LIGHT_GRAY);
		panelBack.setBounds(640, 251, 100, 110);
		panel.add(panelBack);
		JLabel lblBack = new JLabel("Quay Lại", SwingConstants.CENTER);
		lblBack.setForeground(Color.BLACK);
		lblBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblBack.setBounds(0, 86, 100, 14);
		JLabel lblIconBack = new JLabel();
		lblIconBack.setBounds(10, 11, 80, 80);
		panelBack.add(lblIconBack);
		ImageIcon imageIcon_Back = new ImageIcon(AccountantDashBoard.class.getResource("/images/Back.png"));
		Image image_Back = imageIcon_Back.getImage();
		Image newImage_Back = image_Back.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconBack.setIcon(new ImageIcon(newImage_Back));
		lblIconBack.setBounds(10, 11, 80, 80);
		lblIconBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				Login login = new Login();
				genericStuff.call_frame(login);
			}
		});
		genericStuff.hover(lblIconBack, lblBack, panelBack, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		panelBack.add(lblBack);

		JLabel label_13 = new JLabel("2020 Nhóm 23 - Demo Quản Lý Bệnh Viện");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_13.setBounds(206, 386, 330, 14);
		panel.add(label_13);
	}

}
