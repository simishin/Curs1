package qwr.config;

import qwr.Loger;
import qwr.XFields;
import qwr.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс определяет место установки оборудования на объекте
 */
public class Room implements Item {

	static String[] uClassRom ={"А","Б","В","Г"};
	int kClassRom;	//класс пожарной безопасности
	int idRoom;
	int floor;//этаж
	String titleRoom;//наименование помещения
	String	bild;//здание

	static public List<Item> list = new ArrayList<>();
	static public void init(){ list.add(new Room(0,0,0,"","")); }//init

	static public XFields[] uField = new XFields[3];//список полей для генерации элементов
	static {//инициализация класса
		uField[0] =new XFields(1, XFields.Typ.STRING,"Здание","",1,35);
		uField[1] =new XFields(1, XFields.Typ.INT,"Этаж","",-9,25);
		uField[2] =new XFields(1, "Класс пожарной безопасности","", uClassRom);
	}//инициализация класса

	public XFields[] uField(){return uField;}
	//для инициализации и тестирования
	public Room(int idRoom, int floor, int kClassRom, String bild, String titleRoom) {
		this.idRoom = idRoom;
		this.floor = floor;//этаж
		this.titleRoom = titleRoom;
		this.kClassRom = kClassRom;
		this.bild = bild;
	}//---------------------------------------------------------------
	/**
	 * Вызывается из Dialog.addn(List<Item> list, int j)
	 * Вызывает конструктор данного класса new Room(id)
	 * @param id номер нового элемента
	 * @return созданный элемент списка с заданным номером
	 */
	@Override
	public Item addID(int id) {
		Room y = (Room)list.get(0);
		Loger.logs(""+uField[2].isUse()+"   "+y.bild);
		String x0= uField[0].isUse() ? y.bild : "";//этаж
		int	x1 = uField[1].isUse() ? y.floor : 0;
		int x2 = uField[2].isUse() ? y.kClassRom : 0;
		return new Room(id,x1,x2,x0,"");
	}

//	@Override
//	public void printTest(){
//		Loger.prnq(">>>>>>"+uField[0].isUse()+"\t"+uField[1].isUse()+"\t"+uField[2].isUse());
//	}//printTest --------------------------------------------------------------------

	/**
	 * модификация элемента списка по шаблону, находящемся в нулевом элементе
	 * @param index индекс модифицируемого элемента в списке
	 */
	@Override
	public void update(int index){
		Room y = (Room)list.get(0);
		if (uField[0].isUse()) this.bild = y.bild;
		if (uField[1].isUse()) this.floor = y.floor;
		if (uField[2].isUse()) this.kClassRom = y.kClassRom;
	}//update---------------------------------------------------------
//	public int floor(){return floor;}
	/**
	 * Выводит на экран установленные фильтры для конкретного списка элементов
	 * Вызывается из Dialog.printDefine(List<Item> list)
	 * и передает в XFields.printDefine(uField) массив данных нулевого элемента
	 */
	@Override
	public void prnStencil(){
		assert this.idRoom == 0 : "Этот метод вызван не для нулевого элемента";
		Room y = (Room)list.get(0);
		uField[0].let(y.bild);
		uField[1].let(y.floor);
		uField[2].let(y.kClassRom);
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
		Loger.logs(" idRoom:"+this.idRoom);
		Room y = (Room)this;
		uField[0].let(y.bild);
		uField[1].let(y.floor);
		uField[2].let(y.kClassRom);
		boolean q = XFields.uConsol(con,uField,"");
		y.bild=uField[0].lets();
		y.floor=uField[1].let();
		y.kClassRom = uField[2].let();
		return q;
	}//uConsol---------------------------------------------------------

	public static void test() {//создание набора данных для тестирования
		list.add(new Room(21,1,2,"16a","101 Трансформаторная"));
		list.add(new Room(22,1,2,"16a","111 Прачка"));
		list.add(new Room(23,1,2,"16a","103 Ателье"));
		list.add(new Room(24,2,3,"16a","201 Зоопарк"));
		list.add(new Room(25,2,1,"16a","221 Аптека"));
		list.add(new Room(26,2,2,"16a","231 Салон"));
		list.add(new Room(27,2,1,"16a","270 Баня"));
		list.add(new Room(28,3,1,"16a","280 Курилка"));
		list.add(new Room(29,4,2,"16a","290 Курилка"));
		list.add(new Room(30,4,3,"16a","300 Курилка"));
		list.add(new Room(31,4,1,"16a","331 Курилка"));

	}//test

	@Override
	public String printLn() {
		return idRoom+"\t"+floor+"\t- "+ titleRoom +" -\t"+uClassRom[kClassRom]+"\t"+bild; }

	@Override
	public String printHd() { return "#\tЭтаж\t- наименование -\tКласс\tздание"; }

	@Override
	public String printTitle() { return "Помещения объекта"; }

	@Override
	public int idItem() { return idRoom; }

	@Override
	public String title() { return titleRoom; }

	@Override
	public void setTitle(int j, String x) { titleRoom =x; }

}//class Room
