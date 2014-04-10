package skynet.dictgen.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import skynet.dictgen.Config;
import skynet.dictgen.DataDictionaryGenerator;
import skynet.dictgen.DataSource;
import skynet.dictgen.Storer;
import skynet.dictgen.TableMetaInfo;
import skynet.dictgen.db.DbExecutor;
import skynet.dictgen.db.TableMetaInfoRetriver;

public class StandardDataDictionaryGenerator implements DataDictionaryGenerator {

	@Override
	public void generate(DataSource datasource) throws Exception {
		List<TableMetaInfo> list = retriveMetaInfo(datasource);
		String outputWay = StringUtils.capitalize(Config.get("outputway")
				.trim());
		String StorerName = "skynet.dictgen.storer."
				+ (StringUtils.isEmpty(outputWay) ? "Word" : outputWay)
				+ "Storer";
		Class c = Class.forName(StorerName);
		Storer storer = (Storer) c.newInstance();
		storer.store(list);
	}

	protected List<TableMetaInfo> retriveMetaInfo(DataSource datasource)
			throws Exception {
		Class.forName(datasource.getDriverClass());
		return (List<TableMetaInfo>) new DbExecutor(
				new TableMetaInfoRetriver()).execute(datasource);
	}

}
