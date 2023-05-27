package DaoImpl;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Connection.ConnectDB;
import DAO.UserDAO;
import Entity.User;

public class UserDAOImpl extends ConnectDB implements UserDAO{
	
	
	@Override
	public User login(String user, String pass) {
		String query = "select * from [user]\r\n"
				+ "where uName = ? and uPassword = ?";
        try {
        	Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9));
            }
        } catch (Exception e) {
        }
        return null;
	}

	@Override
	public User checkAccountExist(String user) {
		String query = "select * from [user]\n"
                + "where uName like ?\n";
        try {
        	Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9));
            }
        } catch (Exception e) {
        }
        return null;
	}
	
	@Override
	public User checkEmailExist(String email) {
		String query = "select * from [user]\n"
                + "where uEmail like ?\n";
        try {
        	Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9));
            }
        } catch (Exception e) {
        }
        return null;
	}

	@Override
	public void singup(String user, String pass, String email) {
		long millis=System.currentTimeMillis();  
		java.sql.Date now=new java.sql.Date(millis);
		
		String query = "insert into [user]\n"
                + "values(?,null,?,null,?,null,2,null,?)";
        try {
        	Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(3, pass);
            ps.setString(2, email);
            ps.setDate(4, now);
            ps.executeUpdate();
        } catch (Exception e) {
        }
	}

	@Override
	public void sendEmail(String receiver_email, String content) {
		final String username = "duongday271202@gmail.com";//your email id
        final String password = "nrnndzlrjmudzktt";// your password
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver_email));
            message.setSubject("NKD Shop");
            message.setText("Mã OTP của bạn là: "+content);
            //out.println("Sending");
            Transport.send(message);
        } catch (Exception e) {
        }
	}

	@Override
	public String getRandom() {
		Random rd = new Random();
		int number = rd.nextInt(999999);
		return String.format("%06d", number);
	}
	
	@Override
	public User checkAccount(String username_email) {
		String query = "select * from [user]\r\n"
				+ "where uName like ? or uEmail like ?";
        try {
        	Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username_email);
            ps.setString(2, username_email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9));
            }
        } catch (Exception e) {
        }
        return null;
	}

	@Override
	public void changPass(String username_email, String pass) {
		String query = "update [user]\r\n"
				+ "set uPassword= ?\r\n"
				+ "where uName like ? or uEmail like ?";
        try {
        	Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, pass);
            ps.setString(2, username_email);
            ps.setString(3, username_email);
            ps.executeUpdate();
        } catch (Exception e) {
        }
	}

	@Override
	public void updateProfile(String name, String adress, String phone, int id) {
		String query = "update [user]\r\n"
				+ "set uFullName = ?,\r\n"
				+ "uAddress = ?,\r\n"
				+ "uPhone = ?\r\n"
				+ "where uId = ?";
        try {
        	Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, adress);
            ps.setString(3, phone);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
	}
	
	@Override
	public List<User> getAll(int index) {
		List<User> userlist = new ArrayList<>();
		String query = "select * from [user]\r\n"
				+ "order by uId OFFSET ? rows FETCH NEXT 10 ROWS Only;";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, index*10 - 10);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setFullName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setAddress(rs.getString(5));
				user.setPass(rs.getString(6));
				user.setPhone(rs.getString(7));
				user.setRole(rs.getInt(8));
				user.setImage(rs.getString(9));
				userlist.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return userlist;
	}
	
	@Override
	public void addNewUser(User user) {
		String query = "Insert into [user] values (?,?,?,?,?,?,?,?,?)";
		try {
			long millis = System.currentTimeMillis();
			java.sql.Date now = new java.sql.Date(millis);
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getFullName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getAddress());
			ps.setString(5, user.getPass());
			ps.setString(6, user.getPhone());
			ps.setInt(7, user.getRole());
			ps.setString(8, user.getImage());
			ps.setDate(9, now);
			ps.executeUpdate();
		} catch (Exception e) {

		}

	}
	
	@Override
	public int countAccount() {
		String query = "select count (*) from [user]";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
		}
		return 0;
	}
	
	@Override
	public List<User> searchByName(String txtSearch, int index) {
		List<User> userlist = new ArrayList<>();
		String query = "select * from [user]\r\n"
				+ "where uFullName like ?\r\n"
				+ "order by uId OFFSET ? rows FETCH NEXT 10 ROWS Only;";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "%" + txtSearch + "%");
			ps.setInt(2, index*10-10);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setFullName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setAddress(rs.getString(5));
				user.setPass(rs.getString(6));
				user.setPhone(rs.getString(7));
				user.setRole(rs.getInt(8));
				user.setImage(rs.getString(9));
				userlist.add(user);
			}
		} catch (Exception e) {
		}
		return userlist;
	}
	
	@Override
	public int countSearch(String txtSearch) {
		String query = "select count(*) from [user] \r\n"
				+ "where uFullName like ?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "%" + txtSearch + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
		}
		return 0;
	}
	public static void main(String[] args) {
		UserDAO u = new UserDAOImpl();
		System.out.print(u.searchByName("Dương", 1));
	}
}
