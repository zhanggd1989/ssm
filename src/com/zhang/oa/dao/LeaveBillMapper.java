package com.zhang.oa.dao;

import java.util.List;

import com.zhang.oa.domain.LeaveBill;

/**
 * 请假单-Mapper
 * @author Brian.Zhang
 * Jun 29, 2016-3:22:23 PM
 */
public interface LeaveBillMapper {

	/**
	 * 获取所有LeaveBill对象
	 * 
	 * @author zhanggd
	 * @param start
	 * @param number
	 * @return
	 * @throws  
	 * Jun 29, 2016-3:15:20 PM
	 */
	public List<LeaveBill> listAllLeaveBills(int start, int number);

	/**
	 * 获取所有LeaveBill对象条数
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 29, 2016-3:16:39 PM
	 */
	public int listAllLeaveBillsCount();

	/**
	 * 增加LeaveBill对象
	 * 
	 * @author zhanggd
	 * @param leaveBill
	 * @throws  
	 * Jun 29, 2016-3:17:17 PM
	 */
	public void addLeaveBill(LeaveBill leaveBill);

	/**
	 * 编辑LeaveBill对象
	 * 
	 * @author zhanggd
	 * @param leaveBill
	 * @throws  
	 * Jun 29, 2016-3:17:17 PM
	 */
	public void editLeaveBill(LeaveBill leaveBill);

	/**
	 * 删除LeaveBill对象
	 * 
	 * @author zhanggd
	 * @param leaveBill
	 * @throws  
	 * Jun 29, 2016-3:17:17 PM
	 */
	public void deleteLeaveBillById(int id);

	/**
	 * 根据请假单ID获取LeaveBill对象
	 * 
	 * @author zhanggd
	 * @param leaveBillId
	 * @return
	 * @throws  
	 * Jul 5, 2016-3:29:19 PM
	 */
	public LeaveBill findLeaveBillById(String leaveBillId);

	/**
	 * 更新请假单状态
	 * 
	 * @author zhanggd
	 * @param leaveBill
	 * @throws  
	 * Jul 28, 2016-4:19:51 PM
	 */
	public void updateState(LeaveBill leaveBill);

}