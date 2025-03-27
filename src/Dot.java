import processing.core.PApplet;

public class Dot implements Structure {
    public float x, y, z;
    public float distFromPlayer;

    public Dot(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public float getDistFromPlayer() {
        return distFromPlayer;
    }

    @Override
    public void setY(float val){this.y = val;}
    @Override
    public float getY(){return this.y;}

    @Override
    public void draw(PApplet p, Player player){
        distFromPlayer = Math.abs((float)Math.sqrt((float)Math.pow((player.x-x), 2) + (float)Math.pow((player.y-y), 2) + (float)Math.pow((player.z-z), 2)));
        if(!(z < player.z)) {
            //X Position
            double scale = player.screenDist / (z - player.z);
            double xDisp = (x - player.x) * scale;
            double yDisp = (y - player.y) * scale;

            System.out.println(player.x + " " + player.y);

            p.strokeWeight(0);
            p.ellipse((float)xDisp, (float)yDisp, (900/(z-player.z)), (900/(z-player.z)));
        }
    }
}
