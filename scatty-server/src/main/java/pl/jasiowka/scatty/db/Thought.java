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

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Piotr Jasiowka
 */
@Entity
public class Thought implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	private String content;
	private String meaning;
	private int reminded;
	private int quizGood;
	private int quizBad;

	public Thought(String content, String meaning) {
		this.content = content;
		this.meaning = meaning;
		this.reminded = 0;
		this.quizGood = 0;
		this.quizBad = 0;
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public int getReminded() {
		return reminded;
	}

	public void setReminded(int reminded) {
		this.reminded = reminded;
	}

	public int getQuizGood() {
		return quizGood;
	}

	public void setQuizGood(int quizGood) {
		this.quizGood = quizGood;
	}

	public int getQuizBad() {
		return quizBad;
	}

	public void setQuizBad(int quizBad) {
		this.quizBad = quizBad;
	}

}
