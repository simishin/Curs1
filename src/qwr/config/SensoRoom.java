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

/**
 * Класс привязку датчиков к месту и дополнительные параметры датчиков,
 * незатронутые в классе описывающим тип датчиков
 * Состояние датчика вынесено в отдельный класс что бы в дальнейшем можно
 * было данный класс преобразовать в Запись и при этом сохранить изменяемость состояния
 */
public class SensoRoom implements Item {
	int id;			//0-номер датчика
	int	idRoom;		//1-ссылка на место
	int	idSensor;	//2-ссылка на тип датчика
	String title;	//3-описание связи
	QSensor	status;	//состояние датчика

	static public List<Item> list = new ArrayList<>();

	public SensoRoom(int id, int idRoom, int idSensor, String title, QSensor status) {
		this.id = id;
		this.idRoom = idRoom;
		this.idSensor = idSensor;
		this.title = title;
		this.status = status;
	}

	static public XFields[] uField = new XFields[3];//список полей для генерации элементов
	static {//инициализация класса
		uField[0] =new XFields(1, "место установки","", Room.list);
		uField[1] =new XFields(1, "Тип датчика","", VSensor.list);
		uField[2] =new XFields(1, "Описание","",1,35,"");
		list.add(new SensoRoom(0,0,0,"",null));
	}//инициализация класса
	/**
	 * Вызывается из Dialog.addn(List<Item> list, int j)
	 * Вызывает конструктор данного класса new Room(id)
	 * @param id номер нового элемента
	 * @return созданный элемент списка с заданным номером
	 */
	@Override
	public Item addID(int id) {
		SensoRoom y = (SensoRoom)list.get(0);
		Loger.logs(""+uField[0].isUse()+"   "+y.idRoom);
		int x0= uField[0].isUse() ? y.idRoom : 0;
		int	x1 = uField[1].isUse() ? y.idSensor : 0;
		String x2 = uField[2].isUse() ? y.title : "";
		return new SensoRoom(id,x0,x1,x2,new QSensor());
	}

	@Override
	public int idItem() { return id; }

	@Override
	public String title() { return title; }

	@Override
	public void setTitle(int index, String x){
		SensoRoom y = (SensoRoom) list.get(index);
		SensoRoom z = new SensoRoom(y.id, y.idRoom, y.idSensor, x, y.status);
		list.set(index,z);
	}//setTitle
	/**
	 * модификация элемента списка по шаблону, находящемся в нулевом элементе
	 * @param index индекс модифицируемого элемента в списке
	 */
	@Override
	public void update(int index){
		SensoRoom x = (SensoRoom) list.get(index);
		SensoRoom y = (SensoRoom) list.get(0);
		int x0= uField[0].isUse() ? y.idRoom : x.idRoom;
		int	x1 = uField[1].isUse() ? y.idSensor : x.idSensor;
		String x2 = uField[2].isUse() ? y.title : x.title;
		SensoRoom z = new SensoRoom(x.id, x0, x1, x2, x.status);
		list.set(index,z);
	}//update---------------------------------------------------------
	/**
	 * Выводит на экран установленные фильтры для конкретного списка элементов
	 * Вызывается из Dialog.printDefine(List<Item> list)
	 * и передает в XFields.printDefine(uField) массив данных нулевого элемента
	 */
	@Override
	public void prnStencil(){
		SensoRoom y = (SensoRoom) list.get(0);
		uField[0].let(y.idRoom);
		uField[1].let(y.idSensor);
		uField[2].let(y.title);
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
		Loger.logs(" index:"+index);
		SensoRoom y = (SensoRoom) list.get(0);
		uField[0].let(y.idRoom);
		uField[1].let(y.idSensor);
		uField[2].let(y.title);
		boolean q = XFields.uConsol(con,uField,"-Привязка датчика к месту-");
		y.idRoom=uField[0].let();
		y.idSensor=uField[1].let();
		y.title = uField[2].lets();
		list.set(index,y);
		return q;
	}//uConsol---------------------------------------------------------
	@Override
	public String printHd() { return "номер датчика\tместо\tтип\tописание"; }

	@Override
	public String printLn() {
		return id+"\t"+idRoom+"\t("+ idSensor +") "+VSensor.list.get(idSensor).title()+"\t"+title; }
	@Override
	public String printTitle() { return "Привязка датчика к месту"; }

	/**
	 * Возвращение ссылки на список данного класса
	 * Вызывается из LoadExternDataThead.workIntegrate
	 * @return ссылка на список данного класса
	 */
	public List<Item> linkList(){ return list; }
	public static final int sizeAr=5;//9 количество полей в текстовом файле данных
	/**
	 * Создание элемента нужного типа для помещения в очередь новых элементов
	 * полученных из внешних файлов. Вызывается из GrRecords.readRecordExt
	 * @param words срока из файла внешних данных
	 * @param src номер элемента в enum GrRecords
	 * @return новый элемент
	 */
	public static Item creatExtDbf(String[] words, int src) {
//        assert prnq("$ RiUser.creatExtDbf NOT REALISE $");
		if (words.length < sizeAr) {
			for (int i = 0; i < words.length; i++) prnt("+  "+i+"-"+words[i]);prnq("~"+words.length);
			return null; //недостаточное количество элементов
		}
		SensoRoom z;
		try { z=new SensoRoom(Integer.parseInt(words[1]),Integer.parseInt(words[2]),
				Integer.parseInt(words[3]),words[4],new QSensor());
		}
		catch (Exception ex) {ex.printStackTrace();return null;}
		if (list.isEmpty()) return z;
		for (Item x : list) if (z.equals((SensoRoom) x)) return null;
		return z;
	}//creatExtDbf
	@Override
	public String toString() {//создание строки для записи в текстовый файл
		return sepr+ id + sepr+ idRoom + sepr+ idSensor + sepr+ title + sepr;
	}//toString

}//class SensoRoom
