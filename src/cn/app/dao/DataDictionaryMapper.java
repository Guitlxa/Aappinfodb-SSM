package cn.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.app.pojo.DataDictionary;

public interface DataDictionaryMapper {
	
	/**
	 * 根据typeCode查询APP状态
	 * @param typeCode
	 * @return
	 */
	public List<DataDictionary> gedataDictionaryList(@Param("typeCode") String typeCode);
}
