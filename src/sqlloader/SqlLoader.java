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
	// ���ݿ�����ӵ�URL
	private static String url = "jdbc:sqlserver://localhost:1433; DatabaseName=KeyPowerScheduling";
	// ���ݿ��û���
	private static String user = "sa";
	// ���ݿ�����
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
			// 1.������������
			Class.forName(classname);
			// 2.����������������ȡ����
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
				return new Result(-1, false, "�޷��������ݿ�");
			stmt = conn.createStatement();
			// 4.ִ��sql��䣬���ؽ��
			count = stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(-1, false, e.getMessage());
		} finally {
			// 6.�ر���Դ���ȹر�statement���ٹر�connection��
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
				return new Result(-1, false, "�޷��������ݿ�");
			stmt = conn.createStatement();
			// 4.ִ��sql��䣬���ؽ��
			count = stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(-1, false, e.getMessage());
		} finally {
			// 6.�ر���Դ���ȹر�statement���ٹر�connection��
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
				return new Result(-1, false, "�޷��������ݿ�");
			// ����Statement����
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
			int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
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
