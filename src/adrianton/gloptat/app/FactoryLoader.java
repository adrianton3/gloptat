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

package adrianton.gloptat.app;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ServiceLoader;

public class FactoryLoader {
	private static class MyURLClassLoader extends URLClassLoader {
		public MyURLClassLoader() {
			super(new URL[] {});
		}

		public void addURL(URL url) {
			super.addURL(url);
		}
	}
	
	private static ClassLoader addDirToClasspath(File dir) {
		MyURLClassLoader classLoader = new MyURLClassLoader();
		
		try {
			if(dir.exists())
				for(File file : dir.listFiles())
					if(file.getName().endsWith(".jar"))
						classLoader.addURL(file.toURI().toURL());
		} catch(MalformedURLException e) {
			Dbo.out("You shall never see this");
		}

		return classLoader;
	}

	private static <T> Iterator<T> initServiceLoader(ClassLoader cl, Class<T> clazz) {
		return ServiceLoader.load(clazz, cl).iterator();
	}

	public static <T> ArrayList<T> getFactories(String dir, Class<T> clazz) {
		ClassLoader cl = addDirToClasspath(new File(dir));
		Iterator<T> iterator = initServiceLoader(cl, clazz);
		ArrayList<T> instance = new ArrayList<T>();

		if(!iterator.hasNext())
			Dbo.out("Reverting to default OAs");

		while(iterator.hasNext())
			instance.add(iterator.next());

		return instance;
	}
}
