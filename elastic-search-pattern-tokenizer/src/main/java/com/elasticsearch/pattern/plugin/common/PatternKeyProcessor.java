package com.elasticsearch.pattern.plugin.common;

import org.elasticsearch.index.analysis.AnalysisModule;

import com.elasticsearch.pattern.plugin.analyzer.PatternKeyAnalyzerProvider;
import com.elasticsearch.pattern.plugin.tokenizer.PatternKeyTokenizerFactory;

public class PatternKeyProcessor extends AnalysisModule.AnalysisBinderProcessor {

	@Override
	public void processTokenizers(TokenizersBindings tokenizersBindings) {
		tokenizersBindings.processTokenizer(PatternKeyPlugin.TOKENIZER_INDEX_PATTERN, PatternKeyTokenizerFactory.class);
	}

	@Override
	public void processAnalyzers(AnalyzersBindings analyzersBindings) {
		analyzersBindings.processAnalyzer(PatternKeyPlugin.ANALYZER_INDEX_PATTERN, PatternKeyAnalyzerProvider.class);
	}

	
}
