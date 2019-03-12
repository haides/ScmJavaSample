package demo;

import com.sequoiacm.client.common.ScmType.SessionType;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmFile;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.exception.ScmException;

public class uploadbranch {

	public static void main(String[] args) {

		String user = "branchUser";
		String password = "password";
		String filepath = "testfile.zip";

		try {

			// 主站点上传文件：
			String url = "172.26.114.233:8080/rootsite";
			ScmSession session = null;
			session = ScmFactory.Session.createSession(SessionType.AUTH_SESSION,
					new ScmConfigOption(url, user, password));
			String wsName = "ws_distribute";
			ScmWorkspace workspace = ScmFactory.Workspace.getWorkspace(wsName, session);

			ScmFile file = ScmFactory.File.createInstance(workspace);
			file.setFileName(filepath + System.currentTimeMillis());
			file.setContent(filepath);
			file.save();
 
			System.out.println("---->>:create a new ScmFile object in rootsite");
			System.out.println("---->>: " + file.getFileId() + "|Location: " + file.getLocationList() + "|CreateTime: "
					+ file.getCreateTime());

			
			// 分站点上传文件：

			String branch_url = "172.26.114.233:8080/branchsite";
			ScmSession branch_session = null;
			branch_session = ScmFactory.Session.createSession(SessionType.AUTH_SESSION,
					new ScmConfigOption(branch_url, user, password));
			ScmWorkspace branch_workspace = ScmFactory.Workspace.getWorkspace(wsName, branch_session);
			ScmFile branchfile = ScmFactory.File.createInstance(branch_workspace);
			branchfile.setFileName(filepath + System.currentTimeMillis());
			branchfile.setContent(filepath);
			branchfile.save();

			System.out.println("---->>:create a new ScmFile object in branchsite");
			System.out.println("---->>: " + branchfile.getFileId() + "|Location: " + branchfile.getLocationList() + "|CreateTime: "
					+ branchfile.getCreateTime());

		} catch (ScmException e) {
			e.printStackTrace();
		}

	}

}
