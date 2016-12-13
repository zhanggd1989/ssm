package com.zhang.oa.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.zhang.sys.domain.User;

public class LeaveBill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5766686031852326555L;

	private int id;// 请假单ID
	private User applicant;// 请假人
	private String applyTime;// 申请时间
	private String content;// 请假事由
	@DateTimeFormat(pattern="yyyy-MM-dd")  
	private Date startTime;// 开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd")  
	private Date endTime;// 结束时间
	private String remarks;// 备注
	private int state;// 请假单状态:0:初始录入,1:开始审批,2:审批完成

	private String taskId;// 任务ID
	private String approval;// 任务审批人
	private String comment;// 任务批注
	private String outcome;// 任务连线

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getApproval() {
		return approval;
	}
	
	public void setApproval(String approval) {
		this.approval = approval;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	

	@Override
	public String toString() {
		return "LeaveBill [id=" + id + ", applicant=" + applicant + ", applyTime=" + applyTime + ", content=" + content
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", remarks=" + remarks + ", state=" + state
				+ ", taskId=" + taskId + ", comment=" + comment + ", outcome=" + outcome + ", approval=" + approval
				+ "]";
	}

}