package demo;

import java.util.ArrayList;
import java.util.List;

import com.sequoiacm.client.common.ScmType.SessionType;
import com.sequoiacm.client.core.ScmBatch;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmFile;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.element.ScmTags;
import com.sequoiacm.client.exception.ScmException;

public class batch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "172.26.114.227:8080/rootsite";
		String user = "admin";
		String password = "admin";
		ScmSession session = null;
		try {
			session = ScmFactory.Session.createSession(SessionType.AUTH_SESSION,
					new ScmConfigOption(url, user, password));
			String wsName = "ws_default";
			ScmWorkspace workspace = ScmFactory.Workspace.getWorkspace(wsName, session);

			// 创建一个批次
			System.out.println("---->>: create a batch ....");
			ScmBatch Batch = null;

			Batch = ScmFactory.Batch.createInstance(workspace);
			Batch.setName("BatchTest_1");
			ScmTags tage = new ScmTags();
			tage.addTag("btchei", "创建批次");
			Batch.setTags(tage);
			Batch.save();
			System.out.println("---->>: create batch is : " + Batch.getId() + "|File Count: " + Batch.listFiles().size());

			String filepath = "testfile.zip";
			List<ScmFile> bFiles = new ArrayList<>();
			for (int i = 0; i < 5; i++) {

				ScmFile file = ScmFactory.File.createInstance(workspace);
				file.setFileName(filepath + System.currentTimeMillis());
				file.setContent(filepath);
				file.save();
				Batch.attachFile(file.getFileId());
				bFiles.add(i, file);
				System.out.println("---->>: " + file.getFileId() + "|name: " + file.getFileName());
			}

			System.out.println("---->>: create batch is : " + Batch.getId() + "|File Count: " + Batch.listFiles().size());
			System.out.println("---->>: create batch is : " + Batch.toString());

		} catch (ScmException e) {
			e.printStackTrace();
		}

	}

}
