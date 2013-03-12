/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.domain;

/**
 * Task entity.
 * 
 * @author sk
 */
public class Task {

	private String name;
	private String masterDirPath;
	private String backupDirPath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMasterDirPath() {
		return masterDirPath;
	}

	public void setMasterDirPath(String masterDirPath) {
		this.masterDirPath = masterDirPath;
	}

	public String getBackupDirPath() {
		return backupDirPath;
	}

	public void setBackupDirPath(String backupDirPath) {
		this.backupDirPath = backupDirPath;
	}
	
}
