package com.elasticsearch.pattern.plugin.analyzer;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.ElasticsearchIllegalArgumentException;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.settings.IndexSettings;

import com.elasticsearch.pattern.plugin.common.PatternKeyPlugin;
import com.elasticsearch.pattern.plugin.tokenizer.PatternKeyTokenizer;

public class PatternKeyAnalyzerProvider extends AbstractIndexAnalyzerProvider {

	private final PatternKeyAnalyzer analyzer;


	private static final class PatternKeyAnalyzer extends Analyzer {

		private int group;
		private final Settings indexSettings;
		private final String analyzerName;


		public PatternKeyAnalyzer(Settings indexSettings,int group, String name) {
			this.indexSettings = indexSettings;
			this.group = group;
			this.analyzerName = name;
		}

		@Override
		protected TokenStreamComponents createComponents(String s, Reader reader) {

			final TokenStreamComponents source = new TokenStreamComponents(new PatternKeyTokenizer(reader,indexSettings,group,analyzerName));

			return new TokenStreamComponents(source.getTokenizer());
		}
	}

	@Inject
	public PatternKeyAnalyzerProvider(Index index, @IndexSettings Settings indexSettings, Environment env, @Assisted String name, @Assisted Settings settings) {
		super(index, indexSettings, name, settings);

		Settings patterns = indexSettings.getByPrefix("index.analysis.analyzer.pciAnalyzer.patterns.");
		Settings patternNames = indexSettings.getByPrefix("index.analysis.analyzer.pciAnalyzer.patternNames.");
		if (patterns == null || patternNames == null) {
			throw new ElasticsearchIllegalArgumentException("patterns/patternNames is missing for [" + name + "] tokenizer of type " + PatternKeyPlugin.ANALYZER_INDEX_PATTERN);
		}

		int group = settings.getAsInt("group", -1);

		analyzer = new PatternKeyAnalyzer(indexSettings, group,name);
	}

	@Override
	public PatternKeyAnalyzer get() {
		return analyzer;
	}

}
