/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/rmi/Task.java,v $
 * $Revision: 1.3 $
 * $Date: 2010-11-09 17:20:16 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.example.rmi;

import java.rmi.RemoteException;

import de.willuhn.datasource.rmi.DBObject;


/**
 * Interface of the business object for tasks.
 * According to the SQL table, we define some getter an setter here.
 * <pre>
 * CREATE TABLE task (
 *   id NUMERIC default UNIQUEKEY('task'),
 *   project_id int(4) NOT NULL,
 *   name varchar(255) NOT NULL,
 *   comment text NOT NULL,
 *   effort double,
 *   UNIQUE (id),
 *   PRIMARY KEY (id)
 * );
 * </pre>
 * <br>Getters and setters for the primary key (id) are not needed.
 * Every one of the following methods has to throw a RemoteException.
 * <br>
 */
public interface Task extends DBObject
{
	/**
	 * Returns the project for this task.
   * @return the project.
   * @throws RemoteException
   */
  public Project getProject() throws RemoteException;
	
	/**
	 * Stores the Project for this task.
   * @param project
   * @throws RemoteException
   */
  public void setProject(Project project) throws RemoteException;

	/**
	 * Returns the name of this task.
   * @return name of the task.
   * @throws RemoteException
   */
  public String getName() throws RemoteException;

	/**
	 * Stores the name of the task.
   * @param name name of the task.
   * @throws RemoteException
   */
  public void setName(String name) throws RemoteException;
  
  /**
   * Returns the comment for the task.
   * @return comment.
   * @throws RemoteException
   */
  public String getComment() throws RemoteException;

	/**
	 * Stores the task comment.
   * @param comment task comment.
   * @throws RemoteException
   */
  public void setComment(String comment) throws RemoteException;

	/**
	 * Returns the effort for this task.
   * @return effort.
   * @throws RemoteException
   */
  public double getEffort() throws RemoteException;

	/**
	 * Stores the effort for this task.
   * @param effort effort.
   * @throws RemoteException
   */
  public void setEffort(double effort) throws RemoteException;

}


/**********************************************************************
 * $Log: Task.java,v $
 * Revision 1.3  2010-11-09 17:20:16  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/