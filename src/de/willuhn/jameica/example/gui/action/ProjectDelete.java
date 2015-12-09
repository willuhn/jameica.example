/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/gui/action/ProjectDelete.java,v $
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

import de.willuhn.jameica.example.Settings;
import de.willuhn.jameica.example.rmi.Project;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.messaging.StatusBarMessage;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

/**
 * Action for "delete project".
 */
public class ProjectDelete implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {

		// check if the context is a project
  	if (context == null || !(context instanceof Project))
  		throw new ApplicationException(Settings.i18n().tr("Please choose a project"));

    Project p = (Project) context;
    
    try
    {

			// before deleting the project, we show up a confirm dialog ;)
			String question = Settings.i18n().tr("Do you really want to delete this project? " +
	                                         "All assigned tasks will be deleted too.");
			if (!Application.getCallback().askUser(question))
			  return;

      p.delete();
      
      // Send Status update message
      Application.getMessagingFactory().sendMessage(new StatusBarMessage(Settings.i18n().tr("Project deleted successfully"),StatusBarMessage.TYPE_SUCCESS));
    }
    catch (ApplicationException ae)
    {
      throw ae;
    }
    catch (Exception e)
    {
      Logger.error("error while deleting project",e);
      throw new ApplicationException(Settings.i18n().tr("Error while deleting project"));
    }
  }

}


/**********************************************************************
 * $Log: ProjectDelete.java,v $
 * Revision 1.4  2010-11-09 17:20:15  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/