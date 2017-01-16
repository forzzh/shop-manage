package cn.shop.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.shop.manage.pojo.ContentCategory;

@Service
public class ContentCategoryService extends BaseService<ContentCategory> {

	public void saveContentCategory(ContentCategory contentCategory) {
		contentCategory.setId(null);
		contentCategory.setIsParent(false);
		contentCategory.setStatus(1);
		contentCategory.setSortOrder(1);
		save(contentCategory);
		
		ContentCategory parent = getById(contentCategory.getId());
		if (!parent.getIsParent()) {
			parent.setIsParent(true);
			update(parent);
		}
		
	}

	public void deleteAll(ContentCategory contentCategory) {
		List<Object> ids = new ArrayList<Object>();
		
		findAllBySubNode(ids, contentCategory.getId());
		
		deleteByIds(ids);
		
		//判断该节点是否有兄弟节点，如果没有重置isParent
		ContentCategory category = new ContentCategory();
		category.setParentId(contentCategory.getParentId());
		List<ContentCategory> categories = queryListByWhere(category);
		if (categories == null || categories.isEmpty()) {
			ContentCategory parent = new ContentCategory();
			parent.setId(contentCategory.getParentId());
			parent.setIsParent(false);
			updateSelective(parent);
		}
	}

	private void findAllBySubNode(List<Object> ids, Long id) {
		ids.add(id);
		ContentCategory category = new ContentCategory();
		category.setParentId(id);
		
		List<ContentCategory> categories = queryListByWhere(category);
		for (ContentCategory contentCategory : categories) {
			if (contentCategory.getIsParent()) {
				findAllBySubNode(ids, contentCategory.getId());
			}
		}
		
	}
}
