package DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectDB;
import DAO.ProductDAO;
import Entity.Product;

public class ProductDAOImpl extends ConnectDB implements ProductDAO {

	@Override
	public List<Product> getAllProduct(int index) {
		List<Product> plist = new ArrayList<>();
		String query = "select * from product \r\n" + "order by pId OFFSET ? rows FETCH NEXT 9 ROWS Only;";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, index * 9 - 9);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
		}
		return plist;
	}

	@Override
	public List<Product> get12RProduct() {
		List<Product> plist = new ArrayList<Product>();
		String query = "SELECT Top 12 * FROM product \r\n";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plist;
	}

	@Override
	public List<Product> get3LastestProduct() {
		List<Product> plist = new ArrayList<Product>();
		String query = "SELECT Top 3 * FROM product \r\n" + "order by pId DESC";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plist;
	}

	@Override
	public List<Product> get3_6LastestProduct() {
		List<Product> plist = new ArrayList<Product>();
		String query = "SELECT Top 3 * FROM (SELECT Top 6 * FROM product order by pId DESC) a order by a.pId ASC";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plist;
	}

	@Override
	public List<Product> get3BestSellerProduct() {
		List<Product> plist = new ArrayList<Product>();
		String query = "SELECT Top 3 * FROM product \r\n" + "order by sold DESC";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plist;
	}

	@Override
	public List<Product> get3_6BestSellerProduct() {
		List<Product> plist = new ArrayList<Product>();
		String query = "SELECT Top 3 * FROM (SELECT Top 6 * FROM product order by sold DESC) a order by a.sold ASC";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plist;
	}

	@Override
	public List<Product> getProductByCID(String cid) {
		List<Product> plist = new ArrayList<>();
		String query = "select * from product join category2 on product.cateId=category2.cId where category2.cIdBig=?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, cid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
		}
		return plist;
	}

	@Override
	public List<Product> getProductByCID2(String cid) {
		List<Product> plist = new ArrayList<>();
		String query = "select * from product where cateId=?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, cid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
		}
		return plist;
	}

	@Override
	public Product getProductByID(String id) {
		String query = "select * from product where pId=?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
						rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(10));
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public List<Product> searchByName(String txtSearch) {
		List<Product> plist = new ArrayList<>();
		String query = "select * from product where pName like ?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "%" + txtSearch + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
		}
		return plist;
	}

	@Override
	public int countSearch(String txtSearch) {
		String query = "select count (*) from product where pName like ?";
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

	@Override
	public int countProduct(int storeId) {
		String query = "select count (*) from product where storeId= ?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, storeId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
		}
		return 0;
	}
	
	@Override
	public int countProduct() {
		String query = "select count (*) from product";
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
	public List<Product> pagingSearch(String txtSearch, int index) {
		List<Product> plist = new ArrayList<>();
		String query = "select * from product\r\n" + "where pName like ?\r\n"
				+ "order by pId OFFSET ? rows FETCH NEXT 9 ROWS Only;";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "%" + txtSearch + "%");
			ps.setInt(2, index * 9 - 9);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
		}
		return plist;
	}

	@Override
	public List<Product> pagingManagerP(int index) {
		List<Product> plist = new ArrayList<>();
		String query = "select * from product \r\n" + "order by pId OFFSET ? rows FETCH NEXT 10 ROWS Only;";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, index * 10 - 10);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
		}
		return plist;
	}

	@Override
	public List<Product> getNext4Product(int amount) {
		List<Product> plist = new ArrayList<Product>();
		String query = "select * from product \r\n" + "order by pId OFFSET ? rows FETCH NEXT 4 ROWS Only;";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, amount);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
		}
		return plist;
	}

	@Override
	public List<Product> get8Product() {
		List<Product> plist = new ArrayList<Product>();
		String query = "SELECT Top 8 * FROM product ";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plist;
	}

	@Override
	public void deleteProduct(int pid) {
		String query = "delete from product\n"
                + "where pId = ?";
        try {
        	Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, pid);
            ps.executeUpdate();
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
		
	@Override
	public void insertProduct(Product p) {
        String query = "Insert into product values (?,?,?,?,?,?,?,getdate(),0)";
        try {
        	Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, p.getName());
            ps.setInt(2, p.getPrice());
            ps.setString(3, p.getImage());
            ps.setString(4, p.getDescription());
            ps.setInt(5, p.getQuantity());
            ps.setInt(6, p.getCateId());
            ps.setInt(7, p.getStoreId());
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
	
	@Override
	public void editProduct(Product p) {
		// TODO Auto-generated method stub
		String query = "update product\n"
                + "set [pName] = ?,\n"
                + "pPrice = ?,\n"
                + "[pImage] = ?,\n"
                + "[pDescription] = ?,\n"
                + "pQuantity = ?,\n"
                + "cateId = ?,\n"
                + "storeId = ?\n"
                + "where pId = ?";
				try {
				Connection con = super.getConnection();
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, p.getName());
				ps.setInt(2, p.getPrice());
				ps.setString(3, p.getImage());
				ps.setString(4, p.getDescription());
				ps.setInt(5, p.getQuantity());
				ps.setInt(6, p.getCateId());
				ps.setInt(7, p.getStoreId());
				ps.setInt(8, p.getId());
				ps.executeUpdate();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
	}
	
	@Override
	public List<Product> GetProductByStoreId(int storeId, int index) {
		List<Product> plist = new ArrayList<>();
		String query = "select * from product where storeId= ?\r\n"
				+ "order by pId OFFSET ? rows FETCH NEXT 10 ROWS Only;";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, storeId);
			ps.setInt(2, index * 10 - 10);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
		}
		return plist;
	}
	
	public static void main(String[] args) {
		ProductDAO p = new ProductDAOImpl();
		System.out.print(p.getProductByID(Integer.toString(1)));
	}
	
	@Override
	public List<Product> pagingSearchByStore(int StoreId, String txtSearch, int index) {
		List<Product> plist = new ArrayList<>();
		String query = "select * from product\r\n"
				+ "where pName like ? and storeId=?\r\n"
				+ "order by pId OFFSET ? rows FETCH NEXT 10 ROWS Only;";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "%" + txtSearch + "%");
			ps.setInt(2, StoreId);
			ps.setInt(3, index * 10 - 10);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
		}
		return plist;
	}
	
	@Override
	public int countSearchByStore(int storeId, String txtSearch) {
		String query = "select count (*) from product where pName like ? and storeId=?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "%" + txtSearch + "%");
			ps.setInt(2, storeId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
		}
		return 0;
	}
	
	@Override
	public List<Product> getAllProduct() {
		List<Product> plist = new ArrayList<>();
		String query = "select * from product";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
		}
		return plist;
	}
	
	@Override
	public Product getLastestProduct(String pName) {
		Product product = new Product();
		String query = "SELECT TOP 1 * FROM product WHERE pName LIKE ? ORDER BY pId DESC";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, pName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return product;
	}
	
	@Override
	public boolean editApiProduct(Product p) {
		// TODO Auto-generated method stub
		String query = "update product\n"
                + "set [pName] = ?,\n"
                + "pPrice = ?,\n"
                + "[pImage] = ?,\n"
                + "[pDescription] = ?,\n"
                + "pQuantity = ?,\n"
                + "cateId = ?,\n"
                + "storeId = ?\n"
                + "where pId = ?";
				try {
				Connection con = super.getConnection();
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, p.getName());
				ps.setInt(2, p.getPrice());
				ps.setString(3, p.getImage());
				ps.setString(4, p.getDescription());
				ps.setInt(5, p.getQuantity());
				ps.setInt(6, p.getCateId());
				ps.setInt(7, p.getStoreId());
				ps.setInt(8, p.getId());
				ps.executeUpdate();
				return true;
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
				}
	}
	
	@Override
	public List<Product> searchByNameAndStore(String txtSearch, String storeId) {
		List<Product> plist = new ArrayList<>();
		String query = "select * from product where pName like ? and storeId=?";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "%" + txtSearch + "%");
			ps.setString(2, storeId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plist;
	}
	
	@Override
	public List<Product> getLastestProduct() {
		List<Product> plist = new ArrayList<>();
		String query = "SELECT Top 8 * FROM product \r\n" + "order by pId DESC";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plist;
	}
	
	@Override
	public List<Product> getBestSeller() {
		List<Product> plist = new ArrayList<>();
		String query = "SELECT Top 8 * FROM product \r\n" + "order by sold DESC";
		try {
			Connection conn = super.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getInt(3));
				product.setImage(rs.getString(4));
				product.setDescription(rs.getString(5));
				product.setQuantity(rs.getInt(6));
				product.setCateId(rs.getInt(7));
				product.setStoreId(rs.getInt(8));
				product.setSold(rs.getInt(10));
				plist.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plist;
	}
}
