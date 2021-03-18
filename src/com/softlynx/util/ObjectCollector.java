package com.softlynx.util;

import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.SimpleCollector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectCollector<E> extends SimpleCollector {

	private List<E> input;
	private List<E> output;

	public ObjectCollector(List<E> objects) {
		this.input = objects;
		this.output = new ArrayList<>();
	}

	public void collect(int index) throws IOException {
		output.add(input.get(index));
	}

	public boolean needsScores() {
		return false;
	}

	public List<E> getOutput() {
		return output;
	}

	@Override
	public ScoreMode scoreMode() {
		// TODO Auto-generated method stub
		return null;
	}

}
