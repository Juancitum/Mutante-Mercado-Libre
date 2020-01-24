/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Almacenocadenaadn;
import static entity.Mutante.isMutante;
import java.util.List;
import javax.ejb.Stateless;
import javax.management.j2ee.statistics.Stats;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.rpc.ServiceException;

/**
 *
 * @author Juancitum
 */
@Stateless
@Path("/cadenaADN")
public class AlmacenocadenaadnFacadeREST extends AbstractFacade<Almacenocadenaadn> {
    @PersistenceContext(unitName = "Mutante_MERCADOLIBREPU")
    private EntityManager em;

    public AlmacenocadenaadnFacadeREST() {
        super(Almacenocadenaadn.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Almacenocadenaadn entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Long id, Almacenocadenaadn entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Almacenocadenaadn find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Almacenocadenaadn> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Almacenocadenaadn> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count() +  " Registros Totales");
    }

    @GET
    @Path(value = "/stats")
    @Produces("text/plain")
    public String getStatics() {
        return String.valueOf(super.countStats());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    @POST
    @Path("/mutantes")
    @Consumes({"application/json"})
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("empty-statement")
    public Response addMutante(String ingreso){
    String ingreso1 =ingreso.replace("\"", "").replace("{ “dna”", "").replace("dna:[", "").replace("]", "").replace(" ", "").replace("}", "").replace("{", "");
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
           return javax.ws.rs.core.Response.status(Response.Status.EXPECTATION_FAILED).entity("Error").build();
       }else{
            mutante = isMutante(dna);
       }
      
       if(mutante==true){
            return javax.ws.rs.core.Response.status(Response.Status.CREATED).entity("Mutante Si").build();
       }else{
            return javax.ws.rs.core.Response.status(Response.Status.FORBIDDEN).entity("Mutante No").build();
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