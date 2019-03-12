package demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sequoiacm.client.common.ScmType.SessionType;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmDirectory;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmFile;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.exception.ScmException;
import com.sequoiacm.common.MimeType;

public class upload {

	public static void main(String[] args) throws Exception {
		// 
		String url = "sdbubuntu.mshome.net:8080/rootsite";
		String user = "admin";
		String password = "admin";
		ScmSession session = null;
		try {
			session = ScmFactory.Session.createSession(SessionType.AUTH_SESSION,
					new ScmConfigOption(url, user, password));
			String wsName = "ws_default";
			ScmWorkspace workspace = ScmFactory.Workspace.getWorkspace(wsName, session);

			System.out.println("create a directory ....");
			String path = "/test" + System.currentTimeMillis();
			ScmDirectory directory = ScmFactory.Directory.createInstance(workspace, path);

		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		    Date Time = formatter.parse("2017-04-04");   
			
			// System.out.println("---->>:create a new ScmFile object");
			String filepath = "test.png";
			ScmFile file = ScmFactory.File.createInstance(workspace);
			file.setFileName("testFileOne");
			file.setAuthor("testFileAuthor");
			file.setMimeType(MimeType.PLAIN);
			file.setTitle("this is test file title");
			file.setDirectory(directory);
			file.setContent(filepath);
			file.setCreateTime(Time);
			file.save();


			System.out.println("---->>:create a new ScmFile object");
			System.out.println("---->>: " + file.getFileId() + "|Version: " + file.getMajorVersion() + "."
					+ file.getMinorVersion() + "|CreateTime: " + file.getCreateTime());

		} catch (ScmException e) {
			e.printStackTrace();
		}

	}

}
