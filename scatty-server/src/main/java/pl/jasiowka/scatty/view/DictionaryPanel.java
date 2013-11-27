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
package pl.jasiowka.scatty.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import pl.jasiowka.scatty.App;

/**
 * @author Piotr Jasiowka
 */
class DictionaryPanel {

	private App app;
	private AppWindow appWindow;
	private JPanel basePanel;
	private JTextPane box;

	DictionaryPanel(final App app, final AppWindow appWindow,
			final JPanel basePanel) {
		this.app = app;
		this.appWindow = appWindow;
		this.basePanel = basePanel;
		basePanel.setLayout(new BorderLayout());
		box = new JTextPane();
		box.setEditable(false);
		basePanel.add(new JScrollPane(box));
	}

	public void setData(String data) {
		box.setText(data);
	}

	public String getData() {
		return box.getText();
	}

}
