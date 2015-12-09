/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/gui/control/TaskControl.java,v $
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
package de.willuhn.jameica.example.gui.control;

import java.rmi.RemoteException;

import de.willuhn.jameica.example.Settings;
import de.willuhn.jameica.example.rmi.Project;
import de.willuhn.jameica.example.rmi.Task;
import de.willuhn.jameica.gui.AbstractControl;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.input.DecimalInput;
import de.willuhn.jameica.gui.input.Input;
import de.willuhn.jameica.gui.input.SelectInput;
import de.willuhn.jameica.gui.input.TextAreaInput;
import de.willuhn.jameica.gui.input.TextInput;
import de.willuhn.jameica.messaging.StatusBarMessage;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

/**
 * Controller for the task view.
 */
public class TaskControl extends AbstractControl
{

	// the current task object
	private Task task;

	// the input fields for the task.
	private SelectInput project;
	private TextInput name;
	private DecimalInput effort;
	private TextAreaInput comment;

  /**
   * ct.
   * @param view
   */
  public TaskControl(AbstractView view)
  {
    super(view);
  }

	/**
	 * Returns the current task.
   * @return the task.
   */
  private Task getTask()
	{
		if (task != null)
			return task;
		task = (Task) getCurrentObject();
		return task;
	}

	/**
	 * Returns a the field to choose the project.
   * @return the project.
   * @throws RemoteException
   */
  public Input getProject() throws RemoteException
	{
		if (project != null)
			return project;
		
		project = new SelectInput(Settings.getDBService().createList(Project.class),getTask().getProject());
    project.setName(Settings.i18n().tr("Project"));
    project.setMandatory(true);
    return project;
	}

	/**
	 * Returns an input field for the task name.
   * @return input field.
   * @throws RemoteException
   */
  public Input getName() throws RemoteException
	{
		if (name != null)
			return name;
		// "255" is the maximum length of the name attribute.
		name = new TextInput(getTask().getName(),255);
		name.setMandatory(true);
		name.setName(Settings.i18n().tr("Name"));
		return name;
	}

	/**
	 * Returns an input field for the task effort.
   * @return input field.
   * @throws RemoteException
   */
  public Input getEffort() throws RemoteException
	{
		if (effort != null)
			return effort;

		// we assign our system decimal formatter
		effort = new DecimalInput(getTask().getEffort(),Settings.DECIMALFORMAT);
		effort.setName(Settings.i18n().tr("Effort"));
		effort.setComment(Settings.i18n().tr("Hours [example: enter \"0,5\" to store 30 minutes]"));
		return effort;
	}

	/**
	 * Returns an input field for the task comment.
   * @return input field.
   * @throws RemoteException
   */
  public Input getComment() throws RemoteException
	{
		if (comment != null)
			return comment;
		comment = new TextAreaInput(getTask().getComment());
		comment.setName("");
		return comment;
	}

	/**
	 * This method stores the task using the current values. 
	 */
	public void handleStore()
	{
		try
		{

			// get the current task.
			Task t = getTask();

			// invoke all Setters of this task and assign the current values
			t.setName((String) getName().getValue());

			// we can cast the value of the project dialogInput directly to "Project".
			t.setProject((Project) getProject().getValue());

			// the DecimalInput fields returns a Double object
			Double d = (Double) getEffort().getValue(); 
			t.setEffort(d == null ? 0.0 : d.doubleValue());

			t.setComment((String) getComment().getValue());

			// Now, let's store the project
			// The store() method throws ApplicationExceptions if
			// insertCheck() or updateCheck() failed.
			try
			{
				t.store();
        Application.getMessagingFactory().sendMessage(new StatusBarMessage(Settings.i18n().tr("Task stored successfully"),StatusBarMessage.TYPE_SUCCESS));
			}
			catch (ApplicationException e)
			{
        Application.getMessagingFactory().sendMessage(new StatusBarMessage(e.getMessage(),StatusBarMessage.TYPE_ERROR));
			}
		}
		catch (RemoteException e)
		{
			Logger.error("error while storing task",e);
      Application.getMessagingFactory().sendMessage(new StatusBarMessage(Settings.i18n().tr("Error while storing Task: {0}",e.getMessage()),StatusBarMessage.TYPE_ERROR));
		}
	}

}


/**********************************************************************
 * $Log: TaskControl.java,v $
 * Revision 1.3  2010-11-09 17:20:16  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/