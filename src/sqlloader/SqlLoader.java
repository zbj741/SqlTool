package sqlloader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.IResult;

public class SqlLoader {
	// 数据库的连接的URL
	private static String url = "jdbc:sqlserver://localhost:1433; DatabaseName=KeyPowerScheduling";
	// 数据库用户名
	private static String user = "sa";
	// 数据库密码
	private static String password = "ZBJzbj123";
	private static String classname = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static Connection conn = null;
	private static ArrayList<Map<String, String>> resultMap = null;

	public static void setURL(String url) {
		SqlLoader.url = url;
	}

	public static void setCLASSNAME(String classname) {
		SqlLoader.classname = classname;
	}

	public static void setUSER(String user) {
		SqlLoader.user = user;
	}

	public static void setPASSWORD(String pwd) {
		SqlLoader.password = pwd;
	}

	private static boolean connectSQL() {
		try {
			// 1.驱动驱动程序
			Class.forName(classname);
			// 2.从驱动程序管理类获取连接
			conn = DriverManager.getConnection(url, user, password);
			if (conn == null)
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static boolean closeConnection() {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		return true;
	}

	public static IResult excuteDDL(String sql) {
		Statement stmt = null;
		int count = -1;
		try {
			if (!connectSQL())
				return new Result(-1, false, "无法连接数据库");
			stmt = conn.createStatement();
			// 4.执行sql语句，返回结果
			count = stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(-1, false, e.getMessage());
		} finally {
			// 6.关闭资源（先关闭statement，再关闭connection）
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return new Result(-1, false, e.getMessage());
				}
			closeConnection();
		}
		return new Result(count, true, null);
	}

	public static IResult excuteDML(String sql) {
		Statement stmt = null;
		int count = -1;
		try {
			if (!connectSQL())
				return new Result(-1, false, "无法连接数据库");
			stmt = conn.createStatement();
			// 4.执行sql语句，返回结果
			count = stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(-1, false, e.getMessage());
		} finally {
			// 6.关闭资源（先关闭statement，再关闭connection）
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return new Result(-1, false, e.getMessage());
				}
			closeConnection();
		}
		return new Result(count, true, null);
	}

	public static IResult excuteDQL(String sql) {
		Statement stmt = null;
		ResultSet rs = null;
		int count = -1;
		try {
			if (!connectSQL())
				return new Result(-1, false, "无法连接数据库");
			// 创建Statement对象
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
			int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
			resultMap = new ArrayList<Map<String, String>>();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					map.put(md.getColumnName(i), String.valueOf(rs.getObject(i)));
				}
				resultMap.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(-1, false, e.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return new Result(-1, false, e.getMessage());
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return new Result(-1, false, e.getMessage());
				}
			closeConnection();
		}
		return new Result(count, true, null);
	}

	public static List<Map<String, String>> getDQLResult() {
		return SqlLoader.resultMap;
	}
}
