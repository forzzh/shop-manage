package cn.shop.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.shop.common.bean.ItemCatResult;
import cn.shop.manage.service.ItemCatService;

@Controller
@RequestMapping("api/item/cat")
public class ApiItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
//	private static final ObjectMapper MAPPER = new ObjectMapper();
	
//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity<String> queryByItemId(String callblack) {
//		try {
//			ItemCatResult tree = itemCatService.queryAllToTree();
//			
//			String json = MAPPER.writeValueAsString(tree);
//			if (StringUtils.isEmpty(callblack)) {
//				return ResponseEntity.ok(json);
//			} else {
//				StringBuilder builder = new StringBuilder(callblack);
//				builder.append("(").append(json).append(")");
//				return ResponseEntity.ok(builder.toString());
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//	}
//	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ItemCatResult> queryByItemId() {
		try {
			ItemCatResult tree = itemCatService.queryAllToTree();
			return ResponseEntity.ok(tree);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
