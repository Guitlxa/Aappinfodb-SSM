package cn.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.app.pojo.DataDictionary;

public interface DataDictionaryService {
	/**
	 * 根据typeCode查询APP状态
	 * @param typeCode
	 * @return
	 */
	public List<DataDictionary> gedataDictionaryList(String typeCode);
}
