import processing.core.PApplet;

import java.util.HashMap;
import java.util.Map;

public class Box implements Structure {
    //Size dimensions
    public float xSize, zSize, height;
    //Initial position, based on front bottom left corner
    public float x, y, z, distFromPlayer;
    //List of point coordinates
    public HashMap<String, Float[]> vertices;
    public HashMap<String, Float[]> perspVerts;

    public Box(float x, float y, float z, float l, float w, float h){
        this.x = x;
        this.y = y;
        this.z = z;
        this.xSize = l;
        this.zSize = w;
        this.height = h;

        this.vertices = new HashMap<String, Float[]>();
        //Base vertices
        this.vertices.put("baseFL", new Float[]{x, y, z});
        this.vertices.put("baseFR", new Float[]{x+xSize, y, z});
        this.vertices.put("baseBL", new Float[]{x, y, z+zSize});
        this.vertices.put("baseBR", new Float[]{x+xSize, y, z+zSize});

        //Top Vertices
        this.vertices.put("topFL", new Float[]{x, y-height, z});
        this.vertices.put("topFR", new Float[]{x+xSize, y-height, z});
        this.vertices.put("topBL", new Float[]{x, y-height, z+zSize});
        this.vertices.put("topBR", new Float[]{x+xSize, y-height, z+zSize});

        //copy the original vertices to the new one
        //Perpective Vertices are the coordinates of the
        //vertices after perpective translation

        perspVerts = new HashMap<>();
        for(Map.Entry<String, Float[]> entry: vertices.entrySet()){
            Float[] v = entry.getValue();
            perspVerts.put(entry.getKey(), new Float[] {v[0], v[1], v[2]});
        }
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

        //Create the new vertices locations
        for(String s : vertices.keySet()){
            if((vertices.get(s)[2] - player.z) <= 0.01f) continue;
            float scale = player.screenDist / (vertices.get(s)[2] - player.z);
            float xDisp = (vertices.get(s)[0] - player.x) * scale;
            float yDisp = (vertices.get(s)[1] - player.y) * scale;

            perspVerts.get(s)[0] = xDisp;
            perspVerts.get(s)[1] = yDisp;
        }

        //No need to draw a back surface

        p.strokeWeight(0);

        //Draw Left Surface
        if(player.x < vertices.get("topFL")[0] && player.z < vertices.get("baseFL")[2]) {
            p.fill(100, 25, 0);
            p.beginShape();
            p.vertex(perspVerts.get("baseFL")[0], perspVerts.get("baseFL")[1]);
            p.vertex(perspVerts.get("baseBL")[0], perspVerts.get("baseBL")[1]);
            p.vertex(perspVerts.get("topBL")[0], perspVerts.get("topBL")[1]);
            p.vertex(perspVerts.get("topFL")[0], perspVerts.get("topFL")[1]);
            p.endShape();
        }

        //Draw Right Surface
        if(player.x > vertices.get("topFR")[0]) {
            p.fill(150, 75, 0);
            p.beginShape();
            p.vertex(perspVerts.get("baseFR")[0], perspVerts.get("baseFR")[1]);
            p.vertex(perspVerts.get("baseBR")[0], perspVerts.get("baseBR")[1]);
            p.vertex(perspVerts.get("topBR")[0], perspVerts.get("topBR")[1]);
            p.vertex(perspVerts.get("topFR")[0], perspVerts.get("topFR")[1]);
            p.endShape();
        }

        //Draw Top Surface
        if(player.y < vertices.get("topFR")[1]) {
            p.fill(0, 200, 0);
            p.beginShape();
            p.vertex(perspVerts.get("topFR")[0], perspVerts.get("topFR")[1]);
            p.vertex(perspVerts.get("topFL")[0], perspVerts.get("topFL")[1]);
            p.vertex(perspVerts.get("topBL")[0], perspVerts.get("topBL")[1]);
            p.vertex(perspVerts.get("topBR")[0], perspVerts.get("topBR")[1]);
            p.endShape();
        }

        //Draw Bottom Surface
        if(player.y > vertices.get("baseFR")[1]) {
            p.fill(150, 75, 0);
            p.beginShape();
            p.vertex(perspVerts.get("baseFR")[0], perspVerts.get("baseFR")[1]);
            p.vertex(perspVerts.get("baseFL")[0], perspVerts.get("baseFL")[1]);
            p.vertex(perspVerts.get("baseBL")[0], perspVerts.get("baseBL")[1]);
            p.vertex(perspVerts.get("baseBR")[0], perspVerts.get("baseBR")[1]);
            p.endShape();
        }


        //Draw Front Surface
        if(player.z < vertices.get("baseFL")[2]) {
            p.fill(150, 75, 0);
            p.beginShape();
            p.vertex(perspVerts.get("baseFL")[0], perspVerts.get("baseFL")[1]);
            p.vertex(perspVerts.get("baseFR")[0], perspVerts.get("baseFR")[1]);
            p.vertex(perspVerts.get("topFR")[0], perspVerts.get("topFR")[1]);
            p.vertex(perspVerts.get("topFL")[0], perspVerts.get("topFL")[1]);
            p.endShape();
        }

        p.fill(255);
    }
}

