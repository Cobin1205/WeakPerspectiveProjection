import processing.core.PApplet;
import java.util.Random;
import java.util.ArrayList;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends PApplet{

    ArrayList<ArrayList<Structure>> objects;
    Player player;

    public void settings(){
        size(900, 600);
    }

    public void setup() {
        frameRate(60);
        Random rand = new Random();

        player = new Player();
        objects = new ArrayList<>();

        for(int i=0; i<50; i++){
            objects.add(new ArrayList<Structure>());
            for(int j=0; j<50; j++){
                //front and left edges to be flat
                if(i==0 || j==0) {
                    objects.get(i).add(new Box(j*50, 0, i * 50, 50, 50, 50));
                }
                //Otherwise, build up or down
                else {
                    float yVal;
                    // If adjacent cells are even, move up or down
                    if(objects.get(i-1).get(j).getY() == objects.get(i).get(j-1).getY()){
                        yVal = objects.get(i).get(j-1).getY() + 50*(rand.nextInt(3)-1);
                    }

                    else if(objects.get(i).get(j-1).getY() - objects.get(i-1).get(j).getY() == 50f){
                        yVal = objects.get(i).get(j-1).getY() - 50*rand.nextInt(2);
                    }

                    else if(objects.get(i).get(j-1).getY() - objects.get(i-1).get(j).getY() == -50f){
                        yVal = objects.get(i).get(j-1).getY() + 50*rand.nextInt(2);
                    }

                    else { yVal = objects.get(i).get(j - 1).getY(); }
                    objects.get(i).add(new Box(j * 50, yVal, i * 50, 50, 50, 50));
                }
            }
        }
    }

    public void draw(){
        translate(450, 300);
        background(20, 20, 20);

        //Move the player around
        if(keyPressed){
            if(key == 'a' || keyCode == LEFT) player.x -= 5f;
            else if(key == 'd' || keyCode == RIGHT) player.x += 5f;
            else if(key == 'e') player.y -= 5f;
            else if(key ==  'q') player.y += 5f;
            else if(key == 'w' || keyCode == UP) player.z += 5f;
            else if(key == 's' || keyCode == DOWN) player.z -= 5f;
        }

        //Sort the objects so they render in the right order.
        for(int i=objects.size()-1; i>=0; i--) {
            objects.get(i).sort((o1, o2) -> Float.compare(o2.getDistFromPlayer(), o1.getDistFromPlayer()));
            for (Structure s : objects.get(i)) {
                s.draw(this, player);
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}