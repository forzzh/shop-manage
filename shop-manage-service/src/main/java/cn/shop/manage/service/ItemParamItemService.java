package cn.shop.manage.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;

import cn.shop.manage.mapper.ItemParamItemMapper;
import cn.shop.manage.pojo.ItemParamItem;

@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {
	
	@Autowired
	private ItemParamItemMapper itemParamItemMapper;

	public Integer updateItemParam(Long itemId, String itemParams) {
		ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setParamData(itemParams);
		itemParamItem.setItemId(itemId);
		itemParamItem.setUpdated(new Date());
		
		Example example = new Example(ItemParamItem.class);
		example.createCriteria().andEqualTo("itemId", itemId);
		
		return itemParamItemMapper.updateByExampleSelective(itemParamItem, example);
	}

}
