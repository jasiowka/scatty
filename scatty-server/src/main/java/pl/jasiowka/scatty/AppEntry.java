/*
 * Copyright (C) 2013 Piotr Jasiowka. All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package pl.jasiowka.scatty;

import pl.jasiowka.scatty.commons.XmlConfig;

/**
 * @author Piotr Jasiowka
 */
public class AppEntry {

	private static String usageInfo() {
		return "Scatty v.0.1 Copyright (c) 2013 Piotr Jasiowka\nUsage: scatty [start|stop]";
	}

	public static void main(String[] args) {
		XmlConfig config = new XmlConfig();
		App app = new App(config);
		if (args.length == 1) {
			if (args[0].equals("start"))
				app.startApplication();
			else if (args[0].equals("stop"))
				app.stopResponceService();
			else
				System.out.println(usageInfo());
		} else
			System.out.println(usageInfo());
	}

}
