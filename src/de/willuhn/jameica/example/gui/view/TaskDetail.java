/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/gui/view/TaskDetail.java,v $
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
import de.willuhn.jameica.example.gui.action.TaskDelete;
import de.willuhn.jameica.example.gui.control.TaskControl;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.parts.ButtonArea;
import de.willuhn.jameica.gui.util.ColumnLayout;
import de.willuhn.jameica.gui.util.Container;
import de.willuhn.jameica.gui.util.SimpleContainer;
import de.willuhn.util.ApplicationException;

/**
 * Detail view for tasks.
 */
public class TaskDetail extends AbstractView
{

  /**
   * @see de.willuhn.jameica.gui.AbstractView#bind()
   */
  public void bind() throws Exception
  {
		// draw the title
		GUI.getView().setTitle(Settings.i18n().tr("Task details"));

		// instanciate controller
		final TaskControl control = new TaskControl(this);
    
		Container c = new SimpleContainer(getParent());
		
    // layout with 2 columns
    ColumnLayout columns = new ColumnLayout(c.getComposite(),2);

    // left side
    Container left = new SimpleContainer(columns.getComposite());
    left.addHeadline(Settings.i18n().tr("Details"));
		left.addInput(control.getProject());
		left.addInput(control.getName());
		left.addInput(control.getEffort());

    // right side
    Container right = new SimpleContainer(columns.getComposite(),true);
    right.addHeadline(Settings.i18n().tr("Description"));
    right.addInput(control.getComment());

		// add some buttons
		ButtonArea buttons = new ButtonArea();

		buttons.addButton(Settings.i18n().tr("Delete"), new TaskDelete(),control.getCurrentObject());
		buttons.addButton(Settings.i18n().tr("Store"),  new Action()
		{
			public void handleAction(Object context) throws ApplicationException
			{
				control.handleStore();
			}
		},null,true); // "true" defines this button as the default button

		// Don't forget to paint the button area
    buttons.paint(getParent());
  }
}


/**********************************************************************
 * $Log: TaskDetail.java,v $
 * Revision 1.3  2010-11-09 17:20:16  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/