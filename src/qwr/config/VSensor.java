package qwr.config;

import qwr.Item;
import qwr.Loger;
import qwr.XFields;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static qwr.Loger.prnq;
import static qwr.Loger.prnt;
import static qwr.SharSystem.UtilFile.sepr;

public final class VSensor implements Item {
//	static private int count = 0;
private final int id;
	private int jTyp;
	private String title;

	static XFields[] uField = new XFields[2];//список полей для генерации элементов
	private static final String[] uTyp = {"Дискретный", "Инверсный дискретный", "С тремя состояниями",
			"С тремя состояниями инверсный", "С четырьмя состояниями",
			"С четырьмя состояниями инверсный"};
	static public List<Item> list = new ArrayList<>();
	static {//инициализация класса
		uField[0] = new XFields(1, "тип диагностики", "", uTyp);
		uField[1] = new XFields(1, XFields.Typ.STRING, "Обозначение", "", 1, 35);
		list.add(new VSensor(0, 0, ""));
	}//инициализация класса


	/**
	 * Возвращение ссылки на список данного класса
	 * Вызывается из LoadExternDataThead.workIntegrate
	 * @return ссылка на список данного класса
	 */
	public List<Item> linkList(){ return list; }

	public VSensor(int id, int jTyp, String title) {
		this.id = id;
		this.jTyp = jTyp;
		this.title = title;
	}

	@Override
	public String printLn() {return id + "\t" + uTyp[jTyp] + "\t" + title;	}

	@Override
	public String printHd() {return "№\tТип выхода\tтип датчика";}

	@Override
	public String printTitle() { return "Типы применяемых датчиков";}

	@Override
	public int idItem() { return id;	}

	@Override
	public Item addID(int id) {
		VSensor y = (VSensor) list.get(0);
		int x0 = uField[0].isUse() ? y.jTyp : 0;
		String x1= uField[1].isUse() ? y.title : "";
		return new VSensor(id,x0,x1);
	}//addID-----------------------------------------------
	/**
	 * модификация элемента списка по шаблону, находящемся в нулевом элементе
	 */
	@Override
	public void update(int index){
		VSensor y = (VSensor) list.get(0);
		if (uField[0].isUse()) this.jTyp = y.jTyp;
		if (uField[1].isUse()) this.title = y.title;

	}//update---------------------------------------------------------
	/**
	 * Выводит на экран установленные фильтры для конкретного списка элементов
	 * Вызывается из Dialog.printDefine(List<Item> list)
	 * и передает в XFields.printDefine(uField) массив данных нулевого элемента
	 */
	@Override
	public void prnStencil(){
		assert this.id == 0 : "Этот метод вызван не для нулевого элемента";
		VSensor y = (VSensor) list.get(0);
		uField[0].let(y.jTyp);
		uField[1].let(y.title);
		XFields.prnStencil(uField);
	}//printDefine
	/**
	 * Вызывается из Dialog.editDef(Scanner con, List<Item> list, int index)
	 * @param con консоль
	 * @return транслирует данные из XFields.uConsol(con,uField,""), где
	 * истина - выход на верхний уровень меню, лож- продолжение редактирования списка
	 */
	@Override
	public boolean uConsol(int index, Scanner con) {
		Loger.logs(" idRoom:"+this.id);
		VSensor z = (VSensor) list.get(0);
		VSensor y = (VSensor) this;
		uField[0].let(y.jTyp);
		uField[1].let(y.title);
		boolean q = XFields.uConsol(con,uField,"");
		y.jTyp =uField[0].let();
		y.title =uField[1].lets();
		z.jTyp =uField[0].let();
		z.title =uField[1].lets();
		return q;
	}//uConsol---------------------------------------------------------

	@Override
	public void setTitle(int j, String x) { title=x; }

	public String title() {
		return title;
	}

	public static final int sizeAr=4;//9 количество полей в текстовом файле данных
	/**
	 * Создание элемента нужного типа для помещения в очередь новых элементов
	 * полученных из внешних файлов. Вызывается из GrRecords.readRecordExt
	 * @param words срока из файла внешних данных
	 * @param src номер элемента в enum GrRecords
	 * @return новый элемент
	 */
	public static Item creatExtDbf(String[] words, int src) {
		if (words.length < sizeAr) {
			for (int i = 0; i < words.length; i++) prnt("+  "+i+"-"+words[i]);prnq("~"+words.length);
			return null; //недостаточное количество элементов
		}
		VSensor z;
		try { z=new VSensor(Integer.parseInt(words[1]),Integer.parseInt(words[2]), words[3]);
		}
		catch (Exception ex) {ex.printStackTrace();return null;}
		if (list.isEmpty()) return z;
		for (Item x : list) if (z.equals((VSensor) x)) return null;
		return z;
	}//creatExtDbf

	@Override
	public String toString() {//создание строки для записи в текстовый файл
		return sepr+ id + sepr+ jTyp + sepr+ title + sepr;
	}//toString
	//public VSensor(int id, int jTyp, String title)
}//record VSensor