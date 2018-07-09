package com.learn.word.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.word.domain.WordInfo;
import com.learn.word.jpa.dao.WordInfoRepository;
/**
 * 服务层
 * @author PC
 *
 */
@Service
public class WordInfoServiceImpl implements WordInfoService{

	@Autowired
	private WordInfoRepository wordInfoRepository;
	
	@Override
	public WordInfo save(WordInfo wordInfo) {
		if (wordInfo == null) {
			return null;
		}
		Date creatTime = wordInfo.getCreatTime();
		Date nowDate =  new Date();
		
		
		if (creatTime == null) {
			wordInfo.setCreatTime(nowDate);
		}
		wordInfo.setUpdateTime(nowDate);//每次保存都更新最新的时间
		return wordInfoRepository.save(wordInfo);
		
	}

	@Override
	public List<WordInfo> findAll() {
		return wordInfoRepository.findAll();
	}

	@Override
	public WordInfo findByName(String wordName) {
		return wordInfoRepository.findByWordName(wordName);
	}

}
