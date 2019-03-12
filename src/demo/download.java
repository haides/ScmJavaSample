package demo;

import com.sequoiacm.client.common.ScmType.SessionType;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmFile;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.element.ScmId;
import com.sequoiacm.client.exception.ScmException;

public class download {

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

			ScmId downFileId = new ScmId("5ac3a500400001000000003d");
			ScmFile downFile = ScmFactory.File.getInstance(workspace, downFileId);
			downFile.getContent("Downlaod-File.jpg");
			System.out.println("File is downloaded");
		} catch (ScmException e) {
			e.printStackTrace();
		}

	}

}
