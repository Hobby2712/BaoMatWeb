package Connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
	private final String serverName = "localhost";
	private final String dbName = "NKDShop";
	private final String portNumber = "1433";
	private final String instance = "";// MSSQLSERVER LEAVE THIS ONE EMPTY IF YOUR SQL IS A SINGLE INSTANCE
	private final String userID = "sa";
	private final String password = "1234";
	

public Connection getConnection() throws Exception {
	String url = "jdbc:sqlserver://" + serverName + ":" +
		portNumber + "\\" + instance + ";databaseName=" + dbName;
	if (instance == null || instance.trim().isEmpty())
	url = "jdbc:sqlserver://"+serverName+":"+portNumber
	+";databaseName="+dbName;
	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	//DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	
	return DriverManager.getConnection(url, userID, password);
}
}
