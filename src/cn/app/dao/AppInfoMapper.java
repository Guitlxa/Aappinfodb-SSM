package cn.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.app.pojo.AppInfo;

public interface AppInfoMapper {
	
	
	/**
	 * 根据多条件查询信息数量
	 * @param querySoftwareName
	 * @param queryStatus
	 * @param queryFlatformId
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @return
	 */
	Integer Count(@Param("querySoftwareName")String querySoftwareName,
				  @Param("queryStatus")Integer queryStatus,
				  @Param("queryFlatformId")Integer queryFlatformId,
				  @Param("queryCategoryLevel1")Integer queryCategoryLevel1,
				  @Param("queryCategoryLevel2")Integer queryCategoryLevel2,
				  @Param("queryCategoryLevel3")Integer queryCategoryLevel3);
	
	/**
	 * 多条件分页查询列表
	 * @param querySoftwareName
	 * @param queryStatus
	 * @param queryFlatformId
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param currPage
	 * @return
	 */
	List<AppInfo> getAppinfoListPage(@Param("querySoftwareName")String querySoftwareName,
			  @Param("queryStatus")Integer queryStatus,
			  @Param("queryFlatformId")Integer queryFlatformId,
			  @Param("queryCategoryLevel1")Integer queryCategoryLevel1,
			  @Param("queryCategoryLevel2")Integer queryCategoryLevel2,
			  @Param("queryCategoryLevel3")Integer queryCategoryLevel3, 
			  @Param("from") Integer from,
			  @Param("pageSize") Integer pageSize);
	
	
	//查询APK是否存在
	AppInfo getappInfoByidAndAPK(@Param("id")Integer id,
								@Param("APKName")String APKName);
	
	//新增方法
	int addInfo(AppInfo appInfo);
}
