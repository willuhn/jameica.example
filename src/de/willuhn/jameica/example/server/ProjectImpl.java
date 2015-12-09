/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/server/ProjectImpl.java,v $
 * $Revision: 1.9 $
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
import java.util.Date;

import de.willuhn.datasource.db.AbstractDBObject;
import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.datasource.rmi.DBService;
import de.willuhn.jameica.example.Settings;
import de.willuhn.jameica.example.rmi.Project;
import de.willuhn.jameica.example.rmi.Task;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;


/**
 * This is the implemtor of the project interface.
 * You never need to instanciate this class directly.
 * Instead of this, use the dbService to find the right
 * implementor of your interface.
 * Example:
 * 
 * DBService service = (DBService) Application.getServiceFactory().lookup(ExamplePlugin.class,"exampledatabase");
 * 
 * a) create new project
 * Project project = (Project) service.createObject(Project.class,null);
 * 
 * b) load existing project with id "4".
 * Project project = (Project) service.createObject(Project.class,"4");
 * 
 * c) list existing projects
 * DBIterator projects = service.createList(Project.class);
 */
public class ProjectImpl extends AbstractDBObject implements Project
{

	/**
   * @throws RemoteException
   */
  public ProjectImpl() throws RemoteException
  {
    super();
  }

  /**
   * We have to return the name of the sql table here.
	 * @see de.willuhn.datasource.db.AbstractDBObject#getTableName()
	 */
	protected String getTableName()
	{
		return "project";
	}

  /**
   * Sometimes you can display only one of the projects attributes (in combo boxes).
   * Here you can define the name of this field.
   * Please dont confuse this with the "primary KEY".
   * @see de.willuhn.datasource.GenericObject#getPrimaryAttribute()
	 */
	public String getPrimaryAttribute() throws RemoteException
	{
    // we choose the projects name as primary field.
		return "name";
	}

	/**
   * This method will be called, before delete() is executed.
   * Here you can make some dependency checks.
   * If you dont want to delete the project (in case of failed dependencies)
   * you have to throw an ApplicationException. The message of this
   * one will be shown in users UI. So please translate the text into
   * the users language.
	 * @see de.willuhn.datasource.db.AbstractDBObject#deleteCheck()
	 */
	protected void deleteCheck() throws ApplicationException
	{
  }

	/**
   * This method is invoked before executing insert().
   * So lets check the entered data.
	 * @see de.willuhn.datasource.db.AbstractDBObject#insertCheck()
	 */
	protected void insertCheck() throws ApplicationException
	{
    try {
      if (getName() == null || getName().length() == 0)
        throw new ApplicationException(Settings.i18n().tr("Please enter a project name"));

      if (getStartDate() != null && getEndDate() != null && getStartDate().after(getEndDate()))
        throw new ApplicationException(Settings.i18n().tr("start date cannot be after end date"));
      
    }
    catch (RemoteException e)
    {
      Logger.error("insert check of project failed",e);
      throw new ApplicationException(Settings.i18n().tr("unable to store project, please check the system log"));
    }
	}

	/**
   * This method is invoked before every update().
	 * @see de.willuhn.datasource.db.AbstractDBObject#updateCheck()
	 */
	protected void updateCheck() throws ApplicationException
	{
    // we simply call the insertCheck here ;)
    insertCheck();
	}

	/**
	 * @see de.willuhn.datasource.db.AbstractDBObject#getForeignObject(java.lang.String)
	 */
	protected Class getForeignObject(String arg0) throws RemoteException
	{
    // We dont have any foreign keys here. Please check TaskImpl.java
    // if you want to see an example.
		return super.getForeignObject(arg0);
	}

	/**
	 * @see de.willuhn.jameica.example.rmi.Project#getName()
	 */
	public String getName() throws RemoteException
	{
    // Wen can cast this directly to String, the method getField() knows the
    // meta data of this sql table ;)
		return (String) getAttribute("name"); // "name" ist the sql field name
	}

	/**
	 * @see de.willuhn.jameica.example.rmi.Project#getDescription()
	 */
	public String getDescription() throws RemoteException
	{
		return (String) getAttribute("description");
	}

	/**
	 * @see de.willuhn.jameica.example.rmi.Project#getEmail()
	 */
	public String getEmail() throws RemoteException
	{
    return (String) getAttribute("email");
	}

	/**
	 * @see de.willuhn.jameica.example.rmi.Project#getPrice()
	 */
	public double getPrice() throws RemoteException
	{
    // AbstractDBObject will create a java.lang.Double.
    // We only have to cast it.
    Double d = (Double) getAttribute("price");
    return d == null || Double.isNaN(d) ? 0.0 : d.doubleValue();
	}

	/**
	 * @see de.willuhn.jameica.example.rmi.Project#getStartDate()
	 */
	public Date getStartDate() throws RemoteException
	{
		// getField() knows this type too
		return (Date) getAttribute("startdate");
	}

	/**
	 * @see de.willuhn.jameica.example.rmi.Project#getEndDate()
	 */
	public Date getEndDate() throws RemoteException
	{
    return (Date) getAttribute("enddate");
	}

	/**
	 * @see de.willuhn.jameica.example.rmi.Project#setName(java.lang.String)
	 */
	public void setName(String name) throws RemoteException
	{
    // Please use setField(<fieldname>,<value>) to store the data into the right field.
    setAttribute("name",name);
	}

	/**
	 * @see de.willuhn.jameica.example.rmi.Project#setDescription(java.lang.String)
	 */
	public void setDescription(String description) throws RemoteException
	{
    setAttribute("description",description);
	}

	/**
	 * @see de.willuhn.jameica.example.rmi.Project#setPrice(double)
	 */
	public void setPrice(double price) throws RemoteException
	{
    // setField() wants to have an object but <prive> is a primitive type.
    // So we have to make a java.lang.Double
    setAttribute("price",new Double(price));
  }

	/**
	 * @see de.willuhn.jameica.example.rmi.Project#setStartDate(java.util.Date)
	 */
	public void setStartDate(Date startDate) throws RemoteException
	{
    setAttribute("startdate",startDate);
	}

	/**
	 * @see de.willuhn.jameica.example.rmi.Project#setEndDate(java.util.Date)
	 */
	public void setEndDate(Date endDate) throws RemoteException
	{
    setAttribute("enddate",endDate);
	}

	/**
	 * @see de.willuhn.jameica.example.rmi.Project#getTasks()
	 */
	public DBIterator getTasks() throws RemoteException
	{
    try
    {
      // 1) Get the Database Service.
      DBService service = this.getService();

      // you can get the Database Service also via:
      // DBService service = this.getService();
      
      // 3) We create the task list using getList(Class)
      DBIterator tasks = service.createList(Task.class);
      
      // 4) we add a filter to only query for tasks with our project id
      tasks.addFilter("project_id = " + this.getID());
      
      return tasks;
    }
    catch (Exception e)
    {
    	throw new RemoteException("unable to load task list",e);
    }
	}

  /**
   * @see de.willuhn.jameica.example.rmi.Project#getEfforts()
   */
  public double getEfforts() throws RemoteException
  {
  	double sum = 0.0;
  	DBIterator i = getTasks();
  	while (i.hasNext())
  	{
  		Task t = (Task) i.next();
  		sum += t.getEffort();
  	}
  	return sum;
  }

  /**
   * We overwrite the delete method to delete all assigned tasks too.
   * @see de.willuhn.datasource.rmi.Changeable#delete()
   */
  public void delete() throws RemoteException, ApplicationException
  {
  	try
  	{
  		// we start a new transaction
  		// to delete all or nothing
  		this.transactionBegin();

			DBIterator tasks = getTasks();
			while (tasks.hasNext())
			{
				Task t = (Task) tasks.next();
				t.delete();
			}
			super.delete(); // we delete the project itself

			// everything seems to be ok, lets commit the transaction
			this.transactionCommit();

  	}
  	catch (RemoteException re)
  	{
  		this.transactionRollback();
  		throw re;
  	}
  	catch (ApplicationException ae)
  	{
			this.transactionRollback();
  		throw ae;
  	}
  	catch (Throwable t)
  	{
			this.transactionRollback();
  		throw new ApplicationException(Settings.i18n().tr("error while deleting project"),t);
  	}
  }

  /**
   * @see de.willuhn.datasource.GenericObject#getAttribute(java.lang.String)
   */
  public Object getAttribute(String fieldName) throws RemoteException
  {
		// You are able to create virtual object attributes by overwriting
		// this method. Just catch the fieldName and invent your own attributes ;)
		if ("summary".equals(fieldName))
		{
			return new Double(getPrice() * getEfforts());
		}

    return super.getAttribute(fieldName);
  }

}


/**********************************************************************
 * $Log: ProjectImpl.java,v $
 * Revision 1.9  2010-11-09 17:20:16  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/