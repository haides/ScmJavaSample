package demo;

import org.bson.BasicBSONObject;
import org.bson.util.JSON;

import com.sequoiacm.client.common.ScmType.ScopeType;
import com.sequoiacm.client.common.ScmType.SessionType;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmCursor;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmFile;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.element.ScmFileBasicInfo;
import com.sequoiacm.client.element.ScmId;
import com.sequoiacm.client.exception.ScmException;

public class version {

	public static void main(String[] args) {
		String url = "172.26.114.227:8080/rootsite";
		String user = "admin";
		String password = "admin";
		ScmSession session = null;
		try {
			session = ScmFactory.Session.createSession(SessionType.AUTH_SESSION,
					new ScmConfigOption(url, user, password));
			String wsName = "ws_default";
			ScmWorkspace workspace = ScmFactory.Workspace.getWorkspace(wsName, session);

			System.out.println("---->>:验证版本操作");
			System.out.println("---->>:create a new ScmFile object");
			
			String filepath = "update.jpg";


			ScmFile file = ScmFactory.File.createInstance(workspace);
			file.setFileName("test-File-version-20180803");
			file.setContent(filepath);
			file.save();

			ScmCursor<ScmFileBasicInfo> searchFileList;
			BasicBSONObject queryCondition = new BasicBSONObject();
			queryCondition = (BasicBSONObject) JSON.parse("{ \"name\": \"test-File-version-20180803\" }");
			// 查询所有记录，并把查询结果放标对象中
			searchFileList = ScmFactory.File.listInstance(workspace, ScopeType.SCOPE_CURRENT, queryCondition);

				while (searchFileList.hasNext()) {
					System.out.println("---->>: " + file.getFileId() + "|Version: " + file.getMajorVersion() + "."
							+ file.getMinorVersion());
					ScmFileBasicInfo record = (ScmFileBasicInfo) searchFileList.getNext();
					ScmId upfileid = new ScmId(record.getFileId().toString());
					ScmFile upfile = ScmFactory.File.getInstance(workspace, upfileid);
					upfile.updateContent(filepath);
					upfile.updateContent(filepath);
					upfile.updateContent(filepath);
					System.out.println("---->>: " + upfile.getFileId() + "|Version: " + upfile.getMajorVersion() + "."
					+ upfile.getMinorVersion());
					}


				
		} catch (ScmException e) {
			e.printStackTrace();
		
		}
		}}
