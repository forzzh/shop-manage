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
import org.springframework.web.bind.annotation.RequestParam;

import cn.shop.manage.pojo.ItemDesc;
import cn.shop.manage.service.ItemDescService;

@Controller
@RequestMapping("item/desc")
public class ItemDescController {
	
	@Autowired
	private ItemDescService itemDescService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemDescController.class);
	
	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public ResponseEntity<ItemDesc> queryByItemId(@PathVariable("itemId") Long itemId) {
		try {
			ItemDesc itemDesc = itemDescService.getById(itemId);
			if (itemDesc == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(itemDesc);
		} catch (Exception e) {
			LOGGER.error("查询商品详细出错！itemId = {}", itemId);
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
