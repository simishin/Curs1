package qwr.SharSystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static qwr.Loger.*;

/**
 * Клас для сохранения и восстановления данных из файла
 */
public class UtilFile {
	//определяю разделитель элементов пути определенных операционной системой
	public  static final String fileSeparator=System.getProperty("file.separator");
	public  static final String sepr="\t";  //разделитель значений в файлах данных


	static String pach = "qqwr.rlq";
	private static String name(){return "rlq";}
	public static boolean save(){ return save(pach); }
	public static boolean load(){return  loadFile(Path.of(pach),0);}
//saveAs----------------------------------------------------------------------------

	/**
	 * Запись данных в файл выполняется через вызов GrRecords.ХХХХ.writPL(bw)
	 * где определяется тип(класс) данных и ссылка коллекцию элементов с
	 * преобразованием в поток. В классе должен быть переопределен метод
	 * toString() под запись в файл элемента.
	 * @param jpath имя и путь к файлу назначения
	 * @return истина если запись произошла успешно
	 */
	public static boolean save(String jpath){
		assert prnt("\nFileType:"+name()+">save (");
		assert jpath!=null:"--FileType>save pach==null";
		//создаю резервную копию
		try { if (Files.exists(Paths.get(jpath))) //нет файла
			Files.move(Paths.get(jpath),
					Paths.get(jpath.substring(0,jpath.length()-1).concat("$")),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) { e.printStackTrace(); return false;}
		assert prnt("& ");
		//записываю файл
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(jpath)))
		{   bw.write("\n" );//назначение файла
//			GrRecords.TITUL.writPL(bw);//описание данного файла
			for(GrRecords recrd: GrRecords.values()) {
				assert prnt("`"+recrd.name());
				recrd.writPL(bw);
			}//for-if
//			GrRecords.ENDFL.writPL(bw);//установка метки завершение записи
//        bw.newLine();
//        bw.flush();
			prnq(" ) File: "+ jpath+" is UpLoad");
		}catch(IOException ex){System.out.println(ex.getMessage());return false;}//catch
		return true;
	}//saveAs----------------------------------------------------------------------------
///}//loadLoc
	/**
	 * Определение доступности и Чтение содержимого файла.
	 * Вызывается из LoadExternDataThread.scanExtDbf, FileType.loadLoc>RiProdject.loadCurProdject
	 * Вызывает GrRecords.readRecordExt которая создает элемент и заносит его в список list
	 * соответствующего элемента или в общий список Records.quNewExtElement
	 * @param path указатель на читаемый файл
	 * @param scr источник файла, участвующий в создании поля order записи и определяющий
	 *               список назначения (0=Records.linkList() иначе Records.quNewExtElement)
	 * @return истина, если хотя бы одна запись включена в список
	 */
	public static boolean loadFile(Path path, int scr){
		if (!Files.exists(path)) {prnq("ERROR file is no exists! "+path); return false;}
		if (!Files.isReadable(path)){prnq("ERROR not read file! "+path); return false;}
		final int l =32; //задание минимального размера файла
		try { if (Files.size(path) < l) {//проверяю длину файла
			prnq("\nERROR file: "+path+" is litl < "+l); return false;}
		} catch (IOException e) { e.printStackTrace(); }
		AtomicBoolean qchange = new AtomicBoolean(false);
		try {
			Stream.of (Files.readAllLines(path, StandardCharsets.UTF_8)).forEach(readsrt -> {
				assert prnq("Load string "+readsrt.size()+" from "+path);
				for (String str: readsrt){//читаю последовательно строки файла
					if (str.length()<5) {assert prnq("Error length");continue;}
					String[] words =str.split(sepr);//создаю массив значений
					try { if (GrRecords.valueOf(words[0]).endLoad()) break; }//если не существует значение
					catch(Exception e){ assert prnq("Not define Word {"+words[0]+"}"); continue;}
					if(GrRecords.valueOf(words[0]).readRecord(words,scr)) qchange.set(true);
				}//for readsrt
			} );
		}catch(IOException e){e.printStackTrace();}
		return qchange.get();
	}//loadFile-------------------------------------------------------------------------

}//class UtilFile
