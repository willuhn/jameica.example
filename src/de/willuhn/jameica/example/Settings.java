/**********************************************************************
 * $Source: /cvsroot/jameica/jameica_exampleplugin/src/de/willuhn/jameica/example/Settings.java,v $
 * $Revision: 1.6 $
 * $Date: 2010-11-09 17:20:15 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.example;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;

import de.willuhn.datasource.rmi.DBService;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.I18N;


/**
 * This class holds some settings for our plugin.
 */
public class Settings
{

	private static DBService db;
	private static I18N i18n;

  /**
   * Our DateFormatter.
   */
  public final static DateFormat DATEFORMAT = DateFormat.getDateInstance(DateFormat.DEFAULT, Application.getConfig().getLocale());
  
  /**
   * Our decimal formatter.
   */
  public final static DecimalFormat DECIMALFORMAT = (DecimalFormat) DecimalFormat.getInstance(Application.getConfig().getLocale());

  /**
   * Our currency name.
   */
  public final static String CURRENCY = "EUR";

	static
	{
		DECIMALFORMAT.setMinimumFractionDigits(2);
		DECIMALFORMAT.setMaximumFractionDigits(2);
	}

	/**
	 * Small helper function to get the database service.
   * @return db service.
   * @throws RemoteException
   */
  public static DBService getDBService() throws RemoteException
	{
		if (db != null)
			return db;

		try
		{
			// We have to ask Jameica's ServiceFactory.
			// If we are running in Client/Server mode and we are the
			// client, the factory returns the remote dbService from the
			// Jameica server.
			// The name and class of the service is defined in plugin.xml
			db = (DBService) Application.getServiceFactory().lookup(ExamplePlugin.class,"exampledatabase");
			return db;
		}
		catch (Exception e)
		{
			throw new RemoteException("error while getting database service",e);
		}
	}
	
	/**
	 * Small helper function to get the translator.
   * @return translator.
   */
  public static I18N i18n()
	{
		if (i18n != null)
			return i18n;
		i18n = Application.getPluginLoader().getPlugin(ExamplePlugin.class).getResources().getI18N();
		return i18n; 
	}
  
}


/**********************************************************************
 * $Log: Settings.java,v $
 * Revision 1.6  2010-11-09 17:20:15  willuhn
 * @N Beispiel-Plugin auf aktuellen Stand gebracht. Code-Cleanup und Beispiel-Implementierung fuer Search-API hinzugefuegt
 *
 **********************************************************************/