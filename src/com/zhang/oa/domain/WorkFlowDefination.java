package com.zhang.oa.domain;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * 流程定义
 * @author Brian.Zhang 
 * Jul 4, 2016-1:33:05 PM
 */
public class WorkFlowDefination implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7687121127591914104L;

	private String name;
	private MultipartFile sourceFile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultipartFile getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(MultipartFile sourceFile) {
		this.sourceFile = sourceFile;
	}

	@Override
	public String toString() {
		return "WorkFlowDefination [name=" + name + ", sourceFile=" + sourceFile + "]";
	}
	
}