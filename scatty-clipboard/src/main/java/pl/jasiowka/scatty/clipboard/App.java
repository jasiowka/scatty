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
package pl.jasiowka.scatty.clipboard;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import pl.jasiowka.scatty.commons.ServerRemote;

/**
 * @author Piotr Jasiowka
 */
public class App {

    public static final int PORT = 3131;

    public App() {
        try {
            Registry registry = LocateRegistry.getRegistry(PORT);
            ServerRemote server = (ServerRemote) (registry.lookup("ScattyServer"));
            String clipboardData = new SystemClipboard().getContent();
            server.sendThought(clipboardData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
