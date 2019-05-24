package cn.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.app.dao.DataDictionaryMapper;
import cn.app.pojo.DataDictionary;
import cn.app.service.DataDictionaryService;

@Service("dataDictionaryService")
public class DataDictionaryServiceImpe implements DataDictionaryService{
	
	@Resource
	private DataDictionaryMapper datadictionarymapper;

	@Override
	public List<DataDictionary> gedataDictionaryList(String typeCode) {
		return datadictionarymapper.gedataDictionaryList(typeCode);
	}
	
}
