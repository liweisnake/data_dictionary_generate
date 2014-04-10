package skynet.dictgen.cui;

import skynet.dictgen.Config;
import skynet.dictgen.DataDictionaryGenerator;
import skynet.dictgen.DataSource;
import skynet.dictgen.impl.StandardDataDictionaryGenerator;

public class GeneratorStartup {

	public static void main(String [] args) throws Exception{
		DataDictionaryGenerator generator = new StandardDataDictionaryGenerator();
		Config.load();
		DataSource source = new DataSource(Config.get("db.driver"), Config.get("db.url"), Config.get("db.username"), Config.get("db.password"));
		generator.generate(source);
	}
	
}
