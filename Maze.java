package independentProject;

import javafx.scene.input.KeyEvent;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Maze extends javafx.application.Application {
	
	PerspectiveCamera camera;
	final static int height = 6;
	double angle;
	double cameraX = 0.0;
	double cameraY = 0.0;
	double cameraZ = 0.0;
	double[][] maze;
	
	public void start(Stage primaryStage) {
		
		//not entirely sure what these light things do
		AmbientLight light=new AmbientLight(Color.AQUA);
		light.setTranslateX(-180);
		light.setTranslateY(-90);
		light.setTranslateZ(-120);
		//light.getScope().addAll(box,sphere);
		
		PointLight light2=new PointLight(Color.AQUA);
		light2.setTranslateX(180);
		light2.setTranslateY(190);
		light2.setTranslateZ(180);
		//light2.getScope().addAll(box,sphere);
		
	    Group root=new Group();
	    Scene scene = new Scene(root, 600, 600);
	    drawMaze(root);
		
		primaryStage.setTitle("3D Maze");
	    primaryStage.setScene(scene);
	    primaryStage.show();
		scene.setFill(Color.SKYBLUE);
		
		camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(1000.0);
		
		//Understand this
		//Group cameraGroup = new Group();
	    //cameraGroup.getChildren().add(camera);
	    
	    //Hmmm idk here
		//root.getChildren().add(cameraGroup);
		
		scene.setCamera(camera);
		angle = 0.0;
	
		//Translate pivot = new Translate();
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
            	KeyCode key = event.getCode();
            	//System.out.println(key.toString());
                if(key.toString().equals("UP")){
                	camera.setTranslateZ(cameraZ + 1 * Math.cos(angle));
                	camera.setTranslateX(cameraX + 1 * Math.sin(angle));
                	cameraX += 1 * Math.sin(angle);
                	cameraZ += 1 * Math.cos(angle);
                }else { if (key.toString().equals("LEFT")){
                	//i don't think rotate works exactly as intended
                	camera.getTransforms().add(new Rotate(-5, cameraX, cameraY, cameraZ, Rotate.Y_AXIS));
                	angle += -5 * (180/Math.PI);
                }else {if (key.toString().equals("RIGHT")) {
                	camera.getTransforms().add(new Rotate(5, cameraX, cameraY, cameraZ, Rotate.Y_AXIS));
                	angle += 5 * (180/Math.PI);
                }else {if (key.toString().equals("DOWN")) {
                	camera.setTranslateZ(cameraZ + -1 * Math.cos(angle));
                	camera.setTranslateX(cameraX + -1 * Math.sin(angle));
                	cameraX += -1 * Math.sin(angle);
                	cameraZ += -1 * Math.cos(angle);
                }
                }}}}});
		
	}
	
	public static void main(String[] args) {
	      Application.launch(args);
	   }
	
	//maybe make maze not a field--just a thought
	public static void drawMaze(Group root) {
		PhongMaterial wallMaterial = new PhongMaterial();
		wallMaterial.setDiffuseColor(Color.GREEN);
		wallMaterial.setSpecularColor(Color.LIGHTGREEN);
		wallMaterial.setSpecularPower(32.0);
		
		PhongMaterial floorMaterial = new PhongMaterial();
		floorMaterial.setDiffuseColor(Color.RED);
		floorMaterial.setSpecularColor(Color.PINK);
		floorMaterial.setSpecularPower(10.0);
		
		//ideally, this will be deleted
		Box one = drawWall(0, 20, 40, 1, wallMaterial);
		Box two = drawWall(20, 0, 1, 40, wallMaterial);
		Box three = drawWall(-20, 0, 1, 40, wallMaterial);
		Box four = drawWall(0, -20, 40, 1, wallMaterial);
		
		//for (double[] i: maze){
		//  Box wall = drawWall(i[0], i[1], i[2], i[3], wallMaterial);
		//  root.getChildren.add(wall);
		//}
		
		Box floor = new Box(40, 1, 40);
		floor.setMaterial(floorMaterial);
		floor.setTranslateY(3);
		
		root.getChildren().addAll(one, two, three, four, floor);
	}
	
	public static Box drawWall(double x, double z, int widthX, int thicknessZ, PhongMaterial material) {
		Box retval = new Box(widthX, height, thicknessZ);
		retval.setMaterial(material);
		retval.setTranslateZ(z);
		retval.setTranslateX(x);
		return retval;
	}
	
	
	
	//So this is what we have left:
	
	//1) fix the camera movement, right now its wack  (I will work on this)
	//      -- wall opacity!!
	//2) build the maze 
	//3) make the camera not able to go through walls (can be a part, using two dimensional array)
	//   basically, call boolean method (can move in certain direction) and should be able to say yes or no, put that if statement in the key released part)
	//4) find a way to end the game when the camera reaches the end, always checking for this
	//5) make it nice! user interface, start button, replay (can be a part)
	//THIS IS ONE PART: possible next step, not necessary: write code that will generate new mazes each time

}
