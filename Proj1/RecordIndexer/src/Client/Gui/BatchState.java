package Client.Gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import Client.Gui.Images.ImageComponent;
import Client.Gui.Images.ImageNavigator;
import Client.Gui.Tables.Table;
import Client.Gui.spell.Corrector;
import Client.Gui.spell.SpellCorrector.NoSimilarWordFoundException;
import Shared.CommunicatingClasses.GetBatchOut;
import Shared.ModelClasses.*;

public class BatchState {
	
	private Cell currentCell;
	private String[][] indexedData;
	private boolean[][] misspelled;
	private BufferedImage image;
	private ImageComponent imageComponent;
	private FieldHelpPanel fieldHelpPanel;
	private BottomLeftTabbedPane bottomLeftComponent;
	private Table table;
	private FormEntryPanel form;
	private ImageNavigator imageNavigator;
	
	private ArrayList<Corrector> dictionaries;
	private GetBatchOut result;
	private Batch currentBatch;
	private Project project;
	private ArrayList<Field> fields;
	private int fieldNumber;
	private URL url;
	private String urlPrefix;
	
	public BatchState(String host, String port) {
		
		//TODO
		urlPrefix = "http://" + host + ":" + port + "/";
		currentCell = new Cell();
		currentCell.setFieldNum(1);
		currentCell.setRecordNum(0);
		indexedData = null;
		misspelled = null;
		image = null;
		project = null;
		fields = null;
		currentBatch = null;
		result = null;
		imageComponent = null;
		url = null;
		fieldHelpPanel = null;
		table = null;
		form = null;
		imageNavigator = null;
//		project = new Project("1890 Census", 8, 199, 60);
//		fields = new ArrayList<Field>();
//		fields.add(new Field("Last Name", 60, 300, "Records/fieldhelp/last_name.html", "Records/knowndata/1890_last_names.txt", 1));
//		fields.add(new Field("First Name", 360, 280, "Records/fieldhelp/first_name.html", "Records/knowndata/1890_first_names.txt", 1));
//		fields.add(new Field("Gender", 640, 205, "Records/fieldhelp/gender.html", "Records/knowndata/1890_gender.txt", 1));
//		fields.add(new Field("Age", 845, 120, "Records/fieldhelp/age.html", "", 1));
		
	}
	
	public void downloadBatch(GetBatchOut incomingBatch) {
		
		if(incomingBatch != null) {
			this.result = incomingBatch;
			this.setCurrentBatch(result.getBatch());
			this.setProject(result.getProj());
			this.setFields(result.getFields());
			this.setUrl(result.getUrl());
			
			initializeDictionaries();
			
			try {
				InputStream input = this.url.openStream();
				image = ImageIO.read(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			indexedData = new String[this.fields.size()][this.project.getRecordsPerBatch()];
			initializeMisspelled();
			
			imageComponent.updateBatch(this);
			fieldHelpPanel.updateBatch(this);
			imageNavigator.updateBatch(this);
			bottomLeftComponent.getTable().updateBatch(this);
			bottomLeftComponent.getFormEntry().updateBatch(this);
			bottomLeftComponent.getTable().repaint();
		}
	}
	

	public Cell getCurrentCell() {
		return currentCell;
	}

	public void setCurrentCell(Cell currentCell) {
		this.currentCell = currentCell;
	}

	public String[][] getIndexedData() {
		return indexedData;
	}

	public void setIndexedData(String[][] indexedData) {
		this.indexedData = indexedData;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	public Batch getCurrentBatch() {
		return currentBatch;
	}

	public void setCurrentBatch(Batch currentBatch) {
		this.currentBatch = currentBatch;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getFieldNumber() {
		return fieldNumber;
	}

	public void setFieldNumber(int fieldNumber) {
		this.fieldNumber = fieldNumber;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public ImageComponent getImageComponent() {
		return imageComponent;
	}

	public void setImageComponent(ImageComponent imageComponent) {
		this.imageComponent = imageComponent;
	}
	
	public GetBatchOut getResult() {
		return result;
	}

	public void setResult(GetBatchOut result) {
		
//		if(result.getBatch() != null) {
//			this.result = result;
//			this.currentBatch = result.getBatch();
//			this.project = result.getProj();
//			this.fields = result.getFields();
//			url = this.result.getUrl();
//			try {
//				InputStream input = this.url.openStream();
//				image = ImageIO.read(input);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			indexedData = new String[this.fields.size() + 1][this.project.getRecordsPerBatch()];
//			imageComponent.updateBatch(this);
//			fieldHelpPanel.updateBatch(this);
//			bottomLeftComponent.getTable().updateBatch(this);
//			bottomLeftComponent.getTable().repaint();
//		}
	}
	
	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	public FieldHelpPanel getFieldHelpPanel() {
		return fieldHelpPanel;
	}

	public void setFieldHelpPanel(FieldHelpPanel fieldHelpPanel) {
		this.fieldHelpPanel = fieldHelpPanel;
	}
	
	public void updateCell(int x, int y) {
		if(x != -1)
			currentCell.setFieldNum(x);
		if(y != -1)
			currentCell.setRecordNum(y);
		
		
		imageComponent.setCell();
		form.setCell();
		table.setCell();
		fieldHelpPanel.setCell();
	}

	public BottomLeftTabbedPane getBottomLeftComponent() {
		return bottomLeftComponent;
	}

	public void setBottomLeftComponent(BottomLeftTabbedPane bottomLeftComponent) {
		this.bottomLeftComponent = bottomLeftComponent;
		setTable(this.bottomLeftComponent.getTable());
		setForm(this.bottomLeftComponent.getFormEntry());
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
	
	public FormEntryPanel getForm() {
		return form;
	}

	public void setForm(FormEntryPanel form) {
		this.form = form;
	}
	
	public ImageNavigator getImageNavigator() {
		return imageNavigator;
	}

	public void setImageNavigator(ImageNavigator imageNavigator) {
		this.imageNavigator = imageNavigator;
	}

	public boolean[][] getMisspelled() {
		return misspelled;
	}

	public void setMisspelled(boolean[][] misspelled) {
		this.misspelled = misspelled;
	}

	public ArrayList<Corrector> getDictionaries() {
		return dictionaries;
	}

	public void setDictionaries(ArrayList<Corrector> dictionaries) {
		this.dictionaries = dictionaries;
	}

	public void addText(int x, int y, String text) {
		indexedData[x][y] = text;
		table.setText(text, x, y);
		form.setText(text, x, y);
		
		//System.out.println("HERE");
		Corrector corrector = dictionaries.get(x);
		if(!text.equals("")) {
			//System.out.println("HERE");
			if(corrector.implemented) {
				
			ArrayList<String> results = new ArrayList<>();
			//System.out.println("HERE");
			//System.out.println(dictionaries.size());
			
				try {
					results = corrector.suggestSimilarWord(text);
				} catch (NoSimilarWordFoundException e) {
					System.out.println("Caught in BatchState:addText");
					e.printStackTrace();
				}
				
				if(!results.contains(text)) {
					misspelled[x][y] = true;
				}
				else {
					misspelled[x][y] = false;
				}	
			}
			else {
				if(!text.matches("[0-9]+")) {
					misspelled[x][y] = true;
				}
				else {
					misspelled[x][y] = false;
				}
			}
			
		}
		else {
			misspelled[x][y] = false;
		}
	}
	
	public String submit() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.project.getRecordsPerBatch(); ++i) {
			for(int j = 0; j < this.fields.size(); ++j) {
				if(indexedData[j][i] != null) {
					sb.append(indexedData[j][i]);
				}
				if(j != this.fields.size()-1) {
					sb.append(",");
				}
			}
			if(i != this.project.getRecordsPerBatch()-1) {
				sb.append(";");
			}
		}
		return sb.toString();
	}
	
	private void initializeMisspelled() {
		misspelled = new boolean[fields.size()][project.getRecordsPerBatch()];
		for(int i = 0; i < fields.size(); ++i) {
			for(int j = 0; j < project.getRecordsPerBatch(); ++j) {
				misspelled[i][j] = false;
			}
		}
		
	}
	
	private void initializeDictionaries() {
		
		dictionaries = new ArrayList<>();
		for(Field f : fields) {
			
			String suffix = f.getDataPath();
//			String prefix = new File("").getAbsolutePath();
			Corrector corrector = new Corrector();
			
			if(!suffix.equals("")) {
		
				try {			
					URL url = new URL(urlPrefix + suffix);
					InputStream input = url.openStream();
					String theString = IOUtils.toString(input);
					corrector.useDictionary(theString);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Caught in BatchState:initializeDictionaries");
				}
			}
			
			dictionaries.add(corrector);
		}
	}
	
	public ArrayList<String> getSuggestions(String text, int field) {
		Corrector corrector = dictionaries.get(field);
		
		ArrayList<String> results = new ArrayList<>();
		if(!text.equals("")) {
			
			if(corrector.implemented) {
				
				try {
					results = corrector.suggestSimilarWord(text);
				} catch (NoSimilarWordFoundException e) {
					System.out.println("Caught in BatchState:getSuggestions");
					e.printStackTrace();
				}
			}
		}
		return results;
	}
	
	public void updateNav(int rows, int columns, double scale, int dx, int dy) {
		this.imageNavigator.updateRectangle(rows, columns, scale, dx, dy);
	}
	
	
}
