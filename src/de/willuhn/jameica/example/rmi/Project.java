/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/rmi/Project.java,v $
 * $Revision: 1.5 $
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
import java.util.Date;

import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.datasource.rmi.DBObject;


/**
 * Interface of the business object for projects.
 * According to the SQL table, we define some getter an setter here.
 * <pre>
 * CREATE TABLE project (
 *   id NUMERIC default UNIQUEKEY('project'),
 *   name varchar(255) NOT NULL,
 *   description text NOT NULL,
 *   email varchar(255) NOT NULL,
 *   price double,
 *   startdate date,
 *   enddate date,
 *   UNIQUE (id),
 *   PRIMARY KEY (id)
 * );
 * </pre>
 * <br>Getters and setters for the primary key (id) are not needed.
 * Every one of the following methods has to throw a RemoteException.
 * <br>
 */
public interface Project extends DBObject
{

  /**
   * Returns the name of the project.
   * @return name of the project.
   * @throws RemoteException
   */
  public String getName() throws RemoteException;
  
  /**
   * Returns the description text of the project.
   * @return description of the project.
   * @throws RemoteException
   */
  public String getDescription() throws RemoteException;
  
  /**
   * Returns the email of the project contact.
   * @return email of project contact.
   * @throws RemoteException
   */
  public String getEmail() throws RemoteException;
  
  /**
   * Returns the price per hour for the project.
   * @return price.
   * @throws RemoteException
   */
  public double getPrice() throws RemoteException;
  
  /**
   * Returns the start date of the project.
   * @return start date.
   * @throws RemoteException
   */
  public Date getStartDate() throws RemoteException;
  
  /**
   * Returns the end date of the project.
   * @return end date.
   * @throws RemoteException
   */
  public Date getEndDate() throws RemoteException;

  /**
   * Sets the name of the project.
   * @param name name of the project.
   * @throws RemoteException
   */
  public void setName(String name) throws RemoteException;
  
  /**
   * Sets the description  of the project.
   * @param description description  of the project.
   * @throws RemoteException
   */
  public void setDescription(String description) throws RemoteException;
  
  /**
   * Sets the price of the project.
   * @param price price of the project.
   * @throws RemoteException
   */
  public void setPrice(double price) throws RemoteException;
  
  /**
   * Sets the start date of the project.
   * @param startDate start date of the project.
   * @throws RemoteException
   */
  public void setStartDate(Date startDate) throws RemoteException;
  
  /**
   * Sets the end date of the project.
   * @param endDate end date of the project.
   * @throws RemoteException
   */
  public void setEndDate(Date endDate) throws RemoteException;

  /**
   * Additionally we want to have a method that fetches all tasks from this project.
   * @return list of all tasks within this project.
   * @throws RemoteException
   */
  public DBIterator getTasks() throws RemoteException;
  
  /**
   * Returns the effort summary from all tasks of this project. 
   * @return effort summary from all tasks of this project.
   * @throws RemoteException
   */
  public double getEfforts() throws RemoteException;
}


/**********************************************************************
 * $Log: Project.java,v $
 * Revision 1.5  2010-11-09 17:20:16  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/