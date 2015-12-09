/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/server/TaskImpl.java,v $
 * $Revision: 1.4 $
 * $Date: 2010-11-09 17:20:16 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.example.server;

import java.rmi.RemoteException;

import de.willuhn.datasource.db.AbstractDBObject;
import de.willuhn.datasource.rmi.ObjectNotFoundException;
import de.willuhn.jameica.example.Settings;
import de.willuhn.jameica.example.rmi.Project;
import de.willuhn.jameica.example.rmi.Task;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

/**
 * Implementation of the task interface.
 * Look into ProjectImpl for more code comments.
 */
public class TaskImpl extends AbstractDBObject implements Task
{

  /**
   * ct.
   * @throws RemoteException
   */
  public TaskImpl() throws RemoteException
  {
    super();
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getTableName()
   */
  protected String getTableName()
  {
  	// this is the sql table name.
    return "task";
  }

  /**
   * @see de.willuhn.datasource.GenericObject#getPrimaryAttribute()
   */
  public String getPrimaryAttribute() throws RemoteException
  {
  	// our primary attribute is the name.
    return "name";
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#deleteCheck()
   */
  protected void deleteCheck() throws ApplicationException
  {
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#insertCheck()
   */
  protected void insertCheck() throws ApplicationException
  {
		try {
			if (getName() == null || getName().length() == 0)
				throw new ApplicationException(Settings.i18n().tr("Please enter a task name"));
			
			if (getProject() == null)
				throw new ApplicationException(Settings.i18n().tr("Please choose a project"));

			if (getProject().isNewObject())
				throw new ApplicationException(Settings.i18n().tr("Please store project first"));

		}
		catch (RemoteException e)
		{
			Logger.error("insert check of project failed",e);
			throw new ApplicationException(Settings.i18n().tr("unable to store project, please check the system log"));
		}
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#updateCheck()
   */
  protected void updateCheck() throws ApplicationException
  {
  	// same as insertCheck
  	insertCheck();
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getForeignObject(java.lang.String)
   */
  protected Class getForeignObject(String field) throws RemoteException
  {
		// the system is able to resolve foreign keys and loads
		// the according objects automatically. You only have to
		// define which class handles which foreign key.
  	if ("project_id".equals(field))
  		return Project.class;
    return null;
  }

  /**
   * @see de.willuhn.jameica.example.rmi.Task#getProject()
   */
  public Project getProject() throws RemoteException
  {
  	// Yes, we can cast this directly to Project, because getForeignObject(String)
  	// contains the mapping for this attribute.
  	try
  	{
			return (Project) getAttribute("project_id");
  	}
  	catch (ObjectNotFoundException e)
  	{
  		return null;
  	}
  }

  /**
   * @see de.willuhn.jameica.example.rmi.Task#setProject(de.willuhn.jameica.example.rmi.Project)
   */
  public void setProject(Project project) throws RemoteException
  {
  	// same here
  	setAttribute("project_id",project);
  }

  /**
   * @see de.willuhn.jameica.example.rmi.Task#getName()
   */
  public String getName() throws RemoteException
  {
    return (String) getAttribute("name");
  }

  /**
   * @see de.willuhn.jameica.example.rmi.Task#setName(java.lang.String)
   */
  public void setName(String name) throws RemoteException
  {
  	setAttribute("name",name);
  }

  /**
   * @see de.willuhn.jameica.example.rmi.Task#getComment()
   */
  public String getComment() throws RemoteException
  {
    return (String) getAttribute("comment");
  }

  /**
   * @see de.willuhn.jameica.example.rmi.Task#setComment(java.lang.String)
   */
  public void setComment(String comment) throws RemoteException
  {
  	setAttribute("comment",comment);
  }

  /**
   * @see de.willuhn.jameica.example.rmi.Task#getEffort()
   */
  public double getEffort() throws RemoteException
  {
    Double d = (Double) getAttribute("effort");
    return d == null || Double.isNaN(d) ? 0.0 : d.doubleValue();
  }

  /**
   * @see de.willuhn.jameica.example.rmi.Task#setEffort(double)
   */
  public void setEffort(double effort) throws RemoteException
  {
  	setAttribute("effort", new Double(effort));
  }

}


/**********************************************************************
 * $Log: TaskImpl.java,v $
 * Revision 1.4  2010-11-09 17:20:16  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/