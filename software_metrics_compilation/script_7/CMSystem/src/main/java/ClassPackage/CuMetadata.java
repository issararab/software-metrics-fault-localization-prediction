package ClassPackage;

import java.util.Vector;

public class CuMetadata {
	
	
	private Vector<String> typesNames;
	

	public CuMetadata(Vector<String> typesNames) {
		this.typesNames = typesNames;
	}

	public Vector<String> getTypesNames() {
		return typesNames;
	}

	public void setTypesNames(Vector<String> typesNames) {
		this.typesNames = typesNames;
	}

}
