package com.hisun.saas.sys.taglib.selectOption.impl;
import com.hisun.saas.sys.taglib.selectOption.SelectObject;
import com.hisun.saas.sys.taglib.selectOption.vo.SelectNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SexDataSource extends SelectObject {

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
