package skynet.dictgen.storer;

import java.util.List;

import skynet.dictgen.FieldMetaInfo;
import skynet.dictgen.Storer;
import skynet.dictgen.TableMetaInfo;


public class PrintStorer implements Storer{

	@Override
	public void store(List<TableMetaInfo> list) {
		for(TableMetaInfo info : list){
			System.out.println("表名" + info.getTableName());
			System.out.println("\t编号\t列名\t类型\t是否自动增长\t是否可以为空\t默认值\t备注");
			for(FieldMetaInfo fieldInfo : info.getFields()){
				System.out.print("\t" + fieldInfo.getPosition());
				System.out.print("\t" + fieldInfo.getFieldName());
				System.out.print("\t" + fieldInfo.getFieldType());
				System.out.print("\t" + fieldInfo.isAutoIncrement());
				System.out.print("\t" + fieldInfo.isNullable());
				System.out.print("\t" + fieldInfo.getDefaultValue());
				System.out.println("\t" + fieldInfo.getComment());
			}
		}
	}

}
