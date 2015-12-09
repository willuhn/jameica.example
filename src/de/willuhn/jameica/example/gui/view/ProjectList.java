/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/gui/view/ProjectList.java,v $
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
package de.willuhn.jameica.example.gui.view;

import de.willuhn.jameica.example.Settings;
import de.willuhn.jameica.example.gui.action.ProjectDetail;
import de.willuhn.jameica.example.gui.control.ProjectControl;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.parts.ButtonArea;

/**
 * View to show the list of existing projects.
 */
public class ProjectList extends AbstractView
{

  /**
   * @see de.willuhn.jameica.gui.AbstractView#bind()
   */
  public void bind() throws Exception {

		GUI.getView().setTitle(Settings.i18n().tr("Existing projects"));
		
		ProjectControl control = new ProjectControl(this);
		
		control.getProjectsTable().paint(this.getParent());
		
		ButtonArea buttons = new ButtonArea();
		
		// the last parameter "true" makes the button the default one
		buttons.addButton(Settings.i18n().tr("Create new project"), new ProjectDetail(),null,true);
		
		buttons.paint(getParent());
		
  }
}


/**********************************************************************
 * $Log: ProjectList.java,v $
 * Revision 1.4  2010-11-09 17:20:16  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/