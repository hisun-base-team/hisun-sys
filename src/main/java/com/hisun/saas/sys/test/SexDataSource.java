package com.hisun.saas.sys.test;

import com.hisun.saas.sys.taglib.selectTag.AbstractSelectObject;
import com.hisun.saas.sys.taglib.selectTag.SelectNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SexDataSource extends AbstractSelectObject {

	public List<SelectNode> getDataOptions() throws Exception {
		List<SelectNode> selectNodes = new ArrayList<SelectNode>();
		SelectNode selectNode = new SelectNode();
//		selectNode.setOptionKey("");
//		selectNode.setOptionValue("");
//		selectNodes.add(selectNode);
//		selectNode = new SelectNode();
		selectNode.setOptionKey("0");
		selectNode.setOptionValue("选择项1");
		selectNodes.add(selectNode);
		selectNode = new SelectNode();
		selectNode.setOptionKey("1");
		selectNode.setOptionValue("选择项2");
		selectNodes.add(selectNode);
		selectNode = new SelectNode();
		selectNode.setOptionKey("2");
		selectNode.setOptionValue("选择项3");
		selectNodes.add(selectNode);
		selectNode = new SelectNode();
		selectNode.setOptionKey("3");
		selectNode.setOptionValue("选择项4");
		selectNodes.add(selectNode);
		return selectNodes;
	}



}
