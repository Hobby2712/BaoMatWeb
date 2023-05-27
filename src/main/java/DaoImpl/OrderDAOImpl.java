package DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectDB;
import DAO.CartDAO;
import DAO.OrderDAO;
import DAO.ProductDAO;
import Entity.OrderDetails;

public class OrderDAOImpl extends ConnectDB implements OrderDAO {
	ProductDAO p = new ProductDAOImpl();
	CartDAO c = new CartDAOImpl();

	@Override
	public List<OrderDetails> getAllItemsOrder(int uId) {
		List<OrderDetails> orderlist = new ArrayList<>();
		String query = "select * \r\n" + "from orderdetail join [order]\r\n"
				+ "on orderdetail.orderId = [order].orderId\r\n" + "where [order].uId=?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, uId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderDetails order = new OrderDetails();
				order.setId(rs.getInt(1));
				order.setOrderId(rs.getInt(2));
				order.setP(p.getProductByID(rs.getString(3)));
				order.setCount(rs.getInt(4));
				order.setTotalprice(rs.getInt(5));
				order.setStatus(rs.getInt(6));
				orderlist.add(order);
			}
		} catch (Exception e) {
		}
		return orderlist;
	}

	@Override
	public OrderDetails getItemByPID(int orderId, int pid) {
		String query = "select *\r\n" + "from orderdetail \r\n" + "where productId=? and orderId=?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, pid);
			ps.setInt(2, orderId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return new OrderDetails(rs.getInt(1), rs.getInt(2), p.getProductByID(rs.getString(3)), rs.getInt(4),
						rs.getInt(5), rs.getInt(6));
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public int getOrderIdByUId(int uid) {
		String query = "select max(orderId) from [order] where [uId] = ?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
		}
		return 0;
	}

	@Override
	public int getTotalOrder(int orderId) {
		String query = "select SUM(totalPrice) from orderdetail\r\n" + "where orderId=?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
		}
		return 0;
	}

	@Override
	public int countOrderItem() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertOrderItems(int orderId, int pId, int count) {
		String query = "Insert into orderdetail values(?,?,?,null,1)";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, orderId);
			ps.setInt(2, pId);
			ps.setInt(3, count);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertOrder(int uid, String name, String phone, String address) {
		long millis = System.currentTimeMillis();
		java.sql.Date now = new java.sql.Date(millis);
		String query = "Insert into [order] values(?,?,?,?,?,?)";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, uid);
			ps.setString(2, name);
			ps.setString(3, phone);
			ps.setString(4, address);
			ps.setDate(5, now);
			ps.setInt(6, c.getTotalCart(c.getCartIdByUId(uid)));

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteItemOrder(int orderId, int pId) {
		String query = "delete from orderdetail\n" + "where productId=? and orderId=?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, pId);
			ps.setInt(2, orderId);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<OrderDetails> getAllOrder(int storeId) {
		List<OrderDetails> orderlist = new ArrayList<>();
		String query = "select *\r\n" + "from orderdetail join product\r\n"
				+ "on orderdetail.productId = product.pId\r\n" + "where product.storeId = ?\r\n"
				+ "except (select * from orderdetail join product\r\n" + "on orderdetail.productId = product.pId\r\n"
				+ "where product.storeId = ? and orderdetail.status = 5)";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, storeId);
			ps.setInt(2, storeId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderDetails order = new OrderDetails();
				order.setId(rs.getInt(1));
				order.setOrderId(rs.getInt(2));
				order.setP(p.getProductByID(rs.getString(3)));
				order.setCount(rs.getInt(4));
				order.setTotalprice(rs.getInt(5));
				order.setStatus(rs.getInt(6));
				orderlist.add(order);
			}
		} catch (Exception e) {
		}
		return orderlist;
	}

	@Override
	public List<OrderDetails> getAllOrderShipper() {
		List<OrderDetails> orderlist = new ArrayList<>();
		String query = "select *\r\n" + "from orderdetail \r\n" + "except select * from orderdetail\r\n"
				+ "where status = 5 or status = 1";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderDetails order = new OrderDetails();
				order.setId(rs.getInt(1));
				order.setOrderId(rs.getInt(2));
				order.setP(p.getProductByID(rs.getString(3)));
				order.setCount(rs.getInt(4));
				order.setTotalprice(rs.getInt(5));
				order.setStatus(rs.getInt(6));
				orderlist.add(order);
			}
		} catch (Exception e) {
		}
		return orderlist;
	}

	@Override
	public List<OrderDetails> getOrderByStatus(int status) {
		List<OrderDetails> orderlist = new ArrayList<>();
		String query = "select *\r\n" + "from orderdetail \r\n" + "where status = ?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, status);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderDetails order = new OrderDetails();
				order.setId(rs.getInt(1));
				order.setOrderId(rs.getInt(2));
				order.setP(p.getProductByID(rs.getString(3)));
				order.setCount(rs.getInt(4));
				order.setTotalprice(rs.getInt(5));
				order.setStatus(rs.getInt(6));
				orderlist.add(order);
			}
		} catch (Exception e) {
		}
		return orderlist;
	}

	@Override
	public void updateOrderStatus(int id, int status) {
		String query = "update orderdetail\r\n" + "set [status]=?\r\n" + "where id=?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, status);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		OrderDAO dao = new OrderDAOImpl();

		System.out.print(dao.getCountOrderStatus(2));
	}

	@Override
	public List<OrderDetails> getCountOrderStatus(int storeId) {
		List<OrderDetails> orderlist = new ArrayList<>();
		String query = "select status, count(*) from orderdetail\r\n" + "join product \r\n"
				+ "on orderdetail.productId = product.pId\r\n" + "where product.storeId = ?\r\n" + "group by [status]";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, storeId);
			ResultSet rs = ps.executeQuery();
			for (int i = 1; i < 9; i++) {
					OrderDetails order = new OrderDetails();
					order.setStatus(i);
					order.setCount(0);
					orderlist.add(order);
			}
			while (rs.next()) {
				for(OrderDetails list: orderlist) {
					if(list.getStatus() == rs.getInt(1)) {
						list.setCount(rs.getInt(2));
					}
				}
			}
		} catch (Exception e) {
		}
		return orderlist;
	}
	
	@Override
	public boolean updateOrderStatusApi(int id, int status) {
		String query = "update orderdetail\r\n" + "set [status]=?\r\n" + "where id=?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, status);
			ps.setInt(2, id);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
