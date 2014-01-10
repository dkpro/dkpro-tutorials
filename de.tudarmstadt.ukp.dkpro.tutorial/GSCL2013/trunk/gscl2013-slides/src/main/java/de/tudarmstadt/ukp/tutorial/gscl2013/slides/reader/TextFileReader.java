package de.tudarmstadt.ukp.tutorial.gscl2013.slides.reader;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

public class TextFileReader
	extends JCasCollectionReader_ImplBase
{
	public static final String PARAM_PATH = "path";
	@ConfigurationParameter(name = PARAM_PATH, mandatory = true)
	protected File path;

	public static final String PARAM_FILENAME_PATTERN = "filenamePattern";
	@ConfigurationParameter(name = PARAM_FILENAME_PATTERN, mandatory = true, defaultValue = ".*\\.txt")
	protected String filenamePattern;

	public static final String PARAM_LANGUAGE = "language";
	@ConfigurationParameter(name = PARAM_LANGUAGE, mandatory = true)
	protected String language;

	protected Queue<File> files;
	protected int totalFiles;

	@Override
	public void initialize(UimaContext aContext)
		throws ResourceInitializationException
	{
		super.initialize(aContext);
		files = new LinkedList<File>();
		collectFiles(path, filenamePattern, files);
		totalFiles = files.size();
	}

	@Override
	public void getNext(JCas aJCas)
		throws IOException, CollectionException
	{
		File file = files.poll();

		// Now we can set the text and language
		aJCas.setDocumentText(readFileToString(file));
		aJCas.setDocumentLanguage(language);
	}
	
	public boolean hasNext()
		throws IOException, CollectionException
	{
		return !files.isEmpty();
	}

	public Progress[] getProgress()
	{
		return new Progress[] { new ProgressImpl(totalFiles - files.size(), totalFiles, "file") };
	}

	public static void collectFiles(File aPath, String aPattern, Collection<File> aFiles)
	{
		if (aPath.isFile() && Pattern.matches(aPattern, aPath.getName())) {
			aFiles.add(aPath);
		}
		if (aPath.isDirectory()) {
			for (File f : aPath.listFiles()) {
				collectFiles(f, aPattern, aFiles);
			}
		}
	}
}
