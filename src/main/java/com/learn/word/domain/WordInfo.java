package com.learn.word.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
/**
 * 
 * @author PC
 *
 */
@Entity
@Table(name="tb_word_info", uniqueConstraints = { @UniqueConstraint(columnNames = { "word_name" }) })
public class WordInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2455211177002603186L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="word_id")
	private Long wordId;//
	
	/**
	 * 单词
	 */
	
	@Column(name="word_name", length = 64)
	private String wordName;//
	
	/**
	 * 单词详解
	 * 可进行分表
	 */
	@Column(name="word_detail")
	private String wordDetail;//
	
	/**
	 * 是否已学0:未学，1：已学
	 */
	@Column(name="status", length = 4)
	private String status;
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME")
	private Date creatTime;
	/**
	 * 更新时间
	 */
	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	
	/**
	 * 类型，名词：0，动词：1
	 */
	@Column(name = "type",length = 2)
	private String type;

	public Long getWordId() {
		return wordId;
	}

	public void setWordId(Long wordId) {
		this.wordId = wordId;
	}

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String name) {
		this.wordName = name;
	}

	public String getWordDetail() {
		return wordDetail;
	}

	public void setWordDetail(String wordDetail) {
		this.wordDetail = wordDetail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creatTime == null) ? 0 : creatTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((wordName == null) ? 0 : wordName.hashCode());
		result = prime * result + ((wordDetail == null) ? 0 : wordDetail.hashCode());
		result = prime * result + ((wordId == null) ? 0 : wordId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordInfo other = (WordInfo) obj;
		if (creatTime == null) {
			if (other.creatTime != null)
				return false;
		} else if (!creatTime.equals(other.creatTime))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (wordName == null) {
			if (other.wordName != null)
				return false;
		} else if (!wordName.equals(other.wordName))
			return false;
		if (wordDetail == null) {
			if (other.wordDetail != null)
				return false;
		} else if (!wordDetail.equals(other.wordDetail))
			return false;
		if (wordId == null) {
			if (other.wordId != null)
				return false;
		} else if (!wordId.equals(other.wordId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WordInfo [wordId=" + wordId + ", wordName=" + wordName + ", wordDetail=" + wordDetail + ", status=" + status
				+ ", creatTime=" + creatTime + ", updateTime=" + updateTime + ", type=" + type + "]";
	}
	
	
	
}
