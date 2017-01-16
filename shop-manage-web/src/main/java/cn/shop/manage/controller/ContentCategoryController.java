package cn.shop.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.shop.manage.pojo.ContentCategory;
import cn.shop.manage.service.ContentCategoryService;

@Controller
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ContentCategory>> queryListByParentId(@RequestParam(value = "id", defaultValue = "0") Long pid) {
		try {
			ContentCategory contentCategory = new ContentCategory();
			contentCategory.setParentId(pid);
			List<ContentCategory> categories = contentCategoryService.queryListByWhere(contentCategory);
			
			if (categories == null || categories.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(categories);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategory) {
		
		try {
			contentCategoryService.saveContentCategory(contentCategory);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> rename(Long id, String name) {
		
		try {
			ContentCategory record = new ContentCategory();
			record.setId(id);
			record.setName(name);
			contentCategoryService.updateSelective(record);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	public ResponseEntity<Void> delete(ContentCategory contentCategory) {
		try {
			contentCategoryService.deleteAll(contentCategory);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
