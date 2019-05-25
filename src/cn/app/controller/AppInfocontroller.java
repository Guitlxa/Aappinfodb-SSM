package cn.app.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.Constants;
import com.sun.org.apache.bcel.internal.generic.RET;

import cn.app.pojo.AppCategory;
import cn.app.pojo.AppInfo;
import cn.app.pojo.DataDictionary;
import cn.app.pojo.DevUser;
import cn.app.service.AppCategoryService;
import cn.app.service.AppInfoService;
import cn.app.service.DataDictionaryService;
import cn.app.tools.Page;

@Controller
@RequestMapping("/appInf")
public class AppInfocontroller {
	
	@Resource
	private AppInfoService appInfoService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private AppCategoryService appCategoryService;
	
	//跳转到多条件查询页面
/*	@RequestMapping("/litpageT")
	public String litpageT(){
		return "developer/appinfolist";
	}*/
	
	
	//根据多条件查询
	@RequestMapping("/appInfoLists")
	public String appInfoLists(@RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
			@RequestParam(value = "queryStatus", required = false) Integer queryStatus,
			@RequestParam(value = "queryFlatformId", required = false) Integer queryFlatformId,
			@RequestParam(value = "queryCategoryLevel1", required = false) Integer queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) Integer queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) Integer queryCategoryLevel3,
			@RequestParam(value = "currPageNo", required = false) Integer currPageNo, 
			HttpSession session,
			Model model){
		
		// 获得总记录数
		int totalCount = appInfoService.Count(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
		// 设置页面容量
		int pageSize = 5;
		//当前页码
		currPageNo=currPageNo!=null ?currPageNo:1;
		Page pages=new Page();
		pages.setCurrPageNo(currPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		//计算位置偏移量
		int from=(currPageNo-1)*pageSize;
		//分页查询APP列表信息
		List<AppInfo> appinfolist = appInfoService.getAppinfoListPage(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, from, pageSize);		
		//APP状态列表
		List<DataDictionary> statusList=dataDictionaryService.gedataDictionaryList("APP_STATUS"); 
		//APP所属平台列表
		List<DataDictionary> flatFormList=dataDictionaryService.gedataDictionaryList("APP_FLATFORM"); 
		//三级分类的下拉列表取值
		List<AppCategory> categoryLevel1List=appCategoryService.getAppCategoryListByParentId(null);  
		List<AppCategory> categoryLevel2List=null;
		List<AppCategory> categoryLevel3List=null;
		/*if(queryCategoryLevel2!=null && queryCategoryLevel2 !=0){
			categoryLevel2List=appCategoryService.getAppCategoryListByParentId(queryCategoryLevel2);
			model.addAttribute("categoryLevel2List", categoryLevel2List);
		}
		if(queryCategoryLevel3!=null && queryCategoryLevel3 !=0){
			categoryLevel3List=appCategoryService.getAppCategoryListByParentId(queryCategoryLevel3);
			model.addAttribute("categoryLevel3List", categoryLevel3List);
		}*/
		model.addAttribute("statusList", statusList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("categoryLevel2List", categoryLevel2List);
		model.addAttribute("categoryLevel3List", categoryLevel3List);
		model.addAttribute("appinfolist", appinfolist);
		model.addAttribute("pages", pages);
		
		//将查询结果保存到模型中
		model.addAttribute("querySoftwareName", querySoftwareName);
		model.addAttribute("queryStatus", queryStatus);
		model.addAttribute("queryFlatformId", queryFlatformId);
		model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
		
		return "developer/appinfolist";
	}
	
	/**
	 * 验证三级分类查询
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/litY",method=RequestMethod.GET)
	@ResponseBody
	public Object getAppCategoryByPid(Integer pid){
		List<AppCategory> list=appCategoryService.getAppCategoryListByParentId(pid);
		return list;
	}
	
	
	//跳转新增页面
	@RequestMapping("/addT")
	public String addT(){
		return "developer/appinfoadd";
	}
	
	
	//通过typeCode查询所属平台信息(验证)
	@RequestMapping(value=("/getDataDictionaryByCode"),method=RequestMethod.GET)
	@ResponseBody
	public Object getDataDictionaryByCode(@RequestParam("tcode")String tcode){
		List<DataDictionary> dataDictionaryLit= dataDictionaryService.gedataDictionaryList(tcode);
		return dataDictionaryLit;
	}
	
	//在新增操作时，对APKName进行唯一验证
	@RequestMapping(value=("/apkNameY"),method=RequestMethod.GET)
	@ResponseBody
	public Object apkNameY(@RequestParam("APKName")String APKName){
		Map<String, String> map=new HashMap<String, String>();
		if(APKName ==null || ("").equals(APKName)){
			map.put("APKName", "empty"); //APKName为空
		}else {
		AppInfo appInfo = appInfoService.getappInfoByidAndAPK(null, APKName);
			if(appInfo!=null){
				map.put("APKName", "exist"); //APKName已存在
			}else {
				map.put("APKName", "noexist"); //APKName不存在，可以使用
			}
		}
		return map;
	}
	
	//新增APP基础信息
	@RequestMapping(value="/addAPP",method=RequestMethod.POST)
	public String addAPP(AppInfo appInfo,Model model,
			@RequestParam(value = "a_logoPicPath", required = false) MultipartFile attachs,
			HttpSession session,HttpServletRequest request){
		
		String logoPicPath = null;//用于保存图片相对路径
		String logoLocPath = null;//用于保存图片绝对路径
		// 获取上传路径
		String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			if (!attachs.isEmpty()) { // 判断文件是否为空
				// 原文件名
				String oldFileName = attachs.getOriginalFilename();
				// 原文件后缀
				String prefix = FilenameUtils.getExtension(oldFileName);
				int filesize = 500000;	
				if (attachs.getSize() > filesize) {// 上传大小不得超过500KB
					model.addAttribute("fileUploadError","哥哥，太大了~受不鸟了啦~");//报错信息
				} else if (prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
						|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")) {
					// 获取上传之后的文件名
					String fileName = appInfo.getAPKName()+".jpg";
					File targetFile = new File(path, fileName);	
					// 如果上传文件的路径不存在则创建一个
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					// 上传文件到指定目录
					try {
						attachs.transferTo(targetFile);
					} catch (Exception e) {
						model.addAttribute("fileUploadError","文件上传失败");
						e.printStackTrace();
					}
						logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
						logoLocPath = path + File.separator + fileName;
				}
			}
			//新增人id
			DevUser devUser=(DevUser)session.getAttribute("devUserSession");
			appInfo.setCreatedBy(devUser.getId());
			appInfo.setDevId(devUser.getId());
			appInfo.setCreationDate(new Date());
			appInfo.setLogoPicPath(logoPicPath);
			appInfo.setLogoLocPath(logoLocPath);
			int result=appInfoService.addInfo(appInfo);
			if(result>0){
				return "redirect:/appInf/appInfoLists";
			}else {
				return "developer/appinfoadd";
			}
	}
	
	//根据id查询详情信息并跳页面（修改）
	@RequestMapping("/modifyT/{id}")
	public String modifyT(@PathVariable Integer id,Model model){
		AppInfo appInfo = appInfoService.getappInfoByidAndAPK(id, null);
		model.addAttribute("appInfo", appInfo);
		return "developer/appinfomodify";
	}
	
	//根据id查询详情信息并跳页面（查看）
	@RequestMapping("/ViewT/{id}")
	public String ViewT(@PathVariable Integer id,Model model){
		AppInfo appInfo = appInfoService.getappInfoByidAndAPK(id, null);
		model.addAttribute("appInfo", appInfo);
		return "developer/appinfoview";
		}
	
	//根据id删除图片信息
	@RequestMapping(value="/delePhotho",method=RequestMethod.GET)
	@ResponseBody
	public Object delePhotho(@RequestParam("id") Integer id){
		Map<String,String> map=new HashMap<String,String>();
		if(id==null || id==0){
			map.put("result", "failed");
		}else {
			//根据id查询详情对象信息
			AppInfo appInfo=appInfoService.getappInfoByidAndAPK(id, null);
			String logoLocPath=appInfo.getLogoLocPath();//得到上传图片的绝对路径
			//根据图片的结对路径创建一个File对象
			File file=new File(logoLocPath);
			if(file.exists()){ //如果路径存在
				if(file.delete()){ //如果返回true则删除物理文件
					if(appInfoService.deleLogPath(id)){//删除数据库数据信息
						//整个删除成功！
						map.put("result", "success");
					}
				}
			}else {
				//文件不存在
				map.put("result", "noexists");
			}
			
		}
		return map;
	}
	
	//修改APP信息
	@RequestMapping(value=("/modifyAppInfo"),method=RequestMethod.POST)
	public String modifyAppInfo(AppInfo appInfo,Model model,
			@RequestParam(value = "attach", required = false) MultipartFile attachs,
			HttpSession session,HttpServletRequest request){
		String logoPicPath = null;//用于保存图片相对路径
		String logoLocPath = null;//用于保存图片绝对路径
		// 获取上传路径
		String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			if (!attachs.isEmpty()) { // 判断文件是否为空
				// 原文件名
				String oldFileName = attachs.getOriginalFilename();
				// 原文件后缀
				String prefix = FilenameUtils.getExtension(oldFileName);
				int filesize = 500000;	
				if (attachs.getSize() > filesize) {// 上传大小不得超过500KB
					model.addAttribute("fileUploadError","哥哥，太大了~受不鸟了啦~");//报错信息
				} else if (prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
						|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")) {
					// 获取上传之后的文件名
					String fileName = appInfo.getAPKName()+".jpg";
					File targetFile = new File(path, fileName);	
					// 如果上传文件的路径不存在则创建一个
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					// 上传文件到指定目录
					try {
						attachs.transferTo(targetFile);
					} catch (Exception e) {
						model.addAttribute("fileUploadError","文件上传失败");
						e.printStackTrace();
					}
						logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
						logoLocPath = path + File.separator + fileName;
				}
			}
			//修改人id
			DevUser devUser=(DevUser)session.getAttribute("devUserSession");
			appInfo.setModifyBy(devUser.getId());
			appInfo.setModifyDate(new Date());
			appInfo.setDevId(devUser.getId());
			appInfo.setLogoPicPath(logoPicPath);
			appInfo.setLogoLocPath(logoLocPath);
			int result=appInfoService.modifyInfo(appInfo);
			if(result>0){
				return "redirect:/appInf/appInfoLists";
			}else {
				System.out.println("shdsh收到货萨达十大us丢啊按时发达的");
				return "developer/appinfomodify";
			}
	}
	
	//跳转查看页面
		@RequestMapping("/viewT")
		public String viewT(){
			return "developer/appinfoview";
		}
}
