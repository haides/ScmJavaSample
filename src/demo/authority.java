package demo;

import com.sequoiacm.client.common.ScmType.SessionType;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmDirectory;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmFile;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.exception.ScmException;

public class authority {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "172.26.114.233:8080/rootsite";
		String user = "admin"; // 工作区没有给admin用户赋权，所以会出错。
		String password = "admin"; // 换成branchuser/password 就可以成功创建目录和文件。
		ScmSession session = null;
		try {
			session = ScmFactory.Session.createSession(SessionType.AUTH_SESSION,
					new ScmConfigOption(url, user, password));
			String wsName = "ws_distribute";
			ScmWorkspace workspace = ScmFactory.Workspace.getWorkspace(wsName, session);

			// 工作区权限测试
			String workspaceName = "ws_test";
			ScmSession newsession = ScmFactory.Session.createSession(SessionType.AUTH_SESSION,
					new ScmConfigOption(url, "admin", "admin"));
			ScmWorkspace newworkspace = ScmFactory.Workspace.getWorkspace(workspaceName, newsession);
			try {

				System.out.println("---->>: ");
				System.out.println("the workspace's name is : " + newworkspace.getName());
				System.out.println("the workspace's description is : " + newworkspace.getDescription());
				System.out.println("the workspace's metalocation of site name  is : "
						+ newworkspace.getMetaLocation().getSiteName());
				System.out.println(
						"the workspace's metalocation of data type is : " + newworkspace.getMetaLocation().getType());
				System.out.println("the workspace's create time is : " + newworkspace.getCreateTime());

				ScmDirectory folder = ScmFactory.Directory.createInstance(workspace, "/new-bussiness");
				System.out.println("---->>: " + "id:" + folder.getName() + " |name: " + folder.getPath());

				ScmFile file = ScmFactory.File.createInstance(workspace);
				file.setFileName("new-bussiness_file");
				file.setContent("ombuild_for_sdb_2.8.zip");
				file.save();
				System.out.println("---->>: " + "id:" + file.getFileId() + " |name: " + file.getFileName());

			} finally {
				newsession.close();
			}
		} catch (ScmException e) {
			e.printStackTrace();
		}

	}

}
