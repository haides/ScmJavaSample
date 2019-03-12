package demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sequoiacm.client.common.ScmType.SessionType;
import com.sequoiacm.client.core.ScmClass;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmDirectory;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmFile;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.element.ScmClassProperties;
import com.sequoiacm.client.element.ScmTags;
import com.sequoiacm.client.exception.ScmException;

public class metaUpload {

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

			//方法一： 5b6d27964000010000000005 是 ScmClass id_china所定义的标识.
			ScmClassProperties china_id = new ScmClassProperties("5b6d27964000010000000005");
			china_id.addProperty("id_number", "210010291312432432");
			china_id.addProperty("id_name", "赵本山");
			china_id.addProperty("id_address","辽宁铁岭");
			file.setClassProperties(china_id);
			
			file.save();


			System.out.println("---->>:create a new ScmFile object"+ file.getFileId());
			System.out.println("标签方式---->>: "  + file.getTags().toString());
			System.out.println("数据模型方式---->>: "  + file.getClassProperties().toString());

		} catch (ScmException e) {
			e.printStackTrace();
		}
	}

}
