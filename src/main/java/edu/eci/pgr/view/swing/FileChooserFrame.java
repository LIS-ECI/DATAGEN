package edu.eci.pgr.view.swing;

import info.clearthought.layout.TableLayout;

import java.io.File;
import java.io.Serializable;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.view.Mediator;
import edu.eci.pgr.view.ViewConstants;


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
public class FileChooserFrame extends JFileChooser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int returnVal;

	public static final int OPEN =1;
	public static final int SAVE =2;

	public FileChooserFrame(int mode) {
		super();
		initGUI(mode);
	}

	private void initGUI(int mode) {
		try {
			TableLayout thisLayout = new TableLayout(new double[][] {{7.0, TableLayout.FILL, 396.0, 
				TableLayout.FILL, 7.0}, {400}});
			thisLayout.setHGap(5);
			thisLayout.setVGap(5);
			{
				MyFilter filter = new MyFilter();
				this.setFileFilter(filter);
				this.returnVal = mode==OPEN?
						this.showOpenDialog(this)
						:this.showSaveDialog(this);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
							if(mode==SAVE)
								Mediator.getInstance().savePlan(this.getSelectedFile());
							else if(mode==OPEN)
								Mediator.getInstance().loadPlan(this.getSelectedFile());
						}else if (returnVal == JFileChooser.CANCEL_OPTION)
							this.cancelSelection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class MyFilter extends FileFilter{
		@Override
		public boolean accept(File f) {
			return f.getName().endsWith(ViewConstants.FILESEXTENTION)
			|| f.isDirectory();
		}
		@Override
		public String getDescription() {
			return MessageBundleManager.getString("FILEFILTERDESCRIPTION");
		}		
	}

	public boolean isCancel() {
		return returnVal==JFileChooser.CANCEL_OPTION;
	}

}
