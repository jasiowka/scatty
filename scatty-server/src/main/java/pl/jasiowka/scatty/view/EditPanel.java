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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pl.jasiowka.scatty.App;

/**
 * @author Piotr Jasiowka
 */
class EditPanel {

	private App app;
	private AppWindow appWindow;
	private JPanel basePanel;
	private JTextField edit1;
	private JTextField edit2;

	EditPanel(final App app, final AppWindow appWindow, final JPanel basePanel) {
		this.app = app;
		this.appWindow = appWindow;
		this.basePanel = basePanel;
		basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
		edit1 = new JTextField();
		edit2 = new JTextField();
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				app.getDb().addWord(edit1.getText(), edit2.getText());
				appWindow.hide();
			}
		});
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				appWindow.hide();
			}
		});
		basePanel.add(edit1);
		basePanel.add(edit2);
		basePanel.add(btnOK);
		basePanel.add(btnCancel);
	}

	public void setData(String data) {
		edit1.setText(data);
	}

	public String getData() {
		return edit1.getText();
	}

	public void setMeaning(String meaning) {
		edit2.setText(meaning);
	}

	public String getMeaning() {
		return edit2.getText();
	}

}
