package demo;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sequoiacm.client.common.ScmType.SessionType;
import com.sequoiacm.client.core.ScmBreakpointFile;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmCursor;
import com.sequoiacm.client.core.ScmDirectory;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmFile;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.exception.ScmException;

public class uploadBreakPoint {

	public static void main(String[] args) throws ParseException {
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

			System.out.println("create a directory ....");
			String path = "/test" + System.currentTimeMillis();
			ScmDirectory directory = ScmFactory.Directory.createInstance(workspace, path);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date Time = formatter.parse("2018-03-04");

			// System.out.println("---->>:create a new ScmFile object");
			String bigfilepath = "bigfile-193899.zip";
			ScmFile file = ScmFactory.File.createInstance(workspace);

			ScmBreakpointFile bpfile = ScmFactory.BreakpointFile.createInstance(workspace, bigfilepath);
			bpfile.upload(new File(bigfilepath));

			System.out.println("如果失败了，继续用文件标识上传: " + bpfile.getDataId());
			ScmCursor<ScmBreakpointFile> searchFileList = ScmFactory.BreakpointFile.listInstance(workspace);
			while (searchFileList.hasNext()) {
				ScmBreakpointFile record = searchFileList.getNext();
				System.out.println("name: " + record.getFileName() + "|UploadSize: " + record.getUploadSize()
						+ "| Checksum: " + record.getChecksum() + "(" + record.getChecksumType() + ") |User: "
						+ record.getUploadUser() + "|CreateDate: " + record.getCreateTime());

			}

			file.setFileName(bigfilepath);
			file.setAuthor(user);
			file.setDirectory(directory);
			file.setCreateTime(Time);
			file.setContent(bpfile);
			;
			file.save();

			System.out.println("---->>:create a new ScmFile object");
			System.out.println("---->>: " + file.getFileId() + "|Size: " + file.getSize() + "|CreateTime: "
					+ file.getCreateTime());

		} catch (ScmException e) {
			e.printStackTrace();
		}

	}

}
