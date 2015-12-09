/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/gui/view/Welcome.java,v $
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

package de.willuhn.jameica.example.gui.view;

import de.willuhn.jameica.example.Settings;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.util.LabelGroup;
import de.willuhn.util.ApplicationException;


/**
 * Welcome screen of this example plugin.
 * @author willuhn
 */
public class Welcome extends AbstractView
{

  /**
   * this method will be invoked when starting the view.
   * @see de.willuhn.jameica.gui.AbstractView#bind()
	 */
	public void bind() throws Exception
	{
		GUI.getView().setTitle(Settings.i18n().tr("Example plugin"));
		
		LabelGroup group = new LabelGroup(this.getParent(),Settings.i18n().tr("welcome"));
		
		group.addText(Settings.i18n().tr("this page intentionally left blank ;)"),false);

	}

  /**
   * this method will be executed when exiting the view.
   * You don't need to dispose your widgets, the GUI controller will
   * do this in a recursive way for you.
   * @see de.willuhn.jameica.gui.AbstractView#unbind()
	 */
	public void unbind() throws ApplicationException
	{
    // We've nothing to do here ;)
	}

}


/**********************************************************************
 * $Log: Welcome.java,v $
 * Revision 1.3  2010-11-09 17:20:16  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/