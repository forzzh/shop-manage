package cn.shop.manage.service;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.shop.manage.pojo.BasePojo;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public abstract class BaseService<T extends BasePojo> {
	
	@Autowired
	private Mapper<T> mapper;
	
	private Class<T> clazz;

	public BaseService() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}

//	public abstract Mapper<T> getmapper;
	
	/**
	 * 更具id查询
	 * @param id
	 * @return
	 */
	public T getById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<T> queryAll() {
		return mapper.select(null);
	}
	
	/**
	 * 根据条件查询一条记录
	 * @param record
	 * @return
	 */
	public T queryOne(T record) {
		return mapper.selectOne(record);
	}
	
	/**
	 * 根据条件查询多条数据
	 * @param record
	 * @return
	 */
	public List<T> queryListByWhere(T record) {
		return mapper.select(record);
	}
	
	/**
	 * 分页查询
	 * @param record
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<T> queryPageByWhere(T record, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<T> list = mapper.select(record);
		
		return new PageInfo<T>(list);
	}
	
	public Integer save(T record) {
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		return mapper.insert(record);
	}
	
	/**
	 * 选择不为null的插入
	 * @param record
	 * @return
	 */
	public Integer saveSelective(T record) {
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		return mapper.insertSelective(record);
	}
	
	public Integer update(T record) {
		record.setUpdated(new Date());
		return mapper.updateByPrimaryKey(record);
	}
	
	public Integer updateSelective(T record) {
		record.setUpdated(new Date());
		record.setCreated(null);
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	public Integer deleteById(Long id) {
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer deleteByIds(List<Object> ids) {
		Example example = new Example(clazz);
		
		example.createCriteria().andIn("id", ids);
		
		return mapper.deleteByExample(example);
	}
	
	public Integer deleteByWhere(T record) {
		return mapper.delete(record);
	}
}
