package cn.shop.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.shop.common.bean.EasyUIResult;
import cn.shop.manage.pojo.Content;
import cn.shop.manage.service.ContentService;

@Controller
@RequestMapping("content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveContent(Content content) {
		
		try {
			contentService.save(content);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryContentList(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "20") Integer rows, Long categoryId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(contentService.queryContentList(page, rows, categoryId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
