package cn.shop.manage.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.shop.common.bean.EasyUIResult;
import cn.shop.manage.pojo.Item;
import cn.shop.manage.service.ItemDescService;
import cn.shop.manage.service.ItemService;

@Controller
@RequestMapping("item")
public class ItemController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item, String desc, String itemParams) {
		
		try {
			LOGGER.info("新增商品, item={}, desc={}", item, desc);
			if (StringUtils.isEmpty(item.getTitle())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			Boolean itemStatus = itemService.saveItem(item, desc, itemParams);
			if (!itemStatus) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			LOGGER.info("新增商品成功, itemId={}", item.getCid());
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("新增商品出错！item" + item, e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryItemList(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "20") Integer rows) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(itemService.queryItemList(page, rows));
		} catch (Exception e) {
			LOGGER.error("查询商品出错！page = {}, row = {}", page, rows);
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item, String desc, String itemParams) {
		
		try {
			LOGGER.info("修改商品, item={}, desc={}", item, desc);
			if (StringUtils.isEmpty(item.getTitle())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			Boolean itemStatus = itemService.updateItem(item, desc, itemParams);
			if (!itemStatus) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			LOGGER.info("修改商品成功, itemId={}", item.getCid());
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("修改商品出错！item" + item, e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
}
