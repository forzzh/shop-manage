package cn.shop.manage.mapper;

import java.util.List;

import cn.shop.manage.pojo.Content;

import com.github.abel533.mapper.Mapper;

public interface ContentMapper extends Mapper<Content> {

	List<Content> queryContentList(Long categoryId);
}
