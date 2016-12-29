package cn.shop.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.shop.common.bean.EasyUIResult;
import cn.shop.manage.mapper.ItemMapper;
import cn.shop.manage.pojo.Item;
import cn.shop.manage.pojo.ItemDesc;

@Service
public class ItemService extends BaseService<Item> {
	
	@Autowired
	private ItemDescService itemDescService;
	
	@Autowired
	private ItemMapper itemMapper;
	
	public Boolean saveItem(Item item, String desc) {
		item.setStatus(1);
		item.setId(null);
		Integer itemCount = save(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		Integer itemdCount = itemDescService.save(itemDesc);
		
		return itemCount.intValue() == 1 && itemdCount.intValue() == 1;
	}

	public EasyUIResult queryItemList(Integer page, Integer rows) {
		
		// 设置分页参数
        PageHelper.startPage(page, rows);

        // 查询User数据
        Example example = new Example(Item.class);
        example.setOrderByClause("updated DESC"); // 设置排序条件
        
        List<Item> items = itemMapper.selectByExample(example);
        
        // 获取分页后的信息
        PageInfo<Item> pageInfo = new PageInfo<Item>(items);

        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

	public Boolean updateItem(Item item, String desc) {
		
		item.setStatus(null);
		Integer itemCount = updateSelective(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		
		Integer itemdCount = itemDescService.updateSelective(itemDesc);
		
		return itemCount.intValue() == 1 && itemdCount.intValue() == 1;
	}

}
