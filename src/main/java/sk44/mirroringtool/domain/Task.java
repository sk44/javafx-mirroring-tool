/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Task entity.
 * 
 * @author sk
 */
@Entity
public class Task {

	@Id
	private Long id;
	private String name;
	private String masterDirPath;
	private String backupDirPath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
