/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/gui/action/TaskDetail.java,v $
 * $Revision: 1.5 $
 * $Date: 2010-11-09 17:20:15 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.example.gui.action;

import java.rmi.RemoteException;

import de.willuhn.jameica.example.Settings;
import de.willuhn.jameica.example.rmi.Project;
import de.willuhn.jameica.example.rmi.Task;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.util.ApplicationException;

/**
 * Action for "show Task details" or "create new Task".
 */
public class TaskDetail implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {

		Task task = null;
		
		// check if the context is a task
		if (context != null && (context instanceof Task))
			task = (Task) context;
		

		// context null?
		// --> create a new task
		if (context == null)
		{
			try
			{
				task = (Task) Settings.getDBService().createObject(Task.class,null);
			}
			catch (RemoteException e)
			{
				throw new ApplicationException(Settings.i18n().tr("error while creating new task"),e);
			}
		}

		// check if the context is a project
		// --> create a new task and assign the given project
  	if (context != null && (context instanceof Project))
  	{
			try
			{
				Project p = (Project) context;
				if (p.isNewObject())
					throw new ApplicationException(Settings.i18n().tr("Please store the project first"));
				task = (Task) Settings.getDBService().createObject(Task.class,null);
				task.setProject(p);
			}
			catch (RemoteException e)
			{
				throw new ApplicationException(Settings.i18n().tr("Error while creating new task"),e);
			}
  	}


		// ok, lets start the dialog
  	GUI.startView(de.willuhn.jameica.example.gui.view.TaskDetail.class.getName(),task);
  }

}


/**********************************************************************
 * $Log: TaskDetail.java,v $
 * Revision 1.5  2010-11-09 17:20:15  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/