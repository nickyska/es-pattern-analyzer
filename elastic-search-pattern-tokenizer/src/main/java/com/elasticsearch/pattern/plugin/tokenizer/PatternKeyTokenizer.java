package com.elasticsearch.pattern.plugin.tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeFactory;
import org.elasticsearch.ElasticsearchIllegalArgumentException;
import org.elasticsearch.common.collect.ImmutableSet;
import org.elasticsearch.common.regex.Regex;
import org.elasticsearch.common.settings.Settings;

import com.elasticsearch.pattern.plugin.common.PatternKeyPlugin;

public final class PatternKeyTokenizer extends Tokenizer {

	private final CharTermAttribute termAtt = (CharTermAttribute) addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offsetAtt = (OffsetAttribute) addAttribute(OffsetAttribute.class);

	private final StringBuilder str = new StringBuilder();

	private int index;

	private  List<TokenizerPattern> patternsList;

	public PatternKeyTokenizer(Reader input, Settings indexSettings,int group,String analyzerName) {
		this(DEFAULT_TOKEN_ATTRIBUTE_FACTORY, input, indexSettings,group, analyzerName);
	}
	

	public PatternKeyTokenizer(AttributeFactory factory,Reader reader,Settings indexSettings,int group,String analyzerName) {
		super(factory, reader);
		
		String prefix = "index.analysis.analyzer." + analyzerName;
		
		Settings patterns = indexSettings.getByPrefix(prefix + ".patterns.");
		Settings patternNames = indexSettings.getByPrefix(prefix + ".patternNames.");
		
		if (patterns == null || patternNames == null) {
			throw new ElasticsearchIllegalArgumentException("patterns/patternNames is missing for [" + analyzerName + "] tokenizer of type " + PatternKeyPlugin.ANALYZER_INDEX_PATTERN);
		}
		
		this.patternsList = createPatterns(patterns, patternNames, indexSettings);


		validateGroup(patternsList, group);
	}


	private void validateGroup(List<TokenizerPattern> patternsList,int group) {

		for (TokenizerPattern tokenizerPattern : patternsList) {

			Matcher matcher = tokenizerPattern.getMatcher();

			if ((group >= 0) && (group > matcher.groupCount())) {
				throw new IllegalArgumentException(
						"invalid group specified: pattern only has: "
								+ matcher.groupCount() + " capturing groups");
			}
		}

	}


	public boolean incrementToken() {
		if (this.index >= this.str.length())
			return false;
		clearAttributes();

		//TODO: add support for groping
		
		for (TokenizerPattern tokenizerPatternObj : patternsList) {

			Matcher matcher = tokenizerPatternObj.getMatcher();
			String patternName = tokenizerPatternObj.getPatternName();

			while (matcher.find()) {
				
				this.termAtt.setEmpty().append(patternName);
				index = matcher.end();
				return true;
			}
		}

		if (this.str.length() - this.index == 0) {
			this.index = Integer.MAX_VALUE;
			return false;
		}

		return false;
	}

	public void end() throws IOException {
		super.end();
		int ofs = correctOffset(this.str.length());
		this.offsetAtt.setOffset(ofs, ofs);
	}

	public void reset() throws IOException {
		super.reset();
		fillBuffer(this.str, this.input);
		resetMacthers();
		this.index = 0;
	}


	private void resetMacthers() {

		for (TokenizerPattern tokenizerPatternObj : patternsList) {
			tokenizerPatternObj.getMatcher().reset(this.str);
		}
	}

	final char[] buffer = new char['?'];

	private void fillBuffer(StringBuilder sb, Reader input) throws IOException {
		sb.setLength(0);
		int len;
		while ((len = input.read(this.buffer)) > 0) {
			sb.append(this.buffer, 0, len);
		}
	}

	private List<TokenizerPattern> createPatterns(Settings patterns,Settings patternNames,Settings settings) {

		List<TokenizerPattern> patternsList = new ArrayList<>();

		ImmutableSet<Entry<String, String>> patternsEntrySet = patterns.getAsMap().entrySet();

		for (Entry<String, String> entry : patternsEntrySet) {
			String patternKey = entry.getKey();
			String patternValue = entry.getValue();

			Pattern pattern = Regex.compile(patternValue, settings.get("flags"));
			Matcher matcher = pattern.matcher("");

			patternsList.add(new TokenizerPattern(matcher,patternNames.get(patternKey)));
		}

		return patternsList;
	}


}
