package cn.shop.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.shop.common.bean.ItemCatResult;
import cn.shop.manage.service.ItemCatService;

@Controller
@RequestMapping("api/item/cat")
public class ApiItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ItemCatResult> queryByItemId(@PathVariable("itemId") Long itemId) {
		try {
			ItemCatResult tree = itemCatService.queryAllToTree();;
			if (tree == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(tree);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
