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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import pl.jasiowka.scatty.commons.Config;
import pl.jasiowka.scatty.commons.ServerCommands;
import pl.jasiowka.scatty.db.DbController;
import pl.jasiowka.scatty.view.AppWindow;

/**
 * @author Piotr Jasiowka
 */
public class App {

    private Config config;
    private AppWindow appWindow;
    private DbController db;
    private String appPath;

    public App(Config config) {
        try {
            Path path = Paths.get(AppEntry.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            appPath = path.toString().replaceFirst("(/([^/]+)\\.jar)|(/classes)", "");
            this.config = config;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startApplication() {
        db = new DbController(this);
        appWindow = AppWindow.getWindow(this);
        startResponceService();
    }

    public void startResponceService() {
        if (!isResponceServiceRunning()) {
            try {
                new ResponceService(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stopResponceService() {
        if (isResponceServiceRunning()) {
            try {
                Registry registry = LocateRegistry.getRegistry(config.getRmiPort());
                ServerCommands remoteCmd = (ServerCommands) (registry.lookup("ScattyServer"));
                remoteCmd.destroyView();
                remoteCmd.stopResponceService();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isResponceServiceRunning() {
        boolean running = false;
        try {
            Registry registry = LocateRegistry.getRegistry(config.getRmiPort());
            ServerCommands remoteCmd = (ServerCommands) (registry.lookup("ScattyServer"));
            running = remoteCmd.isResponceServiceRunning();
        }
        catch (Exception e) {
            running = false;
        }
        return running;
    }

    public Config getConfig() {
        return config;
    }

    public DbController getDb() {
        return db;
    }

    public String getAppPath() {
        return appPath;
    }

    public AppWindow getView() {
        return appWindow;
    }

}
