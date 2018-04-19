package com.hisun.saas.sys.admin.communication.vo;

import java.util.*;

/**
 * <p>类名称:MailSendMutipltVo</p>
 * <p>类描述: 发送多个人一样的文本</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:16/2/23下午5:12
 * @创建人联系方式:init@hn-hisun.com
 */
public class MailSendMutipltVo extends MailSendVo {
    protected List<String> toList;

    protected Map<String,String> paramMap;

    /**
     *
     * @param paramMap 参数
     */
    public MailSendMutipltVo(List<String> toList,Map<String,String> paramMap){
        this.toList = toList;
        this.paramMap = paramMap;
        init();
    }

    protected void init(){
        setTo(toList);
        Map<String,List<String>> mailParamMap = new HashMap<String,List<String>>();
        Iterator<String> it = paramMap.keySet().iterator();
        String key = null;
        List<String> list = null;
        while (it.hasNext()){
            key = it.next();
            list = new ArrayList<String>();
            list.add(paramMap.get(key));
            mailParamMap.put("%"+key+"%",list);
        }
        setSub(mailParamMap);
    }

    public List<String> getToList() {
        return toList;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }
}
