package cn.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.app.pojo.AppCategory;

public interface AppCategoryMapper {
	
	/**
	 * 根据父节点查询下级目录
	 * @param parentId
	 * @return
	 */
	public List<AppCategory> getAppCategoryListByParentId(@Param("parentId")Integer parentId);	
}
