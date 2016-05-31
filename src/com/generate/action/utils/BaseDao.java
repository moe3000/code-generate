package com.generate.action.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseDao<T > {

	/**
	 * 更新对象
	 * @param item 对象参数
	 * @return 返回更新过的实体
	 */
	Boolean update(T item);

	/**
	 * 保存对象
	 * @param item 添加实体
	 * @return 添加实体的ID
	 */
	Long insert(T item);

	/**
	 * 保存对象集合
	 * @param ts 对象集合
	 * @param nameSpace ibatis引用名
	 * @return 更新是否成功
	 */
	Boolean insert(Collection<T> ts, String nameSpace);
	
	/**
	 * 根据ID数组删除信息
	 * @param ids ID数组
	 * @param nameSpace ibatis引用名
	 * @return 是否成功
	 */
	Boolean delete(Serializable[] ids, String nameSpace);

	/**
	 * 根据ID数组删除信息
	 * @param id ID
	 * @param nameSpace ibatis引用名
	 * @return 是否成功
	 */
	Boolean delete(Serializable id, String nameSpace);

	/**
	 * 根据ID查询信息
	 * @param id ID
	 * @param nameSpace ibatis引用名
	 * @return 查询信息
	 */
	T getItem(Long id, String nameSpace);
	
	/**
	 * 根据封装实体参数信息查询
	 * @param param 实体参数信息
	 * @return 查询信息
	 */
	T getItemByObj(T param);
	
	/**
	 * 根据查询参数查询信息
	 * @param paramMap ID
	 * @param nameSpace ibatis引用名
	 * @return 查询信息
	 */
	T getItemByMap(Map<String, Object> paramMap, String nameSpace);

	/**
	 * 根据封装map参数信息查询
	 * @param paramMap 参数分装map
	 * @param nameSpace ibatis引用名
	 * @return 查询信息
	 */
	List<T> getItemListByMap(Map<String, Object> paramMap, String nameSpace);
	
	/**
	 * 根据实体参数信息查询表信息
	 * @param param 参数分装实体
	 * @param nameSpace ibatis引用名
	 * @return 查询信息
	 */
	List<T> getItemList(T param, String nameSpace);
	
	/**
	 * 根据实体参数信息查询表信息
	 * @param ids ID数组
	 * @param nameSpace ibatis引用名
	 * @return 查询信息
	 */
	List<T> getItemListByIds(Serializable[] ids, String nameSpace);
	
	/**
	 * 根据参数查询信息数量
	 * @param paramMap 参数分装map
	 * @param nameSpace ibatis引用名
	 * @return 信息数量
	 */
    Integer getItemCount(Map<String, Object> paramMap, String nameSpace);
    
   /**
    * 根据map参数进行分页查询
    * @param page 分页信息
    * @param paramMap 查询参数分装map
    * @param nameSpace ibatis引用名
    * @return 分页信息
    */
    Pagination getPageListByMap(Pagination page, Map<String, Object> paramMap, String nameSpace);
    
    /**
     * 根据实体参数进行分页查询
     * @param page 分页信息
     * @param param 查询参数分装map
     * @param nameSpace ibatis引用名
     * @return 分页信息
     */
    Pagination getPageList(Pagination page, T param, String nameSpace);
    
}
