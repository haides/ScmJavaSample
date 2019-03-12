package demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.util.JSON;

import com.sequoiacm.client.common.ScheduleType;
import com.sequoiacm.client.common.ScmType.ScopeType;
import com.sequoiacm.client.common.ScmType.SessionType;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmCursor;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmFile;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmSystem;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.element.ScmScheduleCleanFileContent;
import com.sequoiacm.client.element.ScmScheduleCopyFileContent;
import com.sequoiacm.client.element.ScmTaskBasicInfo;
import com.sequoiacm.client.exception.ScmException;

public class lifecycle {

	public static void main(String[] args) throws ParseException {

		String user = "branchUser";
		String password = "password";
		String filepath = "testfile.zip";

		try {

			// 主站点上传文件：
			String url = "172.26.114.233:8080/branchsite";
			ScmSession session = null;
			session = ScmFactory.Session.createSession(SessionType.AUTH_SESSION,
					new ScmConfigOption(url, user, password));
			String wsName = "ws_distribute";
			ScmWorkspace workspace = ScmFactory.Workspace.getWorkspace(wsName, session);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date Time = formatter.parse("2018-08-05");

			ScmFile file = ScmFactory.File.createInstance(workspace);
			file.setFileName(filepath + System.currentTimeMillis());
			file.setCreateTime(Time);
			file.setContent(filepath);
			file.save();

			System.out.println("---->>:create a new ScmFile object in rootsite");
			System.out.println("---->>: " + file.getFileId() + "|Location: " + file.getLocationList() + "|CreateTime: "
					+ file.getCreateTime());

			BasicBSONObject queryCondition = new BasicBSONObject();
			queryCondition = (BasicBSONObject) JSON.parse("{ \"create_month\": \"201808\" }");

			String ScheduleCron = new String("0 /3 * * * ?");

			ScmScheduleCopyFileContent copyfileTask = new ScmScheduleCopyFileContent("branchsite", "rootsite", "1d",
					(BSONObject) queryCondition, ScopeType.SCOPE_ALL);
			ScmSystem.Schedule.create(session, "ws_distribute", ScheduleType.COPY_FILE, "TEST", "tEST DESC",
					copyfileTask, ScheduleCron);

			ScmScheduleCleanFileContent cleanfileTask = new ScmScheduleCleanFileContent("branchsite", "1d",
					(BSONObject) queryCondition);
			ScmSystem.Schedule.create(session, "ws_distribute", ScheduleType.CLEAN_FILE, "Clean Files", "Clean DESC",
					cleanfileTask, ScheduleCron);

			BasicBSONObject taskCondition = (BasicBSONObject) JSON.parse("{ \"workspace_name\": \"ws_distribute\" }");
			ScmCursor<ScmTaskBasicInfo> tasks = ScmSystem.Task.listTask(session, taskCondition);

			try {
				while (tasks.hasNext()) {
					ScmTaskBasicInfo record = tasks.getNext();
					System.out.println(record.toString());
				}
			} finally {
				tasks.close();
			}

		} catch (ScmException e) {
			e.printStackTrace();
		}

	}

}
