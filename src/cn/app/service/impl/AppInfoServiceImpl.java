package cn.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.app.dao.AppInfoMapper;
import cn.app.pojo.AppInfo;
import cn.app.service.AppInfoService;


@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService{

	@Resource
	private AppInfoMapper appinfomapper;

	@Override
	public Integer Count(String querySoftwareName, Integer queryStatus, Integer queryFlatformId,
			Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3) {
		return appinfomapper.Count(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
	}

	@Override
	public List<AppInfo> getAppinfoListPage(String querySoftwareName, Integer queryStatus, Integer queryFlatformId,
			Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer from,
			Integer pageSize) {
		return appinfomapper.getAppinfoListPage(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, from, pageSize);
	}

	@Override
	public AppInfo getappInfoByidAndAPK(Integer id, String APKName) {
		return appinfomapper.getappInfoByidAndAPK(id, APKName);
	}

	@Override
	public int addInfo(AppInfo appInfo) {
		return appinfomapper.addInfo(appInfo);
	}


	
	
	}
	
