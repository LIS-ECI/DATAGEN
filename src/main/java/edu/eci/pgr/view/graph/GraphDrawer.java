package edu.eci.pgr.view.graph;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import com.jgraph.JGraph;
import com.jgraph.graph.ConnectionSet;
import com.jgraph.graph.DefaultEdge;
import com.jgraph.graph.DefaultGraphCell;
import com.jgraph.graph.DefaultGraphModel;
import com.jgraph.graph.DefaultPort;
import com.jgraph.graph.Edge;
import com.jgraph.graph.GraphConstants;
import com.jgraph.graph.GraphModel;
import com.jgraph.graph.Port;

import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.sorting.Task;
import edu.eci.pgr.view.Mediator;
import edu.eci.pgr.view.ViewConstants;
import edu.eci.pgr.view.ViewPanel;
import edu.eci.pgr.view.swing.model.TableListModel;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class GraphDrawer extends ViewPanel implements Serializable{	
	private static final int EXTRA_HEIGHT = 250;

	private static final long serialVersionUID = 1L;

	public static final int HEIGHT = ModelDrawer.HEIGHT;
	public static final int WIDTH = ModelDrawer.WIDTH;
	public static final int DISTANCE = ModelDrawer.DISTANCE;
	private Color entityColor;
	private static final Color RELATIONSHIP_COLOR = Color.BLACK;
	private static final Border ENTITY_BORDER = BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createRaisedBevelBorder());
	private static final boolean OPAQUE = true;


	private static final Font FONT = new java.awt.Font("Verdana",0,10); //$NON-NLS-1$


	private static final double TABLE = 340; 
	private static final double SEPARATOR = 7;
	private static final double MIN_SEPARATOR = 3;
	private static final double LABEL_N_BUTTON = 22;
	private static final double IMAGE = 52;

	private static final int DELTA_HEIGHT = (int)(SEPARATOR*2+LABEL_N_BUTTON*2+IMAGE);
	private static final int DELTA_WIDTH = (int)(SEPARATOR*2+MIN_SEPARATOR+TABLE);
	
	private JLabel itemsPic;
	private JLabel itemsTitle;
	private JButton back;
	private JButton generateDataButton;
	private JPanel selectButtonPanel;
	private JTable tableList;
	private JScrollPane tableListScrollPane;
	private JGraph graph;
	private GraphModel model;
	private Map<DefaultGraphCell,Map<?,?>> attributes;
	private HashMap<String,DefaultGraphCell> cells;
	private HashMap<String,Position> entities;

	private JScrollPane graphPane;
	public GraphDrawer() {
		entityColor = Mediator.getInstance().BACKGROUND_COLOR;
		initGUI();
	}

	private void initGUI() {
		try {
			TableLayout thisLayout = new TableLayout(new double[][] {{SEPARATOR, TableLayout.FILL, MIN_SEPARATOR, TABLE, SEPARATOR}, {TableLayout.FILL, SEPARATOR, LABEL_N_BUTTON, IMAGE, LABEL_N_BUTTON, SEPARATOR}});
			thisLayout.setHGap(5);
			thisLayout.setVGap(5);
			setLayout(thisLayout);
			model = new DefaultGraphModel();
			this.setBackground(entityColor);
			attributes = new Hashtable<DefaultGraphCell,Map<?,?>>();
			cells = new HashMap<String, DefaultGraphCell>();
			graph = new JGraph(model);
			graph.setToolTipText(MessageBundleManager.getString("GraphDrawer.0")); //$NON-NLS-1$
			graph.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					mouseClickedEvent(e);
				}
			});
			graphPane = new JScrollPane(graph);
			add(graphPane, "1, 0");  //$NON-NLS-1$
			{
				tableListScrollPane = new JScrollPane();
				add(tableListScrollPane, "3, 0");  //$NON-NLS-1$
				tableListScrollPane.setToolTipText(MessageBundleManager.getString("GraphDrawer.1")); //$NON-NLS-1$
				tableListScrollPane.setBackground(entityColor);
				{
					TableListModel tableListModel = 
						new TableListModel(Mediator.getInstance().getTableListColumns(),
								Mediator.getInstance().getTableList());
					tableListModel.addTableModelListener(new TableModelListener(){
						public void tableChanged(TableModelEvent e) {
							tableListModelAction(e);
						}
					});
					tableList = new JTable();
					tableList.setFont(new Font("Verdana",0,11));  //$NON-NLS-1$
					tableList.setBorder(BorderFactory.createCompoundBorder(
							new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null), 
							null));
					tableList .setFillsViewportHeight(true);
					tableListScrollPane.setViewportView(tableList);
					tableList.setModel(tableListModel);
					tableList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				    for(int i=0;i<tableList.getColumnCount();i++){
					    TableColumn col = tableList.getColumnModel().getColumn(i);
					    col.setPreferredWidth(ViewConstants.TABLE_LIST_COLUMNS_WIDTHS[i]);
				    }
				}
			}
			{
				selectButtonPanel = new JPanel();
				add(selectButtonPanel, "1, 4, 3, 4");  //$NON-NLS-1$
				TableLayout selectButtonPanelLayout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL, 7.0, 7.0, TableLayout.FILL, TableLayout.FILL}, {TableLayout.FILL}});
				selectButtonPanelLayout.setHGap(5);
				selectButtonPanelLayout.setVGap(5);
				selectButtonPanel.setBackground(entityColor);
				selectButtonPanel.setLayout(selectButtonPanelLayout);
				{
					generateDataButton = new JButton();
					selectButtonPanel.add(generateDataButton, "1, 0");  //$NON-NLS-1$
					generateDataButton.setText(MessageBundleManager.getString("GraphDrawer.2"));  //$NON-NLS-1$
					generateDataButton.setFont(new java.awt.Font("Verdana",0,11));  //$NON-NLS-1$
					generateDataButton.setBorder(BorderFactory.createCompoundBorder(new SoftBevelBorder(BevelBorder.RAISED,null,null,null,null),null));
					generateDataButton.setToolTipText(MessageBundleManager.getString("GraphDrawer.3")); //$NON-NLS-1$
					generateDataButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							SetRowsPerTableAction();
						}
					});
				}
				{
					back = new JButton();
					selectButtonPanel.add(back, "4, 0");  //$NON-NLS-1$
					back.setFont(new java.awt.Font("Verdana",0,11));  //$NON-NLS-1$
					back.setBorder(BorderFactory.createCompoundBorder(new SoftBevelBorder(BevelBorder.RAISED,null,null,null,null),null));
					back.setText(MessageBundleManager.getString("GraphDrawer.4")); //$NON-NLS-1$
					back.setToolTipText(MessageBundleManager.getString("GraphDrawer.5")); //$NON-NLS-1$
					back.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							backButtonAction();
						}
					});
				}
			}
			{
				itemsTitle = new JLabel();
				add(itemsTitle, "1, 2, 3, 2");  //$NON-NLS-1$
				itemsTitle.setFont(new java.awt.Font("Arial Unicode MS",1,12));  //$NON-NLS-1$
				itemsTitle.setText(MessageBundleManager.getString("GraphDrawer.6")); //$NON-NLS-1$
			}
			{
				itemsPic = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/description.png")));  //$NON-NLS-1$
				this.add(itemsPic, "1, 3, 3, 3");  //$NON-NLS-1$
				itemsPic.setHorizontalAlignment(SwingConstants.LEFT);
				itemsPic.setHorizontalTextPosition(SwingConstants.LEFT);
			}
			this.drawGraph();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private DefaultGraphCell setVertix(String name, Rectangle bound) {
		DefaultGraphCell myCell = cells.get(name);
		if(myCell == null){
			myCell = new DefaultGraphCell(name);
			cells.put(name,myCell);
		}
		Map<?,?> cellAttribute = GraphConstants.createMap();
		attributes.put(myCell, cellAttribute);
		GraphConstants.setBounds(cellAttribute, bound);
		GraphConstants.setBackground(cellAttribute, entityColor);
		GraphConstants.setOpaque(cellAttribute, OPAQUE);
		GraphConstants.setBorder(cellAttribute, ENTITY_BORDER);
		GraphConstants.setBorderColor(cellAttribute, RELATIONSHIP_COLOR);
		GraphConstants.setFont(cellAttribute,FONT);
		GraphConstants.setEditable(cellAttribute, false);
		return myCell;
	}

	private DefaultEdge setEdge(String name, int lineEnd, boolean isEndFill) {
		DefaultEdge myEdge = new DefaultEdge(name);
		Map<?,?> edgeAttribute = GraphConstants.createMap();
		attributes.put(myEdge, edgeAttribute);
		GraphConstants.setLineEnd(edgeAttribute, lineEnd);
		GraphConstants.setEndFill(edgeAttribute, isEndFill);
		GraphConstants.setFont(edgeAttribute,FONT);
		GraphConstants.setEditable(edgeAttribute, false);
		return myEdge;
	}

	private void insertModel(Edge edge, Port source, Port target,
			Object srcCell, Object tgtCell) {
		ConnectionSet cs = new ConnectionSet(edge, source, target);
		Object[] cells = new Object[] { edge, srcCell, tgtCell };
		model.insert(cells, attributes, cs, null,null);
	}

	private void drawGraph() {
		List<Task> tasks = Mediator.getInstance().getTasks();
		if (tasks != null && tasks.size()>0) {
			ModelDrawer mdrawer = new ModelDrawer(tasks);
			for (int i = 0; i < tasks.size(); i++) {
				entities = mdrawer.getPositions();
				DefaultGraphCell cell1 = this.setVertix(tasks.get(i).getTable()
						.getName(), new Rectangle(entities.get(
						tasks.get(i).getTable().getName()).getX(), entities
						.get(tasks.get(i).getTable().getName()).getY(), WIDTH,
						HEIGHT));
				DefaultPort port1 = new DefaultPort();
				cell1.add(port1);
				for (int j = 0; j < tasks.get(i).getDependenciesLength(); j++) {
					DefaultGraphCell cell2 = this.setVertix(tasks.get(i)
							.getDependency(j).getTable().getName(),
							new Rectangle(entities.get(
									tasks.get(i).getDependency(j).getTable()
											.getName()).getX(), entities.get(
									tasks.get(i).getDependency(j).getTable()
											.getName()).getY(), WIDTH, HEIGHT));
					DefaultPort port2 = new DefaultPort();
					cell2.add(port2);
					int arrow = GraphConstants.ARROW_DIAMOND;
					if (tasks.get(i).getDependencies().get(j)
							.isPrimaryDependency())
						arrow = GraphConstants.ARROW_LINE;
					DefaultEdge edge1 = this.setEdge("",  //$NON-NLS-1$
							arrow, true);
					this.insertModel(edge1, port2, port1, cell1, cell2);
				}
				if (tasks.get(i).getDependenciesLength() < 1) {
					DefaultEdge edge = this.setEdge("",  //$NON-NLS-1$
							GraphConstants.ARROW_CLASSIC, true);
					insertModel(edge, port1, port1, cell1, cell1);
				}
			}
			int width = mdrawer.getRadius() * (WIDTH+DISTANCE);
			int height = mdrawer.getRadius() * (HEIGHT+DISTANCE);
			if (height+EXTRA_HEIGHT > ViewConstants.SCREEN_HEIGHT){
				height = ViewConstants.SCREEN_HEIGHT - 100;
			}
			if (width+WIDTH > ViewConstants.SCREEN_WIDTH){
				width = ViewConstants.SCREEN_WIDTH - 100;
			}
			graph.setSize(width,height);
			graphPane.setSize(width,height);
			this.setSize(width+DELTA_WIDTH,height+DELTA_HEIGHT+EXTRA_HEIGHT);
		}
	}
	public void mouseClickedEvent(MouseEvent me) {
		Object tableName = graph.getSelectionCell();
		if(tableName != null){
			Mediator.getInstance().selectButtonAction(tableName.toString());
		}
	}
	protected void tableListModelAction(TableModelEvent e) {
		int row = e.getLastRow();
		int col = e.getColumn();
		if(col == 2 && e.getSource() instanceof TableListModel){
			boolean value = ((Boolean)((TableListModel)e.getSource()).getValueAt(row,col)).booleanValue();
			String tableName = ((String)((TableListModel)e.getSource()).getValueAt(row, 0));
			if(value){
				((TableListModel)e.getSource()).setValueAt(Boolean.TRUE, row, 1);
				if(!Mediator.getInstance().hasStoredData(tableName)){
					JOptionPane.showMessageDialog(null,MessageBundleManager.getString("GraphDrawer.7")+" "+tableName+" "+MessageBundleManager.getString("GraphDrawer.8"),MessageBundleManager.getString("GraphDrawer.9"),JOptionPane.INFORMATION_MESSAGE);    //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				Mediator.getInstance().setUseStoredData(tableName,true);
			}
			else{
				((TableListModel)e.getSource()).setValueAt(Boolean.FALSE, row, 1);
				Mediator.getInstance().setUseStoredData(tableName,false);
			}
		}
	}
	protected void SetRowsPerTableAction() {
		int max = tableList.getModel().getRowCount();
		boolean valid = true;
		for(int i=0;i<max && valid;i++)
			if(!((Boolean)tableList.getModel().getValueAt(i,1)).booleanValue())
				valid = false;
		if(valid)
			Mediator.getInstance().fromTablesListToTablesRows();
		else
			JOptionPane.showMessageDialog(null,MessageBundleManager.getString("GraphDrawer.10"));  //$NON-NLS-1$
	}

	protected void backButtonAction() {
		Mediator.getInstance().fromTableListToDatabaseInformation();
	}
	public void repaintGUI(){
		entityColor = Mediator.getInstance().BACKGROUND_COLOR;
		initGUI();
	}
}
