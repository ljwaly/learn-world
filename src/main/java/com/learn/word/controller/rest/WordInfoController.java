package com.learn.word.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.word.domain.WordInfo;
import com.learn.word.service.WordInfoService;


/**
 * 单词控制器
 * 
 * @author PC
 *
 */
@RestController
@RequestMapping("/word_info")
public class WordInfoController /*extends BaseController*/{

	@Autowired
	private WordInfoService wordInfoService;
	
	
	@RequestMapping("/save")
	@Transactional
	public Map<String, Object> saveAndUpdateWordInfo(WordInfo wordinfo){
		Map<String, Object> resultMap =  new HashMap<String, Object>();
		
		String wordName = wordinfo.getWordName();
		if (wordName == null || "".equals(wordName)) {
			resultMap.put("resultCode", "Params_null");
			resultMap.put("resultDesc", "wordName="+wordName);
			return resultMap;
		}
		WordInfo findByName = wordInfoService.findByName(wordName);
		if (findByName != null) {
			wordinfo.setWordId(findByName.getWordId());
		}
		WordInfo save = wordInfoService.save(wordinfo);
		
		if (save != null) {
			resultMap.put("resultCode", "success");
		} else {
			resultMap.put("resultCode", "fail");
		}
		
		
		resultMap.put("resultDesc", save);
		return resultMap;
		
	}
	
	@RequestMapping("/find")
	@Transactional
	public Map<String, Object> findWordInfo(){
		Map<String, Object> resultMap =  new HashMap<String, Object>();
		
		List<WordInfo> findAll = wordInfoService.findAll();
		
		if (findAll != null) {
			resultMap.put("resultCode", "success");
		} else {
			resultMap.put("resultCode", "fail");
		}
		
		
		resultMap.put("resultDesc", findAll);
		return resultMap;
		
	}
}
