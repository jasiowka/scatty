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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import pl.jasiowka.scatty.commons.ServerCommands;
import pl.jasiowka.scatty.commons.ServerRemote;
import pl.jasiowka.scatty.db.Thought;

/**
 * @author Piotr Jasiowka
 */
public class ResponceService extends UnicastRemoteObject implements
		ServerCommands, ServerRemote {

	private static final long serialVersionUID = 1997901693758033665L;
	private Registry registry;
	private App app;

	public ResponceService(App app) throws RemoteException {
		try {
			this.app = app;
			registry = LocateRegistry.createRegistry(app.getConfig()
					.getRmiPort());
			register("ScattyServer", this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void register(String label, Remote obj) throws Exception {
		registry.bind(label, obj);
	}

	public void unregister(String label) throws Exception {
		Remote remote = registry.lookup(label);
		registry.unbind(label);
		if (remote instanceof UnicastRemoteObject) {
			UnicastRemoteObject.unexportObject(remote, true);
		}
	}

	public void unregisterAll() throws Exception {
		for (String label : registry.list()) {
			unregister(label);
		}
	}

	@Override
	public boolean isResponceServiceRunning() {
		return true;
	}

	@Override
	public void stopResponceService() {
		try {
			unregisterAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroyView() {
		app.getView().destroy();
	}

	@Override
	public int sendThought(String thought) throws RemoteException {
		StringBuilder sb = new StringBuilder();
		List<Thought> r2 = app.getDb().getSimilar(thought);
		if (r2.size() == 0) {
			app.getView().showForEdit(thought, "");
		} else {
			for (Thought t : r2) {
				sb.append(t.getContent() + " = " + t.getMeaning() + "\n");
			}
			app.getView().showForMeaning(sb.toString());
		}
		return 123456789;
	}

}
