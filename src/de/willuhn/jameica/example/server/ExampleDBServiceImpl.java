/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/server/ExampleDBServiceImpl.java,v $
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
package de.willuhn.jameica.example.server;

import java.rmi.RemoteException;

import de.willuhn.datasource.db.EmbeddedDBServiceImpl;
import de.willuhn.jameica.example.ExamplePlugin;
import de.willuhn.jameica.example.rmi.ExampleDBService;
import de.willuhn.jameica.system.Application;

/**
 * this is our database service which can work over RMI.
 */
public class ExampleDBServiceImpl extends EmbeddedDBServiceImpl implements ExampleDBService
{
  /**
   * ct.
   * @throws RemoteException
   */
  public ExampleDBServiceImpl() throws RemoteException
  {
    super(Application.getPluginLoader().getPlugin(ExamplePlugin.class).getResources().getWorkPath() + "/db/db.conf",
    			"exampleuser", "examplepassword");

    // We have to define jameicas classfinder.
    // otherwise, the db service will not be able to find
    // implementors by their interfaces.  
    this.setClassFinder(Application.getClassLoader().getClassFinder());
  }
}


/**********************************************************************
 * $Log: ExampleDBServiceImpl.java,v $
 * Revision 1.3  2010-11-09 17:20:16  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 * Revision 1.2  2004-11-04 19:30:04  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2004/11/03 20:05:39  willuhn
 * *** empty log message ***
 *
 **********************************************************************/