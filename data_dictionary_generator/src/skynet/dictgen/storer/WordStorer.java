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
		ActiveXComponent wordApp = new ActiveXComponent("Word.Application"); // 启动word
		// Set the visible property as required.
		Dispatch.put(wordApp, "Visible", new Variant(true));// //设置word可见
		Dispatch docs = wordApp.getProperty("Documents").toDispatch();
		Dispatch document = Dispatch.call(docs, "Add").toDispatch();
		// 文档对齐，字体设置////////////////////////
		selection = Dispatch.get(wordApp, "Selection").toDispatch();
		Dispatch align = Dispatch.get(selection, "ParagraphFormat")
				.toDispatch(); // 行列格式化需要的对象
		font = Dispatch.get(selection, "Font").toDispatch(); // 字型格式化需要的对象
		// 标题处理////////////////////////
		Dispatch.put(align, "Alignment", "1"); // 1:置中 2:靠右 3:靠左
		Dispatch.put(font, "Size", "26");
		String title = "FSMS数据字典";
		try {
			title = new String(
					(Config.get("word.title") + Config.get("version")));
		} catch (Exception e) {
		}
		Dispatch.call(selection, "TypeText",
				StringUtils.isEmpty(title) ? "FSMS数据字典" : title); // 写入标题内容
		Dispatch.put(font, "Size", "10.5");
		Dispatch.call(selection, "TypeParagraph"); // 空一行段落
		Dispatch.put(align, "Alignment", "3"); // 1:置中 2:靠右 3:靠左
		Dispatch.put(selection, "Text", "");
		Dispatch.call(selection, "MoveDown"); // 光标标往下一行
		// 表格处理////////////////////////
		Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
		Dispatch.put(font, "Bold", "0"); // 字型租体
		Dispatch.put(font, "Size", "10.5");
		Dispatch.put(font, "Name", new Variant("宋体"));
		for (int i = 0; i < list.size(); i++) {
			TableMetaInfo tableInfo = list.get(i);
			List<FieldMetaInfo> fieldsInfo = tableInfo.getFields();
			Dispatch.put(font, "Bold", "0"); // 字型租体
			Dispatch.put(font, "Size", "10.5");
			Dispatch.put(font, "Name", new Variant("Times New Roman"));
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");
			Dispatch.call(selection, "MoveDown");
			Dispatch.put(selection, "Text", "表" + (i + 1) + "："
					+ tableInfo.getTableName().toUpperCase());
			Dispatch.call(selection, "MoveDown");
			Dispatch.call(selection, "TypeParagraph");
			Dispatch.call(selection, "MoveDown");
			Dispatch range = Dispatch.get(selection, "Range").toDispatch();

			item = Dispatch.call(tables, "Add", range,
					new Variant(fieldsInfo.size() + 2), new Variant(7),
					new Variant(1)).toDispatch(); // 设置行数,列数,表格外框宽度
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
			fillTitleCell(offset, 1, "编号");
			fillTitleCell(offset, 2, "字段名");
			fillTitleCell(offset, 3, "字段类型");
			fillTitleCell(offset, 4, "是否自动增长");
			fillTitleCell(offset, 5, "是否可为空");
			fillTitleCell(offset, 6, "默认值");
			fillTitleCell(offset, 7, "备注");

			offset++;
			for (int j = 0; j < fieldsInfo.size(); j++) {
				FieldMetaInfo field = fieldsInfo.get(j);
				fillCell(j + offset, 1, field.getPosition() + "");
				fillCell(j + offset, 2, field.getFieldName());
				fillCell(j + offset, 3, field.getFieldType());
				fillCell(j + offset, 4, field.isAutoIncrement() ? "是" : "否");
				fillCell(j + offset, 5, field.isNullable() ? "是" : "否");
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
				new Variant(col)).toDispatch();// 行，列
		Dispatch.call(cell, "Select");
		Dispatch font1 = Dispatch.get(selection, "Font").toDispatch();
		Dispatch.put(font1, "Bold", "1");
		Dispatch shading = Dispatch.get(selection, "Shading").toDispatch();
		Dispatch.put(shading, "BackgroundPatternColorIndex", new Variant(16));
		Dispatch.put(selection, "Text", text);
	}

	private void fillCell(int row, int col, String text) {
		Dispatch cell = Dispatch.call(item, "Cell", new Variant(row),
				new Variant(col)).toDispatch();// 行，列
		Dispatch.call(cell, "Select");
		Dispatch.put(selection, "Text", text);
	}
}
