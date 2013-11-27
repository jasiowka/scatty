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
package pl.jasiowka.scatty.db;

import java.util.List;

import javax.persistence.*;

import pl.jasiowka.scatty.App;

/**
 * @author Piotr Jasiowka
 */
public class DbController {

	private String dbName;
	private App app;

	public DbController(App app) {
		this.app = app;
		dbName = app.getAppPath() + "/data/dictionary.odb";
	}

	public void addWord(String content, String meaning) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(dbName);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Thought entry = new Thought(content, meaning);
		em.persist(entry);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public List<Thought> getMeaning(String content) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(dbName);
		EntityManager em = emf.createEntityManager();
		StringBuilder q = new StringBuilder();
		q.append("select de ");
		q.append("from Thought de ");
		q.append("where lower(de.content) like '" + content.toLowerCase() + "'");
		TypedQuery<Thought> query = em.createQuery(q.toString(), Thought.class);
		List<Thought> results = query.getResultList();
		em.close();
		emf.close();
		return results;
	}

	public List<Thought> getAll() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(dbName);
		EntityManager em = emf.createEntityManager();
		StringBuilder q = new StringBuilder();
		q.append("select de ");
		q.append("from Thought de");
		TypedQuery<Thought> query = em.createQuery(q.toString(), Thought.class);
		List<Thought> results = query.getResultList();
		em.close();
		emf.close();
		return results;
	}

	public List<Thought> getSimilar(String content) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(dbName);
		EntityManager em = emf.createEntityManager();
		StringBuilder q = new StringBuilder();
		q.append("select de ");
		q.append("from Thought de ");
		q.append("where lower(de.content) like '%" + content.toLowerCase()
				+ "%' ");
		q.append("or locate('" + content.toLowerCase()
				+ "', lower(de.content)) > 0");
		TypedQuery<Thought> query = em.createQuery(q.toString(), Thought.class);
		List<Thought> results = query.getResultList();
		em.close();
		emf.close();
		return results;
	}

}
