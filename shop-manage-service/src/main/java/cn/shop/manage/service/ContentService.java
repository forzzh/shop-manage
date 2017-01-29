package cn.shop.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.shop.common.bean.EasyUIResult;
import cn.shop.manage.mapper.ContentMapper;
import cn.shop.manage.pojo.Content;

@Service
public class ContentService extends BaseService<Content> {
	
	@Autowired
	private ContentMapper contentMapper;

	public EasyUIResult queryContentList(Integer page, Integer rows,
			Long categoryId) {
		PageHelper.startPage(page, rows);
		
		List<Content> list = contentMapper.queryContentList(categoryId);
		
		PageInfo<Content> pageInfo = new PageInfo<Content>(list);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}


}
