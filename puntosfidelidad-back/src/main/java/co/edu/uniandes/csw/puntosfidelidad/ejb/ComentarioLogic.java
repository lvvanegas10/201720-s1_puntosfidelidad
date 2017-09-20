package co.edu.uniandes.csw.puntosfidelidad.ejb;

import co.edu.uniandes.csw.puntosfidelidad.entities.ComentarioEntity;
import co.edu.uniandes.csw.puntosfidelidad.persistence.ComentarioPersistence;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ja.manrique
 */
@Stateless
public class ComentarioLogic {
    
    @Inject
    private ComentarioPersistence persistence;
    
    private static final Logger LOGGER = Logger.getLogger(ComentarioLogic.class.getName());
    
    /*
     * CRUD
     */
    
    public ComentarioEntity createComentario(ComentarioEntity nuevoEntity)
    {
        return persistence.create(nuevoEntity);
    }
    
    public ComentarioEntity getComentario(Long id)
    {
        return persistence.findWithId(id);
    }
    
    public List<ComentarioEntity> getComentarios()
    {
        return persistence.findAll();
    }
    public void deleteComentario(Long id)   
    {
        persistence.delete(id);
    }
    public ComentarioEntity updateComentario(ComentarioEntity nuevoEntity)
    {
        return persistence.update(nuevoEntity);
    }
}
