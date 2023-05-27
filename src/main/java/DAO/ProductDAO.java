package DAO;

import java.util.List;

import Entity.Product;

public interface ProductDAO {
	public List<Product> get12RProduct();
	public List<Product> get8Product();
	public List<Product> getNext4Product(int amount);
	List<Product> get3LastestProduct();
	List<Product> get3_6LastestProduct();
	List<Product> get3BestSellerProduct();
	List<Product> get3_6BestSellerProduct();
	List<Product> getAllProduct(int index);
	public List<Product> getProductByCID(String cid);
	public List<Product> getProductByCID2(String cid);
	public Product getProductByID(String id);
	
	//search
	List<Product> searchByName(String txtSearch);
	public int countSearch(String txt);
	public List<Product> pagingSearch(String txtSearch, int index);
	public int countProduct(int storeId);
	List<Product> pagingManagerP(int index);
	public void deleteProduct(int pid);
	public void insertProduct(Product p);
	public void editProduct(Product p);
	List<Product> GetProductByStoreId(int storeId, int index);
	int countProduct();
	int countSearchByStore(int storeId, String txtSearch);
	List<Product> pagingSearchByStore(int StoreId, String txtSearch, int index);
	
	List<Product> getAllProduct();
	Product getLastestProduct(String pName);
	boolean editApiProduct(Product p);
	List<Product> searchByNameAndStore(String txtSearch, String storeId);
	List<Product> getLastestProduct();
	List<Product> getBestSeller();
}
