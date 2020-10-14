package com.atbm.quanlybenhvien.views.MBusary;

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

public class MBusaryDashBoard extends JFrame {

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
					MBusaryDashBoard frame = new MBusaryDashBoard(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MBusaryDashBoard(final User user) {
		this.user = user;

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
		ImageIcon imageIcon_Medicines = new ImageIcon(MBusaryDashBoard.class.getResource("/images/Manager.png"));
		Image image_Medicines = imageIcon_Medicines.getImage();
		Image newImage_Medicines = image_Medicines.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		lblIconDash.setIcon(new ImageIcon(newImage_Medicines));
		panelDash.add(lblIconDash);

		JLabel lblMnHnhChnh = new JLabel("Màn Hình Chính Quản Lý Tài Vụ");
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
		JLabel lblPatsStats = new JLabel("<html><center>Khám Bệnh<br>Bệnh Nhân</center></html>", SwingConstants.CENTER);
		lblPatsStats.setForeground(Color.BLACK);
		lblPatsStats.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPatsStats.setBounds(0, 86, 100, 30);
		panelPatsStats.add(lblPatsStats);
		JLabel lblIconPatsStats = new JLabel("");
		lblIconPatsStats.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon imageIcon_PatsStats = new ImageIcon(MBusaryDashBoard.class.getResource("/images/PatientsStats.png"));
		Image image_PatsStats = imageIcon_PatsStats.getImage();
		Image newImage_PatsStats = image_PatsStats.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconPatsStats.setIcon(new ImageIcon(newImage_PatsStats));
		lblIconPatsStats.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				MBusaryPatients mBusaryPatients = new MBusaryPatients(user);
				genericStuff.call_frame(mBusaryPatients);
			}
		});
		genericStuff.hover(lblIconPatsStats, lblPatsStats, panelPatsStats, new Color(230, 230, 250), Color.DARK_GRAY,
				Color.BLACK, Color.LIGHT_GRAY);
		lblIconPatsStats.setBounds(10, 0, 80, 75);
		panelPatsStats.add(lblIconPatsStats);

		JPanel panelMeds = new JPanel();
		panelMeds.setLayout(null);
		panelMeds.setBackground(Color.LIGHT_GRAY);
		panelMeds.setBounds(120, 191, 100, 116);
		panel.add(panelMeds);
		JLabel lblMeds = new JLabel("<html>Khám Bệnh<br>Toa Thuốc</html>", SwingConstants.CENTER);
		lblMeds.setForeground(Color.BLACK);
		lblMeds.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblMeds.setBounds(0, 86, 100, 30);
		panelMeds.add(lblMeds);
		JLabel lblIconMeds = new JLabel("");
		lblIconMeds.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon imageIcon_Stats = new ImageIcon(Login.class.getResource("/images/Medicines.png"));
		Image image_Stats = imageIcon_Stats.getImage();
		Image newImage_Stats = image_Stats.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconMeds.setIcon(new ImageIcon(newImage_Stats));
		lblIconMeds.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				MBusaryMeds busaryMeds = new MBusaryMeds(user);
				genericStuff.call_frame(busaryMeds);
			}
		});
		genericStuff.hover(lblIconMeds, lblMeds, panelMeds, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		lblIconMeds.setBounds(10, 0, 80, 75);
		panelMeds.add(lblIconMeds);

		JPanel panelServ = new JPanel();
		panelServ.setLayout(null);
		panelServ.setBackground(Color.LIGHT_GRAY);
		panelServ.setBounds(230, 191, 100, 116);
		panel.add(panelServ);
		JLabel lblServ = new JLabel("<html>Điều Phối<br>Dịch Vụ</html>", SwingConstants.CENTER);
		lblServ.setForeground(Color.BLACK);
		lblServ.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblServ.setBounds(0, 86, 100, 30);
		panelServ.add(lblServ);
		JLabel lblIconServ = new JLabel("");
		lblIconServ.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon imageIcon_Serv = new ImageIcon(MBusaryDashBoard.class.getResource("/images/Service.png"));
		Image image_Serv = imageIcon_Serv.getImage();
		Image newImage_Serv = image_Serv.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconServ.setIcon(new ImageIcon(newImage_Serv));
		lblIconServ.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				MBusaryService mBusaryService = new MBusaryService(user);
				genericStuff.call_frame(mBusaryService);
			}
		});
		genericStuff.hover(lblIconServ, lblServ, panelServ, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		lblIconServ.setBounds(10, 0, 80, 75);
		panelServ.add(lblIconServ);

		JPanel panelSal = new JPanel();
		panelSal.setLayout(null);
		panelSal.setBackground(Color.LIGHT_GRAY);
		panelSal.setBounds(340, 191, 100, 116);
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
		ImageIcon imageIcon_Back = new ImageIcon(MBusaryDashBoard.class.getResource("/images/Back.png"));
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