package com.learn.word.taglib.word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.learn.word.config.ServerApplictionContext;
import com.learn.word.domain.WordInfo;
import com.learn.word.service.WordInfoService;
import com.learn.word.taglib.BaseTag;

public class WordInfoTag extends BaseTag{

	private String wordname;
	private String status;
	private String startTime;//yyyyMMddHHmmss
	private String endTime;//yyyyMMddHHmmss
	private int deal;//0：全部,1：name,2:status,3:起始时间,4：结束时间，5:中间某段时间，6：降序，7，升序
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6860873243976921755L;
	
	@Override
	public int doStartTag() throws JspException {
		setDefaultVar("wordinfo");
		Map<String, Object> resultMap =  new HashMap<String, Object>();
		
		WordInfoService wordInfoService = (WordInfoService) ServerApplictionContext.getAppContext().getBean("wordInfoServiceImpl");
		
		switch (deal) {
		case 0:
			List<WordInfo> findAll = wordInfoService.findAll();
			if (findAll != null) {
				resultMap.put("resultCode", "success");
			} else {
				resultMap.put("resultCode", "fail");
			}
			resultMap.put("resultDesc", findAll);
			break;
			
		case 1:
			if (wordname != null && "".equals(wordname)) {
				WordInfo findByName = wordInfoService.findByName(wordname);
				if (findByName != null) {
					resultMap.put("resultCode", "success");
				} else {
					resultMap.put("resultCode", "fail");
				}
				resultMap.put("resultDesc", findByName);
			}
			break;
		case 2://TODO:
			
		default:
			break;
		}
		
		
 		saveScope(resultMap);
        return super.doStartTag();
    }

	public String getWordname() {
		return wordname;
	}

	public void setWordname(String wordname) {
		this.wordname = wordname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getDeal() {
		return deal;
	}

	public void setDeal(int deal) {
		this.deal = deal;
	}

	
	
	

}
