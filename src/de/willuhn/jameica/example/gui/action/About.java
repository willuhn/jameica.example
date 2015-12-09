/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/gui/action/About.java,v $
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
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.dialogs.AbstractDialog;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

public class About implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {
  	try
  	{
			new de.willuhn.jameica.example.gui.dialog.About(AbstractDialog.POSITION_CENTER).open();
  	}
    catch (ApplicationException ae)
    {
      throw ae;
    }
  	catch (Exception e)
  	{
  		Logger.error("error while opening about dialog",e);
  		throw new ApplicationException(Settings.i18n().tr("Error while opening the About dialog"));
  	}
  }

}


/**********************************************************************
 * $Log: About.java,v $
 * Revision 1.4  2010-11-09 17:20:15  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/