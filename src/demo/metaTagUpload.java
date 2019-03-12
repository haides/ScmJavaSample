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
import com.sequoiacm.client.element.ScmTags;
import com.sequoiacm.client.exception.ScmException;

public class metaTagUpload {

	public static void main(String[] args)throws Exception {

		String url = "172.26.114.227:8080/rootsite";
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

		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SSS");  
		    Date date = formatter.parse("2018-03-04-23:59:59.000");   
			
			// System.out.println("---->>:create a new ScmFile object");
			String filepath = "test.png";
			ScmFile file = ScmFactory.File.createInstance(workspace);
			file.setFileName("Test for China ID copy image with Document Class");
			file.setDirectory(directory);
			file.setContent(filepath);
			file.setCreateTime(date);

			ScmTags tags = new ScmTags();
			tags.addTag("身份证号", "210010291312432432");
			tags.addTag("身份证姓名", "赵本山");
			tags.addTag("身份证地址","辽宁铁岭");
			file.setTags(tags);
			
			file.save();


			System.out.println("---->>:create a new ScmFile object"+ file.getFileId());
			System.out.println("---->>: "  + file.toString());

		} catch (ScmException e) {
			e.printStackTrace();
		}
	}

}
