package com.atbm.quanlybenhvien.views.Busary;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.views.GenericStuff;

public class BusaryServiceStats extends JFrame {

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
					BusaryServiceStats frame = new BusaryServiceStats(new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BusaryServiceStats(final User user) {
		this.user = user;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}