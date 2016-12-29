package cn.shop.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.shop.manage.pojo.ItemCat;
import cn.shop.manage.service.ItemCatService;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {

	
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(@RequestParam(value = "id", defaultValue = "0") Long pid) {
		try {
			ItemCat itemCat = new ItemCat();
			itemCat.setParentId(pid);
			List<ItemCat> itemCats = itemCatService.queryListByWhere(itemCat);
//			List<ItemCat> itemCats = itemCatService.queryItemCatListByParentId(pid);
			
			if (itemCats == null || itemCats.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(itemCats);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
}
