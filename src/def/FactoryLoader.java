/*
 * Copyright 2012 Adrian Toncean
 * 
 * This file is part of Global Optimization AT.
 *
 * Global Optimization AT is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Global Optimization AT is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Global Optimization AT. If not, see <http://www.gnu.org/licenses/>.
 */

package def;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

public class FactoryLoader {
	private static File[] getFiles(String path) {
		File folder = new File(path);
		FilenameFilter filenameFilter = new FilenameFilter() {
   public boolean accept(File dir, String name) {
       return name.toLowerCase().endsWith(".jar");
   }
  };
		File[] listOfFiles = folder.listFiles(filenameFilter);
		
		return listOfFiles;
	}

	private static URL[] getURLs(File[] file) throws MalformedURLException { // no .map in Java :(
		URL[] url = new URL[file.length];

		for (int i = 0; i < file.length; i++)
			url[i] = file[i].toURI().toURL();

		return url;
	}

	private static Factory getFactory(String className, URLClassLoader classLoader) throws FactoryException {
		Factory factory = null;
		
		try {
			Class<?> classToLoad = Class.forName(className, true, classLoader); //failure to find desired class
			Object instance = classToLoad.newInstance(); //failure to instantiate
			factory = (Factory) instance; //this also may throw some important stuff
		}
		catch(ClassNotFoundException e) {
  	throw new FactoryException("Class Not Found: " + className, e);
  }
		catch(InstantiationException e) {
			throw new FactoryException("Instantiation: " + className, e);
		}
		catch(ClassCastException e) {
			throw new FactoryException("ClassCast: " + className, e);
		}
		catch(IllegalAccessException e) {
			throw new FactoryException("Illegal Access: " + className, e);
		}
		
		return factory;
	}
	
	private static ArrayList<Factory> getFactories(URL[] url) {
		ArrayList<Factory> factory = new ArrayList<Factory>(); //factory really needs to be renamed
		
		URLClassLoader childClassLoader = new URLClassLoader(url);
	
		//factory.add(new RandomSearchFactory());
		
		for (int i = 0; i < url.length; i++) {
			String className = "def." + "GA" + "Factory"; //replace "def" with some other nicer name?
			
			try {
	   factory.add(getFactory(className, childClassLoader));
			}
	  catch(FactoryException e) {
	  	Dbo.err("Caught a FactoryException:\n" + e.message + "\n Will possibly revert to default factory set");
	  }
		}
		
		return factory;
	}

	static HashMap<String, Factory> LoadFactories(String path) {
  HashMap<String, Factory> map = new HashMap<String, Factory>();
  
  URL[] url = null;
  try {
   url = getURLs(getFiles(path));
  }
  catch(MalformedURLException e) {
   Dbo.err("You will never see this message");
  }
  
  ArrayList<Factory> factory = getFactories(url);
  
  for(Factory f: factory)
  	map.put(f.getName(), f);
  
  return map;
	}
}
