package cn.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.app.pojo.AppInfo;

public interface AppInfoService {
	/**
	 * 根据多条件查询信息数量
	 * @param softwareName 软件名称
	 * @param status 
	 * @param flatformId
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @return
	 */
	Integer Count(String querySoftwareName,
			  Integer queryStatus,
			  Integer queryFlatformId,
			  Integer queryCategoryLevel1,
			  Integer queryCategoryLevel2,
			  Integer queryCategoryLevel3);
	
	/**
	 * 多条件分页查询列表
	 * @param softwareName
	 * @param status
	 * @param flatformId
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param from
	 * @param pageSize
	 * @return
	 */
	List<AppInfo> getAppinfoListPage(String querySoftwareName,
			 Integer queryStatus,
			  Integer queryFlatformId,
			  Integer queryCategoryLevel1,
			 Integer queryCategoryLevel2,
			  Integer queryCategoryLevel3, 
			   Integer from,
			  Integer pageSize);
	
	//查询APK是否存在
		AppInfo getappInfoByidAndAPK(Integer id,
									String APKName);
		
		//新增方法
		int addInfo(AppInfo appInfo);
		
		//新增方法
		int modifyInfo(AppInfo appInfo);
		
		//根据id删除图片的路径(数据库中)
		boolean deleLogPath(Integer id);
}
