package sqlloader;

import java.util.List;
import java.util.Map;

import sqlloader.SqlLoader;

public class SqlHelper {

	// SQL SELECT
	static String sqlIsExistUser = "select count(userName) as count from Users where userName = '%s'";
	static String sqlCheckUser = "select count(userName) as count from Users where userName = '%s' and userPassword = '%s'";
	static String sqlGetPermission = "select userPermission from Users where userName ='%s'";
	static String sqlGetRealName = "select name from Users where userName='%s'";
	static String sqlGetUserIdByRealName = "select userId from Users where userName='%s'";
	static String sqlGetRealNameById = "select name from Users where userId = %s";

	static String sqlSelectApp = "select * from Apparatus";
	static String sqlSelectAppById = "select * from Apparatus where appId = '%s'";
	static String sqlSelectAppPicName = "select appPicName from Apparatus where appId='%s'";
	static String sqlSelectAppIdByName = "select appId from Apparatus where appName = '%s'";

	static String sqlSelectEqu = "select appName,equName,equNumber,equId from Apparatus ,Equipment where Apparatus.appId=Equipment.appId";
	static String sqlSelectEquById = "select appName,equName,equNumber from Apparatus,Equipment where Apparatus.appId = Equipment.appId and Equipment.equId=%s";
	static String sqlSelectEquByAppId = "select * from Equipment where appId = %s";

	static String sqlSelectExp = "select * from Experiment";
	static String sqlSelectExpById = "select * from Experiment where expId=%s";

	static String sqlSelectStepsById = "select * from Steps where expId=%s";

	static String sqlSelectTaskByUserId = "select * from Task where userId = '%s'";
	static String sqlSelectTaskByTaskId = "select * from Task where taskId ='%s'";

	static String sqlAuditorTaskSelect = "select * from Task where taskStatus = '1' order by appointmentTime";
	static String sqlAdminTaskSelect = "select * from Task where taskStatus = '2' order by appointmentTime";

	static String sqlSelectFinshTask = "select * from Task where taskStatus = '3' ";

	static String sqlSelectCurId = "select IDENT_CURRENT('%s') as cnt";
	// SQL INSERT
	static String sqlInsertApp = "insert into Apparatus VALUES ('%s','%s','%s')";
	static String sqlInsertEqu = "insert into Equipment values (%d,'%s','%s')";
	static String sqlInsertExp = "insert into Experiment values('%s','%s')";
	static String sqlInsertSteps = "insert into Steps values('%s','%s','%s','%s')";
	static String sqlInsertTask = "insert into Task (expId,userId,expectedComTime,appointmentTime,taskStatus,taskPriority,extraDescription)¡¡values ('%s','%s','%s','%s','%s','%s','%s')";
	static String sqlInsertSchedule = "insert into Schedule (taskId,equId,equBeginTime,actualHolTime,expSequence) values ('%s','%s','%s','%s','%s')";
	// SQL DELETE
	static String sqlDeleteApp = "delete from Apparatus where appId='%s'";
	static String sqlDeleteEqu = "delete from Equipment where equId = '%s'";
	static String sqlDeleteStep = "delete from Steps where stepId = %s";
	static String sqlDeleteExp = "delete from Experiment where expId = %s";
	static String sqltotalTimeGroupByEquId = "select equName, C.sumtime from Equipment,(\r\n" + 
			"select sum(actualHolTime) as sumtime, A.equId   from Schedule A,(  select equId as groupid from Schedule group by equId ) B where A.equId = B.groupid group by A.equId ) C\r\n" + 
			"where C.equId = Equipment.equId";
	// SQL UPDATE
	static String sqlUpdateApp = "update Apparatus set appName='%s',appDescription='%s' where appId='%s'";
	static String sqlUpdateEqu = "update Equipment set equName = '%s', equNumber= '%s' where equId= %s";
	static String sqlUpdateExp = "update Experiment set expName='%s',expDescription='%s' where expId=%s";

	static String sqlUpdateTaskAudAgree = "update Task set auditId = %s, auditPassTime= '%s', taskStatus = 2 where taskId=%s";
	static String sqlUpdateTaskAdmAgree = "update Task set adminId = %s, taskBeginTime = '%s',taskEndTime = '%s',taskStatus =3 where taskId=%s";

	static String sqlUpdateTaskAudDecline = "update Task set auditId = %s, auditPassTime= '%s',taskStatus = 4 where taskId=%s";
	static String sqlUpdateTaskAdmDecline = "update Task set adminId = %s, taskStatus = 5 where taskId = %s";

	public static boolean isExistUser(String name) {

		SqlLoader.excuteDQL(String.format(sqlIsExistUser, name));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		if (Integer.parseInt(result.get(0).get("count")) > 0) {
			// System.out.println(Integer.parseInt(result.get(0).get("count")));
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkUser(String name, String password) {
		SqlLoader.excuteDQL(String.format(sqlCheckUser, name, password));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		if (Integer.parseInt(result.get(0).get("count")) > 0) {
			// System.out.println(Integer.parseInt(result.get(0).get("count")));
			return true;
		} else {
			return false;
		}
	}

	public static int getPermission(String name) {
		SqlLoader.excuteDQL(String.format(sqlGetPermission, name));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		int permission = Integer.parseInt(result.get(0).get("userPermission"));
		return permission;
	}

	public static String getRealName(String name) {
		SqlLoader.excuteDQL(String.format(sqlGetRealName, name));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result.get(0).get("name");
	}

	public static String getRealNameById(String id) {
		SqlLoader.excuteDQL(String.format(sqlGetRealNameById, id));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result.get(0).get("name");
	}

	public static String getUserIdByName(String name) {
		SqlLoader.excuteDQL(String.format(sqlGetUserIdByRealName, name));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result.get(0).get("userId");
	}

	public static boolean InsertApp(String appName, String appPicName, String appDescription) {
		Result result = (Result) SqlLoader.excuteDML(String.format(sqlInsertApp, appName, appPicName, appDescription));

		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static List<Map<String, String>> SelectApp() {
		SqlLoader.excuteDQL(String.format(sqlSelectApp));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

	public static List<Map<String, String>> SelectApp(String id) {
		SqlLoader.excuteDQL(String.format(sqlSelectAppById, id));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

	public static String SelectAppNameById(String id) {
		SqlLoader.excuteDQL(String.format(sqlSelectAppById, id));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		String appname = result.get(0).get("appName");
		return appname;
	}

	public static boolean DeleteApp(String id) {
		Result result = (Result) SqlLoader.excuteDML(String.format(sqlDeleteApp, id));

		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static String GetAppPicName(String id) {
		SqlLoader.excuteDQL(String.format(sqlSelectAppPicName, id));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		String name = result.get(0).get("appPicName");
		return name;
	}

	public static boolean UpdateApp(String appId, String appName, String appDescription) {
		Result result = (Result) SqlLoader.excuteDML(String.format(sqlUpdateApp, appName, appDescription, appId));

		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static String GetAppIdByName(String name) {
		SqlLoader.excuteDQL(String.format(sqlSelectAppIdByName, name));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		String appId = (String) result.get(0).get("appId");
		return appId;
	}

	public static boolean InsertEqu(int appId, String equNumber, String equName) {
		Result result = (Result) SqlLoader.excuteDML(String.format(sqlInsertEqu, appId, equNumber, equName));
		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static List<Map<String, String>> SelectEqu() {
		SqlLoader.excuteDQL(String.format(sqlSelectEqu));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

	public static List<Map<String, String>> SelectEqu(String equId) {
		SqlLoader.excuteDQL(String.format(sqlSelectEquById, equId));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

	public static List<Map<String, String>> SelectEquByAppId(String appId) {
		SqlLoader.excuteDQL(String.format(sqlSelectEquByAppId, appId));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

	public static boolean UpdateEqu(String equName, String equNumber, String equId) {
		Result result = (Result) SqlLoader.excuteDML(String.format(sqlUpdateEqu, equName, equNumber, equId));

		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static boolean DeleteEqu(String equId) {
		Result result = (Result) SqlLoader.excuteDML(String.format(sqlDeleteEqu, equId));

		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static boolean InsertExp(String expName, String expDes) {
		Result result = (Result) SqlLoader.excuteDML(String.format(sqlInsertExp, expName, expDes));
		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static List<Map<String, String>> GetCurId(String talbeName) {
		SqlLoader.excuteDQL(String.format(sqlSelectCurId, talbeName));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

	public static boolean InsertSteps(String expId, String appId, String stepHolTime, String expSequence) {
		Result result = (Result) SqlLoader
				.excuteDML(String.format(sqlInsertSteps, expId, appId, stepHolTime, expSequence));
		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static List<Map<String, String>> SelectExperiment() {
		SqlLoader.excuteDQL(String.format(sqlSelectExp));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

	public static List<Map<String, String>> SelectExperiment(String id) {
		SqlLoader.excuteDQL(String.format(sqlSelectExpById, id));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

	public static List<Map<String, String>> SelectStepsById(String expId) {
		SqlLoader.excuteDQL(String.format(sqlSelectStepsById, expId));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

	public static boolean UpdateExp(String expName, String expDescription, String expId) {
		Result result = (Result) SqlLoader.excuteDML(String.format(sqlUpdateExp, expName, expDescription, expId));
		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static boolean DeleteStep(String stepId) {
		Result result = (Result) SqlLoader.excuteDML(String.format(sqlDeleteStep, stepId));

		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static boolean DeleteExp(String expId) {
		Result result = (Result) SqlLoader.excuteDML(String.format(sqlDeleteExp, expId));

		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static boolean InsertTask(String expId, String userId, String expectedComTime, String appointmentTime,
			String taskStatus, String taskPriority, String extraDescription) {
		Result result = (Result) SqlLoader.excuteDML(String.format(sqlInsertTask, expId, userId, expectedComTime,
				appointmentTime, taskStatus, taskPriority, extraDescription));
		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static List<Map<String, String>> SelectTaskByUserId(String userId) {
		SqlLoader.excuteDQL(String.format(sqlSelectTaskByUserId, userId));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

	public static List<Map<String, String>> SelectTaskByTaskId(String taskId) {
		SqlLoader.excuteDQL(String.format(sqlSelectTaskByTaskId, taskId));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

	public static List<Map<String, String>> AuditorSelectTask() {
		SqlLoader.excuteDQL(String.format(sqlAuditorTaskSelect));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

	public static boolean UpdateTaskAudAgree(String auditId, String auditPassTime, String taskId) {
		Result result = (Result) SqlLoader
				.excuteDML(String.format(sqlUpdateTaskAudAgree, auditId, auditPassTime, taskId));
		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static boolean UpdateTaskAudDecline(String auditId, String audPassTime, String taskId) {
		Result result = (Result) SqlLoader
				.excuteDML(String.format(sqlUpdateTaskAudDecline, auditId, audPassTime, taskId));
		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;

	}

	public static List<Map<String, String>> AdminSelectTask() {
		SqlLoader.excuteDQL(String.format(sqlAdminTaskSelect));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

	public static boolean UpdateTaskAdmDecline(String adminId, String taskId) {
		Result result = (Result) SqlLoader.excuteDML(String.format(sqlUpdateTaskAdmDecline, adminId, taskId));
		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static boolean UpdateTaskAdmAgree(String adminId, String taskBeginTime, String taskEndTime, String taskId) {
		Result result = (Result) SqlLoader
				.excuteDML(String.format(sqlUpdateTaskAdmAgree, adminId, taskBeginTime, taskEndTime, taskId));
		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static boolean InsertSchedule(String taskId, String equId, String equBeginTime, String actualHolTime,
			String expSequence) {
		Result result = (Result) SqlLoader
				.excuteDML(String.format(sqlInsertSchedule, taskId, equId, equBeginTime, actualHolTime, expSequence));
		if (result.getIsSuccessed() == true) {
			return true;
		} else
			return false;
	}

	public static List<Map<String, String>> SelectFinshTask() {
		SqlLoader.excuteDQL(String.format(sqlSelectFinshTask));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;

	}
	public static List<Map<String ,String >> SelectTotalTimeGroupByEquId()
	{
		SqlLoader.excuteDQL(String.format(sqltotalTimeGroupByEquId));
		List<Map<String, String>> result = SqlLoader.getDQLResult();
		return result;
	}

}
