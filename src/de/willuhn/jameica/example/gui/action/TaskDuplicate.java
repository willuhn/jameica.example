/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/gui/action/TaskDuplicate.java,v $
 * $Revision: 1.2 $
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
import de.willuhn.jameica.example.rmi.Task;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.util.ApplicationException;

/**
 * Action for "duplicate Task".
 */
public class TaskDuplicate implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {

		if (context == null || !(context instanceof Task))
			throw new ApplicationException(Settings.i18n().tr("Please a task you want to duplicate"));

		Task task = null;
		try
		{
			// Lets create a new Task
			task = (Task) Settings.getDBService().createObject(Task.class,null);
		
			// copy the attributes into the new object.
			task.overwrite((Task)context);
		}
		catch (RemoteException e)
		{
			throw new ApplicationException(Settings.i18n().tr("Error while duplicating the task"),e);
		}

		// ok, lets start the dialog 
  	GUI.startView(de.willuhn.jameica.example.gui.view.TaskDetail.class.getName(),task);
  }

}


/**********************************************************************
 * $Log: TaskDuplicate.java,v $
 * Revision 1.2  2010-11-09 17:20:15  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/