package cn.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.app.pojo.AppCategory;

public interface AppCategoryService {
	/**
	 * 根据父节点查询下级目录
	 * @param parentId
	 * @return
	 */
	public List<AppCategory> getAppCategoryListByParentId(Integer parentId);
}
