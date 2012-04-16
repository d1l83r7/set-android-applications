package es.prueba;

import es.ucm.look.ar.ar2D.drawables.Text2D;
import es.ucm.look.ar.ar3D.core.Color4;
import es.ucm.look.ar.ar3D.core.drawables.Entity3D;
import es.ucm.look.ar.ar3D.core.drawables.primitives.Cube;
import es.ucm.look.data.EntityData;
import es.ucm.look.data.WorldEntity;
import es.ucm.look.data.WorldEntityFactory;

public class MyWorldEntityFactory extends WorldEntityFactory {
	public static final String NAME = "name" ;
	public static final String COLOR = "color";
	
	@Override
	public WorldEntity createWorldEntity (EntityData data){
		WorldEntity we =new WorldEntity (data);
		we.setDrawable2D(new Text2D(data.getPropertyValue(NAME)));
		Entity3D drawable3d = new Entity3D(new Cube());
		String color = data.getPropertyValue (COLOR);
		if(color.equals("red")){
			drawable3d.setMaterial(new Color4 (1.0f, 0.0f, 0.0f));
		}
		else if(color.equals("green"))
			drawable3d.setMaterial(new Color4 (0.0f, 1.0f ,0.0f)) ;
		we.setDrawable3D(drawable3d);
		return we ;
	}
}
