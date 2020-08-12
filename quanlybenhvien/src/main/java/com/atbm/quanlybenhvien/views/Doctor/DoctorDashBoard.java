package com.atbm.quanlybenhvien.views.Doctor;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.views.GenericStuff;
import com.atbm.quanlybenhvien.views.Login;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DoctorDashBoard extends JFrame {

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
					DoctorDashBoard frame = new DoctorDashBoard(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DoctorDashBoard(final User user) {
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
		ImageIcon imageIcon_Medicines = new ImageIcon(DoctorDashBoard.class.getResource("/images/Medicines.png"));
		Image image_Medicines = imageIcon_Medicines.getImage();
		Image newImage_Medicines = image_Medicines.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		lblIconDash.setIcon(new ImageIcon(newImage_Medicines));
		panelDash.add(lblIconDash);

		JLabel lblMnHnhChnh = new JLabel("Màn Hình Chính Bác Sĩ");
		lblMnHnhChnh.setHorizontalAlignment(SwingConstants.CENTER);
		lblMnHnhChnh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblMnHnhChnh.setBounds(120, 11, 280, 40);
		panel_1.add(lblMnHnhChnh);
		JLabel lblChoMngNhn = new JLabel("Chào Mừng Nhân Viên: " + user.getUserName());
		lblChoMngNhn.setHorizontalAlignment(SwingConstants.CENTER);
		lblChoMngNhn.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblChoMngNhn.setBounds(175, 47, 280, 40);
		panel_1.add(lblChoMngNhn);

		JPanel panelPatients = new JPanel();
		panelPatients.setLayout(null);
		panelPatients.setBackground(Color.LIGHT_GRAY);
		panelPatients.setBounds(10, 191, 100, 116);
		panel.add(panelPatients);
		JLabel lblPatients = new JLabel("<html>Ca Bệnh</html>", SwingConstants.CENTER);
		lblPatients.setForeground(Color.BLACK);
		lblPatients.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPatients.setBounds(0, 86, 100, 30);
		panelPatients.add(lblPatients);
		JLabel lblIconPatients = new JLabel("");
		lblIconPatients.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon imageIcon_Patients = new ImageIcon(DoctorDashBoard.class.getResource("/images/Patients.png"));
		Image image_Patients = imageIcon_Patients.getImage();
		Image newImage_Patients = image_Patients.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconPatients.setIcon(new ImageIcon(newImage_Patients));
		lblIconPatients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				DoctorPatients doctorPatients = new DoctorPatients(user);
				genericStuff.call_frame(doctorPatients);
			}
		});
		genericStuff.hover(lblIconPatients, lblPatients, panelPatients, new Color(230, 230, 250), Color.DARK_GRAY,
				Color.BLACK, Color.LIGHT_GRAY);
		lblIconPatients.setBounds(10, 0, 80, 75);
		panelPatients.add(lblIconPatients);

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
		ImageIcon imageIcon_Back = new ImageIcon(DoctorDashBoard.class.getResource("/images/Back.png"));
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
		label_13.setBounds(206, 340, 330, 14);
		panel.add(label_13);
	}
}
