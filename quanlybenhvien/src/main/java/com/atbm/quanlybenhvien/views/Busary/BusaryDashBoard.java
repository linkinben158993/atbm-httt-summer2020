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
import com.atbm.quanlybenhvien.views.CommonSalary;
import com.atbm.quanlybenhvien.views.GenericStuff;
import com.atbm.quanlybenhvien.views.Login;
import com.atbm.quanlybenhvien.views.Doctor.DoctorDashBoard;

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
		panelServiceStats.setBounds(120, 191, 100, 116);
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
				BusaryServiceStats busaryServiceStats = new BusaryServiceStats(user);
				genericStuff.call_dialog(busaryServiceStats);
			}
		});
		genericStuff.hover(lblIconServiceStats, lblServiceStats, panelServiceStats, new Color(230, 230, 250),
				Color.DARK_GRAY, Color.BLACK, Color.LIGHT_GRAY);
		lblIconServiceStats.setBounds(10, 0, 80, 75);
		panelServiceStats.add(lblIconServiceStats);

		JPanel panelSal = new JPanel();
		panelSal.setLayout(null);
		panelSal.setBackground(Color.LIGHT_GRAY);
		panelSal.setBounds(233, 191, 100, 116);
		panel.add(panelSal);
		JLabel lblSal = new JLabel("<html><center>Thống Kê Lương</center></html>", SwingConstants.CENTER);
		lblSal.setForeground(Color.BLACK);
		lblSal.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblSal.setBounds(0, 86, 100, 30);
		panelSal.add(lblSal);
		JLabel lblIconSal = new JLabel("");
		lblIconSal.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon imageIcon_Sal = new ImageIcon(DoctorDashBoard.class.getResource("/images/Money.png"));
		Image image_Sal = imageIcon_Sal.getImage();
		Image newImage_Sal = image_Sal.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconSal.setIcon(new ImageIcon(newImage_Sal));
		lblIconSal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CommonSalary commonSalary = new CommonSalary(user);
				genericStuff.call_dialog(commonSalary);
			}
		});
		genericStuff.hover(lblIconSal, lblIconSal, panelSal, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		lblIconSal.setBounds(10, 0, 80, 75);
		panelSal.add(lblIconSal);

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
		ImageIcon imageIcon_Back = new ImageIcon(BusaryDashBoard.class.getResource("/images/Back.png"));
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
