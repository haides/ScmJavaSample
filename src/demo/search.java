package demo;

import org.bson.BasicBSONObject;
import org.bson.util.JSON;

import com.sequoiacm.client.common.ScmType.ScopeType;
import com.sequoiacm.client.common.ScmType.SessionType;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmCursor;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.element.ScmFileBasicInfo;
import com.sequoiacm.client.exception.ScmException;

public class search {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "172.26.114.227:8080/rootsite";
		String user = "admin";
		String password = "admin";
		ScmSession session = null;
		try {
			session = ScmFactory.Session.createSession(SessionType.AUTH_SESSION,
					new ScmConfigOption(url, user, password));
			String wsName = "ws_test";
			ScmWorkspace workspace = ScmFactory.Workspace.getWorkspace(wsName, session);

			ScmCursor<ScmFileBasicInfo> searchFileList;
			BasicBSONObject queryCondition = new BasicBSONObject();
			queryCondition = (BasicBSONObject) JSON.parse("{ \"name\": \"testFileOne\" }");
			// 查询所有记录，并把查询结果放在游标对象中
			searchFileList = ScmFactory.File.listInstance(workspace, ScopeType.SCOPE_ALL, queryCondition);
			// 从游标中显示所有记录
			try {
				while (searchFileList.hasNext()) {
					ScmFileBasicInfo record = (ScmFileBasicInfo) searchFileList.getNext();
					System.out.println("id:" + record.getFileId() + " |name: " + record.getFileName() + "|version: "
							+ record.getMajorVersion() + "." + record.getMinorVersion() + "|User: " + record.getUser()
							+ "|CreateDate: " + record.getCreateDate());

				}
			} finally {
				searchFileList.close();
			}
		} catch (ScmException e) {
			e.printStackTrace();
		}

	}

}
