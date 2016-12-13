//package com.zhang.oa.service;
//
//import java.util.List;
//
//import org.activiti.engine.impl.RepositoryServiceImpl;
//import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
//import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
//import org.activiti.engine.impl.pvm.PvmActivity;
//import org.activiti.engine.impl.pvm.PvmTransition;
//import org.activiti.engine.impl.pvm.process.ActivityImpl;
//import org.activiti.engine.impl.task.TaskDefinition;
//import org.activiti.engine.repository.ProcessDefinition;
//import org.apache.commons.lang.StringUtils;
//
//public class Test {
//	public static TaskDefinition getNextTaskDefinition(String key, String activityId, String elString, boolean bFlag) {
//		ProcessDefinition processDefinition = getNewProcessDefinition(key);// 根据流程定义key获得最新的流程定义信息
//		if (processDefinition != null) {
//			ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
//					.getDeployedProcessDefinition(processDefinition.getId());
//			ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);// 当前节点
//			if ("userTask".equals(activityImpl.getProperty("type")) && !bFlag) {
//				TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activityImpl.getActivityBehavior())
//						.getTaskDefinition();
//				return taskDefinition;
//			} else {
//				List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();
//				List<PvmTransition> outpvmTransitions = null;
//				for (PvmTransition pvmTransition : pvmTransitions) {
//					PvmActivity pa = pvmTransition.getDestination();// 获取所有的终点节点
//					if ("exclusiveGateway".equals(pa.getProperty("type"))) {
//						outpvmTransitions = pa.getOutgoingTransitions();
//						if (outpvmTransitions.size() == 1) {
//							return getNextTaskDefinition(key, pa.getId(), elString, false);
//						} else if (outpvmTransitions.size() > 1) {
//							for (PvmTransition outPvmTransition : outpvmTransitions) {
//								Object object = outPvmTransition.getProperty("conditionText");
//								if (elString.equals(StringUtils.trim(object.toString()))) {
//									PvmActivity pvmActivity = outPvmTransition.getDestination();
//									return getNextTaskDefinition(key, pvmActivity.getId(), elString, false);
//								}
//							}
//						}
//					} else if ("userTask".equals(pa.getProperty("type"))) {
//						return ((UserTaskActivityBehavior) ((ActivityImpl) pa).getActivityBehavior())
//								.getTaskDefinition();
//					}
//				}
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * 根据key获得一个最新的流程定义
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public static ProcessDefinition getNewProcessDefinition(String key) {
//		// 根据key查询已经激活的流程定义，并且按照版本进行降序。那么第一个就是将要得到的最新流程定义对象
//		List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
//				.processDefinitionKey(key).orderByProcessDefinitionVersion().desc().list();
//		if (processDefinitionList.size() > 0) {
//			return processDefinitionList.get(0);
//		}
//		return null;
//	}
//}
