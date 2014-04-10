package skynet.dictgen.storer;

import java.util.List;

import skynet.dictgen.FieldMetaInfo;
import skynet.dictgen.Storer;
import skynet.dictgen.TableMetaInfo;


public class PrintStorer implements Storer{

	@Override
	public void store(List<TableMetaInfo> list) {
		for(TableMetaInfo info : list){
			System.out.println("����" + info.getTableName());
			System.out.println("\t���\t����\t����\t�Ƿ��Զ�����\t�Ƿ����Ϊ��\tĬ��ֵ\t��ע");
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
