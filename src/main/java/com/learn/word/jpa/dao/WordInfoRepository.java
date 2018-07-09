package com.learn.word.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.learn.word.domain.WordInfo;




/**
 * WordInfoJPA数据接口
 * 用于提供数据库查询的各种语句操作
 * @author PC
 *
 */
public interface WordInfoRepository extends JpaRepository<WordInfo,Long> {

	
	/**
	 * 通过名字找人
	 * @param name
	 * @return
	 */
	public WordInfo findByWordName(@Param("wordName") String wordName);
	
	
}
