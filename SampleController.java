package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class SampleController implements Initializable {
	
	// variables 
	
	 public GraphicsContext gcb, gcf; // canvas�� �� ���  gcf-canvas gcb-canvasef 
	 public boolean freedesign = true, erase = false, drawline = false,
			 drawoval = false,drawrectangle = false; //true false�� Ű�� ����
	 double startX, startY, lastX,lastY,oldX,oldY,holdX,holdY;
	 double hg;
	 
	 String Ids="";
	 
//			 c= Color.rgb(244,244,244); // �׸��� ��� �� 
	 // FXML
	
	@FXML 
	 public TextField Answer;
	
	 public Canvas canvas, canvasef;
	 public Button Pencil;
	 public Button Eraser;
	 public Button oval,line,rect;
	 public ColorPicker colorpick;
	 public RadioButton strokeRB,fillRB;
	 public Slider sizeSlider;
	 public TextArea TalkBoard;
	 
	 public ListView PlayerList;
	 public TextField ID;
	 
	@FXML
		public void onMousePressedListener(MouseEvent e){ //���� �� ���� �׸� �� ���� �� ���� 
			this.startX = e.getX();
			this.startY = e.getY();
			this.oldX = e.getX();
			this.oldY = e.getY();
		}
	 @FXML
	    public void onMouseDraggedListener(MouseEvent e){ // ���콺 ������ ����
	        this.lastX = e.getX();
	        this.lastY = e.getY();
	        	// �巡�� �� �� �Լ��� ȣ�� �� �˰��� 
	        if(drawrectangle)
	            drawRectEffect();
	        if(drawoval)
	            drawOvalEffect();
	        if(drawline)
	            drawLineEffect();
	        if(freedesign)
	            freeDrawing();
	        if(erase)
	        	erase();
	    }
	  @FXML 
	    public void onMouseReleaseListener(MouseEvent e){ 

	        if(drawrectangle)
	            drawRect();
	        if(drawoval)
	            drawOval();
	        if(drawline)
	            drawLine();
	        if(erase)
	        	erase();
	      
	    }
	  @FXML 
	    public void onMouseEnteredListener(MouseEvent e){
		  this.holdX = e.getX();
		  this.holdY = e.getY();
//		  if(erase) {
//			  eraseEffect();
//		  }
	  
	  }
	  
	
	  
	  @FXML
	    public void onMouseExitedListener(MouseEvent event)
	    { //����
//	        System.out.println("mouse exited");
	    }
	  
	  // draw method
	
	
	  	private void drawOval() //Ÿ�� �׸��� �޼ҵ� 
	    {
	        double wh = lastX - startX;
	        double hg = lastY - startY;
	        gcb.setLineWidth(sizeSlider.getValue());
//	        gcb.setLineWidth(5);

	        if(fillRB.isSelected()){
	            gcb.setFill(colorpick.getValue());
	            gcb.fillOval(startX, startY, wh, hg);
	        }else{
	            gcb.setStroke(colorpick.getValue());
	            gcb.strokeOval(startX, startY, wh, hg);
	        }
	    }

	    private void drawRect() //�簢�� �׸��� �޼ҵ� 
	    {
	        double wh = lastX - startX;
	        double hg = lastY - startY;
	        gcb.setLineWidth(sizeSlider.getValue());
//	        gcb.setLineWidth(5);

	        if(fillRB.isSelected()){
	        	 gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gcb.setFill(colorpick.getValue());
	            gcb.fillRect(startX, startY, wh, hg);
	        }else{
	        	gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gcb.setStroke(colorpick.getValue());
	            gcb.strokeRect(startX, startY, wh, hg);
	        }
	    }

	    private void drawLine() //�� �׸��� �޼ҵ� 
	    {	
	    	if(drawline) {
	    	gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	        gcb.setLineWidth(sizeSlider.getValue());
	        gcb.setStroke(colorpick.getValue());
	        gcb.strokeLine(startX, startY, lastX, lastY);
	    	}
	    }

	    public void freeDrawing() // ���콺 �̿� �׸���  �޼ҵ� 
	    {
	    	gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//	    	gcb.setLineWidth(5);
	        gcb.setLineWidth(sizeSlider.getValue());
	        gcb.setStroke(colorpick.getValue());
	        gcb.strokeLine(oldX, oldY, lastX, lastY);
	       //���콺 �̺�Ʈ���� ��ġ �޾ƿ� 
	        oldX = lastX;
	        oldY = lastY;
	    }
	    private void erase() { // ���찳 �޼ҵ� 
	    	if(erase) {
	    		gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//		    	gcb.setLineWidth(5);
		        gcb.setLineWidth(sizeSlider.getValue());
		        gcb.setStroke(Color.WHITE);
		        gcb.strokeLine(oldX, oldY, lastX, lastY);
		       //���콺 �̺�Ʈ���� ��ġ �޾ƿ� 
		        oldX = lastX;
		        oldY = lastY;
	    		 
	    
	    
	    	
	    		}
	    }
	    
	    
	    
	     // ���� �׸� �� ȿ�� 
	    
	    
	    private void drawOvalEffect()
	    {
	        double wh = lastX - startX;
	        double hg = lastY - startY;
	        gcf.setLineWidth(sizeSlider.getValue());

	        if(fillRB.isSelected()){
	            gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gcf.setFill(colorpick.getValue());
	            gcf.fillOval(startX, startY, wh, hg);
	          
	          
	        }else{
	            gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gcf.setStroke(colorpick.getValue());
	            gcf.strokeOval(startX, startY, wh, hg );
	     
	          
	        }
	       }

	    private void drawRectEffect()
	    {
	        double wh = lastX - startX;
	        double hg = lastY - startY;
	        gcf.setLineWidth(sizeSlider.getValue());

	        if(fillRB.isSelected()){
	            gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gcf.setFill(colorpick.getValue());
	            gcf.fillRect(startX, startY, wh, hg);
	          
	        }else{
	            gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gcf.setStroke(colorpick.getValue());
	            gcf.strokeRect(startX, startY, wh, hg );
	          
	        
	        }
	    }
	    
	    
	    private void drawLineEffect()
	    {
	        gcf.setLineWidth(sizeSlider.getValue());
	        gcf.setStroke(colorpick.getValue());
	        gcf.clearRect(0, 0, canvas.getWidth() , canvas.getHeight());
	        gcf.strokeLine(startX, startY, lastX, lastY);
	     
	     
	    }  
	    
//	    private void eraseEffect() {
//	    	    double wh = sizeSlider.getValue();
//		       
//		        gcf.setLineWidth(1);
//
//		      if(erase) {
//		    	  gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//		    	  gcf.setStroke(colorpick.getValue());
//		          gcf.strokeRect(holdX, holdY, wh, wh );
//		      }
//	    }
	    
	    // button connect 
	    
	    @FXML
	    private void setOvalAsCurrentShape(ActionEvent e)
	    {
	        drawline = false;
	        drawoval = true;
	        drawrectangle = false;
	        freedesign = false;
	        erase = false;
	    }

	     @FXML
	    private void setLineAsCurrentShape(ActionEvent e)
	    {
	        drawline = true;
	        drawoval = false;
	        drawrectangle = false;
	        freedesign = false;
	        erase = false;
	    }
	     @FXML
	    private void setRectangleAsCurrentShape(ActionEvent e)
	    {
	        drawline = false;
	        drawoval = false;
	        freedesign = false;
	        erase=false;
	        drawrectangle = true;
	    }
	     	 
	    @FXML 
	    private void clearCanvas(ActionEvent e)
	    {
	        gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	        gcb.clearRect(0, 0, canvasef.getWidth(), canvasef.getHeight());
	    }
	    @FXML
	    public void setErase(ActionEvent e)
	    {
	        drawline = false;
	        drawoval = false;
	        drawrectangle = false;    
	        erase = true;
	        freedesign= false;
	    }

	    @FXML
	    public void setFreeDesign(ActionEvent e)
	    {
	        drawline = false;
	        drawoval = false;
	        drawrectangle = false;    
	        erase = false;
	        freedesign = true;
	    }
    
		@Override
		public void initialize(URL url, ResourceBundle rb) {
			// TODO Auto-generated method stub
			gcf = canvas.getGraphicsContext2D();
			gcb = canvasef.getGraphicsContext2D();
			
			PlayerList.setItems(FXCollections.observableArrayList());
		}	
		
		
		
		//Client
		@FXML 
	    private void inputID(ActionEvent e)
	    {
			
		Ids = ID.getText();
		 TalkBoard.appendText("�ȳ��ϼ���\n");
		 TalkBoard.appendText(Ids +"�� ������ �������Դϴ�.\n");
		 PlayerList.getItems().add(Ids);
		
		 connectClient();
		 
	    }
		
		 
	    private void connectClient()
	    {
	    	
	    }   	 
	    	  
		
}