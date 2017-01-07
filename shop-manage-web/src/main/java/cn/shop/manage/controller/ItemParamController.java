package cn.shop.manage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.shop.manage.pojo.ItemParam;
import cn.shop.manage.service.ItemParamService;

@Controller
@RequestMapping("item/param")
public class ItemParamController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemParamController.class);
	
	@Autowired
	private ItemParamService itemParamService;

	@RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)
	public ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("itemCatId") Long itemCatId) {
		try {
			
			ItemParam record = new ItemParam();
			record.setItemCatId(itemCatId);
			ItemParam itemParam = itemParamService.queryOne(record);
			
			if (itemParam == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(itemParam);
		} catch (Exception e) {
			LOGGER.error("查询商品详细出错！itemCatId = {}", itemCatId);
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(value = "{itemCatId}", method = RequestMethod.POST)
	public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId") Long itemCatId, String paramData) {
		try {
			LOGGER.info("新增商品模板, itemCatId={}", itemCatId);
			ItemParam itemParam = new ItemParam();
			itemParam.setItemCatId(itemCatId);
			itemParam.setParamData(paramData);
			itemParamService.save(itemParam);
			LOGGER.info("新增商品模板成功, itemId={}", itemCatId);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("新增商品模板出错！item" + itemCatId, e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
