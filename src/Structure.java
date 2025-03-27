import processing.core.PApplet;
public interface Structure {
    void draw(PApplet p, Player player);
    float getDistFromPlayer();
    void setY(float val);
    float getY();
}
