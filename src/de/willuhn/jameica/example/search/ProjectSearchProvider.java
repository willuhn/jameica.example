/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/search/ProjectSearchProvider.java,v $
 * $Revision: 1.1 $
 * $Date: 2010-11-09 17:20:16 $
 * $Author: willuhn $
 *
 * Copyright (c) by willuhn - software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.example.search;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.jameica.example.Settings;
import de.willuhn.jameica.example.gui.action.ProjectDetail;
import de.willuhn.jameica.example.rmi.Project;
import de.willuhn.jameica.search.Result;
import de.willuhn.jameica.search.SearchProvider;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

/**
 * Extension to the jameica search service.
 * If you implement the "SearchProvider" interface, jameica automatically
 * detects the provider. You are now able to search for projects in jameica.
 */
public class ProjectSearchProvider implements SearchProvider
{
  /**
   * @see de.willuhn.jameica.search.SearchProvider#getName()
   */
  public String getName()
  {
    return Settings.i18n().tr("Projects");
  }

  /**
   * @see de.willuhn.jameica.search.SearchProvider#search(java.lang.String)
   */
  public List search(String search) throws RemoteException, ApplicationException
  {
    // We have to return a list of "Result" objects
    List<Result> result = new ArrayList<Result>();
    if (search == null || search.length() < 3)
      return result;
    
    String s = "%" + search.toLowerCase() + "%";
    DBIterator projects = Settings.getDBService().createList(Project.class);
    projects.addFilter("lower(name) like ? or lower(description) like ?",new Object[]{s,s});
    while (projects.hasNext())
    {
      result.add(new MyResult((Project)projects.next()));
    }
    return result;
  }
  
  /**
   * Our implementation of the search result items.
   */
  public class MyResult implements Result
  {
    private Project project = null;
    
    /**
     * ct.
     * @param project
     */
    private MyResult(Project project)
    {
      this.project = project;
    }
    
    /**
     * @see de.willuhn.jameica.search.Result#execute()
     */
    public void execute() throws RemoteException, ApplicationException
    {
      new ProjectDetail().handleAction(this.project);
    }

    /**
     * @see de.willuhn.jameica.search.Result#getName()
     */
    public String getName()
    {
      try
      {
        return this.project.getName();
      }
      catch (Exception e)
      {
        Logger.error("unable to determine project name",e);
        return "";
      }
    }
    
  }

}



/**********************************************************************
 * $Log: ProjectSearchProvider.java,v $
 * Revision 1.1  2010-11-09 17:20:16  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/