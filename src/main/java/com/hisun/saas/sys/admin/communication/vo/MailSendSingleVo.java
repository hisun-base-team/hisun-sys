package com.hisun.saas.sys.admin.communication.vo;

import java.util.*;

/**
 * <p>类名称:MailSendSingleVo</p>
 * <p>类描述: 发送单个人</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/12/8上午10:16
 * @创建人联系方式:init@hn-hisun.com
 */
public class MailSendSingleVo extends MailSendVo {
    protected String toSingle;

    protected Map<String,String> paramMap;

    /**
     *
     * @param toSingle 要发送的邮箱
     * @param paramMap 参数
     */
    public MailSendSingleVo(String toSingle,Map<String,String> paramMap){
        this.toSingle = toSingle;
        this.paramMap = paramMap;
        init();
    }

    protected void init(){
        List<String> list = new ArrayList<String>();
        list.add(toSingle);
        setTo(list);
        Map<String,List<String>> mailParamMap = new HashMap<String,List<String>>();
        Iterator<String> it = paramMap.keySet().iterator();
        String key = null;
        while (it.hasNext()){
            key = it.next();
            list = new ArrayList<String>();
            list.add(paramMap.get(key));
            mailParamMap.put("%"+key+"%",list);
        }
        setSub(mailParamMap);
    }

    public String getToSingle() {
        return toSingle;
    }

    public void setToSingle(String toSingle) {
        this.toSingle = toSingle;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }
}
