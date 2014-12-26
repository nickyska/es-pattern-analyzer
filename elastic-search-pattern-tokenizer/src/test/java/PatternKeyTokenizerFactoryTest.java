import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.elasticsearch.common.regex.Regex;
import org.junit.Assert;
import org.junit.Test;

import com.elasticsearch.pattern.plugin.tokenizer.PatternKeyTokenizer;


public class PatternKeyTokenizerFactoryTest {
	
	
	
	
	@Test
    public void defaultModeTest() throws IOException {
//        Analyzer analyzer = new Analyzer() {
//            @Override
//            protected TokenStreamComponents createComponents(String fieldName,
//                                                             Reader reader) {
//            	int group = -1;
//            	Pattern pattern = Regex.compile("[1-9]{6}",null);
//            	String patternName = "Account Name";
//            	
////                Tokenizer t = new PatternKeyTokenizer(reader, pattern, patternName,group);
//                
//                return new TokenStreamComponents(null);
//            }
//        };
//
//        final TokenStream test = analyzer.tokenStream("test", new StringReader("A simple phrase 123456 nicko"));
//        test.reset();
//        CharTermAttribute termAttribute = test.addAttribute(CharTermAttribute.class);

        StringBuilder strTmp = new StringBuilder();
		strTmp.setLength(15);
		strTmp.insert(2, "nicko");
        
        
//        Assert.assertTrue(test.incrementToken());
    }

}
