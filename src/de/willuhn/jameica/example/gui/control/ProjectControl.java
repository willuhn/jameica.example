/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/gui/control/ProjectControl.java,v $
 * $Revision: 1.9 $
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
import java.util.Date;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.datasource.rmi.DBService;
import de.willuhn.jameica.example.Settings;
import de.willuhn.jameica.example.gui.action.TaskDetail;
import de.willuhn.jameica.example.gui.menu.ProjectListMenu;
import de.willuhn.jameica.example.gui.menu.TaskListMenu;
import de.willuhn.jameica.example.rmi.Project;
import de.willuhn.jameica.gui.AbstractControl;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.Part;
import de.willuhn.jameica.gui.formatter.CurrencyFormatter;
import de.willuhn.jameica.gui.formatter.DateFormatter;
import de.willuhn.jameica.gui.formatter.Formatter;
import de.willuhn.jameica.gui.input.DateInput;
import de.willuhn.jameica.gui.input.DecimalInput;
import de.willuhn.jameica.gui.input.Input;
import de.willuhn.jameica.gui.input.LabelInput;
import de.willuhn.jameica.gui.input.TextAreaInput;
import de.willuhn.jameica.gui.input.TextInput;
import de.willuhn.jameica.gui.parts.ContextMenuItem;
import de.willuhn.jameica.gui.parts.TablePart;
import de.willuhn.jameica.messaging.StatusBarMessage;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;


/**
 * @author willuhn
 */
public class ProjectControl extends AbstractControl
{

  // list of all projects
  private TablePart projectList;

  // Input fields for the project attributes,
  private TextInput name;
  private TextAreaInput description;
  private DecimalInput price;
  private DateInput startDate;
  private DateInput endDate;

  private LabelInput effortSummary;

	// list of tasks contained in this project
	private TablePart taskList;

  // this is the currently opened project
  private Project project;

  /**
   * ct.
	 * @param view this is our view (the welcome screen).
	 */
	public ProjectControl(AbstractView view)
	{
		super(view);
	}

  /**
   * Small helper method to get the current project.
   * @return
   */
  private Project getProject()
  {
    if (project != null)
      return project;
    project = (Project) getCurrentObject();
    return project;
  }

  /**
   * Returns the input field for the project name.
   * @return input field.
   * @throws RemoteException
   */
  public Input getName() throws RemoteException
  {
    if (name != null)
      return name;
    // "255" is the maximum length for this input field.
    name = new TextInput(getProject().getName(),255);
    name.setMandatory(true);
    name.setName(Settings.i18n().tr("Name"));
    return name;
  }

  /**
   * Returns the input field for the project description.
   * @return input field.
   * @throws RemoteException
   */
  public Input getDescription() throws RemoteException
  {
    if (description != null)
      return description;
    description = new TextAreaInput(getProject().getDescription());
    description.setName("");
    return description;
  }

  /**
   * Returns the input field for the project price.
   * @return input field.
   * @throws RemoteException
   */
  public Input getPrice() throws RemoteException
  {
    if (price != null)
      return price;
    price = new DecimalInput(getProject().getPrice(), Settings.DECIMALFORMAT);
    price.setComment(Settings.i18n().tr("{0} per Hour",Settings.CURRENCY));
    price.setName(Settings.i18n().tr("Price"));
    
    // if we change the price, we have to refresh the summary
    price.addListener(new Listener() {
      public void handleEvent(Event event)
      {
        try
        {
          Double d = (Double) getPrice().getValue();
          if (d == null)
            return;
          
          double p = d.doubleValue();
          if (Double.isNaN(p))
            return;
          
          double effort = getProject().getEfforts();
          double sum = effort * p;
          
          getEffortSummary().setValue(Settings.DECIMALFORMAT.format(sum));
        }
        catch (Exception e)
        {
          Logger.error("error while calculating sum",e);
          Application.getMessagingFactory().sendMessage(new StatusBarMessage(Settings.i18n().tr("Error while calculating summary: {0}",e.getMessage()),StatusBarMessage.TYPE_ERROR));
        }
      }
    });
    return price;
  }

  /**
   * Returns the input field for the start date.
   * @return input field.
   * @throws RemoteException
   */
  public Input getStartDate() throws RemoteException
  {
    if (startDate != null)
      return startDate;
    
    Date start = getProject().getStartDate();
    if (start == null)
      start = new Date();
    startDate = new DateInput(start,Settings.DATEFORMAT);
    startDate.setName(Settings.i18n().tr("Start date"));
    return startDate;
  }


  /**
   * Returns the input field for the end date.
   * @return input field.
   * @throws RemoteException
   */
  public Input getEndDate() throws RemoteException
  {
    if (endDate != null)
      return endDate;
    
    endDate = new DateInput(getProject().getEndDate(),Settings.DATEFORMAT);
    endDate.setName(Settings.i18n().tr("End date"));
    return endDate;
  }

	/**
	 * Returns a text label that contains the summary of all efforts in this project.
   * @return label.
   * @throws RemoteException
   */
  public Input getEffortSummary() throws RemoteException
	{
		if (effortSummary != null)
			return effortSummary;

    double effort = getProject().getEfforts();
    double sum = effort * getProject().getPrice();

    effortSummary = new LabelInput(Settings.DECIMALFORMAT.format(sum));
    effortSummary.setName(Settings.i18n().tr("Efforts"));
    effortSummary.setComment(Settings.i18n().tr("{0} [{1} Hours]",Settings.CURRENCY,Settings.DECIMALFORMAT.format(effort)));
		return effortSummary;
	}

  /**
   * Creates a table containing all projects.
   * @return a table with projects.
   * @throws RemoteException
   */
  public Part getProjectsTable() throws RemoteException
  {
    // do we have an allready created table?
    if (projectList != null)
      return projectList;
   
    // 1) get the dataservice
    DBService service = Settings.getDBService();
    
    // 2) now we can create the project list.
    //    We do not need to specify the implementing class for
    //    the interface "Project". Jameicas Classloader knows
    //    all classes an finds the right implementation automatically. ;)
    DBIterator projects = service.createList(Project.class);
    
    // 4) create the table
    projectList = new TablePart(projects,new de.willuhn.jameica.example.gui.action.ProjectDetail());

    // 5) now we have to add some columns.
    projectList.addColumn(Settings.i18n().tr("Name of project"),"name"); // "name" is the field name from the sql table.

    // 6) the following fields are a date fields. So we add a date formatter. 
    projectList.addColumn(Settings.i18n().tr("Start date"),"startdate",new DateFormatter(Settings.DATEFORMAT));
    projectList.addColumn(Settings.i18n().tr("End date"),"enddate",    new DateFormatter(Settings.DATEFORMAT));

    // 7) calculated project price (price per hour * hours)
    projectList.addColumn(Settings.i18n().tr("Efforts"),"summary", new CurrencyFormatter(Settings.CURRENCY,Settings.DECIMALFORMAT));

		// 8) we are adding a context menu
		projectList.setContextMenu(new ProjectListMenu());
    return projectList;
  }

	/**
	 * Returns a list of tasks in this project.
   * @return list of tasks in this project
   * @throws RemoteException
   */
  public Part getTaskList() throws RemoteException
	{
		if (taskList != null)
			return taskList;

		DBIterator tasks = getProject().getTasks();
		taskList = new TablePart(tasks,new TaskDetail());
		taskList.addColumn(Settings.i18n().tr("Task name"),"name");
		taskList.addColumn(Settings.i18n().tr("Effort"),"effort",new Formatter()
    {
      public String format(Object o)
      {
      	if (o == null)
      		return "-";
        return o + " h";
      }
    });
    
		TaskListMenu tlm = new TaskListMenu();

		// we add an additional menu item to create tasks with predefined project.
		tlm.addItem(new ContextMenuItem(Settings.i18n().tr("Create new task within this Project"),new Action()
    {
      public void handleAction(Object context) throws ApplicationException
      {
      	new TaskDetail().handleAction(getProject());
      }
    }));
    taskList.setContextMenu(tlm);
    taskList.setSummary(false);
    return taskList;
	}

  /**
   * This method stores the project using the current values. 
   */
  public void handleStore()
  {
    try
    {

      // get the current project.
      Project p = getProject();

      // invoke all Setters of this project and assign the current values
      p.setName((String) getName().getValue());
      p.setDescription((String) getDescription().getValue());

			// we can cast the return value of date input directly to "java.util.Date".
      p.setEndDate((Date) getEndDate().getValue());
      p.setStartDate((Date) getStartDate().getValue());

			// the DecimalInput fields returns a Double object
      Double d = (Double) getPrice().getValue();
      p.setPrice(d == null ? 0.0 : d.doubleValue());

      // Now, let's store the project
      // The store() method throws ApplicationExceptions if
      // insertCheck() or updateCheck() failed.
      try
      {
        p.store();
        Application.getMessagingFactory().sendMessage(new StatusBarMessage(Settings.i18n().tr("Project stored successfully"),StatusBarMessage.TYPE_SUCCESS));
      }
      catch (ApplicationException e)
      {
        Application.getMessagingFactory().sendMessage(new StatusBarMessage(e.getMessage(),StatusBarMessage.TYPE_ERROR));
      }
    }
    catch (RemoteException e)
    {
      Logger.error("error while storing project",e);
      Application.getMessagingFactory().sendMessage(new StatusBarMessage(Settings.i18n().tr("Error while storing Project: {0}",e.getMessage()),StatusBarMessage.TYPE_ERROR));
    }
  }
}


/**********************************************************************
 * $Log: ProjectControl.java,v $
 * Revision 1.9  2010-11-09 17:20:16  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/