package skynet.dictgen.storer;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import skynet.dictgen.Config;
import skynet.dictgen.FieldMetaInfo;
import skynet.dictgen.Storer;
import skynet.dictgen.TableMetaInfo;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class WordStorer implements Storer {

	private Dispatch selection;

	private Dispatch item;

	private Dispatch font;

	@Override
	public void store(List<TableMetaInfo> list) {
		saveList(list);
	}

	private void saveList(List<TableMetaInfo> list) {
		ActiveXComponent wordApp = new ActiveXComponent("Word.Application"); // ����word
		// Set the visible property as required.
		Dispatch.put(wordApp, "Visible", new Variant(true));// //����word�ɼ�
		Dispatch docs = wordApp.getProperty("Documents").toDispatch();
		Dispatch document = Dispatch.call(docs, "Add").toDispatch();
		// �ĵ����룬��������////////////////////////
		selection = Dispatch.get(wordApp, "Selection").toDispatch();
		Dispatch align = Dispatch.get(selection, "ParagraphFormat")
				.toDispatch(); // ���и�ʽ����Ҫ�Ķ���
		font = Dispatch.get(selection, "Font").toDispatch(); // ���͸�ʽ����Ҫ�Ķ���
		// ���⴦��////////////////////////
		Dispatch.put(align, "Alignment", "1"); // 1:���� 2:���� 3:����
		Dispatch.put(font, "Size", "26");
		String title = "FSMS�����ֵ�";
		try {
			title = new String(
					(Config.get("word.title") + Config.get("version")));
		} catch (Exception e) {
		}
		Dispatch.call(selection, "TypeText",
				StringUtils.isEmpty(title) ? "FSMS�����ֵ�" : title); // д���������
		Dispatch.put(font, "Size", "10.5");
		Dispatch.call(selection, "TypeParagraph"); // ��һ�ж���
		Dispatch.put(align, "Alignment", "3"); // 1:���� 2:���� 3:����
		Dispatch.put(selection, "Text", "");
		Dispatch.call(selection, "MoveDown"); // ��������һ��
		// �����////////////////////////
		Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
		Dispatch.put(font, "Bold", "0"); // ��������
		Dispatch.put(font, "Size", "10.5");
		Dispatch.put(font, "Name", new Variant("����"));
		for (int i = 0; i < list.size(); i++) {
			TableMetaInfo tableInfo = list.get(i);
			List<FieldMetaInfo> fieldsInfo = tableInfo.getFields();
			Dispatch.put(font, "Bold", "0"); // ��������
			Dispatch.put(font, "Size", "10.5");
			Dispatch.put(font, "Name", new Variant("Times New Roman"));
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");
			Dispatch.call(selection, "MoveDown");
			Dispatch.put(selection, "Text", "��" + (i + 1) + "��"
					+ tableInfo.getTableName().toUpperCase());
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");
			Dispatch.call(selection, "MoveDown");
			Dispatch range = Dispatch.get(selection, "Range").toDispatch();

			item = Dispatch.call(tables, "Add", range,
					new Variant(fieldsInfo.size() + 2), new Variant(7),
					new Variant(1)).toDispatch(); // ��������,����,��������
			// item = Dispatch.call(tables, "Item", new Variant(i + 1))
			// .toDispatch();

			Dispatch fstCell = Dispatch.call(item, "Cell", new Variant(1),
					new Variant(1)).toDispatch();
			Dispatch secCell = Dispatch.call(item, "Cell", new Variant(1),
					new Variant(7)).toDispatch();
			Dispatch.call(fstCell, "Merge", secCell);

			int offset = 1;
			fillTitleCell(offset, 1, tableInfo.getRemark());

			offset++;
			fillTitleCell(offset, 1, "���");
			fillTitleCell(offset, 2, "�ֶ���");
			fillTitleCell(offset, 3, "�ֶ�����");
			fillTitleCell(offset, 4, "�Ƿ��Զ�����");
			fillTitleCell(offset, 5, "�Ƿ��Ϊ��");
			fillTitleCell(offset, 6, "Ĭ��ֵ");
			fillTitleCell(offset, 7, "��ע");

			offset++;
			for (int j = 0; j < fieldsInfo.size(); j++) {
				FieldMetaInfo field = fieldsInfo.get(j);
				fillCell(j + offset, 1, field.getPosition() + "");
				fillCell(j + offset, 2, field.getFieldName());
				fillCell(j + offset, 3, field.getFieldType());
				fillCell(j + offset, 4, field.isAutoIncrement() ? "��" : "��");
				fillCell(j + offset, 5, field.isNullable() ? "��" : "��");
				fillCell(j + offset, 6, field.getDefaultValue());
				fillCell(j + offset, 7, field.getComment());
			}
			// Dispatch.call(table1, "AutoFitBehavior", Integer.valueOf(1));
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");
		}

		Dispatch.call(document, "SaveAs", "c:/levi/" + title + ".doc");
	}

	private void fillTitleCell(int row, int col, String text) {
		Dispatch cell = Dispatch.call(item, "Cell", new Variant(row),
				new Variant(col)).toDispatch();// �У���
		Dispatch.call(cell, "Select");
		Dispatch font1 = Dispatch.get(selection, "Font").toDispatch();
		Dispatch.put(font1, "Bold", "1");
		Dispatch shading = Dispatch.get(selection, "Shading").toDispatch();
		Dispatch.put(shading, "BackgroundPatternColorIndex", new Variant(16));
		Dispatch.put(selection, "Text", text);
	}

	private void fillCell(int row, int col, String text) {
		Dispatch cell = Dispatch.call(item, "Cell", new Variant(row),
				new Variant(col)).toDispatch();// �У���
		Dispatch.call(cell, "Select");
		Dispatch.put(selection, "Text", text);
	}
}
