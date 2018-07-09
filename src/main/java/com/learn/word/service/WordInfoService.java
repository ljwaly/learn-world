package com.learn.word.service;

import java.util.List;

import com.learn.word.domain.WordInfo;


public interface WordInfoService {
	public WordInfo save(WordInfo wordInfo);

	public List<WordInfo> findAll();

	public WordInfo findByName(String wordName);
}
