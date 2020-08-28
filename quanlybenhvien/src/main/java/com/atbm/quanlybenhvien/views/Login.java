package com.atbm.quanlybenhvien.views;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.atbm.quanlybenhvien.entity.Role;
import com.atbm.quanlybenhvien.entity.User;
import com.atbm.quanlybenhvien.util.ConnectionControl;
import com.atbm.quanlybenhvien.views.Accountant.AccountantDashBoard;
import com.atbm.quanlybenhvien.views.Busary.BusaryDashBoard;
import com.atbm.quanlybenhvien.views.DBA.DBADashBoard;
import com.atbm.quanlybenhvien.views.Doctor.DoctorDashBoard;
import com.atbm.quanlybenhvien.views.MBusary.MBusaryDashBoard;
import com.atbm.quanlybenhvien.views.Receptionist.ReceptionistDashBoard;
import com.atbm.quanlybenhvien.views.Seller.SellerDashBoard;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private GenericStuff genericStuff = new GenericStuff();

	// Đang kéo thả tại tọa độ x y
	public int draggedAtX;
	public int draggedAtY;
	private JPasswordField txtPassword;
	private JTextField txtUsername;
	private User currUser;

	public User getCurrUser() {
		return currUser;
	}

	public void setCurrUser(User currUser) {
		this.currUser = currUser;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {

		setTitle("Trang Đăng Nhập");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelTen = new JPanel();
		panelTen.setLayout(null);
		panelTen.setBackground(Color.LIGHT_GRAY);
		panelTen.setBounds(10, 10, 100, 60);
		contentPane.add(panelTen);

		JLabel lblTen = new JLabel("Tên Đăng Nhập:", SwingConstants.CENTER);
		lblTen.setForeground(Color.BLACK);
		lblTen.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblTen.setBounds(0, 20, 100, 20);
		panelTen.add(lblTen);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(10, 81, 100, 60);
		contentPane.add(panel);

		JLabel lblMtKhu = new JLabel("Mật Khẩu:", SwingConstants.CENTER);
		lblMtKhu.setForeground(Color.BLACK);
		lblMtKhu.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblMtKhu.setBounds(0, 20, 100, 20);
		panel.add(lblMtKhu);

		txtPassword = new JPasswordField();
		txtPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Validate_Login();
			}
		});
		txtPassword.setBounds(120, 95, 210, 34);
		contentPane.add(txtPassword, BorderLayout.SOUTH);

		JLabel lblCredit = new JLabel("Hệ Thống Quản Lý Bệnh Viện Demo");
		lblCredit.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblCredit.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredit.setBounds(10, 222, 314, 28);
		lblCredit.setOpaque(true);
		lblCredit.setBackground(Color.LIGHT_GRAY);
		lblCredit.setForeground(Color.BLACK);
		contentPane.add(lblCredit);

		txtUsername = new JTextField();
		txtUsername.setBounds(120, 26, 210, 34);
		txtUsername.setColumns(10);
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					if (e.getModifiers() > 0) {
						txtUsername.transferFocusBackward();
					} else {
						txtUsername.transferFocus();
					}
					e.consume();
				}
			}
		});
		contentPane.add(txtUsername, BorderLayout.NORTH);

		JPanel panelLogin = new JPanel();
		panelLogin.setLayout(null);
		panelLogin.setBackground(Color.LIGHT_GRAY);
		panelLogin.setBounds(334, 151, 100, 110);
		contentPane.add(panelLogin);
		JLabel lblLogin = new JLabel("Đăng Nhập", SwingConstants.CENTER);
		lblLogin.setForeground(Color.BLACK);
		lblLogin.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblLogin.setBounds(0, 86, 100, 14);
		panelLogin.add(lblLogin);
		JLabel lblIconLogin = new JLabel();
		ImageIcon imageIcon_Login = new ImageIcon(Login.class.getResource("/images/Login.png"));
		Image image_Login = imageIcon_Login.getImage();
		Image newImage_Login = image_Login.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		lblIconLogin.setIcon(new ImageIcon(newImage_Login));
		lblIconLogin.setBounds(10, 11, 80, 80);
		lblIconLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Validate_Login();
			}
		});
		genericStuff.hover(lblIconLogin, lblLogin, panelLogin, new Color(230, 230, 250), Color.DARK_GRAY, Color.BLACK,
				Color.LIGHT_GRAY);
		panelLogin.add(lblIconLogin, BorderLayout.SOUTH);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				draggedAtX = e.getX();
				draggedAtY = e.getY();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - draggedAtX, y - draggedAtY);
			}
		});
	}

	// Chuyển Frame nếu đăng nhập thành công về các trang thuộc quyền của người
	// dùng.
	private void Validate_Login() {
		try {
			boolean pass = Authenticate();
			// Người dùng tồn tại trên hệ thống
			if (pass) {
				JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
				dispose();
				currUser = new User();
				currUser.setUserName(txtUsername.getText());
				currUser.setPassword(new String(txtPassword.getPassword()));
				String redirectTo = isRole(getCurrUser(), Role.ROLE_LIST);
				currUser.setRole(redirectTo);
				// Người dùng tồn tại trên hệ thống nhưng chưa được phân quyền
				if (redirectTo == null) {
					JOptionPane.showMessageDialog(null,
							"Người dùng chưa được phân quyền trên hệ thống! Vui lòng thử lại sau!");
					Login login = new Login();
					genericStuff.call_frame(login);
				} else {
					switch (redirectTo) {
					// DBA
					case "DBA":
						System.out.println("Quản lý csdl.");
						genericStuff.call_frame(new DBADashBoard(currUser));
						break;
					// ROLE_BACSI
					case "ROLE_BACSI":
						System.out.println("Bác sĩ.");
						genericStuff.call_frame(new DoctorDashBoard(currUser));
						break;
					// ROLE_NVBANTHUOC
					case "ROLE_NVBANTHUOC":
						System.out.println("Bán thuốc.");
						genericStuff.call_frame(new SellerDashBoard(currUser));
						break;
					// ROLE_NVKETOAN
					case "ROLE_NVKETOAN":
						System.out.println("Kế toán.");
						genericStuff.call_frame(new AccountantDashBoard(currUser));
						break;
					// ROLE_NVTAIVU
					case "ROLE_NVTAIVU":
						System.out.println("Nhân viên tài vụ.");
						genericStuff.call_frame(new BusaryDashBoard(currUser));
						break;
					// ROLE_NVTIEPTANDIEUPHOI
					case "ROLE_NVTIEPTANDIEUPHOI":
						System.out.println("Nhân viên tiếp tân.");
						genericStuff.call_frame(new ReceptionistDashBoard(currUser));
						break;
					// ROLE_QLTAINGUYENNHANSU
					case "ROLE_QLTAINGUYENNHANSU":
						System.out.println("Nhân viên quản lý tài nguyên nhân sự.");
						break;
					// ROLE_QLTAIVU
					case "ROLE_QLTAIVU":
						System.out.println("Nhân viên quản lý tài vụ.");
						genericStuff.call_frame(new MBusaryDashBoard(currUser));
						break;
					// ROLE_QLCHUYEMON
					case "ROLE_QLCHUYEMON":
						System.out.println("Nhóm quản lý chuyên môn.");
						break;

					default:
						System.out.println("Không phải người trong hệ thống hoặc chưa được cấp quyền truy cập.");
						JOptionPane.showMessageDialog(null,
								"Không phải người trong hệ thống hoặc chưa được cấp quyền truy cập.");
						break;
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Đăng nhập thất bại!");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Đăng nhập thất bại!");
		}
	}

	// Tạo kết nối tới cơ sở dữ liệu được tạo dưới tên đăng nhập và mật khẩu, trả về
	// true nếu tồn tại tài khoản và ngược lại
	private boolean Authenticate() throws SQLException {
		try {
			Connection conn = new ConnectionControl().createConnection(txtUsername.getText(),
					new String(txtPassword.getPassword()));
			if (conn != null) {
				conn.close();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Trả về quyền củ người dùng hiện tại
	private String isRole(User user, List<String> role) {
		try {
			Connection conn = new ConnectionControl().createConnection(user.getUserName(), user.getPassword());

			for (String item : role) {
				// Check quyền của người dùng test là bất kì set trong class role
				String sql_Role = "SELECT SYS_CONTEXT('SYS_SESSION_ROLES', '" + item + "') FROM DUAL";
				PreparedStatement statement_Role = conn.prepareStatement(sql_Role);
				ResultSet res_Role = statement_Role.executeQuery();
				// True hay false đối với các quyền đã set. Admin thì giữ mọi quyền nên sẽ true
				// ở mọi quyền và vẫn dẫn về trang admin
				if (res_Role.next()) {
					if (Boolean.parseBoolean(res_Role.getString(1))) {
						return item;
					} else {
						continue;
					}
				}
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
