package demo;

import com.sequoiacm.client.common.ScmType.SessionType;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmCursor;
import com.sequoiacm.client.core.ScmDirectory;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmFile;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.element.ScmFileBasicInfo;
import com.sequoiacm.client.element.ScmId;
import com.sequoiacm.client.exception.ScmException;

public class directory {

	public static void main(String[] args) {
		
		String url = "172.26.114.233:8080/rootsite";
		String user = "admin";
		String password = "admin";
		ScmSession session = null;
		try {
			session = ScmFactory.Session.createSession(SessionType.AUTH_SESSION,
					new ScmConfigOption(url, user, password));
			String wsName = "ws_default";
			ScmWorkspace workspace = ScmFactory.Workspace.getWorkspace(wsName, session);

			System.out.println("create a directory ....");
			String path = "test";
			String fullpath = "/" + path + System.currentTimeMillis();
			ScmDirectory directory = ScmFactory.Directory.createInstance(workspace, fullpath);
			System.out.println("---->>: " + directory.getPath());

			String filepath = "testfile.zip";
			String[] files = new String[5];

			for (int i = 0; i < 3; i++) {
				ScmDirectory subdirectory = directory.createSubdirectory(path + i);
				System.out.println("---->>: " + subdirectory.getPath());

				ScmFile file = ScmFactory.File.createInstance(workspace);
				file.setFileName((String) (subdirectory.getPath() + filepath).replaceAll("/", "-"));
				file.setDirectory(subdirectory);
				file.setContent(filepath);
				file.save();
				System.out.println("---->>: " + file.getFileId() + "|Directory: " + file.getDirectory().getPath());
				files[i] = file.getFileId().toString();
			}
			// System.out.println("---->>:create a new ScmFile object");

			for (int i = 0; i < 3; i++) {
				ScmFile f = ScmFactory.File.getInstance(workspace, new ScmId(files[i]));
				f.setDirectory(directory);
				System.out.println("---->>: " + f.getFileId() + "|Directory: " + f.getDirectory().getPath());
			}

			ScmDirectory d = ScmFactory.Directory.getInstance(workspace, fullpath);
			ScmCursor<ScmFileBasicInfo> searchFileList;
			// 查询所有记录，并把查询结果放在游标对象中
			searchFileList = d.listFiles(null);

			try {
				while (searchFileList.hasNext()) {
					ScmFileBasicInfo fb = (ScmFileBasicInfo) searchFileList.getNext();
					ScmFile record = ScmFactory.File.getInstance(workspace, fb.getFileId());
					System.out.println("---->>: " + "id:" + record.getFileId() + " |name: " + record.getFileName()
							+ "|Directory: " + record.getDirectory().getPath() + "|version: " + record.getMajorVersion()
							+ "." + record.getMinorVersion() + "|CreateDate: " + record.getCreateTime());
				}
			} finally {
				searchFileList.close();
			}
		} catch (ScmException e) {
			e.printStackTrace();
		}

	}

}
