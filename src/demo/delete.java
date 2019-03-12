package demo;

import com.sequoiacm.client.common.ScmType.SessionType;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmFile;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.exception.ScmException;

public class delete {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "172.26.114.233:8080/rootsite";
		String user = "admin";
		String password = "admin";
		ScmSession session = null;
		try {
			session = ScmFactory.Session.createSession(SessionType.AUTH_SESSION,
					new ScmConfigOption(url, user, password));
			String wsName = "ws_default";
			ScmWorkspace workspace = ScmFactory.Workspace.getWorkspace(wsName, session);

			String filepath = "testfile.zip";
			ScmFile file = ScmFactory.File.createInstance(workspace);
			file.setFileName("testFile-for-delete-record");
			file.setContent(filepath);
			file.save();
			System.out.println("---->>: " + file.getFileId() + "|Version: " + file.getMajorVersion() + "."
					+ file.getMinorVersion());

			file.delete(true);
			System.out.println("---->>:delete a ScmFile object");
		} catch (ScmException e) {
			e.printStackTrace();
		}

	}

}
