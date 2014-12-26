package com.elasticsearch.pattern.plugin.common;

import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.AbstractPlugin;

public class PatternKeyPlugin extends AbstractPlugin {

	private static final String PATTERN_AS_A_KEY_SUPPORT = "Pattern as a key for index";
	public static final String TOKENIZER_INDEX_PATTERN = "Pattern-Key";
	public static final String PLUGIN_NAME = "Pattern-as-Key-Pluging";
	public static final String ANALYZER_INDEX_PATTERN = "Analyzer-Pattern-Key";

	@Override
	public String name() {
		return PLUGIN_NAME;
	}

	@Override
	public String description() {
		return PATTERN_AS_A_KEY_SUPPORT;
	}

	public void onModule(AnalysisModule module) {
        module.addProcessor(new PatternKeyProcessor());
    }
}
