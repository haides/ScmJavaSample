package demo;

import com.sequoiacm.client.common.ScmType.SessionType;
import com.sequoiacm.client.core.ScmAttribute;
import com.sequoiacm.client.core.ScmClass;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.element.ScmId;
import com.sequoiacm.client.element.metadata.ScmAttributeConf;
import com.sequoiacm.client.element.metadata.ScmStringRule;
import com.sequoiacm.client.exception.ScmException;
import com.sequoiacm.common.AttributeType;


public class metaDefind {

	public static void main(String[] args) throws Exception{

		String url = "172.26.114.227:8080/rootsite";
		String user = "admin";
		String password = "admin";
		ScmSession session = null;
		try {
			session = ScmFactory.Session.createSession(SessionType.AUTH_SESSION,
					new ScmConfigOption(url, user, password));
			String wsName = "ws_default";
			ScmWorkspace workspace = ScmFactory.Workspace.getWorkspace(wsName, session);

			System.out.println("create a 身份证模型 ....");
			//创建身份证号属性
			ScmStringRule id_checkString = new ScmStringRule();
			id_checkString.setMaxLength(18);
			ScmAttributeConf sc_id = new ScmAttributeConf();
			sc_id.setName("id_number");
			sc_id.setDisplayName("身份证号");
			sc_id.setRequired(true);
			sc_id.setType(AttributeType.STRING);
			sc_id.setCheckRule(id_checkString);
			ScmAttribute property_id = ScmFactory.Attribute.createInstance(workspace, sc_id);

			//创建身份证姓名属性
			ScmStringRule name_checkString = new ScmStringRule();
			name_checkString.setMaxLength(10);
			ScmAttributeConf sc_name = new ScmAttributeConf();
			sc_name.setName("id_name");
			sc_name.setDisplayName("身份证姓名");
			sc_name.setRequired(true);
			sc_name.setType(AttributeType.STRING);
			sc_name.setCheckRule(name_checkString);
			ScmAttribute property_name = ScmFactory.Attribute.createInstance(workspace,sc_name);

			//创建身份证地址属性
			ScmStringRule address_checkString = new ScmStringRule();
			address_checkString.setMaxLength(100);
			ScmAttributeConf sc_address = new ScmAttributeConf();
			sc_address.setName("id_address");
			sc_address.setDisplayName("身份证地址");
			sc_address.setRequired(false);
			sc_address.setType(AttributeType.STRING);
			sc_address.setCheckRule(address_checkString);
			ScmAttribute property_address = ScmFactory.Attribute.createInstance(workspace,sc_address);

			//创建身份证有效期属性
			ScmAttributeConf sc_expiredate = new ScmAttributeConf();
			sc_expiredate.setName("id_expiredate");
			sc_expiredate.setDisplayName("身份证有效期");
			sc_expiredate.setRequired(false);
			sc_expiredate.setType(AttributeType.DATE);
			ScmAttribute property_expiredate = ScmFactory.Attribute.createInstance(workspace,sc_expiredate);

			//创建身份证数据模型，关联四个属性
			ScmClass id_china = ScmFactory.Class.createInstance(workspace, "id_china", "中国身份证");
			id_china.attachAttr(property_id.getId());
			id_china.attachAttr(property_name.getId());
			id_china.attachAttr(property_address.getId());
			id_china.attachAttr(property_expiredate.getId());

			//获得身份证数据模型的标识
			ScmId classid = id_china.getId();

			System.out.println("---->>:创建的身份证文档类型");
			System.out.println("---->>: " + classid.toString());

		} catch (ScmException e) {
			e.printStackTrace();
		}
	
	}

}
