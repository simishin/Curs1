/**
 * Класс для логирования, расчета времени выполнения программы.
 * запускается:
 * static Loger u= Loger.init("",0);
 * Loger u= Loger.init();
 * ,где первый параметр- имя файла записи лога, либо вывод на экран, если пустой
 * второй параметр: 0-без даты, -1 текущее время, 1 относительное время выполнения
 * также при работе анализируется параметр компилятора -ea (assert).
 * Если параметр компилятора -ea не указан, то логирование можно запустить starLoger()
 * Обнулить счетчик времени можно: startTime().
 * Получить относительное время выполнения задачи можно: int deltTime().
 * Вывести текущий лог можно: void log(String s) или void logs(String s)
 */
package qwr;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Loger{
	public  static boolean  prnq(String s){System.out.println(s); return true;}
	public  static boolean  prnt(String s){System.out.print(s); return true;}
	public  static boolean  prne(String s){System.err.println(s); return true;}

	private static Loger z = null;
	private static FileWriter u = null;// null - вывод на консоль или в заданный файл
	private static long start=0; //0-без даты, -1 текущее время, 1 относительное время выполнения
	public static boolean q=false; //разрешение вывода в зависимости от assert
	private Loger(){}

	public void log(String s){
		if (!q) return;//вывод запрещен
		String pr= ((start == 0) ? "" : (start>0 ? String.valueOf((int) (System.currentTimeMillis()-start))
				: cutTim()))+ (" <").concat(Thread.currentThread().getStackTrace()[2].getClassName() + "."
				+ Thread.currentThread().getStackTrace()[2].getMethodName()).concat("> ").concat(s);
		if (u==null) System.out.println(""+pr);
		else {
			try {
				u.write(" \t"+pr+"\n");
				u.flush();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}//log---------------------------------------------------------------------------
	static public void logs(String s){
		if (!q) return;//вывод запрещен
		String pr= ((start == 0) ? "" : (start>0 ? String.valueOf((int) (System.currentTimeMillis()-start))
				: cutTim()))+ ("(").concat(Thread.currentThread().getStackTrace()[2].getClassName() + "."
				+ Thread.currentThread().getStackTrace()[2].getMethodName()).concat(") :").concat(s);
		if (u==null) System.out.println(" & "+pr);
		else {
			try {
				u.write(" \t"+pr+"\n");
				u.flush();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}//log---------------------------------------------------------------------------

	public static synchronized Loger init(){
		return init("",0);
	}
	public static synchronized Loger init(Scanner inp){
		if (z==null){
			System.out.println("Введите имя файла для записи ЛОГА либо вывод будет на экран");
			String j = inp.nextLine();
			return init(j,0);
		}
		return z;
	}//init-------------------------------------------------------------------------
	public static synchronized Loger init(String j){ return init(j,0); }//init

	public static synchronized Loger init(String j, int qTime){
		if (z==null){
			z= new Loger();
			assert q=true;//проверяю установку отладчика
			if (!q){
				System.err.println("В строке запуска программы не указана VL опция <-ea> ! Вывод лога запрещен.");
			}
//			assert Main.prnq("~"+j);
			if (!j.isBlank()){//вывод будет на экран
				try {
					u = new FileWriter(j,Boolean.parseBoolean(" "));
					u.write("Точка отсчета: \t"+cutTim()+"\n");
					u.flush();
				} catch (IOException e) { e.printStackTrace(); }
				System.out.println("Логирование в <"+j+">");
			}
			else System.out.println("Вывод <лога> на консоль ");
			start = (qTime>0) ? System.currentTimeMillis() : qTime;
		}
		return z;
	}//init
	public static void startTime(){
		if (start>=0) start=System.currentTimeMillis();
		System.out.println("& 0("+Thread.currentThread().getStackTrace()[2].getClassName() + "."
				+ Thread.currentThread().getStackTrace()[2].getMethodName()+")  Таймер перезапущен");
	}//startTime
	public static void begin(){ q=true; System.out.println("Вывод лога разрешен!"); }
	public static void resume(){  assert q=true; } //возобновить
	public static void suspend(){ q=false; }
	protected static int deltTime(){ return (int) (System.currentTimeMillis()-start); }
	private static String cutTim() {
		SimpleDateFormat z = new SimpleDateFormat(" dd.MM.yy: hh:mm.ss ");
		return z.format(new Date());
	}
	//    public static String methodName() {
//        return Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX].getMethodName();
//    }
	public static String getCurrentMethodName() {
		return StackWalker.getInstance()
				.walk(s -> s.skip(1).findFirst())
				.get()
				.getMethodName();
	}

	public static String getCallerMethodName() {
		return StackWalker.getInstance()
				.walk(s -> s.skip(2).findFirst())
				.get()
				.getMethodName();
	}
	public static String getCurrentMethodNameq() {
		return Thread.currentThread().getStackTrace()[2].getClassName() + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
	}

}//Loger