package br.com.ederleite.codekata.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by eml on 16/02/16.
 */
public class ConstantsCodekata {

    /**
     * ISO8859 = "ISO-8859-1"
     */
    public static final String ISO88591 = "ISO-8859-1";

    public static List<String> obterCenariosTestTable(Class<?> pClasseTeste) throws IOException {
	final File testTable = new File(
			"test/" + pClasseTeste.getPackage().getName().replaceAll("\\.", "/") + "/testTable.txt");
	System.out.println(testTable.getAbsolutePath());
	final FileInputStream inputStream = new FileInputStream(testTable);
	final List<String> cenarios = IOUtils.readLines(inputStream, ConstantsCodekata.ISO88591);
	cenarios.remove(0); // linha 0 corresponde ao titulo das colunas
	final Iterator<String> iterator = cenarios.iterator();
	while (iterator.hasNext()) {
	    final String cenario = iterator.next();
	    if (cenario.isEmpty() || cenario.startsWith("!--") || cenario.startsWith("#")) {
		iterator.remove();
	    }
	}
	return cenarios;
    }
}
