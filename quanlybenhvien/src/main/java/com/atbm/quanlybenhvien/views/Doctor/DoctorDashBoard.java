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
import javax.swing.JButton;
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelBack = new JPanel();
		panelBack.setLayout(null);
		panelBack.setBackground(Color.LIGHT_GRAY);
		panelBack.setBounds(344, 176, 100, 110);
		contentPane.add(panelBack);

		JLabel lblBack = new JLabel("Quay Lại", SwingConstants.CENTER);
		lblBack.setForeground(Color.BLACK);
		lblBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblBack.setBounds(0, 86, 100, 14);
		panelBack.add(lblBack);

		JLabel lblIconBack = new JLabel();
		lblIconBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				genericStuff.call_frame(new Login());
			}
		});
		lblIconBack.setBounds(10, 11, 80, 80);
		ImageIcon imgIcon_Back = new ImageIcon(DoctorDashBoard.class.getResource("/images/Back.png"));
		Image image_Back = imgIcon_Back.getImage();
		Image newImage_Back = image_Back.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconBack.setIcon(new ImageIcon(newImage_Back));

		panelBack.add(lblIconBack);

		JButton btnNewButton = new JButton("Test Button");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(355, 142, 89, 23);
		contentPane.add(btnNewButton);
	}
}
