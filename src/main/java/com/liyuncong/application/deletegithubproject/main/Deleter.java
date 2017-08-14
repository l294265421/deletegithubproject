package com.liyuncong.application.deletegithubproject.main;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liyuncong.application.deletegithubproject.common.FileUtil;
import com.liyuncong.application.deletegithubproject.common.UserInfo;

/**
 * 
 * @author liyuncong
 *
 */
public abstract class Deleter {
	private Logger logger = LoggerFactory.getLogger(Deleter.class);
	protected UserInfo userInfo;
	protected static final String loginPageUrl = "https://github.com/login";
	private List<String> projectNames;
	public void delete() {
		if(loadUserInfo() && loadProjectNames() && init()) {
			int emptyProjectNameCounter = 0;
			for (String projectName : projectNames) {
				if (projectName == null || projectName.length() == 0) {
					emptyProjectNameCounter++;
					continue;
				}
				boolean deleteResult = deleteProject(projectName);
				if (!deleteResult) {
					logger.error("failed to delete project: {}", projectName);
				}
			}
			destroy();
			logger.info("{} empty project name", emptyProjectNameCounter);
		}
	}
	
	private boolean loadUserInfo() {
		try {
			userInfo = new UserInfo();
		} catch (IOException e) {
			logger.error("failed to load userInfo:{}", e);
			return false;
		}
		return true;
	}
	
	private boolean loadProjectNames() {
		try {
			projectNames = FileUtil.readAllLines("conf/project_name");
		} catch (IOException e) {
			logger.error("failed to load projectNames:{}", e);
			return false;
		}
		return true;
	}
	
	protected abstract boolean init();
	protected abstract boolean deleteProject(String projectName);
	protected abstract void destroy();
}
