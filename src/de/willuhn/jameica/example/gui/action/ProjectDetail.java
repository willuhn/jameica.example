/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/gui/action/ProjectDetail.java,v $
 * $Revision: 1.4 $
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
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.util.ApplicationException;

/**
 * Action for "show project details" or "create new Project".
 */
public class ProjectDetail implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {

		Project p = null;

		// check if the context is a project
  	if (context != null && (context instanceof Project))
  	{
      p = (Project) context;
  	}
		else
		{
			try
			{
			  // create new project
				p = (Project) Settings.getDBService().createObject(Project.class,null);
			}
			catch (RemoteException e)
			{
				throw new ApplicationException(Settings.i18n().tr("error while creating new project"),e);
			}
		}

		// ok, lets start the dialog
  	GUI.startView(de.willuhn.jameica.example.gui.view.ProjectDetail.class.getName(),p);
  }
}


/**********************************************************************
 * $Log: ProjectDetail.java,v $
 * Revision 1.4  2010-11-09 17:20:15  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/