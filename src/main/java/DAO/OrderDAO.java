package DAO;

import java.util.List;

import Entity.OrderDetails;


public interface OrderDAO {
	public List<OrderDetails> getAllItemsOrder(int uId);
	public List<OrderDetails> getAllOrder(int storeId);
	public OrderDetails getItemByPID(int orderId, int pid);
	public int getOrderIdByUId(int uid);
	public int getTotalOrder(int orderId);
	
	public int countOrderItem();
	void insertOrder(int uid,String name,String phone,String address);
	public void deleteItemOrder(int orderId, int pId);
	void insertOrderItems(int orderId, int pId, int count);
	void updateOrderStatus(int id, int status);
	List<OrderDetails> getAllOrderShipper();
	List<OrderDetails> getOrderByStatus(int status);
	List<OrderDetails> getCountOrderStatus(int storeId);
	boolean updateOrderStatusApi(int id, int status);
}
