/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/gui/dialog/About.java,v $
 * $Revision: 1.1 $
 * $Date: 2010-11-09 17:20:16 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.example.gui.dialog;

import org.eclipse.swt.widgets.Composite;

import de.willuhn.jameica.example.ExamplePlugin;
import de.willuhn.jameica.example.Settings;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.dialogs.AbstractDialog;
import de.willuhn.jameica.gui.input.LabelInput;
import de.willuhn.jameica.gui.parts.ButtonArea;
import de.willuhn.jameica.gui.parts.FormTextPart;
import de.willuhn.jameica.gui.util.LabelGroup;
import de.willuhn.jameica.plugin.AbstractPlugin;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.ApplicationException;

/**
 * Our "About..." dialog.
 */
public class About extends AbstractDialog
{

  /**
   * ct.
   * @param position
   */
  public About(int position)
  {
    super(position);
    this.setTitle(Settings.i18n().tr("About..."));
  }

  /**
   * @see de.willuhn.jameica.gui.dialogs.AbstractDialog#paint(org.eclipse.swt.widgets.Composite)
   */
  protected void paint(Composite parent) throws Exception
  {

		FormTextPart text = new FormTextPart();
		text.setText("<form>" +
			"<p><b>Jameica example plugin</b></p>" +
			"<br/>Licence: GPL (http://www.gnu.org/copyleft/gpl.html)" +
			"<br/><p>Copyright by Olaf Willuhn [info@jameica.org]</p>" +
			"<p>http://www.jameica.org</p>" +
			"</form>");

		text.paint(parent);

		LabelGroup group = new LabelGroup(parent, " Information ");

		AbstractPlugin p = Application.getPluginLoader().getPlugin(ExamplePlugin.class);

		group.addLabelPair(Settings.i18n().tr("Version"), 					new LabelInput(""+p.getManifest().getVersion()));
		group.addLabelPair(Settings.i18n().tr("Working directory"), new LabelInput(""+p.getResources().getWorkPath()));
		
		ButtonArea buttons = new ButtonArea();
		buttons.addButton(Settings.i18n().tr("Close"),new Action() {
      public void handleAction(Object context) throws ApplicationException
      {
        close();
      }
    },null,true);
		buttons.paint(parent);
		getShell().pack();

  }

  /**
   * @see de.willuhn.jameica.gui.dialogs.AbstractDialog#getData()
   */
  protected Object getData() throws Exception
  {
    return null;
  }

}


/**********************************************************************
 * $Log: About.java,v $
 * Revision 1.1  2010-11-09 17:20:16  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/