package com.elasticsearch.pattern.plugin.tokenizer;

import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.ElasticsearchIllegalArgumentException;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.PatternTokenizerFactory;
import org.elasticsearch.index.settings.IndexSettings;

import com.elasticsearch.pattern.plugin.common.PatternKeyPlugin;

public class PatternKeyTokenizerFactory extends	PatternTokenizerFactory {

	private int group;

	@Inject
	public PatternKeyTokenizerFactory(Index index, @IndexSettings Settings indexSettings, @Assisted String name, @Assisted Settings settings) {
		super(index, indexSettings, name, settings);

		Settings patterns = indexSettings.getByPrefix("index.analysis.analyzer.pciAnalyzer.patterns.");
		Settings patternNames = indexSettings.getByPrefix("index.analysis.analyzer.pciAnalyzer.patternNames.");
		
		if (patterns == null || patternNames == null) {
			throw new ElasticsearchIllegalArgumentException("patterns/patternNames is missing for [" + name + "] tokenizer of type " + PatternKeyPlugin.ANALYZER_INDEX_PATTERN);
		}

		this.group = settings.getAsInt("group", -1);

	}


	@Override
	public Tokenizer create(Reader reader) {
		return new PatternKeyTokenizer(reader,indexSettings,group,super.name());
	}



}
