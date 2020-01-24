package entity;

import com.sun.faces.action.RequestMapping;
import java.awt.PageAttributes;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import entity.Almacenocadenaadn;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



@Stateless
@Path("/mutant")
public class Mutante {

    
    @POST
    @Consumes({"application/json"})
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("empty-statement")
    public Response addMutante(String ingreso){
       String ingreso1 =ingreso.replace("\"", "").replace("dna:[", "").replace("]", "").replace(" ", "");
       String[] dna = ingreso1.split(",");
       boolean mutante = false;
       
	String cadena = null;
        Boolean validoCaracteres = true;
        //System.out.println("Cargo Matriz");
        for (String dna1 : dna) {
            cadena = dna1;
            String[] agrego = cadena.split("");
            for (int j = 0; j < agrego.length - 1; j++) {
                if(!agrego[j + 1].equals("A")&&!agrego[j + 1].equals("T")&&!agrego[j + 1].equals("G")&&!agrego[j + 1].equals("C")){
                validoCaracteres = false;
                    break;
                }
            }
        }
       if(validoCaracteres.equals(false) ){
           return javax.ws.rs.core.Response.status(Status.EXPECTATION_FAILED).entity("Error").build();
       }else{
            mutante = isMutante(dna);
       }
      
       if(mutante==true){
            return javax.ws.rs.core.Response.status(Status.CREATED).entity("Mutante Si").build();
       }else{
            return javax.ws.rs.core.Response.status(Status.FORBIDDEN).entity("Mutante No").build();
       }
    }
    
    // Metodo validar 
     public static boolean isMutante(String[] dna) {
       
		String[][] matriz = new String[dna.length][dna.length];
		String[][] matrizTraspuesta = new String[dna.length][dna.length];
		String cadena = null;
		String[] almaceno = new String[dna.length];

		//System.out.println("Cargo Matriz");

		for (int i = 0; i < dna.length; i++) {
			cadena = dna[i];
			String[] agrego = cadena.split("");
			for (int j = 0; j < agrego.length - 1; j++) {
				matriz[i][j] = agrego[j + 1];
			}
		}

		// Trasponer la matriz y comparar

		for (int i = 0; i < dna.length; i++) {
			for (int j = 0; j < dna.length; j++) {
				matrizTraspuesta[j][i] = matriz[i][j];
			}
		}
		int mutante = 0;
		String comparaValor = "";
		String vectorCompara[] = new String[dna.length];
		String almacenoValFila = "";
		String almacenoValColumna = "";
		String valorAlmaceno[];
		// int almacenoPosicion=1;
		int posicion[];
		int contadorFila = 1;
		int contadorColumna = 1;
		int contadorOblicuo = 1;
		String almacenaValor[] = null;
		String[] separo = null;
		// por columna
		for (int i = 0; i < dna.length; i++) {
			cadena = dna[i];
			separo = cadena.split("");

			for (int j = 0; j < separo.length; j++) {
				if (j != 0) {
					if (almacenoValFila.equals(separo[j])) {
						contadorFila++;
						if (contadorFila == 4) {
							mutante++;
						}
					} else {
						almacenoValFila = separo[j];
						contadorFila = 1;
					}

				}

			}
		}

		for (int i = 0; i < dna.length; i++) {
			almacenoValFila = "";
			for (int j = 0; j < dna.length; j++) {
				separo[j] = matrizTraspuesta[i][j];

			}
			for (int j = 0; j < separo.length; j++) {
				if (almacenoValFila.equals(separo[j])) {
					contadorFila++;
					if (contadorFila == 4) {
						mutante++;
					}
				} else {
					almacenoValFila = separo[j];
					contadorFila = 1;
				}
			}
		}

		almacenoValFila = "";
		for (int i = 0; i < dna.length; i++) {
			if (i == 0) {
				cadena = dna[i];
				vectorCompara = cadena.split("");
				contadorOblicuo = 1;
			} else {
				for (int j = 0; j < separo.length-1; j++) {
					cadena = dna[i];
					separo = cadena.split("");
					if (vectorCompara[j].equals(separo[j+1])) {
						contadorOblicuo++;
						if (contadorOblicuo == 4) {
							mutante++;
						}
					}
				}
				vectorCompara = cadena.split("");
			}
		}

		if (mutante > 1) {
			return true;
		} else {
			return false;
		}
	}
        
}
